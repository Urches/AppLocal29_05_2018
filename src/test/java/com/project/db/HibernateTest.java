package com.project.db;

import com.project.model.component.ComponentEntry;
import org.hibernate.Session;
import org.hibernate.query.Query;
import org.junit.Assert;
import org.junit.Test;

import java.util.List;

public class HibernateTest {

    @Test
    public void testFull(){
        ComponentEntry componentEntity = new ComponentEntry();
        componentEntity.setJsonScript("test");

        //add
        try(Session session = HibernateUtils.getSession()){
            session.beginTransaction();
            session.save(componentEntity);
            session.getTransaction().commit();
        } catch (Exception e){
            throw new AssertionError(e);
        }

        //get
        List<ComponentEntry> list;
        try(Session session = HibernateUtils.getSession()){
            session.beginTransaction();
            Query query = session.createQuery("from com.project.model.component.ComponentEntry");
            list = (List<ComponentEntry>)query.list();
            session.getTransaction().commit();
        } catch (Exception e){
            throw new AssertionError(e);
        }
        Assert.assertEquals(componentEntity, list.get(list.size() - 1));

        //remove
        try(Session session = HibernateUtils.getSession()){
            session.beginTransaction();
            session.delete(componentEntity);
            session.getTransaction().commit();
        } catch (Exception e){
            throw new AssertionError(e);
        }
    }
}
