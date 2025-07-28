#!/bin/zsh
export https_proxy=http://127.0.0.1:xxxx
wget https://www.phoronix.com/rss.php -O phoronix_rss.xml
cd /Users/spriple/Codes/Pbot/Main
/opt/homebrew/bin/mvn clean compile
/opt/homebrew/bin/mvn exec:java -Dexec.mainClass=Main
cd ~
