package com.example.menupan.Adapter.Frame;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.menupan.R;
import com.naver.maps.geometry.LatLng;
import com.naver.maps.map.CameraAnimation;
import com.naver.maps.map.CameraPosition;
import com.naver.maps.map.CameraUpdate;
import com.naver.maps.map.MapView;
import com.naver.maps.map.NaverMap;
import com.naver.maps.map.OnMapReadyCallback;
import com.naver.maps.map.overlay.Marker;

public class Frame_Map extends Fragment implements OnMapReadyCallback {

    private View view;
    private MapView mapView;

    public static Frame_Map newInstance(){
        Frame_Map frame_map = new Frame_Map();
        return frame_map;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState){
        view = inflater.inflate(R.layout.frame_frag_map, container, false);
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState){
        super.onViewCreated(view, savedInstanceState);
        mapView=view.findViewById(R.id.frame_frag_map);
        if(mapView!=null){
            mapView.onCreate(null);
            mapView.onResume();
            mapView.getMapAsync(this);
        }
    }

    @Override
    public void onMapReady(@NonNull NaverMap naverMap) {
        goToLocation(naverMap, 36.632599, 127.458799);//나중에 이 부분은 DB에 들어있는 값으로 넣어줘야 함
        Marker marker = new Marker();
        marker.setPosition(new LatLng(36.632599,127.458799));
        marker.setMap(naverMap);
    }

    private void goToLocation(NaverMap naverMap, double lat, double lng){
        CameraUpdate cameraUpdate = CameraUpdate.scrollTo(new LatLng(lat, lng)).animate(CameraAnimation.Linear);
        naverMap.moveCamera(cameraUpdate);
        CameraPosition cameraPosition = new CameraPosition(new LatLng(36.632599, 127.458799), 17);
        naverMap.setCameraPosition(cameraPosition);
    }
}
