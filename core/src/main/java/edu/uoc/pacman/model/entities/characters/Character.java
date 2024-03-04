package edu.uoc.pacman.model.entities.characters;

import edu.uoc.pacman.model.Level;
import edu.uoc.pacman.model.entities.Entity;
import edu.uoc.pacman.model.utils.*;

public abstract class Character extends Entity implements Movable, Hitable {

    private Direction direction;
    private int duration;
    private boolean dead;
    private Position startPosition;
    private Level level;

    public Character(Position position, Direction direction, Sprite sprite, Level level) {
        super(position, true, sprite);
        if (position == null)
            setPosition(new Position(0, 0));
        this.direction = direction == null? Direction.UP: direction;
        dead = false;
        startPosition = new Position(getPosition().getX(), getPosition().getY());
        this.level = level;
    }

    public void reset() {
        setPosition(new Position(startPosition.getX(), startPosition.getY()));
        alive();
    }

    public Direction getDirection() {
        return direction;
    }

    @Override
    public void setDirection(Direction direction) {
        if (direction != null)
            this.direction = direction;
    }

    protected int getDuration() {
        return duration;
    }

    protected void setDuration(int duration) {
        this.duration = duration;
    }

    private void setDead(boolean dead) {
        this.dead = dead;
    }

    public boolean isDead() {
        return dead;
    }

    protected Position getStartPosition() {
        return startPosition;
    }

    private void setStartPosition(Position startPosition) {
        this.startPosition = startPosition;
    }

    public void kill() {
        this.dead = true;
    }

    public void alive() {
        this.dead = false;
    }

    public Level getLevel() {
        return level;
    }

    public void setLevel(Level level) {
        this.level = level;
    }

    @Override
    public String toString() {
        return String.format("%d,%d,%s", getPosition().getX(), getPosition().getY(), getDirection());
    }
}
