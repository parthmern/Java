package com.example.todoApiSpring;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.stereotype.Component;

@Aspect
@Component
public class TimeMonitorAspect {

    @Around("@annotation(TimeMonitor)")
    public void logtime(ProceedingJoinPoint joinPoint){

        System.out.println("Logging time");
        long start = System.currentTimeMillis();
        try{
            // execute the joint point
            joinPoint.proceed();
        }catch (Throwable e){
            System.out.println("error");
        }finally {
            long end = System.currentTimeMillis();
            long total = end-start;
            System.out.println("total ->"+ total);
        }

    }
}
