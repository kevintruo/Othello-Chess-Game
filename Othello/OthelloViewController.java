/*
  File name: OthelloViewController.java
  Author(s): Kevin Truong, 040937076 - Dante Beltran, 040921470
  Course: CST8221 - JAP, Lab Section: 311
  Assignment: 1
  Date: October 16th, 2020
  Professor: Daniel Cormier, Karan Kalsi
  Purpose: The purpose of this file is to create a controller class to create the GUIs for Othello game
  Class list: OthelloViewController, buttonController, menuItemController, mouseController
 */
package othello;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.*;
import java.net.*;
import java.util.Scanner;

/**
 * @author Kevin Truong, Dante Beltran
 * @version 1.0.0
 * @see othello
 * @see java awt *
 * @since 1.8.0_261
 */
public class OthelloViewController extends JFrame {

    /*
     * Left panel encapsulates the smaller panels for the tiles. The left panel
     * has a GridLayout 9x9 structure
     *
     * @value leftPnl
     */
    private final JPanel leftPnl;

    /*
     * Text field that will go to into bottom panel
     */
    private final JTextField txtField;

    /*
     * Pink zone panel that will contain the labels (player's move history)
     *
     * @value pinkZone
     */
    private final JTextArea pinkZone = new JTextArea();

    /*
     * A panel that will contain 2 labels of each player's number of chess
     * pieces on the board
     *
     * @value playersPieces
     */
    private final JPanel playersDetails;

    /*
     * A check box that will show valid moves that will go to the right panel
     *
     * @value checkBox
     */
    private final JCheckBox checkBox;

    /*
     * A 2-D array of JPanel (9x9) that will be encapsulated by the left panel
     * and will contain a label in each of the panel
     *
     * @value chessBoardSquares
     */
    private final JPanel[][] chessBoardSquares;

    /*
     * A 2-D array of JLabel(8x8)
     *
     * @value labels
     */
    private final JLabel[][] labels;

    /*
     * A white chess piece icon
     *
     * @value whiteS
     */
    private final ImageIcon whiteS = new ImageIcon(getClass().getResource("white_s.png"));

    /*
     * A black chess piece icon
     *
     * @value blackS
     */
    private final ImageIcon blackS = new ImageIcon(getClass().getResource("black_s.png"));

    /*
     * A bat chess piece icon
     *
     * @value batS
     */
    private final ImageIcon batS = new ImageIcon(getClass().getResource("bat.png"));

    /*
     * A pumpkin chess piece icon
     *
     * @value pumpkinS
     */
    private final ImageIcon pumpkinS = new ImageIcon(getClass().getResource("pumpkin.png"));

    /*
     * A dog chess piece icon
     *
     * @value dogS
     */
    private final ImageIcon dogS = new ImageIcon(getClass().getResource("dog.png"));

    /*
     * A cat chess piece icon
     *
     * @value catS
     */
    private final ImageIcon catS = new ImageIcon(getClass().getResource("cat.png"));

    /*
     * Player one's icon
     */
    private ImageIcon playerOneIcon = blackS; //Default value

    /*
     * Player two's icon
     */
    private ImageIcon playerTwoIcon = whiteS; //Default value

    /*
     * Check mark
     */
    private final ImageIcon checkMark = new ImageIcon(getClass().getResource("checkMark.png"));

    /*
     * Default dimension for tiles (or buttons) 60x60 pixels
     *
     * @value def
     */
    private final Dimension def = new Dimension(60, 60);

    /*
     * Create a event handler class
     *
     * @value handler
     */
    private final buttonController handler = new buttonController();


    /*
     * Normal pieces menu item
     */
    private final JRadioButtonMenuItem normalReskin = new JRadioButtonMenuItem("Normal Pieces (black and white)");

    /*
     * Cats vs. Dogs
     */
    private final JRadioButtonMenuItem petReskin = new JRadioButtonMenuItem("Cats vs. Dogs");

    /*
     * Pumpkins vs. Bats
     */
    private final JRadioButtonMenuItem spookyReskin = new JRadioButtonMenuItem("Pumpkins vs. Bats");

    /*
     * Debug normal game
     */
    private final JRadioButtonMenuItem normalDebug = new JRadioButtonMenuItem("Normal Game");

    /*
     * Debug corner game
     */
    private final JRadioButtonMenuItem cornerDebug = new JRadioButtonMenuItem("Corner Test");

