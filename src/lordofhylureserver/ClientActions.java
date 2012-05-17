/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package lordofhylureserver;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;

/**
 *
 * @author moritzewert
 */
public class ClientActions extends Thread {
    
    public Socket s;
    public OutputStream out;
    public InputStream in;
    public Server server;
    
    public ClientActions(Server server, Socket s) throws IOException {
        this.s = s;
        out = s.getOutputStream();
        in = s.getInputStream();
        this.server = server;
    }
    
    @Override
    public void run() {
        byte[] buffer = new byte[1024];
        int num;
        String nachricht;
        try {
            while((num = in.read(buffer)) != -1) {
                nachricht = new String(buffer);
                System.out.println(nachricht);
                doAction(nachricht);
            }
        } catch(IOException e) {
            e.printStackTrace();
            System.err.println(e);
        }
    }
    
    public void sendChat(String nachricht) throws IOException {
        out.write(("#!gchat!#"+ nachricht).getBytes());
    }
    
    private void doAction(String nachricht) throws IOException {
        if(nachricht.indexOf("#!gchat!#") != -1) {
            String chatMsg = nachricht.substring(nachricht.indexOf("#!gchat!#") +1, nachricht.length());
            sendChat(chatMsg);
        } else if(nachricht.indexOf("#!auth!#") != -1) {
            //String zum Authentifizieren in der Syntax #!auth!#u:<user>#p:<passwort>
            String user = nachricht.substring(nachricht.indexOf("#!auth!#u:") +1, nachricht.indexOf("#p:") -1);
            String pass = nachricht.substring(nachricht.indexOf("#p:") +1, nachricht.length());
        } else if(nachricht.indexOf("#!pchat!#") != -1) {
            //String zum senden an bestimmten User in der Syntax #!pchat!#u:<user>#m:<msg>
            String user = nachricht.substring(nachricht.indexOf("#!auth!#u:") +1, nachricht.indexOf("#m:") -1);
            String msg = nachricht.substring(nachricht.indexOf("#m:") +1, nachricht.length());
        } else if(nachricht.indexOf("#!charname!#") != -1) {
            //Anfrage des Client nach dem Charname zu dem eingeloggten User
        } else if(nachricht.indexOf("#!ep!#") != -1) {
            //Anfrage des Client nach den Ep zu dem eingeloggten User
        } else if(nachricht.indexOf("#!volk!#") != -1) {
            //Anfrage des Clients nach dem Volk zu dem eingeloggten User
        } else if(nachricht.indexOf("#!beruf!#") != -1) {
            //Anfrage nach dem Beruf
        } else if(nachricht.indexOf("#!geld!#") != -1) {
            //Anfrage nach dem Geld
        }
   }
}
