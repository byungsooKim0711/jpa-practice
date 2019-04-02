package org.kimbs.jpademo.unittests.note;

import static org.mockito.Mockito.when;
import static org.assertj.core.api.Assertions.assertThat;

import java.util.ArrayList;
import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.kimbs.jpademo.note.domain.Note;
import org.kimbs.jpademo.note.repository.NoteRepository;
import org.kimbs.jpademo.note.service.NoteService;
import org.kimbs.jpademo.user.domain.User;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

public class NoteServiceTests {

    @Mock
    NoteRepository noteRepository;

    @InjectMocks
    NoteService noteService;

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void test() {

        User user = new User();
        user.setId(1L);
        user.setEmail("kimbs@naver.com");
        user.setLoginId("kimbs");
        user.setPassword("kimbs");
        user.setPhone("010-0000-0000");

        List<Note> notes = new ArrayList<>();

        Note note1 = new Note();
        note1.setId(1L);
        note1.setMessage("message-001");

        Note note2 = new Note();
        note2.setId(2L);
        note2.setMessage("message-002");

        notes.add(note1);
        notes.add(note2);

        when(noteRepository.findByUserId(user.getId())).thenReturn(notes);

        List<Note> res = noteService.findByUserId(user.getId());

        assertThat(res).hasSize(2).contains(note1, note2);
    }
}