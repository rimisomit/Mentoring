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
	public boolean fireAt() {
		System.out.println(Battleship.msg_MISS);
		return false;
	}
}

