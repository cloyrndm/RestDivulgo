package com.example.demo.Repository;


import com.example.demo.Entity.Idf;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;


/**
 * Created by Cloie Andrea on 15/11/2018.
 */
@Repository
public interface IdfRepository extends JpaRepository<Idf,Long> {
    public List<Idf> findAll();
    public Idf findByFreqIdAndNgramId(int id, int nid);
    public Idf findByFreqId(int id);
}
