package com.example.spacemanager_app;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.graphics.Color;
import android.graphics.drawable.GradientDrawable;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.GoogleAuthProvider;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.database.annotations.Nullable;

import java.text.SimpleDateFormat;
import java.util.Date;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;


public class MainActivity extends AppCompatActivity implements View.OnClickListener{

    private ConstraintLayout constraintLayout;
    GoogleSignInClient signInClient;
    FirebaseAuth firebaseAuth;
    //BaseDados baseDados = new BaseDados();
    DatabaseReference ref, ref2;
    FirebaseDatabase firebaseDatabase;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        constraintLayout = (ConstraintLayout)findViewById(R.id.fundo);

        GoogleSignInOptions signInOptions = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken("495591312267-utj02f26co0mrhsvm47voejunjvs4hki.apps.googleusercontent.com")
                .requestEmail().build();
        signInClient = GoogleSignIn.getClient(this, signInOptions);
        firebaseAuth = FirebaseAuth.getInstance();

        Button iniciar = findViewById(R.id.BIniciar);
        iniciar.setOnClickListener(this);
        Button registo = findViewById(R.id.BRegisto);
        registo.setOnClickListener(this);
        Button google = findViewById(R.id.BGoogle);
        google.setOnClickListener(this);

        firebaseDatabase = FirebaseDatabase.getInstance();
        ref = firebaseDatabase.getReference("reservas");
        ref2 = firebaseDatabase.getReference("reservas_anteriores");
        reservaPassada();
    }

    public void moverReserva(String md5){
        DatabaseReference id = ref2.child(md5);
        DatabaseReference sala = id.child("sala");
        DatabaseReference lugar = id.child("lugar");
        DatabaseReference utilizador = id.child("utilizador");
        DatabaseReference data = id.child("data");
        DatabaseReference horaI = id.child("horaI");
        DatabaseReference horaF = id.child("horaF");

        ref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot reservas : dataSnapshot.getChildren()) {
                    if (reservas.getKey().toString().equals(md5)){
                        for (DataSnapshot detalhes : reservas.getChildren()) {
                            switch (detalhes.getKey()){
                                case "data":
                                    data.setValue(detalhes.getValue());
                                    break;
                                case "horaF":
                                    horaF.setValue(detalhes.getValue());
                                    break;
                                case "horaI":
                                    horaI.setValue(detalhes.getValue());
                                    break;
                                case "lugar":
                                    lugar.setValue(detalhes.getValue());
                                    break;
                                case "sala":
                                    sala.setValue(detalhes.getValue());
                                    break;
                                case "utilizador":
                                    utilizador.setValue(detalhes.getValue());
                                    break;
                            }
                        }
                    }
                }
            }
                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {

                }
            });
    }

    public void reservaPassada(){
        ref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot reservas : dataSnapshot.getChildren()) {
                    for (DataSnapshot detalhes : reservas.getChildren()) {
                        SimpleDateFormat formatter = new SimpleDateFormat("yyyy/MM/dd");
                        Date hoje = new Date();
                        SimpleDateFormat formatter2 = new SimpleDateFormat("HH:mm");
                        Date agora = new Date();
                        if (detalhes.getKey().equals("data")) {
                            if (formatter.format(hoje).compareTo(detalhes.getValue().toString()) > 0){
                                moverReserva(reservas.getKey().toString());
                                reservas.getRef().removeValue();
                            }
                            if (formatter.format(hoje).compareTo(detalhes.getValue().toString()) == 0) {
                                for (DataSnapshot detalhes2 : reservas.getChildren()) {
                                    if (detalhes2.getKey().equals("horaF")) {
                                        if (formatter2.format(agora).compareTo(detalhes2.getValue().toString()) > 0) {
                                            moverReserva(reservas.getKey().toString());
                                            reservas.getRef().removeValue();
                                        }
                                    }
                                }
                            }
                        }
                    }
                }
            }
            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    @Override
    protected void onStart() {
        super.onStart();
        Intent logout = getIntent();
        if (logout.getBooleanExtra("log", true)){
            signInClient.signOut();
            firebaseAuth.signOut();
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data){
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == 123){
            Task<GoogleSignInAccount> task = GoogleSignIn.getSignedInAccountFromIntent(data);
            try {
                GoogleSignInAccount signInAccount = task.getResult(ApiException.class);
                if(signInAccount != null){
                    AuthCredential credential = GoogleAuthProvider.getCredential(signInAccount.getIdToken(),null);
                    firebaseAuth.signInWithCredential(credential).addOnCompleteListener(this,task1 -> {
                        if (task1.isSuccessful()){
                            Intent intent_iniciar = new Intent(MainActivity.this, PaginaInicial.class);
                            MainActivity.this.startActivity(intent_iniciar);
                        }
                    });
                }
            }catch(ApiException e){
                e.printStackTrace();
            }
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.BIniciar:
                Intent intent_iniciar = new Intent(MainActivity.this, IniciarSessao.class);
                MainActivity.this.startActivity(intent_iniciar);
                break;

            case R.id.BRegisto:
                Intent intent_registo = new Intent(MainActivity.this, Registo.class);
                MainActivity.this.startActivity(intent_registo);
                break;

            case R.id.BGoogle:
                Intent intent_signIn = signInClient.getSignInIntent();
                GoogleSignInAccount acct;
                acct = getIntent().getParcelableExtra("utilizador");
                startActivityForResult(intent_signIn, 123);
                break;

            /*case R.id.botao:
                Intent intent_botao = new Intent(MainActivity.this, PaginaInicial.class);
                MainActivity.this.startActivity(intent_botao);
                break;*/
        }
    }
}