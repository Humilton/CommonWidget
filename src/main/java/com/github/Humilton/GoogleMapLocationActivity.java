package com.github.Humilton;

import android.Manifest;
import android.content.pm.PackageManager;
import android.location.LocationManager;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.github.Humilton.databinding.MapBinding;

/**
 * Created by Hamilton on 2017/7/28.
 */

public class GoogleMapLocationActivity extends BaseActivity<MapBinding> {
    private static final int REQUEST_CODE_ASK_LOCATION_PERMISSIONS = 5654;

    @Override
    public int initBinding() {
        return R.layout.map;
    }

    @Override
    public void initView() {
//        mBinding.titleBar.setRightLayoutClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                mBinding.webview.reload();
//            }
//        });

//        if (ActivityCompat.checkSelfPermission(mContext, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED &&
//                ActivityCompat.checkSelfPermission(mContext, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
//
//            ActivityCompat.requestPermissions(mContext,
//                    new String[]{Manifest.permission.ACCESS_COARSE_LOCATION, Manifest.permission.ACCESS_FINE_LOCATION},
//                    REQUEST_CODE_ASK_LOCATION_PERMISSIONS);
//            return;
//        }
//        else {
//            mBinding.webview.loadUrl(getString(R.string.google_map_url));
//        }

        String mapUrl = getString(R.string.google_map_url);
        if(DemoApp.location != null) {
            mapUrl += "?lat=" + DemoApp.location.getLatitude() + "&lon=" + DemoApp.location.getLongitude();
        }
        Log.e("==>", "mapUrl = " + mapUrl);
        mBinding.webview.loadUrl(mapUrl);
    }

//    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions,
//                                           @NonNull int[] grantResults) {
//        if (requestCode == REQUEST_CODE_ASK_LOCATION_PERMISSIONS) {
//            if (grantResults[0] != PackageManager.PERMISSION_GRANTED) {
//                // Permission Denied
//                Toast.makeText(mContext, R.string.location_denied, Toast.LENGTH_SHORT).show();
//            } else {
//                if (ActivityCompat.checkSelfPermission(mContext, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED &&
//                        ActivityCompat.checkSelfPermission(mContext, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
//                    return;
//                }
//            }
//
//            mBinding.webview.loadUrl(getString(R.string.google_map_url));
//        }
//        else {
//            super.onRequestPermissionsResult(requestCode, permissions, grantResults);
//        }
//    }
}
