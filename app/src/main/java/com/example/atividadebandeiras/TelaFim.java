package com.example.atividadebandeiras;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.Image;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import java.io.ByteArrayOutputStream;

public class TelaFim extends AppCompatActivity {

    private String nomeFinal;
    private int scoreFinal;
    private TextView txtScore, txtNome, txtRGM;

    private ImageView imgFoto;
    private Bitmap foto;
    private String rgm;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tela_fim);
        Intent it = getIntent();
        nomeFinal = it.getStringExtra("p_Nome");
        rgm = it.getStringExtra("p_RGM");
        scoreFinal = it.getIntExtra("p_Score", 0);
        foto = BitmapFactory.decodeByteArray(it.getByteArrayExtra("p_foto"), 0, it.getByteArrayExtra("p_foto").length);
        imgFoto = findViewById(R.id.imageView2);
        imgFoto.setImageBitmap(foto);
        txtNome = findViewById(R.id.txtNome);
        txtNome.setText("Nome: " + nomeFinal);
        txtScore = findViewById(R.id.txtScore);
        txtScore.setText(String.valueOf(scoreFinal));
        txtRGM = findViewById(R.id.txtRGM);
        txtRGM.setText("RGM: " + rgm);
    }

    public void voltarTelaPrincipal(View v){
        Intent it = new Intent(this, MainActivity.class);
        startActivity(it);
        finish();
    }

    public void voltarAoQuiz(View v){
        Intent it = new Intent(this, Quiz.class);
        it.putExtra("p_Nome", nomeFinal);
        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        foto.compress(Bitmap.CompressFormat.PNG, 100, stream);
        byte[] byteArray = stream.toByteArray();
        it.putExtra("p_foto", byteArray);
        it.putExtra("p_RGM", rgm);
        startActivity(it);
        finish();
    }
    @Override
    public void onBackPressed()
    {
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
        finish();
    }


}