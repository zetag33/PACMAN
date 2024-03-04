package edu.uoc.pacman.model.entities.characters.ghosts;

import edu.uoc.pacman.model.Level;
import edu.uoc.pacman.model.entities.characters.ghosts.chase.ChaseCoward;
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
class ClydeTest {

    Clyde clyde;

    @Mock
    Level level;

    @Mock
    Pacman pacman;

    @BeforeEach
    void setUp() {
        when(level.getHeight()).thenReturn(8);
        clyde = new Clyde(new Position(2, 3), Direction.DOWN, Behaviour.CHASE, level);
    }

    @Test
    @Tag("basic")
    void correctInstanceChaseBehaviour(){
        assertTrue(clyde.chaseBehaviour instanceof ChaseCoward);
    }

    @Test
    @Tag("basic")
    void getBehaviour() {
        assertEquals(Behaviour.CHASE, clyde.getBehaviour());
    }

    @Test
    @Tag("basic")
    void setBehaviour() {
        clyde.setBehaviour(Behaviour.FRIGHTENED);
        assertEquals(Behaviour.FRIGHTENED, clyde.getBehaviour());
    }

    @Test
    @Tag("basic")
    void reset() {
        clyde.reset();
        assertEquals(Behaviour.INACTIVE, clyde.getBehaviour());
        assertFalse(clyde.isDead());
        assertEquals(Direction.UP, clyde.getDirection());
        assertEquals(new Position(2, 3), clyde.getPosition());
    }

    @Test
    @Tag("basic")
    void testEqualsOK() {
        assertTrue(clyde.equals(clyde));
        assertTrue(clyde.equals(new Clyde(new Position(2, 3), Direction.DOWN, Behaviour.CHASE, level)));
    }

    @Test
    @Tag("basic")
    void testEqualsKO() {
        assertFalse(clyde.equals(new Clyde(new Position(3, 3), Direction.DOWN, Behaviour.CHASE, level)));
        assertFalse(clyde.equals(new Clyde(new Position(2, 3), Direction.UP, Behaviour.CHASE, level)));
        assertFalse(clyde.equals(new Clyde(new Position(2, 3), Direction.DOWN, Behaviour.FRIGHTENED, level)));
        clyde.kill();
        assertFalse(clyde.equals(new Clyde(new Position(2, 3), Direction.DOWN, Behaviour.CHASE, level)));
    }

    @Test
    @Tag("basic")
    void testToString() {
        assertEquals("2,3,DOWN,CHASE:20", clyde.toString());
    }

    @Test
    @Tag("basic")
    void getScatterPosition() {
        assertEquals(new Position(-1, 8), clyde.getScatterPosition());
    }

