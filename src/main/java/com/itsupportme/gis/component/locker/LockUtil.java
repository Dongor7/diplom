package com.itsupportme.gis.component.locker;

import com.itsupportme.gis.component.locker.exception.InvalidEntityException;
import org.springframework.stereotype.Component;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

@Component
public class LockUtil {

    @SuppressWarnings("unchecked")
    public Integer findEntityId(Object object) throws InvalidEntityException {

        Integer entityId;

        Class reflectionObject = object.getClass();
        String methodName      = "getId";

        try {
            Method method = reflectionObject.getMethod(methodName);
            entityId      = (Integer) method.invoke(object);
        } catch (IllegalAccessException | NoSuchMethodException | InvocationTargetException e) {
            throw new InvalidEntityException();
        }

        return entityId;
    }
}
