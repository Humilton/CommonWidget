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
        String mapUrl = getString(R.string.google_map_url);
        if(DemoApp.location != null) {
            mapUrl += "?lat=" + DemoApp.location.getLatitude() + "&lon=" + DemoApp.location.getLongitude();
        }
        mBinding.webview.loadUrl(mapUrl);

//        String mapUrl = "file:///android_asset/travel.html?pos=30.4842586517334|114.4147796630859|湖北省武汉市洪山区南湖大道|30.48876571655273|114.4176712036133|湖北省武汉市洪山区关山大道532号|30.48878860473633|114.4175262451172|湖北省武汉市洪山区关山大道532号|30.4887580871582|114.4176330566406|湖北省武汉市洪山区关山大道532号|30.48859786987305|114.41748046875|湖北省武汉市洪山区关山大道530号";
//        if(DemoApp.location != null) {
//            mapUrl += "&lat=" + DemoApp.location.getLatitude() + "&lon=" + DemoApp.location.getLongitude();
//            Log.e("==>", "lat=" + DemoApp.location.getLatitude() + "&lon=" + DemoApp.location.getLongitude());
//        }
//        mBinding.webview.loadUrl(mapUrl);
    }
}
