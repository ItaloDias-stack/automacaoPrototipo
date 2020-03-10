package com.example.a20151094010099.mqttteste;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import Helper.Base64Custom;
import Models.Usuario;

public class MainActivity extends AppCompatActivity {

    EditText user,senha;
    Button button;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        user = (EditText) findViewById(R.id.user);
        senha = (EditText) findViewById(R.id.senha);
        button = (Button) findViewById(R.id.b);

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Usuario usuario = new Usuario();

                usuario.setUsername(user.getText().toString());
                usuario.setSenha(senha.getText().toString());
                usuario.setId(Base64Custom.codificarBase64(user.getText().toString()));
                usuario.salvar();
            }
        });
    }
}
