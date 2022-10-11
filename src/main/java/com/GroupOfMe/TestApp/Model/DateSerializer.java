package com.GroupOfMe.TestApp.Model;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.ser.std.StdSerializer;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

class DateSerializer extends StdSerializer<Date> {

    private static SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm");

    public DateSerializer() {
        this(null);
    }
    public DateSerializer(Class<Date> t) {
        super(t);
    }

    @Override
    public void serialize(Date value, JsonGenerator generator, SerializerProvider arg2)
            throws IOException {
        generator.writeString(formatter.format(value));
    }
}