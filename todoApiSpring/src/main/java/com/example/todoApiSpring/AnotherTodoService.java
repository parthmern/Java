package com.example.todoApiSpring;

import org.springframework.stereotype.Component;

@Component
public class AnotherTodoService implements TodoService {
    @Override
    public String doSomething(){
        return "Something AnotherTodoService";
    }
}
