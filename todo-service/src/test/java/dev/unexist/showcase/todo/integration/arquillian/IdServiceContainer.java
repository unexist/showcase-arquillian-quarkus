/**
 * @package Showcase
 * @file
 * @copyright 2022 Christoph Kappel <christoph@unexist.dev>
 * @version $Id\$
 *
 *         This program can be distributed under the terms of the Apache License v2.0.
 *         See the file LICENSE for details.
 **/

package dev.unexist.showcase.todo.integration.arquillian;

import org.arquillian.cube.HostIp;
import org.arquillian.cube.HostPort;
import org.arquillian.cube.containerobject.Cube;
import org.arquillian.cube.containerobject.CubeDockerFile;
import org.jboss.shrinkwrap.api.Archive;
import org.jboss.shrinkwrap.api.GenericArchive;
import org.jboss.shrinkwrap.api.ShrinkWrap;
import org.jboss.shrinkwrap.api.asset.StringAsset;
import org.jboss.shrinkwrap.descriptor.api.Descriptors;
import org.jboss.shrinkwrap.descriptor.api.docker.DockerDescriptor;

import java.nio.file.Path;
import java.nio.file.Paths;

@Cube(value = "idservice", portBinding = "8085->8085/tcp")
public class IdServiceContainer {
    private static final String APP_PATH = "/opt/app";
    private static final String JAR_FILE = "id-service-0.1-runner.jar";

    @HostIp
    String dockerHost;

    @HostPort(8085)
    private int port;

    @CubeDockerFile
    public static Archive<?> createContainer() {
        Path jarPath = Paths.get(Paths.get("").toAbsolutePath().toString(),
                "/../id-service/target/", JAR_FILE);

        String dockerDescriptor = Descriptors.create(DockerDescriptor.class)
                .from("eclipse-temurin:11")
                .run("mkdir " + APP_PATH)
                .copy(jarPath.toAbsolutePath().toString(), APP_PATH)
                .cmd("java", "-jar", Paths.get(APP_PATH, JAR_FILE).toString())
                .expose(8085)
                .exportAsString();

        return ShrinkWrap.create(GenericArchive.class)
                .add(new StringAsset(dockerDescriptor), "Dockerfile");
    }

    public int getConnectionPort() {
        return this.port;
    }

    public String getDockerHost() {
        return this.dockerHost;
    }
}
