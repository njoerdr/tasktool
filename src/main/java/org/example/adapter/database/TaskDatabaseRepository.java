package org.example.adapter.database;

import java.util.List;
import java.util.Objects;
import java.util.stream.Stream;

import org.example.core.domain.Task;
import org.example.core.port.TaskRepository;

import io.quarkus.panache.common.Sort;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.ws.rs.NotFoundException;

@ApplicationScoped
public class TaskDatabaseRepository implements TaskRepository {

    @Override
    public List<Task> retrieveAll() {
        List<TaskEntity> entities = TaskEntity.listAll(Sort.by("id"));
        return entities.stream().map(this::toDomain).toList();
    }

    @Override
    public Task retrieve(Long id) {
        TaskEntity entity = TaskEntity.findById(id);
        if (Objects.isNull(entity)) {
            throw new NotFoundException("Task not found");
        }
        return toDomain(entity);
    }

    @Override
    public void update(Task task) {
        TaskEntity entity = fromDomain(task);
        TaskEntity.getEntityManager().merge(entity);
    }

    @Override
    public void persist(Task task) {
        TaskEntity entity = fromDomain(task);
        entity.persist();
    }

    @Override
    public void persist(List<Task> tasks) {
        Stream<TaskEntity> entities = tasks.stream().map(this::fromDomain);
        TaskEntity.persist(entities);
    }

    @Override
    public void delete(Long id) {
        TaskEntity.deleteById(id);
    }

    private Task toDomain(TaskEntity entity) {
        return new Task(entity.id, entity.name, entity.done, entity.created, entity.priority);
    }

    private TaskEntity fromDomain(Task task) {
        var entity = new TaskEntity();
        entity.id = task.getId();
        entity.name = task.getName();
        entity.done = task.isDone();
        entity.created = task.getCreated();
        entity.priority = task.getPriority();
        return entity;
    }

}
