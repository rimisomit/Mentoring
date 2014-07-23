package com.mentoring.battleship;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.NoSuchElementException;
import java.util.Random;
import java.util.Scanner;

/**
 * Board class with objects
 */
public class Board {

	public static String WATER = "-";
	public static String WATER_CRATER = "0";
	public static String SHIP_CRATER = "X";
	private boolean human = true;
	// board pieces
	private Water water = new Water(WATER, WATER);		
	private Crater waterCrater = new Crater(WATER, WATER_CRATER);
	private Ship[] ships;
	// the grid of game pieces
	private BoardCell grid[][];
    private int suggestedArray[][];
	private int dimension;
	// game limits
	public final static int MIN_BOARD_SIZE = 9;
	public final static int MAX_BOARD_SIZE = 100;
	private final static int RANDOM_NUM_SHIPS = 7;
    // initial shots=0
	private int missilesFired = 0;
	private int numHits = 0;
	Random rand = new Random();

    /**
     *
     * @param dim
     * @param configFile
     * @throws BattleshipException
     */
	public Board(int dim, String configFile ) throws BattleshipException {
		this.dimension = dim;
		grid = new BoardCell[dim][dim];
		// Put Water into all cells of the board
		for (int x=0; x<dim; x++) {
			for (int y=0; y<dim; y++) {
				place(x, y, water);
			}
		}
		// create ships at random and place on board
		if (configFile == null) {
			ships = new Ship [RANDOM_NUM_SHIPS];
			// create each sized ship pointing in a random direction[0|1|2|3]
			ships[0] = new Ship(Ship.ShipType.AIRCRAFT, rand.nextInt(3));
			ships[1] = new Ship(Ship.ShipType.BATTLESHIP, rand.nextInt(3));
            ships[2] = new Ship(Ship.ShipType.BATTLESHIP, rand.nextInt(3));
			ships[3] = new Ship(Ship.ShipType.CRUISER, rand.nextInt(3));
            ships[4] = new Ship(Ship.ShipType.CRUISER, rand.nextInt(3));
			ships[5] = new Ship(Ship.ShipType.DESTROYER, rand.nextInt(3));
			ships[6] = new Ship(Ship.ShipType.PATROL, rand.nextInt(3));
			
			// place each ship on the board in a random position.  This can
			// cause the initial direction of the ship to change if it cannot
			// be placed at a spot.
			for (int i=0; i<ships.length; i++) {
				placeShipRandom(ships[i]);
			}
		// create ships from file and place on board
		} else {
			try {
                // read lines
				Scanner lines = new Scanner ( new FileInputStream (configFile));
                // read words from lines
				Scanner words = new Scanner (lines.nextLine());
				// read and check the number of ships
				int numShips = words.nextInt();
				if (numShips > 0) {
					ships = new Ship[numShips];
				} else {
					throw new BattleshipException(
							"Check first line. Incorrect or " +
                                    "null number of ships in file " + configFile + ".");
				}
				// check lines content
				for (int i=0; i<numShips; i++) {
                    //check for EOF
					if (!lines.hasNext()) {
						throw new BattleshipException(
								"Check count of lines, excluding first. " +
                                        "Incorrect number of ships in file " + configFile + ".");
					}
					//start reading from 2nd line
					words = new Scanner (lines.nextLine());
					int startRow = words.nextInt();
					int startCol = words.nextInt();
					String strDir = words.next();
					int hitPoints = words.nextInt();
                    // too many parameters in line
					if (words.hasNext()) {
						throw new BattleshipException(
								"File " + configFile + " has corrupted contents.");
					}
					if (!isValid(startRow, startCol)) {
						throw new BattleshipException(
								"Overlapping or out-of-bounds ships in file " + configFile + ".");
					}
					
					// parse out the direction
					int dir=Ship.NORTH;
					if (strDir.equals("N")) dir = Ship.NORTH;
					else if (strDir.equals("S")) dir = Ship.SOUTH;
					else if (strDir.equals("E")) dir = Ship.EAST;
					else if (strDir.equals("W")) dir = Ship.WEST;
					else throw new NoSuchElementException();
					
					// based on the hit points create the appropriate ship.  All ships
					// must have between 1-5 hit points.
					Ship.ShipType shipType;
					if (hitPoints ==  Ship.ShipType.AIRCRAFT.getHitPoints()) {
						shipType = Ship.ShipType.AIRCRAFT;
					} else if (hitPoints == Ship.ShipType.BATTLESHIP.getHitPoints()) {
						shipType = Ship.ShipType.BATTLESHIP;
					} else if (hitPoints == Ship.ShipType.CRUISER.getHitPoints()) {
						shipType = Ship.ShipType.CRUISER;
					} else if (hitPoints == Ship.ShipType.DESTROYER.getHitPoints()) {
						shipType = Ship.ShipType.DESTROYER;
					} else if (hitPoints == Ship.ShipType.PATROL.getHitPoints()) {
                        shipType = Ship.ShipType.PATROL;
                    } else {
						throw new BattleshipException(
								"Illegal ship size in file " + configFile + ".");
					}
					
					// create the ship and place it on the board if it is valid
					Ship ship = new Ship(shipType, dir);
					if (isShipPositionValid(ship, startRow, startCol, dir)) {
						ships[i]= ship;
						placeShip(ship, startRow, startCol);
					} else {
						throw new BattleshipException(
								"Overlapping or out-of-bounds ships in file " + configFile + ".");
					}
				}	
				
				if (lines.hasNextLine()) {
					throw new BattleshipException(
							"Check first line. Incorrect number of ships in file " + configFile + ".");
				}
			}
			catch (FileNotFoundException e){
				throw new BattleshipException(
						"Cannot open file " + configFile + ".");
			}
			catch (NoSuchElementException e) {
				throw new BattleshipException(
						"File " + configFile + " has corrupted contents.");
			}
			/*catch (IOException e) {
				throw new BattleshipException(
						"File " + configFile + " has corrupted contents.");
			}*/
		}
	}

