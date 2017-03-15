#!/bin/bash
./BuildOnly.sh

echo "Running game..."
cd ../deploy/
java application/RunGame
