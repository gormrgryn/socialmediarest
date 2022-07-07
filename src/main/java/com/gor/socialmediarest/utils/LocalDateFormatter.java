package com.gor.socialmediarest.utils;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.time.DateTimeException;

import org.springframework.stereotype.Component;

@Component
public class LocalDateFormatter {
	private DateTimeFormatter formatter;

	public LocalDate parse(String date) throws DateTimeParseException {
        return LocalDate.parse(date, formatter);
	}

	public String format(LocalDate date) throws DateTimeException {
		return date.format(formatter);
	}

	public LocalDateFormatter() {
		formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
	}
}
