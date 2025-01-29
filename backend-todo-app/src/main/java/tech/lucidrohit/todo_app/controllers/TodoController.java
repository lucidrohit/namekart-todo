package tech.lucidrohit.todo_app.controllers;

import java.util.List;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMethod;

import tech.lucidrohit.todo_app.dtos.TodoDTO;
import tech.lucidrohit.todo_app.services.TodoService;

// Operations -
// GET todos
// GET todos/{id}
// POST todos
// DELETE todos/{id}
// PUT todos/{id}

@RestController
@RequestMapping("/api")
@CrossOrigin(origins = "*", allowedHeaders = "*", methods = {RequestMethod.GET, RequestMethod.POST, RequestMethod.PUT, RequestMethod.DELETE, RequestMethod.OPTIONS})
public class TodoController {

    final TodoService todoService;

    public TodoController(TodoService todoService){
        this.todoService = todoService;
    }

    @GetMapping("/")
    public String sayHello(){
        return "Possible Operations: \n Operations -\n" + //
                        "GET todos\n" + //
                        "GET todos/{id}\n" + //
                        "POST todos\n" + //
                        "DELETE todos/{id}\n" + //
                        "PUT todos/{id}";
    }
    
    @GetMapping("/todos/{id}")
    public TodoDTO getTodo(@PathVariable Long id){
        return todoService.getTodoById(id);
    }

    @GetMapping("/todos")
    public List<TodoDTO> getTodos(){
        return todoService.getTodos();
    }

    @PostMapping("/todos")
    public TodoDTO createTodo(@RequestBody TodoDTO todoDTO){
        return todoService.createTodo(todoDTO);
    }

    @DeleteMapping("/todos/{id}")
    public void deleteTodo(@PathVariable Long id){
        todoService.deleteTodoById(id);
    }

    @PutMapping("/todos/{id}")
    public TodoDTO updateTodo(@PathVariable Long id, @RequestBody TodoDTO todoDTO){
        todoDTO.setId(id);
        return todoService.updateTodoById(todoDTO);
    }
}
