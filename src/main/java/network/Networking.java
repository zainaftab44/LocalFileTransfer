/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package network;

import Globals.Constants;
import java.io.IOException;
import static java.lang.System.out;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Enumeration;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.stream.IntStream;
import javax.swing.JLabel;

/**
 *
 * @author aftabzai
 */
public class Networking {

    ArrayList<String> total_devices;
    Client client;

    public Networking() {
        total_devices = new ArrayList<>();
        client = new Client();
    }

    /**
     * *
     * Scan local network for available devices with app running Steps: 1. Scans
     * reachable devices available in network 2. Checks if application is
     * running on the reachable devices
     *
     * @param available_devices
     */
    public void scanDevices(JLabel available_devices) {
//        ArrayList<String> devices = new ArrayList<String>();
        InetAddress inetAddress = null;
        try {
            try (final DatagramSocket socket = new DatagramSocket()) {
                socket.connect(InetAddress.getByName("8.8.8.8"), 10002);
                inetAddress = socket.getLocalAddress();
            }
            if (inetAddress != null) {
                String subnet = inetAddress.getHostAddress().substring(0, inetAddress.getHostAddress().lastIndexOf('.'));
                IntStream.rangeClosed(0, 255).parallel().forEach(i -> {
                    String host = subnet + "." + i;
                    try {
                        if (InetAddress.getByName(host).isReachable(Constants.TIMEOUT)) {
                            System.out.println(host + " is reachable");
                            for (int j = 0; j < total_devices.size(); j++) {
                                if (total_devices.get(j).equals(host)) {
                                    if (!client.checkIpHasApplication(host)) {
                                        total_devices.remove(j);
                                    } else {
                                        return;
                                    }
                                }
                            }
                            if (client.checkIpHasApplication(host)) {
                                total_devices.add(host);
                            }
                        } else {
                            for (int j = 0; j < total_devices.size(); j++) {
                                if (total_devices.get(j).equals(host)) {
                                    total_devices.remove(j);
                                }
                            }
                        }
                        available_devices.setText("Devices on network: " + total_devices.size());
                    } catch (Exception ex) {
//                        System.out.println(host + " is unreachable");
                    }
                });

            }

        } catch (Exception ex) {
            Logger.getLogger(Networking.class.getName()).log(Level.SEVERE, null, ex);
        }
        available_devices.setText("Devices on network: " + total_devices.size());

//        total_devices = devices;
    }

    /**
     * *
     * Prints IP address to Label
     *
     * @param ip_address - label to display ip address in.
     */
    public void printSelfIpAddress(JLabel ip_address) {
        InetAddress inetAddress = null;
        String ip = "127.0.0.1";
        try (final DatagramSocket socket = new DatagramSocket()) {
            socket.connect(InetAddress.getByName("8.8.8.8"), 10002);
            inetAddress = socket.getLocalAddress();
            ip = inetAddress.getHostAddress();
        } catch (Exception ex) {
            ex.printStackTrace();
        }

        ip_address.setText("IP Address: " + ip);
    }

    
    public boolean isInternetAvailable() {
        InetAddress inetAddress = null;
        try {
            try (final DatagramSocket socket = new DatagramSocket()) {
                socket.connect(InetAddress.getByName("8.8.8.8"), 10002);
                inetAddress = socket.getLocalAddress();
            }
        } catch (Exception ex) {
            Logger.getLogger(Networking.class.getName()).log(Level.SEVERE, null, ex);
        }
        System.out.println(inetAddress.getHostAddress());
        return (inetAddress == null) ? false : true;
    }

}
