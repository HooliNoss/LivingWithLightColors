package ch.hslu.livingWithLightColors.DataAccess;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.logging.Formatter;
import java.util.logging.LogRecord;

/**
 * This class formats log messages for the console.
 * @author Curdin Banzer
 *
 */
public class ConsoleFormatter extends Formatter {
	
	private static final DateFormat dateFormat = new SimpleDateFormat("HH:mm:ss");
	private static final String elementSeparator = " | ";
	private static final String lineSeparator = "\n";
	private static final String messageSeparator = " --> ";

	@Override
	public String format(LogRecord record) {
		String loggerName = record.getLoggerName();

		StringBuilder logEntry = new StringBuilder()
			.append(dateFormat.format(new Date(record.getMillis())))	
			.append(elementSeparator)
			.append(record.getLevel())
			.append(elementSeparator)
			.append(Thread.currentThread().getName())
			.append(elementSeparator)
			.append(loggerName)
			.append(messageSeparator)
			.append(record.getMessage())
			.append(lineSeparator);
		return logEntry.toString();		
	}

}
