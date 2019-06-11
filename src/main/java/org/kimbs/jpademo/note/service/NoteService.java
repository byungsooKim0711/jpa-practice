package org.kimbs.jpademo.note.service;

import org.kimbs.jpademo.exception.ResourceNotFoundException;
import org.kimbs.jpademo.note.domain.Note;
import org.kimbs.jpademo.note.repository.NoteRepository;
import org.kimbs.jpademo.user.domain.User;
import org.kimbs.jpademo.user.service.UserService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class NoteService {


    private final NoteRepository noteRepository;

    private final UserService userService;

    public NoteService(NoteRepository noteRepository, UserService userService) {
        this.noteRepository = noteRepository;
        this.userService = userService;
    }

    public Note create(Long userId, Note note) {
        User user = userService.findById(userId);
        note.setUser(user);
        return noteRepository.saveAndFlush(note);
    }

    public List<Note> findByUserId(Long userId) {
        return noteRepository.findByUserId(userId);
    }

    public Note findByIdAndUserId(Long userId, Long noteId) {
        User user = userService.findById(userId);
        Note note = noteRepository.findByIdAndUserId(noteId, user.getId()).orElseThrow(() -> new ResourceNotFoundException("Note resource not found with ID: " + noteId));
        note.setUser(user);
        return note;
    }

    public void deleteNoteById(Long userId, Long noteId) {
        Note deleted = findByIdAndUserId(userId, noteId);
        noteRepository.deleteById(deleted.getId());
    }

    public Note updateNoteById(Long userId, Long noteId, Note note) {
        Note updated = findByIdAndUserId(userId, noteId);
        updated.setMessage(note.getMessage());

        return updated;
    }
}