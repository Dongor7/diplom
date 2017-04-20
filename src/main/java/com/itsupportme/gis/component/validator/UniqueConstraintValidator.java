package com.itsupportme.gis.component.validator;


import com.itsupportme.gis.component.validator.constraint.UniqueEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;


import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

@Component
public class UniqueConstraintValidator implements ConstraintValidator<UniqueEntity, Object> {

    private String message;
    private String field;

    @Autowired
    private UniqueValidatorModel model;

    @Override
    public void initialize(UniqueEntity uniqueEntity) {
        this.message = uniqueEntity.message();
        this.field   = uniqueEntity.field();
    }

    @Override
    @SuppressWarnings("unchecked")
    public boolean isValid(Object value, ConstraintValidatorContext context) {

        String currentEntityName = value.getClass().getSimpleName();
        String methodName        = "validate" + currentEntityName;

        Class reflectionModel   = model.getClass();

        Boolean result = true;

        try {
            Class[] parameterTypes = {value.getClass()};

            Method method = reflectionModel.getMethod(methodName, parameterTypes);
            result = (Boolean) method.invoke(model, value);
        } catch (IllegalAccessException | NoSuchMethodException | InvocationTargetException  e) {
            e.printStackTrace();
        }

        if (!result) {
            context.buildConstraintViolationWithTemplate(this.message)
                    .addPropertyNode(this.field).addConstraintViolation();
        }

        return result;
    }
}
