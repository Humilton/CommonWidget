package com.github.Humilton;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.databinding.ViewDataBinding;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.text.TextUtils;
import android.util.Log;
import android.view.KeyEvent;
import android.widget.Toast;

import com.github.Humilton.entity.GoogleGeoCodeResponse;
import com.github.Humilton.util.LocationUtil;
import com.lbt05.EvilTransform.GCJPointer;
import com.lbt05.EvilTransform.TransformUtil;
import com.lbt05.EvilTransform.WGSPointer;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.ResponseBody;
/**
 * Created by Hamilton on 2017/7/31.
 */

public abstract class BaseSampleActivity<B extends ViewDataBinding> extends BaseActivity<B> {
    private LocationManager mLocationManager;
    private static final int REQUEST_CODE_ASK_LOCATION_PERMISSIONS = 5654;
    private static final String iNFOMATION_URL = "http://maps.google.cn/maps/api/geocode/json?language=zh-CN&sensor=true&latlng=%1$s,%2$s";
    private Toast tObj = null;

    public void initView() {
        this.getWindow().setFlags(0x80000000, 0x80000000);

        mLocationManager = (LocationManager) mContext.getApplicationContext().getSystemService(Context.LOCATION_SERVICE);
        if (ActivityCompat.checkSelfPermission(mContext, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED &&
                ActivityCompat.checkSelfPermission(mContext, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {

            ActivityCompat.requestPermissions(mContext,
                    new String[]{Manifest.permission.ACCESS_COARSE_LOCATION, Manifest.permission.ACCESS_FINE_LOCATION},
                    REQUEST_CODE_ASK_LOCATION_PERMISSIONS);
            return;
        }
        mLocationManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, 1000, 1000, mLocationListener);
        mLocationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 1000, 1000, mLocationListener);
    }

    private long backKeyDownTime = 0;
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if(tObj != null)  tObj.cancel();

        if (KeyEvent.KEYCODE_BACK == keyCode && this instanceof SampleActivity) {
            if ((System.currentTimeMillis() - backKeyDownTime) > 1000) {
                tObj = Toast.makeText(getApplicationContext(), "再按一次退出程序", Toast.LENGTH_SHORT);
                tObj.show();
                backKeyDownTime = System.currentTimeMillis();
                return true;
            }
        }
        if (KeyEvent.KEYCODE_HOME == keyCode) {
            tObj = Toast.makeText(getApplicationContext(), "HOME 键已被禁用...", Toast.LENGTH_SHORT);
            tObj.show();
            return true;//同理
        }
        return super.onKeyDown(keyCode, event);
    }

    private final LocationListener mLocationListener = new LocationListener() {
        @Override
        public void onLocationChanged(final Location location) {
            DemoApp.location = location;

            boolean isForeign = TransformUtil.outOfChina(location.getLatitude(), location.getLongitude());
            DemoApp.isInChina = !isForeign;

            GCJPointer p2 ;
            if(DemoApp.isInChina) {
                WGSPointer p1 = new WGSPointer(location.getLatitude(), location.getLongitude());
                p2 = p1.toGCJPointer();
            }
            else {
                p2 = new GCJPointer(location.getLatitude(), location.getLongitude());
            }

            LocationUtil.queryLocation(p2.getLatitude(), p2.getLongitude(), new LocationUtil.GoogleGeoCodeResponseCallback() {
                @Override
                public void onResponseSuccess(GoogleGeoCodeResponse data) {
                    for (GoogleGeoCodeResponse.results result : data.results) {
                        DemoApp.formatted_address = result.formatted_address;
                        Log.e("==>", result.formatted_address);
                        for (GoogleGeoCodeResponse.address_component comp : result.address_components) {
                            for (String _type : comp.types) {
                                if (_type.compareTo("country") == 0) {
                                    Log.e("==>", comp.long_name + "," + comp.short_name);
                                    DemoApp.isInChina = comp.short_name.toUpperCase().equals("CN");
                                }
                            }
                        }
                    }
                }
            });
        }

        @Override
        public void onStatusChanged(String provider, int status, Bundle extras) {
        }

        @Override
        public void onProviderEnabled(String provider) {
        }

        @Override
        public void onProviderDisabled(String provider) {
        }
    };

    @Override
    public void onDestroy() {
        super.onDestroy();
        if(mLocationManager != null)
            mLocationManager.removeUpdates(mLocationListener);
    }
}
