# system of systems
Event-driven DDD microservices based on spring and kafka with @JsonPath for @ProjectedPayload kafka deserialization

## infrastructure services

_kafka_

```bash
./mvnw -pl :infrastructure-kafka spring-boot:run
http :8081/actuator/health
```

_h2_

```bash
./mvnw -pl :infrastructure-h2 spring-boot:run
http :8082/actuator/health
```

## application services

_catalog_

```bash
./mvnw -pl :service-catalog spring-boot:run
http :8083/actuator/health

http post :8083/api/v1/products name="iPhone x" qty=4
http get  :8083/api/v1/products                | jq ".content"
http get ":8083/api/v1/products?size=1&page=0" | jq ".content"
```

_orders_

```bash
./mvnw -pl :service-orders spring-boot:run
http :8084/actuator/health

http :8084/api/v1/product-infos | jq ".content"
```

_resources_

* Inspired by this talk: [YouTube: Refactoring to a System of Systems - Oliver Gierke](https://www.youtube.com/watch?v=VWefNT8Lb74)
* JetBrains IDEA REST [HTTP Client](https://blog.jetbrains.com/phpstorm/2018/04/easier-start-with-the-http-client/)
* [GitHub: odrotbohm/sos](https://github.com/odrotbohm/sos/tree/master/30-messaging-sos)
