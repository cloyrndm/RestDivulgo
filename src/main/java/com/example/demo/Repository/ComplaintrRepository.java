package com.example.demo.Repository;

import com.example.demo.Entity.Complaintr;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created by Cloie Andrea on 23/11/2018.
 */
@Repository
public interface ComplaintrRepository extends JpaRepository<Complaintr,Long> {
    public List<Complaintr> findByUserid(Long user_id);
}

