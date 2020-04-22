/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package main;

import mdlaf.*;
import mdlaf.animation.*;
import javax.swing.*;
import java.awt.*;
import java.util.TimerTask;
import mdlaf.utils.*;
import mdlaf.themes.*;
import network.Server;

/**
 *
 * @author aftabzai
 */
public class Main {

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Create and display the form */
        try {
            UIManager.setLookAndFeel(new MaterialLookAndFeel());
            MaterialLookAndFeel.changeTheme(new MaterialOceanicTheme());
        } catch (UnsupportedLookAndFeelException e) {
            e.printStackTrace();
        }
        new transfer_main().setVisible(true);

        java.util.Timer timer = new java.util.Timer();
        int begin = 0;
        int timeInterval = 5000;

        timer.scheduleAtFixedRate(new TimerTask() {
            @Override
            public void run() {
                //call the method
                new Server().start();
            }
        }, begin, timeInterval);
    }

}
