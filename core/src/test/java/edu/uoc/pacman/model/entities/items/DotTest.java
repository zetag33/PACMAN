package edu.uoc.pacman.model.entities.items;

import edu.uoc.pacman.model.entities.Scorable;
import edu.uoc.pacman.model.utils.Position;
import edu.uoc.pacman.model.utils.Sprite;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

import java.lang.reflect.Modifier;
import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.*;

class DotTest {

    Dot dot;

    @BeforeEach
    private void setUp() {
        dot = new Dot(new Position(-4, 6));
    }

    @Test
    @Tag("basic")
    void getPosition() {
        assertEquals(new Position(-4, 6), dot.getPosition());
    }


    @Test
    @Tag("basic")
    void setPosition() {
        dot.setPosition(new Position(-10, 50));
        assertEquals(new Position(-10, 50), dot.getPosition());
    }

    @Test
    @Tag("basic")
    void isPathable() {
        assertTrue(dot.isPathable());
    }

    @Test
    @Tag("basic")
    void setPathable() {
        dot.setPathable(false);
        assertFalse(dot.isPathable());

        dot.setPathable(true);
        assertTrue(dot.isPathable());
    }

    @Test
    @Tag("basic")
    void getSprite() {
        assertEquals(Sprite.DOT, dot.getSprite());
    }



    @Test
    @Tag("basic")
    void isPicked() {
        assertFalse(dot.isPicked());
    }

    @Test
    @Tag("basic")
    void setPicked() {
        dot.setPicked(true);
        assertTrue(dot.isPicked());

        dot.setPicked(false);
        assertFalse(dot.isPicked());
    }

    @Test
    @Tag("basic")
    void getPoints() {
        assertEquals(1, dot.getPoints());
    }

    @Test
    @Tag("sanity")
    @DisplayName("Sanity Dot - Class definition")
    void checkClassSanity() {
        final Class<Dot> ownClass = Dot.class;

        //Class declaration
        int modifiers = ownClass.getModifiers();
        assertTrue(Modifier.isPublic(modifiers));
        assertFalse(Modifier.isStatic(modifiers));
        assertFalse(Modifier.isFinal(modifiers));

        assertEquals("edu.uoc.pacman.model.entities.items", ownClass.getPackageName());
        assertTrue(MapItem.class.isAssignableFrom(ownClass));
        assertTrue(Pickable.class.isAssignableFrom(ownClass));
        assertTrue(Scorable.class.isAssignableFrom(ownClass));
    }


    @Test
    @Tag("sanity")
    @DisplayName("Sanity Dot - Fields definition")
    public void checkFieldsSanity() {
        final Class<Dot> ownClass = Dot.class;

        //check attribute fields
        assertEquals(2, ownClass.getDeclaredFields().length);

        try {
            assertTrue(Modifier.isPrivate(ownClass.getDeclaredField("picked").getModifiers()));
            assertEquals(boolean.class, ownClass.getDeclaredField("picked").getType());

            assertTrue(Modifier.isPrivate(ownClass.getDeclaredField("POINTS").getModifiers()));
            assertTrue(Modifier.isStatic(ownClass.getDeclaredField("POINTS").getModifiers()));
            assertTrue(Modifier.isFinal(ownClass.getDeclaredField("POINTS").getModifiers()));
            assertEquals(int.class, ownClass.getDeclaredField("POINTS").getType());
        } catch (NoSuchFieldException e) {
            fail("[ERROR] There is some problem with the definition of the attributes");
            e.printStackTrace();
        }
    }

    @Test
    @Tag("sanity")
    @DisplayName("Sanity Dot - Methods definition")
    public void checkMethodsSanity() {
        final Class<Dot> ownClass = Dot.class;

        //check constructors
        assertEquals(1, ownClass.getDeclaredConstructors().length);

        try {
            //Constructor
            int constructorModifiers = ownClass.getDeclaredConstructor(Position.class).getModifiers();
            assertTrue(Modifier.isPublic(constructorModifiers));
            assertFalse(Modifier.isStatic(constructorModifiers));
            assertFalse(Modifier.isFinal(constructorModifiers));
        } catch (NoSuchMethodException e) {
            fail("[ERROR] Dot's constructor is defined wrongly");
        }

        //Max public methods: 0 methods
        assertEquals(3, Arrays.stream(ownClass.getDeclaredMethods()).filter(p -> Modifier.isPublic(p.getModifiers())).count());
        //Max protected methods
        assertEquals(0, Arrays.stream(ownClass.getDeclaredMethods()).filter(p -> Modifier.isProtected(p.getModifiers())).count());
        //Max package-private methods
        assertEquals(0, Arrays.stream(ownClass.getDeclaredMethods()).filter(p -> Modifier.isNative(p.getModifiers())).count());
        //Min private methods: 0
        assertTrue(Arrays.stream(ownClass.getDeclaredMethods()).filter(p -> Modifier.isPrivate(p.getModifiers())).count() >= 0);

        try {
            assertTrue(Modifier.isPublic(ownClass.getDeclaredMethod("isPicked").getModifiers()));
            assertEquals(boolean.class, ownClass.getDeclaredMethod("isPicked").getReturnType());
            assertTrue(Modifier.isPublic(ownClass.getDeclaredMethod("setPicked", boolean.class).getModifiers()));
            assertEquals(void.class, ownClass.getDeclaredMethod("setPicked", boolean.class).getReturnType());
            assertTrue(Modifier.isPublic(ownClass.getDeclaredMethod("getPoints").getModifiers()));
            assertEquals(int.class, ownClass.getDeclaredMethod("getPoints").getReturnType());
        } catch (NoSuchMethodException e) {
            fail("[ERROR] There is some problem with the definition of methods");
            e.printStackTrace();
        }
    }
}