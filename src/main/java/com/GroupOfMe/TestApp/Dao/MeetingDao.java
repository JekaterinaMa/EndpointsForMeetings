package com.GroupOfMe.TestApp.Dao;

import com.GroupOfMe.TestApp.Model.Attendant;
import com.GroupOfMe.TestApp.Model.Meeting;

import java.io.IOException;
import java.text.ParseException;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public interface MeetingDao {

    int createMeeting(Meeting meeting) throws IOException;
    Optional<Meeting> deleteMeeting(String name, String responsiblePerson) throws Exception;
    int addAttendantToMeeting(Attendant attendant) throws IOException;
    int removeAttendantFromMeeting(String personToRemove, String meetingName) throws Exception;
    ArrayList<Meeting> filterByDescription(String description);
    ArrayList<Meeting> filterByResponsiblePerson(String responsiblePerson);
    ArrayList<Meeting> filterByCategory(String category);
    ArrayList<Meeting> filterByType(String type);
    ArrayList<Meeting> filterByDate(String date) throws ParseException;
    ArrayList<Meeting> filterByDates(String dateStarts, String dateEnds) throws ParseException;
    ArrayList<Meeting> filterByNumberOfAttendees(String numberOfAttendees);

}
