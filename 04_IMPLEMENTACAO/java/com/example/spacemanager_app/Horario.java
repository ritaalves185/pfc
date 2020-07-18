package com.example.spacemanager_app;

import android.content.Context;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.graphics.Color;
import android.graphics.drawable.GradientDrawable;
import android.os.Bundle;
import android.text.format.DateFormat;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CalendarView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.regex.Pattern;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

public class Horario extends AppCompatActivity implements View.OnClickListener {

    private ConstraintLayout constraintLayout;
    RadioGroup RBduracao;
    Spinner Spinnerhora, Spinnerminutos;
    String dia, mes, ano;
    String[] horas = new String[]{"00", "01", "02", "03", "04", "05", "06", "07", "08", "09", "10", "11", "12", "13", "14", "15", "16", "17", "18", "19", "20", "21", "22", "23"};
    String[] minutos = new String[]{"00", "15", "30", "45"};
    final List<String> listaHoras = new ArrayList<>(Arrays.asList(horas));
    final List<String> listaMinutos = new ArrayList<>(Arrays.asList(minutos));
    String tipo;
    DatabaseReference reservas, db;
    FirebaseDatabase firebaseDatabase;
    ArrayList<String> lugares = new ArrayList<String>();
    ArrayList<String> lugaresOcupados = new ArrayList<String>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.horario);

        firebaseDatabase = FirebaseDatabase.getInstance();
        reservas = firebaseDatabase.getReference("reservas");
        db = firebaseDatabase.getReference();

        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.DAY_OF_YEAR, 1);

        RBduracao = (RadioGroup) findViewById(R.id.duracao);
        Spinnerhora = findViewById(R.id.hora);
        Spinnerminutos = findViewById(R.id.minuto);

        final ArrayAdapter<String> spinnerAdapter = new ArrayAdapter<>(this, R.layout.support_simple_spinner_dropdown_item, listaHoras);
        spinnerAdapter.setDropDownViewResource(R.layout.spinner_horas);
        Spinnerhora.setAdapter(spinnerAdapter);
        SpinnerHoras(Spinnerhora);

        final ArrayAdapter<String> spinnerAdapter2 = new ArrayAdapter<>(this, R.layout.support_simple_spinner_dropdown_item, listaMinutos);
        spinnerAdapter2.setDropDownViewResource(R.layout.spinner_minutos);
        Spinnerminutos.setAdapter(spinnerAdapter2);
        SpinnerMinutos(Spinnerminutos);

        Button escolher = findViewById(R.id.BEscolher);
        escolher.setOnClickListener(this);

        SimpleDateFormat formatter = new SimpleDateFormat("yyyy/MM/dd");
        Date datadata = new Date();
        ano = formatter.format(datadata).substring(0, 4);
        mes = formatter.format(datadata).substring(5, 7);
        dia = formatter.format(datadata).substring(8);

        CalendarView calendarView = findViewById(R.id.calendarView);
        calendarView.setOnDateChangeListener(new CalendarView.OnDateChangeListener() {
            @Override
            public void onSelectedDayChange(@NonNull CalendarView view, int year, int month, int dayOfMonth) {
                dia = String.valueOf(dayOfMonth);
                if (Integer.parseInt(dia) < 10) {
                    dia = "0" + dia;
                }
                mes = String.valueOf(month + 1);
                if (Integer.parseInt(mes) < 10) {
                    mes = "0" + mes;
                }
                ano = String.valueOf(year);
            }
        });

        Intent intent = getIntent();
        tipo = intent.getStringExtra("tipo");

    }

    public String SpinnerHoras(View view) {
        SimpleDateFormat formatter = new SimpleDateFormat("HH:mm");
        Date agora = new Date();
        String agoraagora = formatter.format(agora);
        int m = Integer.parseInt(agoraagora.substring(3));
        for (int i = 0; i < listaHoras.size(); i++) {
            if (listaHoras.get(i).equals(agoraagora.substring(0, 2))) {
                Spinnerhora.setSelection(i);
                if (m > 45) {
                    Spinnerhora.setSelection(i + 1);
                }
                break;
            }
        }
        return Spinnerhora.getSelectedItem().toString();
    }

    public String SpinnerMinutos(View view) {
        SimpleDateFormat formatter = new SimpleDateFormat("HH:mm");
        Date agora = new Date();
        String agoraagora = formatter.format(agora);
        for (int i = 0; i < listaMinutos.size(); i++) {
            int m = Integer.parseInt(listaMinutos.get(i));
            int m2 = Integer.parseInt(agoraagora.substring(3));
            if (m2 <= m) {
                Spinnerminutos.setSelection(i);
                break;
            }
            if (m2 > 45) {
                Spinnerminutos.setSelection(0);
                break;
            }
        }
        return Spinnerminutos.getSelectedItem().toString();
    }

    private void reservas(String inicio, String fim, String data) {
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy/MM/dd");
        Date data1 = new Date();
        String datadata = formatter.format(data1);
        SimpleDateFormat formatter2 = new SimpleDateFormat("HH:mm");
        Date agora = new Date();
        String agoraagora = formatter2.format(agora);
        db.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot tabelas : dataSnapshot.getChildren()) {
                    if (tabelas.getKey().equals("reservas")) {
                        for (DataSnapshot reservas : tabelas.getChildren()) {
                            Map reservaT = (Map) (reservas.getValue());
                            if (reservaT.size() == 6) {
                                if (reservaT.get("data").toString().equals(data)) {
                                    if ((inicio.compareTo(reservaT.get("horaI").toString()) >= 0 && inicio.compareTo(reservaT.get("horaF").toString()) <= 0)
                                            || (fim.compareTo(reservaT.get("horaI").toString()) >= 0 && fim.compareTo(reservaT.get("horaF").toString()) <= 0)) {
                                        lugares.add(reservaT.get("lugar").toString());
                                    }
                                }
                            }
                        }
                    }
                    if (data.compareTo(datadata) == 0) {
                        if (inicio.compareTo(SpinnerHoras(Spinnerhora) + ":" + SpinnerMinutos(Spinnerminutos)) == 0) {
                            for (DataSnapshot tabelas2 : dataSnapshot.getChildren()) {
                                if (tabelas2.getKey().equals("sensores_estado")) {
                                    for (DataSnapshot estado : tabelas2.getChildren()) {
                                        Map lugar = (Map) (estado.getValue());
                                        if (lugar.get("ocupado").equals("sim")) {
                                            String sensor = lugar.get("idSensor").toString();
                                            for (DataSnapshot dataSnapshot3 : dataSnapshot.getChildren()) {
                                                if (dataSnapshot3.getKey().equals("lugar_sensor")) {
                                                    for (DataSnapshot lugares : dataSnapshot3.getChildren()) {
                                                        Map lugar2 = (Map) (lugares.getValue());
                                                        if (lugar2.get("idSensor").equals(sensor)) {
                                                            boolean jaExiste = false;
                                                            for (int i = 0; i < lugaresOcupados.size(); i++) {
                                                                if (lugaresOcupados.get(i).equals(lugar2.get("idLugar").toString())) {
                                                                    jaExiste = true;
                                                                }
                                                            }
                                                            if (!jaExiste) {
                                                                lugaresOcupados.add(lugar2.get("idLugar").toString());
                                                            }
                                                        }
                                                    }
                                                }
                                            }
                                        }
                                    }
                                }
                            }
                        }
                    }
                }
                switch (tipo) {
                    case "mapa":
                        Intent intent_obter = new Intent(Horario.this, MapaSala.class);
                        intent_obter.putExtra("lugares", lugares);
                        intent_obter.putExtra("lugaresOcupados", lugaresOcupados);
                        intent_obter.putExtra("data", data);
                        intent_obter.putExtra("horai", Spinnerhora.getSelectedItem().toString() + ":" + Spinnerminutos.getSelectedItem().toString());
                        intent_obter.putExtra("horaf", fim);
                        intent_obter.putExtra("tipo", "mapa");
                        Horario.this.startActivity(intent_obter);
                        break;
                    case "aleatorio":
                        Intent intent_obter2 = new Intent(Horario.this, LugarObtido.class);
                        intent_obter2.putExtra("tipo", "aleatorio");
                        intent_obter2.putExtra("data", data);
                        intent_obter2.putExtra("horai", Spinnerhora.getSelectedItem().toString() + ":" + Spinnerminutos.getSelectedItem().toString());
                        intent_obter2.putExtra("horaf", fim);
                        intent_obter2.putExtra("lugares", lugares);
                        intent_obter2.putExtra("sala", "1");
                        Horario.this.startActivity(intent_obter2);
                        break;
                    case "pessoas":
                        Intent intent_obter3 = new Intent(Horario.this, Reuniao.class);
                        intent_obter3.putExtra("data", data);
                        intent_obter3.putExtra("horai", Spinnerhora.getSelectedItem().toString() + ":" + Spinnerminutos.getSelectedItem().toString());
                        intent_obter3.putExtra("horaf", fim);
                        intent_obter3.putExtra("tipo", "pessoas");
                        intent_obter3.putExtra("lugaresOcupados", lugaresOcupados);
                        intent_obter3.putExtra("lugares", lugares);
                        Horario.this.startActivity(intent_obter3);
                        break;
                    case "opcao":
                        Intent intent_obter4 = new Intent(Horario.this, Opcao.class);
                        intent_obter4.putExtra("data", data);
                        intent_obter4.putExtra("horai", Spinnerhora.getSelectedItem().toString() + ":" + Spinnerminutos.getSelectedItem().toString());
                        intent_obter4.putExtra("horaf", fim);
                        intent_obter4.putExtra("tipo", "opcao");
                        intent_obter4.putExtra("lugares", lugares);
                        Horario.this.startActivity(intent_obter4);
                        break;
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }

        });

    }

    public boolean checkHora(String sHora) {
        SimpleDateFormat formatter = new SimpleDateFormat("HH:mm");
        Date agora = new Date();
        String agoraagora = formatter.format(agora);
        if (agoraagora.compareTo(sHora) <= 0) {
            return true;
        }
        return false;
    }

    public boolean checkDia(String sDia) {
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy/MM/dd");
        Date hoje = new Date();
        String hojehoje = formatter.format(hoje);
        if (hojehoje.compareTo(sDia) < 0) {
            return true;
        }
        return false;
    }

    public boolean checkHoje(String sDia) {
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy/MM/dd");
        Date hoje = new Date();
        String hojehoje = formatter.format(hoje);
        if (hojehoje.compareTo(sDia) == 0) {
            return true;
        }
        return false;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.BEscolher:
                String data = ano + "/" + mes + "/" + dia;
                if ((checkHora(Spinnerhora.getSelectedItem().toString() + ":" + Spinnerminutos.getSelectedItem().toString()) && checkHoje(data)) || checkDia(data)) {
                    String duracao = ((RadioButton) findViewById(RBduracao.getCheckedRadioButtonId())).getText().toString();
                    int horafim = Integer.parseInt(Spinnerhora.getSelectedItem().toString()) + Integer.parseInt(duracao.substring(0, 1));
                    String horafimString = horafim + "";
                    if (horafim == 24) horafim = 0;
                    if (horafim == 25) horafim = 1;
                    if (horafim == 1 || horafim == 0) {
                        horafimString = "0" + horafim;
                    }
                    String sHF = horafimString + ":" + Spinnerminutos.getSelectedItem().toString();
                    reservas(Spinnerhora.getSelectedItem().toString() + ":" + Spinnerminutos.getSelectedItem().toString(), sHF, data);
                } else if (!checkHora(Spinnerhora.getSelectedItem().toString() + ":" + Spinnerminutos.getSelectedItem().toString()) && checkHoje(data)) {
                    Toast.makeText(getApplicationContext(), R.string.horaPos, Toast.LENGTH_LONG).show();
                } else if (!checkDia(data) && !checkHoje(data)) {
                    Toast.makeText(getApplicationContext(), R.string.dataPos, Toast.LENGTH_LONG).show();
                }
                break;
            case R.id.BVoltar:
                Intent intent_voltar = new Intent(Horario.this, Opcao.class);
                Horario.this.startActivity(intent_voltar);
                break;
        }
    }
}