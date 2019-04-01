package org.kimbs.jpademo.note.controller;

import java.util.List;
import java.util.Optional;

import org.kimbs.jpademo.note.domain.Note;
import org.kimbs.jpademo.note.service.NoteService;
import org.kimbs.jpademo.user.domain.User;
import org.kimbs.jpademo.user.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriComponentsBuilder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;

@RestController
@RequestMapping(value = "/api")
public class NoteController {

    @Autowired
    private NoteService noteService;

    @Autowired
    private UserService userService;

    @GetMapping(value = "/user/{id}/note")
    public ResponseEntity<List<Note>> list(@PathVariable Long id) {
        final List<Note> allNotes = noteService.findByUserId(id);

        return new ResponseEntity<List<Note>>(allNotes, HttpStatus.OK);
    }

    @GetMapping(value = "/user/{userId}/note/{noteId}")
    public ResponseEntity<Optional<Note>> getNoteById(@PathVariable Long userId, @PathVariable Long noteId) {
        final Optional<Note> note = noteService.findByIdAndUserId(noteId, userId);

        return new ResponseEntity<>(note, HttpStatus.OK);
    }

    @PostMapping(value = "/user/{userId}/note")
    public ResponseEntity<Note> postMethodName(@PathVariable Long userId, @RequestBody final Note note, final UriComponentsBuilder uriBuilder) {
        Optional<User> user = userService.findById(userId);

        if (!user.isPresent()) {
            return ResponseEntity.notFound().build();
        }

        note.setUser(user.get());

        Note created = noteService.create(note);
        final HttpHeaders headers = new HttpHeaders();
        headers.setLocation(uriBuilder.path("/api/user/{userId}/note/{noteId}").buildAndExpand(userId, created.getId()).toUri());

        return new ResponseEntity<Note>(created, headers, HttpStatus.CREATED);
    }

    @DeleteMapping(value = "/user/{userId}/note/{noteId}")
    public ResponseEntity<Void> deleteNoteById(@PathVariable Long userId, @PathVariable Long noteId) {
        Optional<Note> deleted = noteService.findByIdAndUserId(noteId, userId);

        if (deleted == null) {
            return ResponseEntity.notFound().build();
        }

        noteService.deleteNoteById(noteId);
        return ResponseEntity.ok().build();
    }

    @PutMapping(value = "/user/{userId}/note/{noteId}")
    public ResponseEntity<Optional<Note>> updateNoteById(@PathVariable Long userId, @PathVariable Long noteId, @RequestBody final Note note) {
        Optional<User> user = userService.findById(userId);
        if (!user.isPresent()) {
            return ResponseEntity.notFound().build();
        }

        note.setUser(user.get());

        Optional<Note> updated = noteService.findByIdAndUserId(noteId, userId);

        if (updated == null) {
            return ResponseEntity.notFound().build();
        }

        note.setId(updated.get().getId());
        updated = Optional.of(noteService.create(note));
		return new ResponseEntity<>(updated, HttpStatus.OK);
    }
}