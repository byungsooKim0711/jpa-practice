package org.kimbs.jpademo.unittests.note;

import static org.junit.Assert.assertEquals;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.kimbs.jpademo.note.domain.Note;
import org.kimbs.jpademo.note.repository.NoteRepository;
import org.kimbs.jpademo.user.domain.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.context.junit4.SpringRunner;

import static org.assertj.core.api.Assertions.assertThat;

@RunWith(SpringRunner.class)
@DataJpaTest
public class NoteRepositoryTests {

    @Autowired
	private TestEntityManager entityManager;

	@Autowired
    NoteRepository repository;
    
    @Test
	public void 레포지토리에_노트가_비었을_때() {
		Iterable<Note> notes = repository.findAll();
 
		assertThat(notes).isEmpty();
    }
    
    @Test
    public void 노트를_하나_등록했을_때() {
        Note note = new Note();
        note.setId(1L);
        note.setMessage("message");

        User user = new User();
        user.setLoginId("kimbs");
        user.setPassword("kimbs");
        user.setPhone("010-0000-0000");
        user.setEmail("vlakd0711@naver.com");
        note.setUser(user);

        Note saved = repository.save(note);

        assertEquals(saved.getId(), note.getId());
        assertEquals(saved.getMessage(), note.getMessage());
    }
}