package edu.uoc.pacman.model;

import edu.uoc.pacman.model.entities.characters.ghosts.Behaviour;
import edu.uoc.pacman.model.entities.characters.ghosts.Blinky;
import edu.uoc.pacman.model.entities.characters.ghosts.Ghost;
import edu.uoc.pacman.model.entities.characters.pacman.Pacman;
import edu.uoc.pacman.model.entities.items.MapItem;
import edu.uoc.pacman.model.exceptions.LevelException;
import edu.uoc.pacman.model.utils.Position;
import edu.uoc.pacman.model.utils.Sprite;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

import java.io.BufferedReader;
import java.lang.reflect.Modifier;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class LevelTest {

    Level level;

    @BeforeEach
    @DisplayName("Set up of LevelTest")
    private void setUp() {
        try {
            level = new Level("levels/level1.txt", 3);
        } catch (LevelException e) {
            fail();
        }
    }

    @Test
    @Tag("basic")
    void getFileName() {
        assertEquals("levels/level1.txt", level.getFileName());
    }

    @Test
    @Tag("basic")
    void getLives() {
        assertEquals(3,level.getLives());
    }

    @Test
    @Tag("basic")
    void increaseLives() {
        level.increaseLives();
        assertEquals(4,level.getLives());
    }

    @Test
    @Tag("basic")
    void decreaseLives() {
        level.decreaseLives();
        assertEquals(2,level.getLives());
    }

    @Test
    @Tag("basic")
    void getWidth() {
        assertEquals(26,level.getWidth());
    }

    @Test
    @Tag("basic")
    void getHeight() {
        assertEquals(8,level.getHeight());
    }


    @Test
    @Tag("basic")
    void getMapItemXY() {
        assertEquals(Sprite.WALL,level.getMapItem(0,0).getSprite());
        assertEquals(Sprite.PATH,level.getMapItem(1,1).getSprite());
        assertEquals(Sprite.ENERGIZER,level.getMapItem(1,4).getSprite());
        assertEquals(Sprite.DOT,level.getMapItem(2,4).getSprite());
        assertEquals(Sprite.LIFE,level.getMapItem(24,6).getSprite());
    }

    @Test
    @Tag("basic")
    void getMapItemPosition() {
        assertEquals(Sprite.WALL,level.getMapItem(new Position(0,0)).getSprite());
        assertEquals(Sprite.PATH,level.getMapItem(new Position(1,1)).getSprite());
        assertEquals(Sprite.ENERGIZER,level.getMapItem(new Position(1,4)).getSprite());
        assertEquals(Sprite.DOT,level.getMapItem(new Position(2,4)).getSprite());
        assertEquals(Sprite.LIFE,level.getMapItem(new Position(24,6)).getSprite());
    }

    @Test
    @Tag("basic")
    void getGhostList() {
        assertTrue(level.getGhostList() instanceof  List<Ghost>);
        assertEquals(4, level.getGhostList().size());
    }

    @Test
    @Tag("basic")
    void getScore() {
        assertEquals(0, level.getScore());
    }

    @Test
    @Tag("basic")
    void addPoints() {
        level.addPoints(3);
        assertEquals(3,level.getScore());
        level.addPoints(2);
        assertEquals(5,level.getScore());
        level.addPoints(-4);
        assertEquals(5,level.getScore());
        level.addPoints(1);
        assertEquals(6,level.getScore());
    }

    @Test
    @Tag("basic")
    void getPacman() {
        assertTrue(level.getPacman() instanceof Pacman);
        assertEquals(new Position(1,1), level.getPacman().getPosition());
    }

    @Test
    @Tag("basic")
    void getBlinky() {
        assertTrue(level.getBlinky() instanceof Blinky);
        assertEquals(new Position(6,1), level.getBlinky().getPosition());
    }

    @Test
    @Tag("basic")
    void isPathable() {
        assertTrue(level.getMapItem(2,1).isPathable());
        assertTrue(level.getMapItem(6,1).isPathable());
        assertTrue(level.getMapItem(2,4).isPathable());
        assertFalse(level.getMapItem(0,0).isPathable());
        assertFalse(level.getMapItem(3,2).isPathable());

    }

    @Test
    @Tag("basic")
    void setGhostsFrightened() {
        level.setGhostsFrightened();
        assertTrue(level.getGhostList().stream().allMatch(g -> g.getBehaviour() == Behaviour.FRIGHTENED));
    }

    @Test
    @Tag("advanced")
    void hasWon() {
        assertFalse(level.hasWon());

        try {
            level = new Level("levels/level2.txt",5);
            assertFalse(level.hasWon());
            level.getPacman().move();
            assertTrue(level.hasWon());
        } catch (LevelException e) {
            fail(e.getMessage());
        }

    }


    @Test
    @Tag("sanity")
    @DisplayName("Sanity Level - Class definition")
    void checkClassSanity() {
        final Class<Level> ownClass = Level.class;

        //Class declaration
        int modifiers = ownClass.getModifiers();
        assertTrue(Modifier.isPublic(modifiers));
        assertFalse(Modifier.isStatic(modifiers));
        assertFalse(Modifier.isFinal(modifiers));
        assertFalse(Modifier.isAbstract(modifiers));

        assertEquals("edu.uoc.pacman.model",ownClass.getPackageName());
    }

    @Test
    @Tag("sanity")
    @DisplayName("Sanity Level - Fields definition")
    public void checkFieldsSanity() {
        final Class<Level> ownClass = Level.class;

        //check attribute fields
        assertEquals(12, ownClass.getDeclaredFields().length);

        try {
            assertTrue(Modifier.isPrivate(ownClass.getDeclaredField("MIN_WIDTH").getModifiers()));
            assertEquals(int.class, ownClass.getDeclaredField("MIN_WIDTH").getType());
            assertTrue(Modifier.isPrivate(ownClass.getDeclaredField("MIN_HEIGHT").getModifiers()));
            assertEquals(int.class, ownClass.getDeclaredField("MIN_HEIGHT").getType());
            assertTrue(Modifier.isPrivate(ownClass.getDeclaredField("fileName").getModifiers()));
            assertEquals(String.class, ownClass.getDeclaredField("fileName").getType());
            assertTrue(Modifier.isPrivate(ownClass.getDeclaredField("width").getModifiers()));
            assertEquals(int.class, ownClass.getDeclaredField("width").getType());
            assertTrue(Modifier.isPrivate(ownClass.getDeclaredField("height").getModifiers()));
            assertEquals(int.class, ownClass.getDeclaredField("height").getType());
            assertTrue(Modifier.isPrivate(ownClass.getDeclaredField("mapItemList").getModifiers()));
            assertEquals(List.class, ownClass.getDeclaredField("mapItemList").getType());
            assertTrue(Modifier.isPrivate(ownClass.getDeclaredField("pacman").getModifiers()));
            assertEquals(Pacman.class, ownClass.getDeclaredField("pacman").getType());
            assertTrue(Modifier.isPrivate(ownClass.getDeclaredField("ghostList").getModifiers()));
            assertEquals(List.class, ownClass.getDeclaredField("ghostList").getType());
            assertTrue(Modifier.isPrivate(ownClass.getDeclaredField("score").getModifiers()));
            assertEquals(int.class, ownClass.getDeclaredField("score").getType());
            assertTrue(Modifier.isPrivate(ownClass.getDeclaredField("lives").getModifiers()));
            assertEquals(int.class, ownClass.getDeclaredField("lives").getType());
            assertTrue(Modifier.isPrivate(ownClass.getDeclaredField("tick").getModifiers()));
            assertEquals(int.class, ownClass.getDeclaredField("tick").getType());
            assertTrue(Modifier.isPrivate(ownClass.getDeclaredField("UPDATE_GAME").getModifiers()));
            assertTrue(Modifier.isStatic(ownClass.getDeclaredField("UPDATE_GAME").getModifiers()));
            assertTrue(Modifier.isFinal(ownClass.getDeclaredField("UPDATE_GAME").getModifiers()));
            assertEquals(int.class, ownClass.getDeclaredField("UPDATE_GAME").getType());

        } catch (NoSuchFieldException e) {
            fail("[ERROR] There is some problem with the definition of the attributes");
            e.printStackTrace();
        }
    }

    @Test
    @Tag("sanity")
    @DisplayName("Sanity Level - Methods definition")
    public void checkMethodsSanity() {
        final Class<Level> ownClass = Level.class;

        //check constructors
        assertEquals(1, ownClass.getDeclaredConstructors().length);

        try {
            //Constructor
            int constructorModifiers = ownClass.getDeclaredConstructor(String.class, int.class).getModifiers();
            assertTrue(Modifier.isPublic(constructorModifiers));
            assertFalse(Modifier.isStatic(constructorModifiers));
            assertFalse(Modifier.isFinal(constructorModifiers));
        } catch (NoSuchMethodException e) {
            fail("[ERROR] Level's constructor is defined wrongly");
        }

        //Max public methods: 20 methods
        assertEquals(20, Arrays.stream(ownClass.getDeclaredMethods()).filter(p -> Modifier.isPublic(p.getModifiers())).count());
        //Max protected methods
        assertEquals(0, Arrays.stream(ownClass.getDeclaredMethods()).filter(p -> Modifier.isProtected(p.getModifiers())).count());
        //Max package-private methods
        assertEquals(0, Arrays.stream(ownClass.getDeclaredMethods()).filter(p -> Modifier.isNative(p.getModifiers())).count());
        //Min private methods: 0
        assertTrue(Arrays.stream(ownClass.getDeclaredMethods()).filter(p -> Modifier.isPrivate(p.getModifiers())).count() >= 7);

        try {
            assertTrue(Modifier.isPublic(ownClass.getDeclaredMethod("getFileName").getModifiers()));
            assertEquals(String.class, ownClass.getDeclaredMethod("getFileName").getReturnType());
            assertTrue(Modifier.isPrivate(ownClass.getDeclaredMethod("setFileName", String.class).getModifiers()));
            assertEquals(void.class, ownClass.getDeclaredMethod("setFileName", String.class).getReturnType());
            assertTrue(Modifier.isPublic(ownClass.getDeclaredMethod("getLives").getModifiers()));
            assertEquals(int.class, ownClass.getDeclaredMethod("getLives").getReturnType());
            assertTrue(Modifier.isPrivate(ownClass.getDeclaredMethod("setLives", int.class).getModifiers()));
            assertEquals(void.class, ownClass.getDeclaredMethod("setLives", int.class).getReturnType());
            assertTrue(Modifier.isPublic(ownClass.getDeclaredMethod("increaseLives").getModifiers()));
            assertEquals(void.class, ownClass.getDeclaredMethod("increaseLives").getReturnType());
            assertTrue(Modifier.isPublic(ownClass.getDeclaredMethod("decreaseLives").getModifiers()));
            assertEquals(void.class, ownClass.getDeclaredMethod("decreaseLives").getReturnType());
            assertTrue(Modifier.isPrivate(ownClass.getDeclaredMethod("parse").getModifiers()));
            assertEquals(void.class, ownClass.getDeclaredMethod("parse").getReturnType());
            assertTrue(Modifier.isPrivate(ownClass.getDeclaredMethod("getFirstNonEmptyLine", BufferedReader.class).getModifiers()));
            assertEquals(String.class, ownClass.getDeclaredMethod("getFirstNonEmptyLine", BufferedReader.class).getReturnType());
            assertTrue(Modifier.isPublic(ownClass.getDeclaredMethod("getWidth").getModifiers()));
            assertEquals(int.class, ownClass.getDeclaredMethod("getWidth").getReturnType());
            assertTrue(Modifier.isPrivate(ownClass.getDeclaredMethod("setWidth", int.class).getModifiers()));
            assertEquals(void.class, ownClass.getDeclaredMethod("setWidth", int.class).getReturnType());
            assertTrue(Modifier.isPublic(ownClass.getDeclaredMethod("getHeight").getModifiers()));
            assertEquals(int.class, ownClass.getDeclaredMethod("getHeight").getReturnType());
            assertTrue(Modifier.isPrivate(ownClass.getDeclaredMethod("setHeight", int.class).getModifiers()));
            assertEquals(void.class, ownClass.getDeclaredMethod("setHeight", int.class).getReturnType());
            assertTrue(Modifier.isPrivate(ownClass.getDeclaredMethod("getMapItemList").getModifiers()));
            assertEquals(List.class, ownClass.getDeclaredMethod("getMapItemList").getReturnType());
            assertTrue(Modifier.isPublic(ownClass.getDeclaredMethod("getMapItemListIterator").getModifiers()));
            assertEquals(Iterator.class, ownClass.getDeclaredMethod("getMapItemListIterator").getReturnType());
            assertTrue(Modifier.isPublic(ownClass.getDeclaredMethod("getMapItem", int.class, int.class).getModifiers()));
            assertEquals(MapItem.class, ownClass.getDeclaredMethod("getMapItem", int.class, int.class).getReturnType());
            assertTrue(Modifier.isPublic(ownClass.getDeclaredMethod("getMapItem", Position.class).getModifiers()));
            assertEquals(MapItem.class, ownClass.getDeclaredMethod("getMapItem", Position.class).getReturnType());
            assertTrue(Modifier.isPublic(ownClass.getDeclaredMethod("addMapItem", MapItem.class).getModifiers()));
            assertEquals(void.class, ownClass.getDeclaredMethod("addMapItem", MapItem.class).getReturnType());
            assertTrue(Modifier.isPublic(ownClass.getDeclaredMethod("removeMapItem", MapItem.class).getModifiers()));
            assertEquals(void.class, ownClass.getDeclaredMethod("removeMapItem", MapItem.class).getReturnType());
            assertTrue(Modifier.isPublic(ownClass.getDeclaredMethod("getGhostList").getModifiers()));
            assertEquals(List.class, ownClass.getDeclaredMethod("getGhostList").getReturnType());
            assertTrue(Modifier.isPublic(ownClass.getDeclaredMethod("getScore").getModifiers()));
            assertEquals(int.class, ownClass.getDeclaredMethod("getScore").getReturnType());
            assertTrue(Modifier.isPublic(ownClass.getDeclaredMethod("addPoints", int.class).getModifiers()));
            assertEquals(void.class, ownClass.getDeclaredMethod("addPoints", int.class).getReturnType());
            assertTrue(Modifier.isPublic(ownClass.getDeclaredMethod("getPacman").getModifiers()));
            assertEquals(Pacman.class, ownClass.getDeclaredMethod("getPacman").getReturnType());
            assertTrue(Modifier.isPublic(ownClass.getDeclaredMethod("getBlinky").getModifiers()));
            assertEquals(Blinky.class, ownClass.getDeclaredMethod("getBlinky").getReturnType());
            assertTrue(Modifier.isPublic(ownClass.getDeclaredMethod("isPathable", Position.class).getModifiers()));
            assertEquals(boolean.class, ownClass.getDeclaredMethod("isPathable", Position.class).getReturnType());
            assertTrue(Modifier.isPublic(ownClass.getDeclaredMethod("setGhostsFrightened").getModifiers()));
            assertEquals(void.class, ownClass.getDeclaredMethod("setGhostsFrightened").getReturnType());
            assertTrue(Modifier.isPublic(ownClass.getDeclaredMethod("hasWon").getModifiers()));
            assertEquals(boolean.class, ownClass.getDeclaredMethod("hasWon").getReturnType());
            assertTrue(Modifier.isPublic(ownClass.getDeclaredMethod("update").getModifiers()));
            assertEquals(void.class, ownClass.getDeclaredMethod("update").getReturnType());
        } catch (NoSuchMethodException e) {
            fail("[ERROR] There is some problem with the definition of methods");
            e.printStackTrace();
        }
    }

}