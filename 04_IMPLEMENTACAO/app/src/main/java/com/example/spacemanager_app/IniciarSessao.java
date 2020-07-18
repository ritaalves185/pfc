package com.example.spacemanager_app;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.graphics.Color;
import android.graphics.drawable.GradientDrawable;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.EmailAuthProvider;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.UserProfileChangeRequest;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

public class IniciarSessao extends AppCompatActivity implements View.OnClickListener{

    private ConstraintLayout constraintLayout;
    EditText utilizador, pass;
    FirebaseAuth fa;
    FirebaseDatabase firebaseDatabase;
    DatabaseReference databaseReference;
    String sUtilizador, sPass;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.sessao);

        utilizador = findViewById(R.id.utilizador);
        pass = findViewById(R.id.pass);

        fa = FirebaseAuth.getInstance();
        firebaseDatabase = FirebaseDatabase.getInstance();
        databaseReference = firebaseDatabase.getReference("utilizadores");

        Button sessao = findViewById(R.id.BIniciar);
        sessao.setOnClickListener(this);
        Button voltar = findViewById(R.id.BVoltar);
        voltar.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.BIniciar:
                iniciar();
                break;
            case R.id.BVoltar:
                Intent intent_voltar = new Intent(IniciarSessao.this, MainActivity.class);
                IniciarSessao.this.startActivity(intent_voltar);
                break;
        }
    }

    private void iniciar(){
        sUtilizador = utilizador.getText().toString().trim();
        sPass = pass.getText().toString().trim();

        if(sUtilizador.isEmpty()){
            utilizador.setError("Tem que inserir um endereço de e-mail.");
            utilizador.requestFocus();
            return;
        }

        if(!Patterns.EMAIL_ADDRESS.matcher(sUtilizador).matches()){
            utilizador.setError("O endereço de e-mail não está correto.");
            utilizador.requestFocus();
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

        databaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
            String nomeUtilizador = "";
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot ds:dataSnapshot.getChildren()){
                    for (DataSnapshot ds2:ds.getChildren()){
                        if(ds2.getKey().equals("email")&& ds2.getValue().equals(sUtilizador)){
                            for (DataSnapshot ds3:ds.getChildren()) {
                                if (ds3.getKey().equals("nome")){
                                    nomeUtilizador = ds3.getValue().toString();
                                }
                            }
                        }
                    }
                }
                AuthCredential authCredential = EmailAuthProvider.getCredential(sUtilizador,sPass);
                fa.signInWithCredential(authCredential).addOnCompleteListener(IniciarSessao.this,task -> {
                    if (task.isSuccessful()){
                        UserProfileChangeRequest userProfileChangeRequest = new UserProfileChangeRequest.Builder().setDisplayName(nomeUtilizador).build();
                        fa.getCurrentUser().updateProfile(userProfileChangeRequest).addOnCompleteListener(IniciarSessao.this, task1 -> {
                            if (task1.isSuccessful()){
                                Intent intent = new Intent(IniciarSessao.this, PaginaInicial.class);
                                IniciarSessao.this.startActivity(intent);
                            }
                        });

                    }
                });
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }
}