    @Test
    @Tag("expert")
    void move() {
        try {
            Level level = new Level("levels/level1.txt",3);

            Clyde clyde = spy((Clyde) level.getGhostList().stream().filter(g -> g instanceof Clyde).findFirst().get());
            clyde.setBehaviour(Behaviour.CHASE);

            assertEquals(new Position(2,6), clyde.getPosition());
            clyde.move(); //Pacman is in (1,1), then distance is sqrt(26) --> scatterPosition (-1,8)
            assertEquals(new Position(2,5), clyde.getPosition());
            assertEquals(Direction.UP, clyde.getDirection());
            verify(clyde, times(1)).hit();

            level.getPacman().setPosition(new Position(5,2));
            clyde.move(); //ScatterPosition
            assertEquals(new Position(1,5), clyde.getPosition());
            assertEquals(Direction.LEFT, clyde.getDirection());

            level.getPacman().setPosition(new Position(10,6));
            clyde.move(); //ScatterPosition
            assertEquals(new Position(1,6), clyde.getPosition());
            assertEquals(Direction.DOWN, clyde.getDirection());

            level.getPacman().setPosition(new Position(24,1));
            clyde.move(); //Pacman's position
            assertEquals(new Position(2,6), clyde.getPosition());
            assertEquals(Direction.RIGHT, clyde.getDirection());

            //There is a wall between Pacman and Clyde
            level.getPacman().setPosition(new Position(4,4));
            clyde.setPosition(new Position(4,6));
            clyde.move(); //ScatterPosition
            assertEquals(new Position(5,6), clyde.getPosition()); //No (4,6) because it is the opposite direction
            assertEquals(Direction.RIGHT, clyde.getDirection());

            clyde = spy(new Clyde(new Position(5,6),Direction.DOWN, Behaviour.INACTIVE, level));
            clyde.move();
            assertEquals(new Position(5,6), clyde.getPosition());
            assertEquals(Direction.DOWN, clyde.getDirection());
            verify(clyde, never()).hit();

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
        assertTrue(clyde.hit());
        verify(pacman, times(1)).kill();
    }

    @Test
    @Tag("advanced")
    @DisplayName("Ghost is SCATTER, then ghost kills Pacman (NORMAL)")
    void hitPGhostKillsPacman() {
        Clyde clyde = spy(new Clyde(new Position(2, 3), Direction.DOWN, Behaviour.SCATTER, level));
        when(level.getPacman()).thenReturn(pacman);
        when(pacman.getState()).thenReturn(State.NORMAL);
        when(pacman.getPosition()).thenReturn(new Position(2, 3));
        assertTrue(clyde.hit());
        verify(pacman, times(1)).kill();
    }

    @Test
    @Tag("advanced")
    @DisplayName("Ghost is SCATTER and Pacman is INVINCIBLE, then ghost does not kills Pacman")
    void hitPGhostNotKillPacman1() {
        Clyde clyde = spy(new Clyde(new Position(2, 3), Direction.DOWN, Behaviour.SCATTER, level));
        when(level.getPacman()).thenReturn(pacman);
        when(pacman.getState()).thenReturn(State.INVINCIBLE);
        when(pacman.getPosition()).thenReturn(new Position(2, 3));
        assertTrue(clyde.hit());
        verify(pacman, never()).kill();
    }

    @Test
    @Tag("advanced")
    @DisplayName("Ghost is CHASE and Pacman is INVINCIBLE, then ghost does not kills Pacman")
    void hitPGhostNotKillPacman2() {
        Clyde clyde = spy(new Clyde(new Position(2, 3), Direction.DOWN, Behaviour.CHASE, level));
        when(level.getPacman()).thenReturn(pacman);
        when(pacman.getState()).thenReturn(State.INVINCIBLE);
        when(pacman.getPosition()).thenReturn(new Position(2, 3));
        assertTrue(clyde.hit());
        verify(pacman, never()).kill();
    }


    @Test
    @Tag("advanced")
    @DisplayName("Ghost is INACTIVE, then it does not kill Pacman")
    void hitGhostInactive() {
        Clyde clyde = spy(new Clyde(new Position(2, 3), Direction.DOWN, Behaviour.INACTIVE, level));
        assertFalse(clyde.hit());
        verify(clyde,never()).kill();
        verify(pacman, never()).kill();
    }

    @Test
    @Tag("advanced")
    @DisplayName("Ghost is FRIGHTENED, then Pacman (by logical it is EATER) kills ghost")
    void hitPacmanKillsGhost1() {
        Clyde clyde = spy(new Clyde(new Position(2, 3), Direction.DOWN, Behaviour.FRIGHTENED, level));
        when(level.getPacman()).thenReturn(pacman);
        when(pacman.getPosition()).thenReturn(new Position(2, 3));
        assertTrue(clyde.hit());
        verify(clyde, times(1)).kill();
    }


    @Test
    @Tag("basic")
    void kill() {
        clyde.kill();
        assertTrue(clyde.isDead());
        assertEquals(Behaviour.INACTIVE, clyde.getBehaviour());
        verify(level, times(1)).addPoints(clyde.getPoints());
    }

    @Test
    @Tag("basic")
    void testReset() {
        //Given the setUp state
        //When
        clyde.reset();
        //Then
        assertFalse(clyde.isDead());
        assertEquals(new Position(2, 3), clyde.getPosition());
        assertEquals(Behaviour.INACTIVE, clyde.getBehaviour());
        assertEquals(Direction.UP, clyde.getDirection());

        //Given
        clyde.setPosition(new Position(4, 5));
        clyde.setBehaviour(Behaviour.FRIGHTENED);
        clyde.setDirection(Direction.LEFT);
        //When
        clyde.reset();
        //Then
        assertFalse(clyde.isDead());
        assertEquals(new Position(2, 3), clyde.getPosition());
        assertEquals(Behaviour.INACTIVE, clyde.getBehaviour());
        assertEquals(Direction.UP, clyde.getDirection());
    }

    @Test
    @Tag("basic")
    void getDirection() {
        assertEquals(Direction.DOWN, clyde.getDirection());
    }

    @Test
    @Tag("basic")
    void setDirection() {
        clyde.setDirection(Direction.LEFT);
        assertEquals(Direction.LEFT, clyde.getDirection());

        clyde.setDirection(Direction.RIGHT);
        assertEquals(Direction.RIGHT, clyde.getDirection());

        clyde.setDirection(Direction.UP);
        assertEquals(Direction.UP, clyde.getDirection());
    }

    @Test
    @Tag("basic")
    void isDead() {
        assertFalse(clyde.isDead());
    }

    @Test
    @Tag("basic")
    void alive() {
        clyde.alive();
        assertFalse(clyde.isDead());
        clyde.kill();
        assertTrue(clyde.isDead());
        clyde.alive();
        assertFalse(clyde.isDead());
    }

    @Test
    @Tag("basic")
    void getLevel() {
        assertEquals(level, clyde.getLevel());
    }

    @Test
    @Tag("basic")
    void setLevel() {
        clyde.setLevel(null);
        assertNull(clyde.getLevel());
    }

    @Test
    @Tag("basic")
    void getPosition() {
        assertEquals(new Position(2, 3), clyde.getPosition());
    }

    @Test
    @Tag("basic")
    void setPosition() {
        clyde.setPosition(new Position(-1, 50));
        assertEquals(new Position(-1, 50), clyde.getPosition());

        clyde.setPosition(new Position(2, 3));
        assertEquals(new Position(2, 3), clyde.getPosition());
    }

    @Test
    @Tag("basic")
    void isPathable() {
        assertTrue(clyde.isPathable());
    }

    @Test
    @Tag("basic")
    void setPathable() {
        clyde.setPathable(false);
        assertFalse(clyde.isPathable());

        clyde.setPathable(true);
        assertTrue(clyde.isPathable());
    }

    @Test
    @Tag("basic")
    void getSprite() {
        assertEquals(Sprite.CLYDE, clyde.getSprite());
    }

    @Test
    @Tag("basic")
    void getPoints() {
        assertEquals(100, clyde.getPoints());
    }

    @Test
    @Tag("sanity")
    @DisplayName("Sanity Clyde - Class definition")
    void checkClassSanity() {
        final Class<Clyde> ownClass = Clyde.class;

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
    @DisplayName("Sanity Clyde - Fields definition")
    public void checkFieldsSanity() {
        final Class<Clyde> ownClass = Clyde.class;

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
    @DisplayName("Sanity Clyde - Methods definition")
    public void checkMethodsSanity() {
        final Class<Clyde> ownClass = Clyde.class;

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
            fail("[ERROR] Clyde's constructor is defined wrongly");
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