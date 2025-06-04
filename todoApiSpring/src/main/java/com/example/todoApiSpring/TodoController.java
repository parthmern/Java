package com.example.todoApiSpring;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
//@RequestMapping("/api/v1")
public class TodoController {
    private static List<Todo> todoList;

    private TodoService todoService;

    TodoController(){
        todoList = new ArrayList<>();
        todoList.add(new Todo(1,false, "Todo1", 1));
        todoList.add(new Todo(2,false, "Todo2", 2));
        this.todoService = new TodoService();
    }

    @GetMapping("/todos")
    public  List<Todo> getTodos(@RequestParam(required = false, defaultValue = "true") Boolean isCompleted){
        System.out.println("query param is" + isCompleted);
        return  todoList;
    }

    @PostMapping("/todo")
    //@ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<Todo> createTodo(@RequestBody Todo newTodo){
        todoList.add(newTodo);
        return ResponseEntity.status(HttpStatus.CREATED).body(newTodo);
    }

    @GetMapping("/todo/{todoId}")
    public ResponseEntity<Todo> getTodoById(@PathVariable Long todoId){
        for(Todo todo : todoList){
            if(todo.getId() == todoId){
                return ResponseEntity.ok(todo);
            }
        }
        return ResponseEntity.notFound().build();
    }

}
