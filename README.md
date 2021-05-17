# Neueda URL Shortener
This is a Spring project demonstrating URL shortener. This is a dockerized solution . The docker image is available in the docker hub at: docker build . --file Dockerfile --tag shivasr/neueda-url-shortener:1.0.0.

## How this works
This application is developed using Spring boot framework and launches a server at port, say 8080 and exposes the two REST API endpoints:

* POST /, eg: POST http://localhost:8080/: To create a short URL and a mapping record in the database
  This returns a JSON containing id, originalURL and the shortURL.
  
* GET /{urlCode}, eg: GET http://localhost:8080/3ibJF44: Redirects to the original URL in the mapping record.

## How to deploy
This solution is containerized using the docker. Follow the below steps to deploy the solution:
Step 1: Build the project (If you want to use docker image directly from the hub, go to step 3)
```shell
# On Windows
mvnw.cmd clean install

# On Linux
mvn clean install
```
Step 2: Build the Docker image

```shell
docker build . --file Dockerfile --tag shivasr/neueda-url-shortener:1.0.0
```

Step 3: Run the Docker image
```shell
docker run -p 8080:5000 shivasr/neueda-url-shortener:1.0.0
```

## How to use the URL shortener

### Create a short URL

Once the solution is deployed using a REST API client such as postman create a URL mapping record by posting a JSON to 
the endpoint "/":

```http request
POST http://localhost:8080/
Content-Type: application/json
Body:

{
    "url": "https://www.google.com/search?q=shivakumar+ramannavar&rlz=1C1GCEO_enIE945IE945&oq=shivakumar+ramannavar&aqs=chrome..69i57j69i61.5793j0j7&sourceid=chrome&ie=UTF-8"
}
```

The below is the response:
```http request
{
    "id": 1,
    "originalURL": "https://www.google.com/search?q=shivakumar+ramannavar&rlz=1C1GCEO_enIE945IE945&oq=shivakumar+ramannavar&aqs=chrome..69i57j69i61.5793j0j7&sourceid=chrome&ie=UTF-8",
    "shortURL": "http://localhost:8080/3ibJF44"
}
```

### Use the short URL
Using a suitable http browser navigate to the short URL http://localhost:8080/3ibJF44 and you can see the server redirects you to the original URL you supplied. 