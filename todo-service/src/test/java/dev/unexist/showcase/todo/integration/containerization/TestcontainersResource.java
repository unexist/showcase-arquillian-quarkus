/**
 * @package Showcase-Testing-Quarkus
 *
 * @file Hoverfly resource manager
 * @copyright 2022 Christoph Kappel <christoph@unexist.dev>
 * @version $Id$
 *
 * This program can be distributed under the terms of the Apache License v2.0.
 * See the file LICENSE for details.
 **/

package dev.unexist.showcase.todo.integration.containerization;

import io.quarkus.test.common.QuarkusTestResourceLifecycleManager;
import org.testcontainers.containers.GenericContainer;
import org.testcontainers.images.builder.ImageFromDockerfile;

import java.util.Map;

public class TestcontainersResource implements QuarkusTestResourceLifecycleManager {
    private static final String SERVICE_URL = "localhost:8085";
    private static final String APP_PATH = "/opt/app";
    private static final String JAR_FILE = "id-service-0.1.jar";

    private GenericContainer<?> container;

    @Override
    public Map<String, String> start() {
        this.container = new GenericContainer(
                new ImageFromDockerfile()
                        .withDockerfileFromBuilder(builder ->
                                builder
                                        .from("eclipse-temurin:11")
                                        .run("mkdir /opt/app")
                                        .copy("../../../../../id-service/target/" + JAR_FILE, APP_PATH)
                                        .cmd("javan", "-jar", APP_PATH + JAR_FILE)
                                        .build()))
                .withExposedPorts(8085);

        this.container.start();

        return Map.of("id.service.url", SERVICE_URL);
    }

    @Override
    public void stop() {
        this.container.stop();
    }
}