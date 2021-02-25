package com.example.vstore.bind;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

import org.springframework.transaction.annotation.Transactional;
import org.springframework.jdbc.core.support.JdbcDaoSupport;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;

@Repository
@Transactional
public interface UserServer extends JpaRepository<com.example.vstore.bind.User, Long>{
    @Query("select u from User u where u.user_name = ?1 and u.password = ?2")
    User findByUser_nameAndPassword(String userName, String password);

    @Query("update User u set u.wallet = ?1 where u.id_user = ?2")
    void updateWallet(Long wallet, Long id);

    @Query("select u from User u where u.id_user = ?1")
    User findById_user(Long id_user);

    @Query("select u from User u where u.user_name = ?1")
    Optional<User> findByUser_name(String userName);

    @Query("select u from User u where u.user_name = ?1")
    User findByUser_name1(String userName);


}