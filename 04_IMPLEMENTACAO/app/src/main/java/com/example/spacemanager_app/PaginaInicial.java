package com.example.spacemanager_app;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.graphics.Color;
import android.graphics.drawable.GradientDrawable;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.PopupMenu;
import android.widget.TextView;

import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.firebase.auth.FirebaseAuth;
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

public class PaginaInicial extends AppCompatActivity implements View.OnClickListener{

    private ConstraintLayout constraintLayout;
    DatabaseReference ref;
    FirebaseDatabase firebaseDatabase;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.pagina_inicial);

        firebaseDatabase = FirebaseDatabase.getInstance();
        ref = firebaseDatabase.getReference("reservas");

        Button reserva = findViewById(R.id.BReserva);
        reserva.setOnClickListener(this);
        Button pesquisa = findViewById(R.id.BPesquisa);
        pesquisa.setOnClickListener(this);
        Button reservas = findViewById(R.id.BMinhas);
        reservas.setOnClickListener(this);
        Button sair = findViewById(R.id.BSair);
        sair.setOnClickListener(this);
        TextView utilizador = findViewById(R.id.utilizador);
        utilizador.setText(FirebaseAuth.getInstance().getCurrentUser().getDisplayName());
        Button prolonga = findViewById(R.id.BProlonga);
        prolonga.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.BReserva:
                Intent intent_reserva = new Intent(PaginaInicial.this, Reserva.class);
                PaginaInicial.this.startActivity(intent_reserva);
                break;

            case R.id.BPesquisa:
                Intent intent_pesquisa = new Intent(PaginaInicial.this, Pesquisa.class);
                PaginaInicial.this.startActivity(intent_pesquisa);
                break;

            case R.id.BMinhas:
                Intent intent_minhas = new Intent(PaginaInicial.this, AsMinhasReservas.class);
                PaginaInicial.this.startActivity(intent_minhas);
                break;

            case R.id.BProlonga:
                Intent intent_prolongar = new Intent(PaginaInicial.this, ProlongarReserva.class);
                PaginaInicial.this.startActivity(intent_prolongar);
                break;

            case R.id.BSair:
                Intent intent_voltar = new Intent(PaginaInicial.this, MainActivity.class);
                intent_voltar.putExtra("log", true);
                PaginaInicial.this.startActivity(intent_voltar);
                break;
        }
    }
}
