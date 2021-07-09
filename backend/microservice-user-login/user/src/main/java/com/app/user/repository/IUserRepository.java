package com.app.user.repository;

import com.app.user.model.User;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface IUserRepository extends MongoRepository<User, String> {
    User findByEmail(String email);

    User findByPassword(String pass);
}