    /*
     * Debug side game
     */
    private final JRadioButtonMenuItem sideDebug = new JRadioButtonMenuItem("Side Tests");

    /*
     * Debug 1x capture game
     */
    private final JRadioButtonMenuItem X1Debug = new JRadioButtonMenuItem("1X Capture Test");

    /*
     * Debug 2x capture game
     */
    private final JRadioButtonMenuItem X2Debug = new JRadioButtonMenuItem("2X Capture Test");

    /*
     * Debug empty board game
     */
    private final JRadioButtonMenuItem emptyDebug = new JRadioButtonMenuItem("Empty Board");

    /*
     * Debug inner square game
     */
    private final JRadioButtonMenuItem innerDebug = new JRadioButtonMenuItem("Inner Square Test");

    /*
     * Othello model instance
     */
    private final OthelloModel model = new OthelloModel();

    /*
     * Player's turn. Default value is 1 (BLACK)
     */
    private int turn = 1;

    /*
     * New Connection Menu Tab
     */
    JMenuItem newConnectionMenu = new JMenuItem("New Connection");

    JMenuItem disconnectMenu = new JMenuItem("Disconnect");

    JButton submitBtn = new JButton("Submit");

    OthelloNetworkModalViewController networkDialog = new OthelloNetworkModalViewController(this);

    OthelloNetworkController connection = new OthelloNetworkController();

