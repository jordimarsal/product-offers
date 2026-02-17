#  Product Offers API

[![codecov](https://codecov.io/gh/jordimarsal/product-offers/branch/main/graph/badge.svg)](https://codecov.io/gh/jordimarsal/product-offers)

This service enables endpoints for Product Offers API
<br>
## Implemented with:
- Java 17 
- Spring Boot 3.2.0
- H2 2.2.224
- Maven 3.8.3
- JUnit 5.10.1
- Mockito 5.7.0
- Lombok 1.18.26
- Jacoco 0.8.1
<br>


## Running the service
<br>

> #### Running the service locally with Maven
>
> ```bash
> mvn clean install
> 
> ./mvnw spring-boot:run
> ```
> This will run the service locally on port 8080
<br>

<br>


## Testing the service


### <span style="color:orange">Endpoints for Product Offers API:</span>

### GET - getInquiryPrices
    Consulta de precios
```bash
# happy path
curl -i --location --request GET \
http://localhost:8080/inquiry-prices\?applicationDate=2020-06-14%2010:00:00\&productId=35455\&brandId=1 \
--header 'x-correlator:abc12345678'

# not found
curl -i --location --request GET \
http://localhost:8080/inquiry-prices\?applicationDate=2023-06-14%2010:00:00\&productId=35455\&brandId=1 \
--header 'x-correlator:abc12345678'
```

## Code formatting (maven)

- Apply spotless locally:

```bash
mvn spotless:apply
```

- Check formatting (CI / fail build):

```bash
mvn spotless:check
```

