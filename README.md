# Credit card issuing

## Problem description

Application for credit card issuing.  

Functionality: 
* insert person
* search person
* generate credit card for person  

For more details look into documents/ProblemDescription.odt

## Technical description

### Architecture overview 
Restful web service 

### Technology
* Java 17 (long term support version)
* Spring Boot
* Maven
* Spring Data JPA

For more info on architecture and technology look into  

## Start app

## Start app in development mode

* start infrastructure container: in terminal navigate to /docker folder then run docker compose up -d dev_db
* run maven mvn clean package 
* to run the application activate spring profile dev 

## Stop app in development mode

* after stoping application do not forget to shout down infrastructure container

## Start test

## Api doc 
* after the application is started go to http://localhost:8080/swagger-ui/index.html

## Postman 
* tutorial https://blog.postman.com/postman-now-supports-openapi-3-1/
* open api file is here src/main/resources/openApi/api.yaml
* do not forget to set baseUrl to http://localhost:8080/ (for dev env)

## Author 
Ivan Gavlik
ivangavlik963@gmail.com
