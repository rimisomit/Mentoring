package com.mentoring.battleship;

import java.io.IOException;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.Scanner;

/**
 * Main class
 * Main loop, Checking input commands
 */

public class Battleship {
    // Predefined error messages
    private final static String error_ILLEGAL_NUM_ARGS = "Usage: java Battleship N [config-file]" +
            "\nWhere:" +
            "\n  N - count of ships" +
            "\n  [config-file] is optional";
    private final static String ERROR_BOARD_TOO_SMALL = "Board must be at least " + Board.MIN_BOARD_SIZE + " by " + Board.MIN_BOARD_SIZE;
    private final static String ERROR_BOARD_TOO_LARGE = "Board must be at most 100 by 100.";
    private final static String ERROR_ILLEGAL_COMMAND = "Illegal command.";
    private final static String ERROR_WRONG_ARGUMENTS = "Wrong number of arguments.";
    private final static String ERROR_INVALID_VIEW_COMMAND = "Can only view myboards, myships, compboard, compships.";
    private final static String ERROR_SAME_COORDINATES = "Coordinates previously fired upon.";
    private final static String ERROR_ILLEGAL_COORDINATES = "Illegal coordinates.";
    //Feedback
    public final static String MSG_HIT = "Hit!";
    public final static String MSG_MISS = "Miss!";
    public final static String MSG_SUNK = "Sunk!";
    public final static String MSG_COMP_WIN = "Comp win!";
    public final static String MSG_YOU_WIN = "You win!";
    public final static String MSG_FIRE_ERROR = "err";
    public final static String MSG_FIRE_VICTORY = "victory";
    public final static String MSG_FIRE_NOTHIT = "nothit";
    public final static String MSG_FIRE_HIT = "hit";
    public final static String MSG_LOAD_FILE = "loadfromfile";
    public final static String MSG_LOAD_RANDOM = "loadrandom";
    // User cmd input commands
    private final static String CMD_BLANK = "";
    private final static String cmd_VIEW = "view";
    private final static String cmd_SHOW = "show";
    private final static String cmd_compBOARD = "compboard";
    private final static String cmd_myBOARD = "myboard";
    private final static String cmd_compSHIPS = "compships";
    private final static String cmd_mySHIPS = "myships";
    private final static String cmd_FIRE = "fire";
    private final static String cmd_MyStats = "mystats";
    private final static String cmd_CompStats = "compstats";
    private final static String cmd_HELP = "help";
    private final static String cmd_QUIT = "quit";
    private final static String cmd_EXIT = "exit";
    private final static String cmd_SAVE = "save";
    private Board compBoard, myBoard;
    private final static String USER_PROMPT = "[Battleship] user$ > ";
    private final static String compPrompt = "[Battleship] comp$ > ";
    private static int arrayDimension;
    //String timeStamp = new SimpleDateFormat("yyyy-MM-dd--HH-mm-ss").format(Calendar.getInstance().getTime());
    private FileSaveGame fileSaveGame;

    /**
     * @param dim
     * @param configFile
     * @throws BattleshipException
     */
    public Battleship(int dim, String configFile) throws BattleshipException {
        // create board from file
        compBoard = new Board(dim, configFile);
        compBoard.setHuman(false);
        myBoard = new Board(dim, configFile);
        myBoard.setHuman(true);
        fileSaveGame = new FileSaveGame("template.xls");
    }

    /**
     * help
     */
    private void printHelpMsg() {
        System.out.println("List of commands:");
        System.out.println();
        System.out.println(" view|show myboard|compboard - displays the boards");
        System.out.println(" view|show myships|compships - displays the placement of the ships");
        System.out.println();
        System.out.println(" fire 0 2 - fires a missile at the cell at [0,2]");
        System.out.println();
        System.out.println(" mystats|compstats - prints out the game statistics");
        System.out.println();
        System.out.println(" quit/exit - exits the game");
    }

    /**
     * @param b mystats, compstats
     */
    private void printStats(Board b) {
        int numFired = b.getMissilesFired();
        if (b.isHuman()) {
            System.out.println("\t\tStats for comp");
        } else {
            System.out.println("\t\t Stats for user");
        }
        System.out.println("Number of missiles fired: " + b.getMissilesFired());
        System.out.println("Hit ratio: " +
                (numFired == 0 ? 0 : (double) b.getNumHits() / b.getMissilesFired() * 100) + "%");
        System.out.println("Number of ships sunk: " + b.getShipsSunk());
    }

