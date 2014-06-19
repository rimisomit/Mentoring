package com.mentoring.battleship;
import java.io.*;
import java.net.URL;
import java.util.*;
import java.text.SimpleDateFormat;

/**
 * Main class
 * Main loop, Checking input commands
 */
// TODO compile and run
// javac -d classes -cp src src/com/bt/Battleship.java
// --run with classpath
// java -cp classes com.mentoring.bt.Battleship 10
// TODO create jar
// jar cvf bin/btjar.jar src/META-INF/MANIFEST.MF classes/
// TODO execute
// java -cp bin:bin/btjar.jar com.mentoring.bt.Battleship
//    or
// java -jar <filename>.jar
//    or
// java -cp config:target/BattleShip-1.0-SNAPSHOT.jar com.mentoring.battleship.Battleship

public class Battleship {
	// Predefined error messages
	private final static String error_ILLEGAL_NUM_ARGS = "Usage: java Battleship N [config-file]" +
                                                    "\nWhere:" +
                                                    "\n  N - count of ships" +
                                                    "\n  [config-file] is optional";
	private final static String error_BOARD_TOO_SMALL = "Board must be at least " + Board.MIN_BOARD_SIZE + " by " + Board.MIN_BOARD_SIZE;
	private final static String error_BOARD_TOO_LARGE = "Board must be at most 100 by 100.";
	private final static String error_ILLEGAL_COMMAND = "Illegal command.";
	private final static String error_WRONG_ARGUMENTS = "Wrong number of arguments.";
	private final static String error_INVALID_VIEW_COMMAND = "Can only view myboards, myships, compboard, compships.";
	private final static String error_SAME_COORDINATES = "Coordinates previously fired upon.";
	private final static String error_ILLEGAL_COORDINATES = "Illegal coordinates.";
    //Feedback
	public final static String msg_HIT = "Hit!";
	public final static String msg_MISS = "Miss!";
	public final static String msg_SUNK = "Sunk!";
	public final static String msg_compWIN = "Comp win!";
    public final static String msg_youWIN = "You win!";
	// User cmd input commands
	private final static String cmd_BLANK = "";
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
	private final static String userPrompt = "[Battleship] user$ > ";
    private final static String compPrompt = "[Battleship] comp$ > ";
    private static int arrayDimension;
    String timeStamp = new SimpleDateFormat("yyyy-MM-dd--HH-mm-ss").format(Calendar.getInstance().getTime());

    //constructor. Create game with two boards
	public Battleship(int dim, String configFile) throws BattleshipException {
		// create board from file
		compBoard = new Board(dim, configFile);
        compBoard.setHuman(false);
        myBoard = new Board(dim, configFile);
        myBoard.setHuman(true);
	}

    // >help output
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

    // >stats show statistic
    private void printStats(Board b) {
        int numFired = b.getMissilesFired();
        if (b.isHuman()) {
            System.out.println("\t\tStats for comp");
        } else {
             System.out.println("\t\t Stats for user");
        }
        System.out.println("Number of missiles fired: " + b.getMissilesFired());
        System.out.println("Hit ratio: " +
                (numFired == 0 ? 0 : (double)b.getNumHits() / b.getMissilesFired() * 100) + "%");
        System.out.println("Number of ships sunk: " + b.getShipsSunk());
    }

