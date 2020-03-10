package com.example.a20151094010099.mqttteste;

import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.support.v4.app.NotificationCompat;
import android.support.v4.app.TaskStackBuilder;
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

public class Cozinha extends AppCompatActivity {
    MqTTOptions mqtt = new MqTTOptions();
    Switch luzCozinha;
    TextView fogoCozinha,gasCozinha;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cozinha);
        luzCozinha=(Switch) findViewById(R.id.luzCozinha);
        Toolbar toolbar = (Toolbar) findViewById(R.id.app_bar);
        toolbar.setTitle("Cozinha");
        toolbar.setTitleTextColor(getResources().getColor(R.color.corDoTexto));
        setSupportActionBar(toolbar);
        fogoCozinha = (TextView) findViewById(R.id.fogoCozinha);
        gasCozinha = (TextView) findViewById(R.id.gasCozinha);
        mqtt.conect(getApplicationContext()).setCallback(new MqttCallback() {
            @Override
            public void connectionLost(Throwable cause) {

            }

            @Override
            public void messageArrived(String topic, MqttMessage message) throws Exception {
                if (topic.equals("/casa/fogo")){
                    if (message.getPayload().toString().equals("0")){
                        fogoCozinha.setText("Não");

                    }if(message.getPayload().toString().equals("1")){
                        fogoCozinha.setText("Sim");
                        notificacao("Fogo","Sua casa está pegando fogo", getApplicationContext(),R.drawable.ic_error_outline_black_24dp);
                    }if (message.getPayload().toString().equals("2")){
                        gasCozinha.setText("Não");
                    }if (message.getPayload().toString().equals("3")){
                        notificacao("Vazamento de gás","Sua casa está com vazamento de gás", getApplicationContext(),R.drawable.ic_error_outline_black_24dp);
                        gasCozinha.setText("Sim");
                    }
                }
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

    public void notificacao(String titulo, String texto, Context context, int img) {
        NotificationCompat.Builder mBuilder = new NotificationCompat.Builder(context)
                .setSmallIcon(img)
                .setContentTitle(titulo)
                .setContentText(texto);
        Intent resultIntent = new Intent(context,Sala.class);
        TaskStackBuilder stackBuilder = TaskStackBuilder.create(context);
        stackBuilder.addParentStack(Sala.class);
        stackBuilder.addNextIntent(resultIntent);
        int id = (int) (Math.random()*1000);
        PendingIntent pendingIntent = stackBuilder.getPendingIntent(id,PendingIntent.FLAG_UPDATE_CURRENT);
        mBuilder.setContentIntent(pendingIntent);
        NotificationManager notificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        notificationManager.notify(id,mBuilder.build());


    }

    public void acender(View view){
        if(luzCozinha.isChecked()){
            mqtt.publica("/casa/luz", "2");
        }else{
            mqtt.publica("/casa/luz", "3");
        }
    }
}
