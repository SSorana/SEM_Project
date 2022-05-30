# Sprint Retrospective, Iteration # 1

| Story ID/ Category                                                            | Task #                                                                        | Task Assigned to                     | Estimated effort per task (in hours) | Actual effort per task (in hours) | Done (yes/no) | Notes |
|-------------------------------------------------------------------------------|-------------------------------------------------------------------------------|--------------------------------------|--------------------------------------|-----------------------------------|---------------|-------|
| As a User I want to be able to subscribe to a basic or a premium subscription | - Make controllers services and repositories for Team, User, Equipment, Field | Jorn Dijk                            | 2                                    | 2                                 | yes           |       |
|                                                                               | - Make entities for User and Team                                             | Jorn Dijk                            | 1                                    | 1                                 | yes           |       |
|                                                                               | - Make endpoint to subscribe to basic subscription                            | Jorn Dijk                            | 1                                    | 1                                 | yes           |       |
|                                                                               | - Make endpoint to subscribe to premium subscription                          | Jason Miao                           | 1                                    | 1                                 | yes           |       |
|                                                                               |                                                                               |                                      |                                      |                                   |               |       |
| As a User I want to be able to create an account                              | - Implement functionality to the controllers and services                     | Joppe Boerop                         | 1                                    | 2                                 | yes           |       |
|                                                                               | - Add tests                                                                   | Kieran McAlpine                      | 1                                    | 2                                 | yes           |       |
|                                                                               | - Add database connection logic                                               | Kieran McAlpine                      | 1                                    | 3                                 | yes           |       |
|                                                                               |                                                                               |                                      |                                      |                                   | yes           |       |
| As a user I want to be able to login into my account                          | - Make entities for Reservation, FieldReservation, EquipmentReservation       | Sorana Stan                          | 2                                    | 3                                 | yes           |       |
|                                                                               | - Make entity for Field                                                       | Willem van de Sanden                 | 1                                    | 1                                 | yes           |       |
|                                                                               | - Make tests for UserService                                                  | Willem van de Sanden                 | 2                                    | 2                                 | yes           |       |
|                                                                               | - Make methods for login controller & repository & service                    | Willem van de Sanden and Sorana Stan | 2                                    | 3                                 | yes           |       |

## Main Problems Encountered
### Problem 1:
Did not understand how to implement microservices

### Problem 2:
Did not know how to connect microservices

## Adjustments for the next Sprint Plan
Do research and learn more about how microservices work


# Sprint Retrospective, Iteration # 2

| Story ID/ Category                                           | Task #                                            | Task Assigned to         | Estimated effort per task (in hours) | Actual effort per task (in hours) | Done (yes/no) | Notes |
|--------------------------------------------------------------|---------------------------------------------------|--------------------------|--------------------------------------|-----------------------------------|---------------|-------|
| Reorganise the template to fit the microservice architecture | - Make seperate modules for the microservices     | Jorn Dijk and Jason Miao | 1                                    | 5.5                               | yes           |       |
|                                                              | - Make a Eureka server                            | Jorn Dijk and Jason Miao | 2                                    | 5.5                               | yes           |       |
|                                                              | - Begin on the Team and Equipment microservice    | Jorn Dijk                | 0.5                                  | 1                                 | yes           |       |
|                                                              | - Add seperate databases for the microservices    | Jorn Dijk and Jason Miao | 0.5                                  | 1                                 | yes           |       |
|                                                              |                                                   |                          |                                      |                                   |               |       |
|                                                              | - Reconfigure database logic for new architecture | Kieran McAlpine          | 2                                    | 4                                 | No            |       |

## Main Problems Encountered
### Problem 1:
Creating the component diagram for assignment 1

### Problem 2:
Malnourished communication

### Problem 3:
Deciding on the design pattern

## Adjustments for the next Sprint Plan
-Understand how components diagram work
-Be more proactive in communication with the group
-Come to a conclusion of a decision faster



# Sprint Retrospective, Iteration # 3

