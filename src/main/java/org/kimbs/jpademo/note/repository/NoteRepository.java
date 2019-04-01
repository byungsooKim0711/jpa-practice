package org.kimbs.jpademo.note.repository;

import java.util.List;

import org.kimbs.jpademo.note.domain.Note;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface NoteRepository extends JpaRepository<Note, Long> {
    List<Note> findByUserId(Long userId);

    Note findByIdAndUserId(Long id, Long userId);
}