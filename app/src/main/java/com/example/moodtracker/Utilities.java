package com.example.moodtracker;

import android.graphics.Color;

import com.example.moodtracker.data.Mood;

public class Utilities {

    // 24 hrs in milliseconds
    public static final float ONE_DAY_IN_MILLISECONDS = 86400000;

    public static int getEmojiResourceId(Mood mood) {
        int resId = R.drawable.very_happy_face; // mood - cheerful

        switch (mood) {
            case Happy:
                resId = R.drawable.happy_face;
                break;
            case Meh:
                resId = R.drawable.neutral_face;
                break;
            case Sad:
                resId = R.drawable.very_sad_face;
                break;
            case Angry:
                resId = R.drawable.angry_face;
                break;
        }

        return resId;
    }

    public static int getColor(Mood mood) {
        int colorInt = Color.parseColor("#FFFF00"); //yellow for cheerful mood

        switch (mood) {
            case Happy:
                colorInt = Color.parseColor("#98FB98");
                break;
            case Meh:
                colorInt = Color.parseColor("#6495ED");
                break;
            case Sad:
                colorInt = Color.parseColor("#A9A9A9");
                break;
            case Angry:
                colorInt = Color.parseColor("#DC143C");
                break;
        }

        return colorInt;
    }
}
