package edu.uoc.pacman.model.utils;

public enum Sprite {

    BLINKY('B', "images/blinky.png"),
    CLYDE('C', "images/clyde.png"),
    DOT('.', "images/dot.png"),
    ENERGIZER('0', "images/energizer.png"),
    INKY('I', "images/inky.png"),
    LIFE('L', "images/life.png"),
    PACMAN_DOWN('V', "images/pac_down.png"),
    PACMAN_LEFT('>', "images/pac_left.png"),
    PACMAN_RIGHT('<', "images/pac_right.png"),
    PACMAN_UP('^', "images/pac_up.png"),
    PATH(' ', "images/path.png"),
    PINKY('P', "images/pinky.png"),
    WALL('#', "images/wall.png");

    private final char symbol;
    private final String imageSrc;

    private Sprite(char symbol, String imageSrc) {
        this.symbol = symbol;
        this.imageSrc = imageSrc;
    }

    public char getSymbol() {
        return symbol;
    }

    public String getImageSrc() {
        return imageSrc;
    }
}
