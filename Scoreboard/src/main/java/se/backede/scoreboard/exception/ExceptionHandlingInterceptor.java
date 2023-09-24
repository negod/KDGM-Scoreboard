/*
 */
package se.backede.scoreboard.exception;

/**
 *
 * @author Joakim Backede <joakim.backede@outlook.com>
 */
import jakarta.interceptor.AroundInvoke;
import jakarta.interceptor.Interceptor;
import jakarta.interceptor.InvocationContext;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.ConstraintViolationException;
import java.util.logging.Level;
import java.util.logging.Logger;

@Interceptor
public class ExceptionHandlingInterceptor {

    @AroundInvoke
    public Object handleException(InvocationContext context) throws Exception {
        try {
            // Anropa den övervakade metoden
            return context.proceed();
        } catch (Exception e) {
            if (e instanceof ConstraintViolationException) {

                ConstraintViolationException cve = (ConstraintViolationException) e;
                Logger.getLogger(ExceptionHandlingInterceptor.class.getName()).log(Level.INFO, "Constraint Violation Exception cought in Interceptor");

                for (ConstraintViolation<?> constraintViolation : cve.getConstraintViolations()) {
                    Logger.getLogger(ExceptionHandlingInterceptor.class.getName()).log(Level.INFO, "Constraint violation descriptor [0]", constraintViolation.getConstraintDescriptor());
                    Logger.getLogger(ExceptionHandlingInterceptor.class.getName()).log(Level.INFO, "Constraint violation message [0]", constraintViolation.getMessage());
                }

            } else {
                Logger.getLogger(ExceptionHandlingInterceptor.class.getName()).log(Level.INFO, "Cought runtime exception in Interceptor ", e);
            }
            throw e;
        }
    }
}
