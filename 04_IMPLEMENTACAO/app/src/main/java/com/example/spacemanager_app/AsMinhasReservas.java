package com.example.spacemanager_app;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.Map;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

public class AsMinhasReservas extends AppCompatActivity implements View.OnClickListener {

    DatabaseReference ref;
    FirebaseDatabase firebaseDatabase;
    TextView naoExiste, data, horaInicio, horaFim, sala, lugar, nr;
    Button voltar, cancelar/*,anterior, seguinte*/;
    ImageView anterior, seguinte;
    Map[] reservasArray;
    int nReservas = 0, reservaAtual = 0;
    private ConstraintLayout constraintLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.minhas_reservas);

        firebaseDatabase = FirebaseDatabase.getInstance();
        ref = firebaseDatabase.getReference("reservas");
        voltar = findViewById(R.id.BVoltar);
        voltar.setOnClickListener(this);
        naoExiste = findViewById(R.id.naoExisteReserva);
        data = findViewById(R.id.data);
        horaInicio = findViewById(R.id.hora);
        horaFim = findViewById(R.id.duracao);
        sala = findViewById(R.id.sala);
        lugar = findViewById(R.id.lugar);
        cancelar = findViewById(R.id.BCancelar);
        cancelar.setOnClickListener(this);
        anterior = findViewById(R.id.imageView2);
        anterior.setOnClickListener(this);
        seguinte = findViewById(R.id.imageView3);
        seguinte.setOnClickListener(this);
        nr = findViewById(R.id.nreservas);

        ordenarReservas();
    }

    private void ordenarReservas() {
        ref.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                int nReservas = 0;
                for (DataSnapshot reservas : dataSnapshot.getChildren()) {
                    Map map = (Map) (reservas.getValue());
                    for (DataSnapshot detalhes : reservas.getChildren()) {
                        if (detalhes.getKey().equals("utilizador") && detalhes.getValue().equals(FirebaseAuth.getInstance().getCurrentUser().getEmail())) {
                            nReservas++;
                            reservaAtual = 1;
                            anterior.setEnabled(false);
                            anterior.setImageResource(R.drawable.anteriordis);
                        }
                    }
                }
                nr.setText("Reserva" + " " + reservaAtual  + "/" + nReservas);
                reservasArray = new Map[nReservas];
                for (DataSnapshot reservas : dataSnapshot.getChildren()) {
                    for (DataSnapshot detalhes : reservas.getChildren()) {
                        if (detalhes.getKey().equals("utilizador") && detalhes.getValue().equals(FirebaseAuth.getInstance().getCurrentUser().getEmail())) {
                            Map reserva = (Map) (reservas.getValue());
                            if(reserva.size() == 6) {
                                String data = reserva.get("data").toString();
                                if (reservasArray[0] == null) {
                                    reservasArray[0] = (Map) (reservas.getValue());
                                    reservasArray[0].put("md5", reservas.getKey());
                                }
                                else {
                                    for (int j = 0; j < reservasArray.length-1; j++) {
                                        Map r = (Map) (reservasArray[j]);
                                        if (data.compareTo(r.get("data").toString()) == 0) {
                                            String hora = reserva.get("horaI").toString();
                                            if (hora.compareTo(r.get("horaI").toString()) < 0) {
                                                for (int k = reservasArray.length - 1; k > j; k--) {
                                                    reservasArray[k] = reservasArray[k - 1];
                                                }
                                                reservasArray[j] = reserva;
                                                reservasArray[j].put("md5", reservas.getKey());
                                                break;
                                            }
                                            if (hora.compareTo(r.get("horaI").toString()) > 0 && reservasArray[j+1] == null) {
                                                reservasArray[j + 1] = reserva;
                                                reservasArray[j+1].put("md5", reservas.getKey());
                                                break;
                                            }
                                            if (hora.compareTo(r.get("horaI").toString()) == 0 && reservasArray[j+1] == null){
                                                reservasArray[j + 1] = reserva;
                                                reservasArray[j+1].put("md5", reservas.getKey());
                                                break;
                                            }
                                        }
                                        if (data.compareTo(r.get("data").toString()) < 0) {
                                            for (int k = reservasArray.length - 1; k > j; k--) {
                                                reservasArray[k] = reservasArray[k - 1];
                                            }
                                            reservasArray[j] = reserva;
                                            reservasArray[j].put("md5", reservas.getKey());
                                            break;
                                        } else if (data.compareTo(r.get("data").toString()) > 0 && reservasArray[j+1] == null) {
                                            reservasArray[j + 1] = reserva;
                                            reservasArray[j + 1].put("md5", reservas.getKey());
                                            break;
                                        }
                                    }
                                }
                            }
                        }
                    }
                }
                if (nReservas != 0){
                    anterior.setEnabled(false);
                    data.setText("Data: " + reservasArray[0].get("data").toString());
                    horaInicio.setText("Hora de início: " + reservasArray[0].get("horaI").toString() + "h");
                    horaFim.setText("Hora de fim: " + reservasArray[0].get("horaF").toString() + "h");
                    sala.setText("Sala: " + reservasArray[0].get("sala").toString());
                    lugar.setText("Posto de trabalho: " + reservasArray[0].get("lugar").toString());
                    naoExiste.setVisibility(View.INVISIBLE);
                } else{
                    naoExiste.setVisibility(View.VISIBLE);
                    data.setVisibility(View.INVISIBLE);
                    horaInicio.setVisibility(View.INVISIBLE);
                    horaFim.setVisibility(View.INVISIBLE);
                    sala.setVisibility(View.INVISIBLE);
                    lugar.setVisibility(View.INVISIBLE);
                    cancelar.setVisibility(View.INVISIBLE);
                    seguinte.setVisibility(View.INVISIBLE);
                    anterior.setVisibility(View.INVISIBLE);
                    nr.setText("");
                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    public void verReservasAnteriores(int nr) {
        if (reservaAtual <= 1) {
            anterior.setEnabled(false);
            anterior.setImageResource(R.drawable.anteriordis);
        }
        if (reservaAtual >= reservasArray.length) {
            seguinte.setEnabled(false);
            seguinte.setImageResource(R.drawable.seguintedis);
        }
        if (reservaAtual == 1) {
            seguinte.setEnabled(true);
            seguinte.setImageResource(R.drawable.aseguir);
            anterior.setEnabled(false);
            anterior.setImageResource(R.drawable.anteriordis);
        }
        if(reservaAtual < reservasArray.length && reservaAtual > 1) {
            seguinte.setEnabled(true);
            seguinte.setImageResource(R.drawable.aseguir);
            anterior.setEnabled(true);
            anterior.setImageResource(R.drawable.anterior);
        }
            data.setText("Data: " + reservasArray[nr].get("data").toString());
            horaInicio.setText("Hora de início: " + reservasArray[nr].get("horaI").toString() + "h");
            horaFim.setText("Hora de fim: " + reservasArray[nr].get("horaF").toString() + "h");
            sala.setText("Sala: " + reservasArray[nr].get("sala").toString());
            lugar.setText("Posto de trabalho: " + reservasArray[nr].get("lugar").toString());
    }

    public void verReservasSeguintes(int nr) {
        if (nReservas == 1){
            seguinte.setEnabled(false);
            seguinte.setImageResource(R.drawable.seguintedis);
        }
        if (reservaAtual >= reservasArray.length) {
            seguinte.setEnabled(false);
            seguinte.setImageResource(R.drawable.seguintedis);
        }
        if (reservaAtual > 1 ){
            anterior.setEnabled(true);
            anterior.setImageResource(R.drawable.anterior);
        }
        if(reservaAtual < nReservas && reservaAtual > 1) {
            seguinte.setEnabled(true);
            seguinte.setImageResource(R.drawable.aseguir);
            anterior.setEnabled(true);
            anterior.setImageResource(R.drawable.anterior);
        }
        data.setText("Data: " + reservasArray[nr].get("data").toString());
        horaInicio.setText("Hora de início: " + reservasArray[nr].get("horaI").toString() + "h");
        horaFim.setText("Hora de fim: " + reservasArray[nr].get("horaF").toString() + "h");
        sala.setText("Sala: " + reservasArray[nr].get("sala").toString());
        lugar.setText("Posto de trabalho: " + reservasArray[nr].get("lugar").toString());
    }

    public void cancelarReserva(Map reserva){
        String md5 = reserva.get("md5").toString();
        ref.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot reservas : dataSnapshot.getChildren()) {

                    if (reservas.getKey().equals(md5)){
                        reservas.getRef().removeValue();
                    }
                }
                int nres = 0;
                for (DataSnapshot reservas : dataSnapshot.getChildren()) {
                    Map map = (Map) (reservas.getValue());
                    for (DataSnapshot detalhes : reservas.getChildren()) {
                        if (detalhes.getKey().equals("utilizador") && detalhes.getValue().equals(FirebaseAuth.getInstance().getCurrentUser().getEmail())) {
                            nres++;
                        }
                    }
                }
                if (nres!=0) {
                    data.setText("Data: " + reservasArray[0].get("data").toString());
                    horaInicio.setText("Hora de início: " + reservasArray[0].get("horaI").toString() + "h");
                    horaFim.setText("Hora de fim: " + reservasArray[0].get("horaF").toString() + "h");
                    sala.setText("Sala: " + reservasArray[0].get("sala").toString());
                    lugar.setText("Posto de trabalho: " + reservasArray[0].get("lugar").toString());
                    reservaAtual = 1;
                    ordenarReservas();
                    nres--;
                    nr.setText("Reserva" + " " + reservaAtual  + "/" + nres);
                    verReservasAnteriores(0);
                }
                else{
                    naoExiste.setVisibility(View.VISIBLE);
                    data.setVisibility(View.INVISIBLE);
                    horaInicio.setVisibility(View.INVISIBLE);
                    horaFim.setVisibility(View.INVISIBLE);
                    sala.setVisibility(View.INVISIBLE);
                    lugar.setVisibility(View.INVISIBLE);
                    cancelar.setVisibility(View.INVISIBLE);
                    seguinte.setVisibility(View.INVISIBLE);
                    anterior.setVisibility(View.INVISIBLE);
                    nr.setText("");
                }

            }
            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.BVoltar:
                Intent intent_voltar = new Intent(AsMinhasReservas.this, PaginaInicial.class);
                AsMinhasReservas.this.startActivity(intent_voltar);
                break;
            case R.id.imageView2:
                nr.setText(R.string.nreservas);
                reservaAtual--;
                verReservasAnteriores(reservaAtual-1);
                nr.setText("Reserva" + " " + reservaAtual  + "/" + reservasArray.length);
                break;
            case R.id.imageView3:
                nr.setText(R.string.nreservas);
                reservaAtual++;
                verReservasSeguintes(reservaAtual-1);
                nr.setText("Reserva" + " " + reservaAtual  + "/" + reservasArray.length);
                break;
            case R.id.BCancelar:
                cancelarReserva(reservasArray[reservaAtual-1]);
                nr.setText("Reserva" + " " + reservaAtual  + "/" + reservasArray.length);
                break;
        }
    }
}