package edu.uoc.pacman.model.entities.characters.ghosts;

import edu.uoc.pacman.model.Level;
import edu.uoc.pacman.model.entities.characters.ghosts.chase.ChasePatrol;
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
class InkyTest {

    Inky inky;

    @Mock
    Level level;

    @Mock
    Pacman pacman;

    @BeforeEach
    void setUp() {
        when(level.getWidth()).thenReturn(8);
        when(level.getHeight()).thenReturn(8);
        inky = new Inky(new Position(2, 3), Direction.DOWN, Behaviour.CHASE, level);
    }

    @Test
    @Tag("basic")
    void correctInstanceChaseBehaviour(){
        assertTrue(inky.chaseBehaviour instanceof ChasePatrol);
    }

    @Test
    @Tag("basic")
    void getBehaviour() {
        assertEquals(Behaviour.CHASE, inky.getBehaviour());
    }

    @Test
    @Tag("basic")
    void setBehaviour() {
        inky.setBehaviour(Behaviour.SCATTER);
        assertEquals(Behaviour.SCATTER, inky.getBehaviour());
    }

    @Test
    @Tag("basic")
    void reset() {
        inky.reset();
        assertEquals(Behaviour.INACTIVE, inky.getBehaviour());
        assertFalse(inky.isDead());
        assertEquals(Direction.UP, inky.getDirection());
        assertEquals(new Position(2, 3), inky.getPosition());
    }

    @Test
    @Tag("basic")
    void testEqualsOK() {
        assertTrue(inky.equals(inky));
        assertTrue(inky.equals(new Inky(new Position(2, 3), Direction.DOWN, Behaviour.CHASE, level)));
    }

    @Test
    @Tag("basic")
    void testEqualsKO() {
        assertFalse(inky.equals(new Inky(new Position(3, 3), Direction.DOWN, Behaviour.CHASE, level)));
        assertFalse(inky.equals(new Inky(new Position(2, 3), Direction.UP, Behaviour.CHASE, level)));
        assertFalse(inky.equals(new Inky(new Position(2, 3), Direction.DOWN, Behaviour.FRIGHTENED, level)));
        inky.kill();
        assertFalse(inky.equals(new Inky(new Position(2, 3), Direction.DOWN, Behaviour.CHASE, level)));
    }

    @Test
    @Tag("basic")
    void testToString() {
        assertEquals("2,3,DOWN,CHASE:20", inky.toString());
    }

    @Test
    @Tag("basic")
    void getScatterPosition() {
        assertEquals(new Position(8, 8), inky.getScatterPosition());
    }

