package com.example.praktikum03;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.SeekBar;
import android.widget.TextView;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.SeekBar;
import android.widget.TextView;

import android.widget.Toast;
import android.util.Log;
public class AddActivity extends AppCompatActivity implements SeekBar.OnSeekBarChangeListener{

    EditText title_input, author_input;
    Button add_button;
    private SeekBar jumlah;
    private TextView total_input;
    private RadioButton fiksi, non_fiksi;
    private String jenis_buku_value_input;
    private CheckBox komik,romance,horor,humor,scifi,sains,sejarah;
    EditText publisher_input, year_input;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add);

        title_input = findViewById(R.id.title_input);
        author_input = findViewById(R.id.author_input);
        total_input = findViewById(R.id.total_input);

        publisher_input = findViewById(R.id.penerbit);
        year_input = findViewById(R.id.tahun);
        SeekBar jumlah = findViewById(R.id.jumlah);
        RadioGroup jenis_buku =  findViewById(R.id.jenis_buku);
        total_input = findViewById(R.id.total_input);
        komik = (CheckBox)findViewById(R.id.komik);
        romance = (CheckBox)findViewById(R.id.romance);
        horor = (CheckBox)findViewById(R.id.horor);
        humor = (CheckBox)findViewById(R.id.humor);
        scifi = (CheckBox)findViewById(R.id.scifi);
        sains = (CheckBox)findViewById(R.id.sains);
        sejarah = (CheckBox)findViewById(R.id.sejarah);

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
        add_button = findViewById(R.id.add_button);
        add_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String penerbit = ((EditText) findViewById(R.id.penerbit)).getText().toString();
                int jumlah_hasil= jumlah.getProgress();
                fiksi = findViewById(R.id.fiksi);
                non_fiksi = findViewById(R.id.non_fiksi);

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

//
                String finalGenre = genre;
                AlertDialog.Builder builder = new AlertDialog.Builder(AddActivity.this);
                MyDatabaseHelper myDB = new MyDatabaseHelper(AddActivity.this);
                builder.setPositiveButton("YA", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        long afterinsert = myDB.addBook(title_input.getText().toString().trim(),
                                author_input.getText().toString().trim(),
                                Integer.valueOf(jumlah_hasil),
                                publisher_input.getText().toString().trim(),
                                jenis_buku_value_input,
                                finalGenre,
                                Integer.valueOf(year_input.getText().toString())
                        );
                        String after_insert = Long.toString(afterinsert);
                        Log.d("test", "insert berhasil"+afterinsert);
                        Intent intent = new Intent(AddActivity.this,LihatActivity.class);
                        intent.putExtra("idxxx", after_insert);
                        intent.putExtra("judul", title_input.getText().toString().trim());
                        intent.putExtra("penulis", author_input.getText().toString().trim());
                        intent.putExtra("penerbit", publisher_input.getText().toString().trim());
                        intent.putExtra("tahun", year_input.getText().toString());
                        String jumlah_hsl = String.valueOf(jumlah_hasil);
                        intent.putExtra("jumlah", jumlah_hsl);
                        intent.putExtra("jenis_buku", jenis_buku_value_input);
                        intent.putExtra("genre", finalGenre);
                        Log.d("test","test"+after_insert);
                        startActivity(intent);
                    }
                });
                builder.setNegativeButton("Tidak", new DialogInterface.OnClickListener() {

                            public void onClick(DialogInterface dialog, int id) {
                                dialog.cancel();
                            }
                        });
                builder.setMessage("Apakah Anda Ingin Menyimpan?"+"\n"+"\n"+"\n"+"Judul Buku "+title_input.getText().toString().trim()+" "+"Penulis "+author_input.getText().toString().trim()+" "+"Penerbit "+publisher_input.getText().toString().trim()+" "+"Tahun "+year_input.getText().toString()+" "+"Jumlah Buku "+jumlah_hasil+" "+"Jenis Buku "+jenis_buku_value_input+" "+"Genre "+finalGenre).create().show();
//aman


            }
        });
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
