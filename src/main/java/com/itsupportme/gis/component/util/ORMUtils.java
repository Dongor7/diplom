package com.itsupportme.gis.component.util;

import org.hibernate.Hibernate;
import org.hibernate.proxy.HibernateProxy;


public class ORMUtils {

    @SuppressWarnings("unchecked")
    public static <T> T initializeAndUnproxy(T entity) {

        if (entity == null) {
            return null;
        }

        if (entity instanceof HibernateProxy) {
            Hibernate.initialize(entity);
            entity = (T) ((HibernateProxy) entity).getHibernateLazyInitializer().getImplementation();
        }

        return entity;
    }
}
