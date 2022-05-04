package com.example.happypet.util;

import android.os.Build;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.room.TypeConverter;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class LocalDateTimeConverter {

    @RequiresApi(api = Build.VERSION_CODES.O)
    @TypeConverter
    public static LocalDateTime toDate(@NonNull String dateString) {
        return LocalDateTime.parse(dateString);
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    @NonNull
    @TypeConverter
    public static String toDateString(@NonNull LocalDateTime date) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        return date.format(formatter);
    }

}