    @Test
    @Tag("expert")
    void move() {
        try {
            Level level = new Level("levels/level1.txt",3);
            Inky inky = spy((Inky) level.getGhostList().stream().filter(g -> g instanceof Inky).findFirst().get());
            assertEquals(new Position(1,5), inky.getPosition());
            //targetBlinkyPosition is (3,1)--> +2 steps in X (Pacman is facing right at (1,1))
            // targetBlinkyPosition - FirstBlinkyPosition = (3,1) - (6,1) = (-3, 0)
            //targetPosition = 2 * (-3,0) = (-6,0)
            inky.move();
            //Inky in level1.text is facing UP
            // UP(1,4): sqrt(65), DOWN(1,6): sqrt(85), RIGHT(2,5): sqrt(89), LEFT(0,5): sqrt(69) but there is a WALL.
            assertEquals(new Position(1,4), inky.getPosition());
            assertEquals(Direction.UP, inky.getDirection());
            verify(inky,times(1)).hit();

            level.getPacman().setPosition(new Position(5,2));
            level.getPacman().setDirection(Direction.DOWN);
            //inky is (1,4), UP
            //targetBlinkyPosition is (5,4)--> +2 steps in Y (Pacman is facing down at (5,2))
            // targetBlinkyPosition - FirstBlinkyPosition = (5,4) - (6,1) = (-1, 3)
            //targetPosition = 2 * (-1,3) = (-2,6)
            inky.move();
            //left(0,4): sqrt(8), but there is a wall; down(1,5): sqrt(1), but it's opposite,
            // up(1,3): sqrt(18); right(2,4): sqrt(20)
            assertEquals(new Position(1,3), inky.getPosition());
            assertEquals(Direction.UP, inky.getDirection());

            level.getPacman().setPosition(new Position(8,5));
            level.getPacman().setDirection(Direction.RIGHT);
            inky.setPosition(new Position(6,1));
            inky.setDirection(Direction.LEFT);
            //targetBlinkyPosition is (10,5)--> +2 steps in X (Pacman is facing right at (10,5))
            // targetBlinkyPosition - FirstBlinkyPosition = (10,5) - (6,1) = (4, 4)
            //targetPosition = 2 * (4,4) = (8,8)
            inky.move();
            //UP(6,0): sqrt(4), but WALL  , DOWN(6,2): sqrt(40), RIGHT(7,1): sqrt(50) -->opposite, LEFT(5,1): sqrt(58)
            assertEquals(new Position(6,2), inky.getPosition());
            assertEquals(Direction.DOWN, inky.getDirection());

            inky = spy(new Inky(new Position(3,6),Direction.LEFT, Behaviour.INACTIVE, level));
            inky.move();
            assertEquals(new Position(3,6), inky.getPosition());
            assertEquals(Direction.LEFT, inky.getDirection());
            verify(inky, never()).hit();
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
        assertTrue(inky.hit());
        verify(pacman, times(1)).kill();
    }

    @Test
    @Tag("advanced")
    @DisplayName("Ghost is SCATTER, then ghost kills Pacman (NORMAL)")
    void hitPGhostKillsPacman() {
        Inky inky = spy(new Inky(new Position(2, 3), Direction.DOWN, Behaviour.SCATTER, level));
        when(level.getPacman()).thenReturn(pacman);
        when(pacman.getState()).thenReturn(State.NORMAL);
        when(pacman.getPosition()).thenReturn(new Position(2, 3));
        assertTrue(inky.hit());
        verify(pacman, times(1)).kill();
    }

    @Test
    @Tag("advanced")
    @DisplayName("Ghost is SCATTER and Pacman is INVINCIBLE, then ghost does not kills Pacman")
    void hitPGhostNotKillPacman1() {
        Inky inky = spy(new Inky(new Position(2, 3), Direction.DOWN, Behaviour.SCATTER, level));
        when(level.getPacman()).thenReturn(pacman);
        when(pacman.getState()).thenReturn(State.INVINCIBLE);
        when(pacman.getPosition()).thenReturn(new Position(2, 3));
        assertTrue(inky.hit());
        verify(pacman, never()).kill();
    }

    @Test
    @Tag("advanced")
    @DisplayName("Ghost is CHASE and Pacman is INVINCIBLE, then ghost does not kills Pacman")
    void hitPGhostNotKillPacman2() {
        Inky inky = spy(new Inky(new Position(2, 3), Direction.DOWN, Behaviour.CHASE, level));
        when(level.getPacman()).thenReturn(pacman);
        when(pacman.getState()).thenReturn(State.INVINCIBLE);
        when(pacman.getPosition()).thenReturn(new Position(2, 3));
        assertTrue(inky.hit());
        verify(pacman, never()).kill();
    }


    @Test
    @Tag("advanced")
    @DisplayName("Ghost is INACTIVE, then it does not kill Pacman")
    void hitGhostInactive() {
        Inky inky = spy(new Inky(new Position(2, 3), Direction.DOWN, Behaviour.INACTIVE, level));
        assertFalse(inky.hit());
        verify(inky,never()).kill();
        verify(pacman, never()).kill();
    }

    @Test
    @Tag("advanced")
    @DisplayName("Ghost is FRIGHTENED, then Pacman (by logical it is EATER) kills ghost")
    void hitPacmanKillsGhost1() {
        Inky inky = spy(new Inky(new Position(2, 3), Direction.DOWN, Behaviour.FRIGHTENED, level));
        when(level.getPacman()).thenReturn(pacman);
        when(pacman.getPosition()).thenReturn(new Position(2, 3));
        assertTrue(inky.hit());
        verify(inky, times(1)).kill();
    }


    @Test
    @Tag("basic")
    void kill() {
        inky.kill();
        assertTrue(inky.isDead());
        assertEquals(Behaviour.INACTIVE, inky.getBehaviour());
        verify(level, times(1)).addPoints(inky.getPoints());
    }

    @Test
    @Tag("basic")
    void testReset() {
        //Given the setUp state
        //When
        inky.reset();
        //Then
        assertFalse(inky.isDead());
        assertEquals(new Position(2, 3), inky.getPosition());
        assertEquals(Behaviour.INACTIVE, inky.getBehaviour());
        assertEquals(Direction.UP, inky.getDirection());

        //Given
        inky.setPosition(new Position(4, 5));
        inky.setBehaviour(Behaviour.FRIGHTENED);
        inky.setDirection(Direction.LEFT);
        //When
        inky.reset();
        //Then
        assertFalse(inky.isDead());
        assertEquals(new Position(2, 3), inky.getPosition());
        assertEquals(Behaviour.INACTIVE, inky.getBehaviour());
        assertEquals(Direction.UP, inky.getDirection());
    }

    @Test
    @Tag("basic")
    void getDirection() {
        assertEquals(Direction.DOWN, inky.getDirection());
    }

    @Test
    @Tag("basic")
    void setDirection() {
        inky.setDirection(Direction.LEFT);
        assertEquals(Direction.LEFT, inky.getDirection());

        inky.setDirection(Direction.RIGHT);
        assertEquals(Direction.RIGHT, inky.getDirection());

        inky.setDirection(Direction.UP);
        assertEquals(Direction.UP, inky.getDirection());
    }

    @Test
    @Tag("basic")
    void isDead() {
        assertFalse(inky.isDead());
    }

    @Test
    @Tag("basic")
    void alive() {
        inky.alive();
        assertFalse(inky.isDead());
        inky.kill();
        assertTrue(inky.isDead());
        inky.alive();
        assertFalse(inky.isDead());
    }

    @Test
    @Tag("basic")
    void getLevel() {
        assertEquals(level, inky.getLevel());
    }

    @Test
    @Tag("basic")
    void setLevel() {
        inky.setLevel(null);
        assertNull(inky.getLevel());
    }

    @Test
    @Tag("basic")
    void getPosition() {
        assertEquals(new Position(2, 3), inky.getPosition());
    }

    @Test
    @Tag("basic")
    void setPosition() {
        inky.setPosition(new Position(-1, 50));
        assertEquals(new Position(-1, 50), inky.getPosition());

        inky.setPosition(new Position(2, 3));
        assertEquals(new Position(2, 3), inky.getPosition());
    }

    @Test
    @Tag("basic")
    void isPathable() {
        assertTrue(inky.isPathable());
    }

    @Test
    @Tag("basic")
    void setPathable() {
        inky.setPathable(false);
        assertFalse(inky.isPathable());

        inky.setPathable(true);
        assertTrue(inky.isPathable());
    }

    @Test
    @Tag("basic")
    void getSprite() {
        assertEquals(Sprite.INKY, inky.getSprite());
    }


    @Test
    @Tag("basic")
    void getPoints() {
        assertEquals(200, inky.getPoints());
    }

    @Test
    @Tag("sanity")
    @DisplayName("Sanity Inky - Class definition")
    void checkClassSanity() {
        final Class<Inky> ownClass = Inky.class;

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
    @DisplayName("Sanity Inky - Fields definition")
    public void checkFieldsSanity() {
        final Class<Inky> ownClass = Inky.class;

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
    @DisplayName("Sanity Inky - Methods definition")
    public void checkMethodsSanity() {
        final Class<Inky> ownClass = Inky.class;

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
            fail("[ERROR] Inky's constructor is defined wrongly");
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