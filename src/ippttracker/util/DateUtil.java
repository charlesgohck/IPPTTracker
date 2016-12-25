/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ippttracker.util;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

/**
 *
 * @author charlesgoh
 * Referenced from code.makery.ch javafx 8 tutorial
 */
public class DateUtil {
    //Date pattern used for conversion
    private static final String DATE_PATTERN = "dd/MM/yyyy";
    
    //Date formatter
    private static final DateTimeFormatter DATE_FORMATTER = DateTimeFormatter.ofPattern(DATE_PATTERN);
    
    public static String format(LocalDate date) {
        if (date == null) {
            return null;
        } else {
            return DATE_FORMATTER.format(date);
        }
    }
    
    //Converts a String in the DATE_PATTERN format to LocalDate object
    public static LocalDate parse(String dateString) {
        try {
            return DATE_FORMATTER.parse(dateString, LocalDate::from);
        } catch (DateTimeParseException e) {
            return null;
        }
    }
    
    //Checks the String whether it is a valid date
    public static boolean validDate(String dateString) {
        //Try to parse the String.
        return DateUtil.parse(dateString) != null;
    }
    
}
