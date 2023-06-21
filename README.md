# Credit card issuing

## Problem description

Application for credit card printing.  

Functionality: 
* insert request for credit card print
* print credit card
* search request

For more details look into `documents/ProblemDescription.odt`

## Architecture overview  and technical description

look into `documents/TechnicalDescription.odt`

## Start app

## Start app in development mode

* start infrastructure container: in terminal navigate to `/docker` folder then run `docker compose up -d dev_db`
* run `maven mvn clean package` 
* Optional set location where files should be created use env variable `hr.rba.credicardprint.csvfile.dir`. Default location is `print/files`
* Optional set csv file delimiter use env env variable `hr.rba.credicardprint.csvfile.del`. Default is `,`
* before running application activate `spring profile dev` 

## Stop app in development mode
* after stopping application do not forget to shout down infrastructure container

## Start test
* run `mvn test`

## Doc 
* Java DOC run `mvn javadoc:javadoc` to generate java doc. It is generated in `/target/site/apidocs/index.html`
* API DOC after the application is started go to http://localhost:8080/swagger-ui/index.html

## Postman 
* tutorial https://blog.postman.com/postman-now-supports-openapi-3-1/
* open api file is here `src/main/resources/openApi/api.yaml`
* do not forget to set baseUrl to `http://localhost:8080/` (for dev env)

## Author 
Ivan Gavlik
ivangavlik963@gmail.com
