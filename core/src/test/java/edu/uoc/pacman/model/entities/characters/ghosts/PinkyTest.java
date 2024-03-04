package edu.uoc.pacman.model.entities.characters.ghosts;

import edu.uoc.pacman.model.Level;
import edu.uoc.pacman.model.entities.characters.ghosts.chase.ChaseAmbush;
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
import static org.mockito.Mockito.times;

@ExtendWith(MockitoExtension.class)
class PinkyTest {

    Pinky pinky;

    @Mock
    Level level;

    @Mock
    Pacman pacman;

    @BeforeEach
    void setUp() {
        pinky = new Pinky(new Position(2, 3), Direction.DOWN, Behaviour.CHASE, level);
    }

    @Test
    @Tag("basic")
    void correctInstanceChaseBehaviour(){
        assertTrue(pinky.chaseBehaviour instanceof ChaseAmbush);
    }

    @Test
    @Tag("basic")
    void getBehaviour() {
        assertEquals(Behaviour.CHASE, pinky.getBehaviour());
    }

    @Test
    @Tag("basic")
    void setBehaviour() {
        pinky.setBehaviour(Behaviour.SCATTER);
        assertEquals(Behaviour.SCATTER, pinky.getBehaviour());
    }

    @Test
    @Tag("basic")
    void reset() {
        pinky.reset();
        assertEquals(Behaviour.INACTIVE, pinky.getBehaviour());
        assertFalse(pinky.isDead());
        assertEquals(Direction.UP, pinky.getDirection());
        assertEquals(new Position(2, 3), pinky.getPosition());
    }

    @Test
    @Tag("basic")
    void testEqualsOK() {
        assertTrue(pinky.equals(pinky));
        assertTrue(pinky.equals(new Pinky(new Position(2, 3), Direction.DOWN, Behaviour.CHASE, level)));
    }

    @Test
    @Tag("basic")
    void testEqualsKO() {
        assertFalse(pinky.equals(new Pinky(new Position(3, 3), Direction.DOWN, Behaviour.CHASE, level)));
        assertFalse(pinky.equals(new Pinky(new Position(2, 3), Direction.UP, Behaviour.CHASE, level)));
        assertFalse(pinky.equals(new Pinky(new Position(2, 3), Direction.DOWN, Behaviour.FRIGHTENED, level)));
        pinky.kill();
        assertFalse(pinky.equals(new Pinky(new Position(2, 3), Direction.DOWN, Behaviour.CHASE, level)));
    }

    @Test
    @Tag("basic")
    void testToString() {
        assertEquals("2,3,DOWN,CHASE:20", pinky.toString());
    }

    @Test
    @Tag("basic")
    void getScatterPosition() {
        assertEquals(new Position(-1, -1), pinky.getScatterPosition());
    }

    @Test
    @Tag("expert")
    void move() {
        try {
            Level level = new Level("levels/level1.txt",3);
            Pinky pinky = spy((Pinky) level.getGhostList().stream().filter(g -> g instanceof Pinky).findFirst().get());
            assertEquals(new Position(1,6), pinky.getPosition());
            pinky.move(); //targetPosition is (5,1) --> 4 steps in X (Pacman is facing right at (1,1))
            assertEquals(new Position(2,6), pinky.getPosition());
            assertEquals(Direction.RIGHT, pinky.getDirection());
            verify(pinky,times(1)).hit();

            level.getPacman().setPosition(new Position(5,2));
            level.getPacman().setDirection(Direction.DOWN);
            pinky.move(); //targetPosition is (5,6)
            assertEquals(new Position(3,6), pinky.getPosition());
            assertEquals(Direction.RIGHT, pinky.getDirection());

            level.getPacman().setPosition(new Position(1,1));
            level.getPacman().setDirection(Direction.RIGHT);
            pinky.setPosition(new Position(4,1));
            pinky.setDirection(Direction.LEFT);
            // P..GT  Because Ghost is facing left, it cannot go back, so the shortest path is (4,2). UP is a wall.
            pinky.move();
            assertEquals(new Position(4,2), pinky.getPosition());
            assertEquals(Direction.DOWN, pinky.getDirection());

            level.getPacman().setPosition(new Position(2,2));
            level.getPacman().setDirection(Direction.LEFT);
            pinky.move();
            assertEquals(new Position(4,3), pinky.getPosition());
            assertEquals(Direction.DOWN, pinky.getDirection());

            //There is a wall between Pacman and Pinky
            level.getPacman().setPosition(new Position(4,4));
            pinky.setPosition(new Position(4,6));
            pinky.move();
            assertEquals(new Position(3,6), pinky.getPosition());
            assertEquals(Direction.LEFT, pinky.getDirection());

            pinky = spy(new Pinky(new Position(3,6),Direction.LEFT, Behaviour.INACTIVE, level));
            pinky.move();
            assertEquals(new Position(3,6), pinky.getPosition());
            assertEquals(Direction.LEFT, pinky.getDirection());
            verify(pinky, never()).hit();
        } catch (LevelException e) {
            System.out.println("[ERROR] Loading level while testing move()");
        }
    }

