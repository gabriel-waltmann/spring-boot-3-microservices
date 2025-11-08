package com.gabriel.microservices.product;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.boot.testcontainers.service.connection.ServiceConnection;
import org.springframework.context.annotation.Import;
import org.testcontainers.containers.MongoDBContainer;

@Import(TestcontainersConfiguration.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class ProductServiceApplicationTests {

    @ServiceConnection
    static MongoDBContainer mongoDBContainer = new MongoDBContainer("mongo:7.0.5");

    @LocalServerPort
    private int port;

    @BeforeEach
    void setUp() {
        RestAssured.baseURI = "http://localhost";
        RestAssured.port = port;
    }

    static {
        mongoDBContainer.start();
    }

	@Test
	void shouldCreateProduct() {
        String productRequest = """
            {
                "name": "Test Product",
                "description": "This is a test product",
                "price": 99.99
            }
            """;

        RestAssured.given()
                .contentType(ContentType.JSON)
                .body(productRequest)
                .when()
                .post("/api/product")
                .then()
                .statusCode(201)
                .body("id", Matchers.notNullValue())
                .body("name", Matchers.equalTo("Test Product"))
                .body("description", Matchers.equalTo("This is a test product"))
                .body("price", Matchers.equalTo(99.99f));
	}

}
