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

zuul.prefix=/api<br/>
zuul.ignoredServices='*'<br/>
zuul.routes.auth-service.path=/auth-service/**<br/>
zuul.routes.auth-service.serviceId=AUTH-SERVICE<br/>
zuul.ribbon.restclient.enabled=true<br/>
hystrix.command.default.execution.isolation.thread.timeoutInMilliseconds=60000<br/>

Note: Detail code for edge server can be found in edge-server project
<hr>

## Service Discovery

The key part of Service discovery is Registry. I use Netflix Eureka in this project. Eureka is a good example of the client-side discovery pattern, when client is responsible for determining locations of available service instances (using Registry server) and load balancing requests across them.

With Spring Boot, you can easily build Eureka Registry with spring-cloud-starter-eureka-server dependency, @EnableEurekaServer annotation and simple configuration properties.

@EnableEurekaServer
@SpringBootApplication
public class EurekaServerApplication {

	public static void main(String[] args) {
		SpringApplication.run(EurekaServerApplication.class, args);
	}
}

Every service will get register to Eureka server on its starup and provide all required info like URL, Host etc.

application.properties for eureka server.

spring.application.name=eureka-service<br/>
server.port=1111<br/>
eureka.instance.client.register-with-eureka=false<br/>
eureka.instance.client.fetch-registry=false<br/>
logging.level.com.netflix.eureka=OFF<br/>
logging.level.com.netflix.discover=OFF<br/>

Note: Detail code for eureka server can be found in eureka-server project
<hr>
