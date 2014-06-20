@echo off
set target=C:\Users\user\Dropbox\git\Mentoring\BattleShipMaven\target\BattleShip-1.0-SNAPSHOT.jar
set destination=C:\Users\user\Dropbox\git\Mentoring\BattleShipMaven\bin\bt.jar
set config=C:\Users\user\Dropbox\git\Mentoring\BattleShipMaven\config
set lib=C:\Users\user\Dropbox\git\Mentoring\BattleShipMaven\lib
copy %target% %destination%
java -cp %config%\*;%lib%\*;bt.jar com.mentoring.battleship.Battleship
::PAUSE