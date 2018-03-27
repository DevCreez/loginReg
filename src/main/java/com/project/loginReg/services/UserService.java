package com.project.loginReg.services;

import java.util.ArrayList;
import java.util.List;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.project.loginReg.models.User;
import com.project.loginReg.models.Role;
import com.project.loginReg.repositories.UserRepository;
import com.project.loginReg.repositories.RoleRepository;

@Service
public class UserService {
	private UserRepository userRepository;
    private RoleRepository roleRepository;
    private BCryptPasswordEncoder bcrypt;
    
    public UserService(UserRepository userRepository, RoleRepository roleRepository, BCryptPasswordEncoder bcrypt)     {
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
        this.bcrypt = bcrypt;

        init();
    }
    
    public void init(){
        if(roleRepository.findAll().size() < 1){
            Role user = new Role();
            user.setName("ROLE_USER");

            Role admin = new Role();
            admin.setName("ROLE_ADMIN");

            roleRepository.save(user);
            roleRepository.save(admin);
        }
    } //this function checks to see if theres any roles in the role table and if its empty it initializes and creates a user and admin roles

    // 1
    public void saveUser(User user) {
        user.setPassword(bcrypt.encode(user.getPassword()));
        user.setRoles(roleRepository.findByName("ROLE_USER"));
        userRepository.save(user);
    }
     
     // 2 
    public void saveAdmin(User user) {
        user.setPassword(bcrypt.encode(user.getPassword()));
        user.setRoles(roleRepository.findByName("ROLE_ADMIN"));
        userRepository.save(user);
    }    
    
    // 3
    public User findByUsername(String username) {
        return userRepository.findByUsername(username);
    }

	public User findByEmail(String email){
		return userRepository.findByEmail(email);
	}

    public ArrayList<User> all(){
        return (ArrayList<User>)userRepository.findAll();
    }
}
