#  Product Offers API

This service enables endpoints for Product Offers API
<br><br>


## Configuration


The adapter takes the following config vars:

* ***EXAMPLE_AUTHORIZED_PATH***: path for example_authorized endpoint ("/example_authorized" by
default)
* ***EXAMPLE_AUTHORIZED_VERSION***: service version to get example version

<br>


## Running the adapter
<br>

> #### Running the adapter locally
>
> ```bash
> JAVA_OPTS="-XX:+UseContainerSupport -XX:+UseParallelGC -XX:MaxRAMPercentage=75"
> LOG_LEVEL="DEBUG"
> TIMEOUT_REQUEST="15s"
> ../mvnw spring-boot:run
> ```
> This will run the adapter locally on port 8080
<br>

<br>


## Testing the adapter


### <span style="color:orange">Endpoints for Product Offers API:</span>

### GET - getInquiryPrices
    Consulta de precios
> ```
> curl -i --location --request GET http://localhost:8080/inquiry-prices \
>    --header 'x-tenant:local-ci' \
>    --header 'x-correlator: "abc12345678" ' \
>    --header 'x-token-info: {"activated_roles":["user","owner","admin"], "client_id":"novum", "sub":"USER_ID"}' 
> ```
