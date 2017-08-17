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
import android.util.Log;
import android.view.KeyEvent;
import android.widget.Toast;

import com.github.Humilton.entity.GoogleGeoCodeResponse;

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

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (KeyEvent.KEYCODE_BACK == keyCode) {
            Toast.makeText(getApplicationContext(), "Back 键已被禁用...", Toast.LENGTH_SHORT).show();
            return true;//同理
        }
        if (KeyEvent.KEYCODE_HOME == keyCode) {
            Toast.makeText(getApplicationContext(), "HOME 键已被禁用...", Toast.LENGTH_SHORT).show();
            return true;//同理
        }
        return super.onKeyDown(keyCode, event);
    }

    private final LocationListener mLocationListener = new LocationListener() {
        @Override
        public void onLocationChanged(final Location location) {
            DemoApp.location = location;

            String urlString = String.format(iNFOMATION_URL, location.getLatitude(), location.getLongitude());
            Request request = new Request.Builder()
                    .url(urlString)
                    .build();

            new OkHttpClient().newCall(request).enqueue(new Callback() {

                @Override
                public void onFailure(Call call, IOException e) {
                    e.printStackTrace();
                }

                @Override
                public void onResponse(Call call, okhttp3.Response response) throws IOException {
                    if (response == null) return;
                    ResponseBody resp = response.body();
                    if (resp == null) return;

                    // deconstruct like https://stackoverflow.com/questions/14314183/get-country-name-from-latitude-and-longitude
                    try {
                        GoogleGeoCodeResponse data = DemoApp.gson.fromJson(resp.string(), GoogleGeoCodeResponse.class);
                        if (data == null) return;
                        if (data.status.toUpperCase().equals("OK")) {
                            for (GoogleGeoCodeResponse.results result : data.results)
                                for (GoogleGeoCodeResponse.address_component comp : result.address_components) {
                                    for (String _type : comp.types) {
                                        if (_type.compareTo("country") == 0) {
                                            Log.e("==>", comp.long_name + "," + comp.short_name);
                                            DemoApp.isInChina = comp.short_name.toUpperCase().equals("CN");
                                            return;
                                        }
                                    }
                                }
                        }
                    } catch (NullPointerException e) {
                        e.printStackTrace();
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
