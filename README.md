# SauceDemo Automation Framework

#SauceDemo Test Automation  
Automated UI tests for [SauceDemo](https://www.saucedemo.com/) using:  
✔ **Java** + **Selenium WebDriver**  
✔ **TestNG** for test management  
✔ **Allure Reports** for visualization  
✔ **Page Object Model (POM)** design 

## Tech Stack
- Java 17
- TestNG
- Selenium 4
- WebDriverManager

## How to Run
1. Clone the repository (then cd to the repo)
2. Run tests and generate report by running the command below:
   mvn clean test allure:report

3. Viewing Test Reports:
Option 1: Auto-open in browser (Recommended)
   allure:serve
This will start a local server and automatically open the Allure report in your browser.
