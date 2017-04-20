package com.itsupportme.gis.component.validator.constraint;

import com.itsupportme.gis.component.validator.UniqueConstraintValidator;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.*;

@Documented
@Constraint(validatedBy = UniqueConstraintValidator.class)
@Target({ ElementType.TYPE })
@Retention(RetentionPolicy.RUNTIME)
public @interface UniqueEntity {

    String message();
    String field();

    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};

}
