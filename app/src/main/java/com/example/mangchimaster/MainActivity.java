package com.example.mangchimaster;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.content.Intent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    private ListView myListView;
    DBHelper mydb;
    ArrayAdapter mAdapter;


    // info . 데이터 베이스에 맛집 정보와 위치정보를 추가하고 지도 버튼을 누르면 해당 좌표로 이동, 간단한 정보를 볼수있는 프로젝트


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mydb = new DBHelper(this);
        ArrayList array_list = mydb.getAllMovie();

        mAdapter =
                new ArrayAdapter(this, android.R.layout.simple_list_item_1, array_list);

        myListView = (ListView) findViewById(R.id.listView1);
        myListView.setAdapter(mAdapter);
        myListView.setOnItemClickListener(new OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position,
                                    long arg4) {
                String item = (String) ((ListView) parent).getItemAtPosition(position);
                String[] strArray = item.split("" + "");
                int id = Integer.parseInt(strArray[0]);
                Bundle dataBundle = new Bundle();
                dataBundle.putInt("id", id);
                Intent intent = new Intent(getApplicationContext(), Displaymatzip.class);
                intent.putExtras(dataBundle);
                startActivity(intent);
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        mAdapter.clear();
        mAdapter.addAll(mydb.getAllMovie());
        mAdapter.notifyDataSetChanged();
    }

    public void onClick(View target) {
        Bundle bundle = new Bundle();
        bundle.putInt("id", 0);
        Intent intent = new Intent(getApplicationContext(), Displaymatzip.class);
        intent.putExtras(bundle);
        startActivity(intent);
    }
}