package com.example.a20151094010099.mqttteste;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.util.Random;

import AppBD.BD;
import Helper.Base64Custom;
import Models.Placa;
import Models.Usuario;

public class CadastrarPlaca extends AppCompatActivity {

    EditText placa;
    Button cadastrarP;


    @Override
    protected void onDestroy() {
        super.onDestroy();
        Intent intent = new Intent(getApplicationContext(),Carrega.class);
        startActivity(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cadastrar_placa);
        Toolbar toolbar = (Toolbar) findViewById(R.id.app_bar);
        toolbar.setTitle("Cadastrar Placa");
        toolbar.setTitleTextColor(getResources().getColor(R.color.corDoTexto));
        setSupportActionBar(toolbar);
        placa = (EditText) findViewById(R.id.placa);
        cadastrarP = (Button) findViewById(R.id.cadastrarP);

        cadastrarP.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int cont = 0;
                for (Placa p : BD.placas){
                    if(placa.getText().toString().equals(p.getPlaca()+"")){
                        cont = 1;
                    }
                }
                if(cont == 0){
                    BD.placas.clear();
                    Placa p = new Placa();
                    p.setPlaca(placa.getText().toString());
                    Usuario usuario = new Usuario();
                    usuario.setUsername("admin");
                    usuario.setSenha("admin");
                    usuario.setId("YWRtaW4=");
                    p.setUsuario(usuario);
                    Random random = new Random();
                    int id = random.nextInt(10000);
                    for (Placa placa: BD.placas){
                        if(id == Integer.parseInt(placa.getId())){
                            id = random.nextInt(10000);
                        }
                    }
                    p.setId(id+"");
                    p.salvar();
                    Intent intent = new Intent(getApplicationContext(),Carrega.class);
                    startActivity(intent);
                }else{
                    Toast.makeText(getApplicationContext(), "Essa placa já existe", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.cadastra_placa:
                Toast.makeText(getApplicationContext(), "Cadastrar Placa", Toast.LENGTH_SHORT).show();
                break;
            case R.id.listar_placas:
                Intent intent = new Intent(getApplicationContext(),ListaDePlacas.class);
                startActivity(intent);
                break;
            default:
                break;
        }
        return super.onOptionsItemSelected(item);
    }

}
