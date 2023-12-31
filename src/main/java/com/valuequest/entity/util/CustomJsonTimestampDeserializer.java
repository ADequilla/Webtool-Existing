package com.valuequest.entity.util;

import java.io.IOException;
import java.text.ParseException;
import java.util.Date;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.valuequest.util.Constantas;

public class CustomJsonTimestampDeserializer extends JsonDeserializer<Date> {

	@Override
	public Date deserialize(JsonParser parser, DeserializationContext ctx) throws IOException, JsonProcessingException {
		try {
			return Constantas.ftimestamp.parse(parser.getValueAsString());
		} catch (ParseException e) {
			e.printStackTrace();
		}
		
		return null;
	}

}
