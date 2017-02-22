# microservice-sample

Sample examples is build using Spring Cloud API, Showcasing <br/>
1 - Configure Eureka server, eureka-server project.<br/>
2 - Build service and register to Eureka, login-service/token-service project.<br/>
3 - Client load balancer using Ribbon and Feign, FallBack, auth-client project. Using eureka discouvery how client communicates to end service.<br/>
4 - Configure edge-server(zuul), edge-server. Using Zuul we can forward particular request to Client load balancer.<br/>


