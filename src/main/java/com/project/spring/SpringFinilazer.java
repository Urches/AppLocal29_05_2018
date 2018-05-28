package com.project.spring;

import com.project.db.HibernateUtils;
import org.springframework.beans.factory.DisposableBean;
import org.springframework.context.annotation.Bean;

public class SpringFinilazer implements DisposableBean {
    @Override
    public void destroy() throws Exception {
        System.out.println("destroy");
        HibernateUtils.turnOffHibernate();
    }
}
