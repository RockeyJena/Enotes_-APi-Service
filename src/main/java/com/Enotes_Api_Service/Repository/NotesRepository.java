package com.Enotes_Api_Service.Repository;

import com.Enotes_Api_Service.Entity.Notes;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface NotesRepository extends JpaRepository<Notes,Integer> {
    List<Notes> findByIsDeletedFalse();
}
