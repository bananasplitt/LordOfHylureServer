/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package lordofhylureserver;

import java.io.IOException;

/**
 *
 * @author moritzewert
 */
public class GlobalChat {
    
    public Server server;
    
    public GlobalChat(Server server) {
        this.server = server;
    }
    
    public synchronized void send(String message) {
        for( int i=0; i < server.connections.size(); i++) {
            try {
                ((ClientActions)server.connections.elementAt(i)).sendChat(message);
            } catch (IOException e) {
                e.printStackTrace();
                System.err.println(e);
            }
        }
    }
}