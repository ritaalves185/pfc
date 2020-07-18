package com.example.spacemanager_app;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.graphics.Color;
import android.graphics.drawable.GradientDrawable;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.Map;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

public class MapaSalaReuniao extends AppCompatActivity implements View.OnClickListener{

    private ConstraintLayout constraintLayout;
    Button lugar1,lugar2,lugar3,lugar4,lugar5,lugar6,lugar7,lugar8,lugar9,lugar10,lugar11,lugar12;
    FirebaseDatabase firebaseDatabase;
    DatabaseReference ref, reservas, lugaresDB;
    String sala = "1";
    String sData, sHoraInicio, sHoraFim, lugaresM;
    int lugar = 0;
    ArrayList<String> lugares, lugaresOcupados;
    String total;
    int atual;
    Button reservar;
    boolean naoPrimeiro;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.mapa_reuniao);
        constraintLayout = findViewById(R.id.fundo);

        firebaseDatabase = FirebaseDatabase.getInstance();
        ref = firebaseDatabase.getReference();
        lugaresDB = firebaseDatabase.getReference("lugares");
        reservas = firebaseDatabase.getReference("reservas");

        Intent intent = getIntent();
        sData = intent.getStringExtra("data");
        sHoraInicio = intent.getExtras().getString("horai");
        sHoraFim = intent.getStringExtra("horaf");
        lugares = intent.getExtras().getStringArrayList("lugares");
        lugaresOcupados = intent.getExtras().getStringArrayList("lugaresOcupados");
        total = intent.getStringExtra("nPessoas");
        atual = intent.getIntExtra("atual", 1);
        lugaresM = intent.getStringExtra("lugaresM");
        naoPrimeiro = intent.getBooleanExtra("permissao", false);

        lugar1 = findViewById(R.id.button);
        lugar1.setOnClickListener(this);
        lugar2 = findViewById(R.id.button2);
        lugar2.setOnClickListener(this);
        lugar3 = findViewById(R.id.button3);
        lugar3.setOnClickListener(this);
        lugar4 = findViewById(R.id.button4);
        lugar4.setOnClickListener(this);
        lugar5 = findViewById(R.id.button5);
        lugar5.setOnClickListener(this);
        lugar6 = findViewById(R.id.button6);
        lugar6.setOnClickListener(this);
        lugar7 = findViewById(R.id.button7);
        lugar7.setOnClickListener(this);
        lugar8 = findViewById(R.id.button8);
        lugar8.setOnClickListener(this);
        lugar9 = findViewById(R.id.button9);
        lugar9.setOnClickListener(this);
        lugar10 = findViewById(R.id.button10);
        lugar10.setOnClickListener(this);
        lugar11 = findViewById(R.id.button11);
        lugar11.setOnClickListener(this);
        lugar12 = findViewById(R.id.button12);
        lugar12.setOnClickListener(this);

        reservar = findViewById(R.id.BReservar);
        reservar.setEnabled(false);
        reservar.setBackgroundColor(getResources().getColor(R.color.botaodis));
        reservar.setOnClickListener(this);
        Button voltar = findViewById(R.id.BVoltar);
        voltar.setOnClickListener(this);

        mudarCor();
        ocupados();
    }

    public void mudarCor(){
        for(int i = 0; i < lugares.size(); i++){
            if(lugares.get(i).equals("1")){
                lugar1.setBackgroundColor(getResources().getColor(R.color.reservado));
                lugar1.setEnabled(false);
            }
            if(lugares.get(i).equals("2")){
                lugar2.setBackgroundColor(getResources().getColor(R.color.reservado));
                lugar2.setEnabled(false);
            }
            if(lugares.get(i).equals("3")){
                lugar3.setBackgroundColor(getResources().getColor(R.color.reservado));
                lugar3.setEnabled(false);
            }
            if(lugares.get(i).equals("4")){
                lugar4.setBackgroundColor(getResources().getColor(R.color.reservado));
                lugar4.setEnabled(false);
            }
            if(lugares.get(i).equals("5")){
                lugar5.setBackgroundColor(getResources().getColor(R.color.reservado));
                lugar5.setEnabled(false);
            }
            if(lugares.get(i).equals("6")){
                lugar6.setBackgroundColor(getResources().getColor(R.color.reservado));
                lugar6.setEnabled(false);
            }
            if(lugares.get(i).equals("7")){
                lugar7.setBackgroundColor(getResources().getColor(R.color.reservado));
                lugar7.setEnabled(false);
            }
            if(lugares.get(i).equals("8")){
                lugar8.setBackgroundColor(getResources().getColor(R.color.reservado));
                lugar8.setEnabled(false);
            }
            if(lugares.get(i).equals("9")){
                lugar9.setBackgroundColor(getResources().getColor(R.color.reservado));
                lugar9.setEnabled(false);
            }
            if(lugares.get(i).equals("10")){
                lugar10.setBackgroundColor(getResources().getColor(R.color.reservado));
                lugar10.setEnabled(false);
            }
            if(lugares.get(i).equals("11")){
                lugar11.setBackgroundColor(getResources().getColor(R.color.reservado));
                lugar11.setEnabled(false);
            }
            if(lugares.get(i).equals("12")){
                lugar12.setBackgroundColor(getResources().getColor(R.color.reservado));
                lugar12.setEnabled(false);
            }
        }
    }

    public void ocupados(){
        for(int i = 0; i < lugaresOcupados.size(); i++){
            if(lugaresOcupados.get(i).equals("1")){
                lugar1.setBackgroundColor(getResources().getColor(R.color.ocupado));
                lugar1.setEnabled(false);
            }
            if(lugaresOcupados.get(i).equals("2")){
                lugar2.setBackgroundColor(getResources().getColor(R.color.ocupado));
                lugar2.setEnabled(false);
            }
            if(lugaresOcupados.get(i).equals("3")){
                lugar3.setBackgroundColor(getResources().getColor(R.color.ocupado));
                lugar3.setEnabled(false);
            }
            if(lugaresOcupados.get(i).equals("4")){
                lugar4.setBackgroundColor(getResources().getColor(R.color.ocupado));
                lugar4.setEnabled(false);
            }
            if(lugaresOcupados.get(i).equals("5")){
                lugar5.setBackgroundColor(getResources().getColor(R.color.ocupado));
                lugar5.setEnabled(false);
            }
            if(lugaresOcupados.get(i).equals("6")){
                lugar6.setBackgroundColor(getResources().getColor(R.color.ocupado));
                lugar6.setEnabled(false);
            }
            if(lugaresOcupados.get(i).equals("7")){
                lugar7.setBackgroundColor(getResources().getColor(R.color.ocupado));
                lugar7.setEnabled(false);
            }
            if(lugaresOcupados.get(i).equals("8")){
                lugar8.setBackgroundColor(getResources().getColor(R.color.ocupado));
                lugar8.setEnabled(false);
            }
            if(lugaresOcupados.get(i).equals("9")){
                lugar9.setBackgroundColor(getResources().getColor(R.color.ocupado));
                lugar9.setEnabled(false);
            }
            if(lugaresOcupados.get(i).equals("10")){
                lugar10.setBackgroundColor(getResources().getColor(R.color.ocupado));
                lugar10.setEnabled(false);
            }
            if(lugaresOcupados.get(i).equals("11")){
                lugar11.setBackgroundColor(getResources().getColor(R.color.ocupado));
                lugar11.setEnabled(false);
            }
            if(lugaresOcupados.get(i).equals("12")){
                lugar12.setBackgroundColor(getResources().getColor(R.color.ocupado));
                lugar12.setEnabled(false);
            }
        }
    }

    private void reservas(String sala, int lugar) {
        lugaresDB.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                int n = 0;
                for (DataSnapshot reservas : dataSnapshot.getChildren()) {
                    n++;
                }
               /* boolean daparareservar = true;
                for (DataSnapshot reservas : dataSnapshot.getChildren()) {
                    Map reservaT = (Map) (reservas.getValue());
                    if(reservaT.size() == 6) {
                        if (reservaT.get("data").toString().equals(sData)) {
                            if ((sHoraInicio.compareTo(reservaT.get("horaI").toString()) >= 0 && sHoraInicio.compareTo(reservaT.get("horaF").toString()) <= 0)
                                    || (sHoraFim.compareTo(reservaT.get("horaI").toString()) >= 0 && sHoraFim.compareTo(reservaT.get("horaF").toString()) <= 0)) {
                                if (reservaT.get("sala").toString().equals(sala)) {
                                    if (reservaT.get("lugar").toString().equals(Integer.toString(lugar))) {
                                        daparareservar = false;
                                        break;
                                    }
                                }
                            }
                        }
                    }
                }*/

                if (lugares.size()+lugaresOcupados.size()+Integer.parseInt(total)<n || naoPrimeiro) {
                    reserva(lugar);
                    lugares.add(Integer.toString(lugar));
                    if (atual<Integer.parseInt(total)){
                        atual++;
                        Intent intent_lugarobtido = new Intent(MapaSalaReuniao.this, MapaSalaReuniao.class);
                        intent_lugarobtido.putExtra("data", sData);
                        intent_lugarobtido.putExtra("horai", sHoraInicio);
                        intent_lugarobtido.putExtra("horaf", sHoraFim);
                        intent_lugarobtido.putExtra("sala", sala);
                        intent_lugarobtido.putExtra("nPessoas", total);
                        intent_lugarobtido.putExtra("lugaresOcupados", lugaresOcupados);
                        intent_lugarobtido.putExtra("lugares", lugares);
                        intent_lugarobtido.putExtra("atual", atual);
                        intent_lugarobtido.putExtra("permissao", true);
                        intent_lugarobtido.putExtra("lugar", Integer.toString(lugar));
                        lugaresM = lugaresM + lugar + ",";
                        intent_lugarobtido.putExtra("lugaresM", lugaresM);
                        MapaSalaReuniao.this.startActivity(intent_lugarobtido);
                    }
                    else{
                        Intent intent_lugarobtido = new Intent(MapaSalaReuniao.this, LugarObtido.class);
                        intent_lugarobtido.putExtra("data", sData);
                        intent_lugarobtido.putExtra("horai", sHoraInicio);
                        intent_lugarobtido.putExtra("horaf", sHoraFim);
                        intent_lugarobtido.putExtra("sala", sala);
                        intent_lugarobtido.putExtra("tipo", "pessoas");
                        intent_lugarobtido.putExtra("lugar", Integer.toString(lugar));
                        lugaresM = lugaresM + lugar;
                        intent_lugarobtido.putExtra("lugaresM", lugaresM);
                        MapaSalaReuniao.this.startActivity(intent_lugarobtido);
                    }

                } else {
                    Toast.makeText(getApplicationContext(), R.string.naofazerreserva, Toast.LENGTH_LONG).show();
                    Intent intent_lugarobtido = new Intent(MapaSalaReuniao.this, Reserva.class);
                    MapaSalaReuniao.this.startActivity(intent_lugarobtido);
                }
            }
            @Override
            public void onCancelled (@NonNull DatabaseError databaseError){

            }
        });
    }

    public void colocarCorOriginal(){
        lugar1.setBackgroundColor(getResources().getColor(R.color.mapa));
        lugar2.setBackgroundColor(getResources().getColor(R.color.mapa));
        lugar3.setBackgroundColor(getResources().getColor(R.color.mapa));
        lugar4.setBackgroundColor(getResources().getColor(R.color.mapa));
        lugar5.setBackgroundColor(getResources().getColor(R.color.mapa));
        lugar6.setBackgroundColor(getResources().getColor(R.color.mapa));
        lugar7.setBackgroundColor(getResources().getColor(R.color.mapa));
        lugar8.setBackgroundColor(getResources().getColor(R.color.mapa));
        lugar9.setBackgroundColor(getResources().getColor(R.color.mapa));
        lugar10.setBackgroundColor(getResources().getColor(R.color.mapa));
        lugar11.setBackgroundColor(getResources().getColor(R.color.mapa));
        lugar12.setBackgroundColor(getResources().getColor(R.color.mapa));
        mudarCor();
        ocupados();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.button:
                colocarCorOriginal();
                lugar1.setBackgroundColor(getResources().getColor(R.color.selecionado));
                lugar = 1;
                reservar.setEnabled(true);
                reservar.setBackgroundColor(getResources().getColor(R.color.botao));
                break;
            case R.id.button2:
                colocarCorOriginal();
                lugar2.setBackgroundColor(getResources().getColor(R.color.selecionado));
                lugar = 2;
                reservar.setEnabled(true);
                reservar.setBackgroundColor(getResources().getColor(R.color.botao));
                break;
            case R.id.button3:
                colocarCorOriginal();
                lugar3.setBackgroundColor(getResources().getColor(R.color.selecionado));
                lugar = 3;
                reservar.setEnabled(true);
                reservar.setBackgroundColor(getResources().getColor(R.color.botao));
                break;
            case R.id.button4:
                colocarCorOriginal();
                lugar4.setBackgroundColor(getResources().getColor(R.color.selecionado));
                lugar = 4;
                reservar.setEnabled(true);
                reservar.setBackgroundColor(getResources().getColor(R.color.botao));
                break;
            case R.id.button5:
                colocarCorOriginal();
                lugar5.setBackgroundColor(getResources().getColor(R.color.selecionado));
                lugar = 5;
                reservar.setEnabled(true);
                reservar.setBackgroundColor(getResources().getColor(R.color.botao));
                break;
            case R.id.button6:
                colocarCorOriginal();
                lugar6.setBackgroundColor(getResources().getColor(R.color.selecionado));
                lugar = 6;
                reservar.setEnabled(true);
                reservar.setBackgroundColor(getResources().getColor(R.color.botao));
                break;
            case R.id.button7:
                colocarCorOriginal();
                lugar7.setBackgroundColor(getResources().getColor(R.color.selecionado));
                lugar = 7;
                reservar.setEnabled(true);
                reservar.setBackgroundColor(getResources().getColor(R.color.botao));
                break;
            case R.id.button8:
                colocarCorOriginal();
                lugar8.setBackgroundColor(getResources().getColor(R.color.selecionado));
                lugar = 8;
                reservar.setEnabled(true);
                reservar.setBackgroundColor(getResources().getColor(R.color.botao));
                break;
            case R.id.button9:
                colocarCorOriginal();
                lugar9.setBackgroundColor(getResources().getColor(R.color.selecionado));
                lugar = 9;
                reservar.setEnabled(true);
                reservar.setBackgroundColor(getResources().getColor(R.color.botao));
                break;
            case R.id.button10:
                colocarCorOriginal();
                lugar10.setBackgroundColor(getResources().getColor(R.color.selecionado));
                lugar = 10;
                reservar.setEnabled(true);
                reservar.setBackgroundColor(getResources().getColor(R.color.botao));
                break;
            case R.id.button11:
                colocarCorOriginal();
                lugar11.setBackgroundColor(getResources().getColor(R.color.selecionado));
                lugar = 11;
                reservar.setEnabled(true);
                reservar.setBackgroundColor(getResources().getColor(R.color.botao));
                break;
            case R.id.button12:
                colocarCorOriginal();
                lugar12.setBackgroundColor(getResources().getColor(R.color.selecionado));
                lugar = 12;
                reservar.setEnabled(true);
                reservar.setBackgroundColor(getResources().getColor(R.color.botao));
                break;
            case R.id.BReservar:
                reservas(sala,lugar);
                break;
            case R.id.BVoltar:
                Intent intent_voltar = new Intent(MapaSalaReuniao.this, Reserva.class);
                MapaSalaReuniao.this.startActivity(intent_voltar);
                break;
        }
    }

    public void reserva(int nlugar){
        String s = sHoraInicio + sData + sHoraFim + nlugar;
        String md5 = "";
        try {
            MessageDigest md = MessageDigest.getInstance("MD5");
            md.update(s.getBytes(),0,s.length());
            md5 = new BigInteger(1, md.digest()).toString(16);
        }catch(NoSuchAlgorithmException e){
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
}