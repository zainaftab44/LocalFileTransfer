/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package network;

import Globals.Constants;
import Globals.Variables;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author aftabzai
 */
public class CommandsManager {

    Socket connection;

    public CommandsManager(Socket connect) {
        connection = connect;
    }

    void start() {
        DataInputStream dis = null;
        try {
            dis = new DataInputStream(connection.getInputStream());
//            DataOutputStream dos =new DataOutputStream(connection.getOutputStream());

            String cmd = dis.readUTF();

            switch (cmd) {
                case Constants.CMD_INFO:
                    //Placeholder check
                    break;
                case Constants.CMD_CONNECT:
                    //Connection Establishment in this check
                    if (dis.readUTF().equals(Variables.code)) {
                        DataOutputStream dos = new DataOutputStream(connection.getOutputStream());
                        dos.writeUTF(Constants.ACKNOWLEDGE_CONNECTION);
                        
                        dos.close();
                    }
                    break;
                                      
            }

        } catch (Exception ex) {

        } finally {
            try {
                dis.close();
                connection.close();
            } catch (Exception ex) {
                Logger.getLogger(CommandsManager.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

}
