package com.crud.assignment.task.controller;

import com.crud.assignment.task.model.OrderDetails;
import com.crud.assignment.task.service.OrderDetailsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.awt.print.Book;
import java.util.List;


@RestController
@RequestMapping("/demagicland")
public class OrderDetailsController {

    @Autowired
    OrderDetailsService orderdetailservice;

    //To retrieve all the order details from the database
    @GetMapping("OrderDetail")
    public List<OrderDetails> getallOrderDetails()
    {
    return orderdetailservice.getallOrderDetails();
    }

    //To retrieve the particular order detail from the database
   @GetMapping("OrderDetail/{id}")
    public OrderDetails getOrder(@PathVariable("id") Integer id){
        return orderdetailservice.getOrder(id);
    }

    //To insert data to db
    @PostMapping("OrderDetail")
    public ResponseEntity<OrderDetails> createorder(@RequestBody OrderDetails orderdetails){
        OrderDetails od = orderdetailservice.creatorderdetail(orderdetails);
        return new ResponseEntity<OrderDetails>(od, HttpStatus.OK);
    }

    // To update the data in db based on order id
    @PutMapping("OrderDetail/{id}")
    public ResponseEntity<OrderDetails> updateorder(@PathVariable("id") int id, @RequestBody OrderDetails orderdetails){

        OrderDetails od = orderdetailservice.updateorderdetails(id, orderdetails);
        return new ResponseEntity<OrderDetails>(od, HttpStatus.OK);
    }

    //Delete the particular row based on order id from table
    @DeleteMapping("OrderDetail/{id}")
    public ResponseEntity<String> deleteorder(@PathVariable("id") int id){
        boolean isDeleted = orderdetailservice.deleteorderdetails(id);
        if(isDeleted){
            String responseContent = "Order Details has been deleted successfully";
            return new ResponseEntity<String>(responseContent,HttpStatus.OK);
        }
        String error = "Error while deleting book from database";
        return new ResponseEntity<String>(error,HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
