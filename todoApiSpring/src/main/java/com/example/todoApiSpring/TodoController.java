package com.example.todoApiSpring;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

@RestController
public class TodoController {
    private static List<Todo> todoList;

    TodoController(){
        todoList = new ArrayList<>();
        todoList.add(new Todo(1,false, "Todo1", 1));
        todoList.add(new Todo(2,false, "Todo2", 2));

    }

    @GetMapping("/todos")
    public  List<Todo> getTodos(){
        return  todoList;
    }

    @PostMapping("/todo")
    public Todo createTodo(@RequestBody Todo newTodo){
        todoList.add(newTodo);
        return newTodo;
    }



}
