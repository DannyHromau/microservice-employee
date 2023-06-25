# Microservice-employee

This is an application(backend) for creating, deleting and editing employee accounts in the company.

# IntelliJ IDE

JetBrains provide a the IntelliJ IDE.

This IDE is recommended for excellent Maven integration, and very fast build times.

# Tools
 * Java 17
 * Spring framework (boot, data, mvc, logging, test)
 * Maven
 * Lombok
 * PostgreSQL
 * Liquibase
 * Mapstruct
 * Swagger

# Dependencies

All required dependencies are listed in the 'pom.xml' file.

## Setting up
1. Open project in IntelliJ, it will create an `.idea`.
2. Use *File* > *Project Structure* to confirm Java 17 is used.
3. Create *Edit Configuration* (if not exist Add new *Maven* configuration) or check build and run options(must be specified Java 17 SDK for 'microservice-emplyee' module and 'com.dannyhromau.employee.Application' as running directory).
4. Use the *Maven* tools window to:
   * *Toggle "Skip Tests" Mode* (if You won't to testing the application)
   * *Execute Maven Goal*: `clean install`
   * check the target directory (You should see an archive there named *microservice-employee-1.0-SNAPSHOT.jar*).

## Db settings
This project uses postgreSQL database.
Before each create database(for example *postgres*).
Connection settings are specified by *application.yml* file (for using custom settings change required fields in this file).
For initialization, you must sequentially use SQL queries specified in the *initDB.sql* file (resource folder).
Tables, fields, keys and indexes creates by liquibase changelog files.
     
## Running and testing
1. Run the application with command `java -jar microservice-employee-1.0-SNAPSHOT.jar --spring.config=application` in *command line*.
2. For tetsing all the endpoints possible see swagger user interface (go to "<hostname>/swagger-ui/index.html#/".

Hope You enjoy!
