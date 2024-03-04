package edu.uoc.pacman.model.entities.characters.pacman;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

import java.lang.reflect.Modifier;
import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.*;

class StateTest {

    @Test
    @Tag("basic")
    void getDuration() {
        assertEquals(Integer.MAX_VALUE,State.NORMAL.getDuration());
        assertEquals(30,State.EATER.getDuration());
        assertEquals(5,State.INVINCIBLE.getDuration());
    }

    @Test
    @Tag("basic")
    void testToString() {
        assertEquals("NORMAL:"+Integer.MAX_VALUE,State.NORMAL.toString());
        assertEquals("EATER:30",State.EATER.toString());
        assertEquals("INVINCIBLE:5",State.INVINCIBLE.toString());
    }

    @Test
    @Tag("sanity")
    @DisplayName("Sanity State - Enum definition")
    void checkEnumSanity() {
        final Class<State> ownClass = State.class;

        assertTrue(ownClass.isEnum());
        int modifiers = ownClass.getModifiers();
        assertTrue(Modifier.isPublic(modifiers));
        assertFalse(Modifier.isStatic(modifiers));
        assertTrue(Modifier.isFinal(modifiers));

        assertEquals("edu.uoc.pacman.model.entities.characters.pacman",ownClass.getPackageName());
    }

    @Test
    @Tag("sanity")
    @DisplayName("Sanity State - Fields definition")
    public void checkFieldsSanity() {
        final Class<State> ownClass = State.class;

        //check attribute fields: 1 attributes + 3 values + $VALUES
        assertEquals(5, ownClass.getDeclaredFields().length);

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
    @DisplayName("Sanity State - Methods definition")
    public void checkMethodsSanity() {
        final Class<State> ownClass = State.class;

        //check constructors
        assertEquals(1, ownClass.getDeclaredConstructors().length);

        try {
            //Constructor: the first parameter is the value itself and the second one is the ordinal.
            int constructorModifiers = ownClass.getDeclaredConstructor(String.class, int.class, int.class).getModifiers();
            assertTrue(Modifier.isPrivate(constructorModifiers));
            assertFalse(Modifier.isStatic(constructorModifiers));
            assertFalse(Modifier.isFinal(constructorModifiers));
        } catch (NoSuchMethodException e) {
            fail("[ERROR] State's constructor is defined wrongly");
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