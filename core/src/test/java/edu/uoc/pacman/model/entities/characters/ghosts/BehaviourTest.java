package edu.uoc.pacman.model.entities.characters.ghosts;

import edu.uoc.pacman.model.entities.characters.pacman.State;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

import java.lang.reflect.Modifier;
import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.*;

class BehaviourTest {

    @Test
    @Tag("basic")
    void getDuration() {
        assertEquals(30,Behaviour.FRIGHTENED.getDuration());
        assertEquals(20, Behaviour.CHASE.getDuration());
        assertEquals(10,Behaviour.SCATTER.getDuration());
        assertEquals(5,Behaviour.INACTIVE.getDuration());
    }

    @Test
    @Tag("basic")
    void testToString() {
        assertEquals("FRIGHTENED:30",Behaviour.FRIGHTENED.toString());
        assertEquals("CHASE:20", Behaviour.CHASE.toString());
        assertEquals("SCATTER:10",Behaviour.SCATTER.toString());
        assertEquals("INACTIVE:5",Behaviour.INACTIVE.toString());
    }

    @Test
    @Tag("sanity")
    @DisplayName("Sanity Behaviour - Enum definition")
    void checkEnumSanity() {
        final Class<Behaviour> ownClass = Behaviour.class;

        assertTrue(ownClass.isEnum());
        int modifiers = ownClass.getModifiers();
        assertTrue(Modifier.isPublic(modifiers));
        assertFalse(Modifier.isStatic(modifiers));
        assertTrue(Modifier.isFinal(modifiers));

        assertEquals("edu.uoc.pacman.model.entities.characters.ghosts",ownClass.getPackageName());
    }

    @Test
    @Tag("sanity")
    @DisplayName("Sanity Behaviour - Fields definition")
    public void checkFieldsSanity() {
        final Class<Behaviour> ownClass = Behaviour.class;

        //check attribute fields: 1 attributes + 4 values + $VALUES
        assertEquals(6, ownClass.getDeclaredFields().length);

        try {
            assertTrue(Modifier.isPrivate(ownClass.getDeclaredField("duration").getModifiers()));
            assertEquals(int.class, ownClass.getDeclaredField("duration").getType());
        } catch (NoSuchFieldException e) {
            fail("[ERROR] There is some problem with the definition of the attributes");
            e.printStackTrace();
        }
    }

    @Test
    @Tag("sanity")
    @DisplayName("Sanity Behaviour - Methods definition")
    public void checkMethodsSanity() {
        final Class<Behaviour> ownClass = Behaviour.class;

        //check constructors
        assertEquals(1, ownClass.getDeclaredConstructors().length);

        try {
            //Constructor: the first parameter is the value itself and the second one is the ordinal.
            int constructorModifiers = ownClass.getDeclaredConstructor(String.class, int.class, int.class).getModifiers();
            assertTrue(Modifier.isPrivate(constructorModifiers));
            assertFalse(Modifier.isStatic(constructorModifiers));
            assertFalse(Modifier.isFinal(constructorModifiers));
        } catch (NoSuchMethodException e) {
            fail("[ERROR] Behaviour's constructor is defined wrongly");
        }

        //Max public methods: 2 methods + values() + valueOf()
        assertEquals(4, Arrays.stream(ownClass.getDeclaredMethods()).filter(p -> Modifier.isPublic(p.getModifiers())).count());
        //Max protected methods
        assertEquals(0, Arrays.stream(ownClass.getDeclaredMethods()).filter(p -> Modifier.isProtected(p.getModifiers())).count());
        //Max package-private methods
        assertEquals(0, Arrays.stream(ownClass.getDeclaredMethods()).filter(p -> Modifier.isNative(p.getModifiers())).count());
        //Min private methods: $values()
        assertTrue(Arrays.stream(ownClass.getDeclaredMethods()).filter(p -> Modifier.isPrivate(p.getModifiers())).count() >= 1);

        try {
            assertTrue(Modifier.isPublic(ownClass.getDeclaredMethod("getDuration").getModifiers()));
            assertEquals(int.class, ownClass.getDeclaredMethod("getDuration").getReturnType());
            assertTrue(Modifier.isPublic(ownClass.getDeclaredMethod("toString").getModifiers()));
            assertEquals(String.class, ownClass.getDeclaredMethod("toString").getReturnType());
        } catch (NoSuchMethodException e) {
            fail("[ERROR] There is some problem with the definition of methods");
            e.printStackTrace();
        }
    }
}