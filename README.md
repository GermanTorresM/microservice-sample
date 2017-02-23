# microservice-sample

This is a proof-of-concept application, which demonstrates [Microservice Architecture Pattern](http://martinfowler.com/microservices/) using Spring Boot, Spring Cloud, Spring Config.

## Functional services

Decomposed into two core microservices. All of them are independently deployable applications, organized around certain business capability.

#### Login service

Method: POST<br/>
Path : /authenticate<br/>
Description: Validate userid password.<br/>
Curl Command: curl -H "Content-Type: application/json" -X POST -d '{"loginId":"xyz","password":"xyz"}' http://localhost:1112/loginservice/authenticate<br/>

Note: Business logic is hard coded, If userid/password are sample its authenticated.

#### Token service

Method: GET<br/>
Path : /validateToken<br/>
Description: Validate token.<br/>
Curl Command: curl -H "Content-Type: application/json" -X GET  http://localhost:1115/tokenservice/validateToken/tokenId<br/>

Note: No Business logic applied, returns hard coded true.<br/>

Method: GET<br/>
Path : /generatetoken<br/>
Description: Generate Token based on data.<br/>
Curl Command: curl -H "Content-Type: application/json" -X GET  http://localhost:1115/tokenservice/generatetoken/data<br/>

Note: UUID string is return as token.<br/>

<hr/>
Sample examples is build using Spring Cloud API, Showcasing <br/>
1 - Configure Eureka server, eureka-server project.<br/>
2 - Build service and register to Eureka, login-service/token-service project.<br/>
3 - Client load balancer using Ribbon and Feign, FallBack, auth-client project. Using eureka discouvery how client communicates to end service.<br/>
4 - Configure edge-server(zuul), edge-server. Using Zuul we can forward particular request to Client load balancer.<br/>