    /**
     * @throws IOException
     * @throws InterruptedException
     * @throws URISyntaxException
     */
    private void mainLoop() throws IOException, InterruptedException, URISyntaxException {
        Integer roundCount = 0;
        // display my board with ships
        System.out.println("\t\tMy board");
        myBoard.display(true);
        System.out.println("Hint: type help");
        // wait for user input
        Scanner lineScanner = new Scanner(System.in);
        String cmd;

        do {
            cmd = CMD_BLANK;
            System.out.print(USER_PROMPT);
            Scanner wordScanner = new Scanner(lineScanner.nextLine());
            if (wordScanner.hasNext()) {
                cmd = wordScanner.next();
                // >view/show handle
                if (cmd.equals(cmd_VIEW) | cmd.equals(cmd_SHOW)) {
                    if (wordScanner.hasNext()) {
                        String type = wordScanner.next();
                        if (wordScanner.hasNext()) {
                            System.out.println(ERROR_WRONG_ARGUMENTS);
                            continue;
                        }
                        // >view/show board or ships
                        if (type.equals(cmd_compBOARD)) {
                            System.out.println("\t\t Opponent's board");
                            compBoard.display(false);
                        } else if (type.equals(cmd_compSHIPS)) {
                            System.out.println("\t\t Opponent's board cheat mode");
                            compBoard.display(true);
                        } else if (type.equals(cmd_myBOARD)) {
                            System.out.println("\t\t My board");
                            myBoard.display(false);
                        } else if (type.equals(cmd_mySHIPS)) {
                            System.out.println("\t\t My Ships");
                            myBoard.display(true);
                        } else {
                            System.out.println(ERROR_INVALID_VIEW_COMMAND);
                        }
                    } else {
                        System.out.println(ERROR_WRONG_ARGUMENTS);
                    }
                    // >fire
                } else if (cmd.equals(cmd_FIRE)) {
                    String userResult = userFire(wordScanner);
                    if (userResult.equals(MSG_FIRE_HIT) || userResult.equals(MSG_FIRE_ERROR)) {
                        continue;
                    }
                    //FIXME
                    /*while (compFire().equals(MSG_FIRE_HIT)) {
                        }*/
                    String exitCode;
                    do {
                        exitCode = compFire();
                    } while (MSG_FIRE_HIT.equals(exitCode) || MSG_FIRE_ERROR.equals(exitCode));
                    System.out.println(" -----------Round " + roundCount++ + " End--------------");
                }
                // >stats
                else if (cmd.equals(cmd_MyStats)) {
                    showStats(wordScanner, compBoard);
                } else if (cmd.equals(cmd_CompStats)) {
                    showStats(wordScanner, myBoard);
                } else if (cmd.equals(cmd_HELP)) {
                    if (wordScanner.hasNext()) {
                        System.out.println(ERROR_WRONG_ARGUMENTS);
                    } else {
                        printHelpMsg();
                    }
                    // =====Save to xls=====
                } else if (cmd.equals(cmd_SAVE)) {
                    if (wordScanner.hasNext()) {
                        System.out.println(ERROR_WRONG_ARGUMENTS);
                    } else {
                        fileSaveGame.saveBoardToFile(myBoard, compBoard);
                    }
                } else if (cmd.equals(cmd_QUIT) | cmd.equals(cmd_EXIT)) {
                    if (wordScanner.hasNext()) {
                        System.out.println(ERROR_WRONG_ARGUMENTS);
                        cmd = CMD_BLANK;
                    }
                } else if (!cmd.equals(CMD_BLANK)) {
                    System.out.println(ERROR_ILLEGAL_COMMAND);
                }
            }
        } while (!(cmd.equals(cmd_QUIT) | cmd.equals(cmd_EXIT)));
    }

    private void showStats(Scanner wordScanner, Board board) {
        if (wordScanner.hasNext()) {
            System.out.println(ERROR_WRONG_ARGUMENTS);
        } else {
            printStats(board);
        }
    }

    /**
     * @throws IOException
     */
    public void clearConsole() throws IOException {
        final String os = System.getProperty("os.name");
        if (os.contains("Windows")) {
            Runtime.getRuntime().exec("cls");
        } else {
            Runtime.getRuntime().exec("clear");
        }
    }

    /**
     * @param args
     * @throws IOException
     * @throws InterruptedException
     * @throws URISyntaxException
     */
    public static void main(String[] args) throws IOException, InterruptedException, URISyntaxException {
        try {
            System.out.println("Choose board size 10 .. 100");
            arrayDimension = Integer.parseInt(getUserResponse(1));
            // check min..max size
            if (arrayDimension < Board.MIN_BOARD_SIZE) {
                throw new BattleshipException(ERROR_BOARD_TOO_SMALL);
            } else if (arrayDimension > Board.MAX_BOARD_SIZE) {
                throw new BattleshipException(ERROR_BOARD_TOO_LARGE);
            }
            // create Battleship instance
            System.out.println("Load ships from file? yes/no");
            String configFile = null;
            if (getUserResponse(0).equals(MSG_LOAD_FILE)) {
                System.out.println("Enter file name in config directory");
                configFile = getUserResponse(-1);
            }
            //System.out.println(getUserResponse(0));
            Battleship bs;
            bs = new Battleship(arrayDimension, configFile);
            // enter main loop
            bs.mainLoop();
        } catch (BattleshipException e) {
            System.err.println(e.getMessage());
        } catch (NumberFormatException e) {
            System.err.println(error_ILLEGAL_NUM_ARGS);
        }
    }

