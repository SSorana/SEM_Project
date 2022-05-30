# CSE2115 - Sports Reservation System, Group 26B
## Description of project
A reservation application a sports centre.

## How to run it

### Setting up database
The application is set up to be able to run with postgres or non-persistent h2

* Using postgres: `spring.profiles.active=production` should be in `application.properties`  
* Using h2: `spring.profiles.active=development` should be in  `application.properties`  \
  &nbsp;

`application.properties` can be found in the resources package of each microservice.

#### Using Postgres
* Change postgres username and password in `application-production.properties`
* Create databases called: 
  * auth
  * equipment
  * field
  * reservation
  * user

### Running program

`gradle bootRun` Or start every microservice through its main

## Testing
```
gradle test
```

To generate a coverage report:
```
gradle jacocoTestCoverageVerification
```


And
```
gradle jacocoTestReport
```
The coverage report is generated in: build/reports/jacoco/test/html, which does not get pushed to the repo. Open index.html in your browser to see the report. 

### Static analysis
```
gradle checkStyleMain
gradle checkStyleTest
gradle pmdMain
gradle pmdTest
```

### Test results

| Microservice | Instruction coverage | Branch coverage |
|--------------|----------------------|-----------------|
| Equipment    | 76%                  | 100%            |
| Field        | 62%                  | 100%            |
| Reservation  | 92%                  | 100%            |
| User         | 71%                  | 100%            |

All the above microservice also have integration tests for their databases.
Authentication microservice has no tests. \
The purpose of the CLI is a manual end-to-end integration test, it's not setup to use authentication.

### Notes
- You should have a local .gitignore file to make sure that any OS-specific and IDE-specific files do not get pushed to the repo (e.g. .idea). These files do not belong in the .gitignore on the repo.
- If you change the name of the repo to something other than template, you should also edit the build.gradle file.
- You can add issue and merge request templates in the .gitlab folder on your repo. 