| Story ID/ Category           | Task #                                                          | Task Assigned to | Estimated effort per task (in hours) | Actual effort per task (in hours) | Done (yes/no) | Notes                                                                 |
|------------------------------|-----------------------------------------------------------------|------------------|--------------------------------------|-----------------------------------|---------------|-----------------------------------------------------------------------|
|                              | - Add missing folders and entities for Reservation microservice | Sorana Stan      | 0.5                                  | 1                                 | Yes           |                                                                       |
|                              | - Added tests in the Reservation microservice                   | Sorana Stan      | 2                                    | 2                                 | Yes           |                                                                       |
|                              |                                                                 |                  |                                      |                                   |               |                                                                       |
| Create the user microservice | - Create user entity and tests                                  | Jason Miao       | 1                                    | 0.5                               | Yes           | Yet to complete all the features in the microservice                  |
|                              | - Create user repository                                        | Jason Miao       | 1                                    | 1.5                               | Yes           | Yet to complete all the controller test methods                       |
|                              | - Create user controller and service tests                      | Jason Miao       | 0.5                                  | 0.5                               | Yes           |                                                                       |
|                              | - Create Reservation controller, service and repository         | Jorn Dijk        | 0.5                                  | 0.5                               | No            | Made basis for the classes but did not implement all requirements yet |

## Main Problems Encountered
### Problem 1:
Proper use of milestones


## Adjustments for the next Sprint Plan
Have a meeting to discuss and implement proper milestones



# Sprint Retrospective, Iteration # 4

| Story ID/ Category                         | Task #                                                                            | Task Assigned to     | Estimated effort per task (in hours) | Actual effort per task (in hours) | Done (yes/no) | Notes                                                |
|--------------------------------------------|-----------------------------------------------------------------------------------|----------------------|--------------------------------------|-----------------------------------|---------------|------------------------------------------------------|
| Create authentication microservice         | - Add microservice structure and folders                                          | Sorana Stan          | 0.5                                  | 1                                 | Yes           |
|                                            | - Add authentication service and token creation                                   | Kieran McAlpine      | 4                                    | 6                                 | Yes           |
|                                            | - Add login service and token creation                                            | Sorana Stan          | 4                                    | 5                                 | No            | Still needs to be implemented
|                                            |                                                                                   |                      |                                      |                                   |               |
| Add field microservice functionality       | - Add isHall to Field microservice to determine difference between field and hall | Willem van de Sanden | 1                                    | 1                                 | Yes              |
|                                            | - Add field entity and tests                                                      | Willem van de Sanden | 1                                    | 1                                 | Yes              |
|                                            | - Add field controller and tests                                                  | Willem van de Sanden | 1                                    | 1                                 | Yes              |
|                                            | - Add field service and tests                                                     | Willem van de Sanden | 1                                    | 1                                 | Yes              |
|                                            | - Add field repository                                                            | Willem van de Sanden | 0.5                                  | 0.5                               | Yes              |
|                                            |                                                                                   |                      |                                      |                                   |               |
| Add equipment microservice functionality   | - Create equipment entity and tests                                               | Joppe Boerop         | 1                                    | 1                                 | Yes           |
|                                            | - Create equipment repository                                                     | Joppe Boerop         | 1                                    | 1.5                               | Yes           |
|                                            | - Create equipment controller and tests                                           | Joppe Boerop         | 0.5                                  | 0.5                               | Yes           |
|                                            | - Create equipment service and tests                                              | Joppe Boerop         | 0.5                                  | 0.5                               | Yes           |
| Add reservation microservice functionality |                                                                                   |                      |                                      |                                   |               |
| As a user I want to make a reservation     | - implement controller, service and repository logic                              | Jorn Dijk            | 1                                    | 2                                 | Yes           | Only the basic functionality
| As a user I want to remove a reservation   | - implement controller, service and repository logic                              | Jorn Dijk            | 0.5                                  | 0.5                               | Yes           |
| As a user I want to search a reservation   | - implement controller, service and repository logic                              | Jorn Dijk            | 0.5                                  | 0.5                               | Yes           |
|                                            | - Add checks for if making reservation are valid                                  | Jorn Dijk            | 2                                    | 6                                 | No            | The reservations for all team members are not yet added
Enhance Code                               |                                                                                   |                      |                                      |                                   |               |
| Configure Database for each Microservice   | - Add a config file and properties file to each microservice                      | Kieran McAlpine      | 0.5                                  | 0.5                               | Yes           |
| Fix Pmd violations                         | - Fix PMD violations and add lombok                                               | Kieran McAlpine      | 2                                    | 3                                 | Yes           |
| Add team implementations to user microservice | - team functionalities                                                         | Jason Miao           | 2                                    | 2                                 | Yes           | Will continue to work on the functionalities in the following weeks

