package com.mindhub.Homebanking.utils;

import org.springframework.stereotype.Component;

@Component
public class MathRandom {
    public int getRandomNumber(int min, int max){
        return (int) ((Math.random() * (max - min)) + min);
    }
}
