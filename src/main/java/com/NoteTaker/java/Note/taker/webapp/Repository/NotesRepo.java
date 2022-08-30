package com.NoteTaker.java.Note.taker.webapp.Repository;

import com.NoteTaker.java.Note.taker.webapp.Model.Notes;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface NotesRepo extends JpaRepository<Notes,Integer> {

    @Query("from Notes as n where n.user.id=:uid")
    Page<Notes> findNotesByUser(@Param("uid") int uid, Pageable pageable);

    @Query("SELECT n FROM Notes n WHERE CONCAT(n.title) LIKE %?1%")
    List<Notes> findByKeyword(@Param("keyword") String keyword);


}
