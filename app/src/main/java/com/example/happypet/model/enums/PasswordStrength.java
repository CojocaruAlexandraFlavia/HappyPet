package com.example.happypet.model.enums;

import android.graphics.Color;

import androidx.annotation.NonNull;

public enum PasswordStrength {
    WEAK("WEAK", Color.parseColor("#FF0000")),
    MEDIUM("MEDIUM", Color.parseColor("#FF8C00")),
    STRONG("STRONG", Color.parseColor("#0000FF")),
    VERY_STRONG("VERY STRONG", Color.parseColor("#00FF00"));

    public String message;
    public int color;
    private static final int MIN_LENGTH = 8;
    private static final int MAX_LENGTH = 15;

    PasswordStrength(String message, int color) {
        this.message = message;
        this.color = color;
    }

    public static PasswordStrength calculate(@NonNull String password) {
        int score = 0;
        boolean upper = false, lower = false, digit = false, specialChar = false;

        for (int i = 0; i < password.length(); i++) {
            char c = password.charAt(i);

            if (!specialChar  &&  !Character.isLetterOrDigit(c)) {
                score++;
                specialChar = true;
            } else {
                if (!digit  &&  Character.isDigit(c)) {
                    score++;
                    digit = true;
                } else {
                    if (!upper || !lower) {
                        if (Character.isUpperCase(c)) {
                            upper = true;
                        } else {
                            lower = true;
                        }

                        if (upper && lower) {
                            score++;
                        }
                    }
                }
            }
        }

        int length = password.length();

        if (length > MAX_LENGTH) {
            score++;
        } else if (length < MIN_LENGTH) {
            score = 0;
        }

        switch(score) {
            case 0 : return WEAK;
            case 1 : return MEDIUM;
            case 2 : return STRONG;
            case 3 : return VERY_STRONG;
            default:
        }

        return VERY_STRONG;
    }
}
