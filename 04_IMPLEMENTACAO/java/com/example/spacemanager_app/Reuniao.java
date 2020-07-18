package com.example.spacemanager_app;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.graphics.Color;
import android.graphics.drawable.GradientDrawable;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import java.util.ArrayList;

public class Reuniao extends AppCompatActivity implements View.OnClickListener{

    private ConstraintLayout constraintLayout;
    RadioGroup pessoas;
    String sData, sHoraInicio, sHoraFim;
    ArrayList<String> lugares, lugaresOcupados;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.reuniao);

        Button lugar = findViewById(R.id.BObter);
        lugar.setOnClickListener(this);
        Button voltar = findViewById(R.id.BVoltar);
        voltar.setOnClickListener(this);
        pessoas = (RadioGroup) findViewById(R.id.reuniao);

        Intent intent = getIntent();
        sData = intent.getStringExtra("data");
        sHoraInicio = intent.getExtras().getString("horai");
        sHoraFim = intent.getStringExtra("horaf");
        lugares = intent.getExtras().getStringArrayList("lugares");
        lugaresOcupados = intent.getExtras().getStringArrayList("lugaresOcupados");
    }

@Override
public void onClick(View v) {
        switch (v.getId()) {
            case R.id.BObter:
                Intent intent_lugar = new Intent(Reuniao.this, MapaSalaReuniao.class);
                intent_lugar.putExtra("data", sData);
                intent_lugar.putExtra("horai", sHoraInicio);
                intent_lugar.putExtra("horaf", sHoraFim);
                intent_lugar.putExtra("tipo", "pessoas");
                intent_lugar.putExtra("lugaresOcupados", lugaresOcupados);
                intent_lugar.putExtra("lugares", lugares);
                intent_lugar.putExtra("nPessoas", ((RadioButton)findViewById(pessoas.getCheckedRadioButtonId())).getText().toString().substring(0,1));
                intent_lugar.putExtra("atual", 1);
                intent_lugar.putExtra("lugaresM", "");
                Reuniao.this.startActivity(intent_lugar);
                break;
            case R.id.BVoltar:
                Intent intent_voltar = new Intent(Reuniao.this, Reserva.class);
                Reuniao.this.startActivity(intent_voltar);
                break;
        }
    }
}
