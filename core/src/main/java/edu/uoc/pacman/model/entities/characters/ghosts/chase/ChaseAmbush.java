package edu.uoc.pacman.model.entities.characters.ghosts.chase;

import edu.uoc.pacman.model.entities.characters.ghosts.Ghost;
import edu.uoc.pacman.model.entities.characters.pacman.Pacman;
import edu.uoc.pacman.model.utils.Direction;
import edu.uoc.pacman.model.utils.Position;

public class ChaseAmbush implements ChaseBehaviour {

    private static final int TILES_OFFSET = 4;

    public ChaseAmbush() {}

    @Override
    public Position getChasePosition(Ghost ghost) {
        Pacman p = ghost.getLevel().getPacman();
        Direction d = p.getDirection();
        return Position.add(p.getPosition(), new Position(d.getX() * TILES_OFFSET, d.getY() * TILES_OFFSET));
    }
}
