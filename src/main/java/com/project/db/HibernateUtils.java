package com.project.db;

import com.project.model.exceptions.LogicException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

public class HibernateUtils {

    private static  final SessionFactory factory;

    static {
        try {
            Configuration configuration = new Configuration();
            configuration.configure();

            factory = configuration.buildSessionFactory();
        } catch (Throwable e){
            e.printStackTrace();
           throw new LogicException("Hibernate error!");
        }
    }

    public static Session getSession(){
        return factory.openSession();
    }

    public static void turnOffHibernate(){
        factory.close();
    }
}
