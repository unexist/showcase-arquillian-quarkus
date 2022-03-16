/**
 * @package Quarkus-Logging-Tracing-Quarkus
 *
 * @file Todo service and domain service
 * @copyright 2021-2022 Christoph Kappel <christoph@unexist.dev>
 * @version $Id$
 *
 * This program can be distributed under the terms of the Apache License v2.0.
 * See the file LICENSE for details.
 **/

package dev.unexist.showcase.todo.domain.todo;

import com.tersesystems.echopraxia.Logger;
import com.tersesystems.echopraxia.LoggerFactory;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import java.util.Optional;
import java.util.UUID;

@ApplicationScoped
public class TodoService {
    private static final Logger<Todo.FieldBuilder> LOGGER = LoggerFactory.getLogger(TodoService.class)
            .withFieldBuilder(Todo.FieldBuilder.class);

    @Inject
    TodoRepository todoRepository;

    /**
     * Create new {@link Todo} entry and store it in repository
     *
     * @param  base  A {@link TodoBase} entry
     *
     * @return Either id of the entry on success; otherwise {@code -1}
     **/

    public Optional<Todo> create(TodoBase base) {
        Todo todo = new Todo(base);

        todo.setId(UUID.randomUUID().toString());

        LOGGER.info("Added id to todo: {}",
                fb -> fb.onlyTodo("todo", todo));

        return Optional.of(todo);
    }

    /**
     * Update {@link Todo} at with given id
     *
     * @param  todo  A {@link Todo} to update
     *
     * @return Either {@code true} on success; otherwise {@code false}
     **/

    public boolean update(Todo todo) {
        boolean ret = false;

        if (this.todoRepository.update(todo)) {
            LOGGER.info("Updated todo: {}",
                    fb -> fb.onlyTodo("todo", todo));

            ret = true;
        } else {
            LOGGER.error("Cannot update todo: {}",
                    fb -> fb.onlyTodo("todo", todo));
        }

        return ret;
    }

    /**
     * Find {@link Todo} by given id
     *
     * @param  id  Id to look for
     *
     * @return A {@link Optional} of the entry
     **/

    public Optional<Todo> findById(String id) {
        return this.todoRepository.findById(id);
    }
}
