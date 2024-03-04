package edu.uoc.pacman.model.entities.characters.ghosts;

import edu.uoc.pacman.model.Level;
import edu.uoc.pacman.model.entities.characters.Character;
import edu.uoc.pacman.model.entities.characters.ghosts.chase.ChaseBehaviour;
import edu.uoc.pacman.model.utils.Direction;
import edu.uoc.pacman.model.utils.Position;
import edu.uoc.pacman.model.utils.Sprite;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

import java.lang.reflect.Modifier;
import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.*;

class GhostTest {

    @Test
    @Tag("sanity")
    @DisplayName("Sanity Ghost - Class definition")
    void checkClassSanity() {
        final Class<Ghost> ownClass = Ghost.class;

        //Class declaration
        int modifiers = ownClass.getModifiers();
        assertTrue(Modifier.isPublic(modifiers));
        assertFalse(Modifier.isStatic(modifiers));
        assertFalse(Modifier.isFinal(modifiers));
        assertTrue(Modifier.isAbstract(modifiers));

        assertEquals("edu.uoc.pacman.model.entities.characters.ghosts", ownClass.getPackageName());
        assertTrue(Character.class.isAssignableFrom(ownClass));
    }


    @Test
    @Tag("sanity")
    @DisplayName("Sanity Ghost - Fields definition")
    public void checkFieldsSanity() {
        final Class<Ghost> ownClass = Ghost.class;

        //check attribute fields
        assertEquals(3, ownClass.getDeclaredFields().length);

        try {
            assertTrue(Modifier.isPrivate(ownClass.getDeclaredField("behaviour").getModifiers()));
            assertEquals(Behaviour.class, ownClass.getDeclaredField("behaviour").getType());
            assertTrue(Modifier.isPrivate(ownClass.getDeclaredField("scatterPosition").getModifiers()));
            assertEquals(Position.class, ownClass.getDeclaredField("scatterPosition").getType());
            assertTrue(Modifier.isProtected(ownClass.getDeclaredField("chaseBehaviour").getModifiers()));
            assertEquals(ChaseBehaviour.class, ownClass.getDeclaredField("chaseBehaviour").getType());
        } catch (NoSuchFieldException e) {
            fail("[ERROR] There is some problem with the definition of the attributes");
            e.printStackTrace();
        }
    }

    @Test
    @Tag("sanity")
    @DisplayName("Sanity Ghost - Methods definition")
    public void checkMethodsSanity() {
        final Class<Ghost> ownClass = Ghost.class;

        //check constructors
        assertEquals(1, ownClass.getDeclaredConstructors().length);

        try {
            //Constructor
            int constructorModifiers = ownClass.getDeclaredConstructor(Position.class, Position.class,
                    Direction.class, Behaviour.class, Sprite.class, Level.class).getModifiers();
            assertTrue(Modifier.isProtected(constructorModifiers));
            assertFalse(Modifier.isStatic(constructorModifiers));
            assertFalse(Modifier.isFinal(constructorModifiers));
        } catch (NoSuchMethodException e) {
            fail("[ERROR] Ghost's constructor is defined wrongly");
        }

        //Max public methods: 9 methods
        assertEquals(9, Arrays.stream(ownClass.getDeclaredMethods()).filter(p -> Modifier.isPublic(p.getModifiers())).count());
        //Max protected methods
        assertEquals(0, Arrays.stream(ownClass.getDeclaredMethods()).filter(p -> Modifier.isProtected(p.getModifiers())).count());
        //Max package-private methods
        assertEquals(0, Arrays.stream(ownClass.getDeclaredMethods()).filter(p -> Modifier.isNative(p.getModifiers())).count());
        //Min private methods: 0
        assertTrue(Arrays.stream(ownClass.getDeclaredMethods()).filter(p -> Modifier.isPrivate(p.getModifiers())).count() >= 3);

        try {
            assertTrue(Modifier.isPublic(ownClass.getDeclaredMethod("getBehaviour").getModifiers()));
            assertEquals(Behaviour.class, ownClass.getDeclaredMethod("getBehaviour").getReturnType());
            assertTrue(Modifier.isPublic(ownClass.getDeclaredMethod("setBehaviour", Behaviour.class).getModifiers()));
            assertEquals(void.class, ownClass.getDeclaredMethod("setBehaviour", Behaviour.class).getReturnType());
            assertTrue(Modifier.isPrivate(ownClass.getDeclaredMethod("nextBehaviour").getModifiers()));
            assertEquals(void.class, ownClass.getDeclaredMethod("nextBehaviour").getReturnType());
            assertTrue(Modifier.isPublic(ownClass.getDeclaredMethod("reset").getModifiers()));
            assertEquals(void.class, ownClass.getDeclaredMethod("reset").getReturnType());
            assertTrue(Modifier.isPublic(ownClass.getDeclaredMethod("equals", Object.class).getModifiers()));
            assertEquals(boolean.class, ownClass.getDeclaredMethod("equals", Object.class).getReturnType());
            assertTrue(Modifier.isPublic(ownClass.getDeclaredMethod("toString").getModifiers()));
            assertEquals(String.class, ownClass.getDeclaredMethod("toString").getReturnType());
            assertTrue(Modifier.isPublic(ownClass.getDeclaredMethod("getScatterPosition").getModifiers()));
            assertEquals(Position.class, ownClass.getDeclaredMethod("getScatterPosition").getReturnType());
            assertTrue(Modifier.isPrivate(ownClass.getDeclaredMethod("setScatterPosition", Position.class).getModifiers()));
            assertEquals(void.class, ownClass.getDeclaredMethod("setScatterPosition", Position.class).getReturnType());
            assertTrue(Modifier.isPrivate(ownClass.getDeclaredMethod("getTargetPosition").getModifiers()));
            assertEquals(Position.class, ownClass.getDeclaredMethod("getTargetPosition").getReturnType());
            assertTrue(Modifier.isPublic(ownClass.getDeclaredMethod("move").getModifiers()));
            assertEquals(void.class, ownClass.getDeclaredMethod("move").getReturnType());
            assertTrue(Modifier.isPublic(ownClass.getDeclaredMethod("hit").getModifiers()));
            assertEquals(boolean.class, ownClass.getDeclaredMethod("hit").getReturnType());
            assertTrue(Modifier.isPublic(ownClass.getDeclaredMethod("kill").getModifiers()));
            assertEquals(void.class, ownClass.getDeclaredMethod("kill").getReturnType());
        } catch (NoSuchMethodException e) {
            fail("[ERROR] There is some problem with the definition of methods");
            e.printStackTrace();
        }
    }

}