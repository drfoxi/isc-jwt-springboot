package com.iscdemo.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.iscdemo.models.entity.User;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserDao extends JpaRepository<User, Integer> {

	User findByUsername(String username);
	List<User> findByUserRole(int userRole);

    User findById(long id);
}