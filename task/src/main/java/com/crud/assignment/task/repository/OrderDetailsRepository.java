package com.crud.assignment.task.repository;

import com.crud.assignment.task.model.OrderDetails;
import org.springframework.data.repository.CrudRepository;

//Repo that extends CrudRepository
public interface OrderDetailsRepository extends CrudRepository<OrderDetails,Integer> {
}
