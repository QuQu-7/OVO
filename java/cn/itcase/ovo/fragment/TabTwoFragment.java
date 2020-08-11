package cn.itcase.ovo_ui.fragment;

import android.Manifest;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;

import com.baidu.location.BDLocation;
import com.baidu.location.BDLocationListener;
import com.baidu.location.LocationClient;
import com.baidu.location.LocationClientOption;

import java.util.ArrayList;
import java.util.List;

public class TabTwoFragment extends androidx.fragment.app.Fragment {

    public LocationClient mLocationClient;
    //private MapView mapview;
    private TextView positionText;
    private StringBuilder currentPosition;
    List<String> permissionList = new ArrayList<>();
    public TabTwoFragment() {
    }

    @Nullable
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.layout2, null);
        positionText = view.findViewById(R.id.position_text_view);
        return view;
    }
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mLocationClient = new LocationClient((getActivity().getApplicationContext()));
        mLocationClient.registerNotifyLocationListener(new MyLocationListener());

        if(ContextCompat.checkSelfPermission(getActivity(), Manifest.permission.ACCESS_FINE_LOCATION)!= PackageManager.PERMISSION_GRANTED)
        {
            permissionList.add(Manifest.permission.ACCESS_FINE_LOCATION);
        }
        if(ContextCompat.checkSelfPermission(getActivity(),Manifest.permission.READ_PHONE_STATE)!= PackageManager.PERMISSION_GRANTED)
        {
            permissionList.add(Manifest.permission.READ_PHONE_STATE);
        }
        if (ContextCompat.checkSelfPermission(getActivity(), Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
            permissionList.add(Manifest.permission.WRITE_EXTERNAL_STORAGE);
        }
        if (!permissionList.isEmpty()) {
            String[] permissions = permissionList.toArray(new String[permissionList.size()]);
            ActivityCompat.requestPermissions(getActivity(), permissions, 1);
        }
        else {
            requestLocation();
        }

    }
    /*初始化函数，并启动位置客户端LocationClient*/
    private void requestLocation() {
        initLocation();
        mLocationClient.start();
    }

    /*初始化函数*/
    private void initLocation() {
        LocationClientOption option = new LocationClientOption();
        option.setScanSpan(5000);
        // option.setLocationMode(LocationClientOption.LocationMode.Device_Sensors);
        option.setIsNeedAddress(true);
        mLocationClient.setLocOption(option);
    }

//    private void initLocation() {
//        LocationClientOption option = new LocationClientOption();
//        option.setScanSpan(5000);
//        option.setLocationMode(LocationClientOption.LocationMode.Device_Sensors);
//        mLocationClient.setLocOption(option);
//    }


    /*只有同意打开相关权限才可以开启本程序*/
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        switch (requestCode) {
            case 1:
                if (grantResults.length > 0) {
                    for (int result : grantResults) {
                        if (result != PackageManager.PERMISSION_GRANTED) {
                            Toast.makeText(getActivity(), "必须同意所有权限才能使用本程序", Toast.LENGTH_SHORT).show();
                            getActivity().finish();
                            return;
                        }
                    }
                    requestLocation();
                } else {
                    Toast.makeText(getActivity(), "发生未知错误", Toast.LENGTH_SHORT).show();
                    getActivity().finish();
                }
                break;
            default:
        }
    }

    /*监听线程，获得当前的经纬度，并显示*/
    public class MyLocationListener implements BDLocationListener {
        @Override
        public void onReceiveLocation(final BDLocation location) {
            getActivity().runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    currentPosition = new StringBuilder();
//                    currentPosition.append("纬度:").append(location.getLatitude()).append("\n");
//                    currentPosition.append("经度:").append(location.getLongitude()).append("\n");
//                    currentPosition.append("国家:").append(location.getCountry()).append("\n");
                    currentPosition.append(location.getProvince());
                    currentPosition.append(location.getCity());
//                    currentPosition.append("区:").append(location.getDistrict()).append("\n");
//                    currentPosition.append("街道:").append(location.getStreet()).append("\n");
//                    currentPosition.append("定位方式：");
//                    if (location.getLocType() == BDLocation.TypeGpsLocation) {
//                        currentPosition.append("GPS");
//                    } else if (location.getLocType() == BDLocation.TypeNetWorkLocation) {
//                        currentPosition.append("网络");
//                    }
                    positionText.setText(currentPosition);
                }
            });
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        mLocationClient.stop();
    }
}
