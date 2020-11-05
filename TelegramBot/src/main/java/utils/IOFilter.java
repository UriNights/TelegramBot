package utils;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

public class IOFilter {

	public static LocalDateTime dateAndTimeFormatter(String dateAndTime) {

		if (dateAndTime.matches("[0-9]{1,4}(-[0-9]{1,2}){2} [0-9]{1,2}(:[0-9]{1,2}){2}")) {

			String[] splitedDateAndTime = dateAndTime.split(" ");

			// ISO_LOCAL_DATE -> '2011-12-03'
			// ISO_LOCAL_TIME -> '10:15:30'

			try {
				LocalDate date = LocalDate.parse(splitedDateAndTime[0], DateTimeFormatter.ISO_LOCAL_DATE);
				LocalTime time = LocalTime.parse(splitedDateAndTime[1], DateTimeFormatter.ISO_LOCAL_TIME);
				LocalDateTime dateAndTimeFormatted = LocalDateTime.of(date, time);

				return dateAndTimeFormatted;
						
			} catch (DateTimeParseException e) {
				return null;
			}
		}
		
		return null;
	}
}
