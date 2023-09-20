package com.gbadegesin.urlgen.utils;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;
import java.util.UUID;
import java.util.stream.Collectors;


public class StringUtils {

    /**
     * checks if a string is empty, null or blank
     * @param term: the string to check against
     * @return the boolean response
     */
    public static boolean isEmptyBlank(String term){
        return term == null || term.isEmpty();
    }




    /**
     * Converts exception stacktrace to string
     * @param e: the exception
     * @return the string stacktrace
     */
    public static String stackTraceToString(Exception e){
        StringWriter stringWriter = new StringWriter();
        PrintWriter printWriter = new PrintWriter(stringWriter);
        e.printStackTrace(printWriter);

        return stringWriter.toString();
    }


    /**
     * Converts a string to toggle case
     * @param string: the string
     * @return the toggle cased string
     */
    public static String convertStringToToggleCase(String string){
        string = string.toLowerCase();

        return Arrays.stream(string.split(" ")).map(s -> {
            String lead = String.valueOf(s.charAt(0));
            return s.replaceFirst(lead, lead.toUpperCase());
        }).collect(Collectors.joining(" "));
    }


    /**
     * Generates uuid
     * @param removeSeparator: flag to remove (-) separator in uuid
     * @return the uuid
     */
    public static String generateUUID(boolean removeSeparator){
        if(removeSeparator)
            return UUID.randomUUID().toString().replaceAll("-", "").toUpperCase();
        else
            return UUID.randomUUID().toString();
    }

    public static String getCurrDateRef() {
        SimpleDateFormat sdf = new SimpleDateFormat("yyMMdd");
        return sdf.format(new Date());
    }

    public static String generateUrl() {

        return generateUUID(true).substring(0, 5);
    }
}
