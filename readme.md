# Trade Reporting Engine

A Java program (SpringBoot) that reads a set of XML event files, extracts a set of
elements (fields), stores them in DB, filters the events based on a set of criteria, and reports the events in JSON Format.

## Pre-requisites

Java version >= 17

Gradle version >=7.6.1

Git version >= 2.3


## Instructions for running the application
git clone this project using below command
```bash
git clone https://github.com/sarfrazgithub/tradereportingengine.git
```

Go inside the project folder
```bash
cd tradereportingengine/
```

Start the application ( application runs on default port 8080)
```bash
./gradleW bootrun
```

Access the Reports API using below curl command or hit the url using browser/postman

```bash
curl --GET http://localhost:8080/api/v1/reports
```

## Explanation

I have used a singleton design pattern and a simple CRUD app flow for this assessment.

The provided XML events are placed in the application under src/main/resources/events.

After the application startup, these XML events are parsed and stored in the H2 database using a command line runner.

Totally there are 3 criteria for generating the report.

For the first two criteria, I have used JPA Specification to filter the records at the DB level. When the requirement changes, we can update the specifications accordingly to filter the record.

For the third criterion, we need to check whether the seller and buyer party references are not anagrams, I have written a java predicate as it will be a high-cost operation if we have to perform the same check at the DB level.

So before generating the reports, we will get the data from DB for which the first two criteria is met and then we will perform stream and filter operation for the third criteria.





 