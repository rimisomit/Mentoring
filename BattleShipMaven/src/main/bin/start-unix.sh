#!/bin/sh

libDir='lib/*'
configDir='config/*'
resDir='/resources/*'
jarName='/lib/bt.jar'
mainClass='com.mentoring.battleship.Battleship'

cd ..
java -cp ${libDir}:${configDir}:${resDir}:template.xls:${jarName} ${mainClass}
$SHELL