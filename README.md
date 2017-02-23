# microservice-sample

This is a proof-of-concept application, which demonstrates [Microservice Architecture Pattern](http://martinfowler.com/microservices/) using Spring Boot, Spring Cloud, Spring Config.

<hr/>
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

## Edge Server

This is entry point to outside world.

In theory, a client could make requests to each of the microservices directly. But obviously, there are challenges and limitations with this option, like necessity to know all endpoints addresses, perform http request for each peace of information separately, merge the result on a client side. Another problem is non web-friendly protocols, which might be used on the backend.

It can be used for authentication, insights, stress and canary testing, service migration, static response handling, active traffic management.

<b>With Spring Cloud we can enable it with one @EnableZuulProxy annotation</b>


@EnableZuulProxy
public class Config {
}

<b>Route requests to appropriate microservices, defined in application.properties</b>

spring.application.name=edge-server
server.port=1114
eureka.instance.prefer-ip-address=true
eureka.instance.leaseRenewalIntervalInSeconds=10
eureka.instance.metadataMap.instanceId=${vcap.application.instance_id:${spring.application.name}:${spring:application:instance_id:${server.port}}}
eureka.client.serviceUrl.defaultZone=http://127.0.0.1:1111/eureka/  
zuul.prefix=/api
zuul.ignoredServices='*'
zuul.routes.auth-service.path=/auth-service/**
zuul.routes.auth-service.serviceId=AUTH-SERVICE
zuul.ribbon.restclient.enabled=true
hystrix.command.default.execution.isolation.thread.timeoutInMilliseconds=60000

Sample examples is build using Spring Cloud API, Showcasing <br/>
1 - Configure Eureka server, eureka-server project.<br/>
2 - Build service and register to Eureka, login-service/token-service project.<br/>
3 - Client load balancer using Ribbon and Feign, FallBack, auth-client project. Using eureka discouvery how client communicates to end service.<br/>
4 - Configure edge-server(zuul), edge-server. Using Zuul we can forward particular request to Client load balancer.<br/>