## Main Problems Encountered
### Problem 1:
Hidden PMD error in field controller. Found out where it was eventually by the trace of the error.


# Sprint Retrospective, Iteration # 5

| Story ID/ Category              | Task #                                                              | Task Assigned to | Estimated effort per task (in hours) | Actual effort per task (in hours) | Done (yes/no) | Notes                                                       |
|---------------------------------|---------------------------------------------------------------------|------------------|--------------------------------------|-----------------------------------|---------------|-------------------------------------------------------------|
| Create a command line interface | - Research cli's for spring                                         | Kieran McAlpine  | 1                                    | 2                                 | Yes           |                                                             |
|                                 | - Implement generic structure                                       | Kieran McAlpine  | 0.5                                  | 0.5                               | Yes           |                                                             |
|                                 | - Add microservice communication                                    | Kieran McAlpine  | 6                                    | 10                                | No            | Still need to add some more interactions and fix pmd issues |
| Help with reservation           | - Help with cleaning up reservation microservice and design pattern | Kieran McAlpine  | 2                                    | 4                                 | Yes           | Jorn still needs to implement some of the advice given      |
| Started user microservice test  | - Started user service testing                                      | Jason Miao       | 1.5                                  | 1.5                               | No            | Some tests still do not pass, will work on them


## Main Problems Encountered
### Problem 1:
Reservation microservice was messy and design pattern was wrongly implemented. Brainstorming together fixed it.


# Sprint Retrospective, Iteration # 6

| Story ID/ Category    | Task #                           | Task Assigned to | Estimated effort per task (in hours) | Actual effort per task (in hours) | Done (yes/no) | Notes                                      |
|-----------------------|----------------------------------|------------------|--------------------------------------|-----------------------------------|---------------|--------------------------------------------|
| Enhance CLI           | - Resolve Pmd violations         | Kieran McAlpine  | 1.5                                  | 2                                 | Yes           |                                            |
|                       | - Add exceptions                 | Kieran McAlpine  | 2                                    | 3                                 | No            | Not all classes have custom exceptions yet |


# Sprint Retrospective, Iteration # 7

| Story ID/ Category               | Task #                                | Task Assigned to | Estimated effort per task (in hours) | Actual effort per task (in hours) | Done (yes/no) | Notes |
|----------------------------------|---------------------------------------|------------------|--------------------------------------|-----------------------------------|---------------|-------|
| Authentication                   | - Add login service implementation    | Sorana Stan      | 5                                    |                                   |               |       |
|                                  | - Add login controller implementation | Sorana Stan      | 2                                    |                                   |               |       |
|                                  | - Add zuul routes and add permission  | Kieran McAlpine  | 2                                    | 4                                 |               |       |
| Improve tests                    | - Add equipment microservice tests    | Joppe Boerop     | 1                                    | 1                                 | Yes           |       |
|                                  | - Add reservation microservice tests  | Joppe Boerop     | 3                                    | 3                                 | Yes           |       |
| Improve tests                    | - Add user microservice tests         | Kieran McAlpine  | 0.5                                  | 0.5                               | Yes           |       |
|                                  | - Add reservation microservice tests  | Kieran McAlpine  | 2                                    | 2                                 | Yes           |       |
|                                  | - Add field microservice tests        | Kieran McAlpine  | 1                                    | 1                                 | Yes           |       |
|                                  | - Add integration tests for databases | Kieran McAlpine  | 2                                    | 2                                 | Yes           |       |
| Improve reservation microservice | - Rewrite queries                     | Kieran McAlpine  | 1                                    | 1.5                               | Yes           |       |
|                                  | - Implement design pattern            | Kieran McAlpine  | 4                                    | 6                                 | Yes           |       |
|                                  | - Small changes                       | Kieran McAlpine  | 1                                    | 1                                 | Yes           |       |
| Add custom exceptions            | - Rewrite controllers                 | Kieran McAlpine  | 1.5                                  | 1.5                               | Yes           |       |
|                                  | - Fix tests                           | Kieran McAlpine  | 0.5                                  | 1                                 | Yes           |       |
|                                  | - Add exceptions                      | Kieran McAlpine  | 1                                    | 1                                 | Yes           |       |
| Completing user microservice test  | - Completed user microservice tests    | Jason Miao    | 2                         | 3                                 | Yes