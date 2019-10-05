# Prost Server Side

This is a service for the App Prost

## Dependencies
| Name       | Version |
|------------|---------|
| Gradle     | [5.4.1](https://github.com/gradle/gradle/releases/tag/v5.4.1)   |
| Kotlin     | [1.3.31](https://github.com/JetBrains/kotlin/releases/tag/v1.3.31)  |
| Spek       | [2.0.2](https://github.com/spekframework/spek/releases/tag/2.0.2)   |
| Log4J      | [2.11.2](https://github.com/apache/logging-log4j2/releases/tag/log4j-2.11.2)  |
| Vertx      | [3.7.0](https://github.com/eclipse-vertx/vert.x/releases/tag/3.7.0)   |
| RxKotlin   | [2.3.0](https://github.com/ReactiveX/RxKotlin/releases/tag/2.3.0)   |
| Micrometer Metrics Prometheus | [1.1.4](https://github.com/micrometer-metrics/micrometer/releases/tag/v1.1.4)   |

## Environment Variables
```
PORT /* Listen HTTP port. Default: 9090 */
```

## API DOC

### Common Error Message
```json
{
  "error": {
    "code": "MODULE_XXXX",
    "message": "ERROR DESCRIPTION"
  }
}
```

### Register a User
```
/prost/api/user
```
Request Body
```json
{
	"name": "STRING",
	"lastName": "STRING",
	"email": "STRING",
	"password": "STRING",
	"phone": "STRING",
	"description": "STRING",
	"birthday": LONG,
	"interests": STRING[]
}
```

Response Created 201
```json
{
    "id": "STRING", 
    "name": "STRING",
    "lastName": "STRING",
    "email": "STRING",
    "phone": "STRING",
    "description": "STRING",
    "birthday": LONG,
    "interests": STRING[]
}
```

### Login User
```json
{
  "email": "STRING",
  "password": "STRING"
}
```

Response Ok 200
```json
{
    "id": "STRING", 
    "name": "STRING",
    "lastName": "STRING",
    "email": "STRING",
    "phone": "STRING",
    "description": "STRING",
    "birthday": LONG,
    "interests": STRING[]
}
```

