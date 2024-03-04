package edu.uoc.pacman.model.utils;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

import java.lang.reflect.Modifier;
import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.*;

class PositionTest {

    Position position;

    @BeforeEach
    @DisplayName("Set up of PositionTest")
    private void setUp() {
        position = new Position(0, 0);
    }

    @Test
    @Tag("basic")
    void getX() {
        assertEquals(0, position.getX());
    }

    @Test
    @Tag("basic")
    void setX() {
        position.setX(5);
        assertEquals(5, position.getX());

        position.setX(-53);
        assertEquals(-53, position.getX());

        position.setX(0);
        assertEquals(0, position.getX());
    }

    @Test
    @Tag("basic")
    void getY() {
        assertEquals(0, position.getY());
    }

    @Test
    @Tag("basic")
    void setY() {
        position.setY(8);
        assertEquals(8, position.getY());

        position.setY(-18);
        assertEquals(-18, position.getY());

        position.setY(0);
        assertEquals(0, position.getY());
    }

    @Test
    @Tag("basic")
    void distance() {
        assertEquals(Math.sqrt(34),position.distance(new Position(5,3)));
        assertEquals(Math.sqrt(37),(new Position(-1,-4)).distance(new Position(0,2)));
        assertEquals(Math.sqrt(13),(new Position(3,5)).distance(new Position(1,2)));
        assertEquals(Math.sqrt(2),(new Position(3,2)).distance(new Position(4,1)));
        assertEquals(Math.sqrt(40),(new Position(0,4)).distance(new Position(6,2)));
    }

    @Test
    @Tag("basic")
    void add() {
        assertEquals(new Position(2,3), Position.add(position, new Position(2,3)));
        assertEquals(new Position(5,6), Position.add(new Position(3,3), new Position(2,3)));
        assertEquals(new Position(-5,6), Position.add(new Position(-5,6), position));
        assertEquals(new Position(-5,6), Position.add(new Position(10,36), new Position(-15, -30)));
        assertEquals(new Position(15,-3), Position.add(new Position(-10,7), new Position(25, -10)));
    }

    @Test
    @Tag("basic")
    @DisplayName("Test of equals when returning true")
    void testEqualsTrue() {
        assertTrue(position.equals(position));

        Position p1 = new Position(0,0);
        assertTrue(position.equals(p1));
    }

    @Test
    @Tag("basic")
    @DisplayName("Test of equals when returning false")
    void testEqualsFalse() {
        Position p1 = new Position(0,1);
        assertFalse(position.equals(p1));

        p1 = new Position(1,0);
        assertFalse(position.equals(p1));

        p1 = new Position(1,-10);
        assertFalse(position.equals(p1));
    }

    @Test
    @Tag("basic")
    @DisplayName("Test of hashCode when returning true")
    void testHashCodeTrue() {
        Position p1 = new Position(0,0);
        assertEquals(position.hashCode(), p1.hashCode());
    }

    @Test
    @Tag("basic")
    @DisplayName("Test of hashCode when returning false")
    void testHashCodeFalse() {
        Position p1 = new Position(0,1);
        assertNotEquals(position.hashCode(), p1.hashCode());
    }

    @Test
    @Tag("sanity")
    @DisplayName("Sanity Position - Class definition")
    void checkClassSanity() {
        final Class<Position> ownClass = Position.class;

        //Class declaration
        int modifiers = ownClass.getModifiers();
        assertTrue(Modifier.isPublic(modifiers));
        assertFalse(Modifier.isStatic(modifiers));
        assertFalse(Modifier.isFinal(modifiers));

        assertEquals("edu.uoc.pacman.model.utils",ownClass.getPackageName());
    }


    @Test
    @Tag("sanity")
    @DisplayName("Sanity Position - Fields definition")
    public void checkFieldsSanity() {
        final Class<Position> ownClass = Position.class;

        //check attribute fields
        assertEquals(2, ownClass.getDeclaredFields().length);

        try {
            assertTrue(Modifier.isPrivate(ownClass.getDeclaredField("x").getModifiers()));
            assertEquals(int.class, ownClass.getDeclaredField("x").getType());
            assertTrue(Modifier.isPrivate(ownClass.getDeclaredField("y").getModifiers()));
            assertEquals(int.class, ownClass.getDeclaredField("y").getType());
        } catch (NoSuchFieldException e) {
            fail("[ERROR] There is some problem with the definition of the attributes");
            e.printStackTrace();
        }
    }

