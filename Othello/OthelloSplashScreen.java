/*
  File Name: OthelloSplashScreen.java
  Authors: Kevin Truong, 040937076 - Dante Beltran, 040921470
  Course: CST8221 - JAP, Lab Section: 311
  Assignment: 1
  Date: October 16th, 2020
  Professor: Daniel Cormier, Karan Kalsi
  Purpose: The purpose of the OthelloSplashScreen is to create a splash screen window and display it for x seconds.
 */
package othello;

import java.awt.*;
import javax.swing.*;
import java.util.Timer;
import java.util.TimerTask;
import javax.swing.plaf.ProgressBarUI;
import javax.swing.plaf.metal.MetalProgressBarUI;

/**
 * @author Kevin Truong
 * @author Dante Beltran
 * @version 1.0.0
 * @see othello, java.awt.*, javax.swing.*, java.util.Timer, java.util.TimerTask
 * @since 1.8.0_261
 */
public class OthelloSplashScreen extends JWindow {

    /**
     * Swing components are serializable and require serialVersionUID
     * @value serialVersionUID
     */
    private static final long serialVersionUID = 6248477390124803341L;

    /**
     * Timer duration
     */
    private int timerDuration = 10;

    /**
     * Progress bar
     */
    private JProgressBar progressBar;

    /**
     * Progress bar minimum value
     */
    private final static int PB_MIN = 0;

    /**
     * Progress bar maximum value
     */
    private final static int PB_MAX = 1000;

    private final static Color color = new Color(19, 8, 49);

    /**
     * Shows a splash screen in the center of the desktop for the amount of time
     * given in the constructor.
     */
    public void showSplashWindow() {
        //Create content pane
        JPanel content = new JPanel(new BorderLayout());
        content.setBackground(Color.WHITE);

        //Set the window's bounds, position the window in the center of the screen
        int width = 400;
        int height = 600;
        Dimension screen = Toolkit.getDefaultToolkit().getScreenSize();
        int x = (screen.width - width) / 2;
        int y = (screen.height - height) / 2;

        //set the location and the size of the window
        setBounds(x, y, width, height);

        //Setting labels for the splash screen
        JLabel label = new JLabel(new ImageIcon(getClass().getResource("astronaut.gif")));
        JLabel demo = new JLabel("Setting up an 8x8 board...", JLabel.CENTER);
        JLabel title = new JLabel("Kevin & Dante's Othello Client", JLabel.CENTER);

        //Creating new progress bar
        progressBar = new JProgressBar();
        ProgressBarUI ui = new MetalProgressBarUI(){
            /**
             * The "selectionForeground" is the color of the text when it is painted
             * over a filled area of the progress bar.
             */
            @Override
            protected Color getSelectionForeground() {
                return Color.BLACK;
            }
            /**
             * The "selectionBackground" is the color of the text when it is painted
             * over an unfilled area of the progress bar.
             */
            @Override
            protected Color getSelectionBackground(){
                return Color.WHITE;
            }
        };
        progressBar.setUI(ui);
        progressBar.setMinimum(PB_MIN);
        progressBar.setMaximum(PB_MAX);
        progressBar.setStringPainted(true);
        progressBar.setBorderPainted(false);
        progressBar.setBackground(color);
        progressBar.setForeground(Color.WHITE);
        progressBar.setBorder(BorderFactory.createEmptyBorder(5,100,5,100));

        //Create a new timer and a timer task to change the text of the label every x seconds
        Timer timer = new Timer();
        TimerTask task = new TimerTask() {
            @Override
            public void run() {
                //Once every task succeeds every x second, minus one to the timer duration
                timerDuration--;
                switch (timerDuration) {
                    case 0: //If timer duration is 0, stop the timer
                        timer.cancel();
                        timer.purge();
                        break;
                    case 9:
                        demo.setText("Polishing new disks...");
                        break;
                    case 7:
                        demo.setText("Getting things ready...");
                        break;
                    case 4:
                        demo.setText("Finding an opponent...");
                        break;
                    case 3:
                        demo.setText("A worthy opponent has been found");
                        break;
                    case 2:
                        demo.setText("Prepare for battle");
                        break;
                }
            }
        };
        timer.scheduleAtFixedRate(task, 1000, 1000); //Start the timer and execute task every 1000ms and last 1000ms(task, delay, period)

        //Bottom panel to add label text and progress bar
        JPanel bottomPnl = new JPanel(new BorderLayout());

        demo.setOpaque(true);
        demo.setBackground(color);
        demo.setFont(new Font("Comic Sans MS", Font.ITALIC, 18));
        demo.setForeground(Color.WHITE);
        demo.setBorder(BorderFactory.createEmptyBorder(5,5,5,5));
        title.setOpaque(true);
        title.setBackground(color);
        title.setFont(new Font("Comic Sans MS", Font.ITALIC, 24));
        title.setForeground(Color.WHITE);
        content.add(title, BorderLayout.NORTH);
        content.add(label, BorderLayout.CENTER);
        bottomPnl.setBackground(color);
        bottomPnl.add(demo, BorderLayout.SOUTH);
        bottomPnl.add(progressBar, BorderLayout.NORTH);
        content.add(bottomPnl, BorderLayout.SOUTH);
        content.setBorder(BorderFactory.createLineBorder(Color.BLACK, 2));

        //Set the cursor to the wait cursor
        setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));

        //replace the window content pane with the content JPanel
        content.setBackground(color);
        setContentPane(content);

        //make the splash window visible
        setVisible(true);

        //Snooze for awhile, pretending the code is loading something awesome while
        //our splashscreen is entertaining the user.
        // this is the loop the progress of which is tracked
        for (int i = PB_MIN; i <= PB_MAX; i += 100) {
            final int percent=i;
            //The lines below are required to keep the GUI responsive to the user
            //It will execute the code within the event dispatch thread
            try {
                SwingUtilities.invokeLater(() -> progressBar.setValue(percent));
                //make the program inactive for a while so that the GUI thread can do its work
                Thread.sleep(100);
            } catch (InterruptedException ignored) {
            }
        }

        //destroy the window and release all resources
        dispose();
    }
}
