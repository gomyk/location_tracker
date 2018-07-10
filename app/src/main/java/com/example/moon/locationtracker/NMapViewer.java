package com.example.moon.locationtracker;

import android.os.Bundle;
import android.util.Log;

import com.nhn.android.maps.NMapActivity;
import com.nhn.android.maps.NMapView;
import com.nhn.android.maps.overlay.NMapPathData;
import com.nhn.android.maps.overlay.NMapPathLineStyle;
import com.nhn.android.mapviewer.overlay.NMapOverlayManager;
import com.nhn.android.mapviewer.overlay.NMapPathDataOverlay;

import java.util.List;

public class NMapViewer extends NMapActivity {
    private DatabaseHelper db;

    private NMapView mMapView;// 지도 화면 View
    private final String CLIENT_ID = "Rfiu1FCN4mw9nHdVEqR4";// 애플리케이션 클라이언트 아이디 값

    private NMapOverlayManager mOverlayManager;
    private NMapViewerResourceProvider mMapViewerResourceProvider;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // create resource provider
        mMapViewerResourceProvider = new NMapViewerResourceProvider(this);
        // create overlay manager
        db = new DatabaseHelper(this);
        mMapView = new NMapView(this);
        mOverlayManager = new NMapOverlayManager(this, mMapView, mMapViewerResourceProvider);
        setContentView(mMapView);
        mMapView.setClientId(CLIENT_ID); // 클라이언트 아이디 값 설정
        mMapView.setClickable(true);
        mMapView.setEnabled(true);
        mMapView.setFocusable(true);
        mMapView.setFocusableInTouchMode(true);
        mMapView.requestFocus();
        List<dataFrame> listData = db.getAlldataFrames();

        NMapPathData pathData = new NMapPathData(listData.size());
        pathData.initPathData();
        for(int i=0;i<listData.size();i++){
            pathData.addPathPoint(listData.get(i).getLongitude(),listData.get(i).getLatitude(),NMapPathLineStyle.TYPE_SOLID);
        }
        pathData.endPathData();

        NMapPathDataOverlay pathDataOverlay = mOverlayManager.createPathDataOverlay(pathData);
        pathDataOverlay.showAllPathData(0);
    }
}