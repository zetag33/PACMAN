package edu.uoc.pacman.model.entities;

import edu.uoc.pacman.model.utils.Position;
import edu.uoc.pacman.model.utils.Sprite;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

import java.lang.reflect.Modifier;
import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.*;

class EntityTest {

    @Test
    @Tag("sanity")
    @DisplayName("Sanity Entity - Class definition")
    void checkClassSanity() {
        final Class<Entity> ownClass = Entity.class;

        //Class declaration
        int modifiers = ownClass.getModifiers();
        assertTrue(Modifier.isPublic(modifiers));
        assertFalse(Modifier.isStatic(modifiers));
        assertFalse(Modifier.isFinal(modifiers));
        assertTrue(Modifier.isAbstract(modifiers));

        assertEquals("edu.uoc.pacman.model.entities",ownClass.getPackageName());
    }


    @Test
    @Tag("sanity")
    @DisplayName("Sanity Entity - Fields definition")
    public void checkFieldsSanity() {
        final Class<Entity> ownClass = Entity.class;

        //check attribute fields
        assertEquals(3, ownClass.getDeclaredFields().length);

        try {
            assertTrue(Modifier.isPrivate(ownClass.getDeclaredField("position").getModifiers()));
            assertEquals(Position.class, ownClass.getDeclaredField("position").getType());
            assertTrue(Modifier.isPrivate(ownClass.getDeclaredField("pathable").getModifiers()));
            assertEquals(boolean.class, ownClass.getDeclaredField("pathable").getType());
            assertTrue(Modifier.isPrivate(ownClass.getDeclaredField("sprite").getModifiers()));
            assertEquals(Sprite.class, ownClass.getDeclaredField("sprite").getType());
        } catch (NoSuchFieldException e) {
            fail("[ERROR] There is some problem with the definition of the attributes");
            e.printStackTrace();
        }
    }

    @Test
    @Tag("sanity")
    @DisplayName("Sanity Entity - Methods definition")
    public void checkMethodsSanity() {
        final Class<Entity> ownClass = Entity.class;

        //check constructors
        assertEquals(1, ownClass.getDeclaredConstructors().length);

        try {
            //Constructor
            int constructorModifiers = ownClass.getDeclaredConstructor(Position.class, boolean.class, Sprite.class).getModifiers();
            assertTrue(Modifier.isProtected(constructorModifiers));
            assertFalse(Modifier.isStatic(constructorModifiers));
            assertFalse(Modifier.isFinal(constructorModifiers));
        } catch (NoSuchMethodException e) {
            fail("[ERROR] Entity's constructor is defined wrongly");
        }

        //Max public methods: 5 methods
        assertEquals(5, Arrays.stream(ownClass.getDeclaredMethods()).filter(p -> Modifier.isPublic(p.getModifiers())).count());
        //Max protected methods
        assertEquals(1, Arrays.stream(ownClass.getDeclaredMethods()).filter(p -> Modifier.isProtected(p.getModifiers())).count());
        //Max package-private methods
        assertEquals(0, Arrays.stream(ownClass.getDeclaredMethods()).filter(p -> Modifier.isNative(p.getModifiers())).count());
        //Min private methods: 0
        assertTrue(Arrays.stream(ownClass.getDeclaredMethods()).filter(p -> Modifier.isPrivate(p.getModifiers())).count() >= 0);

        try {
            assertTrue(Modifier.isPublic(ownClass.getDeclaredMethod("getPosition").getModifiers()));
            assertEquals(Position.class, ownClass.getDeclaredMethod("getPosition").getReturnType());
            assertTrue(Modifier.isPublic(ownClass.getDeclaredMethod("setPosition", Position.class).getModifiers()));
            assertEquals(void.class, ownClass.getDeclaredMethod("setPosition", Position.class).getReturnType());
            assertTrue(Modifier.isPublic(ownClass.getDeclaredMethod("isPathable").getModifiers()));
            assertEquals(boolean.class, ownClass.getDeclaredMethod("isPathable").getReturnType());
            assertTrue(Modifier.isPublic(ownClass.getDeclaredMethod("setPathable", boolean.class).getModifiers()));
            assertEquals(void.class, ownClass.getDeclaredMethod("setPathable", boolean.class).getReturnType());
            assertTrue(Modifier.isPublic(ownClass.getDeclaredMethod("getSprite").getModifiers()));
            assertEquals(Sprite.class, ownClass.getDeclaredMethod("getSprite").getReturnType());
            assertTrue(Modifier.isProtected(ownClass.getDeclaredMethod("setSprite", Sprite.class).getModifiers()));
            assertEquals(void.class, ownClass.getDeclaredMethod("setSprite", Sprite.class).getReturnType());
        } catch (NoSuchMethodException e) {
            fail("[ERROR] There is some problem with the definition of methods");
            e.printStackTrace();
        }
    }
}