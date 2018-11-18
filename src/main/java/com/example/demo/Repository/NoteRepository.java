package com.example.demo.Repository;

import com.example.demo.Entity.Note;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Created by Cloie Andrea on 15/07/2018.
 */
@Repository
public interface NoteRepository extends JpaRepository<Note,Long> {
}
