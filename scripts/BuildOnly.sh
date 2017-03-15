#!/bin/bash
echo "Building project..."
cd ..	#Go to project root

THE_CLASSPATH=
PROGRAM_NAME=application/RunGame.java

mkdir -p deploy	#Make deploy output folder

cd src

javac -d ../deploy -classpath ".:${THE_CLASSPATH}" $PROGRAM_NAME

if [ $? -eq 0 ]
then
  echo "Build successful."
fi


# #!/bin/bash
# cd src
# mkdir -p ../bin
# javac -d ../bin */*.java */*/*.java
# ln -s ../out/application/Game.class ../Game.class
