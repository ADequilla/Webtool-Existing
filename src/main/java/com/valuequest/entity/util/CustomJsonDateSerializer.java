/**
 * 
 */
package com.valuequest.entity.util;

import java.io.IOException;
import java.util.Date;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.valuequest.util.Constantas;

public class CustomJsonDateSerializer extends JsonSerializer<Date>
{
    @Override
    public void serialize(Date date, JsonGenerator jgen, SerializerProvider sp) throws IOException, JsonProcessingException {
    	
        jgen.writeString(Constantas.fdate.format(date).toUpperCase());
    }

	
}
