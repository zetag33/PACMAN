package edu.uoc.pacman.model.entities.characters.ghosts;

import edu.uoc.pacman.model.Level;
import edu.uoc.pacman.model.entities.characters.ghosts.chase.ChaseAggressive;
import edu.uoc.pacman.model.entities.characters.pacman.Pacman;
import edu.uoc.pacman.model.entities.characters.pacman.State;
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

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class BlinkyTest {
    Blinky blinky;


    @Mock
    Level level;

    @Mock
    Pacman pacman;

    @BeforeEach
    void setUp() {
        when(level.getWidth()).thenReturn(8);
        blinky = new Blinky(new Position(2, 3), Direction.DOWN, Behaviour.CHASE, level);
    }

    @Test
    @Tag("basic")
    void correctInstanceChaseBehaviour(){
        assertTrue(blinky.chaseBehaviour instanceof ChaseAggressive);
    }

    @Test
    @Tag("basic")
    void getBehaviour() {
        assertEquals(Behaviour.CHASE, blinky.getBehaviour());
    }

    @Test
    @Tag("basic")
    void setBehaviour() {
        blinky.setBehaviour(Behaviour.FRIGHTENED);
        assertEquals(Behaviour.FRIGHTENED, blinky.getBehaviour());
    }

    @Test
    @Tag("basic")
    void reset() {
        blinky.reset();
        assertEquals(Behaviour.INACTIVE, blinky.getBehaviour());
        assertFalse(blinky.isDead());
        assertEquals(Direction.UP, blinky.getDirection());
        assertEquals(new Position(2, 3), blinky.getPosition());
    }

    @Test
    @Tag("basic")
    void testEqualsOK() {
        assertTrue(blinky.equals(blinky));
        assertTrue(blinky.equals(new Blinky(new Position(2, 3), Direction.DOWN, Behaviour.CHASE, level)));
    }

    @Test
    @Tag("basic")
    void testEqualsKO() {
        assertFalse(blinky.equals(new Blinky(new Position(3, 3), Direction.DOWN, Behaviour.CHASE, level)));
        assertFalse(blinky.equals(new Blinky(new Position(2, 3), Direction.UP, Behaviour.CHASE, level)));
        assertFalse(blinky.equals(new Blinky(new Position(2, 3), Direction.DOWN, Behaviour.FRIGHTENED, level)));
        blinky.kill();
        assertFalse(blinky.equals(new Blinky(new Position(2, 3), Direction.DOWN, Behaviour.CHASE, level)));
    }

    @Test
    @Tag("basic")
    void testToString() {
        assertEquals("2,3,DOWN,CHASE:20", blinky.toString());
    }

    @Test
    @Tag("basic")
    void getScatterPosition() {
        assertEquals(new Position(8, -1), blinky.getScatterPosition());
    }

    @Test
    @Tag("expert")
    void move() {
        try {
            Level level = new Level("levels/level1.txt",3);
            Blinky blinky = spy(level.getBlinky());
            assertEquals(new Position(6,1), blinky.getPosition());
            blinky.move();
            assertEquals(new Position(5,1), blinky.getPosition());
            assertEquals(Direction.LEFT, blinky.getDirection());
            verify(blinky, times(1)).hit();

            level.getPacman().setPosition(new Position(5,2));
            blinky.move();
            assertEquals(new Position(5,2), blinky.getPosition());
            assertEquals(Direction.DOWN, blinky.getDirection());

            level.getPacman().setPosition(new Position(10,6));
            blinky.move();
            assertEquals(new Position(6,2), blinky.getPosition());
            assertEquals(Direction.RIGHT, blinky.getDirection());

            level.getPacman().setPosition(new Position(2,2));
            blinky.move();
            assertEquals(new Position(6,1), blinky.getPosition());
            assertEquals(Direction.UP, blinky.getDirection());

            //There is a wall between Pacman and Blinky
            level.getPacman().setPosition(new Position(4,4));
            blinky.setPosition(new Position(4,6));
            blinky.move();
            assertEquals(new Position(3,6), blinky.getPosition());
            assertEquals(Direction.LEFT, blinky.getDirection());

            blinky = spy(new Blinky(new Position(3,6),Direction.LEFT, Behaviour.INACTIVE, level));
            blinky.move();
            assertEquals(new Position(3,6), blinky.getPosition());
            assertEquals(Direction.LEFT, blinky.getDirection());
            verify(blinky, never()).hit();
        } catch (LevelException e) {
            System.out.println("[ERROR] Loading level while testing move()");
        }
    }

    @Test
    @Tag("advanced")
    @DisplayName("Ghost is CHASE, then ghost kills Pacman (NORMAL).")
    void hitGhostKillsPacman1() {
        when(level.getPacman()).thenReturn(pacman);
        when(pacman.getPosition()).thenReturn(new Position(2, 3));
        when(pacman.getState()).thenReturn(State.NORMAL);
        assertTrue(blinky.hit());
        verify(pacman, times(1)).kill();
    }

    @Test
    @Tag("advanced")
    @DisplayName("Ghost is SCATTER, then ghost kills Pacman (NORMAL)")
    void hitGhostKillsPacman2() {
        Blinky blinky = spy(new Blinky(new Position(2, 3), Direction.DOWN, Behaviour.SCATTER, level));
        when(level.getPacman()).thenReturn(pacman);
        when(pacman.getState()).thenReturn(State.NORMAL);
        when(pacman.getPosition()).thenReturn(new Position(2, 3));
        assertTrue(blinky.hit());
        verify(pacman, times(1)).kill();
    }

    @Test
    @Tag("advanced")
    @DisplayName("Ghost is SCATTER and Pacman is INVINCIBLE, then ghost does not kills Pacman")
    void hitGhostNotKillPacman1() {
        Blinky blinky = spy(new Blinky(new Position(2, 3), Direction.DOWN, Behaviour.SCATTER, level));
        when(level.getPacman()).thenReturn(pacman);
        when(pacman.getState()).thenReturn(State.INVINCIBLE);
        when(pacman.getPosition()).thenReturn(new Position(2, 3));
        assertTrue(blinky.hit());
        verify(pacman, never()).kill();
    }

    @Test
    @Tag("advanced")
    @DisplayName("Ghost is CHASE and Pacman is INVINCIBLE, then ghost does not kills Pacman")
    void hitGhostNotKillPacman2() {
        Blinky blinky = spy(new Blinky(new Position(2, 3), Direction.DOWN, Behaviour.CHASE, level));
        when(level.getPacman()).thenReturn(pacman);
        when(pacman.getState()).thenReturn(State.INVINCIBLE);
        when(pacman.getPosition()).thenReturn(new Position(2, 3));
        assertTrue(blinky.hit());
        verify(pacman, never()).kill();
    }


    @Test
    @Tag("advanced")
    @DisplayName("Ghost is INACTIVE, then it does not kill Pacman")
    void hitGhostInactive() {
        Blinky blinky = spy(new Blinky(new Position(2, 3), Direction.DOWN, Behaviour.INACTIVE, level));
        assertFalse(blinky.hit());
        verify(blinky,never()).kill();
        verify(pacman, never()).kill();
    }

    @Test
    @Tag("advanced")
    @DisplayName("Ghost is FRIGHTENED, then Pacman (by logical it is EATER) kills ghost")
    void hitPacmanKillsGhost() {
        Blinky blinky = spy(new Blinky(new Position(2, 3), Direction.DOWN, Behaviour.FRIGHTENED, level));
        when(level.getPacman()).thenReturn(pacman);
        when(pacman.getPosition()).thenReturn(new Position(2, 3));
        assertTrue(blinky.hit());
        verify(blinky, times(1)).kill();
    }


    @Test
    @Tag("basic")
    void kill() {
        blinky.kill();
        assertTrue(blinky.isDead());
        assertEquals(Behaviour.INACTIVE, blinky.getBehaviour());
        verify(level, times(1)).addPoints(blinky.getPoints());
    }

    @Test
    @Tag("basic")
    void testReset() {
        //Given the setUp state
        //When
        blinky.reset();
        //Then
        assertFalse(blinky.isDead());
        assertEquals(new Position(2, 3), blinky.getPosition());
        assertEquals(Behaviour.INACTIVE, blinky.getBehaviour());
        assertEquals(Direction.UP, blinky.getDirection());

        //Given
        blinky.setPosition(new Position(4, 5));
        blinky.setBehaviour(Behaviour.FRIGHTENED);
        blinky.setDirection(Direction.LEFT);
        //When
        blinky.reset();
        //Then
        assertFalse(blinky.isDead());
        assertEquals(new Position(2, 3), blinky.getPosition());
        assertEquals(Behaviour.INACTIVE, blinky.getBehaviour());
        assertEquals(Direction.UP, blinky.getDirection());
    }

    @Test
    @Tag("basic")
    void getDirection() {
        assertEquals(Direction.DOWN, blinky.getDirection());
    }

    @Test
    @Tag("basic")
    void setDirection() {
        blinky.setDirection(Direction.LEFT);
        assertEquals(Direction.LEFT, blinky.getDirection());

        blinky.setDirection(Direction.RIGHT);
        assertEquals(Direction.RIGHT, blinky.getDirection());

        blinky.setDirection(Direction.UP);
        assertEquals(Direction.UP, blinky.getDirection());
    }

    @Test
    @Tag("basic")
    void isDead() {
        assertFalse(blinky.isDead());
    }

    @Test
    @Tag("basic")
    void alive() {
        blinky.alive();
        assertFalse(blinky.isDead());
        blinky.kill();
        assertTrue(blinky.isDead());
        blinky.alive();
        assertFalse(blinky.isDead());
    }

    @Test
    @Tag("basic")
    void getLevel() {
        assertEquals(level, blinky.getLevel());
    }

    @Test
    @Tag("basic")
    void setLevel() {
        blinky.setLevel(null);
        assertNull(blinky.getLevel());
    }

    @Test
    @Tag("basic")
    void getPosition() {
        assertEquals(new Position(2, 3), blinky.getPosition());
    }

    @Test
    @Tag("basic")
    void setPosition() {
        blinky.setPosition(new Position(-1, 50));
        assertEquals(new Position(-1, 50), blinky.getPosition());

        blinky.setPosition(new Position(2, 3));
        assertEquals(new Position(2, 3), blinky.getPosition());
    }

    @Test
    @Tag("basic")
    void isPathable() {
        assertTrue(blinky.isPathable());
    }

    @Test
    @Tag("basic")
    void setPathable() {
        blinky.setPathable(false);
        assertFalse(blinky.isPathable());

        blinky.setPathable(true);
        assertTrue(blinky.isPathable());
    }

    @Test
    @Tag("basic")
    void getSprite() {
        assertEquals(Sprite.BLINKY, blinky.getSprite());
    }

    @Test
    @Tag("basic")
    void getPoints() {
        assertEquals(400, blinky.getPoints());
    }

    @Test
    @Tag("sanity")
    @DisplayName("Sanity Blinky - Class definition")
    void checkClassSanity() {
        final Class<Blinky> ownClass = Blinky.class;

        //Class declaration
        int modifiers = ownClass.getModifiers();
        assertTrue(Modifier.isPublic(modifiers));
        assertFalse(Modifier.isStatic(modifiers));
        assertFalse(Modifier.isFinal(modifiers));

        assertEquals("edu.uoc.pacman.model.entities.characters.ghosts", ownClass.getPackageName());
        assertTrue(Ghost.class.isAssignableFrom(ownClass));
    }


    @Test
    @Tag("sanity")
    @DisplayName("Sanity Blinky - Fields definition")
    public void checkFieldsSanity() {
        final Class<Blinky> ownClass = Blinky.class;

        //check attribute fields
        assertEquals(1, ownClass.getDeclaredFields().length);

        try {
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
    @DisplayName("Sanity Blinky - Methods definition")
    public void checkMethodsSanity() {
        final Class<Blinky> ownClass = Blinky.class;

        //check constructors
        assertEquals(1, ownClass.getDeclaredConstructors().length);

        try {
            //Constructor
            int constructorModifiers = ownClass.getDeclaredConstructor(Position.class, Direction.class,
                    Behaviour.class, Level.class).getModifiers();
            assertTrue(Modifier.isPublic(constructorModifiers));
            assertFalse(Modifier.isStatic(constructorModifiers));
            assertFalse(Modifier.isFinal(constructorModifiers));
        } catch (NoSuchMethodException e) {
            fail("[ERROR] Blinky's constructor is defined wrongly");
        }

        //Max public methods: 0 methods
        assertEquals(1, Arrays.stream(ownClass.getDeclaredMethods()).filter(p -> Modifier.isPublic(p.getModifiers())).count());
        //Max protected methods
        assertEquals(0, Arrays.stream(ownClass.getDeclaredMethods()).filter(p -> Modifier.isProtected(p.getModifiers())).count());
        //Max package-private methods
        assertEquals(0, Arrays.stream(ownClass.getDeclaredMethods()).filter(p -> Modifier.isNative(p.getModifiers())).count());

        try {
            assertTrue(Modifier.isPublic(ownClass.getDeclaredMethod("getPoints").getModifiers()));
            assertEquals(int.class, ownClass.getDeclaredMethod("getPoints").getReturnType());
        } catch (NoSuchMethodException e) {
            fail("[ERROR] There is some problem with the definition of methods");
            e.printStackTrace();
        }
    }
}