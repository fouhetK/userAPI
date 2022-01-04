# Build from sources


## Before using the API:

### Install Git

To work on the project, It is needed to have Git or a GUI (like TortoiseGit) installed on your Computer.

- To download Git : [Git](https://git-scm.com/downloads)

### Java version

The current version of Java used in this API is the jdk-11.0.2

- It can be downloaded here : [Java jdk-11.0.2](https://www.oracle.com/fr/java/technologies/javase/jdk11-archive-downloads.html)

⚠️When downloading new jdk, don't forget to change the environment variables of your system.

### Download API

If you are using Git without any UI, open a `Git Bash` console and use the command:
```git clone https://github.com/fouhetK/userAPI.git```

### Opening the project

Open the project with IntelliJ or any Java IDE.

⚠️ The project is using Maven for its dependency, it may take some time for Maven to download them.


## The API

### Spring

The API was made using the v2.6.1 of Spring Boot.

### Database

The API use an empty embedded database H2.

### Logged information

The current API logs the information in the console and in four different files that are situated in the `logs` folder.
- `SpringBoot2App/application.log` for all the messages of the Spring framework.
- `Hibernate/application.log` for all the messages of the Hibernate framework.
- `AOP/application.log` to register the information about each request using the Aspect Oriented Programming (Parameters, time of execution, error and return value).
- `Error/application.log` to register all Error that doesn't come from the three precedent sources.

All the information about the logging are located in the `log4j2.xml` within the `src/main/resources/` folder.

The default value for the logs level are `debug` for the AOP, Hibernate and Spring logger and `error` for the Error.


## How to use the API:

### Start the project

To start the project, Run the `UserApiApplication` situated in the `src/main/java/com/atos/userapi/` folder.

The API is currently using the basic configuration, meaning it will be usable on localhost with a specific port (8080 for Windows).

If you are using another operating system than Windows, the port will be displayed in the console of the IDE used like:
```2022-01-03T17:58:09.819+0100 INFO Tomcat initialized with port(s): 8080 (http)```

### Available Call

The current API allow three requests:
1. Get to `http://localhost:8080/api/user` to recuperate the informations of all the Users present in the database.
2. Get to `http://localhost:8080/api/user/{id}` where `{id}` is the `id` of the user you want to recuperate the informations of.
   - If the `Id` is not found, the API return an Error 404 User not found.
3. Post to `http://localhost:8080/api/user`
   - The body of the Post request must contain the information of the User in JSON format.
   - For a user to be created, he need three variable, a `username`, a `country` and a `birthdate`.
     - The username have no restriction.
     - The country must be `France` if you want the User to be registered successfully.
     - The birthdate must be in `yyyy-mm-dd` to be valid. ⚠️The user must be an Adult (above 18 years old) to be registered successfully.
   - The user also possesses two "optional" variables, a `gender` and a `phone`.
     - Currently, the API only accept `M` for Male and `F` for Female as a gender.
     - The Phone number accept different format (xx xx xx xx xx, +33x xx xx xx xx, xx xxxx xxxx , xx-xx-xx-xx-xx, xxxxxxxxxx ...)


## Tests

All the tests are contained within the `src/test/java/com/atos/userapi/` folder.

To run the test and see the result, right click on the test and select `Run`.

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
