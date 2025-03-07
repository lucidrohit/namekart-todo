package tech.lucidrohit.todo_app.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import tech.lucidrohit.todo_app.entities.TodoEntity;

@Repository
public interface TodoRepository extends JpaRepository<TodoEntity, Long> {
    
}
