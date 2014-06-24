package com.mentoring.battleship;
/**
 * Crater subclass, on Board cell hit.
 * nonCheatMode [X|O]
 * cheatMode [X|[A..E]]
 */
public class Crater extends BoardCell {

    // Constructor. display crater in cheat and nonCheatMode
	public Crater(String cheatVal, String noCheatVal) {
		super(cheatVal, noCheatVal);
	}
	@Override
	public boolean isOccupied() {
        return true;
    }
	@Override
	public boolean wasHit() {
        return true;
    }

    /**
     *
     * @return
     */
	@Override
	public boolean fireAt() {
		System.err.println("This should never happens");
		assert false;
		return false;
	}
}