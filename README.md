# leetty
- [Description](#description)
- [Architecture](#architecture)
  - [leetty-auth-service](#leetty-auth-service)
  - [leetty-web-service](#leetty-web-service)
  - [leetty-telegram-bot](#leetty-telegram-bot)
  - [leetty-gateway](#leetty-gateway)
- [How to try it?](#how-to-try-it)
- [Can I deploy it by myself? How?](#can-i-deploy-it-by-myself-how)
- [Afterword](#afterword)
## Description
`Leetty` - is a Telegram-bot that helps you to interact with LeetCode platform.

This project was started as my bachelor's degree final project.

You also can read more about it by following the link: [graduate-work-rus-lang](https://docs.google.com/document/d/1ImOsaW_-6R2gidV9Cxh06JHzzyZZEYlQ/edit?usp=sharing&ouid=100798249487840626524&rtpof=true&sd=true) _(still in progress)_

**_I may translate it to English someday :)_**
## Architecture
![leetty-architecture](docs/leetty-architecture.png)
### leetty-auth-service
`leetty-auth-service` - is a Java-service that provides secure communication between `leetty-telegram-bot` instances and `leetty-web-service`.

It uses OAuth2 protocol, in particularly [Client Credentials Flow](https://auth0.com/docs/get-started/authentication-and-authorization-flow/client-credentials-flow), for M2M (Machine-2-Machine) communication.

#### Tech Stack
- Java 17
- Docker
- Sprint Boot
  - Actuator
  - Micrometer
  - OAuth2 Authorization Server
  - Security
- Lombok
### leetty-web-service
`leetty-web-service` - is a Java-service that is the main backend of `Leetty`.

It interacts with [LeetCode](https://leetcode.com) using GraphQL, stores user data to PostgreSQL and makes major computation.

#### Tech Stack
- Java 17
- Docker
- Spring Boot
  - Actuator
  - Micrometer
  - Data JPA
  - OAuth2 Resource Server
  - Web
  - Security
- [Netflix DGS Framework](https://netflix.github.io/dgs)
- Flyway
- PostgreSQL
- H2
- Lombok
### leetty-telegram-bot
`leetty-telegram-bot` - is a Java-service that interacts with Telegam updates. It uses Redis as an in-memory DB for store cache date, such as user states and callback data.

#### Tech Stack
- Java 17
- Docker
- Spring Boot
  - Actuator
  - Micrometer
  - Cloud Open Feign
  - OAuth2 Client
  - Security
  - Web
  - State Machine
  - Data Redis
  - Kafka
- Flyway
- Lombok
- Redis
### leetty-gateway
`leetty-gateway` - is a Golang-service implemented as a gateway between Nginx and Apache Kafka.

This service will be used if you use Webhook-schema to receive updates from Telegam.

#### Tech Stack
- Golang
- Docker
- Makefile
- Paketo Buildpacks
- Nginx
- Apache Kafka

Source code for `leetty-gateway` is [here](https://github.com/ksilisk/leetty-gateway).

## How to try it?
You can try `Leetty` by following the link: https://t.me/LeettyBot

## Can I deploy it by myself? How?
Yes, you can.

### Run locally
- Clone project
- Fill `applicaiton.properties`-files for every microservice
  - Actually, I didn't provide examples for `application.properties`, but I have [task at backlog](https://github.com/ksilisk/leetty/issues/25) to do it someday
- Run it and enjoy!

### Deploy to remote server
- Clone project
- Fill `application.properties`-files
- Fill [docker-env-sample](.env_sample)
- Try run it in Docker locally
- _(Optional)_ Implement your own CI/CD pipeline
  - Deployment process, which I implemented using GitHub actions, may help you - [here](.github/workflows/deploy.yml)
- Run it on remote server and enjoy!
## Afterword
**_in progress_**
