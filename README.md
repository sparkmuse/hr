## Use cases
- [x] user has to be able to create a job offer and read a single and list all offers. 
- [x] candidate has to be able to apply for an offer.
- [x] user has to be able to read one and list all applications per offer.
- [X] user has to be able to progress the status of an application.
- [X] user has to be able to track the number of applications.
- [X] status change triggers a notification (*)


## How to run the application

### Prerequisites
- Java 11
- Maven

### Running 
```shell script
mvn spring-boot:run
```

Visit the endpoint to access the Swagger UI and Documentation. You can test the application through the swagger UI

http://localhost:8080/swagger-ui.html

** Note: ** We are using a very old version of Swagger because there is a problem with Maven Central and the newer ones.


## Main points I looked into while developing the application

* Create the application endpoints in a restful manner
* Since an application does not make any sense without an offer then all application related interactions have to be done through the Offer, even though it might mean some data redundancy.

## Things I wish I had more time to do.

* Implement documentation through Spring RESTdocs. It's way better than swagger and it creates very good quality documentation. I used swagger here because I did not have much more time.
* Perhaps rethink the interactions between the Offer and the application.
* Add maybe more DTOs for offers and application and it's respective mappers.
* General tidying up and cleaning. In some of the tests are duplicated quite a bit of the code and I think it could be done better. If I had a bit more time. 



 