    /**
     * @param question
     * @return dimension|filename
     */
    public static String getUserResponse(int question) {
        System.out.print(USER_PROMPT);
        Scanner userChoiceLine = new Scanner(System.in);
        Scanner userChoiceWord = new Scanner(userChoiceLine.nextLine());
        String userChoiceWordS = userChoiceWord.next();
        // Ask dimension
        if (question == 1) {
            if (Integer.parseInt(userChoiceWordS) > 9 | Integer.parseInt(userChoiceWordS) < 100) {
                return userChoiceWordS;
            }
        }
        // load board to file?
        if (question == 0) {
            if (userChoiceWordS.equals("yes")) {
                return MSG_LOAD_FILE;
            } else {
                System.out.println("Loading random");
                return MSG_LOAD_RANDOM;
            }
        }
        // Ask filename
        if (question == -1) {
            URL fileURL = Battleship.class.getClassLoader().getResource("config/" + userChoiceWordS);
            //URL fileURL = Battleship.class.getClassLoader().getResource("Myconfig");
            if (fileURL == null) {
                System.out.println("No file found, loading random");
                return null;
            }
            String configFile = fileURL.getPath();
            System.out.println("Using file " + configFile);
            return configFile;
        }
        return null;
    }
public int[][] getSuggestedCoords(){
    int[][] suggestedCoords = new int[1][1];
    if (myBoard.getSuggestedArray() == null) {
        return null;
    }
    for (int x = 0; x < myBoard.getSuggestedArray().length; x++) {

    }
    return suggestedCoords;
}
    /**
     * @return string hit | not hit
     * @throws InterruptedException
     */
    public String compFire() throws InterruptedException {
        int row = 0, col = 0;
        //row = myBoard.rand.nextInt(3);
        //col = myBoard.rand.nextInt(1);
        if (myBoard.getSuggestedArray() != null) {
            int[][] suggestedArray = myBoard.getSuggestedArray();
            int randomNum = myBoard.rand.nextInt(3);
            do {
                row = suggestedArray[randomNum][0];
                col = suggestedArray[randomNum][1];
            } while (!(row >= 0 && col >= 0));
            suggestedArray[randomNum][0] = -1;
            suggestedArray[randomNum][1] = -1;
        } else {
            row = myBoard.rand.nextInt(myBoard.getDim() - 2) + 1;
            col = myBoard.rand.nextInt(myBoard.getDim() - 2) + 1;
        }
        System.out.println(compPrompt + " fire " + row + " " + col);
        //clearConsole();
        if (myBoard.fire(row, col)) {
            if (myBoard.checkWin()) {
                System.out.println(MSG_COMP_WIN);
                myBoard.display(false);
                printStats(myBoard);
                return MSG_FIRE_VICTORY;
            }
            Thread.sleep(200);
            System.out.println("\t\t My board");
            myBoard.display(false);
        } else {
            System.out.println(ERROR_SAME_COORDINATES);
            return MSG_FIRE_HIT;
        }
        System.out.println("Value = " + myBoard.getPieceValue(row, col));
        if (myBoard.getPieceValue(row, col).equals("X")) {
            return MSG_FIRE_HIT;
        } else {
            return MSG_FIRE_NOTHIT;
        }
    }


    /**
     * @param wordScanner
     * @return string hit | not hit
     * @throws InterruptedException
     */
    public String userFire(Scanner wordScanner) throws InterruptedException {
        int row = 0, col = 0;

        if (wordScanner.hasNextInt()) {
            row = wordScanner.nextInt();
        } else {
            System.out.println(ERROR_ILLEGAL_COMMAND);
            //continue;
            return MSG_FIRE_ERROR;
        }
        if (wordScanner.hasNextInt()) {
            col = wordScanner.nextInt();
        } else {
            System.out.println(ERROR_ILLEGAL_COMMAND);
            //continue;
            return MSG_FIRE_ERROR;
        }
        if (wordScanner.hasNext()) {
            System.out.println(ERROR_WRONG_ARGUMENTS);
            //continue;
            return MSG_FIRE_ERROR;
        }
        if (!compBoard.isValid(row, col)) {
            System.out.println(ERROR_ILLEGAL_COORDINATES);
            return MSG_FIRE_ERROR;
        }
        if (compBoard.fire(row, col)) {
            if (compBoard.checkWin()) {
                System.out.println(MSG_YOU_WIN);
                compBoard.display(false);
                printStats(compBoard);
                return MSG_FIRE_VICTORY;
            }
            Thread.sleep(2000);
            System.out.println("\t\t Opponent's board");
            compBoard.display(false);
            System.out.println("Value = " + myBoard.getPieceValue(row, col));
            if (compBoard.getPieceValue(row, col).equals("X")) {
                return MSG_FIRE_HIT;
            } else {
                return MSG_FIRE_NOTHIT;
            }
        } else {
            System.out.println(ERROR_SAME_COORDINATES);
            return MSG_FIRE_HIT;
        }
    }
}
