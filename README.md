# Secret Santa App
## How to run
### Development
> Note: You need to use below command to run development env as by default app runs on 'prod' profile.
```
./gradlew bootRun --args="--spring.profiles.active=dev"
```

* With above command "docker-compose.yml" will be automatically executed. 
* Everytime our app starts - existing database content will be dropped.
* Following containers will start within docker-compose.yml:
  * mongodb
  * rabbitmq

Credentials and connection details will be automatically obtained by Spring Boot for above containers.

### Production (in-progress)
Production build will be available on https://secret-santa-app.tgawlik.com.pl few minutes after merging changes into 'main' branch.

Running production env:
```
docker compose --profile prod up [-d]
```

> Note: To make 'watchtower' container running properly you need to add 'config.json' file in the app root directory. This file should contain credentials to private docker registry which contains our app docker image. 

## Environment configuration
1. All environment variables are configurable in .env file.
2. Default credentials and app database name are initialized for MongoDB in init-mongo.js file during db first startup.

## Troubleshooting
TBD