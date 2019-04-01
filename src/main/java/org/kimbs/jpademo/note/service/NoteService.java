package org.kimbs.jpademo.note.service;

import java.util.List;
import java.util.Optional;

import org.kimbs.jpademo.note.domain.Note;
import org.kimbs.jpademo.note.repository.NoteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class NoteService {

    @Autowired
    private NoteRepository noteRepository;

    public Note create(Note note) {
        Note created = noteRepository.saveAndFlush(note);
        return created;
    }

    public List<Note> findByUserId(Long id) {
        return noteRepository.findByUserId(id);
    }

    public Optional<Note> findByIdAndUserId(Long id, Long userId) {
        return noteRepository.findByIdAndUserId(id, userId);
    }

    public void deleteNoteById(Long id) {
        noteRepository.deleteById(id);
    }
}