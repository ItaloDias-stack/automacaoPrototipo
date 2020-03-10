package com.example.a20151094010099.mqttteste;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Switch;
import android.widget.Toast;

import MQTT.MqTTOptions;

public class Banheiro extends AppCompatActivity {
    MqTTOptions mqtt = new MqTTOptions();
    Switch luzBanheiro;
    Intent intent;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_banheiro);
        luzBanheiro=(Switch) findViewById(R.id.luzBanheiro);
        Toolbar toolbar = (Toolbar) findViewById(R.id.app_bar);
        toolbar.setTitle("Banheiro");
        toolbar.setTitleTextColor(getResources().getColor(R.color.corDoTexto));
        setSupportActionBar(toolbar);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu,menu);
        return super.onCreateOptionsMenu(menu);
    }

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

    public void acender(View view){
        if(luzBanheiro.isChecked()){
            mqtt.publica("/casa/banheiro/luz", "1");
        }else{
            mqtt.publica("/casa/banheiro/luz", "0");
        }
    }
}
