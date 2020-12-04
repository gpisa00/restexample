package it.arteprogrammazione.restexample.commons.aspects;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Configuration;

@Aspect
@Configuration
public class InputOutputAspectLog {

    private final Logger logger = LoggerFactory.getLogger(InputOutputAspectLog.class);

    @Pointcut("execution(* it.arteprogrammazione.restexample.services.interfaces.*.*.*(..))")
    public void serviceMethods() {
    }

    @Before("serviceMethods()")
    public void serviceExecution(JoinPoint joinPoint) {
        logger.info("INIZIO - Esecuzione metodo: " + joinPoint.getSignature().getName());
        for (Object o : joinPoint.getArgs()) {
            logger.info("INPUT : " + o.toString());
        }
    }

    @AfterReturning(value = "serviceMethods()", returning = "result")
    public void afterServiceExecution(JoinPoint joinPoint, Object result) {
        if (result != null)
            logger.info("RESULT : " + result.toString());
        logger.info("FINE - Esecuzione metodo: " + joinPoint.getSignature().getName());
    }

    @AfterThrowing(value = "serviceMethods()", throwing = "ex")
    public void afterExceptionServiceExecution(JoinPoint joinPoint, Throwable ex) {
        logger.error("FINE - Esecuzione metodo: " + joinPoint.getSignature().getName() +
                " terminato con un errore - " + ex.getMessage());
    }

}
