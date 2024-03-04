package edu.uoc.pacman.model.entities.characters.ghosts;

import edu.uoc.pacman.model.Level;
import edu.uoc.pacman.model.entities.Scorable;
import edu.uoc.pacman.model.entities.characters.Character;
import edu.uoc.pacman.model.entities.characters.ghosts.chase.ChaseBehaviour;
import edu.uoc.pacman.model.entities.characters.pacman.*;
import edu.uoc.pacman.model.utils.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public abstract class Ghost extends Character implements Scorable {

    private Behaviour behaviour;
    private Position scatterPosition;
    protected ChaseBehaviour chaseBehaviour;

    protected Ghost(Position startPosition,
                    Position scatterPosition,
                    Direction direction,
                    Behaviour behaviour,
                    Sprite sprite,
                    Level level) {
        super(startPosition, direction, sprite, level);
        this.behaviour = behaviour;
        this.scatterPosition = scatterPosition;
    }

    public Behaviour getBehaviour() {
        return behaviour;
    }

    public void setBehaviour(Behaviour behaviour) {
        if (behaviour != null) {
            this.behaviour = behaviour;
            setDuration(behaviour.getDuration());
        }
    }

    private void nextBehaviour() {
        setDuration(getDuration() - 1);
        if (getDuration() == 0) {
            if (behaviour == Behaviour.CHASE) {
                setBehaviour(Behaviour.SCATTER);
            } else {
                setBehaviour(Behaviour.CHASE);
            }
        }
    }

    @Override
    public void reset() {
        super.reset();
        behaviour = Behaviour.INACTIVE;
        setDirection(Direction.UP);
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        Ghost g = (Ghost) o;
        return isDead() == g.isDead() && behaviour == g.behaviour && getDirection() == g.getDirection()
                && getPosition().equals(g.getPosition()) && getDuration() == g.getDuration();
    }

    @Override
    public String toString() {
        return String.format("%d,%d,%s,%s", getPosition().getX(), getPosition().getY(), getDirection().name(), behaviour.toString());
    }

    public Position getScatterPosition() {
        return scatterPosition;
    }

    private void setScatterPosition(Position scatterPosition) {
        this.scatterPosition = scatterPosition;
    }

    private Position getTargetPosition() {
        return switch (behaviour) {
            case CHASE -> chaseBehaviour.getChasePosition(this);
            case SCATTER, FRIGHTENED -> scatterPosition;
            default -> null;
        };
    }

    @Override
    public void move() {
        Position target = getTargetPosition();
        if (target != null) {
            Direction[] directions = Direction.values();
            Position[] adj = new Position[directions.length];
            List<Double> distances = new ArrayList<>();
            for (int i = 0; i < directions.length; i++) {
                adj[i] = Position.add(getPosition(), new Position(directions[i].getX(), directions[i].getY()));
                distances.add(target.distance(adj[i]));
            }
            while (true) {
                double min = Collections.min(distances);
                int idx = distances.indexOf(min);
                if (getLevel().isPathable(adj[idx]) && directions[idx] != getDirection().opposite()) {
                    int lidx = distances.lastIndexOf(min);
                    if (idx != lidx && getLevel().isPathable(adj[lidx]) && directions[lidx] != getDirection().opposite()) {
                        idx = lidx;
                    }
                    setPosition(adj[idx]);
                    setDirection(directions[idx]);
                    hit();
                    break;
                } else {distances.set(idx, Double.MAX_VALUE);}
            }

        }
        nextBehaviour();
    }

    @Override
    public boolean hit() {
        if (behaviour == Behaviour.INACTIVE) {
            return false;
        }
        Pacman p = this.getLevel().getPacman();
        boolean e = p.getPosition().equals(this.getPosition());
        if (e) {
            if (behaviour == Behaviour.FRIGHTENED) {
                this.kill();
            } else if (behaviour != Behaviour.INACTIVE && p.getState() == State.NORMAL) {
                p.kill();
            }
        }
        return e;
    }

    @Override
    public void kill() {
        super.kill();
        this.getLevel().addPoints(this.getPoints());
        behaviour = Behaviour.INACTIVE;
    }
}
