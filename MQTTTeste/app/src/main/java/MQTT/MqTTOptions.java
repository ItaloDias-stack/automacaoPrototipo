package MQTT;

import android.content.Context;
import android.widget.Toast;

import com.example.a20151094010099.mqttteste.Tela1;

import org.eclipse.paho.android.service.MqttAndroidClient;
import org.eclipse.paho.client.mqttv3.IMqttActionListener;
import org.eclipse.paho.client.mqttv3.IMqttToken;
import org.eclipse.paho.client.mqttv3.MqttClient;
import org.eclipse.paho.client.mqttv3.MqttConnectOptions;
import org.eclipse.paho.client.mqttv3.MqttException;

public class MqTTOptions {

    static String MQTTSERVER = "tcp://m14.cloudmqtt.com:16962";
    static String MQTTUSER = "placa1";
    static String MQTTSENHA = "123";
    MqttAndroidClient client;

    public MqttAndroidClient conect(final Context context) {

        String clientId = MqttClient.generateClientId();
        client = new MqttAndroidClient(context, MQTTSERVER, clientId);

        MqttConnectOptions options = new MqttConnectOptions();
        options.setUserName(MQTTUSER);
        options.setPassword(MQTTSENHA.toCharArray());

        try {
            IMqttToken token = client.connect(options);
            token.setActionCallback(new IMqttActionListener() {
                @Override
                public void onSuccess(IMqttToken asyncActionToken) {
                    // We are connected
                    subscription();
                    Toast.makeText(context, "Conectado", Toast.LENGTH_SHORT).show();
                }

                @Override
                public void onFailure(IMqttToken asyncActionToken, Throwable exception) {

                }
            });
        } catch (MqttException e) {
            e.printStackTrace();
        }
        return client;
    }

    public void publica(String topico, String message) {
        try {
            client.publish(topico, message.getBytes(), 0, false);
        } catch (MqttException e) {
            e.printStackTrace();
        }
    }

    public void subscription() {
        try {
            client.subscribe("/casa/luz", 0);
            client.subscribe("/casa/presenca", 0);
            client.subscribe("/casa/fogo", 0);
            client.subscribe("/casa/chuva", 0);
            client.subscribe("/casa/temp", 0);
        } catch (MqttException e) {
            e.printStackTrace();
        }
    }
}
