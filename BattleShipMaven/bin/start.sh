#!/bin/sh
cp ../target/BattleShip-1.0-SNAPSHOT.jar bt.jar
java -cp ../lib/*:../config/*:bt.jar com.mentoring.battleship.Battleship
