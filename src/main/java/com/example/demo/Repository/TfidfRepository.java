package com.example.demo.Repository;

import com.example.demo.Entity.Tfidf;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;


/**
 * Created by Cloie Andrea on 12/11/2018.
 */
@Repository
public interface TfidfRepository extends JpaRepository<Tfidf,Long> {
//    public static final String FIND_PROJECTS = "SELECT projectId, projectName FROM projects";

//    @Query(value = FIND_PROJECTS, nativeQuery = true)
//    public List<Tfidf> findArt();
//    public interface UserRepository  extends JpaRepository<User, Integer> {
//        User findByUsernameAndPassword(String username,String password);
    public List<Tfidf> findAll();
    public Tfidf findByNgramId(int id);
    public Tfidf findByFreqId(int id);
    public Tfidf findByTfidfId(int id);
}
