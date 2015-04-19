package edu.spsu.swe2313.group7.library.model.serializers;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.TimeZone;

/**
 *
 * @author Robert Whitaker
 */
public class JsonDateSerializer extends JsonSerializer<Date> {

	private static final ThreadLocal<SimpleDateFormat> dateFormat = new ThreadLocal<SimpleDateFormat>() {
		@Override
		protected SimpleDateFormat initialValue() {
			SimpleDateFormat gmt = new SimpleDateFormat("MM-dd-yyyy");
			// Setting the timezone on the serializer forces the output string
			// to always be in GMT. Having a consistent timezone across
			// different messages is not required, but makes them easier for
			// humans to read and compare.
			gmt.setTimeZone(TimeZone.getTimeZone("GMT"));
			return gmt;
		}
	};

	@Override
	public void serialize(Date date, JsonGenerator gen, SerializerProvider sp) throws IOException, JsonProcessingException {
		String formattedDate = dateFormat.get().format(date);
		gen.writeString(formattedDate);
	}
}

