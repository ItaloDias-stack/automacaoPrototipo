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
import android.widget.TextView;
import android.widget.Toast;

import org.eclipse.paho.client.mqttv3.IMqttDeliveryToken;
import org.eclipse.paho.client.mqttv3.MqttCallback;
import org.eclipse.paho.client.mqttv3.MqttMessage;

import MQTT.MqTTOptions;

public class Quarto1 extends AppCompatActivity {
    MqTTOptions mqtt = new MqTTOptions();
    Switch luzQuarto1;
    TextView fogoQuarto1;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quarto1);
        luzQuarto1=(Switch) findViewById(R.id.luzQuarto1);
        Toolbar toolbar = (Toolbar) findViewById(R.id.app_bar);
        toolbar.setTitle("Quarto 1");
        toolbar.setTitleTextColor(getResources().getColor(R.color.corDoTexto));
        setSupportActionBar(toolbar);
        fogoQuarto1 = (TextView) findViewById(R.id.fogoQuarto1);
        mqtt.conect(getApplicationContext()).setCallback(new MqttCallback() {
            @Override
            public void connectionLost(Throwable cause) {

            }

            @Override
            public void messageArrived(String topic, MqttMessage message) throws Exception {
                /*if (topic.equals("/casa/fogo")){
                    if (message.getPayload().toString().equals("4")){
                        fogoQuarto1.setText("Sim");
                    }if (message.getPayload().toString().equals("5")){
                        fogoQuarto1.setText("Não");
                    }
                }*/
            }

            @Override
            public void deliveryComplete(IMqttDeliveryToken token) {

            }
        });
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

    public void acender(View view){
        if(luzQuarto1.isChecked()){
            mqtt.publica("/casa/luz", "4");
        }else{
            mqtt.publica("/casa/luz", "5");
        }
    }
}
