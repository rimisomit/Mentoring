package com.mentoring.battleship;

/**
 * Ship subclass
 * Used enum data structure
 */

public class Ship extends BoardCell {
	
	// valid ship directions
	public static int NORTH = 0;
	public static int SOUTH = 1;
	public static int EAST = 2;
	public static int WEST = 3;
	
	// const data structure
	public enum ShipType {
		//Ship Type (getHitPoints, Symb in cheat mode, Symb in non cheat mode)
		AIRCRAFT (5, "A", Board.WATER),
		BATTLESHIP (4, "B", Board.WATER),
		CRUISER (3, "C", Board.WATER),
		DESTROYER (2, "D", Board.WATER),
        PATROL (1, "E", Board.WATER);
		// variables
		private final int hitPoints;
		private final String cheatVal;
		private final String nocheatVal;

		// constructor
		ShipType(int hitPoints, String cheatVal, String nocheatVal) {
			this.hitPoints = hitPoints;
			this.cheatVal = cheatVal;
			this.nocheatVal = nocheatVal;
		}
		// Methods. Getters for ShipType
		public int getHitPoints() {
            return hitPoints;
        }
		public String getCheatVal() {
            return cheatVal;
        }
		public String getNoCheatVal() {
            return nocheatVal;
        }
	}
	// ship data
	private int hitPoints;
	private int dir;
    private int startRow;
    private int startColumn;
    private int endRow;
    private int endColumn;
	private boolean sunk = false;
    //private boolean isHit = false;

    /**
     *
     * @param ship
     * @param dir
     */
	public Ship(ShipType ship, int dir) {
		super(ship.getCheatVal(), ship.getNoCheatVal());
		this.hitPoints = ship.getHitPoints();
		this.dir = dir;
	}

	//Getters
	public boolean getSunk() {
		return sunk;
	}
    public String getCheatVal () { return this.cheatVal; }
	public int getDirection() {
		return dir;
	}
	public int getHitPoints() {
        return hitPoints;
    }
    public int getShipStarRow() { return startRow; }
    public int getShipEndRow() { return endRow; }
    public int getShipStarColumn() { return startColumn; }
    public int getShipEndColumn() { return endColumn; }

	// Setters
    //TODO this.sunk vs sunk

    /**
     *
     * @param val
     */
	public void setSunk(boolean val) {
        this.sunk = val;
    }
	public void setDirection(int dir) {
        this.dir = dir;
    }
    public void setShipCoords(int startRow, int startColumn) {
        this.startRow = startRow;
        this.startColumn = startColumn;
        if (dir == 2) {
           this.endColumn = startColumn + hitPoints - 1;
           this.endRow = startRow;
        }
        if (dir == 3) {
            this.endColumn = startColumn - hitPoints + 1;
            this.endRow = startRow;
        }
        if (dir == 0) {
            this.endRow = startRow - hitPoints + 1;
            this.endColumn = startColumn;
        }
        if (dir == 1) {
            this.endRow = startRow + hitPoints - 1;
            this.endColumn = startColumn;
        }
        if (this.endColumn < startColumn) {
            int temp = endColumn;
            this.endColumn = startColumn;
            this.startColumn = temp;
        }
        if (this.endRow < startRow) {
            int temp = endRow;
            this.endRow = startRow;
            this.startRow = temp;
        }
        }

	@Override
	public boolean isOccupied() {
        return true;
    }
	@Override
	public boolean wasHit() {
        return false;
    }
    @Override
    public boolean wasSunk() {
        return (hitPoints == 0);
    }
	@Override
	public boolean fireAt() {
		// user hit a ship
		System.out.println(Battleship.MSG_HIT);
        //decreased hit points by one
		hitPoints--;
        //isHit = true;
        //check if no hit points
		if (hitPoints == 0) {
			System.out.println(Ship.this.cheatVal + "-ship " + Battleship.MSG_SUNK);
			sunk = true;
		}
		return true;
	}
}

