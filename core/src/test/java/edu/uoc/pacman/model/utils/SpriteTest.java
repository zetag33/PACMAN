package edu.uoc.pacman.model.utils;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

import java.lang.reflect.Modifier;
import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.*;

class SpriteTest {

    @Test
    @Tag("basic")
    void getSymbol() {
        assertEquals('B', Sprite.BLINKY.getSymbol());
        assertEquals('P', Sprite.PINKY.getSymbol());
        assertEquals('I', Sprite.INKY.getSymbol());
        assertEquals('C', Sprite.CLYDE.getSymbol());
        assertEquals('.', Sprite.DOT.getSymbol());
        assertEquals('0', Sprite.ENERGIZER.getSymbol());
        assertEquals('#', Sprite.WALL.getSymbol());
        assertEquals(' ', Sprite.PATH.getSymbol());
        assertEquals('L', Sprite.LIFE.getSymbol());
        assertEquals('V', Sprite.PACMAN_DOWN.getSymbol());
        assertEquals('^', Sprite.PACMAN_UP.getSymbol());
        assertEquals('<', Sprite.PACMAN_RIGHT.getSymbol());
        assertEquals('>', Sprite.PACMAN_LEFT.getSymbol());
    }

    @Test
    @Tag("basic")
    void getImageSrc() {
        assertEquals("images/blinky.png", Sprite.BLINKY.getImageSrc());
        assertEquals("images/pinky.png", Sprite.PINKY.getImageSrc());
        assertEquals("images/inky.png", Sprite.INKY.getImageSrc());
        assertEquals("images/clyde.png", Sprite.CLYDE.getImageSrc());
        assertEquals("images/dot.png", Sprite.DOT.getImageSrc());
        assertEquals("images/energizer.png", Sprite.ENERGIZER.getImageSrc());
        assertEquals("images/wall.png", Sprite.WALL.getImageSrc());
        assertEquals("images/path.png", Sprite.PATH.getImageSrc());
        assertEquals("images/life.png", Sprite.LIFE.getImageSrc());
        assertEquals("images/pac_down.png", Sprite.PACMAN_DOWN.getImageSrc());
        assertEquals("images/pac_up.png", Sprite.PACMAN_UP.getImageSrc());
        assertEquals("images/pac_right.png", Sprite.PACMAN_RIGHT.getImageSrc());
        assertEquals("images/pac_left.png", Sprite.PACMAN_LEFT.getImageSrc());
    }

    @Test
    @Tag("sanity")
    @DisplayName("Sanity Sprite - Enum definition")
    void checkEnumSanity() {
        final Class<Sprite> ownClass = Sprite.class;

        assertTrue(ownClass.isEnum());
        int modifiers = ownClass.getModifiers();
        assertTrue(Modifier.isPublic(modifiers));
        assertFalse(Modifier.isStatic(modifiers));
        assertTrue(Modifier.isFinal(modifiers));

        assertEquals("edu.uoc.pacman.model.utils", ownClass.getPackageName());
    }


    @Test
    @Tag("sanity")
    @DisplayName("Sanity Sprite - Fields definition")
    public void checkFieldsSanity() {
        final Class<Sprite> ownClass = Sprite.class;

        //check attribute fields: 2 attributes + 13 values + $VALUES
        assertEquals(16, ownClass.getDeclaredFields().length);

        try {
            assertTrue(Modifier.isPrivate(ownClass.getDeclaredField("symbol").getModifiers()));
            assertEquals(char.class, ownClass.getDeclaredField("symbol").getType());
            assertTrue(Modifier.isPrivate(ownClass.getDeclaredField("imageSrc").getModifiers()));
            assertEquals(String.class, ownClass.getDeclaredField("imageSrc").getType());
        } catch (NoSuchFieldException e) {
            fail("[ERROR] There is some problem with the definition of the attributes");
            e.printStackTrace();
        }
    }

    @Test
    @Tag("sanity")
    @DisplayName("Sanity Sprite - Methods definition")
    public void checkMethodsSanity() {
        final Class<Sprite> ownClass = Sprite.class;

        //check constructors
        assertEquals(1, ownClass.getDeclaredConstructors().length);

        Arrays.stream(ownClass.getDeclaredConstructors()).forEach(i -> System.out.println(i));

        try {
            //Constructor: the first parameter is the value itself and the second one is the ordinal.
            int constructorModifiers = ownClass.getDeclaredConstructor(String.class, int.class, char.class, String.class).getModifiers();
            assertTrue(Modifier.isPrivate(constructorModifiers));
            assertFalse(Modifier.isStatic(constructorModifiers));
            assertFalse(Modifier.isFinal(constructorModifiers));
        } catch (NoSuchMethodException e) {
            fail("[ERROR] Sprite's constructor is defined wrongly");
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
            assertTrue(Modifier.isPublic(ownClass.getDeclaredMethod("getSymbol").getModifiers()));
            assertEquals(char.class, ownClass.getDeclaredMethod("getSymbol").getReturnType());
            assertTrue(Modifier.isPublic(ownClass.getDeclaredMethod("getImageSrc").getModifiers()));
            assertEquals(String.class, ownClass.getDeclaredMethod("getImageSrc").getReturnType());
        } catch (NoSuchMethodException e) {
            fail("[ERROR] There is some problem with the definition of methods");
            e.printStackTrace();
        }
    }
}