package com.mentoring.battleship;
/**
 * Abstract Supper class, extension for Ship, Crater and Water
 */
public abstract class BoardCell {
	
	public String cheatVal;		// "view/show ships" representation
	public String nocheatVal;		// "view/show board" representation

    /**
     *  How to fill in a cell in cheatMode and nonCheatMode
     * @param cheat
     * @param nocheat
     */
    public BoardCell(String cheat, String nocheat) {
		cheatVal = cheat;
		nocheatVal = nocheat;
	}

    public String getVal(boolean cheat) {
		return cheat ? cheatVal : nocheatVal;
	}
	// is this cell currently occupied?
	public abstract boolean isOccupied();
	// was the cell already hit by a missile (i.e. it is a crater)?
	public abstract boolean wasHit();
	// does firing at this cell produce a hit?
	public abstract boolean fireAt();
}

