package edu.uoc.pacman.model.entities.characters.ghosts;

import edu.uoc.pacman.model.Level;
import edu.uoc.pacman.model.entities.characters.ghosts.chase.ChaseAggressive;
import edu.uoc.pacman.model.entities.characters.ghosts.chase.ChasePatrol;
import edu.uoc.pacman.model.utils.Direction;
import edu.uoc.pacman.model.utils.Position;
import edu.uoc.pacman.model.utils.Sprite;

public class Inky extends Ghost {

    private static final int POINTS = 200;

    public Inky(Position startPosition, Direction direction, Behaviour behaviour, Level level) {
        super(startPosition, new Position(level.getWidth(), level.getHeight()), direction, behaviour, Sprite.INKY, level);
        chaseBehaviour = new ChasePatrol();
    }

    @Override
    public int getPoints() {
        return Inky.POINTS;
    }
}
