
package info.justingrimes.mqttlibrary;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.eclipse.paho.mqttv5.client.MqttClient;
import org.eclipse.paho.mqttv5.client.MqttConnectionOptions;
import org.eclipse.paho.mqttv5.client.MqttConnectionOptionsBuilder;
import org.eclipse.paho.mqttv5.common.MqttException;


public class MqttConnection {
    private static final Logger log = LogManager.getLogger(MqttConnection.class);
    private static final boolean RETAIN_MESSAGES = true;
    private static final int QOS_LEVEL = 0;
    private final MqttClient client;
    private final MqttConnectionOptions connOptions = new MqttConnectionOptionsBuilder()
            .cleanStart(true)
            .build();

    public MqttConnection(String serverAddress, String clientID) throws MqttException {
        try {
            log.info("Creating a MqttClient");
            client = new MqttClient(serverAddress, clientID, null);
        } catch (MqttException e) {
            log.error(e.getMessage());
            throw e;

        }
    }

    public void sendMessage(String message, String topic) {
        try {
            log.info("Attempting to send message using server: {}, clientId: {}",
                    client.getCurrentServerURI(),
                    client.getClientId());
            client.connect(connOptions);
            client.publish(topic,
                    message.getBytes(),
                    QOS_LEVEL,
                    RETAIN_MESSAGES);
            client.disconnect();
        } catch (MqttException e) {
            log.error("Message send failure");
            log.error(e.getMessage());
        }

    }
}
