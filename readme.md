TODO
====
RESEARCH
--------
- [ ] Build App locally with Maven
- [ ] Run App locally (it runs with default Spring profile with embedded H2 db)
- [ ] Research [App REST API](http://localhost:8080/swagger-ui/)
- [ ] Research transitive Maven dependencies for test libraries (IDEA Maven view: open `spring-boot-starter-test`)

RUN LOCAL ENVIRONMENT FOR AUTOTESTS DEVELOPMENT
-----------------------------------------------
- [ ] Build Docker image for App
- [ ] Run test environment for App and Postgres DB with Docker Compose

DEVELOP API AUTOTESTS
---------------------
- [ ] Configure Maven build to split Unit and Integration tests
- [ ] Cover with integration autotests API for:
- CRUD operations for Owner
- CRUD operations for Pet 
- [ ] Keep code quality for autotests:
- easy readable
- covers important corner cases
- code reused
- focused (one test = one scenario)
- isolated (tests run reproduce in any order)

DEVELOP CI/CD PIPELINE
----------------------
- [ ] Develop GitLab CI pipeline covering stages: build and integration tests run
- [ ] Run on corporate GitLab environment and make sure it reproduces 


---


TROUBLESHOOTING
===============

Get local containerized application console output:
```shell
docker container ls --all
docker container logs <app-container>
```
