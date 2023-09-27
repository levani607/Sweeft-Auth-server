package me.levani.authorizationserver.model.annotation;

import me.levani.authorizationserver.model.request.FlowExecutionRequest;
import jakarta.validation.Constraint;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import jakarta.validation.Payload;

import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import static java.lang.annotation.ElementType.FIELD;

@Target({FIELD})
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Constraint(validatedBy = {FlowExecutionValidator.FlowExecutionValidatorImpl.class})
public @interface FlowExecutionValidator {


    String message() default "";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
    class FlowExecutionValidatorImpl implements ConstraintValidator<FlowExecutionValidator, List<FlowExecutionRequest>> {

        @Override
        public boolean isValid(List<FlowExecutionRequest> requests, ConstraintValidatorContext constraintValidatorContext) {
            Set<Integer> collect = requests.stream().map(FlowExecutionRequest::getOrderId).collect(Collectors.toSet());
            if(collect.size()!=requests.size()){
                constraintValidatorContext.buildConstraintViolationWithTemplate("Duplicate values found in order ids!").addConstraintViolation();
                return false;
            }
            return true;
        }
    }
}
