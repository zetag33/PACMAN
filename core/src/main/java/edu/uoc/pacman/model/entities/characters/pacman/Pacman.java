package edu.uoc.pacman.model.entities.characters.pacman;

import edu.uoc.pacman.model.Level;
import edu.uoc.pacman.model.entities.Scorable;
import edu.uoc.pacman.model.entities.characters.Character;
import edu.uoc.pacman.model.entities.characters.ghosts.Behaviour;
import edu.uoc.pacman.model.entities.characters.ghosts.Ghost;
import edu.uoc.pacman.model.entities.items.*;
import edu.uoc.pacman.model.utils.*;

public class Pacman extends Character {

    private State state;

    public Pacman(Position startPosition, Direction direction, State state, Level level) {
        super(startPosition, direction, null, level);
        setDirection(direction);
        this.state = state;
    }

    public State getState() {
        return state;
    }

    public void setState(State state) {
        if (state != null)
            this.state = state;
    }

    private void nextState() {
        setDuration(getDuration() - 1);
        if (getDuration() == 0) {
            state = State.NORMAL;
        }
    }

    @Override
    public void reset() {
        super.reset();
        state = State.INVINCIBLE;
        setDirection(Direction.UP);
    }

    @Override
    public void move() {
        Position newPosition = Position.add(getPosition(), new Position(getDirection().getX(), getDirection().getY()));
        if (getLevel().getMapItem(newPosition).isPathable()) {
            setPosition(newPosition);
            eat();
            hit();
        }
        nextState();
    }

    @Override
    public void setDirection(Direction direction) {
        super.setDirection(direction);
        if (direction != null) {
            switch (direction) {
                case UP -> setSprite(Sprite.PACMAN_UP);
                case DOWN -> setSprite(Sprite.PACMAN_DOWN);
                case LEFT -> setSprite(Sprite.PACMAN_LEFT);
                case RIGHT -> setSprite(Sprite.PACMAN_RIGHT);
            }
        }
    }

    private void eat() {
        MapItem object = getLevel().getMapItem(getPosition());
        if (object instanceof Pickable p) {
            getLevel().removeMapItem(object);
            getLevel().addMapItem(new Path(object.getPosition()));
            p.setPicked(true);
            if (object instanceof Scorable s) {
                getLevel().addPoints(s.getPoints());
            }
            if (object instanceof Energizer) {
                getLevel().setGhostsFrightened();
                state = State.EATER;
            }
            if (object instanceof Life) {
                getLevel().increaseLives();
            }
        }
    }

    @Override
    public boolean hit() {
        for (Ghost g: getLevel().getGhostList()) {
            if (getPosition().equals(g.getPosition())) {
                if (state == State.INVINCIBLE || g.getBehaviour() == Behaviour.INACTIVE) {
                    return false;
                }
                if (g.getBehaviour() == Behaviour.FRIGHTENED) {
                    g.kill();
                } else {
                    kill();
                }
                return true;
            }
        }
        return false;
    }

    @Override
    public void kill() {
        super.kill();
        getLevel().decreaseLives();
        state = State.INVINCIBLE;
    }
}
