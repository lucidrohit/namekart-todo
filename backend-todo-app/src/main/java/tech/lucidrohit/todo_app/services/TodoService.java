package tech.lucidrohit.todo_app.services;

import java.util.List;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import tech.lucidrohit.todo_app.dtos.TodoDTO;
import tech.lucidrohit.todo_app.entities.TodoEntity;
import tech.lucidrohit.todo_app.repositories.TodoRepository;
import tech.lucidrohit.todo_app.exceptions.TodoNotFoundException;

@Service
public class TodoService {
    private final Logger logger = LoggerFactory.getLogger(TodoService.class);
    final TodoRepository todoRepository;
    final ModelMapper modelMapper;

    public TodoService(ModelMapper modelMapper, TodoRepository todoRepository) {
        this.modelMapper = modelMapper;
        this.todoRepository = todoRepository;
        logger.info("TodoService initialized");
    }

    public TodoDTO getTodoById(Long id) {
        logger.info("Fetching todo with id: {}", id);
        TodoEntity todoEntity = todoRepository.findById(id)
            .orElseThrow(() -> {
                logger.error("Todo not found with id: {}", id);
                return new TodoNotFoundException(id);
            });
        logger.info("Found todo: {}", todoEntity);
        return modelMapper.map(todoEntity, TodoDTO.class);
    }

    public TodoDTO createTodo(TodoDTO todoDTO) {
        logger.info("Creating new todo: {}", todoDTO);
        TodoEntity todoEntity = modelMapper.map(todoDTO, TodoEntity.class);
        TodoDTO created = modelMapper.map(todoRepository.save(todoEntity), TodoDTO.class);
        logger.info("Created todo: {}", created);
        return created;
    }

    public List<TodoDTO> getTodos() {
        logger.info("Fetching all todos");
        List<TodoDTO> todos = todoRepository.findAll()
            .stream()
            .map(todoEntity -> modelMapper.map(todoEntity, TodoDTO.class))
            .collect(Collectors.toList());
        logger.info("Found {} todos", todos.size());
        return todos;
    }

    public TodoDTO updateTodoById(TodoDTO todoDTO) {
        logger.info("Updating todo with id: {}", todoDTO.getId());
        todoRepository.findById(todoDTO.getId())
            .orElseThrow(() -> {
                logger.error("Todo not found with id: {}", todoDTO.getId());
                return new TodoNotFoundException(todoDTO.getId());
            });
        
        TodoEntity todoEntity = modelMapper.map(todoDTO, TodoEntity.class);
        TodoDTO updated = modelMapper.map(todoRepository.save(todoEntity), TodoDTO.class);
        logger.info("Updated todo: {}", updated);
        return updated;
    }

    public void deleteTodoById(Long id) {
        logger.info("Deleting todo with id: {}", id);
        todoRepository.findById(id)
            .orElseThrow(() -> {
                logger.error("Todo not found with id: {}", id);
                return new TodoNotFoundException(id);
            });
        
        todoRepository.deleteById(id);
        logger.info("Deleted todo with id: {}", id);
    }
}
