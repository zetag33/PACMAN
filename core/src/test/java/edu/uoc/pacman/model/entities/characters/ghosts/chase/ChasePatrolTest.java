package edu.uoc.pacman.model.entities.characters.ghosts.chase;

import edu.uoc.pacman.model.entities.characters.ghosts.Ghost;
import edu.uoc.pacman.model.utils.Position;
import edu.uoc.pacman.model.utils.Sprite;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

import java.lang.reflect.Modifier;
import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.*;

class ChasePatrolTest {

    @Test
    @Tag("sanity")
    @DisplayName("Sanity ChasePatrol - Class definition")
    void checkClassSanity() {
        final Class<ChasePatrol> ownClass = ChasePatrol.class;

        //Class declaration
        int modifiers = ownClass.getModifiers();
        assertTrue(Modifier.isPublic(modifiers));
        assertFalse(Modifier.isStatic(modifiers));
        assertFalse(Modifier.isFinal(modifiers));
        assertFalse(Modifier.isAbstract(modifiers));

        assertEquals("edu.uoc.pacman.model.entities.characters.ghosts.chase",ownClass.getPackageName());
        assertTrue(ChaseBehaviour.class.isAssignableFrom(ownClass));
    }

    @Test
    @Tag("sanity")
    @DisplayName("Sanity ChasePatrol - Fields definition")
    public void checkFieldsSanity() {
        final Class<ChasePatrol> ownClass = ChasePatrol.class;

        //check attribute fields
        assertEquals(2, ownClass.getDeclaredFields().length);

        try {
            assertTrue(Modifier.isPrivate(ownClass.getDeclaredField("TILES_OFFSET").getModifiers()));
            assertTrue(Modifier.isStatic(ownClass.getDeclaredField("TILES_OFFSET").getModifiers()));
            assertTrue(Modifier.isFinal(ownClass.getDeclaredField("TILES_OFFSET").getModifiers()));
            assertEquals(int.class, ownClass.getDeclaredField("TILES_OFFSET").getType());

            assertTrue(Modifier.isPrivate(ownClass.getDeclaredField("VECTOR_INCREASE").getModifiers()));
            assertTrue(Modifier.isStatic(ownClass.getDeclaredField("VECTOR_INCREASE").getModifiers()));
            assertTrue(Modifier.isFinal(ownClass.getDeclaredField("VECTOR_INCREASE").getModifiers()));
            assertEquals(int.class, ownClass.getDeclaredField("VECTOR_INCREASE").getType());
        } catch (NoSuchFieldException e) {
            fail("[ERROR] There is some problem with the definition of the attributes");
            e.printStackTrace();
        }
    }

    @Test
    @Tag("sanity")
    @DisplayName("Sanity ChasePatrol - Methods definition")
    public void checkMethodsSanity() {
        final Class<ChasePatrol> ownClass = ChasePatrol.class;

        //check constructors
        assertEquals(1, ownClass.getDeclaredConstructors().length);

        try {
            //Constructor
            int constructorModifiers = ownClass.getDeclaredConstructor().getModifiers();
            assertTrue(Modifier.isPublic(constructorModifiers));
            assertFalse(Modifier.isStatic(constructorModifiers));
            assertFalse(Modifier.isFinal(constructorModifiers));
        } catch (NoSuchMethodException e) {
            fail("[ERROR] ChasePatrol's constructor is defined wrongly");
        }

        //Max public methods: 6 methods
        assertEquals(1, Arrays.stream(ownClass.getDeclaredMethods()).filter(p -> Modifier.isPublic(p.getModifiers())).count());
        //Max protected methods
        assertEquals(0, Arrays.stream(ownClass.getDeclaredMethods()).filter(p -> Modifier.isProtected(p.getModifiers())).count());
        //Max package-private methods
        assertEquals(0, Arrays.stream(ownClass.getDeclaredMethods()).filter(p -> Modifier.isNative(p.getModifiers())).count());
        //Min private methods: 0
        assertEquals(0, Arrays.stream(ownClass.getDeclaredMethods()).filter(p -> Modifier.isPrivate(p.getModifiers())).count());

        try {
            assertTrue(Modifier.isPublic(ownClass.getDeclaredMethod("getChasePosition", Ghost.class).getModifiers()));
            assertEquals(Position.class, ownClass.getDeclaredMethod("getChasePosition", Ghost.class).getReturnType());
        } catch (NoSuchMethodException e) {
            fail("[ERROR] There is some problem with the definition of methods");
            e.printStackTrace();
        }
    }

}