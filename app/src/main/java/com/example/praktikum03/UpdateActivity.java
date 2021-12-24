package com.example.praktikum03;


import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;
import android.database.Cursor;
import android.widget.CheckBox;

import android.widget.RadioButton;
import android.widget.RadioGroup;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class UpdateActivity extends AppCompatActivity implements SeekBar.OnSeekBarChangeListener{
    EditText title_input, author_input, publisher_input;
    TextView total_input;
    Button update_button, delete_button;
    SeekBar jumlah;

    String id, title, author, total,publisher,  genre, type;
    String year;
    private RadioButton fiksi, non_fiksi;
    private String jenis_buku_value_input;
    private CheckBox komik,romance,horor,humor,scifi,sains,sejarah;
    EditText year_input;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update);

        title_input = findViewById(R.id.title_input2);
        author_input = findViewById(R.id.author_input2);
        total_input = findViewById(R.id.total_input2);

        publisher_input = findViewById(R.id.penerbit2);

        year_input = findViewById(R.id.tahun2);

        RadioGroup jenis_buku =  findViewById(R.id.jenis_buku2);
        fiksi = findViewById(R.id.fiksi2);
        non_fiksi = findViewById(R.id.non_fiksi2);

        komik = (CheckBox)findViewById(R.id.komik2);
        romance = (CheckBox)findViewById(R.id.romance2);
        horor = (CheckBox)findViewById(R.id.horor2);
        humor = (CheckBox)findViewById(R.id.humor2);
        scifi = (CheckBox)findViewById(R.id.scifi2);
        sains = (CheckBox)findViewById(R.id.sains2);
        sejarah = (CheckBox)findViewById(R.id.sejarah2);


        update_button = findViewById(R.id.update_button);
        delete_button = findViewById(R.id.delete_button);

        //First we call this
        getAndSetIntentData();

        //Set actionbar title after getAndSetIntentData method
        ActionBar ab = getSupportActionBar();
        if (ab != null) {
            ab.setTitle(title);
        }
        SeekBar jumlah = findViewById(R.id.jumlah2);
        total_input = findViewById(R.id.total_input2);
        Log.d("te","test"+total_input.getText().toString());
        jumlah.setProgress(Integer.parseInt(total_input.getText().toString()));
        jumlah.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar jumlah, int i, boolean b) {
                total_input.setText(String.valueOf(i));
            }

            @Override
            public void onStartTrackingTouch(SeekBar jumlah) {
            }

            @Override
            public void onStopTrackingTouch(SeekBar jumlah) {
            }
        });


        update_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //And only then we call this
                MyDatabaseHelper myDB = new MyDatabaseHelper(UpdateActivity.this);
                title = title_input.getText().toString().trim();
                author = author_input.getText().toString().trim();
                total = total_input.getText().toString().trim();
                publisher = publisher_input.getText().toString().trim();

                year = year_input.getText().toString().trim();

                if(fiksi.isChecked()){
                    jenis_buku_value_input = "fiksi";
                }
                else if(non_fiksi.isChecked()){
                    jenis_buku_value_input = "non fiksi";
                }
                else {
                    jenis_buku_value_input = "Belum Dipilih";
                }
                String genre=" ";
                if (!komik.isChecked() && !romance.isChecked() && !horor.isChecked() && !humor.isChecked() && !scifi.isChecked() && !sains.isChecked() && !sejarah.isChecked()){
                    genre = "Belum Dipilih";
                }

                if (komik.isChecked()){
                    genre += "Komik, ";
                } if(romance.isChecked()){
                    genre += "Romance, ";
                } if(horor.isChecked()){
                    genre += "Horor, ";

                } if(humor.isChecked()){
                    genre += "Komedi/Humor, ";

                } if(scifi.isChecked()){
                    genre += "Sci-Fi, ";

                } if(sains.isChecked()){
                    genre += "Sains, ";

                } if(sejarah.isChecked()){
                    genre += "Sejarah, ";

                }
                int jumlah_hasil= jumlah.getProgress();
                String finalGenre = genre;
                myDB.updateData(id, title, author, String.valueOf(jumlah_hasil),publisher, jenis_buku_value_input, finalGenre,Integer.parseInt(year));
            }
        });
        delete_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                confirmDialog();
            }
        });

    }

    void getAndSetIntentData(){
        if(getIntent().hasExtra("id"))
                {
            //Getting Data from Intent
            id = getIntent().getStringExtra("id");


            MyDatabaseHelper myDB = new MyDatabaseHelper(UpdateActivity.this);
            Cursor cursor = myDB.searchData(id);
            if (cursor != null) {

                cursor.moveToFirst();

                Log.d("test","masuk sini cursor tidak null cursor close aman"+cursor.getString(0));
                publisher_input.setText(cursor.getString(3));
                title_input.setText(cursor.getString(1));
                author_input.setText(cursor.getString(2));
                total_input.setText(cursor.getString(7));
                year_input.setText(cursor.getString(4));
                switch (cursor.getString(5)){
                    case "fiksi":
                        fiksi.setChecked(true);
                        break;
                    case "non fiksi":
                        non_fiksi.setChecked(true);
                        break;
                }

                if (cursor.getString(6) != null) {

                    String genres = cursor.getString(6);

                    if (genres.contains("Komik")) {
                        komik.setChecked(true);
                    }
                    if (genres.contains("Romance")) {
                        romance.setChecked(true);
                    }
                    if (genres.contains("Sains")) {
                        sains.setChecked(true);
                    }
                    if (genres.contains("Sejarah")) {
                        sejarah.setChecked(true);
                    }
                    if (genres.contains("Komedi/Humor")) {
                        humor.setChecked(true);
                    }
                    if (genres.contains("Sci-Fi")) {
                        scifi.setChecked(true);
                    }
                    if (genres.contains("Horor")) {
                        horor.setChecked(true);
                    }
                }

            }

        }else{
            Toast.makeText(this, "No data.", Toast.LENGTH_SHORT).show();
        }
    }

    void confirmDialog(){
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Delete " + title + " ?");
        builder.setMessage("Are you sure you want to delete " + title + " ?");
        builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                MyDatabaseHelper myDB = new MyDatabaseHelper(UpdateActivity.this);
                myDB.deleteOneRow(id);
                finish();
            }
        });
        builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {

            }
        });
        builder.create().show();
    }

    @Override
    public void onProgressChanged(SeekBar seekBar, int i, boolean b) {

    }

    @Override
    public void onStartTrackingTouch(SeekBar seekBar) {

    }

    @Override
    public void onStopTrackingTouch(SeekBar seekBar) {

    }
}
