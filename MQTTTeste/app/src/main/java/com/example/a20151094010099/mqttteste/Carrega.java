package com.example.a20151094010099.mqttteste;

import android.content.Intent;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.ValueEventListener;

import AppBD.BD;
import Models.Placa;
import Models.Usuario;

public class Carrega extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_carrega);
        Usuario usuario = new Usuario();
        usuario.setUsername("123456");
        usuario.setSenha("123456");
        usuario.setId("123456");
        Placa placa = new Placa();
        placa.setId("1");
        placa.setPlaca("12345");
        placa.setUsuario(usuario);
        BD.placas.add(placa);

        if(BD.placas.get(0) != null){
            BD.placas.clear();
        }
        BD.getFirebase().child("placas").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for(DataSnapshot d: dataSnapshot.getChildren()){
                    Placa placa = d.getValue(Placa.class);
                    if (placa.getUsuario().getUsername().equals(BD.usuario.getUsername())){
                        BD.placas.add(placa);
                        BD.cont = 1;
                    }
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

        BD.getFirebase().child("usuarios").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                vai();
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }
    public void vai(){
        Intent intent = new Intent(getApplicationContext(),Tela1.class);
        startActivity(intent);
    }
}
