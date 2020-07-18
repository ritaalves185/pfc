package com.example.spacemanager_app;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.graphics.Color;
import android.graphics.drawable.GradientDrawable;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

public class Reserva extends AppCompatActivity implements View.OnClickListener{

    private ConstraintLayout constraintLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.reserva);

        Button qualquer = findViewById(R.id.BQualquer);
        qualquer.setOnClickListener(this);
        Button lugar = findViewById(R.id.BLugar);
        lugar.setOnClickListener(this);
        Button pessoas = findViewById(R.id.BPessoas);
        pessoas.setOnClickListener(this);
        Button opcao = findViewById(R.id.BOpcao);
        opcao.setOnClickListener(this);
        Button voltar = findViewById(R.id.BVoltar);
        voltar.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
           case R.id.BQualquer:
                Intent intent_qualquer = new Intent(Reserva.this, Horario.class);
                intent_qualquer.putExtra("tipo", "aleatorio");
                Reserva.this.startActivity(intent_qualquer);
                break;

            case R.id.BLugar:
                Intent intent_lugar = new Intent(Reserva.this, Horario.class);
                intent_lugar.putExtra("tipo", "mapa");
                Reserva.this.startActivity(intent_lugar);
                break;

            case R.id.BPessoas:
                Intent intent_pessoas = new Intent(Reserva.this, Horario.class);
                intent_pessoas.putExtra("tipo", "pessoas");
                Reserva.this.startActivity(intent_pessoas);
                break;

            case R.id.BOpcao:
                Intent intent_opcao = new Intent(Reserva.this, Horario.class);
                intent_opcao.putExtra("tipo", "opcao");
                Reserva.this.startActivity(intent_opcao);
                break;

            case R.id.BVoltar:
                Intent intent_voltar = new Intent(Reserva.this, PaginaInicial.class);
                Reserva.this.startActivity(intent_voltar);
                break;
        }
    }
}
