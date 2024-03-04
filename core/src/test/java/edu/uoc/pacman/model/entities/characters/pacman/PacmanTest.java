package edu.uoc.pacman.model.entities.characters.pacman;

import edu.uoc.pacman.model.Level;
import edu.uoc.pacman.model.entities.characters.Character;
import edu.uoc.pacman.model.entities.characters.ghosts.Behaviour;
import edu.uoc.pacman.model.entities.characters.ghosts.Blinky;
import edu.uoc.pacman.model.entities.items.MapItem;
import edu.uoc.pacman.model.entities.items.Path;
import edu.uoc.pacman.model.exceptions.LevelException;
import edu.uoc.pacman.model.utils.Direction;
import edu.uoc.pacman.model.utils.Position;
import edu.uoc.pacman.model.utils.Sprite;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.lang.reflect.Modifier;
import java.util.Arrays;
import java.util.Iterator;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;
import static org.mockito.internal.verification.VerificationModeFactory.times;

@ExtendWith(MockitoExtension.class)
class PacmanTest {

    Pacman pacman;

    @Mock
    Level level;

    @BeforeEach
    void setUp() {
        pacman = new Pacman(new Position(1, 1), Direction.RIGHT, State.NORMAL, level);

    }

    @Test
    @Tag("basic")
    void reset() {
        pacman.reset();
        assertEquals(State.INVINCIBLE, pacman.getState());
        assertFalse(pacman.isDead());
        assertEquals(Direction.UP, pacman.getDirection());
        assertEquals(new Position(1, 1), pacman.getPosition());

    }

    @Test
    @Tag("basic")
    void getDirection() {
        assertEquals(Direction.RIGHT, pacman.getDirection());
    }

    @Test
    @Tag("basic")
    void setDirection() {
        pacman.setDirection(Direction.UP);
        assertEquals(Direction.UP, pacman.getDirection());
        assertEquals(Sprite.PACMAN_UP, pacman.getSprite());

        pacman.setDirection(Direction.DOWN);
        assertEquals(Direction.DOWN, pacman.getDirection());
        assertEquals(Sprite.PACMAN_DOWN, pacman.getSprite());

        pacman.setDirection(Direction.LEFT);
        assertEquals(Direction.LEFT, pacman.getDirection());
        assertEquals(Sprite.PACMAN_LEFT, pacman.getSprite());

        pacman.setDirection(Direction.RIGHT);
        assertEquals(Direction.RIGHT, pacman.getDirection());
        assertEquals(Sprite.PACMAN_RIGHT, pacman.getSprite());
    }

    @Test
    @Tag("basic")
    void isDead() {
        assertFalse(pacman.isDead());
    }

    @Test
    @Tag("basic")
    void kill() {
        pacman.kill();
        assertTrue(pacman.isDead());
        assertEquals(State.INVINCIBLE, pacman.getState());
        verify(level, times(1)).decreaseLives();
    }

    @Test
    @Tag("basic")
    void alive() {
        pacman.alive();
        assertFalse(pacman.isDead());
        pacman.kill();
        assertTrue(pacman.isDead());
        pacman.alive();
        assertFalse(pacman.isDead());

    }

    @Test
    @Tag("basic")
    void getLevel() {
        assertEquals(level, pacman.getLevel());
    }

    @Test
    @Tag("basic")
    void setLevel() {
        pacman.setLevel(null);
        assertNull(pacman.getLevel());
    }

    @Test
    @Tag("basic")
    void testToString() {
        assertEquals("1,1,RIGHT", pacman.toString());
    }


    @Test
    @Tag("basic")
    void getPosition() {
        assertEquals(new Position(1, 1), pacman.getPosition());
    }

    @Test
    @Tag("basic")
    void setPosition() {
        pacman.setPosition(new Position(6, 9));
        assertEquals(new Position(6, 9), pacman.getPosition());
    }

    @Test
    @Tag("basic")
    void isPathable() {
        assertTrue(pacman.isPathable());
    }

    @Test
    @Tag("basic")
    void setPathable() {
        pacman.setPathable(false);
        assertFalse(pacman.isPathable());
    }

