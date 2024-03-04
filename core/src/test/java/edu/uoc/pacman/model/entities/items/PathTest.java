package edu.uoc.pacman.model.entities.items;

import edu.uoc.pacman.model.utils.Position;
import edu.uoc.pacman.model.utils.Sprite;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

import java.lang.reflect.Modifier;
import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.*;

class PathTest {
    Path path;

    @BeforeEach
    private void setUp(){
        path = new Path(new Position(4,3));
    }

    @Test
    @Tag("basic")
    void getPosition() {
        assertEquals(new Position(4,3), path.getPosition());
    }

    @Test
    @Tag("basic")
    void setPosition() {
        path.setPosition(new Position(-10,-20));
        assertEquals(new Position(-10,-20), path.getPosition());
    }

    @Test
    @Tag("basic")
    void isPathable() {
        assertTrue(path.isPathable());
    }

    @Test
    @Tag("basic")
    void setPathable() {
        path.setPathable(false);
        assertFalse(path.isPathable());

        path.setPathable(true);
        assertTrue(path.isPathable());
    }

    @Test
    @Tag("basic")
    void getSprite() {
        assertEquals(Sprite.PATH, path.getSprite());
    }

    @Test
    @Tag("sanity")
    @DisplayName("Sanity Path - Class definition")
    void checkClassSanity() {
        final Class<Path> ownClass = Path.class;

        //Class declaration
        int modifiers = ownClass.getModifiers();
        assertTrue(Modifier.isPublic(modifiers));
        assertFalse(Modifier.isStatic(modifiers));
        assertFalse(Modifier.isFinal(modifiers));

        assertEquals("edu.uoc.pacman.model.entities.items",ownClass.getPackageName());
    }


    @Test
    @Tag("sanity")
    @DisplayName("Sanity Path - Fields definition")
    public void checkFieldsSanity() {
        final Class<Path> ownClass = Path.class;

        //check attribute fields
        assertEquals(0, ownClass.getDeclaredFields().length);
        assertTrue(MapItem.class.isAssignableFrom(ownClass));
    }

    @Test
    @Tag("sanity")
    @DisplayName("Sanity Path - Methods definition")
    public void checkMethodsSanity() {
        final Class<Path> ownClass = Path.class;

        //check constructors
        assertEquals(1, ownClass.getDeclaredConstructors().length);

        try {
            //Constructor
            int constructorModifiers = ownClass.getDeclaredConstructor(Position.class).getModifiers();
            assertTrue(Modifier.isPublic(constructorModifiers));
            assertFalse(Modifier.isStatic(constructorModifiers));
            assertFalse(Modifier.isFinal(constructorModifiers));
        } catch (NoSuchMethodException e) {
            fail("[ERROR] Path's constructor is defined wrongly");
        }

        //Max public methods: 0 methods
        assertEquals(0, Arrays.stream(ownClass.getDeclaredMethods()).filter(p -> Modifier.isPublic(p.getModifiers())).count());
        //Max protected methods
        assertEquals(0, Arrays.stream(ownClass.getDeclaredMethods()).filter(p -> Modifier.isProtected(p.getModifiers())).count());
        //Max package-private methods
        assertEquals(0, Arrays.stream(ownClass.getDeclaredMethods()).filter(p -> Modifier.isNative(p.getModifiers())).count());
        //Min private methods: 0
        assertTrue(Arrays.stream(ownClass.getDeclaredMethods()).filter(p -> Modifier.isPrivate(p.getModifiers())).count() >= 0);
    }
}