package edu.uoc.pacman.model.entities.characters.ghosts.chase;

import edu.uoc.pacman.model.entities.characters.ghosts.Ghost;
import edu.uoc.pacman.model.utils.Position;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

import java.lang.reflect.Modifier;
import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.*;

class ChaseBehaviourTest {

    private final Class<ChaseBehaviour> ownClass = ChaseBehaviour.class;

    @Test
    @Tag("sanity")
    @DisplayName("Sanity ChaseBehaviour - Interface definition")
    void checkInterfaceSanity() {
        assertTrue(ownClass.isInterface());
        int modifiers = ownClass.getModifiers();
        assertTrue(Modifier.isPublic(modifiers));
        assertFalse(Modifier.isStatic(modifiers));
        assertFalse(Modifier.isFinal(modifiers));

        assertEquals("edu.uoc.pacman.model.entities.characters.ghosts.chase", ownClass.getPackageName());
    }

    @Test
    @Tag("sanity")
    @DisplayName("Sanity ChaseBehaviour - Fields definition")
    public void checkFieldsSanity() {

        //check attribute fields: 0 attributes
        assertEquals(0, ownClass.getDeclaredFields().length);
    }

    @Test
    @Tag("sanity")
    @DisplayName("Sanity ChaseBehaviour - Methods definition")
    public void checkMethodsSanity() {
        //Number of methods
        assertEquals(1, ownClass.getDeclaredMethods().length);

        //Max public methods:
        assertEquals(1, Arrays.stream(ownClass.getDeclaredMethods()).filter(p -> Modifier.isPublic(p.getModifiers())).count());

        try {
            assertEquals(Position.class, ownClass.getDeclaredMethod("getChasePosition", Ghost.class).getReturnType());
        } catch (NoSuchMethodException e) {
            fail("[ERROR] There is some problem with the definition of methods");
            e.printStackTrace();
        }
    }
}