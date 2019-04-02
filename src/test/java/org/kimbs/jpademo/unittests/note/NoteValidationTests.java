package org.kimbs.jpademo.unittests.note;

import static org.junit.Assert.assertEquals;

import java.util.Set;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;

import org.junit.BeforeClass;
import org.junit.Test;
import org.kimbs.jpademo.note.domain.Note;

public class NoteValidationTests {

    private static Validator validator;
    
    @BeforeClass
    public static void setUp() {
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        validator = factory.getValidator();
    }

    @Test
    public void 메세지의_길이가_200이_넘었을_때() {
        Note note = new Note();
        note.setId(1234L);
        note.setMessage("aaaaaaaaa.aaaaaaaaa.aaaaaaaaa.aaaaaaaaa." +
                        "aaaaaaaaa.aaaaaaaaa.aaaaaaaaa.aaaaaaaaa." +
                        "aaaaaaaaa.aaaaaaaaa.aaaaaaaaa.aaaaaaaaa." +
                        "aaaaaaaaa.aaaaaaaaa.aaaaaaaaa.aaaaaaaaa." + 
                        "aaaaaaaaa.aaaaaaaaa.aaaaaaaaa.aaaaaaaaa." +
                        "@@");

        Set<ConstraintViolation<Note>> violations = validator.validate(note);

        assertEquals(1, violations.size());

        String message = validator.validateProperty(note, "message").iterator().next().getMessage();
        assertEquals(message, "should be message length less than 200");
    }

    @Test
    public void 메세지가_비었을_때() {
        Note note = new Note();
        note.setId(1234L);
        note.setMessage("");

        Set<ConstraintViolation<Note>> violations = validator.validate(note);

        assertEquals(1, violations.size());

        String message = validator.validateProperty(note, "message").iterator().next().getMessage();
        assertEquals(message, "should be message length greater than 0");
    }
}