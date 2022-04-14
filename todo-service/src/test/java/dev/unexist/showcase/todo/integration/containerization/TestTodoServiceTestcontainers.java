/**
 * @package Showcase-Testing-Quarkus
 *
 * @file Tests with Hoverfly
 * @copyright 2022 Christoph Kappel <christoph@unexist.dev>
 * @version $Id$
 *
 * This program can be distributed under the terms of the Apache License v2.0.
 * See the file LICENSE for details.
 **/

package dev.unexist.showcase.todo.integration.containerization;

import dev.unexist.showcase.todo.domain.todo.TodoBase;
import io.quarkus.test.common.QuarkusTestResource;
import io.quarkus.test.junit.QuarkusTest;
import io.restassured.http.ContentType;
import org.junit.jupiter.api.Test;

@QuarkusTest
@QuarkusTestResource(value = TestcontainersResource.class, restrictToAnnotatedClass = true)
public class TestTodoServiceTestcontainers {

    @Test
    void shouldCreateTodoWithRestAssured() {
        String location =
            RestAssured.given()
                .contentType(ContentType.JSON)
                .body(new TodoBase("test", "test"))
            .when()
                .post("/todo")
            .then()
                .statusCode(201)
                    .extract().header("location");

        Assertions.assertThat(location).isNotBlank();
    }
}
