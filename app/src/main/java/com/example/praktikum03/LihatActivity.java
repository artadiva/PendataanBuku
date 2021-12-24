package com.example.praktikum03;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import  android.widget.Button;
public class LihatActivity extends AppCompatActivity {
    String  judul,penulis,penerbit,tahun,jumlah,jenis_buku,genre;
    String id;
    private TextView lihat_judul;
    private TextView lihat_penulis;
    private TextView lihat_penerbit;
    private TextView lihat_tahun;
    private TextView lihat_jumlah;
    private TextView lihat_jenis_buku;
    private TextView lihat_genre;
    Button edit_button, home_button ;


    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lihat);
        lihat_judul = (TextView) findViewById(R.id.lihat_judul);
        lihat_penulis = (TextView) findViewById(R.id.lihat_penulis);
        lihat_penerbit = (TextView) findViewById(R.id.lihat_penerbit);
        lihat_tahun = (TextView) findViewById(R.id.lihat_tahun);
        lihat_jumlah = (TextView) findViewById(R.id.lihat_jumlah);
        lihat_jenis_buku = (TextView) findViewById(R.id.lihat_jenis_buku);
        lihat_genre = (TextView) findViewById(R.id.lihat_genre);
        Intent intent = getIntent();
        id = intent.getStringExtra("idxxx");
        judul = intent.getStringExtra("judul");
        penulis = intent.getStringExtra("penulis");
        penerbit = intent.getStringExtra("penerbit");
        tahun = intent.getStringExtra("tahun");
        jumlah = intent.getStringExtra("jumlah");
        jenis_buku = intent.getStringExtra("jenis_buku");
        genre = intent.getStringExtra("genre");
        lihat_judul.setText(judul);
        lihat_penulis.setText(penulis);
        lihat_penerbit.setText(penerbit);
        lihat_tahun.setText(tahun);
        lihat_jumlah.setText(jumlah);
        lihat_jenis_buku.setText(jenis_buku);
        lihat_genre.setText(genre);
        home_button = findViewById(R.id.home_lihat);
        edit_button = findViewById(R.id.edit);
        edit_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(LihatActivity.this, UpdateActivity.class);

                intent.putExtra("id",id);
                Log.d("test","test"+id);
                startActivity(intent);
            }
        });

        home_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view){
                Intent intent = new Intent( LihatActivity.this, MainActivity.class);
                startActivity(intent);
            }
        });

    }
}