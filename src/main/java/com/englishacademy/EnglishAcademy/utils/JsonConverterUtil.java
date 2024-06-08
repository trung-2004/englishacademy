package com.englishacademy.EnglishAcademy.utils;

import com.englishacademy.EnglishAcademy.dtos.booking.LessonDay;
import com.englishacademy.EnglishAcademy.entities.DayOfWeek;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

public class JsonConverterUtil {
    public static List<LessonDay> convertJsonToLessonDay(String jsonString) {
        List<LessonDay> lessonDays = new ArrayList<>();

        if (jsonString == null || jsonString.trim().isEmpty() || jsonString.trim().charAt(0) != '[') {
            return lessonDays;
        }
        JSONArray jsonArray = new JSONArray(jsonString);

        for (int i = 0; i < jsonArray.length(); i++) {
            JSONObject jsonObject = jsonArray.getJSONObject(i);
            LessonDay lessonDay = new LessonDay();

            String dayOfWeekString = jsonObject.getString("dayOfWeek").toUpperCase();
            String dayOfWeek = String.valueOf(dayOfWeekString);

            String startTimeString = jsonObject.getString("startTime");
            LocalTime startTime = LocalTime.parse(startTimeString);

            String endTimeString = jsonObject.getString("endTime");
            LocalTime endTime = LocalTime.parse(endTimeString);

            lessonDay.setDayOfWeek(dayOfWeek);
            lessonDay.setStartTime(startTime);
            lessonDay.setEndTime(endTime);

            lessonDays.add(lessonDay);
        }

        return lessonDays;
    }
}
