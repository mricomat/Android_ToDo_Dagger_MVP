package com.mricomat.todo_dagger_mvp_example.utils;

import java.text.DecimalFormat;

/**
 * Created by mricomat on 22/03/2018.
 */

public class StringsUtils {

    public static final String SPACE = " ";
    public static final String DOUBLE_SPACE = "  ";
    public static final String COLON = ":";
    public static final String TWO_ZEROS_FORMAT = "00";
    public static final String CONCAT_STRINGS = "%s:%s";

    public static String capitalizeFirstLetter(String str) {
        return str.substring(0, 1).toUpperCase() + str.substring(1);
    }

    public static String formatForTwoDigits(int number) {
        DecimalFormat df = new DecimalFormat(TWO_ZEROS_FORMAT);
        return df.format(number);
    }

    public static boolean isEmpty(String str) {
        return str == null || str.length() == 0;
    }
}
