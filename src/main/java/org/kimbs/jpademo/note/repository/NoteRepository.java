package org.kimbs.jpademo.note.repository;

import java.util.List;
import java.util.Optional;

import org.kimbs.jpademo.note.domain.Note;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface NoteRepository extends JpaRepository<Note, Long> {
    List<Note> findByUserId(Long userId);

    Optional<Note> findByIdAndUserId(Long userId, Long noteId);
}