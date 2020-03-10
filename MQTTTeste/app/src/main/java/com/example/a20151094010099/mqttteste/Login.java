package com.example.a20151094010099.mqttteste;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.ValueEventListener;

import AppBD.BD;
import Models.Usuario;

public class Login extends AppCompatActivity {

    EditText username, senha;
    Button logar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        Toolbar toolbar = (Toolbar) findViewById(R.id.app_bar);
        toolbar.setTitle("Realize o seu login");
        toolbar.setTitleTextColor(getResources().getColor(R.color.corDoTexto));
        setSupportActionBar(toolbar);

        username = (EditText) findViewById(R.id.username);
        senha = (EditText) findViewById(R.id.password);
        logar = (Button) findViewById(R.id.logar);

        logar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                BD.getFirebase().child("usuarios").addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        for (DataSnapshot d : dataSnapshot.getChildren()) {
                            Usuario user = d.getValue(Usuario.class);
                            if (user.getUsername().equals(username.getText().toString()) && senha.getText().toString().equals(user.getSenha())){
                                BD.usuario = user;
                                Intent intent = new Intent(getApplicationContext(),Carrega.class);
                                startActivity(intent);
                            }else{
                                Toast.makeText(Login.this, "Usuario ou senha inválidos", Toast.LENGTH_SHORT).show();
                                username.setText("");
                                senha.setText("");
                            }

                        }
                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {

                    }
                });
            }
        });
    }
}
