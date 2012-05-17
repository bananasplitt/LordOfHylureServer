/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package lordofhylureserver;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Vector;

/**
 *
 * @author moritzewert
 */
public class Server implements Runnable {
    
    public ServerSocket server;
    public Thread listener;
    public Vector connections = new Vector();
    public GlobalChat gchat;
    
    public Server(int port) {
        try {
            server = new ServerSocket(port);
            listener = new Thread(this);
            listener.start();
        } catch(IOException e) {
            e.printStackTrace();
            System.err.println(e);
        }
        gchat = new GlobalChat(this);
    }

    @Override
    public void run() {
        try {
            while(true) {
                Socket client = server.accept();
                ClientActions CA = new ClientActions(this, client);
                CA.start();
                connections.addElement(CA);
            }
        } catch(IOException e) {
            e.printStackTrace();
            System.err.println(e);
        }
    }
}
