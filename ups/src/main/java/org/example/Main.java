package org.example;

import java.io.IOException;
import java.util.List;

import org.example.domain.*;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;

public class Main {
    public static void main(String[] args) throws IOException, InterruptedException {
        // sudo su - postgres
        // psql
        // ALTER USER postgres with encrypted password 'abc123';

        Configuration configuration = new Configuration();
        configuration.configure("hibernate.cfg.xml");

        SessionFactory sessionFactory = configuration.buildSessionFactory();
        Session session = sessionFactory.openSession();
        // Step 1: Clear data from all tables
        Transaction clearTransaction = session.beginTransaction();
        session.createNativeQuery("DELETE FROM u_delivery_location").executeUpdate();
        session.createNativeQuery("DELETE FROM u_go_deliver").executeUpdate();
        session.createNativeQuery("DELETE FROM u_go_pickup").executeUpdate();
        session.createNativeQuery("DELETE FROM u_query").executeUpdate();
        session.createNativeQuery("DELETE FROM a_book_truck").executeUpdate();
        session.createNativeQuery("DELETE FROM acks").executeUpdate();
        session.createNativeQuery("DELETE FROM a_start_deliver").executeUpdate();
        session.createNativeQuery("DELETE FROM package").executeUpdate();
        session.createNativeQuery("DELETE FROM truck").executeUpdate();
        session.createNativeQuery("DELETE FROM seqnum").executeUpdate();
        clearTransaction.commit();

        // 关闭 session 和 sessionFactory
        session.close();

        UpsServer upsServer = new UpsServer(22222, args[0], sessionFactory);
        upsServer.start();
        sessionFactory.close();

        // MockAmazon mockamazon= new MockAmazon("vcm-30481.vm.duke.edu",22222);
        // mockamazon.connectToWorld(mockamazon.createUConnect(null,10));
        // mockamazon.sendAInformWorld();
        // Thread.sleep(5000);
        // mockamazon.sendABookTruck();
        // while(true){
        //     ;
        // }

        
    }
}