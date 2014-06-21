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
	private boolean sunk = false;

    // Constructor.
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
	// Setters
	public void setSunk(boolean val) {
        sunk = val;
    }
	public void setDirection(int dir) {
        this.dir = dir;
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
	public boolean fireAt() {
		// user hit a ship
		System.out.println(Battleship.msg_HIT);
        //decreased hit points by one
		hitPoints--;
        //check if no hit points
		if (hitPoints == 0) {
			System.out.println(Ship.this.cheatVal + "-ship " + Battleship.msg_SUNK);
			sunk = true;
		}
		return true;
	}
}

