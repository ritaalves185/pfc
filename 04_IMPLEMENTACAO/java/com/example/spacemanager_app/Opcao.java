package com.example.spacemanager_app;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.graphics.Color;
import android.graphics.drawable.GradientDrawable;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import java.util.ArrayList;
import java.util.Map;
import java.util.Random;

public class Opcao extends AppCompatActivity implements View.OnClickListener{

	private ConstraintLayout constraintLayout;
	DatabaseReference db;
	FirebaseDatabase firebaseDatabase;
    CheckBox monitor, janela;
	String sData, sHoraInicio, sHoraFim;
	ArrayList<String> lugares;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
		super.onCreate(savedInstanceState);
		setContentView(R.layout.opcao);

		Button lugar = findViewById(R.id.BObter);
		lugar.setOnClickListener(this);
		Button voltar = findViewById(R.id.BVoltar);
		voltar.setOnClickListener(this);
		monitor = findViewById(R.id.monitor);
        janela = findViewById(R.id.janela);

		Intent intent = getIntent();
		sData = intent.getStringExtra("data");
		sHoraInicio = intent.getExtras().getString("horai");
		sHoraFim = intent.getStringExtra("horaf");
		lugares = intent.getStringArrayListExtra("lugares");

        firebaseDatabase = FirebaseDatabase.getInstance();
        db = firebaseDatabase.getReference();
	}

    private void reservas(boolean m, boolean j){
        db.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
            	String idM = "0", idJ = "0";
				ArrayList<String> lugaresDes = new ArrayList<String>();
                for (DataSnapshot tabelas : dataSnapshot.getChildren()) {
                    if (tabelas.getKey().equals("caracteristicas")) {
						for (DataSnapshot carac : tabelas.getChildren()){
							Map c = (Map) (carac.getValue());
							if (c.get("descricao").toString().equals("monitor") && m){
								idM = c.get("idCaracteristica").toString();
							}
							if (c.get("descricao").toString().equals("janela") && j){
								idJ = c.get("idCaracteristica").toString();
							}
						}
                    }
					if (tabelas.getKey().equals("lugares_caracteristicas")) {
						for (DataSnapshot lugares : tabelas.getChildren()) {
							Map l = (Map) (lugares.getValue());
							if (l.get("idCaracteristicaM").toString().equals(idM) && l.get("idCaracteristicaJ").toString().equals(idJ)){
								lugaresDes.add(l.get("idLugar").toString());
							}
						}
					}
                }

                for (int i = 0; i<lugaresDes.size();i++){
                	for (int j = 0; j<lugares.size();j++){
                		if (lugaresDes.get(i).equals(lugares.get(j))){
                			lugaresDes.remove(lugaresDes.get(i));
						}
					}
				}
				Random rand = new Random();
				int rand2 = rand.nextInt(lugaresDes.size());
				String lugar = lugaresDes.get(rand2);
				Intent intent_lugar = new Intent(Opcao.this, LugarObtido.class);
				intent_lugar.putExtra("lugar", lugar);
				intent_lugar.putExtra("data", sData);
				intent_lugar.putExtra("horai", sHoraInicio);
				intent_lugar.putExtra("horaf", sHoraFim);
				intent_lugar.putExtra("sala", "1");
				intent_lugar.putExtra("tipo", "opcao");
				Opcao.this.startActivity(intent_lugar);
            }

            @Override
            public void onCancelled (@NonNull DatabaseError databaseError){

            }
            });
    }

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
			case R.id.BObter:
			    reservas(monitor.isChecked(), janela.isChecked());
				break;
			case R.id.BVoltar:
				Intent intent_voltar = new Intent(Opcao.this, Reserva.class);
				Opcao.this.startActivity(intent_voltar);
				break;

		}
	}
}