    @Test
    @Tag("advanced")
    @DisplayName("Ghost is CHASE, then ghost kills Pacman (NORMAL).")
    void hitGhostKillsPacman() {
        when(level.getPacman()).thenReturn(pacman);
        when(pacman.getPosition()).thenReturn(new Position(2, 3));
        when(pacman.getState()).thenReturn(State.NORMAL);
        assertTrue(pinky.hit());
        verify(pacman, times(1)).kill();
    }

    @Test
    @Tag("advanced")
    @DisplayName("Ghost is SCATTER, then ghost kills Pacman (NORMAL)")
    void hitPGhostKillsPacman() {
        Pinky pinky = spy(new Pinky(new Position(2, 3), Direction.DOWN, Behaviour.SCATTER, level));
        when(level.getPacman()).thenReturn(pacman);
        when(pacman.getState()).thenReturn(State.NORMAL);
        when(pacman.getPosition()).thenReturn(new Position(2, 3));
        assertTrue(pinky.hit());
        verify(pacman, times(1)).kill();
    }

    @Test
    @Tag("advanced")
    @DisplayName("Ghost is SCATTER and Pacman is INVINCIBLE, then ghost does not kills Pacman")
    void hitPGhostNotKillPacman1() {
        Pinky pinky = spy(new Pinky(new Position(2, 3), Direction.DOWN, Behaviour.SCATTER, level));
        when(level.getPacman()).thenReturn(pacman);
        when(pacman.getState()).thenReturn(State.INVINCIBLE);
        when(pacman.getPosition()).thenReturn(new Position(2, 3));
        assertTrue(pinky.hit());
        verify(pacman, never()).kill();
    }

    @Test
    @Tag("advanced")
    @DisplayName("Ghost is CHASE and Pacman is INVINCIBLE, then ghost does not kills Pacman")
    void hitPGhostNotKillPacman2() {
        Pinky pinky = spy(new Pinky(new Position(2, 3), Direction.DOWN, Behaviour.CHASE, level));
        when(level.getPacman()).thenReturn(pacman);
        when(pacman.getState()).thenReturn(State.INVINCIBLE);
        when(pacman.getPosition()).thenReturn(new Position(2, 3));
        assertTrue(pinky.hit());
        verify(pacman, never()).kill();
    }


    @Test
    @Tag("advanced")
    @DisplayName("Ghost is INACTIVE, then it does not kill Pacman")
    void hitGhostInactive() {
        Pinky pinky = spy(new Pinky(new Position(2, 3), Direction.DOWN, Behaviour.INACTIVE, level));
        assertFalse(pinky.hit());
        verify(pinky,never()).kill();
        verify(pacman, never()).kill();
    }

    @Test
    @Tag("advanced")
    @DisplayName("Ghost is FRIGHTENED, then Pacman (by logical it is EATER) kills ghost")
    void hitPacmanKillsGhost1() {
        Pinky pinky = spy(new Pinky(new Position(2, 3), Direction.DOWN, Behaviour.FRIGHTENED, level));
        when(level.getPacman()).thenReturn(pacman);
        when(pacman.getPosition()).thenReturn(new Position(2, 3));
        assertTrue(pinky.hit());
        verify(pinky, times(1)).kill();
    }


