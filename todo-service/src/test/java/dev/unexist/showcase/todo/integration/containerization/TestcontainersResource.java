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
import org.testcontainers.containers.FixedHostPortGenericContainer;
import org.testcontainers.containers.GenericContainer;
import org.testcontainers.containers.wait.strategy.Wait;
import org.testcontainers.images.builder.ImageFromDockerfile;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Map;

public class TestcontainersResource implements QuarkusTestResourceLifecycleManager {
    private static final String SERVICE_URL = "localhost:8085";
    private static final String APP_PATH = "/opt/app";
    private static final String JAR_FILE = "id-service-0.1-runner.jar";

    private GenericContainer<?> container;

    @Override
    public Map<String, String> start() {
        Path jarPath = Paths.get(Paths.get("").toAbsolutePath().toString(), "/../id-service/target/", JAR_FILE);

        GenericContainer<?> container = new GenericContainer(
                new ImageFromDockerfile()
                        .withFileFromPath(JAR_FILE, jarPath)
                        .withDockerfileFromBuilder(builder ->
                                builder
                                        .from("eclipse-temurin:11")
                                        .run("mkdir " + APP_PATH)
                                        .copy(JAR_FILE, APP_PATH)
                                        .cmd("java", "-jar", Paths.get(APP_PATH, JAR_FILE).toString())
                                        .expose(8085)
                                        .build()))
                .withExposedPorts(8085);

        this.container = new FixedHostPortGenericContainer(container.getDockerImageName())
            .withFixedExposedPort(8085, 8085)
                .waitingFor(Wait.forLogMessage(".*Listening on:.*", 1));

        this.container.start();

        return Map.of("id.service.url", SERVICE_URL);
    }

    @Override
    public void stop() {
        this.container.stop();
    }
}