    @Test
    @Tag("sanity")
    @DisplayName("Sanity Position - Methods definition")
    public void checkMethodsSanity() {
        final Class<Position> ownClass = Position.class;

        //check constructors
        assertEquals(1, ownClass.getDeclaredConstructors().length);

        try {
            //Constructor
            int constructorModifiers = ownClass.getDeclaredConstructor(int.class, int.class).getModifiers();
            assertTrue(Modifier.isPublic(constructorModifiers));
            assertFalse(Modifier.isStatic(constructorModifiers));
            assertFalse(Modifier.isFinal(constructorModifiers));
        } catch (NoSuchMethodException e) {
            fail("[ERROR] Position's constructor is defined wrongly");
        }

        //Max public methods: 9 methods
        assertEquals(9, Arrays.stream(ownClass.getDeclaredMethods()).filter(p -> Modifier.isPublic(p.getModifiers())).count());
        //Max protected methods
        assertEquals(0, Arrays.stream(ownClass.getDeclaredMethods()).filter(p -> Modifier.isProtected(p.getModifiers())).count());
        //Max package-private methods
        assertEquals(0, Arrays.stream(ownClass.getDeclaredMethods()).filter(p -> Modifier.isNative(p.getModifiers())).count());
        //Min private methods: 0
        assertTrue(Arrays.stream(ownClass.getDeclaredMethods()).filter(p -> Modifier.isPrivate(p.getModifiers())).count() >= 0);


        try {
            assertTrue(Modifier.isPublic(ownClass.getDeclaredMethod("getX").getModifiers()));
            assertEquals(int.class, ownClass.getDeclaredMethod("getX").getReturnType());
            assertTrue(Modifier.isPublic(ownClass.getDeclaredMethod("setX", int.class).getModifiers()));
            assertEquals(void.class, ownClass.getDeclaredMethod("setX", int.class).getReturnType());
            assertTrue(Modifier.isPublic(ownClass.getDeclaredMethod("getY").getModifiers()));
            assertEquals(int.class, ownClass.getDeclaredMethod("getY").getReturnType());
            assertTrue(Modifier.isPublic(ownClass.getDeclaredMethod("setY", int.class).getModifiers()));
            assertEquals(void.class, ownClass.getDeclaredMethod("setY", int.class).getReturnType());
            assertTrue(Modifier.isPublic(ownClass.getDeclaredMethod("distance", Position.class).getModifiers()));
            assertEquals(double.class, ownClass.getDeclaredMethod("distance", Position.class).getReturnType());
            assertTrue(Modifier.isPublic(ownClass.getDeclaredMethod("add", Position.class, Position.class).getModifiers()));
            assertTrue(Modifier.isStatic(ownClass.getDeclaredMethod("add", Position.class, Position.class).getModifiers()));
            assertEquals(Position.class, ownClass.getDeclaredMethod("add", Position.class, Position.class).getReturnType());
            assertTrue(Modifier.isPublic(ownClass.getDeclaredMethod("equals", Object.class).getModifiers()));
            assertEquals(boolean.class, ownClass.getDeclaredMethod("equals", Object.class).getReturnType());
            assertTrue(Modifier.isPublic(ownClass.getDeclaredMethod("hashCode").getModifiers()));
            assertEquals(int.class, ownClass.getDeclaredMethod("hashCode").getReturnType());
            assertTrue(Modifier.isPublic(ownClass.getDeclaredMethod("toString").getModifiers()));
            assertEquals(String.class, ownClass.getDeclaredMethod("toString").getReturnType());
        } catch (NoSuchMethodException e) {
            fail("[ERROR] There is some problem with the definition of methods");
            e.printStackTrace();
        }
    }
}