    @Test
    @Tag("basic")
    void kill() {
        pinky.kill();
        assertTrue(pinky.isDead());
        assertEquals(Behaviour.INACTIVE, pinky.getBehaviour());
        verify(level, times(1)).addPoints(pinky.getPoints());
    }

    @Test
    @Tag("basic")
    void testReset() {
        //Given the setUp state
        //When
        pinky.reset();
        //Then
        assertFalse(pinky.isDead());
        assertEquals(new Position(2, 3), pinky.getPosition());
        assertEquals(Behaviour.INACTIVE, pinky.getBehaviour());
        assertEquals(Direction.UP, pinky.getDirection());

        //Given
        pinky.setPosition(new Position(4, 5));
        pinky.setBehaviour(Behaviour.FRIGHTENED);
        pinky.setDirection(Direction.LEFT);
        //When
        pinky.reset();
        //Then
        assertFalse(pinky.isDead());
        assertEquals(new Position(2, 3), pinky.getPosition());
        assertEquals(Behaviour.INACTIVE, pinky.getBehaviour());
        assertEquals(Direction.UP, pinky.getDirection());
    }

    @Test
    @Tag("basic")
    void getDirection() {
        assertEquals(Direction.DOWN, pinky.getDirection());
    }

    @Test
    @Tag("basic")
    void setDirection() {
        pinky.setDirection(Direction.LEFT);
        assertEquals(Direction.LEFT, pinky.getDirection());

        pinky.setDirection(Direction.RIGHT);
        assertEquals(Direction.RIGHT, pinky.getDirection());

        pinky.setDirection(Direction.UP);
        assertEquals(Direction.UP, pinky.getDirection());
    }

    @Test
    @Tag("basic")
    void isDead() {
        assertFalse(pinky.isDead());
    }

    @Test
    @Tag("basic")
    void alive() {
        pinky.alive();
        assertFalse(pinky.isDead());
        pinky.kill();
        assertTrue(pinky.isDead());
        pinky.alive();
        assertFalse(pinky.isDead());
    }

    @Test
    @Tag("basic")
    void getLevel() {
        assertEquals(level, pinky.getLevel());
    }

    @Test
    @Tag("basic")
    void setLevel() {
        pinky.setLevel(null);
        assertNull(pinky.getLevel());
    }

    @Test
    @Tag("basic")
    void getPosition() {
        assertEquals(new Position(2, 3), pinky.getPosition());
    }

    @Test
    @Tag("basic")
    void setPosition() {
        pinky.setPosition(new Position(-1, 50));
        assertEquals(new Position(-1, 50), pinky.getPosition());

        pinky.setPosition(new Position(2, 3));
        assertEquals(new Position(2, 3), pinky.getPosition());
    }

    @Test
    @Tag("basic")
    void isPathable() {
        assertTrue(pinky.isPathable());
    }

    @Test
    @Tag("basic")
    void setPathable() {
        pinky.setPathable(false);
        assertFalse(pinky.isPathable());

        pinky.setPathable(true);
        assertTrue(pinky.isPathable());
    }

    @Test
    @Tag("basic")
    void getSprite() {
        assertEquals(Sprite.PINKY, pinky.getSprite());
    }


    @Test
    @Tag("basic")
    void getPoints() {
        assertEquals(300, pinky.getPoints());
    }

    @Test
    @Tag("sanity")
    @DisplayName("Sanity Pinky - Class definition")
    void checkClassSanity() {
        final Class<Pinky> ownClass = Pinky.class;

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
    @DisplayName("Sanity Pinky - Fields definition")
    public void checkFieldsSanity() {
        final Class<Pinky> ownClass = Pinky.class;

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
    @DisplayName("Sanity Pinky - Methods definition")
    public void checkMethodsSanity() {
        final Class<Pinky> ownClass = Pinky.class;

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
            fail("[ERROR] Pinky's constructor is defined wrongly");
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