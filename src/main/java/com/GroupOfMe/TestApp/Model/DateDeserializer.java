package com.GroupOfMe.TestApp.Model;

import com.fasterxml.jackson.core.JacksonException;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.deser.std.StdDeserializer;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class DateDeserializer extends StdDeserializer<Date> {
    private static SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm");
    public DateDeserializer() {
        this(null);
    }
    public DateDeserializer(Class<Date> t) {
        super(t);
    }

    @Override
    public Date deserialize(JsonParser jsonParser, DeserializationContext deserializationContext)
            throws IOException, JacksonException {
        String date = jsonParser.getText();
        try {
            return formatter.parse(date);
        }
        catch (ParseException e) {
            e.printStackTrace();
        }
        return null;
    }
}
