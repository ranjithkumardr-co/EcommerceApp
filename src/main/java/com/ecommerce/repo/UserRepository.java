package com.ecommerce.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.ecommerce.entity.*;


@Repository
public interface UserRepository extends JpaRepository<User, Integer> {
	
	User findByUserName(String userName);
	

}
