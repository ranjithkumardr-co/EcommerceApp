package com.ecommerce.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.ecommerce.entity.Cart;
import com.ecommerce.entity.User;


@Repository
public interface CartRepository extends JpaRepository<Cart, Integer> {

	
	Cart findByUser(User user);
}
