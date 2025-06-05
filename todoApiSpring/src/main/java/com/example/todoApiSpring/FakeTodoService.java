package com.example.todoApiSpring;

import org.springframework.stereotype.Component;

@Component
public class FakeTodoService implements TodoService {
    @Override
    public String doSomething(){
        return "Something FakeTodoService";
    }
}
