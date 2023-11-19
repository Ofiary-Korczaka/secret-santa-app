# Secret Santa App
## How to run
### Production
```
./gradlew bootRun --args="--spring.profiles.active=prod"
```

In this case you need to run firstly in the app root directory:
```
docker compose up [-d]
```

/src/main/resources/application-prod.yml contains connection configuration to the database and message queue broker.

### Development
```
./gradlew bootRun --args="--spring.profiles.active=dev"
```

* With above command "docker-compose.yml" will be automatically executed. 
* Everytime our app starts - existing database content will be dropped.