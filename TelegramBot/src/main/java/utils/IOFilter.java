package utils;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

public class IOFilter {

	public static LocalDate dateFormatter(String date) {

		if (date.matches("[0-9]{1,4}(-[0-9]{1,2}){2}")) {

			// ISO_LOCAL_DATE -> '2011-12-03'

			try {
				LocalDate dateFormatted = LocalDate.parse(date, DateTimeFormatter.ISO_LOCAL_DATE);
				return dateFormatted;
						
			} catch (DateTimeParseException e) {
				return null;
			}
		}
		
		return null;
	}

	public static String firstTwoWords(String textAnswer) {

		String[] splittedText = textAnswer.split(" ");
		
		if (splittedText.length == 1) {
			return textAnswer;
		}
		
		return splittedText[0] + " " + splittedText[1];
	}
}
