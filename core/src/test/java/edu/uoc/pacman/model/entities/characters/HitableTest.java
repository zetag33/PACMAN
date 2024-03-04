package edu.uoc.pacman.model.entities.characters;

import edu.uoc.pacman.model.utils.Direction;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

import java.lang.reflect.Modifier;
import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.*;

class HitableTest {

    private final Class<Hitable> ownClass = Hitable.class;

    @Test
    @Tag("sanity")
    @DisplayName("Sanity Hitable - Interface definition")
    void checkInterfaceSanity() {
        assertTrue(ownClass.isInterface());
        int modifiers = ownClass.getModifiers();
        assertTrue(Modifier.isPublic(modifiers));
        assertFalse(Modifier.isStatic(modifiers));
        assertFalse(Modifier.isFinal(modifiers));

        assertEquals("edu.uoc.pacman.model.entities.characters", ownClass.getPackageName());
    }

    @Test
    @Tag("sanity")
    @DisplayName("Sanity Hitable - Fields definition")
    public void checkFieldsSanity() {

        //check attribute fields: 0 attributes
        assertEquals(0, ownClass.getDeclaredFields().length);
    }

    @Test
    @Tag("sanity")
    @DisplayName("Sanity Hitable - Methods definition")
    public void checkMethodsSanity() {
        //Number of methods
        assertEquals(1, ownClass.getDeclaredMethods().length);

        //Max public methods:
        assertEquals(1, Arrays.stream(ownClass.getDeclaredMethods()).filter(p -> Modifier.isPublic(p.getModifiers())).count());

        try {
            assertEquals(boolean.class, ownClass.getDeclaredMethod("hit").getReturnType());
        } catch (NoSuchMethodException e) {
            fail("[ERROR] There is some problem with the definition of methods");
            e.printStackTrace();
        }

    }
}