package edu.uoc.pacman.model.entities.characters.ghosts;

public enum Behaviour {
    CHASE(20),
    FRIGHTENED(30),
    SCATTER(10),
    INACTIVE(5);

    private final int duration;

    private Behaviour(int duration) {
        this.duration = duration;
    }

    public int getDuration() {
        return duration;
    }

    @Override
    public String toString() {
        return String.format("%s:%d", name(), duration);
    }
}
