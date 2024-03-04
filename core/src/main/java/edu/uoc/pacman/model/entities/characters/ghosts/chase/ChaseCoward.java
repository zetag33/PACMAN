package edu.uoc.pacman.model.entities.characters.ghosts.chase;

import edu.uoc.pacman.model.entities.characters.ghosts.Ghost;
import edu.uoc.pacman.model.utils.Position;

public class ChaseCoward implements ChaseBehaviour {

    private static final int TILES_TO_CHASE = 8;

    public ChaseCoward() {}

    @Override
    public Position getChasePosition(Ghost ghost) {
        Position pP = ghost.getLevel().getPacman().getPosition();
        return ghost.getPosition().distance(pP) >= TILES_TO_CHASE? pP: ghost.getScatterPosition();
    }
}
