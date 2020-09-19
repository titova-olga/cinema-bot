package com.java_school.informator.aspects;

import com.java_school.informator.constants.RestUrls;
import com.java_school.informator.dto.CinemaUserChoiceDTO;
import com.java_school.informator.dto.DateUserChoiceDTO;
import com.java_school.informator.dto.FilmUserChoiceDTO;
import com.java_school.informator.dto.UserChoiceDTO;
import lombok.SneakyThrows;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.time.LocalDate;

@Component
@Aspect
public class KafkaSenderAspect {

    @Autowired
    @LoadBalanced
    private RestTemplate restTemplate;

    @SneakyThrows
    @Around("execution(* com.java_school.informator.controllers.UserChoiceController.*(..)))")
    public void beforeAddingNewUserChoice(ProceedingJoinPoint jp)  {
        Object arg = jp.getArgs()[0];
        if(arg instanceof UserChoiceDTO) {
            //UserChoiceDTO userChoiceDTO = (UserChoiceDTO) arg;
            //long chatId = userChoiceDTO.getChatId();
            restTemplate.postForLocation(RestUrls.USER_CHOICE, arg);
        }
        jp.proceed();
    }
}
