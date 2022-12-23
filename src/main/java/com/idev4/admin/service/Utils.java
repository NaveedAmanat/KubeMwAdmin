package com.idev4.admin.service;

import org.springframework.stereotype.Component;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.Instant;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.Locale;

@Component
public class Utils {

    public Instant parseStringDateToInstant(String input) {
        Date date = null;
        try {
            date = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'").parse(input);
        } catch (ParseException e) {
            System.out.println(e);
        }
        return date.toInstant();
    }

    public Date parseStringDateToDate(String input) {
        Date date = null;
        try {
            date = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'").parse(input);
        } catch (ParseException e) {
            System.out.println(e);
        }
        return date;
    }

    public String parseDatetoLocalDate(String input) {

        DateTimeFormatter inputFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'", Locale.ENGLISH);
        DateTimeFormatter outputFormatter = DateTimeFormatter.ofPattern("dd-MMM-yyyy", Locale.ENGLISH);
        LocalDate date = LocalDate.parse(input, inputFormatter);
        String formattedDate = outputFormatter.format(date);
        return formattedDate;
    }

    // Added by Zohaib Asim - Dated 14-01-2021
    public String parseDatetoLocalDate(CharSequence input) {
        DateTimeFormatter inputFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'", Locale.ENGLISH);
        DateTimeFormatter outputFormatter = DateTimeFormatter.ofPattern("dd-MM-yyy", Locale.ENGLISH);
        LocalDate date = LocalDate.parse(input, inputFormatter);
        String formattedDate = outputFormatter.format(date);
        //System.out.println(formattedDate); // prints 10-04-2018

        return formattedDate;
    }
    // End by Zohaib Asim
}
