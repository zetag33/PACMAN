package edu.uoc.pacman.model.entities.items;

import edu.uoc.pacman.model.entities.Entity;
import edu.uoc.pacman.model.utils.Position;
import edu.uoc.pacman.model.utils.Sprite;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

import java.lang.reflect.Modifier;
import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.*;

class WallTest {

    Wall wall;

    @BeforeEach
    private void setUp(){
        wall = new Wall(new Position(3,4));
    }

    @Test
    @Tag("basic")
    void getPosition() {
        assertEquals(new Position(3,4), wall.getPosition());
    }

    @Test
    @Tag("basic")
    void setPosition() {
        wall.setPosition(new Position(-1,20));
        assertEquals(new Position(-1,20), wall.getPosition());
    }

    @Test
    @Tag("basic")
    void isPathable() {
        assertEquals(false, wall.isPathable());
    }

    @Test
    @Tag("basic")
    void setPathable() {
        wall.setPathable(true);
        assertEquals(true, wall.isPathable());

        wall.setPathable(false);
        assertEquals(false, wall.isPathable());
    }

    @Test
    @Tag("basic")
    void getSprite() {
        assertEquals(Sprite.WALL, wall.getSprite());
    }

    @Test
    @Tag("sanity")
    @DisplayName("Sanity Wall - Class definition")
    void checkClassSanity() {
        final Class<Wall> ownClass = Wall.class;

        //Class declaration
        int modifiers = ownClass.getModifiers();
        assertTrue(Modifier.isPublic(modifiers));
        assertFalse(Modifier.isStatic(modifiers));
        assertFalse(Modifier.isFinal(modifiers));

        assertEquals("edu.uoc.pacman.model.entities.items",ownClass.getPackageName());
        assertTrue(MapItem.class.isAssignableFrom(ownClass));
    }


    @Test
    @Tag("sanity")
    @DisplayName("Sanity Wall - Fields definition")
    public void checkFieldsSanity() {
        final Class<Wall> ownClass = Wall.class;

        //check attribute fields
        assertEquals(0, ownClass.getDeclaredFields().length);
    }

    @Test
    @Tag("sanity")
    @DisplayName("Sanity Wall - Methods definition")
    public void checkMethodsSanity() {
        final Class<Wall> ownClass = Wall.class;

        //check constructors
        assertEquals(1, ownClass.getDeclaredConstructors().length);

        try {
            //Constructor
            int constructorModifiers = ownClass.getDeclaredConstructor(Position.class).getModifiers();
            assertTrue(Modifier.isPublic(constructorModifiers));
            assertFalse(Modifier.isStatic(constructorModifiers));
            assertFalse(Modifier.isFinal(constructorModifiers));
        } catch (NoSuchMethodException e) {
            fail("[ERROR] Wall's constructor is defined wrongly");
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