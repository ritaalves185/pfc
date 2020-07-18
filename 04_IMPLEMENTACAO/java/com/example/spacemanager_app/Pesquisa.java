package com.example.spacemanager_app;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.graphics.Color;
import android.graphics.drawable.GradientDrawable;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.regex.Pattern;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

public class Pesquisa extends AppCompatActivity implements View.OnClickListener {

    private ConstraintLayout constraintLayout;
    DatabaseReference ref;
    FirebaseDatabase firebaseDatabase;
    EditText pessoa;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.pesquisa);

        firebaseDatabase = FirebaseDatabase.getInstance();
        ref = firebaseDatabase.getReference("reservas");

        pessoa = findViewById(R.id.pessoa);
        Button pesquisa = findViewById(R.id.BPesquisa);
        pesquisa.setOnClickListener(this);
        Button voltar = findViewById(R.id.BVoltar);
        voltar.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.BPesquisa:
                Intent intent_pessoa_pesquisada = new Intent(Pesquisa.this, PessoaPesquisada.class);
                intent_pessoa_pesquisada.putExtra("pessoa", pessoa.getText().toString());
                Pesquisa.this.startActivity(intent_pessoa_pesquisada);
                break;
            case R.id.BVoltar:
                Intent intent_voltar = new Intent(Pesquisa.this, PaginaInicial.class);
                Pesquisa.this.startActivity(intent_voltar);
                break;
        }
    }
}