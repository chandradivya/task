package com.crud.assignment.task.service;

import com.crud.assignment.task.model.OrderDetails;
import com.crud.assignment.task.repository.OrderDetailsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import java.awt.print.Book;
import java.util.ArrayList;
import java.util.List;

@Transactional
@Service
public class OrderDetailsService {

    @PersistenceContext
    private EntityManager entityManager;

    /**
     * This method is responsible to get all orderid available in database and return it as List<OrderDetails>
     */
    public List<OrderDetails> getallOrderDetails()
    {
        String hql = "FROM OrderDetails";
        return (List<OrderDetails>) entityManager.createQuery(hql).getResultList();
    }

    /**
     * This method is responsible to get a particular order detail by given book orderid
     */

    public OrderDetails getOrder(int orderid) {

        return entityManager.find(OrderDetails.class, orderid);
    }

    /**
     * This method will get the latest inserted record from the database and return the object of order details class
     * @return order
     */
    private OrderDetails getLastInsertedBook(){
        String hql = "from OrderDetails order by id ASC";
        Query query = entityManager.createQuery(hql);
        query.setMaxResults(1);
        OrderDetails recentorder = (OrderDetails)query.getSingleResult();
        return recentorder;
    }

    /**
     * This method is responsible to create new order detail in database
     */
    public OrderDetails creatorderdetail(OrderDetails orderdetails) {
        entityManager.persist(orderdetails);
        OrderDetails od = getLastInsertedBook();
        return od;
    }
    /**
     * This method is responsible for deleting a particular(which id will be passed that record)
     * record from the database
     */

    public boolean deleteorderdetails(int orderid) {
        OrderDetails od = getOrder(orderid);
        entityManager.remove(od);

        //Checking whether entityManager contains earlier deleted order detail or not
        // if contains then order details is not deleted from DB that's why returning false;
        boolean status = entityManager.contains(od);
        if(status){
            return false;
        }
        return true;
    }
}
