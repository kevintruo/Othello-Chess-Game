package othello;

/*
  File Name: OthelloModel.java
  Authors: Kevin Truong, 040937076 - Dante Beltran, 040921470
  Course: CST8221 - JAP, Lab Section: 311
  Assignment: 1
  Date: October 16th, 2020
  Professor: Daniel Cormier, Karan Kalsi
  Purpose: The purpose is to create a model to check for valid moves
  and capture chess pieces using an internal 2D array
 */

public class OthelloModel {
    private int[][] board = new int[7][7];

    public static final int NORMAL = 0;
    public static final int CORNER_TEST = 1;
    public static final int OUTER_TEST = 2;
    public static final int TEST_CAPTURE = 3;
    public static final int TEST_CAPTURE2 = 4;
    public static final int UNWINNABLE = 5;
    public static final int INNER_TEST = 6;

    public void initialize(int mode) {
        switch (mode) {
            case CORNER_TEST:
                board = new int[][]{
                        {2, 0, 0, 0, 0, 0, 0, 1},
                        {0, 1, 0, 0, 0, 0, 2, 0},
                        {0, 0, 0, 0, 0, 0, 0, 0},
                        {0, 0, 0, 0, 0, 0, 0, 0},
                        {0, 0, 0, 0, 0, 0, 0, 0},
                        {0, 0, 0, 0, 0, 0, 0, 0},
                        {0, 1, 0, 0, 0, 0, 1, 0},
                        {2, 0, 0, 0, 0, 0, 0, 2}};

                break;
            case OUTER_TEST:
                board = new int[][]{
                        {0, 0, 0, 0, 0, 0, 0, 0},
                        {0, 2, 2, 2, 2, 2, 2, 0},
                        {0, 2, 1, 1, 1, 1, 2, 0},
                        {0, 2, 1, 0, 0, 1, 2, 0},
                        {0, 2, 1, 0, 0, 1, 2, 0},
                        {0, 2, 1, 1, 1, 1, 2, 0},
                        {0, 2, 2, 2, 2, 2, 2, 0},
                        {0, 0, 0, 0, 0, 0, 0, 0}};
                break;
            case INNER_TEST:
                board = new int[][]{
                        {2, 2, 2, 2, 2, 2, 2, 2},
                        {2, 0, 0, 0, 0, 0, 0, 2},
                        {2, 0, 2, 2, 2, 2, 0, 2},
                        {2, 0, 2, 1, 1, 2, 0, 2},
                        {2, 0, 2, 1, 1, 2, 0, 2},
                        {2, 0, 2, 2, 2, 2, 0, 2},
                        {2, 0, 0, 0, 0, 0, 0, 2},
                        {2, 2, 2, 2, 2, 2, 2, 2}};
                break;
            case UNWINNABLE:
                board = new int[][]{
                        {0, 0, 0, 0, 0, 0, 0, 0},
                        {0, 0, 0, 0, 0, 0, 0, 0},
                        {0, 0, 0, 0, 0, 0, 0, 0},
                        {0, 0, 0, 0, 0, 0, 0, 0},
                        {0, 0, 0, 0, 0, 0, 0, 0},
                        {0, 0, 0, 0, 0, 0, 0, 0},
                        {0, 0, 0, 0, 0, 0, 0, 0},
                        {0, 0, 0, 0, 0, 0, 0, 0}};
                break;
            case TEST_CAPTURE:
                board = new int[][]{
                        {0, 0, 0, 0, 0, 0, 0, 0},
                        {0, 1, 1, 1, 1, 1, 1, 0},
                        {0, 1, 1, 1, 1, 1, 1, 0},
                        {0, 1, 2, 2, 2, 1, 1, 0},
                        {0, 1, 2, 0, 2, 1, 1, 0},
                        {0, 1, 2, 2, 2, 1, 1, 0},
                        {0, 1, 1, 1, 1, 1, 1, 0},
                        {0, 0, 0, 0, 0, 0, 0, 0}};
                break;

            case TEST_CAPTURE2:
                board = new int[][]{
                        {1, 1, 1, 1, 1, 1, 1, 1},
                        {1, 1, 1, 1, 1, 1, 1, 1},
                        {1, 2, 2, 2, 1, 2, 1, 1},
                        {1, 2, 2, 2, 2, 2, 1, 1},
                        {1, 2, 2, 0, 2, 2, 1, 1},
                        {1, 2, 2, 2, 2, 1, 1, 1},
                        {1, 2, 1, 2, 2, 2, 1, 1},
                        {1, 1, 1, 1, 1, 1, 1, 1}};
                break;
            default:
                board = new int[][]{
                        {0, 0, 0, 0, 0, 0, 0, 0},
                        {0, 0, 0, 0, 0, 0, 0, 0},
                        {0, 0, 0, 0, 0, 0, 0, 0},
                        {0, 0, 0, 2, 1, 0, 0, 0},
                        {0, 0, 0, 1, 2, 0, 0, 0},
                        {0, 0, 0, 0, 0, 0, 0, 0},
                        {0, 0, 0, 0, 0, 0, 0, 0},
                        {0, 0, 0, 0, 0, 0, 0, 0}};

        }
    }