    /**
     *
     * @return dimension size
     */
	public int getDim() {
		return dimension;
	}
	
	public BoardCell getPiece(int x, int y) {
		// if a spot is valid, return the BoardCell at that grid position
		return isValid(x, y) ? grid[x][y] : null;
	}
	
	public int getMissilesFired() {
        return missilesFired;
    }
	
	public int getNumHits() {
        return numHits;
    }

    /**
     *
     * @return number of sunk ships
     */
	public int getShipsSunk() {
		int numSunk = 0;
		// loop through the ships to determine how many are sunk
		for (int i=0; i<ships.length; i++) {
			if (ships[i].getSunk()) {
				numSunk++;
			}
		}
		return numSunk;
	}

    /**
     *
     * @return total ship count
     */
	public int getNumShips() {
        return ships.length;
    }
	
	public boolean isValid(int x, int y) {
        return x >= 0 && x < dimension && y >= 0 && y < dimension;
    }

	// Put specific piece(water, ship, etc) on coord
	public void place(int x, int y, BoardCell piece) {
        grid[x][y] = piece;
    }
	
	public boolean checkWin() {
        return getNumShips() == getShipsSunk();
    }

    /**
     * display board
     * @param cheat
     */
	public void display(boolean cheat) {
		// print column header
        System.out.print("   ");
		for (int col=0; col< dimension; col++) {
			System.out.printf("%3d", col);
		}
		System.out.println();

		// print each row
		for (int row=0; row< dimension; row++) {
			System.out.printf("%3d", row);
			for (int col=0; col< dimension; col++) {
				BoardCell piece = getPiece(row, col);
				// print out the cell piece.  if cheat is enabled we are viewing
				// the ships, otherwise we are viewing the board
				System.out.printf("%3s", piece.getVal(cheat));
			}
			System.out.println();
		}
	}

    /**
     *
     * @param x
     * @param y
     * @return piece string value
     */
    public String getPieceValue(int x, int y) {
        BoardCell piece = getPiece(x, y);
        return piece.getVal(false);
    }

