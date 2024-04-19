package br.com.alura.model.enums;


import lombok.Getter;

@Getter
public enum ScoreValue {

    ZERO(0),
    ONE(1),
    TWO(2),
    THREE(3),
    FOUR(4),
    FIVE(5),
    SIX(6),
    SEVEN(7),
    EIGHT(8),
    NINE(9),
    TEN(10);

    private final int value;

    ScoreValue(int value) {
        this.value = value;
    }
}
