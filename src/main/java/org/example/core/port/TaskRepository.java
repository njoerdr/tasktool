package org.example.core.port;

import java.util.List;

import org.example.core.domain.Task;

public interface TaskRepository {

    List<Task> retrieveAll();

    Task retrieve(Long id);

    void update(Task task);

    void persist(Task task);

    void persist(List<Task> tasks);

    void delete(Long id);
}
