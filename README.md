# Automated Testing of Jira API using Serenity and Rest Assured

This project demonstrates how to automate testing of Jira REST APIs using Serenity BDD framework with Rest Assured in Java.

## Features

- Automated testing of Jira APIs for various operations including issue management.
- APIs included:
  - Create Issue
  - Update Issue
  - Get Issue Details
  - Add Comment to Issue
  - Update Comment
  - Add Attachment to Issue

## Prerequisites

- Java Development Kit (JDK) installed
- Maven installed
- IDE (like IntelliJ IDEA, Eclipse) for editing and running the tests
- Access to a Jira instance (cloud or server) with appropriate permissions and API token
## Setup
1. Clone this repository to your local machine:

```bash
1. git clone https://github.com/ritesh2195/Jira-API-Testing-Serenity-Rest-Assured.git
2. Open the project in your IDE.
3. Update serenity.properties file with your Jira base URL, username, and API token.
4. Install dependencies using Maven:**mvn clean install**

## Usages
1. Run the tests: mvn clean verify
2. View test results in the console or in the target/site/serenity directory for detailed reports.

## Configuration
1. Update serenity.properties file with your Jira base URL, username, and API token.
2. Modify test scenarios or add new ones in the src/test/java directory.

## Folder Structure
1. src/test/java: Contains test scripts.
2. src/main/java: Contains helper classes and configurations.
3. src/test/resources: Contains test data or resources.

