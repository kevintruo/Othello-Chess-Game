/*
  File Name: Othello.java
  Authors: Kevin Truong, 040937076 - Dante Beltran, 040921470
  Course: CST8221 - JAP, Lab Section: 311
  Assignment: 1-2
  Date: October 16th, 2020
  Professor: Daniel Cormier, Karan Kalsi
  Purpose: The purpose of the Othello class is to launch
  the program. It is responsible for creating and launching my splash screen.
  It will then instantiate OtthelloViewController and use it to complete Frame.
 */
package othello;

import java.awt.*;

/**
 * @author Kevin Truong
 * @author Dante Beltran
 * @version 1.0.0
 * @see java.awt.EventQueue
 * @since 1.8.0_261
 */
public class Othello {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        //Create the splash screen
        OthelloSplashScreen splashWindow = new OthelloSplashScreen();

        //Show the Splash screen
        splashWindow.showSplashWindow();

        //Create and display the main application GUI
        EventQueue.invokeLater(() -> new OthelloViewController("Kevin & Dante's Othello Client"));
    }
}
