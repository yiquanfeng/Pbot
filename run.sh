#!/bin/zsh
cd /Users/spriple/Codes/Pbot/Main
export https_proxy=http://127.0.0.1:xxxx
wget https://www.phoronix.com/rss.php -O phoronix_rss.xml
/usr/bin/mvn clean compile
/usr/bin/mvn exec:java -Dexec.mainClass=Main
cd ~
