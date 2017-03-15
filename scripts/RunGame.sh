#!/bin/bash
./BuildOnly.sh

echo "Running game..."

#Hacky way to make custom key config work from shell script
mkdir -p ../deploy/src/configs
cp ../src/configs/KeyBindings.properties ../deploy/src/configs/

cd ../deploy/

java application/RunGame
