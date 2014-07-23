package com.mentoring.battleship;
/**
 * Water class
 * nonCheatMode [-]
 * cheatMode [-|[A..E]]
 */
public class Water extends BoardCell {

    /**
     * Create water cell
     * @param cheat
     * @param nocheat
     */
	public Water(String cheat, String nocheat) {
		super(cheat, nocheat);
	}

    @Override
	public boolean isOccupied() {
        return false;
    }

    @Override
	public boolean wasHit() {
        return false;
    }

    @Override
    public boolean wasSunk() {
        return false;
    }

    @Override
	public boolean fireAt() {
		System.out.println(Battleship.MSG_MISS);
		return false;
	}

    public int getShipStarRow() { return -1; }
    public int getShipEndRow() { return -1; }
    public int getShipStarColumn() { return -1; }
    public int getShipEndColumn() { return -1; }
}

