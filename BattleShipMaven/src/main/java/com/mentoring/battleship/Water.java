package com.mentoring.battleship;
/**
 * Water class
 * nonCheatMode [-]
 * cheatMode [-|[A..E]]
 */
public class Water extends BoardCell {

    //Constructor. Create water cell
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

