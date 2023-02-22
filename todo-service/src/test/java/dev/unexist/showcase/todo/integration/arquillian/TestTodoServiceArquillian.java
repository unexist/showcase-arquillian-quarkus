/**
 * @package Showcase
 * @file
 * @copyright 2022-present Christoph Kappel <christoph@unexist.dev>
 * @version $Id$
 *
 * This program can be distributed under the terms of the Apache License v2.0.
 * See the file LICENSE for details.
 **/

package dev.unexist.showcase.todo.integration.arquillian;

import dev.unexist.showcase.todo.domain.todo.TodoBase;
import io.restassured.RestAssured;
import org.jboss.arquillian.container.test.api.Deployment;
import org.jboss.arquillian.container.test.api.RunAsClient;
import org.jboss.arquillian.junit5.ArquillianExtension;
import org.jboss.arquillian.test.api.ArquillianResource;
import org.jboss.shrinkwrap.api.ShrinkWrap;
import org.jboss.shrinkwrap.api.asset.EmptyAsset;
import org.jboss.shrinkwrap.api.spec.JavaArchive;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.net.URI;

import static io.restassured.RestAssured.given;

@ExtendWith(ArquillianExtension.class)
public class TestTodoServiceArquillian {
    private static final Logger LOGGER = LoggerFactory.getLogger(TestTodoServiceArquillian.class);

    @ArquillianResource
    URI uri;

    @Deployment
    public static JavaArchive createDeployment() {
        JavaArchive archive =  ShrinkWrap.create(JavaArchive.class)
                .addClass(TodoBase.class)
                .addAsManifestResource(EmptyAsset.INSTANCE, "beans.xml");

        return archive;
    }

    @BeforeEach
    public void setUp() {
        RestAssured.baseURI = this.uri.toString();
        LOGGER.info("{}", this.uri.toString());
    }

    @Test
    @RunAsClient
    public void shouldGetNewId() {
        given()
        .when().get("/id")
        .then()
            .statusCode(200);
    }
}
