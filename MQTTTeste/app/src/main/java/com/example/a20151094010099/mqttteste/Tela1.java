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
import android.widget.TextView;
import android.widget.Toast;

import AppBD.BD;
import MQTT.MqTTOptions;

public class Tela1 extends AppCompatActivity {
    Button quarto1;
    Button quarto2;
    Button cozinha;
    Button banheiro;
    Button sala;
    MqTTOptions mqtt = new MqTTOptions();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tela1);
        mqtt.conect(getApplicationContext());
        sala = (Button) findViewById(R.id.botaoSala);
        quarto1 = (Button) findViewById(R.id.botaoQuarto1);
        quarto2 = (Button) findViewById(R.id.botaoQuarto2);
        cozinha = (Button) findViewById(R.id.botaoCozinha);
        banheiro = (Button) findViewById(R.id.botaoBanheiro);
        Toolbar toolbar = (Toolbar) findViewById(R.id.app_bar);
        toolbar.setTitle("Menu");
        toolbar.setTitleTextColor(getResources().getColor(R.color.corDoTexto));
        setSupportActionBar(toolbar);

       // textView.setText(BD.placas.get(0)+"");
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu,menu);
        return super.onCreateOptionsMenu(menu);
    }

    Intent intent;
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case R.id.cadastra_placa:
                intent = new Intent(getApplicationContext(),CadastrarPlaca.class);
                startActivity(intent);
                break;
            case R.id.listar_placas:
                intent = new Intent(getApplicationContext(),ListaDePlacas.class);
                startActivity(intent);
                break;
            default:
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    public void vaiPSala(View view){
        Intent intent = new Intent(getApplicationContext(),Sala.class);
        startActivity(intent);
    }

    public void vaiPQuarto1(View view){
        Intent intent = new Intent(getApplicationContext(),Quarto1.class);
        startActivity(intent);
    }

    public void vaiPQuarto2(View view){
        Intent intent = new Intent(getApplicationContext(),Quarto2.class);
        startActivity(intent);
    }

    public void vaiPCozinha(View view){
        Intent intent = new Intent(getApplicationContext(),Cozinha.class);
        startActivity(intent);
    }

    public void vaiPBanheiro(View view){
        Intent intent = new Intent(getApplicationContext(),Banheiro.class);
        startActivity(intent);
    }
    public void cadast(View view){
        Intent intent = new Intent(getApplicationContext(),MainActivity.class);
        startActivity(intent);
    }
}
