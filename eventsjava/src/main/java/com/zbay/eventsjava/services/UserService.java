package com.zbay.eventsjava.services;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.zbay.eventsjava.models.Role;
import com.zbay.eventsjava.models.User;
import com.zbay.eventsjava.repositories.RoleRepository;
import com.zbay.eventsjava.repositories.UserRepository;
@Service
public class UserService {
    private UserRepository userRepository;
    private RoleRepository roleRepository;
    private BCryptPasswordEncoder bCryptPasswordEncoder;
    
    public UserService(UserRepository userRepository, RoleRepository roleRepository, BCryptPasswordEncoder bCryptPasswordEncoder)     {
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
    }
    
    public void saveWithUserRole(User user) {
        user.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));
        user.setRoles(roleRepository.findByName("ROLE_USER"));
        userRepository.save(user);
    }
     
    public void saveWithAdminRole(User user) {
    	List<Role> roles = new ArrayList<Role>();
    	roles.add(roleRepository.findByName("ROLE_USER").get(0));
    	roles.add(roleRepository.findByName("ROLE_ADMIN").get(0));
        user.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));
        user.setRoles(roles);
        userRepository.save(user);
    }  
    
    public void saveWithSuperAdminRole(User user) {
    	List<Role> roles = new ArrayList<Role>();
    	roles.add(roleRepository.findByName("ROLE_USER").get(0));
    	roles.add(roleRepository.findByName("ROLE_ADMIN").get(0));
    	roles.add(roleRepository.findByName("ROLE_SUPERADMIN").get(0));
        user.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));
        user.setRoles(roles);
        userRepository.save(user);
    }  
    
    public List<User> findByEmail(String email) {
        return userRepository.findByEmail(email);
    }
    
    public List<User> findAll(){
    	return this.userRepository.findAll();
    }
    
    public void update(User user){
    	user.setUpdatedAt(new Date());
    	this.userRepository.save(user);
    }
    
    public boolean adminExists() {
    	return this.userRepository.findAllAdmins().size() > 0;
    }
    
    public void deleteUserById(long id) {
    	this.userRepository.delete(id);
    }
    
    public void makeAdminById(long id) {
    	User user = this.userRepository.findOne(id);
    	user.setRoles(roleRepository.findByName("ROLE_ADMIN"));
    	this.userRepository.save(user);
    }
}
