package com.GroupOfMe.TestApp.ServiceLayer;

import com.GroupOfMe.TestApp.Dao.JasonServiceDao;
import com.GroupOfMe.TestApp.Model.Attendant;
import com.GroupOfMe.TestApp.Model.Meeting;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;

import java.io.IOException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Optional;

@Service
public class MeetingService {
    private final JasonServiceDao serviceDao;

    @Autowired
    public MeetingService(@Qualifier("json") JasonServiceDao serviceDao) {
        this.serviceDao = serviceDao;
    }

    public int createMeeting(Meeting meeting) throws IOException {
        return serviceDao.createMeeting(meeting);
    }

    public Optional<Meeting> deleteMeeting(String name, String responsiblePerson) throws Exception {
        return serviceDao.deleteMeeting(name, responsiblePerson);
    }
    public int addAttendantToMeeting(Attendant attendant) throws IOException {
        return serviceDao.addAttendantToMeeting(attendant);
    }
    public int removeAttendantFromMeeting(String personToRemove, String meetingName) throws Exception {
        return serviceDao.removeAttendantFromMeeting(personToRemove,meetingName);
    }
    public ArrayList<Meeting> filterByDescription(String description) {
        return serviceDao.filterByDescription(description);
    }
    public ArrayList<Meeting> filterByResponsiblePerson(String responsiblePerson) {
        return serviceDao.filterByResponsiblePerson(responsiblePerson);
    }
    public ArrayList<Meeting> filterByCategory(String category) {
        return serviceDao.filterByCategory(category);
    }
    public ArrayList<Meeting> filterByType(String type) {
        return serviceDao.filterByType(type);
    }
    public ArrayList<Meeting> filterByDate(String date) throws ParseException {
        return serviceDao.filterByDate(date);
    }
    public ArrayList<Meeting> filterByDates(String dateStarts, String dateEnds) throws ParseException {
        return serviceDao.filterByDates(dateStarts, dateEnds);
    }
    public ArrayList<Meeting> filterByNumberOfAttendees(String numberOfAttendees) {
        return serviceDao.filterByNumberOfAttendees(numberOfAttendees);
    }

}
