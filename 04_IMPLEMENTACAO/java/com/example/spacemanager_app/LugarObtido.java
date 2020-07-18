package com.example.spacemanager_app;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.graphics.Color;
import android.graphics.drawable.GradientDrawable;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.Random;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

public class LugarObtido extends AppCompatActivity implements View.OnClickListener{

    private ConstraintLayout constraintLayout;
    String sData, sHoraInicio, sHoraFim, sSala, sLugar, tipo;
    DatabaseReference reservas;
    FirebaseDatabase firebaseDatabase;
    ArrayList<String> lugares;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.lugar_obtido);

        Button voltar = findViewById(R.id.BVoltar);
        voltar.setOnClickListener(this);
        firebaseDatabase = FirebaseDatabase.getInstance();

        Intent intent = getIntent();
        sData = intent.getStringExtra("data");
        sHoraInicio = intent.getExtras().getString("horai");
        sHoraFim = intent.getStringExtra("horaf");
        sLugar = intent.getStringExtra("lugar");
        lugares = intent.getStringArrayListExtra("lugares");
        sSala = intent.getStringExtra("sala");
        tipo = intent.getStringExtra("tipo");

        TextView data = findViewById(R.id.TVdata);
        data.setText(data.getText() + " " + sData);
        TextView horai = findViewById(R.id.TVhorai);
        horai.setText(horai.getText() + " " + sHoraInicio);
        TextView horaf = findViewById(R.id.TVhoraf);
        horaf.setText(horaf.getText() + " " + sHoraFim);
        TextView sala = findViewById(R.id.TVsala);
        sala.setText(sala.getText() + " " + sSala);
        TextView lugar = findViewById(R.id.TVlugar);

        if (tipo.equals("aleatorio")){
            reserva(obterLugar());
            lugar.setText(lugar.getText() + " " + sLugar);
        }
        else if (tipo.equals("opcao")){
            reserva(sLugar);
            lugar.setText(lugar.getText() + " " + sLugar);
        }
        else if (tipo.equals("mapa")){
            lugar.setText(lugar.getText() + " " + sLugar);
        }
        else if(tipo.equals("pessoas")){
            String sLugares = intent.getStringExtra("lugaresM");
            lugar.setText(lugar.getText() + " " + sLugares);
        }
    }

    public String obterLugar(){
        Random rand = new Random();
        String lugar = "";
        int rand2 = rand.nextInt(12);
        lugar = (rand2+1)+"";
        for (int i = 0; i < lugares.size(); i++){
            if (lugares.get(i).equals(lugar)){
                lugar = "";
            }
        }
        return lugar;
    }

    public void reserva(String nlugar){
        if (!nlugar.equals("")) {
            sSala = "1";
            sLugar = nlugar;
            reservas = firebaseDatabase.getReference("reservas");
            String s = sHoraInicio + sData + sHoraFim;
            String md5 = "";
            try {
                MessageDigest md = MessageDigest.getInstance("MD5");
                md.update(s.getBytes(), 0, s.length());
                md5 = new BigInteger(1, md.digest()).toString(16);
            } catch (NoSuchAlgorithmException e) {
                System.err.println("Erro ao gerar o código MD5");
            }

            DatabaseReference id = reservas.child(md5);
            DatabaseReference sala = id.child("sala");
            sala.setValue(1);
            DatabaseReference lugar = id.child("lugar");
            lugar.setValue(nlugar);
            DatabaseReference utilizador = id.child("utilizador");
            utilizador.setValue(FirebaseAuth.getInstance().getCurrentUser().getEmail());
            DatabaseReference data = id.child("data");
            data.setValue(sData);
            DatabaseReference horaI = id.child("horaI");
            horaI.setValue(sHoraInicio);
            DatabaseReference horaF = id.child("horaF");
            horaF.setValue(sHoraFim);
        }
        else{
            Toast.makeText(getApplicationContext(), R.string.nhaLuagres, Toast.LENGTH_LONG).show();
            Intent intent_voltar = new Intent(LugarObtido.this, Reserva.class);
            LugarObtido.this.startActivity(intent_voltar);
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.BVoltar:
                Intent intent_voltar = new Intent(LugarObtido.this, Reserva.class);
                LugarObtido.this.startActivity(intent_voltar);
                break;
        }
    }
}
