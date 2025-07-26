cd /Users/spriple/Codes/Pbot/PyScraper
source ./venv/bin/activate
python main.py
cd /Users/spriple/Codes/Pbot/Main
mvn clean compile
mvn exec:java -Dexec.mainClass=HttpHandler
cd ~
