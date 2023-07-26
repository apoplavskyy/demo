# Spring boot refresh project #

## Backend technical task ##

Implement an app which provides http endpoints to save and retrieve employees. The app should be based on Spring, Hibernate and h2 as a database.

Each employee has a name, unique code, and salary. Some employees might have subordinates and these subordinates might have their own subordinates.
The app should be able to save a single employee or several, retrieve a single or several, and get a salary sum of all subordinates of a given employee.
Like we have employee Alex, with 2 subordinates Anna and Rein. Rein also has 2 subordinates, Michael and Kristin.
- Alex
 - Anna (1000 eur)
 - Rein (1000 eur)
      - Michael (1000 eur)
      - Kristin (1000 eur)

So, the result of getting a sum of salaries by Alex should be 4000 euros.

We will look at the details of the implementation, so it’s better to do less but with better quality.
Ship the code on Git Hub.

## Notes ##

to package: "mvn package"

to run with H2 database profile : ". ./run.sh" 

H2 console: http://localhost:8000/h2-console/  (sa/password)

## Configuration files ##

#### Shared: Property file ####

application.yml

```
server:
  port: 8000

```

#### Default profile: H2 db  ####

application-default.yml

```
spring:
  datasource:
    url: jdbc:h2:mem:test
    driverClassName: org.h2.Driver
    username: sa
    password: password
  h2.console.enabled: true
  jpa:
    database-platform: org.hibernate.dialect.H2Dialect
    hibernate.ddl-auto: update
```

application.yml
```
server:
  port: 8000
```

config.properties

```
ScanConfig.depth = 7
```
you can increase depth in case of big company

## Test files ##

#### H2 console ####

http://localhost:8000/h2-console/  (sa/password)
```
insert into employee (name, salary) values ('Alex', 1000);
insert into employee (name, salary, superior_id) values ('Anna', 1000, 1);
insert into employee (name, salary, superior_id) values ('Rein', 1000, 1);
insert into employee (name, salary, superior_id) values ('Michael', 1000, 3);
insert into employee (name, salary, superior_id) values ('Kristin', 1000, 3);

insert into employee (name, salary, superior_id) values ('test51', 100, 5);
insert into employee (name, salary, superior_id) values ('test52', 100, 5);

insert into employee (name, salary, superior_id) values ('test71', 100, 7);
insert into employee (name, salary, superior_id) values ('test72', 100, 7);
```

#### REST ####

```
curl -X POST --header 'Content-Type: application/json'   -d '{ "name": "Alex", "salary": 1000 }'  http://localhost:8000/api/1/employee

curl -X POST --header 'Content-Type: application/json' -d '{ "name": "Anna", "salary": 1000, "superior" : 1 }'  http://localhost:8000/api/1/employee
curl -X POST --header 'Content-Type: application/json' -d '{ "name": "Rein", "salary": 1000, "superior" : 1 }'  http://localhost:8000/api/1/employee

curl -X POST --header 'Content-Type: application/json' -d '{ "name": "Michael", "salary": 1000, "superior" : 3 }'  http://localhost:8000/api/1/employee
curl -X POST --header 'Content-Type: application/json' -d '{ "name": "Kristin", "salary": 1000, "superior" : 3 }'  http://localhost:8000/api/1/employee

list all: http://localhost:8000/api/1/employee/

list one: http://localhost:8000/api/1/employee/<id>

list by ids: http://localhost:8000/api/1/employee/ids?id=1,2

salary sum of all subordinates of a given employee 
http://localhost:8000/api/1/employee/1/statistic

```
