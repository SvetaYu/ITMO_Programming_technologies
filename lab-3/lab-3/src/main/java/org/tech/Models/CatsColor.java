package org.tech.Models;

public enum CatsColor {
    BLACK("black"),
    WHITE("white"),
    GREY("grey"),
    RED("red"),
    BROWN("brown");

    private final String color;

    CatsColor(String color) {
        this.color = color;
    }

    public String getColor() {
        return color;
    }
}
