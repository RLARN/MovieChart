package com.example.mangchimaster;

import androidx.appcompat.app.AppCompatActivity;

import android.app.FragmentManager;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

public class Display extends AppCompatActivity
        implements OnMapReadyCallback {

    private GoogleMap mMap;


//    지도가 나오는 자바 파일 이다.

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_main);
        setContentView(R.layout.display_map);

        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this); // 지도를 핸들링하기 위한 객채화..?
    }


    @Override
    public void onMapReady(final GoogleMap googleMap) {


        Intent intent = getIntent(); /*데이터 수신*/

        String name = intent.getExtras().getString("name");
        String menu = intent.getExtras().getString("menu");
        String rating = intent.getExtras().getString("rating");
        String wd = intent.getExtras().getString("wedo");
        String kd = intent.getExtras().getString("kyeongdo");



        double wdd = Double.parseDouble(wd); /*스트링 데이터를 지도에 입력하기 위한 형 변환 이것때문에 3시간 날아감*/
        double kdd = Double.parseDouble(kd);

        // 이 아래는 여러가지 형변환 시도,,,,, 결론적으로 위 방법이 맞았다.

        //double wdd = (int) Double.parseDouble(wd) + 1;  // 실수로 변환 후, 정수로 캐스팅, 그리고 1을 더하기
        //double kdd = (int) Double.parseDouble(kd) + 1;
        //long wdd = intent.getExtras().getLong("wedo");
        //long kdd = intent.getExtras().getLong("kyeongdo");
        //int wdd = Integer.parseInt(wd);
        //int kdd = Integer.parseInt(kd);
        //long wdd = Long.parseLong(wd);
        //long kdd = Long.parseLong(kd);


        //구글 맵 핸들링

        mMap = googleMap;
        //위치 지정
        LatLng SEOUL = new LatLng(wdd, kdd); //좌표
        MarkerOptions markerOptions = new MarkerOptions( );
        markerOptions.position(SEOUL);
        markerOptions.title(name);//마커 제목
        markerOptions.snippet("대표매뉴: " + menu + "   / 별점 " + rating + "개"); // 마커 설명
        mMap.addMarker(markerOptions);
        mMap.getUiSettings().setZoomControlsEnabled(true);
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(
                wdd, kdd), 15));// 카메라 좌표와 줌
        mMap.animateCamera(CameraUpdateFactory.zoomTo(10)); // 카메라 줌
    }

    public void back(View view){
        finish();
    } //돌아가기 버튼 눌렀을때

}
