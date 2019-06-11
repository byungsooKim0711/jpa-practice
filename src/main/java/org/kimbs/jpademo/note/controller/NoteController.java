package org.kimbs.jpademo.note.controller;

import org.kimbs.jpademo.note.domain.Note;
import org.kimbs.jpademo.note.service.NoteService;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.List;

@RestController
@RequestMapping(value = "/api")
public class NoteController {


    private final NoteService noteService;

    public NoteController(NoteService noteService) {
        this.noteService = noteService;
    }

    @GetMapping(value = "/user/{id}/note")
    public List<Note> list(@PathVariable Long id) {
        return noteService.findByUserId(id);
    }

    @GetMapping(value = "/user/{userId}/note/{noteId}")
    public ResponseEntity<Note> getNoteById(@PathVariable Long userId, @PathVariable Long noteId) {
        final Note note = noteService.findByIdAndUserId(userId, noteId);

        return new ResponseEntity<>(note, HttpStatus.OK);
    }

    @PostMapping(value = "/user/{userId}/note")
    public ResponseEntity<Note> createNote(@PathVariable Long userId, @RequestBody final Note note, final UriComponentsBuilder uriBuilder) {
        Note created = noteService.create(userId, note);

        final HttpHeaders headers = new HttpHeaders();
        headers.setLocation(uriBuilder.path("/api/user/{userId}/note/{noteId}").buildAndExpand(userId, created.getId()).toUri());

        return new ResponseEntity<>(created, headers, HttpStatus.CREATED);
    }

    @DeleteMapping(value = "/user/{userId}/note/{noteId}")
    public ResponseEntity<Void> deleteNoteById(@PathVariable Long userId, @PathVariable Long noteId) {
        noteService.deleteNoteById(userId, noteId);

        return ResponseEntity.accepted().build();
    }

    @PutMapping(value = "/user/{userId}/note/{noteId}")
    public ResponseEntity<Note> updateNoteById(@PathVariable Long userId, @PathVariable Long noteId, @RequestBody final Note note) {
        Note updated = noteService.updateNoteById(userId, noteId, note);

		return new ResponseEntity<>(updated, HttpStatus.OK);
    }
}