    @Test
    @Tag("basic")
    void getSprite() {
        assertEquals(Sprite.PACMAN_RIGHT, pacman.getSprite());
    }

    @Test
    @Tag("basic")
    void getState() {
        assertEquals(State.NORMAL, pacman.getState());
    }

    @Test
    @Tag("basic")
    void setState() {
        pacman.setState(State.EATER);
        assertEquals(State.EATER, pacman.getState());

        pacman.setState(State.INVINCIBLE);
        assertEquals(State.INVINCIBLE, pacman.getState());

        pacman.setState(State.NORMAL);
        assertEquals(State.NORMAL, pacman.getState());
    }

    @Test
    @Tag("expert")
    void moveToDot() {
        try {
            Level level = new Level("levels/level1.txt", 3);
            MapItem dot = level.getMapItem(2,1);
            Pacman pacman = spy(level.getPacman());
            pacman.move();
            assertEquals(new Position(2, 1), pacman.getPosition());
            verify(pacman, times(1)).hit();
            assertEquals(1, level.getScore());

            Iterator<MapItem> itr = level.getMapItemListIterator();

            boolean path = false;
            while(itr.hasNext()){
                MapItem item = itr.next();
                if(item.equals(dot)) fail("The dot has not been removed");
                if(item instanceof Path && item.getPosition().equals(new Position(2,1))){
                    path = true;
                }
            }

            assertTrue(path, "You have not replaced dot by path");


        } catch (LevelException e) {
            throw new RuntimeException(e);
        }
    }

    @Test
    @Tag("expert")
    void moveToEnergizer() {
        try {
            Level level = new Level("levels/level1.txt", 3);
            MapItem energizer = level.getMapItem(1,4);
            Pacman pacman = spy(level.getPacman());
            pacman.setPosition(new Position(2,4));
            pacman.setDirection(Direction.LEFT);
            pacman.move();
            assertEquals(new Position(1, 4), pacman.getPosition());
            verify(pacman, times(1)).hit();
            assertEquals(5, level.getScore());

            assertEquals(State.EATER, pacman.getState());
            //All ghosts are frightened
            assertTrue(level.getGhostList().stream().allMatch(g -> g.getBehaviour() == Behaviour.FRIGHTENED));

            Iterator<MapItem> itr = level.getMapItemListIterator();
            boolean path = false;
            while(itr.hasNext()){
                MapItem item = itr.next();
                if(item.equals(energizer)) fail("The energizer has not been removed");
                if(item instanceof Path && item.getPosition().equals(new Position(1,4))){
                    path = true;
                }
            }

            assertTrue(path, "You have not replaced energizer by path");
        } catch (LevelException e) {
            throw new RuntimeException(e);
        }
    }

    @Test
    @Tag("expert")
    void moveToLife() {
        try {
            Level level = new Level("levels/level1.txt", 3);

            MapItem life = level.getMapItem(24,6);
            Pacman pacman = spy(level.getPacman());
            pacman.setPosition(new Position(23,6)); //RIGHT
            pacman.move();
            assertEquals(new Position(24, 6), pacman.getPosition());
            verify(pacman, times(1)).hit();
            assertEquals(0, level.getScore());
            assertEquals(4, level.getLives());

            Iterator<MapItem> itr = level.getMapItemListIterator();

            boolean path = false;
            while(itr.hasNext()){
                MapItem item = itr.next();
                if(item.equals(life)) fail("The life has not been removed");
                if(item instanceof Path && item.getPosition().equals(new Position(24,6))){
                    path = true;
                }
            }

            assertTrue(path, "You have not replaced life by path");


        } catch (LevelException e) {
            throw new RuntimeException(e);
        }
    }


    @Test
    @Tag("expert")
    @DisplayName("Ghost is CHASE, then ghost kills Pacman (NORMAL).")
    void hitGhostKillsPacman1() {
        try {
            Level level = new Level("levels/level1.txt", 3);
            level.getPacman().setPosition(new Position(6, 1)); //Blinky's position
            Pacman pacman = spy(level.getPacman());
            assertTrue(pacman.hit());
            verify(pacman, times(1)).kill();
        } catch (LevelException e) {
            throw new RuntimeException(e);
        }
    }

