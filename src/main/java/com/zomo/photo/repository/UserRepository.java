package com.zomo.photo.repository;

import com.zomo.photo.entity.User;
import org.springframework.data.repository.CrudRepository;

public interface UserRepository extends CrudRepository<User,Integer> {
    Integer countByUsername(String username);
    Integer countByUsernameAndPassword(String username,String password);
    User findByUsernameAndPassword(String username,String password);

}

