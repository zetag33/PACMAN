package edu.uoc.pacman.model.entities.characters;

import edu.uoc.pacman.model.Level;
import edu.uoc.pacman.model.entities.Entity;
import edu.uoc.pacman.model.entities.Scorable;
import edu.uoc.pacman.model.entities.characters.ghosts.Ghost;
import edu.uoc.pacman.model.entities.items.Pickable;
import edu.uoc.pacman.model.utils.Direction;
import edu.uoc.pacman.model.utils.Position;
import edu.uoc.pacman.model.utils.Sprite;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

import java.lang.reflect.Modifier;
import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.*;

class CharacterTest {
    @Test
    @Tag("sanity")
    @DisplayName("Sanity Character - Class definition")
    void checkClassSanity() {
        final Class<Character> ownClass = Character.class;

        //Class declaration
        int modifiers = ownClass.getModifiers();
        assertTrue(Modifier.isPublic(modifiers));
        assertFalse(Modifier.isStatic(modifiers));
        assertFalse(Modifier.isFinal(modifiers));
        assertTrue(Modifier.isAbstract(modifiers));

        assertEquals("edu.uoc.pacman.model.entities.characters",ownClass.getPackageName());
        assertTrue(Entity.class.isAssignableFrom(ownClass));
        assertTrue(Movable.class.isAssignableFrom(ownClass));
        assertTrue(Hitable.class.isAssignableFrom(ownClass));
    }


    @Test
    @Tag("sanity")
    @DisplayName("Sanity Character - Fields definition")
    public void checkFieldsSanity() {
        final Class<Character> ownClass = Character.class;

        //check attribute fields
        assertEquals(5, ownClass.getDeclaredFields().length);

        try {
            assertTrue(Modifier.isPrivate(ownClass.getDeclaredField("startPosition").getModifiers()));
            assertEquals(Position.class, ownClass.getDeclaredField("startPosition").getType());
            assertTrue(Modifier.isPrivate(ownClass.getDeclaredField("dead").getModifiers()));
            assertEquals(boolean.class, ownClass.getDeclaredField("dead").getType());
            assertTrue(Modifier.isPrivate(ownClass.getDeclaredField("duration").getModifiers()));
            assertEquals(int.class, ownClass.getDeclaredField("duration").getType());
            assertTrue(Modifier.isPrivate(ownClass.getDeclaredField("direction").getModifiers()));
            assertEquals(Direction.class, ownClass.getDeclaredField("direction").getType());
            assertTrue(Modifier.isPrivate(ownClass.getDeclaredField("level").getModifiers()));
            assertEquals(Level.class, ownClass.getDeclaredField("level").getType());
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