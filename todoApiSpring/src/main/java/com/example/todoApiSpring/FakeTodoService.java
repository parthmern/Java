package com.example.todoApiSpring;

import org.springframework.stereotype.Component;

@Component
public class FakeTodoService implements TodoService {
    @Override
    @TimeMonitor
    public String doSomething(){
        System.out.println("Something FakeTodoService");
        return "Something FakeTodoService";
    }
}
