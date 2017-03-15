#!/bin/bash
./BuildOnly.sh

echo "Running game..."
cd bin/
java application/RunGame
