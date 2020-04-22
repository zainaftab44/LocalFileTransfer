/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package network;

import Globals.Constants;
import java.net.InetSocketAddress;
import java.net.Socket;

/**
 *
 * @author aftabzai
 */
public class Client {

    Socket connection;

    public Client() {
    }

    /**
     * *
     * Checks if the device on network has application open
     *
     * @param ip - address of the local device
     */
    public boolean checkIpHasApplication(String ip) {
        connection = new Socket();
        try {
            connection.connect(new InetSocketAddress(ip, Constants.PORT), Constants.TIMEOUT);
            connection.close();
            return true;
        } catch (Exception ex) {
            System.out.println(ip + " is not running the app.");
//            ex.printStackTrace();
        }
        return false;
    }

}
