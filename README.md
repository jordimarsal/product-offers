#  Product Offers API

This service enables endpoints for Product Offers API
<br>
<br>


## Running the service
<br>

> #### Running the service locally
>
> ```bash
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
> ```