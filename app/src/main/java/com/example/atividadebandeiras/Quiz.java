package com.example.atividadebandeiras;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import java.io.ByteArrayOutputStream;

public class Quiz extends AppCompatActivity {

    private Button btnResponder;
    private RadioGroup rdgAnswers;
    private ImageView imgBandeira;
    private RadioButton rdbAns1, rdbAns2, rdbAns3, rdbAns4, resposta;
    private int score = 0;
    private int currentIndex = 0;
    private String nome;
    private String rgm;

    private Bitmap foto;
    private String[][] choices = {
            {"Brasil", "Grécia", "Suiça", "Africa do Sul"},
            {"Colombia", "Austrália","Argentina", "Croácia"},
            {"Tunisia", "Costa Rica", "China", "França"},
            {"Estados Unidos", "Nigéria", "Suécia", "Equador"},
            {"Polonia", "Cazaquistão", "Chile", "México"},
            {"Marrocos", "Israel", "Espanha", "Butão"},
            {"Indonésia", "Coréia do Sul", "Turquia", "Hungria"},
            {"Bulgaria", "Grécia", "Tunisia", "Romenia"},
            {"Argélia", "Angola", "Zâmbia", "Lesoto"},
            {"Taiwan", "Malásia", "Japão", "Haiti"}
    };

    private String[] answers = {
            "Suiça",
            "Argentina",
            "Tunisia",
            "Equador",
            "Chile",
            "Marrocos",
            "Indonésia",
            "Grécia",
            "Zâmbia",
            "Malásia"
    };

    private int[] images = {
            R.drawable.suica,
            R.drawable.argentina,
            R.drawable.tunisia,
            R.drawable.equador,
            R.drawable.chile,
            R.drawable.marrocos,
            R.drawable.indonesia,
            R.drawable.grecia,
            R.drawable.zambia,
            R.drawable.malasia
    };
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quiz);


        btnResponder = findViewById(R.id.btnResponder);
        btnResponder.setEnabled(false);

        rdgAnswers = findViewById(R.id.rdgAnswers);

        rdbAns1 = findViewById(R.id.rdbAns1);
        rdbAns2 = findViewById(R.id.rdbAns2);
        rdbAns3 = findViewById(R.id.rdbAns3);
        rdbAns4 = findViewById(R.id.rdbAns4);
        imgBandeira = findViewById(R.id.imgBandeira);;

        rdgAnswers.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int i) {
                btnResponder.setEnabled(true);
            }
        });
        loadQuestion();
    }
    public void responder(View v){

        int checked = rdgAnswers.getCheckedRadioButtonId();
        resposta = findViewById(checked);
        if(resposta.getText().toString().equals(answers[currentIndex])){
            score++;
        }
        rdgAnswers.clearCheck();
        currentIndex++;
        loadQuestion();
        btnResponder.setEnabled(false);
    }

    public void loadQuestion(){
        if(currentIndex == 10){
            Intent it = getIntent();
            nome = it.getStringExtra("p_Nome");
            rgm = it.getStringExtra("p_RGM");
            foto = BitmapFactory.decodeByteArray(it.getByteArrayExtra("p_foto"), 0, it.getByteArrayExtra("p_foto").length);
            endScreen();
            return;
        }
        rdbAns1.setText(choices[currentIndex][0]);
        rdbAns2.setText(choices[currentIndex][1]);
        rdbAns3.setText(choices[currentIndex][2]);
        rdbAns4.setText(choices[currentIndex][3]);
        imgBandeira.setImageResource(images[currentIndex]);
    }

    public void endScreen(){
        Intent it = new Intent(this, TelaFim.class);
        it.putExtra("p_Nome", nome);
        it.putExtra("p_Score", score);
        it.putExtra("p_RGM", rgm);
        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        foto.compress(Bitmap.CompressFormat.PNG, 100, stream);
        byte[] byteArray = stream.toByteArray();
        it.putExtra("p_foto", byteArray);
        startActivity(it);
        finish();
    }

    @Override
    public void onBackPressed()
    {
        Intent intent = new Intent(this,MainActivity.class);
        startActivity(intent);
        finish();
    }
}