**Prerequisites**

- Java 17 (recommend [Eclipse Temurin 17](https://adoptium.net/temurin/releases/?version=17))
- IntelliJ (recommended) or your favorite Java IDE
- Bash environment with installed 'curl' or Postman

**Preamble**

Your mission would you decide to accept it:

As part of the multi-disciplinary development elite team **PIT** in Statista you are creating software to alleviate the
issues from our Sales team. For this, a new requirement has been raised to implement a RESTful web service that stores
_booking_ objects in memory and return information about them.

Note: A _booking_ is a request from any of our beloved customers to acquire one of our products.

**Challenge**

The module **code-challenge** already contains the basic structure of a Spring Boot application for you.

The bookings to be stored have the following fields:

- booking_id
- description
- price
- currency
- subscription_start_date
- email
- department

Please define as many departments as you will like, and create a unique method `doBusiness()` for each department. This
is your time to shine, so the method implementations can be as simple, elegant, or complicated as you want.

Feel free to select the best data type that, in your opinion, would define those fields the best.

You should not use a database or some other sort of persistence but come up with an own data structure to store the
transactions.

In any moment where the implementation implies the usage of an external server (i.e. sending e-mail's) feel free to mock
creatively these external resources, keeping in mind that all real-world use-cases must be covered.

In general, we are looking for a good implementation, code quality, code resilience, code extensibility, code
maintainability and how the implementation is tested.
Basically something you wouldn't be too embarrassed to push to production.

**API Specification:**

**POST /bookingservice/bookings**

Sample Body:

```
{"description": "Cool description!", "price": 50.00, "currency": USD, "subscription_start_date": 683124845000, "email": "valid@email.ok", "department": "cool department"}
```

Creates a new booking and sends an e-mail with the details.

**PUT /bookingservice/bookings/{bookingId}**

Sample Body:

```
{"description": "Cool description!", "price": 50.00, "currency": USD, "subscription_start_date": 683124845000, "email": "valid@email.ok", "department": "another_department" }
```

Insert, replace if already exists a booking.

**GET /bookingservice/bookings/{bookingId}**

Returns the specified booking as JSON.

**GET /bookingservice/bookings/department/{department}**

Returns a JSON list of all bookings ids with the given department.

**GET /bookingservice/bookings/currencies**

Returns a JSON list with all used currencies in the existing bookings.

**GET /bookingservice/sum/{currency}**

Returns the sum of all bookings prices with the given currency.

**GET /bookingservice/bookings/dobusiness/{bookingId}**

Returns the result of `doBusiness()` for the given booking corresponding department.

**Input / Output Examples:**

=========================================
### Please find below the information about the tech stack used for the service

The service is written in Java and uses the following tools and frameworks:
* Spring Boot 3.2.0 as an underlying framework.
* [Official Apache Maven documentation](https://maven.apache.org/guides/index.html) as build tool
* [Spring Web](https://docs.spring.io/spring-boot/docs/3.2.0/reference/htmlsingle/index.html#web)
* [Validation](https://docs.spring.io/spring-boot/docs/3.2.0/reference/htmlsingle/index.html#io.validation) for Bean validation.
* [Spring Boot Actuator](https://docs.spring.io/spring-boot/docs/3.2.0/reference/htmlsingle/index.html#actuator) to expose operational information.

### Running the service with an IDE (i.e. IntelliJ IDEA)

Cloning the service and running it:
```sh
git clone https://github.com/randyhbh/todo-list.git

cd todo-list

./mvnw clean verify spring-boot:run
```

## OpenAPI documentation
The OpenAPI documentation for the API is available in [openapi.yaml](openapi.yaml). The OpenAPI is also exposed on the
http://localhost:8080/swagger-ui/index.html address when the service is running locally and can be tested from there.

## Code Structure

The service strives to follow the clean architecture approach

![clean architecture diagram](docs/clean_architecture.png)

All API endpoints are located in [com.statista.code.challenge.api.http.v1](code-challenge/src/main/java/com/statista/code/challenge/api/http/v1) package.
Interfacing with external systems (ex: databases) is limited to [com.statista.code.challenge.infra](code-challenge/src/main/java/com/statista/code/challenge/infra)
package. API Use-cases implementations are located in [com.statista.code.challenge.usecases](code-challenge/src/main/java/com/statista/code/challenge/usecases)

### Design Decisions

The data type used for the `price` at the moment of creating a booking was changed to an `Integer` representing the `price` in
cents, as `JSON` does not define a type with precision and using `number` type could result in losing some cents.

Currency is implemented using Java.util.Currency class which represents a currency. Currencies are identified by their 
ISO 4217 currency codes. Any wrong currency or not capitalized one will result in an exception been thrown.

For the department's functionality, an Enum was used to represent each department. Each department has a specialized 
implementation of the DepartmentOperation interface that represents each of the Departments.

The endpoint `GET /bookingservice/sum/{currency}` was implemented in a separate controller class since it represents a different resource.

Ideally, the endpoint namings should be composed like this:

- `bookingservice` should be renamed to `booking-service`
- `dobusiness` should be renamed to `do-business`