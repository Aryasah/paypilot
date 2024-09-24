/**
 * Author: Shrilakshmi Upadhya
 * Date: 6th September 2024
 * 
 * Description: This is the repository for Users model.
 */

package com.paypilot.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.paypilot.model.User;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, String> {
	long countByUserId(String userId);
	Optional<User> findByUserId(String userId);
	Optional<User> findByEmail(String email);
	User findByEmailAndUserId(String email, String userId);
}