    // The rest is up to you.  Good luck!

    /**
     * Return the contents of a given square
     *
     * @param x x coordinate
     * @param y y coordinate
     * @return 0 for empty, 1 for playerOne, 2 for playerTwo
     */
    public int getBoard(int x, int y) {
        //If it is out of bound
        if (x < 0 || x > 7 || y < 0 || y > 7)
            return -1;

        return board[x][y];
    }

    /**
     * To get the number of chips that a player has
     *
     * @param player the number of the player (one or two)
     * @return integer the number of chips
     */
    public int getChips(int player) {
        int numb = 0;
        //Start of for loops
        for (int[] i : board) {
            for (int j : i) {
                //If is a player one's chip
                if (j == player)
                    numb++;
            }
        }
        return numb;
    }

    /**
     * Check if isValid
     *
     * @param x      x coordinate
     * @param y      y coordinate
     * @param player player colour
     * @return to check if the move is valid
     */
    public boolean isValid(int x, int y, int player) {
        //If the current square is empty
        if (getBoard(x, y) == 0) {
            //Checking 8 directions:
            //Checking TOP direction:
            int left = decCoordinate(y);
            int right = incCoordinate(y);
            int bottom = incCoordinate(x);
            int top = decCoordinate(x);

            if (checkDirections(x, left, player, "LEFT"))
                return true;
            else if (checkDirections(x, right, player, "RIGHT"))
                return true;
            else if (checkDirections(bottom, y, player, "BOTTOM"))
                return true;
            else if (checkDirections(top, y, player, "TOP"))
                return true;
            else if (checkDirections(top, left, player, "TOP_LEFT"))
                return true;
            else if (checkDirections(top, right, player, "TOP_RIGHT"))
                return true;
            else if (checkDirections(bottom, left, player, "BOTTOM_LEFT"))
                return true;
            else return checkDirections(bottom, right, player, "BOTTOM_RIGHT");
        }
        //Return false by default
        return false;
    }

    /**
     * Decrement the coordinate by one
     *
     * @param coordinate the current coordinate
     * @return coordinate - 1
     */
    private int decCoordinate(int coordinate) {
        return coordinate - 1;
    }

    /**
     * Increment the coordinate by one
     *
     * @param coordinate the current coordinate
     * @return coordinate + 1
     */
    private int incCoordinate(int coordinate) {
        return coordinate + 1;
    }