    /**
     *
     * @param x
     * @param y
     * @return was fire successful
     */
	public boolean fire(int x, int y) {
		// cannot fire on an invalid spot, or a cell that was already hit
		// (i.e. contains a crater)
		if (!isValid(x, y) || getPiece(x, y).wasHit()) {
			return false;
		}
		// fire at the piece, if it returns true it was hit (a ship piece)
		missilesFired++;
		if (getPiece(x,y).fireAt()) {
			// get the ship's icon to store with the crater
			String shipVal = getPiece(x,y).getVal(true);
			numHits++;
            if (getPiece(x,y).wasSunk()) {
                int startRow = getPiece(x,y).getShipStarRow();
                int startColumn = getPiece(x,y).getShipStarColumn();
                int endRow = getPiece(x,y).getShipEndRow();
                int endColumn = getPiece(x,y).getShipEndColumn();
                //System.out.println(getPiece(x,y).get);
                //System.out.println(getPiece(x,y).getShipStarRow() + ":" + getPiece(x,y).getShipStarColumn());
                //System.out.println(getPiece(x,y).getShipEndRow() + ":" + getPiece(x,y).getShipEndColumn());
                for (int row = startRow - 1; row <= endRow + 1; row++)
                    for (int col = startColumn - 1; col <= endColumn + 1; col++) {
                        if (!getPiece(row,col).wasHit()) {
                            place(row, col, waterCrater);
                        }
                    }
            }
            // replace with ship crater that remembers the ship it hit
            place(x,y, new Crater(shipVal, SHIP_CRATER));
            if (suggestedArray == null) {
                setSuggestedArray(x, y);
            }
		} else {
			place(x,y, waterCrater);
            suggestedArray = null;
		}
		return true;
	}

    public void setSuggestedArray(int x, int y) {
        suggestedArray = new int[4][2];
        suggestedArray[0][0] = x + 1;
        suggestedArray[0][1] = y;
        //
        suggestedArray[1][0] = x;
        suggestedArray[1][1] = y + 1;
        //
        suggestedArray[2][0] = x - 1;
        suggestedArray[2][1] = y;
        //
        suggestedArray[3][0] = x;
        suggestedArray[3][1] = y - 1;

        System.out.println(suggestedArray[0][0] + ":" +
        suggestedArray[0][1] + "   " +
        suggestedArray[1][0] + ":" +
        suggestedArray[1][1] + "   " +
        suggestedArray[2][0] + ":" +
        suggestedArray[2][1] + "   " +
        suggestedArray[3][0] + ":" +
        suggestedArray[3][1]);

    }

    //public int[][] poss

    public int[][] getSuggestedArray() {
        return suggestedArray;
    }

    public void placeCraters(int x, int y) {

    }

    /**
     * Generate random cords for a ship and place it
     * @param ship
     */
	private void placeShipRandom(Ship ship) {
		int row = 0, col = 0;
		boolean placed = false;
		// since the board size contains enough squares to hold all ships, we
		// loop until the ship can be placed
        try {
        int repeatLimit = 0;
		while (!placed) {
			// starting coord should not be near borders
			row = rand.nextInt(getDim() - 2) + 0;
			col = rand.nextInt(getDim() - 2) + 0;
            if (row == 0 || col == 0) {
                //System.out.println(row + " & " + col + " " + ship.cheatVal + " " + ship.getDirection());
            }
            // try each direction[N|S|E|W] for this coors
			for (int i=0; i<4; i++) {
                // check if ship can be placed
				if (isShipPositionValid(ship, row, col, ship.getDirection())) {
					placed = true;
				} else {
                    //try new direction[N|S|E|W]
					ship.setDirection((ship.getDirection() + 1) % 4);
				}
			}
            repeatLimit++;
            if (repeatLimit > 1000000) {
                throw new BattleshipException("Cannot put " + ship.getCheatVal() + "-ship");
            }
		}
		placeShip(ship, row, col);	// place verified ship
	}
        catch(BattleshipException e) {
            System.err.println(e.getMessage());
    }
    }

    /**
     * Place already verified ship on board by coords
     * @param ship
     * @param row
     * @param col
     */
	private void placeShip(Ship ship, int row, int col) {
		// input: ship, stating coords
		for (int i=0; i<ship.getHitPoints(); i++) {
			if (ship.getDirection() == Ship.NORTH) place(row-i, col, ship);
			else if (ship.getDirection() == Ship.SOUTH) place(row+i, col, ship);
			else if (ship.getDirection() == Ship.EAST) place(row, col+i, ship);
			else place(row, col-i, ship);
		}
        ship.setShipCoords(row, col);
	}

