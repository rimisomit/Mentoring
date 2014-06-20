#!/bin/sh
cp ../target/BattleShip-1.0-SNAPSHOT.jar bt.jar
libDir='../lib/*'
configDir='../config/*'
resDir='../resources/*'
jarName='bt.jar'
mainClass='com.mentoring.battleship.Battleship'
java -cp ${libDir}:${configDir}:${resDir}:${jarName} ${mainClass}
