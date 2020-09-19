package com.java_school.informator.aspects;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.stereotype.Component;

@Component
@Aspect
public class KafkaSenderAspect {

    //"&& @target(org.springframework.web.bind.annotation.PostMapping)"
    //execution(* com.java_school.informator.controllers.UserChoiceController.*.*(..))
    @Around("execution(* com.java_school.informator.controllers.UserChoiceController.*(..)))")
    public void beforeAddingNewUserChoice(ProceedingJoinPoint jp)  {

        System.out.println("Send to kafka...");
    }
}