	private void mainLoop() throws IOException {
		// display my board with ships
        System.out.println("\t\tMy board");
        myBoard.display(true);
        System.out.println("Hint: type help");
        // wait for user input
		Scanner lineScanner = new Scanner(System.in);
		String cmd;

		do {
			cmd = cmd_BLANK;
			System.out.print(userPrompt);
			Scanner wordScanner = new Scanner(lineScanner.nextLine());
			if (wordScanner.hasNext()) {
				cmd = wordScanner.next();
	            // >view/show handle
				if (cmd.equals(cmd_VIEW)|cmd.equals(cmd_SHOW)) {
					if (wordScanner.hasNext()) {
						String type = wordScanner.next();
						if (wordScanner.hasNext()) {
							System.out.println(error_WRONG_ARGUMENTS);
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
							System.out.println(error_INVALID_VIEW_COMMAND);
						}
					} else {
						System.out.println(error_WRONG_ARGUMENTS);
					}
                // >fire
				} else if (cmd.equals(cmd_FIRE)) {
					int row=0, col=0;
					if (wordScanner.hasNextInt()) {
                        row = wordScanner.nextInt();
                    }
					else {
						System.out.println(error_ILLEGAL_COMMAND);
						continue;
					}
					if (wordScanner.hasNextInt()) {
                        col = wordScanner.nextInt();
                    }
					else {
						System.out.println(error_ILLEGAL_COMMAND);
						continue;
					}
					if (wordScanner.hasNext()) {
						System.out.println(error_WRONG_ARGUMENTS);
						continue;
					}
					if (!compBoard.isValid(row, col)) {
                        System.out.println(error_ILLEGAL_COORDINATES);
                    }
					else {
                        //player fire
						if (compBoard.fire(row, col)) {
							if (compBoard.checkWin()) {
								System.out.println(msg_youWIN);
								compBoard.display(false);
								printStats(compBoard);
								return;
							}
                            System.out.println("\t\t Opponent's board");
							compBoard.display(false);
                            try {
                                Thread.sleep(3000);
                            } catch (InterruptedException e) {
                                e.printStackTrace();
                            }
                        }
                        //comp fire
                        row = myBoard.rand.nextInt(myBoard.getDim()-2) + 1;
                        col = myBoard.rand.nextInt(myBoard.getDim()-2) + 1;
                        System.out.println(compPrompt + " fire " + row + " " + col);
                        try {
                            Thread.sleep(3000);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                        clearConsole();
                        if (myBoard.fire(row, col)) {
                            if (myBoard.checkWin()) {
                                System.out.println(msg_compWIN);
                                myBoard.display(false);
                                printStats(myBoard);
                                return;
                                }
                            System.out.println("\t\t My board");
                            myBoard.display(false);
                            System.out.println(" -----------Round End--------------");
						} else {
							System.out.println(error_SAME_COORDINATES);
						}
					}
                // >stats
				} else if (cmd.equals(cmd_MyStats)) {
					if (wordScanner.hasNext()) {
						System.out.println(error_WRONG_ARGUMENTS);
					} else {
						printStats(compBoard);
					}
                } else if (cmd.equals(cmd_CompStats)) {
                    if (wordScanner.hasNext()) {
                        System.out.println(error_WRONG_ARGUMENTS);
                    } else {
                        printStats(myBoard);
                    }
				} else if (cmd.equals(cmd_HELP)) {
					if (wordScanner.hasNext()) {
						System.out.println(error_WRONG_ARGUMENTS);
					} else {
						printHelpMsg();
					}
                    // =====Save to xls=====
                } else if (cmd.equals(cmd_SAVE)) {
                    if (wordScanner.hasNext()) {
                        System.out.println(error_WRONG_ARGUMENTS);
                    } else {
                        saveGame(myBoard, compBoard);
                    }
				} else if (cmd.equals(cmd_QUIT) | cmd.equals(cmd_EXIT)) {
					if (wordScanner.hasNext()) {
						System.out.println(error_WRONG_ARGUMENTS);
						cmd = cmd_BLANK;
					}
				} else if (!cmd.equals(cmd_BLANK)){
					System.out.println(error_ILLEGAL_COMMAND);
				}
			}
		} while (!(cmd.equals(cmd_QUIT)|cmd.equals(cmd_EXIT)));
	}

    public void clearConsole() {
        try {
            final String os = System.getProperty("os.name");
            if (os.contains("Windows")) {
                Runtime.getRuntime().exec("cls");
            } else {
                Runtime.getRuntime().exec("clear");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

	public static void main(String[] args) throws IOException {
		try {
            System.out.println("Choose board size 10 .. 100");
            arrayDimension = Integer.parseInt(getUserResponce(1));
            // check min..max size
			if (arrayDimension < Board.MIN_BOARD_SIZE) {
				throw new BattleshipException(error_BOARD_TOO_SMALL);
			} else if (arrayDimension > Board.MAX_BOARD_SIZE) {
				throw new BattleshipException(error_BOARD_TOO_LARGE);
			}
		    // create Battleship instance
            System.out.println("Load ships from file? yes/no");
            String configFile = null;
            if (getUserResponce(0).equals("loadfromfile")) {
                System.out.println("Enter file name in config directory");
                configFile = getUserResponce(-1);
            }
            //System.out.println(getUserResponce(0));
			Battleship bs;
			bs = new Battleship(arrayDimension, configFile);
			// enter main loop
			bs.mainLoop();
		}
		catch (BattleshipException e) {
			System.err.println(e.getMessage());
		}
		catch (NumberFormatException e) {
			System.err.println(error_ILLEGAL_NUM_ARGS);
		} 
	}
    public static String getUserResponce(int question) {
        System.out.print(userPrompt);
        Scanner userChoiceLine = new Scanner(System.in);
        Scanner userChoiceWord = new Scanner(userChoiceLine.nextLine());
        String userChoiceWordS = userChoiceWord.next();
        // Ask dimension
        if (question == 1) {
            if (Integer.parseInt(userChoiceWordS) > 9 | Integer.parseInt(userChoiceWordS) < 100 ) {
                return userChoiceWordS;
            }
        }
        // load board to file?
        if (question == 0) {
            if (userChoiceWordS.equals("yes")) {
                return "loadfromfile";
            } else {
                System.out.println("Loading random");
                return "loadrandom";
            }
        }
        // Ask filename
        if (question == -1) {
            URL fileURL = Battleship.class.getClassLoader().getResource(userChoiceWordS);
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

    public void loadGame() {
        System.out.println("Loaded");
    }

    public void saveGame(Board mBoard, Board cBoard) throws IOException {
        FileSaveGame fileSaveGame = new FileSaveGame("template.xls");
        fileSaveGame.saveBoardToFile(mBoard, cBoard);
    }
}
