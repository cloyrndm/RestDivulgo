package com.example.demo.Repository;

import com.example.demo.Entity.Stopwords;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Created by Katrina on 1/11/2019.
 */
@Repository
public interface StopwordsRepository extends JpaRepository<Stopwords, Integer> {

    Stopwords findByStopwords(String stopwords);
}