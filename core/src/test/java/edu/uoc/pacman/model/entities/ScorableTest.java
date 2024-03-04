package edu.uoc.pacman.model.entities;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

import java.lang.reflect.Modifier;
import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.*;

class ScorableTest {

    @Test
    @Tag("sanity")
    @DisplayName("Sanity Scorable - Interface definition")
    void checkInterfaceSanity() {
        final Class<Scorable> ownClass = Scorable.class;

        assertTrue(ownClass.isInterface());
        int modifiers = ownClass.getModifiers();
        assertTrue(Modifier.isPublic(modifiers));
        assertFalse(Modifier.isStatic(modifiers));
        assertFalse(Modifier.isFinal(modifiers));

        assertEquals("edu.uoc.pacman.model.entities", ownClass.getPackageName());
    }

    @Test
    @Tag("sanity")
    @DisplayName("Sanity Scorable - Fields definition")
    public void checkFieldsSanity() {
        final Class<Scorable> ownClass = Scorable.class;

        //check attribute fields: 0 attributes
        assertEquals(0, ownClass.getDeclaredFields().length);
    }

    @Test
    @Tag("sanity")
    @DisplayName("Sanity Scorable - Methods definition")
    public void checkMethodsSanity() {
        final Class<Scorable> ownClass = Scorable.class;

        //Number of methods
        assertEquals(1, ownClass.getDeclaredMethods().length);

        //Max public methods:
        assertEquals(1, Arrays.stream(ownClass.getDeclaredMethods()).filter(p -> Modifier.isPublic(p.getModifiers())).count());
    }
}
