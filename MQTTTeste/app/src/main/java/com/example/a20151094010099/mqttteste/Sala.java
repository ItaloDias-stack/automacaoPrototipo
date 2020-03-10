package com.example.a20151094010099.mqttteste;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.support.v4.app.NotificationCompat;
import android.support.v4.app.NotificationManagerCompat;
import android.support.v4.app.TaskStackBuilder;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.SeekBar;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;
import android.os.Handler;

import org.eclipse.paho.client.mqttv3.IMqttDeliveryToken;
import org.eclipse.paho.client.mqttv3.MqttCallback;
import org.eclipse.paho.client.mqttv3.MqttMessage;

import MQTT.MqTTOptions;


public class Sala extends AppCompatActivity {
    MqTTOptions mqtt = new MqTTOptions();
    Switch aSwitch;
    Switch seguranca;
    ProgressBar temp;
    TextView progresso, presenca, chuva;
    int pStatus;
    private Handler handler = new Handler();
    protected static final int TIMER_RUNTIME = 10000;
    protected boolean mbActive;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sala);


        aSwitch = (Switch) findViewById(R.id.luzSala);
        seguranca = (Switch) findViewById(R.id.seguranca);
        temp = (ProgressBar) findViewById(R.id.temp);
        chuva = (TextView) findViewById(R.id.chuva);
        progresso = (TextView) findViewById(R.id.progresso);
        presenca = (TextView) findViewById(R.id.presenca);
        mqtt.conect(getApplicationContext()).setCallback(new MqttCallback() {
            @Override
            public void connectionLost(Throwable cause) {

            }

            @Override
            public void messageArrived(String topic, MqttMessage message) throws Exception {
                String mensagem = new String(message.getPayload());
                //Toast.makeText(Sala.this, "Tópico:" + topic + " mensagem: " + mensagem, Toast.LENGTH_SHORT).show();
                if (topic.equals("/casa/presenca")) {
                    if (mensagem.equals("2")) {
                        presenca.setText("Tem gente em casa");
                    }
                    if (mensagem.equals("3")) {
                        presenca.setText("Não tem gente");
                    }
                }
                if (topic.equals("/casa/chuva")) {

                    if (mensagem.equals("1")) {
                        chuva.setText("Chuva: Sim");
                        notificacao("Alerta de chuva", "Está chuvendo", getApplicationContext(), R.drawable.ic_chuva);
                    }else{
                        chuva.setText("Cuva: Não");
                    }

                }
                if (topic.equals("/casa/temp")) {
                    tempG(Float.parseFloat(mensagem));
                }

            }

            @Override
            public void deliveryComplete(IMqttDeliveryToken token) {

            }
        });

        Toolbar toolbar = (Toolbar) findViewById(R.id.app_bar);
        toolbar.setTitle("Sala");
        toolbar.setTitleTextColor(getResources().getColor(R.color.corDoTexto));
        setSupportActionBar(toolbar);

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    Intent intent;

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.cadastra_placa:
                intent = new Intent(getApplicationContext(), CadastrarPlaca.class);
                startActivity(intent);
                break;
            case R.id.listar_placas:
                intent = new Intent(getApplicationContext(), ListaDePlacas.class);
                startActivity(intent);
                break;
            default:
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    public void acender(View view) {
        if (aSwitch.isChecked()) {
            mqtt.publica("/casa/luz", "1");
        } else {
            mqtt.publica("/casa/luz", "0");
        }
    }

    public void security(View view) {
        if (seguranca.isChecked()) {
            mqtt.publica("/casa/presenca", "6");
        } else {
            mqtt.publica("/casa/presenca", "7");
        }
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

    public void tempG(final float temperatura) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                while (pStatus < temperatura) {
                    pStatus += 1;

                    handler.post(new Runnable() {

                        @Override
                        public void run() {
                            temp.setProgress(pStatus);
                            progresso.setText(pStatus + "ºC");

                        }
                    });
                    try {
                        Thread.sleep(200);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        }).start();
    }

}
