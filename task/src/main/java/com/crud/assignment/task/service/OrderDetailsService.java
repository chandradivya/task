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
    private OrderDetails getLastInsertedorder(){
        String hql = "from OrderDetails order by id DESC";
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
        OrderDetails od = getLastInsertedorder();
        return od;
    }

    /**
     * This method is responsible to update book detail in database
     */
    public OrderDetails updateorderdetails(int orderid, OrderDetails orderdetails) {

        //First We are taking order detail from database by given order id and
        // then updating detail with provided orderodetails object
        OrderDetails orderdetailsfromDB = getOrder(orderid);
        //orderdetailsfromDB.setOrder_id(orderdetails.getOrder_id());
        orderdetailsfromDB.setOrderdesc(orderdetails.getOrderdesc());
        orderdetailsfromDB.setCustname(orderdetails.getCustname());
        orderdetailsfromDB.setOrderstatus(orderdetails.getOrderstatus());
        orderdetailsfromDB.setProduct_type(orderdetails.getProduct_type());


        entityManager.flush();

        //again i am taking updated result of book and returning the book object
        OrderDetails updateorderdetails = getOrder(orderid);

        return updateorderdetails;
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
