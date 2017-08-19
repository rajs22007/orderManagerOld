# orderManager
order management spring boot micro service to orchestrate the life cycle of order

orderManager Activiti Spring Boot Micro Service
August 2017
INSTRUCTOR: Ajeya  Raj HB
rajs22007@gmail.com

18 August 2017
Features to be implemented.
ORDER Creation. 
ORDER search
Task creation
Task search
Perform actions on tasks

Links to be checked and work on with:
https://github.com/jbarrez/spring-boot-with-activiti-example 
http://syncrequest.com/ 
https://www.youtube.com/watch?v=Vm1J2wUhNOk 
https://spring.io/blog/2015/03/08/getting-started-with-activiti-and-spring-boot

activity provide business rule task

Order Manager Microservice Github:
  https://github.com/rajs22007/orderManager
  First commit is done with sample flow
  Yet to integrate locally with MySQL.
  Can uncomment the commented pom entry, main class code, and property file entry, and comment-out the H2 entry from pom file.

Create Order:
  curl -X POST http://localhost:8080/createOrder -H 'authorization: Basic  YWRtaW46YWRtaW4=' -H 'Content-Type: application/json' -d '{"orderName": "mobileOrder", "productName": "moto turbo", "createdBy":"Ashish", "clientUid":"A23074", "vendorUid": "NA"}'

Start Order Process:
  curl -X POST http://localhost:8080/start-order-process -H 'authorization: Basic YWRtaW46YWRtaW4=' -H 'Content-Type: application/json' -d '{"orderId": "1", "isAssigned": "true", "vendorUid": "Ajeya03"}'

Fetch Tasks:
  curl -X GET http://localhost:8080/runtime/tasks -H 'authorization: Basic YWRtaW46YWRtaW4=' -H 'Accept: application/json'

Accept Offer Acceptance Task:
  curl -X POST http://localhost:8080/runtime/tasks/10 -H 'authorization: Basic YWRtaW46YWRtaW4=' -H 'Content-Type: application/json' -d '{"action" : "complete", "variables": [ {"name":"status", "value":"accept"} ]}'

Accept Inspection Task:
  curl -X POST http://localhost:8080/runtime/tasks/15 -H 'authorization: Basic YWRtaW46YWRtaW4=' -H 'Content-Type: application/json' -d '{"action" : "complete", "variables": [ {"name":"inspectionStatus", "value":"accept"} ]}'

Accept Review Task:
  curl -X POST http://localhost:8080/runtime/tasks/20 -H 'authorization: Basic YWRtaW46YWRtaW4=' -H 'Content-Type: application/json' -d '{"action" : "complete", "variables": [ {"name":"reviewStatus", "value":"accept"} ]}'