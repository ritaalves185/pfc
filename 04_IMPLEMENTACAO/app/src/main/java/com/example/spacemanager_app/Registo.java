package com.example.spacemanager_app;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.graphics.Color;
import android.graphics.drawable.GradientDrawable;
import android.os.Bundle;
import android.util.Log;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;


import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.math.BigInteger;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

public class Registo extends AppCompatActivity implements View.OnClickListener{

    private ConstraintLayout constraintLayout;
    EditText nome, email, pass;
    private String sNome, sEmail, sPass;
    FirebaseAuth fa;
    FirebaseDatabase firebaseDatabase;
    DatabaseReference databaseReference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.registo);

        fa = FirebaseAuth.getInstance();
        firebaseDatabase = FirebaseDatabase.getInstance();
        databaseReference = firebaseDatabase.getReference("utilizadores");

        Button registo = findViewById(R.id.BRegisto);
        registo.setOnClickListener(this);
        Button voltar = findViewById(R.id.BVoltar);
        voltar.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.BRegisto:
                registo();
                break;
            case R.id.BVoltar:
                Intent intent_voltar = new Intent(Registo.this, MainActivity.class);
                Registo.this.startActivity(intent_voltar);
                break;
        }
    }

    public void registo(){
        nome = findViewById(R.id.nome);
        email = findViewById(R.id.email);
        pass = findViewById(R.id.pass);

        sNome = nome.getText().toString().trim();
        sEmail = email.getText().toString().trim();
        sPass = pass.getText().toString().trim();

        if(sEmail.isEmpty()){
            email.setError("Tem que inserir um endereço de e-mail.");
            email.requestFocus();
            return;
        }

        if(!Patterns.EMAIL_ADDRESS.matcher(sEmail).matches()){
            email.setError("O endereço de e-mail não está correto.");
            email.requestFocus();
            return;
        }

        if(sPass.isEmpty()){
            pass.setError("Tem que inserir uma palavra-chave.");
            pass.requestFocus();
            return;
        }

        if(sPass.length()<6){
            pass.setError("A palavra-chave tem que ter no mínimo 8 caractéres.");
            pass.requestFocus();
            return;
        }

        fa.createUserWithEmailAndPassword(sEmail,sPass).addOnCompleteListener(this, task -> {
            if (!task.isSuccessful()) {
                Log.e("TAG", "CANCELADA!!!!!");
            }
            if(task.isSuccessful()){
                Log.e("TAG", "SUCESSO!!!!!");
                databaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        String md5 = "";
                        try {
                            MessageDigest md = MessageDigest.getInstance("MD5");
                            md.update(sEmail.getBytes(),0,sEmail.length());
                            md5 = new BigInteger(1, md.digest()).toString(16);
                        }catch(NoSuchAlgorithmException e){
                            System.err.println("Erro ao gerar o código MD5");
                        }

                        DatabaseReference utilizador = databaseReference.child(md5);
                        DatabaseReference nome = utilizador.child("nome");
                        nome.setValue(sNome);
                        DatabaseReference email = utilizador.child("email");
                        email.setValue(sEmail);
                        DatabaseReference pass = utilizador.child("pass");
                        pass.setValue(sPass);
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }
                });
                Intent intent = new Intent(Registo.this, MainActivity.class);
                Registo.this.startActivity(intent);
            }
        });
    }
}