    /**
     * Check if that direction has a valid move
     * @param x x coordinate
     * @param y y coordinate
     * @param player player chess piece (black or white)
     * @param direction the direction that you are checking
     * @return true if found, false if not found
     */
    private boolean checkDirections(int x, int y, int player, String direction) {
        if (getBoard(x, y) != player) {
            //Move is potentially valid
            while (getBoard(x, y) != -1) {
                if (getBoard(x, y) == -1 || getBoard(x, y) == 0)
                    break;

                if (getBoard(x, y) == player)
                    return true;
                else {
                    switch (direction) {
                        case "TOP":
                            x = decCoordinate(x);
                            break;
                        case "LEFT":
                            y = decCoordinate(y);
                            break;
                        case "BOTTOM":
                            x = incCoordinate(x);
                            break;
                        case "RIGHT":
                            y = incCoordinate(y);
                            break;
                        case "TOP_LEFT":
                            x = decCoordinate(x);
                            y = decCoordinate(y);
                            break;
                        case "TOP_RIGHT":
                            x = decCoordinate(x);
                            y = incCoordinate(y);
                            break;
                        case "BOTTOM_LEFT":
                            x = incCoordinate(x);
                            y = decCoordinate(y);
                            break;
                        case "BOTTOM_RIGHT":
                            x = incCoordinate(x);
                            y = incCoordinate(y);
                            break;
                        default: //SHOULD NOT REACH HERE
                            break;
                    }
                }
            }
        }
        return false;
    }

    /**
     * To make a move and capture chess pieces
     * @param x x coordinate
     * @param y y coordinate
     * @param player player chess piece (black or white)
     * @return the number of chess pieces that captured
     */
    public int move(int x, int y, int player) {
        if (!isValid(x, y, player))
            return 0;
        //The square is valid and empty => set it to the colour of the appropriate player
        board[x][y] = player;

        int left = decCoordinate(y);
        int right = incCoordinate(y);
        int bottom = incCoordinate(x);
        int top = decCoordinate(x);

        return flipChip(x, left, player, "LEFT")
                + flipChip(x, right, player, "RIGHT")
                + flipChip(bottom, y, player, "BOTTOM")
                + flipChip(top, y, player, "TOP")
                + flipChip(top, left, player, "TOP_LEFT")
                + flipChip(top, right, player, "TOP_RIGHT")
                + flipChip(bottom, left, player, "BOTTOM_LEFT")
                + flipChip(bottom, right, player, "BOTTOM_RIGHT");
    }

    /**
     * Capture chips if the move is valid
     * @param x x coordinate
     * @param y y coordinate
     * @param player player chess piece (black or white)
     * @param direction the direction that you are checking
     * @return the number of chess pieces that captured
     */
    private int flipChip(int x, int y, int player, String direction) {
        if(!checkDirections(x,y,player, direction))
            return 0;
        int counter = 0;
        //Check if the next piece is white
        if (getBoard(x, y) != player) {
            //Loop until the end of the board (the edge: -1) or next piece is white
            while (getBoard(x, y) != -1 && getBoard(x, y) != player && getBoard(x, y) != 0) {
                //change to black
                board[x][y] = player;
                //increment
                counter++;
                switch (direction) {
                    case "LEFT":
                        y = decCoordinate(y);
                        break;
                    case "BOTTOM":
                        x = incCoordinate(x);
                        break;
                    case "RIGHT":
                        y = incCoordinate(y);
                        break;
                    case "TOP":
                        x = decCoordinate(x);
                        break;
                    case "TOP_LEFT":
                        x = decCoordinate(x);
                        y = decCoordinate(y);
                        break;
                    case "TOP_RIGHT":
                        x = decCoordinate(x);
                        y = incCoordinate(y);
                        break;
                    case "BOTTOM_LEFT":
                        x = incCoordinate(x);
                        y = decCoordinate(y);
                        break;
                    case "BOTTOM_RIGHT":
                        x = incCoordinate(x);
                        y = incCoordinate(y);
                        break;
                    default: //SHOULD NOT REACH HERE
                        break;
                }
            }
        }
        return counter;
    }

    /**
     * To check if there is a valid move for one player
     * @param player player one or two
     * @return true if there are one or more valid moves, false there are not any valid moves
     */
    public boolean canMove(int player) {
        for (int i = 0; i < board.length; i++)
            for (int j = 0; j < board[i].length; j++)
                if (isValid(i, j, player))
                    return true;

        return false;
    }
}
