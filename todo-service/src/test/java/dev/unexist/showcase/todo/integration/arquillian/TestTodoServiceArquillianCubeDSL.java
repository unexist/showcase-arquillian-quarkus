/**
 * @package Showcase
 * @file
 * @copyright 2022 Christoph Kappel <christoph@unexist.dev>
 * @version $Id$
 *
 * This program can be distributed under the terms of the Apache License v2.0.
 * See the file LICENSE for details.
 **/

package dev.unexist.showcase.todo.integration.arquillian;

import org.arquillian.cube.docker.impl.client.containerobject.dsl.Container;
import org.arquillian.cube.docker.impl.client.containerobject.dsl.DockerContainer;
import org.jboss.arquillian.junit.Arquillian;
import org.junit.Test;
import org.junit.runner.RunWith;

import static io.restassured.RestAssured.given;
import static org.assertj.core.api.Assertions.assertThat;

@RunWith(Arquillian.class)
public class TestTodoServiceArquillianCubeDSL {
    private static final String APP_PATH = "/opt/app";
    private static final String JAR_FILE = "id-service-0.1-runner.jar";

    @DockerContainer
    Container idServiceContainer = Container.withContainerName("idService")
            .fromImage("eclipse-temurin:11")
            .withCommand("mkdir " + APP_PATH)
            .withPortBinding(8085)
            .build();

    @Test
    public void shouldGetNewId() {
        given()
        .when().get("/id")
        .then()
            .statusCode(200);

        assertThat(this.idServiceContainer).isNotNull();
    }
}
