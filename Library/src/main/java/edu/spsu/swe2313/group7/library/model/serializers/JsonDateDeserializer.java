package edu.spsu.swe2313.group7.library.model.serializers;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import org.apache.log4j.Logger;

public class JsonDateDeserializer extends JsonDeserializer<Date> {

	private static final Logger LOGGER = Logger.getLogger(JsonDateDeserializer.class);

	private static final ThreadLocal<SimpleDateFormat> dateFormatNoTimeZone = new ThreadLocal<SimpleDateFormat>() {
		@Override
		protected SimpleDateFormat initialValue() {
			return new SimpleDateFormat("yyyy-MM-dd");
		}
	};

	private static final ThreadLocal<SimpleDateFormat> dateFormatRFC822TimeZone = new ThreadLocal<SimpleDateFormat>() {
		@Override
		protected SimpleDateFormat initialValue() {
			return new SimpleDateFormat("yyyy-MM-dd");
		}
	};

	@Override
	public Date deserialize(JsonParser parser, DeserializationContext context) throws IOException, JsonProcessingException {
		return this.deserialize(parser.getText());
	}

	public Date deserialize(String date) {
		try {
			// now we put an RFC822 timezone on it, so try that first
			Date result = dateFormatRFC822TimeZone.get().parse(date, new java.text.ParsePosition(0));
			if (result != null) {
				return result;
			}
			// but originally we did not have a timezone on it, so try that too
			result = dateFormatNoTimeZone.get().parse(date, new java.text.ParsePosition(0));
			if (result != null) {
				return result;
			}
		} catch (Exception e) {
			LOGGER.error("exception parsing date '" + date + "'", e);
		}
		LOGGER.warn("unable to parse date '" + date + "'");
		return null;
	}
};