    /**
     * Ship position verification
     * @param ship
     * @param row
     * @param col
     * @param dir
     * @return
     */
	private boolean isShipPositionValid(Ship ship, int row, int col, int dir) {
		int rowInc = 0;
		int colInc = 0;
		// stepping factors based on direction
        //
		if (dir == Ship.NORTH) rowInc = -1;
		else if (dir == Ship.SOUTH) rowInc = 1;
		else if (dir == Ship.EAST) colInc = 1;
        //
		else if (dir == Ship.WEST) colInc = -1;
		// check borders. start row + (direction * size-1)
		int rowBound = row + (rowInc * (ship.getHitPoints() - 1)); //=row if horizontal
        int colBound = col + (colInc * (ship.getHitPoints() - 1)); //=col if vertical
        //System.out.println(row + " : " + col);
        //System.out.println("---------------------");
        //System.out.println(rowBound + " : " + colBound);
        if (rowBound < 1 || rowBound > getDim() - 2)
			return false;
		else if (colBound < 1 || colBound > getDim() - 2)
			return false;
		// put ship correctly
        int count = 0;
        //FIXME
            if (row==0 && col == 0) {
                while (count < ship.getHitPoints()
                        && !getPiece(row, col).isOccupied()
                        && !getPiece((row + 1), col).isOccupied()
                        && !getPiece(row, (col + 1)).isOccupied()
                        && !getPiece((row + 1), (col + 1)).isOccupied()
                        ) {

                    row += rowInc;
                    col += colInc;
                    count++;
                    //System.out.println(row + " " + rowInc + " | " + col + " " + colInc);
                }
            }
            if (!(col == 0) && row==0) {
                //System.out.println(row + " " + col);
                while (count < ship.getHitPoints()
                        && !getPiece(row, col).isOccupied()
                        && !getPiece(row, (col - 1)).isOccupied()
                        && !getPiece((row + 1), col).isOccupied()
                        && !getPiece(row, (col + 1)).isOccupied()
                        && !getPiece((row + 1), (col - 1)).isOccupied()
                        && !getPiece((row + 1), (col + 1)).isOccupied()) {

                    row += rowInc;
                    col += colInc;
                    count++;
                    //System.out.println(row + " " + rowInc + " | " + col + " " + colInc);
                }
            }
            if (!(row==0) && col == 0) {
                //System.out.println(row + " " + col);
                while (count < ship.getHitPoints()
                        && !getPiece(row, col).isOccupied()
                        && !getPiece((row - 1), col).isOccupied()
                        && !getPiece((row + 1), col).isOccupied()
                        && !getPiece(row, (col + 1)).isOccupied()
                        && !getPiece((row - 1), (col + 1)).isOccupied()
                        && !getPiece((row + 1), (col + 1)).isOccupied()
                        ) {

                    row += rowInc;
                    col += colInc;
                    count++;
                    //System.out.println(row + " " + rowInc + " | " + col + " " + colInc);
                }
            }
            if (!(row == 0) && !(col == 0)) {
                while (count < ship.getHitPoints()
                        && !getPiece(row, col).isOccupied()
                        && !getPiece((row - 1), col).isOccupied()
                        && !getPiece(row, (col - 1)).isOccupied()
                        && !getPiece((row + 1), col).isOccupied()
                        && !getPiece(row, (col + 1)).isOccupied()
                        && !getPiece((row + 1), (col - 1)).isOccupied()
                        && !getPiece((row - 1), (col + 1)).isOccupied()
                        && !getPiece((row + 1), (col + 1)).isOccupied()
                        && !getPiece((row - 1), (col - 1)).isOccupied()) {

                    row += rowInc;
                    col += colInc;
                    count++;
                    //System.out.println(row + " " + rowInc + " | " + col + " " + colInc);
                }
            }
		// if all cells are unoccupied we can place the ship here
        //System.out.println(count == ship.getHitPoints());
        return (count == ship.getHitPoints());
    }

    public boolean isHuman() {
        return human;
    }

    public void setHuman(boolean human) {
        this.human = human;
    }

}