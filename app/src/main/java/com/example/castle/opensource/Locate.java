package com.example.castle.opensource;


import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;

/**
 * Created by castle on 3/26/2017.
 */

public class Locate {

    public void sendToMessageBroker(final double l, final double lon) {
        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    System.out.println("Send to messagebroker thread opened");
                    ConnectionFactory factory = new ConnectionFactory();
                    factory.setUri("amqp://quksnfgk:yzAXZ5GkPsV4MU8cOdklWW6JFTEy54fD@clam.rmq.cloudamqp.com/quksnfgk");
                    Connection conn = factory.newConnection();
                    Channel ch = conn.createChannel();
                    ch.confirmSelect();
                    ch.basicPublish("logs", "", null,(l + ", " + lon).getBytes());
                    ch.waitForConfirmsOrDie();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
        thread.start();

    }
}
