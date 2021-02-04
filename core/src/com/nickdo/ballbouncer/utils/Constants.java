package com.nickdo.ballbouncer.utils;

public final class Constants {

    /*
        Body Userdata Bits
     */
    public static final short BIT_FOOD = 1;
    public static final short BIT_PLATFORM = 2;
    public static final short BIT_ITEM = 3;
    public static final short BIT_BALL = 4;

    /*
        Ball Velocity
     */
    //TODO horizontal velocity hier als konstante setzen
    public static final float VERTICAL_BALL_VELO_NORMAL = 6.4f;
    public static final float VERTICAL_BALL_VELO_FAST = 7.9f;
    public static final float VERTICAL_BALL_VELO_SLOW = 4.6f;

    public static final float VERTICAL_BALL_GRAVITY_SCALE_NORMAL = 1f;
    public static final float VERTICAL_BALL_GRAVITY_SCALE_FAST = 1.5f;
    public static final float VERTICAL_BALL_GRAVITY_SCALE_SLOW = 0.5f;


    /*
        Food daten
     */
    public static final String FOOD_1_NAME = "items/apple_green.png";
    public static final String FOOD_2_NAME = "items/apple_red.png";
    public static final String FOOD_3_NAME = "items/apple_purple.png";
    public static final String FOOD_4_NAME = "items/apple_golden.png";
    public static final int FOOD_1_LIMIT = 5;  //50
    public static final int FOOD_2_LIMIT = 10;  //200
    public static final int FOOD_3_LIMIT = 20;  //1000
    public static final int FOOD_4_LIMIT = 30;  //2000 ?
    public static final int FOOD_5_LIMIT = 50;  //2000 ?
    public static final int FOOD_6_LIMIT = 75;  //2000 ?
    public static final int FOOD_7_LIMIT = 100;  //2000 ?
    public static final int FOOD_1_POINTS = 1;
    public static final int FOOD_2_POINTS = 2;
    public static final int FOOD_3_POINTS = 3;
    public static final int FOOD_4_POINTS = 4;

    /*
        Powerup Daten
     */
    public static final int POWER_UP_DURATION = 10000;  //10000 -> 10 Sekunden
    public static final int POWER_UP_COOLDOWN = 3000;   //30000 -> 30 Sekunden
    public static final int POWER_UP_MIN_SPAWN = 3000;  //30000 -> 30 Sekunden
    public static final int POWER_UP_DESPAWN_DELAY = 5000; //5000 -> 5 Sekunden
    public static final int POWER_UP_FLASH_DELAY = 3000; //3000 -> 3 Sekunden

    /*
        Sound files
     */
    public static final String SOUND_FOOD_PICKUP = "sounds/foodPickup.mp3";
    public static final String SOUND_PLATFORM_HIT = "sounds/jump.wav";
    public static final String SOUND_POWERUP_PICKUP = "sounds/powerUpPickup.mp3";
    public static final String SOUND_RESPAWN = "sounds/respawn.mp3";

    public static final float PLATFORM_DECREASEMENT = 0.003f;
}
