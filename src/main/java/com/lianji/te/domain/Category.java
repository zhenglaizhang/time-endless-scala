package com.lianji.te.domain;

public enum Category {
    Porttrait(0), Landscape(1), Animal(2), Other(3);

    private int value;

    Category(int value) {
        this.value = value;
    }

    public int getValue() {
        return value;
    }

    public void setValue(int value) {
        this.value = value;
    }
}
