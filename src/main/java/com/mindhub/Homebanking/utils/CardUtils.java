package com.mindhub.Homebanking.utils;

public class CardUtils {

    public static String CardNumber(MathRandom mathRandom) {
        String number = String.format("%04d", mathRandom.getRandomNumber(0, 10000)) + "-" + String.format("%04d", mathRandom.getRandomNumber(0, 10000)) + "-" + String.format("%04d", mathRandom.getRandomNumber(0, 10000)) + "-" + String.format("%04d", mathRandom.getRandomNumber(0, 10000));
        return number;
    }

    public static String CardCvv(MathRandom mathRandom) {
        String cvv = String.format("%03d", mathRandom.getRandomNumber(0, 1000));
        return cvv;
    }

}
