package dev.amirgol.springtaskbackend.Domain.tasks.repository;

import dev.amirgol.springtaskbackend.Domain.category.model.Category;
import dev.amirgol.springtaskbackend.Domain.tasks.model.Task;

import java.util.List;
import java.util.Optional;

public interface TaskRepository {
    Optional<List<Task>> findAllTasks();

    Optional<Task> findTaskById(short id);

    Optional<Task> findTaskByTitle(String title);

    Optional<List<Task>> findTaskCategories(Category category);

    void save(Task task);

    void delete(Task task);
}
