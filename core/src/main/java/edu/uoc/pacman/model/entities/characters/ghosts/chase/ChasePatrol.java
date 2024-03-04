package edu.uoc.pacman.model.entities.characters.ghosts.chase;

import edu.uoc.pacman.model.entities.characters.ghosts.Blinky;
import edu.uoc.pacman.model.entities.characters.ghosts.Ghost;
import edu.uoc.pacman.model.entities.characters.pacman.Pacman;
import edu.uoc.pacman.model.utils.Direction;
import edu.uoc.pacman.model.utils.Position;

public class ChasePatrol implements ChaseBehaviour {

    private static final int TILES_OFFSET = 2, VECTOR_INCREASE = 2;


    public ChasePatrol() {}

    @Override
    public Position getChasePosition(Ghost ghost) {
        Pacman p = ghost.getLevel().getPacman();
        Direction d = p.getDirection();
        Position targetBlinkyPosition = Position.add(p.getPosition(), new Position(d.getX() * TILES_OFFSET, d.getY() * TILES_OFFSET));
        Blinky b = p.getLevel().getBlinky();
        if (b != null) {
            Position blinkyPosition = b.getPosition();
            Position s = Position.add(targetBlinkyPosition, new Position(-1 * blinkyPosition.getX(), -1 * blinkyPosition.getY()));
            s.setX(s.getX() * VECTOR_INCREASE);
            s.setY(s.getY() * VECTOR_INCREASE);
            return s;
        }
        return targetBlinkyPosition;
    }
}
