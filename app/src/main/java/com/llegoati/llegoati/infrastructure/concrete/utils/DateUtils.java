package com.llegoati.llegoati.infrastructure.concrete.utils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by Yansel on 6/3/2017.
 */

public class DateUtils {
    public static String formatDate(String date) throws ParseException {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
        Date dateConverted = simpleDateFormat.parse(date.split("T")[0]);
        SimpleDateFormat shortformat = new SimpleDateFormat("dd/MM/yyyy");

        return shortformat.format(dateConverted);
    }
}