    @Test
    @Tag("expert")
    @DisplayName("Ghost is SCATTER, then ghost kills Pacman (NORMAL)")
    void hitGhostKillsPacman2() {
        try {
            Level level = new Level("levels/level1.txt", 3);
            level.getBlinky().setBehaviour(Behaviour.SCATTER);
            level.getPacman().setPosition(new Position(6, 1)); //Blinky's position
            Pacman pacman = spy(level.getPacman());
            assertTrue(pacman.hit());
            verify(pacman, times(1)).kill();
        } catch (LevelException e) {
            throw new RuntimeException(e);
        }
    }

    @Test
    @Tag("expert")
    @DisplayName("Ghost is SCATTER and Pacman is INVINCIBLE, then ghost does not kills Pacman")
    void hitGhostNotKillPacman1() {
        try {
            Level level = new Level("levels/level1.txt", 3);
            level.getBlinky().setBehaviour(Behaviour.SCATTER);
            level.getPacman().setState(State.INVINCIBLE);
            level.getPacman().setPosition(new Position(6, 1)); //Blinky's position
            Pacman pacman = spy(level.getPacman());
            assertFalse(pacman.hit());
            verify(pacman, never()).kill();
        } catch (LevelException e) {
            throw new RuntimeException(e);
        }
    }

    @Test
    @Tag("expert")
    @DisplayName("Ghost is CHASE and Pacman is INVINCIBLE, then ghost does not kills Pacman")
    void hitGhostNotKillPacman2() {
        try {
            Level level = new Level("levels/level1.txt", 3);
            level.getPacman().setState(State.INVINCIBLE);
            level.getPacman().setPosition(new Position(6, 1)); //Blinky's position
            Pacman pacman = spy(level.getPacman());
            assertFalse(pacman.hit());
            verify(pacman, never()).kill();
        } catch (LevelException e) {
            throw new RuntimeException(e);
        }
    }


    @Test
    @Tag("expert")
    @DisplayName("Ghost is INACTIVE, then it does not kill Pacman (NORMAL) and it is not killed")
    void hitGhostInactive() {
        try {
            Level level = new Level("levels/level1.txt", 3);
            level.getBlinky().setBehaviour(Behaviour.INACTIVE);
            level.getPacman().setPosition(new Position(6, 1)); //Blinky's position
            Pacman pacman = spy(level.getPacman());
            Blinky blinky = spy(level.getBlinky());
            assertFalse(pacman.hit());
            verify(pacman, never()).kill();
            verify(blinky, never()).kill();

            pacman.setState(State.EATER);
            assertFalse(pacman.hit());
            verify(pacman, never()).kill();
            verify(blinky, never()).kill();
        } catch (LevelException e) {
            throw new RuntimeException(e);
        }
    }

    @Test
    @Tag("expert")
    @DisplayName("Ghost is FRIGHTENED, then Pacman (by logical it is EATER) kills ghost")
    void hitPacmanKillsGhost() {
        try {
            Level level = new Level("levels/level1.txt", 3);
            level.getBlinky().setBehaviour(Behaviour.FRIGHTENED);
            level.getPacman().setPosition(new Position(6, 1)); //Blinky's position
            Pacman pacman = spy(level.getPacman());
            Blinky blinky = level.getBlinky();
            pacman.hit();
            verify(pacman, never()).kill();
            assertEquals(400, level.getScore());
            assertTrue(blinky.isDead());
            assertEquals(Behaviour.INACTIVE, blinky.getBehaviour());
        } catch (LevelException e) {
            throw new RuntimeException(e);
        }
    }

    @Test
    @Tag("sanity")
    @DisplayName("Sanity Pacman - Class definition")
    void checkClassSanity() {
        final Class<Pacman> ownClass = Pacman.class;

        //Class declaration
        int modifiers = ownClass.getModifiers();
        assertTrue(Modifier.isPublic(modifiers));
        assertFalse(Modifier.isStatic(modifiers));
        assertFalse(Modifier.isFinal(modifiers));

        assertEquals("edu.uoc.pacman.model.entities.characters.pacman", ownClass.getPackageName());
        assertTrue(Character.class.isAssignableFrom(ownClass));
    }


