# Phoronix QQNT Bot(Pbot)
## architecture
Protocol : [OneBot11](https://github.com/botuniverse/onebot)  
the OneBot11 implementation: [NapCat](https://github.com/NapNeko/NapCatQQ)  
BotPlatform: QQNT  
Application Backward: Server using Java(this Project)  
Scraper: Python Service using Selenium and undetected_chromedriver  

## now Function
- time compare and multitask balance
- scrape the phoronix rss file  
- rss file parse using DOM and xxx  
- json message using Gson
- send api requests to NapCat server (now is only /send_group_msg)

bug-report
- if i remove time update, then some news will repeat


