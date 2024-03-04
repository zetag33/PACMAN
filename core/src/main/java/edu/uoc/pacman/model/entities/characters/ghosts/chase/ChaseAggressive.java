package edu.uoc.pacman.model.entities.characters.ghosts.chase;

import edu.uoc.pacman.model.entities.characters.ghosts.Ghost;
import edu.uoc.pacman.model.utils.Position;

public class ChaseAggressive implements ChaseBehaviour {

    public ChaseAggressive() {}

    @Override
    public Position getChasePosition(Ghost ghost) {
        return ghost.getLevel().getPacman().getPosition();
    }
}
