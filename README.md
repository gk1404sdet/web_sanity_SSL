<<<<<<< HEAD
# web_sanity_SSL
=======
# moolya-test-automation
This project comprises automated UI tests for moolya.

The automation framework is designed by following the principles of behavior-driven development which promotes writing the tests in simple english. Cucumber tool is used to support this.

Page Object model design pattern is incorporated, which represents the pages of the application as objects that helps in improving code maintainability and readability.

## Tools
* Selenium WebDriver
* Java
* TestNG
* Rest Assured
* Maven

## Requirements
In order to utilize the project, you need to have the following installed:

* Java
* Chrome
* Maven

## Pushing code

Before pushing your code, do the following:

1. review the changes, `git diff` or your tool of preference.
2. run tests, ensure everything works.
3. commit your code
4. push code.

## Pre-Requisites
1. install java 21
2. create a file named creds.properties with the following data. (might need to add more credentials based on the type of test you are running. The following is a sample)
    ```properties
    test_username=sbx_ci_4nP7jjF1MPJJEQz76iwui5k7HTMbrbW2P6XbcN65CeiD
    test_password=sbx_cs_B8oSq77mBDsSf31HULwC7ZyBX1RtL29z72xEVS5HKJzU
   ```
   *Note:* Never check this file in
## Usage
To run all the tests, navigate to `moolya-test-automation` directory and run:

```
mvn test -DcredsFilePath=creds.properties
```

To run the tests in headless browser:

```
mvn test -DcredsFilePath=creds.properties \
        -DbrowserMode=headless
```

To run only the tests with a specific tag:

```
mvn test -DcredsFilePath=creds.properties \
        -Dcucumber.filter.tags=<Tag>
```

Example Command:

```
mvn test -DcredsFilePath=creds.properties \
        -DbrowserMode=headless \
        -Dcucumber.filter.tags="@SmokeTest"
```

### Parameters

`-DcredsFilePath` - Path to creds file. If relative path, start from repo base directory.

`-DbrowserMode`  - (Optional) If the tests need to be executed in headless mode, provide the value as 'headless'.

`-DDcucumber.filter.tags` - (Optional) Provide the specific scenario tags to be executed here. For example: '@RegressionTest' '@SmokeTest'

`-Denv` - (Optional) Chooses the checkout demo page url based on this. Default is `DEV`

# Web Sanity Automation by GK

This repo is for web sanity automation framework.  
Initial setup for sanity script execution.

## Reporting

Cucumber HTML test execution reports can be found in `moolya-test-automation/target/cucumber/cucumber.html`.
>>>>>>> 7115034 (Web Sanity Script)