    @Test
    @Tag("sanity")
    @DisplayName("Sanity Pacman - Fields definition")
    public void checkFieldsSanity() {
        final Class<Pacman> ownClass = Pacman.class;

        //check attribute fields
        assertEquals(1, ownClass.getDeclaredFields().length);

        try {
            assertTrue(Modifier.isPrivate(ownClass.getDeclaredField("state").getModifiers()));
            assertFalse(Modifier.isStatic(ownClass.getDeclaredField("state").getModifiers()));
            assertFalse(Modifier.isFinal(ownClass.getDeclaredField("state").getModifiers()));
            assertEquals(State.class, ownClass.getDeclaredField("state").getType());
        } catch (NoSuchFieldException e) {
            fail("[ERROR] There is some problem with the definition of the attributes");
            e.printStackTrace();
        }
    }

    @Test
    @Tag("sanity")
    @DisplayName("Sanity Pacman - Methods definition")
    public void checkMethodsSanity() {
        final Class<Pacman> ownClass = Pacman.class;

        //check constructors
        assertEquals(1, ownClass.getDeclaredConstructors().length);

        try {
            //Constructor
            int constructorModifiers = ownClass.getDeclaredConstructor(Position.class, Direction.class,
                    State.class, Level.class).getModifiers();
            assertTrue(Modifier.isPublic(constructorModifiers));
            assertFalse(Modifier.isStatic(constructorModifiers));
            assertFalse(Modifier.isFinal(constructorModifiers));
        } catch (NoSuchMethodException e) {
            fail("[ERROR] Pacman's constructor is defined wrongly");
        }

        //Max public methods: 7 methods
        assertEquals(7, Arrays.stream(ownClass.getDeclaredMethods()).filter(p -> Modifier.isPublic(p.getModifiers())).count());
        //Max protected methods
        assertEquals(0, Arrays.stream(ownClass.getDeclaredMethods()).filter(p -> Modifier.isProtected(p.getModifiers())).count());
        //Max package-private methods
        assertEquals(0, Arrays.stream(ownClass.getDeclaredMethods()).filter(p -> Modifier.isNative(p.getModifiers())).count());
        //Min private methods: 2 methods
        assertTrue(Arrays.stream(ownClass.getDeclaredMethods()).filter(p -> Modifier.isPublic(p.getModifiers())).count() >= 2);

        try {
            assertTrue(Modifier.isPublic(ownClass.getDeclaredMethod("getState").getModifiers()));
            assertEquals(State.class, ownClass.getDeclaredMethod("getState").getReturnType());
            assertTrue(Modifier.isPublic(ownClass.getDeclaredMethod("setState", State.class).getModifiers()));
            assertEquals(void.class, ownClass.getDeclaredMethod("setState", State.class).getReturnType());
            assertTrue(Modifier.isPrivate(ownClass.getDeclaredMethod("nextState").getModifiers()));
            assertEquals(void.class, ownClass.getDeclaredMethod("nextState").getReturnType());
            assertTrue(Modifier.isPublic(ownClass.getDeclaredMethod("reset").getModifiers()));
            assertEquals(void.class, ownClass.getDeclaredMethod("reset").getReturnType());
            assertTrue(Modifier.isPublic(ownClass.getDeclaredMethod("move").getModifiers()));
            assertEquals(void.class, ownClass.getDeclaredMethod("move").getReturnType());
            assertTrue(Modifier.isPublic(ownClass.getDeclaredMethod("setDirection", Direction.class).getModifiers()));
            assertEquals(void.class, ownClass.getDeclaredMethod("setDirection", Direction.class).getReturnType());
            assertTrue(Modifier.isPrivate(ownClass.getDeclaredMethod("eat").getModifiers()));
            assertEquals(void.class, ownClass.getDeclaredMethod("eat").getReturnType());
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