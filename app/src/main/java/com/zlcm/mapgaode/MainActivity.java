package com.zlcm.mapgaode;

import android.location.Location;
import android.location.LocationListener;
import android.os.CountDownTimer;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.amap.api.location.AMapLocation;
import com.amap.api.location.AMapLocationClient;
import com.amap.api.location.AMapLocationClientOption;
import com.amap.api.location.AMapLocationListener;
import com.google.gson.Gson;
import com.loc.g;

import org.json.JSONArray;

import static com.loc.g.s;

public class MainActivity extends AppCompatActivity {

    private AMapLocationClient mLocationClient;
    private double longitude;
    private double latitude;
    private int i=1;
    private StringBuffer stringBuffer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mLocationClient = new AMapLocationClient(getApplicationContext());
        AMapLocationClientOption locationOption = new AMapLocationClientOption();
        //设置定位间隔
        locationOption.setInterval(5000);
        //设置定位模式，其他模式见LocationMode
        locationOption.setLocationMode(AMapLocationClientOption.AMapLocationMode.Battery_Saving);
        mLocationClient.setLocationOption(locationOption);
        mLocationClient.setLocationListener(new AMapLocationListener() {



            @Override
            public void onLocationChanged(AMapLocation aMapLocation) {
                //纬度
                latitude = aMapLocation.getLatitude();
                //经度
                longitude = aMapLocation.getLongitude();
                Log.i("location",
                        "纬度" + latitude + "经度"
                                + longitude
                );
            }
        });
        //开启定位
        mLocationClient.startLocation();
        stringBuffer = new StringBuffer();
        new CountDownTimer(30000,5000){

            @Override
            public void onTick(long millisUntilFinished) {

                LocationInfoBean locationInfoBean = new LocationInfoBean();
                locationInfoBean.setLatitude(latitude);
                locationInfoBean.setLongitude(longitude);
                String s = new Gson().toJson(locationInfoBean);
                Toast.makeText(MainActivity.this, "第"+i+"次"+ s, Toast.LENGTH_SHORT).show();
                stringBuffer.append(s);
                i++;
            }

            @Override
            public void onFinish() {
                System.out.println("aaa"+stringBuffer.toString());
            }
        }.start();

    }
}
