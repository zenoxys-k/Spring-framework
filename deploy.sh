#!/bin/bash

APP_NAME="framework"
SRC_DIR="./src"
BUILD_DIR="./bin"
LIB_DIR="./lib"
SERVLET_API_JAR="$LIB_DIR/servlet-api.jar"

echo "🧹 Nettoyage..."
rm -rf $BUILD_DIR
mkdir -p $BUILD_DIR

echo "📦 Compilation..."

find $SRC_DIR -name "*.java" > sources.txt

javac -cp "$SERVLET_API_JAR" -d $BUILD_DIR @sources.txt

rm sources.txt

echo "📦 Création du WAR..."

jar -cvf $APP_NAME.jar -C $BUILD_DIR .

echo "🚀 Déploiement terminé. Copie vers Tomcat si nécessaire."