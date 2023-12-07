package com.example.atividadebandeiras;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;

import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.os.Bundle;
import android.provider.MediaStore;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;

public class MainActivity extends AppCompatActivity {

    private TextView txvNome, txvRGM;
    private Button btnIniciar, btnSair;
    private String nome;
    private String rgm;
    private EditText edtNome, edtRGM;
    private ImageView imgFoto;

    private Bitmap foto;
    private Intent it;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

//        txvNome = findViewById(R.id.txvNome);
//        txvRGM = findViewById(R.id.txvRGM);

        btnIniciar = findViewById(R.id.btnIniciar);
        btnSair = findViewById(R.id.btnSair);
        btnIniciar.setEnabled(false);
        btnIniciar.setBackgroundColor(Color.parseColor("#DAD6D6"));

        edtNome = findViewById(R.id.edtNome);
        edtNome.addTextChangedListener(textWatcher);
        edtRGM = findViewById(R.id.edtRGM);
        edtRGM.addTextChangedListener(textWatcher);

        imgFoto = findViewById(R.id.imgFoto);
    }
    private TextWatcher textWatcher = new TextWatcher() {
        @Override
        public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

        }

        @Override
        public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            String text1 = edtNome.getText().toString().trim();
            String text2 = edtRGM.getText().toString().trim();
            btnIniciar.setEnabled(!text1.isEmpty() && !text2.isEmpty());
            if(!text1.isEmpty() && !text2.isEmpty()){
                btnIniciar.setBackgroundColor(Color.parseColor("#B9B8B8"));
            }
            else{
                btnIniciar.setBackgroundColor(Color.parseColor("#DAD6D6"));
            }
        }

        @Override
        public void afterTextChanged(Editable editable) {

        }
    };

    public void iniciarQuiz(View v){
        nome = edtNome.getText().toString();
        rgm = edtRGM.getText().toString();
        it = new Intent(this, Quiz.class);
        it.putExtra("p_Nome", nome);
        it.putExtra("p_RGM", rgm);
        if(foto != null){
            ByteArrayOutputStream stream = new ByteArrayOutputStream();
            foto.compress(Bitmap.CompressFormat.PNG, 100, stream);
            byte[] byteArray = stream.toByteArray();
            it.putExtra("p_foto", byteArray);
        }
        else{
            Toast.makeText(this, "Foto necessária", Toast.LENGTH_SHORT).show();
            return;
        }
        startActivity(it);
        finish();
    }

    public void sair(View v){
        finish();
    }

    @Override
    public void onBackPressed()
    {
        finish();

    }

    public void takePicture(View v){
        askCameraPermission();
    }

    private void askCameraPermission() {
        if(ContextCompat.checkSelfPermission(this, Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED){
            ActivityCompat.requestPermissions(this, new String[] {Manifest.permission.CAMERA}, 87);
        }
        else{
            openCamera();
        }
    }

    private void openCamera() {
        Intent camera = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        startActivityForResult(camera, 78);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if(requestCode == 87) {
            if(grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED){
                openCamera();
            }
            else{
                Toast.makeText(this, "Autorização para usar a camera é necessária", Toast.LENGTH_SHORT).show();
            }
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == 78){
            foto =(Bitmap) data.getExtras().get("data");
            imgFoto.setImageBitmap(foto);
        }
    }
}