    /**
     * Initial constructor to initialize the GUIs
     *
     * @param clientName Client Name
     */
    public OthelloViewController(String clientName) {

        super(clientName);

        //Create a main panel that will encapsulate all components
        /*
         * Main panel encapsulates all other panels. The main panel has a
         * BorderLayout structure
         *
         * @value mainPnl
         */
        JPanel mainPnl = new JPanel(new BorderLayout());
        //Create gray border lines around the main panel that are 5 pixels wide
        //mainPnl.setBorder(BorderFactory.createLineBorder(Color.GRAY, 5));
        mainPnl.setBorder(BorderFactory.createMatteBorder(0, 0, 5, 0, Color.GRAY));

        //Start of the left panel
        //Create a left panel that will contain the chessboard and some buttons
        leftPnl = new JPanel(new GridLayout(9, 9));
        //Create a gray border line: 5 pixels wide for the bottom side and 2 pixels wide for the right side
        leftPnl.setBorder(BorderFactory.createMatteBorder(5, 5, 5, 2, Color.GRAY));
        //Create a 2D array JPanel 9x9
        chessBoardSquares = new JPanel[9][9];
        labels = new JLabel[8][8];
        initializeBoard(false, false);
        //Add the left panel to the main panel in the center position
        mainPnl.add(leftPnl, BorderLayout.CENTER);

        //Create and initialize right panel with border layout structure
        /*
         * Right panel encapsulates the check box, pink zone and player's chess
         * pieces count. The right panel has a BorderLayout structure
         *
         * @value rightPnl
         */
        JPanel rightPnl = new JPanel(new BorderLayout());
        //Create gray border lines for left and bottom sides that are 5 pixels wide
        rightPnl.setBorder(BorderFactory.createMatteBorder(5, 5, 5, 5, Color.GRAY));
        //Create a new check box and set its to uncheck (false)
        checkBox = new JCheckBox("Show Valid Moves", false);
        //Set the alignment of the check box to the left
        checkBox.setAlignmentX(Component.LEFT_ALIGNMENT);
        //Add an action listener to print in console whenever the check box is checked or unchecked
        checkBox.addActionListener((ActionEvent e) -> {
            if (checkBox.isSelected())
                checkMark();
            else
                unCheckMark();
        });
        //Add the newly created check box to the North (top) side of the border layout
        rightPnl.add(checkBox, BorderLayout.NORTH);

        //Set editable to false
        pinkZone.setEditable(false);
        //Set the background of the panel to pink
        pinkZone.setBackground(Color.PINK);
        //Set margin
        pinkZone.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));
        pinkZone.setLineWrap(true);
        pinkZone.setWrapStyleWord(true);
        JScrollPane scrollPane = new JScrollPane(pinkZone, JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED, JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        scrollPane.setBorder(BorderFactory.createMatteBorder(5, 0, 5, 0, Color.GRAY));
        //Set the default size of the pink zone panel to 450x425 pixels
        //Note: I got this number from the swing screenshot. I used MS 3D Paint to measure the size of the pink zone area
        scrollPane.setPreferredSize(new Dimension(450, 425));
        //Add the pink zone panel to the right panel in the center position
        rightPnl.add(scrollPane, BorderLayout.CENTER);

        //Initialized players details panel with grid layout structure 2x2
        playersDetails = new JPanel(new GridLayout(0, 2));
        //Create an empty border (padding) for all sides that are 5 pixels wide
        playersDetails.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));

        //Initialize player label
        initializePlayersDetails();

        //Add players details panel to right panel in the south position (bottom)
        rightPnl.add(playersDetails, BorderLayout.SOUTH);
        //Add right panel to the main panel in the EAST position (right)
        mainPnl.add(rightPnl, BorderLayout.EAST);

        //Initialize bottom panel
        /*
         * Bottom panel contains a text field that will request focus when the
         * window first opens and a submit button
         *
         * @value bottomPnl
         */
        JPanel bottomPnl = new JPanel(new BorderLayout());
        //Set border for bottom panel
        bottomPnl.setBorder(BorderFactory.createMatteBorder(0, 5, 0, 5, Color.GRAY));
        //Initialize the text field and set its margin to 1 for all sides
        txtField = new JTextField();
        txtField.setMargin(new Insets(1, 1, 1, 1));
        //Request focus for text field when window first opens
        addWindowListener(new WindowAdapter() {
            @Override
            public void windowOpened(WindowEvent we) {
                txtField.requestFocus();
            }
        });
        //Add text field to the bottom panel in the center position
        bottomPnl.add(txtField, BorderLayout.CENTER);
        //Initialize submit button using createButton() method
        /*
         * Submit button that will go to into bottom panel
         *
         * @value submitBtn
         */
        submitBtn.setActionCommand("Submit");
        submitBtn.setForeground(Color.RED);
        submitBtn.setBackground(Color.BLACK);
        submitBtn.setEnabled(false);
        submitBtn.addActionListener(e -> {
            try {
                connection.comm(txtField.getText());
            } catch (IOException ioException) {
                ioException.printStackTrace();
            }
        });
        //submitBtn = createButton("Submit", "Submit", Color.RED, Color.BLACK, handler);
        //Set preferred size for the submit button to 50x19 pixels
        //Note: I got this number by using MS 3D paint to get the measurement
        submitBtn.setPreferredSize(new Dimension(50, 19));
        //Set the margin to 1 for all sides
        submitBtn.setMargin(new Insets(1, 1, 1, 1));
        //Add the submit button to the bottom panel in the east position
        bottomPnl.add(submitBtn, BorderLayout.EAST);
        //Add the bottom panel to the main panel in the south position (bottom)
        mainPnl.add(bottomPnl, BorderLayout.SOUTH);

        //Adding menu items to FILE menu
        /*
         * File menu
         */
        JMenu fileMenu = new JMenu("File");
        /*
         * New game menu
         */
        JMenuItem newGame = new JMenuItem("New Game");
        fileMenu.add(newGame);
        /*
         * Load menu
         */
        JMenuItem loadGame = new JMenuItem("Load");
        fileMenu.add(loadGame);
        loadGame.setEnabled(false);
        /*
         * Load menu
         */
        JMenuItem saveGame = new JMenuItem("Save");
        fileMenu.add(saveGame);
        saveGame.setEnabled(false);
        /*
         * Exit menu
         */
        JMenuItem exitGame = new JMenuItem("Exit");
        fileMenu.add(exitGame);
        /*
         * Create an event handler class
         *
         * @value skinHandler
         */
        menuItemController menuHandler = new menuItemController();
        newGame.addActionListener(menuHandler);
        exitGame.addActionListener((ActionEvent e) -> handleClosing());

        //Adding radio button menu items to RESKIN PIECES menu and to a button group
        ButtonGroup groupOne = new ButtonGroup();
        /*
         * Reskin pieces menu
         */
        JMenu reskinMenu = new JMenu("Reskin Pieces");
        reskinMenu.add(normalReskin);
        reskinMenu.add(petReskin);
        reskinMenu.add(spookyReskin);
        groupOne.add(normalReskin);
        groupOne.add(petReskin);
        groupOne.add(spookyReskin);

        //Reskin pieces action listeners
        normalReskin.addActionListener(menuHandler);
        normalReskin.setSelected(true);
        spookyReskin.addActionListener(menuHandler);
        petReskin.addActionListener(menuHandler);

        //Adding radio button menu items to DEBUG SCENARIOS menu and to a button group
        ButtonGroup groupTwo = new ButtonGroup();
        /*
         * Debug scenarios menu
         */
        JMenu debugMenu = new JMenu("Debug Scenarios");
        debugMenu.add(normalDebug);
        normalDebug.setSelected(true);
        debugMenu.add(cornerDebug);
        debugMenu.add(sideDebug);
        debugMenu.add(X1Debug);
        debugMenu.add(X2Debug);
        debugMenu.add(emptyDebug);
        debugMenu.add(innerDebug);
        groupTwo.add(normalDebug);
        groupTwo.add(cornerDebug);
        groupTwo.add(sideDebug);
        groupTwo.add(X1Debug);
        groupTwo.add(X2Debug);
        groupTwo.add(emptyDebug);
        groupTwo.add(innerDebug);

        //Adding RESKIN PIECES and DEBUG SCENARIOS to GAME menu
        /*
         * Game menu
         */
        JMenu gameMenu = new JMenu("Game");
        gameMenu.add(reskinMenu);
        gameMenu.add(debugMenu);

        //adding newConnection menu button to network menu
        /*
         * Network Menu
         */
        JMenu networkMenu = new JMenu("Network");
        networkMenu.add(newConnectionMenu);
        //setting an action listener when newConnectionMenu Button is clicked
        newConnectionMenu.addActionListener((ActionEvent avt) -> showNewConnectionDialog());
        //adding disconnect menu button to network menu
        /*
         * Disconnect Menu Tab
         */
        networkMenu.add(disconnectMenu);
        //setting an action listener when disconnectMenu Button is clicked
        disconnectMenu.addActionListener((ActionEvent avt) -> {
            try {
                showDisconnectMessage();
            } catch (IOException e) {
                e.printStackTrace();
            }
        });
        disconnectMenu.setEnabled(false);
        //Adding ABOUT menu item to HELP menu
        /*
         * Help menu
         */
        JMenu helpMenu = new JMenu("Help");
        /*
         * About menu
         */
        JMenuItem aboutMenu = new JMenuItem("About");
        helpMenu.add(aboutMenu);
        aboutMenu.addActionListener((ActionEvent avt) -> showAboutDialog());

        //Adding menus to menu bar
        /*
         * Menu bar
         */
        JMenuBar menuBar = new JMenuBar();
        menuBar.add(fileMenu);
        menuBar.add(gameMenu);
        menuBar.add(networkMenu);
        menuBar.add(helpMenu);

        //Add menu bar at the top position
        mainPnl.add(menuBar, BorderLayout.NORTH);

        //Add main panel to the frame
        add(mainPnl);

        setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
        addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                handleClosing();
            }
        });
        pack();
        setLocationRelativeTo(null);//screen center
        setResizable(false); //frame is set to not be resizable
        setVisible(true);
    }

    /**
     * The purpose of the method is to create a button with passed in parameters
     *
     * @param text    the text of the button
     * @param ac      action command
     * @param bgc     background color
     * @param handler event handler
     * @return newly created button
     */
    private JButton createButton(String text, String ac, Color bgc, buttonController handler) {
        JButton temp = new JButton(text);
        temp.setPreferredSize(def);
        temp.setForeground(Color.BLACK);
        temp.setBackground(bgc);
        temp.setActionCommand(ac);
        temp.addActionListener(handler);
        return temp;
    }

    /**
     * @author Kevin Truong, Dante Beltran
     * @version 1.0.0
     * @see othello, java.awt.*, java.awt.event.*, javax.swing.*;
     * @since 1.8.0_261
     */
    private class buttonController implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            JButton temp = (JButton) e.getSource();
            String action = temp.getActionCommand();
            if (action.equals("move")) {
                int X = findX();
                int Y = findY();
                if (X != -1 && Y != -1) {
                    int move = model.move(X, Y, turn);

                    //If the move is valid
                    if (move > 0) {
                        pinkZone.append("Player " + turn + " capture " + move + " piece(s)\n");

                        if (turn == 1)
                            turn = 2;
                        else
                            turn = 1;

                        if(!model.canMove(1) && !model.canMove(2)){
                            pinkZone.append("END OF GAME\n");
                            pinkZone.append("Player 1 finishes with " + model.getChips(1) + " chip(s).\n");
                            pinkZone.append("Player 2 finishes with " + model.getChips(2) + " chip(s).\n");
                            if(model.getChips(1) > model.getChips(2))
                                pinkZone.append("Player 1 wins\n");
                            else if (model.getChips(1) < model.getChips(2))
                                pinkZone.append("Player 2 wins\n");
                            else
                                pinkZone.append("DRAW\n");
                            pinkZone.append("\n\nPlay again? (Select 'New Game' from the File menu.)\n");
                            leftPnl.removeAll();
                            leftPnl.revalidate();
                            leftPnl.repaint();
                            initializeBoard(checkBox.isSelected(), true);
                            playersDetails.removeAll();
                            playersDetails.revalidate();
                            playersDetails.repaint();
                            initializePlayersDetails();
                            return;
                        }

                        if(!model.canMove(turn)) {
                            pinkZone.append("Player " + turn + " turn\n");
                            pinkZone.append("No valid moves. Please skip\n");
                        } else
                            pinkZone.append("Player " + turn + " turn\n");
                        leftPnl.removeAll();
                        leftPnl.revalidate();
                        leftPnl.repaint();
                        initializeBoard(checkBox.isSelected(), true);
                    }
                    //the move is invalid
                    else
                        pinkZone.append("Player " + turn + ": Invalid move\n");

                    playersDetails.removeAll();
                    playersDetails.revalidate();
                    playersDetails.repaint();
                    initializePlayersDetails();
                }
            }
            else if (action.equals("skip")){
                leftPnl.removeAll();
                leftPnl.revalidate();
                leftPnl.repaint();
                initializeBoard(checkBox.isSelected(), true);
                if (turn == 1)
                    turn = 2;
                else
                    turn = 1;

                pinkZone.append("Player " + turn + " turn\n");
                if(!model.canMove(turn))
                    pinkZone.append("No valid moves. Please skip\n");
                temp.setActionCommand("move");
                temp.setText("move");
                leftPnl.removeAll();
                leftPnl.revalidate();
                leftPnl.repaint();
                initializeBoard(checkBox.isSelected(), true);
                playersDetails.removeAll();
                playersDetails.revalidate();
                playersDetails.repaint();
                initializePlayersDetails();
            }
            else {
                resetColourBtn(action);
                temp.setBackground(Color.WHITE);
            }

        }
    }

    /**
     * @author Kevin Truong, Dante Beltran
     * @version 1.0.0
     * @see othello, java.awt.*, java.awt.event.*, javax.swing.*;
     * @since 1.8.0_261
     */
    private class menuItemController implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {

            //Setting first move to be player 1
            turn = 1;

            //Clearing left panel
            leftPnl.removeAll();
            leftPnl.revalidate();
            leftPnl.repaint();

            //Clearing player detail panel
            playersDetails.removeAll();
            playersDetails.revalidate();
            playersDetails.repaint();

            //Get JMenuItem source
            JMenuItem temp = (JMenuItem) e.getSource();

            //Reinitialize skin based on sources
            if (normalReskin.equals(temp)) {
                playerOneIcon = blackS;
                playerTwoIcon = whiteS;
            } else if (spookyReskin.equals(temp)) {
                playerOneIcon = batS;
                playerTwoIcon = pumpkinS;
            } else if (petReskin.equals(temp)) {
                playerOneIcon = dogS;
                playerTwoIcon = catS;
            }
            initializeBoard(checkBox.isSelected(), false);
            initializePlayersDetails();
        }
    }

    /**
     * The purpose of this method to to handle closing
     */
    private void handleClosing() {
        int answer = showWarningMessage();
        switch (answer) {
            case JOptionPane.YES_OPTION:
                dispose();
                break;

            case JOptionPane.NO_OPTION:
                break;
        }
    }

    /**
     * The purpose of this method is to show warning message
     *
     * @return integer
     */
    private int showWarningMessage() {
        String[] buttonLabels = new String[]{"Yes", "No"};
        String defaultOption = buttonLabels[0];

        return JOptionPane.showOptionDialog(null,
                "Do you want to exit this awesome client?",
                "Warning",
                JOptionPane.YES_NO_OPTION,
                JOptionPane.WARNING_MESSAGE,
                null,
                buttonLabels,
                defaultOption);
    }

    /**
     * The purpose of this method is to create a JDialog to display information
     */
    private void showAboutDialog() {
        JOptionPane.showMessageDialog(null,
                "<html>Othello Game<br/>by Kevin Truong<br/>and Dante Beltran<br/>November 2020</html>",
                "About", JOptionPane.INFORMATION_MESSAGE);
    }

    /**
     * The purpose of this method is to display a message to the pinkzone if the the disconnect button is pressed
     */
    private void showDisconnectMessage() throws IOException {
        connection.closeConnection();
    }

    /**
     * The purpose is to re-initialize board
     */
    private void initializeBoard(boolean isChecked, boolean isMove) {

        //Initialize the scenario (normal should be selected when the app first started
        if (!isMove)
            initializeScenario();

        //Start of the for loop
        for (int i = 0; i < chessBoardSquares.length; i++) {
            for (int j = 0; j < chessBoardSquares[i].length; j++) {
                //In each tile, create a new temporary panel with border layout structure
                JPanel pnl = new JPanel(new BorderLayout());

                //If the panel is supposed to contain label (If i and j are not equal to the last index)
                if (i != chessBoardSquares.length - 1 && j != chessBoardSquares[i].length - 1) {

                    //Create a temporary label
                    JLabel lbl = new JLabel("", JLabel.CENTER);

                    //Setting icon for the label
                    int temp = model.getBoard(i, j);
                    if (temp == 0)
                        lbl.setIcon(null);
                    else if (temp == 1)
                        lbl.setIcon(playerOneIcon);
                    else if (temp == 2)
                        lbl.setIcon(playerTwoIcon);
                    //else do nothing

                    //i is y and j is x
                    if (isChecked)
                        if (model.isValid(i, j, turn))
                            lbl.setIcon(checkMark);

                    //Set opaque to true (this is required to change the background color
                    lbl.setOpaque(true);

                    //Set the default background of the label to black
                    lbl.setBackground(Color.BLACK);

                    //If the background of the label is supposed to be white
                    //Algorithm: The result of the sum of i and j indexes and 2 is not equal to 0
                    if ((i + j) % 2 != 0) //Set the background of the label to white
                    {
                        lbl.setBackground(Color.WHITE);
                    }

                    //Set the default size of the tile is 60x60 pixels
                    lbl.setPreferredSize(def);
                    //Set the label in the labels 2-D array equal to the temporary array
                    labels[i][j] = lbl;
                    //Add the newly created and initialized label to the temporary panel
                    pnl.add(labels[i][j], BorderLayout.CENTER);
                } //If the panel is supposed to contain a button
                else {

                    //Create a button
                    JButton btn = null;

                    //If the button is supposed to be the "move" button, has coordinates at (8,8)
                    if (i == 8 && j == 8) {
                        //Initialize button with createButton() method
                        btn = createButton("move", "move", Color.WHITE, handler);

                        //If the board is empty the text changes to text
                        if (!model.canMove(turn)) {
                            btn.setText("skip");
                            btn.setActionCommand("skip");
                        }

                        if(!model.canMove(1) && !model.canMove(2)){
                            btn.setEnabled(false);
                        }

                        //Set the font to 10pt
                        btn.setFont(new Font("Arial", Font.PLAIN, 10));
                        //Create gray border lines for top and left sides that are 1 pixel wide
                        btn.setBorder(BorderFactory.createMatteBorder(1, 1, 0, 0, Color.GRAY));
                    }

                    //If the button is supposed to have number from 1 to 8
                    else if (j == 8) {
                        //Initialize button with createButton() method
                        btn = createButton(Integer.toString(i + 1), Integer.toString(i + 1), Color.lightGray, handler);
                        //Create gray border lines for left and bottom sides that are 1 pixel wide
                        btn.setBorder(BorderFactory.createMatteBorder(0, 2, 1, 0, Color.GRAY));
                    }

                    //If the button is supposed to have letter to A to H
                    else if (i == 8) {
                        //Initialize button with createButton() method
                        btn = createButton(String.valueOf((char) (j + 65)), String.valueOf((char) (j + 65)), Color.lightGray, handler);
                        //Create gray border lines for top and right sides that are 1 pixel wide
                        btn.setBorder(BorderFactory.createMatteBorder(2, 0, 0, 1, Color.GRAY));
                    }

                    //Add newly created and initialized button to the temporary panel
                    if (btn != null) {
                        pnl.add(btn, BorderLayout.CENTER);
                        pnl.setBackground(Color.LIGHT_GRAY);
                    }
                }

                //Set the JPanel 2D array with i and j indexes equal to the temporary panel
                chessBoardSquares[i][j] = pnl;
                //Add the newly created panel to the left panel
                leftPnl.add(chessBoardSquares[i][j]);
                //Next j
            }
            //Next i
        }
        if (!isMove)
            displayTextArea();
    }

    /**
     * The purpose is to re-initialize players details
     */
    private void initializePlayersDetails() {
        //Initialize all the labels for the panel
        /*
         * Player one title
         *
         * @value playerOneTitle
         */
        JLabel playerOneTitle = new JLabel("Player 1 Pieces:");
        /*
         * Player two title
         *
         * @value playerTwoTitle
         */
        JLabel playerTwoTitle = new JLabel("Player 2 Pieces:");
        playerOneTitle.setOpaque(true);
        playerTwoTitle.setOpaque(true);

        if(turn == 1){
            playerOneTitle.setBackground(Color.RED);
            playerOneTitle.setForeground(Color.WHITE);
            playerTwoTitle.setBackground(null);
            playerTwoTitle.setForeground(Color.BLACK);
        } else if (turn == 2){
            playerTwoTitle.setBackground(Color.BLUE);
            playerTwoTitle.setForeground(Color.WHITE);
            playerOneTitle.setBackground(null);
            playerOneTitle.setForeground(Color.BLACK);
        }

        /*
         * Player one's number of chess pieces
         *
         * @value playerOneCount
         */
        JLabel playerOneCount = new JLabel(Integer.toString(model.getChips(1)), playerOneIcon, JLabel.RIGHT);
        /*
         * Player two's number of chess pieces
         *
         * @value playerTwoCount
         */
        JLabel playerTwoCount = new JLabel(Integer.toString(model.getChips(2)), playerTwoIcon, JLabel.RIGHT);

        //Adding all the labels in the panel
        playersDetails.add(playerOneTitle);
        playersDetails.add(playerOneCount);
        playersDetails.add(playerTwoTitle);
        playersDetails.add(playerTwoCount);
    }

    /**
     * To display the number of chess pieces of player one and two
     */
    private void displayTextArea() {
        pinkZone.setText("Player 1 initialized with " + model.getChips(1) + " piece(s).\n");
        pinkZone.append("Player 2 initialized with " + model.getChips(2) + " piece(s).\n");
        if(model.canMove(turn))
            pinkZone.append("Player 1 turn\n");
    }

    /**
     * The purpose of this method is to initialize the scenario on the board
     */
    private void initializeScenario() {
        if (normalDebug.isSelected())
            model.initialize(OthelloModel.NORMAL);
        else if (cornerDebug.isSelected())
            model.initialize(OthelloModel.CORNER_TEST);
        else if (sideDebug.isSelected())
            model.initialize(OthelloModel.OUTER_TEST);
        else if (X1Debug.isSelected())
            model.initialize(OthelloModel.TEST_CAPTURE);
        else if (X2Debug.isSelected())
            model.initialize(OthelloModel.TEST_CAPTURE2);
        else if (emptyDebug.isSelected())
            model.initialize(OthelloModel.UNWINNABLE);
        else if (innerDebug.isSelected())
            model.initialize(OthelloModel.INNER_TEST);
        else //Default
            model.initialize(OthelloModel.NORMAL);
    }

    /**
     * Reset all colour to light gray of the same row or column
     *
     * @param ac action command
     */
    private void resetColourBtn(String ac) {
        int x = ac.charAt(0);
        for (int i = 0; i < chessBoardSquares.length - 1; i++) {

            if (ac.equals("move")) {
                chessBoardSquares[8][i].getComponent(0).setBackground(Color.LIGHT_GRAY);
                chessBoardSquares[i][8].getComponent(0).setBackground(Color.LIGHT_GRAY);
            }
            //If the button pressed is on the horizontal row
            if (x >= 65)
                chessBoardSquares[8][i].getComponent(0).setBackground(Color.LIGHT_GRAY);
                //If the button pressed is on the vertical column
            else
                chessBoardSquares[i][8].getComponent(0).setBackground(Color.LIGHT_GRAY);
        }
    }

    /**
     * To set check mark on the board
     */
    private void checkMark() {
        for (int i = 0; i < chessBoardSquares.length; i++) {
            for (int j = 0; j < chessBoardSquares[i].length; j++) {
                if (model.isValid(i, j, turn)) {
                    labels[i][j].setIcon(checkMark);
                }
            }
        }
    }

    /**
     * To uncheck mark on the board
     */
    private void unCheckMark() {
        for (int i = 0; i < chessBoardSquares.length; i++) {
            for (int j = 0; j < chessBoardSquares[i].length; j++) {
                if (model.isValid(i, j, turn)) {
                    labels[i][j].setIcon(null);
                }
            }
        }
    }

    /**
     * To find the X coordinate of the selected square
     * @return X coordinate
     */
    private int findX() {
        for (int i = 0; i < chessBoardSquares.length - 1; i++) {
            if (chessBoardSquares[i][8].getComponent(0).getBackground() == Color.WHITE)
                return i;
        }
        return -1;
    }

    /**
     * To find the Y coordinate of the selected square
     * @return Y coordinate
     */
    private int findY() {
        for (int i = 0; i < chessBoardSquares.length - 1; i++) {
            if (chessBoardSquares[8][i].getComponent(0).getBackground() == Color.WHITE)
                return i;
        }
        return -1;
    }

    /**
     * The purpose of this method is to create a modal JDialog set up a new connection
     */
    private void showNewConnectionDialog() {
        Point thisLocation = getLocation();
        Dimension parentSize = getSize();
        Dimension dialogSize = networkDialog.getSize();

        int offsetX = (parentSize.width - dialogSize.width) / 2 + thisLocation.x;
        int offsetY = (parentSize.height - dialogSize.height) / 2 + thisLocation.y;

        networkDialog.setLocation(offsetX, offsetY);
        networkDialog.setVisible(true);
        if(networkDialog.pressedConnect())
            pinkZone.append("Negotiating Connection to " + networkDialog.getAddress() + " on port " + networkDialog.getPort() + "\n");
        else
            return;
        connection.connect(networkDialog.getUserName(), networkDialog.getAddress(), networkDialog.getPort());
        connection.start();
    }

    public class OthelloNetworkController extends Thread{
        private String name;
        private String host;
        private int port;
        private Socket socket;
        private InputStream inputStream;
        private OutputStream outputStream;
        private Scanner scanner;
        private PrintWriter out;

        public void connect(String name, String host, int port){
            try{
                this.name = name;
                this.host = host;
                this.port = port;
                socket = new Socket();
                socket.connect(new InetSocketAddress(InetAddress.getByName(this.host),this.port),10000);
                socket.setSoTimeout(10000);
                socket.setSoLinger(true, 5);
                socket.setTcpNoDelay(true);
                outputStream = socket.getOutputStream();
                out = new PrintWriter(outputStream, true);
            } catch (UnknownHostException uhe){
                pinkZone.append("Error: Unknown host\n");
            } catch (SocketTimeoutException ste){
                pinkZone.append("Error: Connection refused. Check your host and port\n");
            } catch (ConnectException ce){
                pinkZone.append("Error: Could not negotiate a connection\n");
            } catch (IOException e){
                pinkZone.append("Error: IOException\n");
            } finally{
                if(socket.isConnected()) {
                    pinkZone.append("Connection successful\nWelcome to " + name + "'s Othello Server.\nUse '/help' for commands.\n");
                    out.println(name);
                    newConnectionMenu.setEnabled(false);
                    disconnectMenu.setEnabled(true);
                    submitBtn.setEnabled(true);
                }
            }
        }

        public void comm(String text) throws IOException {
            out.println(text);
        }

        public void closeConnection() throws IOException {
            out.println("/bye");
            socket.close();
            newConnectionMenu.setEnabled(true);
            disconnectMenu.setEnabled(false);
            submitBtn.setEnabled(false);
        }

        @Override
        public void run(){
            try {
                inputStream = socket.getInputStream();
                scanner = new Scanner(inputStream);

                boolean done = false;
                while(!done && scanner.hasNextLine()){
                    String line = scanner.nextLine();
                    pinkZone.append(line + "\n");
                    if(line.equals("/bye"))
                        done = true;
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
