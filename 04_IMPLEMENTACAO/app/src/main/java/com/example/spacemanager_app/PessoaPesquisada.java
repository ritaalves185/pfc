package com.example.spacemanager_app;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.graphics.Color;
import android.graphics.drawable.GradientDrawable;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.regex.Pattern;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

public class PessoaPesquisada extends AppCompatActivity implements View.OnClickListener {

    private ConstraintLayout constraintLayout;
    DatabaseReference ref;
    FirebaseDatabase firebaseDatabase;
    TextView pessoa, TVhoraI, TVhoraF, TVlugar, TVsala, TVdata, TVnaoReserva;
    String pessoaIntent;

    protected void onCreate(Bundle savedInstanceState) {
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.pessoa_pesquisada);

        firebaseDatabase = FirebaseDatabase.getInstance();
        ref = firebaseDatabase.getReference("reservas");

        pessoa = findViewById(R.id.TVpessoaPesquisada);
        TVhoraI = findViewById(R.id.TVhoraI);
        TVhoraF = findViewById(R.id.TVhoraF);
        TVlugar = findViewById(R.id.TVLugar);
        TVsala = findViewById(R.id.TVSala);
        TVdata = findViewById(R.id.TVdata);
        Button voltar = findViewById(R.id.BVoltar);
        voltar.setOnClickListener(this);
        TVnaoReserva = findViewById(R.id.naoha);

        Intent intent = getIntent();
        pessoaIntent = intent.getStringExtra("pessoa");
        pesquisa(pessoaIntent);
    }

    public void pesquisa(String pessoaString) {
        boolean regex = Pattern.matches("([A-Z]|[a-z]|[0-9])+@[a-z]+[_]?[a-z]+.[a-z]{2,3}", pessoaIntent);
        if (regex) {
            ref.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    String utilizador = "", data = "", horafim = "", horainicio = "", sala = "", lugar = "";
                    boolean haReserva = false;
                    SimpleDateFormat formatter = new SimpleDateFormat("yyyy/MM/dd");
                    Date datadata = new Date();
                    String datadata2 = formatter.format(datadata);

                    SimpleDateFormat formatter2 = new SimpleDateFormat("HH:mm");
                    Date horahora = new Date();
                    String horahora2 = formatter2.format(horahora);

                    for (DataSnapshot reservas : dataSnapshot.getChildren()) {
                        for (DataSnapshot detalhes : reservas.getChildren()) {
                            if (detalhes.getKey().equals("utilizador") && detalhes.getValue().equals(pessoaString)) {
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
                        if (datadata2.compareTo(data) == 0 && horahora2.compareTo(horainicio) >= 0 && horahora2.compareTo(horafim) <= 0) {
                            haReserva = true;
                            pessoa.setText(pessoa.getText().toString() + pessoaString);
                            TVdata.setText("Data: " + data);
                            TVhoraI.setText("Hora de início : " + horainicio);
                            TVhoraF.setText("Hora de fim: " + horafim);
                            TVsala.setText("Sala: " + sala);
                            TVlugar.setText("Posto de trabalho: " + lugar);
                            TVnaoReserva.setVisibility(View.INVISIBLE);
                            break;
                        }
                    }
                    if (!haReserva){
                        TVnaoReserva.setVisibility(View.VISIBLE);
                    }
                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {

                }
            });
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.BVoltar:
                Intent intent_voltar = new Intent(PessoaPesquisada.this, PaginaInicial.class);
                PessoaPesquisada.this.startActivity(intent_voltar);
                break;
        }
    }
}