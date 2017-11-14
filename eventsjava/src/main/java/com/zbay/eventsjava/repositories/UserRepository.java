package com.zbay.eventsjava.repositories;
import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import com.zbay.eventsjava.models.User;

public interface UserRepository extends CrudRepository<User, Long> {
	
    List<User> findByEmail(String email);
    
    public List<User> findAll();
    
    @Query("SELECT u FROM User u JOIN u.roles r WHERE r.name = 'ROLE_ADMIN'")
    public List<User> findAllAdmins(); 
    
}