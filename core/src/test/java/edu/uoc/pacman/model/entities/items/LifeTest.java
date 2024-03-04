package edu.uoc.pacman.model.entities.items;

import edu.uoc.pacman.model.entities.Scorable;
import edu.uoc.pacman.model.entities.characters.ghosts.Ghost;
import edu.uoc.pacman.model.utils.Position;
import edu.uoc.pacman.model.utils.Sprite;
import org.junit.jupiter.api.*;

import java.lang.reflect.Modifier;
import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.*;

class LifeTest {

    Life life;

    @BeforeEach
    private void setUp() {
        life = new Life(new Position(14, -23));
    }

    @Test
    @Tag("basic")
    void getPosition() {
        assertEquals(new Position(14, -23), life.getPosition());
    }

    @Test
    @Tag("basic")
    void setPosition() {
        life.setPosition(new Position(-10, 20));
        assertEquals(new Position(-10, 20), life.getPosition());
    }

    @Test
    @Tag("basic")
    void isPathable() {
        assertTrue(life.isPathable());
    }

    @Test
    @Tag("basic")
    void setPathable() {
        life.setPathable(false);
        assertFalse(life.isPathable());

        life.setPathable(true);
        assertTrue(life.isPathable());
    }

    @Test
    @Tag("basic")
    void getSprite() {
        assertEquals(Sprite.LIFE, life.getSprite());
    }


    @Test
    @Tag("basic")
    void isPicked() {
        assertFalse(life.isPicked());
    }

    @Test
    @Tag("basic")
    void setPicked() {
        life.setPicked(true);
        assertTrue(life.isPicked());

        life.setPicked(false);
        assertFalse(life.isPicked());
    }

    @Test
    @Tag("sanity")
    @DisplayName("Sanity Life - Class definition")
    void checkClassSanity() {
        final Class<Life> ownClass = Life.class;

        //Class declaration
        int modifiers = ownClass.getModifiers();
        assertTrue(Modifier.isPublic(modifiers));
        assertFalse(Modifier.isStatic(modifiers));
        assertFalse(Modifier.isFinal(modifiers));

        assertEquals("edu.uoc.pacman.model.entities.items", ownClass.getPackageName());
        assertTrue(MapItem.class.isAssignableFrom(ownClass));
        assertTrue(Pickable.class.isAssignableFrom(ownClass));
    }


    @Test
    @Tag("sanity")
    @DisplayName("Sanity Life - Fields definition")
    public void checkFieldsSanity() {
        final Class<Life> ownClass = Life.class;

        //check attribute fields
        assertEquals(1, ownClass.getDeclaredFields().length);

        try {
            assertTrue(Modifier.isPrivate(ownClass.getDeclaredField("picked").getModifiers()));
            assertEquals(boolean.class, ownClass.getDeclaredField("picked").getType());
        } catch (NoSuchFieldException e) {
            fail("[ERROR] There is some problem with the definition of the attributes");
            e.printStackTrace();
        }
    }

    @Test
    @Tag("sanity")
    @DisplayName("Sanity Life - Methods definition")
    public void checkMethodsSanity() {
        final Class<Life> ownClass = Life.class;

        //check constructors
        assertEquals(1, ownClass.getDeclaredConstructors().length);

        try {
            //Constructor
            int constructorModifiers = ownClass.getDeclaredConstructor(Position.class).getModifiers();
            assertTrue(Modifier.isPublic(constructorModifiers));
            assertFalse(Modifier.isStatic(constructorModifiers));
            assertFalse(Modifier.isFinal(constructorModifiers));
        } catch (NoSuchMethodException e) {
            fail("[ERROR] Life's constructor is defined wrongly");
        }

        //Max public methods: 0 methods
        assertEquals(2, Arrays.stream(ownClass.getDeclaredMethods()).filter(p -> Modifier.isPublic(p.getModifiers())).count());
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
        } catch (NoSuchMethodException e) {
            fail("[ERROR] There is some problem with the definition of methods");
            e.printStackTrace();
        }
    }
}