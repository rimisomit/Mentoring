package com.mentoring.bt;
import java.io.IOException;
import java.util.*;

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
// execute
// java -cp bin:bin/btjar.jar com.mentoring.bt.Battleship
//    or
// java -jar <filename>.jar
// TODO put config file inside classpath

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
	private Board board, myBoard;
	private final String userPrompt = "[Battleship] user$ > ";
    private final String compPrompt = "[Battleship] comp$ > ";

    //constructor. Create game with two boards
	public Battleship(int dim, String configFile) throws BattleshipException {
		// create board from file
		board = new Board(dim, configFile);
        board.setHuman(false);
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

	private void mainLoop() {
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
							board.display(false);
						} else if (type.equals(cmd_compSHIPS)) {
                            System.out.println("\t\t Opponent's board cheat mode");
							board.display(true);
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
					if (!board.isValid(row, col)) {
                        System.out.println(error_ILLEGAL_COORDINATES);
                    }
					else {
                        //player fire
						if (board.fire(row, col)) {
							if (board.checkWin()) {
								System.out.println(msg_youWIN);
								board.display(false);
								printStats(board);
								return;
							}
                            System.out.println("\t\t Opponent's board");
							board.display(false);
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
						printStats(board);
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

    public static void clearConsole() {

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

	public static void main(String[] args) {
		try {
			// Check arguments. >java Battleship N [config-file]
			if (args.length < 1 || args.length > 2) {
				throw new BattleshipException(error_ILLEGAL_NUM_ARGS);
			}
			// Read input params
			int dim = Integer.parseInt(args[0]);
            // check min..max size
			if (dim < Board.MIN_BOARD_SIZE) {
				throw new BattleshipException(error_BOARD_TOO_SMALL);
			} else if (dim > Board.MAX_BOARD_SIZE) {
				throw new BattleshipException(error_BOARD_TOO_LARGE);
			}
		    // create Battleship instance
			Battleship bs;
			String configFile = args.length == 1 ? null : args[1];
			bs = new Battleship(dim, configFile);

            // TODO bs.getClass().getClassLoader().getResourceAsStream();
            // TODO create jar by console

            // TODO install maven
            // TODO create test maven project by arh types, ensure build SU
            // TODO put BT into maven
            // TODO Apache POI library to save progress

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
}
