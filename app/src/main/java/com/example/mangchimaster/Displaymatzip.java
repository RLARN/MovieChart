package com.example.mangchimaster;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.os.Parcelable;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class Displaymatzip extends AppCompatActivity {

    private DBHelper mydb;
    TextView name;
    TextView gigan;
    TextView thname;
    TextView location;
    TextView wedo;
    TextView kyeongdo;
    TextView rating;
    int id = 0;

    //서울 좌표 37.56 // 126.97
    //대림 좌표 37.40 // 126.93

    //무비 데이터베이스의 코드를 그대로 사용
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dispaly_matzip);
        name = (TextView) findViewById(R.id.name);
        wedo = (TextView) findViewById(R.id.wedo);
        kyeongdo = (TextView) findViewById(R.id.kyeongdo);
        gigan = (TextView) findViewById(R.id.gigan);
        thname = (TextView) findViewById(R.id.thname);
        location = (TextView) findViewById(R.id.location);


        rating = (TextView) findViewById(R.id.rating);

        mydb = new DBHelper(this);

        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            int Value = extras.getInt("id");
            if (Value > 0) {
                Cursor rs = mydb.getData(Value);
                id = Value;
                rs.moveToFirst(); // 아래 빨간줄뜨는데 왜 뜨는지 모르겠다, 코드역시 정상 작동중...
                String n = rs.getString(rs.getColumnIndex(DBHelper.MOVIE_COLUMN_NAME));
                String g = rs.getString(rs.getColumnIndex(DBHelper.MOVIE_COLUMN_GIGAN));

                String thn = rs.getString(rs.getColumnIndex(DBHelper.MOVIE_COLUMN_THNAME));
                String lo = rs.getString(rs.getColumnIndex(DBHelper.MOVIE_COLUMN_LOCATION));

                String w = rs.getString(rs.getColumnIndex(DBHelper.MOVIE_COLUMN_WEDO));
                String k = rs.getString(rs.getColumnIndex(DBHelper.MOVIE_COLUMN_KYEONGDO));
                String r = rs.getString(rs.getColumnIndex(DBHelper.MOVIE_COLUMN_RATING));
                if (!rs.isClosed()) {
                    rs.close();
                }
                Button b = (Button) findViewById(R.id.button1);

                name.setText((CharSequence) n);
                gigan.setText((CharSequence) g);
                thname.setText((CharSequence) thn);
                location.setText((CharSequence) lo);
                wedo.setText((CharSequence) w);
                kyeongdo.setText((CharSequence) k);
                rating.setText((CharSequence) r);
            }
        }
    }


    /*데이터 베이스 헨들링은 기존 코드를 그대로 이용*/
    public void insert(View view) {
        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            int Value = extras.getInt("id");
            if (Value > 0) {
                if (mydb.updateMovie(id, name.getText().toString(), gigan.getText().toString(), thname.getText().toString(), location.getText().toString(), wedo.getText().toString(), kyeongdo.getText().toString(), rating.getText().toString())) {
                    Toast.makeText(getApplicationContext(), "수정되었음", Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                    startActivity(intent);
                } else {
                    Toast.makeText(getApplicationContext(), "수정되지 않았음", Toast.LENGTH_SHORT).show();
                }
            } else {
                if (mydb.insertMovie(name.getText().toString(), gigan.getText().toString(), thname.getText().toString(), location.getText().toString(), wedo.getText().toString(), kyeongdo.getText().toString(), rating.getText().toString())) {
                    Toast.makeText(getApplicationContext(), "추가되었음", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(getApplicationContext(), "추가되지 않았음", Toast.LENGTH_SHORT).show();
                }
                finish();
            }
        }








    }

    public void delete(View view) {
        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            int Value = extras.getInt("id");
            if (Value > 0) {
                mydb.deleteMovie(id);
                Toast.makeText(getApplicationContext(), "삭제되었음", Toast.LENGTH_SHORT).show();
                finish();
            } else {
                Toast.makeText(getApplicationContext(), "삭제되지 않았음", Toast.LENGTH_SHORT).show();
            }
        }
    }

    public void edit(View view) {
        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            int value = extras.getInt("id");
            if (value > 0) {
                if (mydb.updateMovie(id, name.getText().toString(), gigan.getText().toString(), thname.getText().toString(), location.getText().toString(), wedo.getText().toString(), kyeongdo.getText().toString(), rating.getText().toString())) {
                    Toast.makeText(getApplicationContext(), "수정되었음", Toast.LENGTH_SHORT).show();
                    finish();
                } else {
                    Toast.makeText(getApplicationContext(), "수정되지 않았음", Toast.LENGTH_SHORT).show();
                }
            }
        }
    }


    //맵 버튼을 눌렀을때 지도를 보여준다, onClick으로 display_matzip.xml에 맵핑되어있다.
    public void map(View view){
        Intent intent = new Intent(getApplicationContext(), Display.class);

        String mangchi = name.getText().toString();/*지도로 데이터 송신 하기 위해 스트링으로 처리 하였다.*/
        String kimchi = gigan.getText().toString();

        String thn = thname.getText().toString();
        String lo = location.getText().toString();

        String wd = wedo.getText().toString();
        String kd = kyeongdo.getText().toString();
        String rt = rating.getText().toString();


        //long wd = Long.parseLong(wedo.getText().toString());
        //long kd = Long.parseLong(kyeongdo.getText().toString());
        //long wdd = Long.parseLong(wd);
        //long kdd = Long.parseLong(kd);


        intent.putExtra("name", mangchi); /*지도로 데이터 송신 부분*/
        intent.putExtra("thname", thn);
        intent.putExtra("location", lo);
        intent.putExtra("gigan", kimchi);
        intent.putExtra("wedo", wd);
        intent.putExtra("kyeongdo", kd);
        intent.putExtra("rating", rt);



        startActivity(intent); /*스타뜨 인텐트, 지도 보여준다*/

    }
}