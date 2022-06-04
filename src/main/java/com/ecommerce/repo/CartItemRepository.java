package com.ecommerce.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.ecommerce.entity.CartItem;


@Repository
public interface CartItemRepository extends JpaRepository<CartItem, Integer> {

}
