package com.ecommerce.repo;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.ecommerce.entity.User;
import com.ecommerce.entity.UserOrder;


@Repository
public interface UserOrderRepository extends JpaRepository<UserOrder, Integer>{

	

	List<UserOrder> findByUser(User user);

}
