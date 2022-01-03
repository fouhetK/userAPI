# Build from sources


## Before using the API:

### Install Git

It is needed to have Git or a GUI (like TortoiseGit) installed on your PC

- to download Git : https://git-scm.com/downloads

### Java version

The current version of Java used in this API is the jdk-11.0.2

- It can be downloaded here : https://www.oracle.com/fr/java/technologies/javase/jdk11-archive-downloads.html


## The API

### Spring

The API use the v2.6.1 of Spring Boot.

## How to use the API:

### Download the API source code

If you are using Git without any UI, open a `Git Bash` console and use the commande
```git clone https://github.com/fouhetK/userAPI.git```

### Opening the project

Open the project with IntelliJ or any Java IDE.

:warning: The project is using Maven for its dependency, it may take some time for Maven to download them.

### Start the project

To start the project, Run the `UserApiApplication` situated in the `src/main/java/com/atos/userapi/` folder.

The API is currently using the basic configuration, meaning it will be usable on localhost with a specific port (8080 for windows).

### Logged information

The current API log the information in the console and in four differents files that are situated in the `logs` folder.
- SpringBoot2App/application.log  for all the logs of the Spring framwork.
- Hibernate/application.log to register all the message of the Hibernate framwork.
- AOP/application.log to register the information about each request using the Aspect Oriented Programming (Parameters, time execution, error and return value).
- Error/application.log to register all Error that doesn't come from the three precedent sources.

All the information about the logging are located in the `log4j2.xml` within the `src/main/resources/` folder.

The default value for the Logger are `debug` for the AOP, Hibernate and Spring logger and `error` for the Error.


## Tests

All the tests are contained within the `src/test/java/com/atos/userapi/` folder.

To run the test and see the result, right click on the test and select `Run`

### Unit tests

1. The UserEntityTest test if the data are not corrupted when created.
1. The UserServiceTest use a Mock repository.
2. The UserApiTest use a Mock Service and a Mock Mvc.

### Integration tests

The integration test is made in three step.
1. The test between the UserService and the UserRepository.
2. The test between the UserApi and the UserService with a Mock Mvc to emulate the Post/Get message.
3. The test between Postman and our API.
   - For this test, the API must be started and Postman used to send Post/Get message.
   - A collection of Postman request are present within the `Tests Postman` folder.
   - Within this collection, the request are separated in three:
     - The Get at the base of the collection.
     - The Post request that result in a Success in the "Success" folder.
     - The Post request that result in an Error in the "Error" folder.
