package com.tom.mongoDB;

import com.mongodb.MongoClient;
import com.mongodb.ServerAddress;
import com.mongodb.client.MongoDatabase;
import com.tom.AppSharedData.AppSharedData;
import java.io.IOException;
import java.net.Socket;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;

public class MongoServerInstance {

    public static MongoDatabase mongoDatabase;

    private MongoServerInstance() {
        // TODO - implement MongoServerInstance.MongoServerInstance
        throw new UnsupportedOperationException();
    }

    public static MongoDatabase getInstance() {
        System.out.println("Checking if MongoDB running?");
        if (mongoDatabase == null) {
            try {
                ServerAddress serverAddress = new ServerAddress(AppSharedData.serverHost, AppSharedData.port);
                Socket socket = new Socket(AppSharedData.serverHost, AppSharedData.port);
                if (socket.getInetAddress() != null) {
                    System.out.println("inetAddress- " + socket.getInetAddress().getHostAddress());
                        MongoClient mongoClient = new MongoClient(serverAddress);//specify ip and port,
                        mongoDatabase = mongoClient.getDatabase("test");
                        System.out.println("Server Started \n System now Connected to MongoDB at IP: " + AppSharedData.serverHost);
                        System.out.println(mongoDatabase.getName());
                        AppSharedData.MongoRunning = true;
                }
            } catch (IOException ex) {
                System.out.println(ex);
                JOptionPane.showMessageDialog(null, "Unable to start the server, please check connection settings!!");
                Logger.getLogger(MongoServerInstance.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        return mongoDatabase;
    }

}
