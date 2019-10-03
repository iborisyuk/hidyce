package com.playtika.utils;

import java.util.Random;

public class Dice {
   private static final Random rand = new Random();

   public static int roll() {
     return rand.nextInt(6 + 1);
   }

   public static int getSumRoll(int countDice) {
       int sum = 0;

       for (int i = 0; i < countDice; i++) {
           sum += roll();
       }

       return sum;
   }
}
