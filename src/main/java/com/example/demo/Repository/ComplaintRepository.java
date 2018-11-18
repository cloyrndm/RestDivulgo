package com.example.demo.Repository;

import com.example.demo.Entity.Complaint;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created by Cloie Andrea on 15/07/2018.
 */

@Repository
public interface ComplaintRepository extends JpaRepository<Complaint,Long> {
//    Complaint findByUserId(Long user_id);
public List<Complaint> findByUserId(Long user_id);
}
