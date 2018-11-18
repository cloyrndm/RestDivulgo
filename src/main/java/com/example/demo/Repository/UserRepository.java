package com.example.demo.Repository;


import com.example.demo.Entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Created by Cloie Andrea on 15/07/2018.
 */
@Repository
public interface UserRepository extends JpaRepository<User,Long> {
//    public User findByUsername(String username);
    public User findUserByEmail(String email);

//    public User findOneByUsername(String username);
    User findByUsernameAndPassword(String username, String password);
//    @Query("SELECT user FROM User user where t.title = ?1 AND t.description = ?2")
//    public User findByUsernameAndPassword(String username, String password);
}
