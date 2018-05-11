# crud-with-vaadin

A simple CRUD web application using Vaadin.

## Pre-requisites

- Java 8 
- Maven

## Download and build the project

Get the repository locally:

```
git clone https://github.com/ermalaliraj/crud-with-vaadin.git
cd crud-with-vaadin
```

Use maven to build the project:

```
mvn clean install
```
Afterwards you will have a target folder containing a jar file (_./target/crud-with-vaadin-0.1.0.jar_) needed to run the application.
 
## Run the Application

```
java -jar target\crud-with-vaadin-0.1.0.jar
```
The Application should now be running and listening in http://127.0.0.1:8080 

![app](./doc/homepage.png)
 
# TODO
tests running through mvn install command line do not succeed.
Run clean/install using IDE.
 

# See
* [Creating CRUD UI with Vaadin - spring.io](https://spring.io/guides/gs/crud-with-vaadin/)
 
 