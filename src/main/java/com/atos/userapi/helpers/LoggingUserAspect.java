package com.atos.userapi.helpers;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;

@Aspect
@Component
public class LoggingUserAspect {
    private static final Logger logger = LogManager.getLogger(LoggingUserAspect.class);

    @Around("execution(* com.atos.userapi.api.UserApi.addUser(..) )")
    public Object callOnUserPOST( ProceedingJoinPoint proceedingJoinPoint ) throws Throwable {
        return logDataFromUserAPI(proceedingJoinPoint);
    }

    @Around("execution(* com.atos.userapi.api.UserApi.getUser(..) )")
    public Object callOnUserGET( ProceedingJoinPoint proceedingJoinPoint ) throws Throwable {
        return logDataFromUserAPI(proceedingJoinPoint);
    }

    private Object logDataFromUserAPI(ProceedingJoinPoint proceedingJoinPoint) throws Throwable {
        Object[] args = proceedingJoinPoint.getArgs();
        for (int i = 0; i < args.length; i++){
            logger.debug("parameter " + i + " : " + args[i]);
        }
        Object value = null;
        try {
            long startTime = System.currentTimeMillis();
            value = proceedingJoinPoint.proceed(); // call methode addUser
            long endTime = System.currentTimeMillis();
            logger.debug(((endTime - startTime)) + " milliseconds");
        } catch (Throwable e) {
            logger.error("Error : " + e.getMessage());
            e.printStackTrace();
            throw e;
        }
        logger.debug("return value : " + value);
        return value;
    }
}
