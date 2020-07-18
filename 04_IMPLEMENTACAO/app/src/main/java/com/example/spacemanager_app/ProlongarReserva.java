package com.example.spacemanager_app;

import android.app.AlarmManager;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.graphics.Color;
import android.graphics.drawable.GradientDrawable;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.text.SimpleDateFormat;
import java.util.Date;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

public class ProlongarReserva extends AppCompatActivity implements View.OnClickListener{

    private ConstraintLayout constraintLayout;
    DatabaseReference ref;
    FirebaseDatabase firebaseDatabase;
    RadioGroup RBduracao;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.prolongar);

        firebaseDatabase = FirebaseDatabase.getInstance();
        ref = firebaseDatabase.getReference("reservas");

        Button prolongar = findViewById(R.id.BProlongar);
        prolongar.setOnClickListener(this);
        Button voltar = findViewById(R.id.BVoltar);
        voltar.setOnClickListener(this);
        RBduracao = (RadioGroup) findViewById(R.id.duracao);
    }
    public void prolonga(int opcao){
        ref.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                String utilizador = "", data = "", horafim = "", horainicio = "", sala="", lugar="", md5="";
                boolean haReserva = false;
                SimpleDateFormat formatter = new SimpleDateFormat("yyyy/MM/dd");
                Date datadata = new Date();
                String datadata2 = formatter.format(datadata);

                SimpleDateFormat formatter2 = new SimpleDateFormat("HH:mm");
                Date horahora = new Date();
                String horahora2 = formatter2.format(horahora);

                for (DataSnapshot reservas : dataSnapshot.getChildren()) {
                    md5 = reservas.getKey();
                    for (DataSnapshot detalhes : reservas.getChildren()) {
                        if (detalhes.getKey().equals("utilizador") && detalhes.getValue().equals(FirebaseAuth.getInstance().getCurrentUser().getEmail())) {
                            utilizador = detalhes.getValue().toString();
                        }
                        if (detalhes.getKey().equals("data")) {
                            data = detalhes.getValue().toString();
                        }
                        if (detalhes.getKey().equals("horaI")) {
                            horainicio = detalhes.getValue().toString();
                        }
                        if (detalhes.getKey().equals("horaF")) {
                            horafim = detalhes.getValue().toString();
                        }
                        if (detalhes.getKey().equals("sala")) {
                            sala = detalhes.getValue().toString();
                        }
                        if (detalhes.getKey().equals("lugar")) {
                            lugar = detalhes.getValue().toString();
                        }
                    }
                    String hh = "";
                    if (datadata2.compareTo(data) == 0 && horahora2.compareTo(horainicio) >= 0 && horahora2.compareTo(horafim) <= 0) {
                        reservas.getRef().removeValue();
                        DatabaseReference id = ref.child(md5);
                        DatabaseReference saladr = id.child("sala");
                        saladr.setValue(sala);
                        DatabaseReference lugardr = id.child("lugar");
                        lugardr.setValue(lugar);
                        DatabaseReference utilizadordr = id.child("utilizador");
                        utilizadordr.setValue(FirebaseAuth.getInstance().getCurrentUser().getEmail());
                        DatabaseReference datadr = id.child("data");
                        datadr.setValue(data);
                        DatabaseReference horaIdr = id.child("horaI");
                        horaIdr.setValue(horainicio);
                        DatabaseReference horaFdr = id.child("horaF");
                        haReserva = true;
                        int minutos = 0, horas = 0;
                        if (opcao == 15 || opcao == 30) {
                            minutos = Integer.parseInt(horafim.substring(3)) + opcao;
                            hh = horafim.substring(0, 2) + ":" + minutos;
                            if (minutos == 60) {
                                hh = Integer.parseInt(hh.substring(0, 2)) + 1 + ":00";
                            }
                            if (minutos == 75) {
                                hh = Integer.parseInt(hh.substring(0, 2)) + 1 + ":15";
                            }
                            horaFdr.setValue(hh);
                            Toast.makeText(getApplicationContext(), R.string.reservaprolonga + " " + hh + "h", Toast.LENGTH_LONG).show();
                            break;
                        }
                        if (opcao == 1) {
                            horas = Integer.parseInt(horafim.substring(0, 2)) + opcao;
                            hh = horas + ":" + horafim.substring(3);
                            horaFdr.setValue(hh);
                            Toast.makeText(getApplicationContext(), R.string.reservaprolonga + " " + hh + "h", Toast.LENGTH_LONG).show();
                            Toast.makeText(getApplicationContext(), R.string.reservaprolonga + " " + hh + "h", Toast.LENGTH_LONG).show();
                            break;
                        }
                    }
                }
                if (haReserva == false){
                    Toast.makeText(getApplicationContext(), R.string.naoprolonga, Toast.LENGTH_LONG).show();
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
            case R.id.BProlongar:
                Intent intent_lugar = new Intent(ProlongarReserva.this, PaginaInicial.class);
                String minutos = ((RadioButton)findViewById(RBduracao.getCheckedRadioButtonId())).getText().toString();
                String valor = minutos.substring(0,minutos.indexOf(" "));
                prolonga(Integer.parseInt(valor));
                ProlongarReserva.this.startActivity(intent_lugar);
                break;
            case R.id.BVoltar:
                Intent intent_voltar = new Intent(ProlongarReserva.this, Reserva.class);
                ProlongarReserva.this.startActivity(intent_voltar);
                break;
        }
    }
}
