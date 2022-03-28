/**
 * @package Showcase
 * @file Test repository implementation
 * @copyright 2022 Christoph Kappel <christoph@unexist.dev>
 * @version $Id\$
 *
 *         This program can be distributed under the terms of the Apache License v2.0.
 *         See the file LICENSE for details.
 **/

package dev.unexist.showcase.todo;

import dev.unexist.showcase.todo.infrastructure.persistence.ListTodoRepository;
import org.jboss.arquillian.container.test.api.Deployment;
import org.jboss.arquillian.junit5.ArquillianExtension;
import org.jboss.shrinkwrap.api.ShrinkWrap;
import org.jboss.shrinkwrap.api.spec.JavaArchive;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import javax.inject.Inject;

import static org.assertj.core.api.Assertions.assertThat;

@ExtendWith(ArquillianExtension.class)
public class TestListTodoRepository {

    @Inject
    ListTodoRepository repository;


    @Deployment
    public static JavaArchive createDeployment() {
        JavaArchive archive =  ShrinkWrap.create(JavaArchive.class, "test.jar")
                .addClass(ListTodoRepository.class);

        return archive;
    }

    @Test
    public void test_get_id_should_succeed() {
        assertThat(this.repository.getAll().size()).isEqualTo(0);
    }
}
