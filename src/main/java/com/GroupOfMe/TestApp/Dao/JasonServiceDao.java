package com.GroupOfMe.TestApp.Dao;

import com.GroupOfMe.TestApp.Model.Attendant;
import com.GroupOfMe.TestApp.Model.Meeting;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.io.File;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.stream.Collectors;

@Repository("json")
public class JasonServiceDao implements MeetingDao {

    private final String path = "./src/main/java/com/GroupOfMe/TestApp/Json/Meetings.json";
    public final SimpleDateFormat formatter=new SimpleDateFormat("yyyy-MM-dd");
    private final ObjectMapper mapper = new ObjectMapper().enable(SerializationFeature.INDENT_OUTPUT);
    public List<Meeting> existingMeetings=new ArrayList<>();

    // --- constructor
    public JasonServiceDao() {
        mapper.registerModule(new JavaTimeModule());
        //mapper.setDateFormat(new SimpleDateFormat("yyyy-MM-dd HH:mm"));
        try {
            List<Meeting> immutableList = new ArrayList<>(List.of(mapper.readerFor(Meeting.class).readValue(
                    new File(path),
                    Meeting[].class)));
            existingMeetings.addAll(immutableList);

        } catch (IOException e) {
            System.out.println("!!! Exception in initialization of existing meetings !!! ");
            e.printStackTrace();
        }
    }

    //---- methods
    @Override
    public int createMeeting(Meeting meeting) throws IOException {

            if (existingMeetings.isEmpty()) {
                mapper.writeValue(new File(path), meeting);
            }
            else {
                existingMeetings.add(meeting);
                mapper.writeValue(new File(path), existingMeetings);
            }
        return 1;
    }

    @Override
    public Optional<Meeting> deleteMeeting(String name, String responsiblePerson) throws Exception {

        Optional<Meeting> MeetingName = existingMeetings
                .stream()
                .filter(meeting -> meeting.name().equals(name) && meeting.responsiblePerson().equals(responsiblePerson))
                .findFirst();
        if (MeetingName.isEmpty()) {
            throw new Exception("No matching meeting name was found or person trying to delete is not responsible for the meeting");
        }
        existingMeetings.remove(MeetingName.get());
        mapper.writeValue(new File(path), existingMeetings);
        return MeetingName;
    }

    @Override
    public int addAttendantToMeeting(Attendant attendant) throws IOException {
        Optional<Meeting> meetingsAtThatTime = existingMeetings.stream()
                .filter(meeting -> (meeting.startDate().before(attendant.time()) || meeting.startDate().equals(attendant.time()))
                        && meeting.endDate().after(attendant.time()))
                .findAny();
        if (meetingsAtThatTime.isEmpty()) {
            System.out.println("There are no meetings at that time ");
            return 0; }
        else { Optional<Attendant> attendantToAdd = meetingsAtThatTime.stream()
                .flatMap(meeting -> meeting.listOfAttendants().stream())
                .filter(attendantAtThatTime -> attendantAtThatTime.name().equals(attendant.name()))
                .findAny();
                if (attendantToAdd.isEmpty()) {
                    int requiredMeeting = existingMeetings.indexOf(meetingsAtThatTime.get());
                    existingMeetings.get(requiredMeeting).addAttendantToList(attendant);
                    mapper.writeValue(new File(path), existingMeetings);
                    return 1;}
                else {
                    System.out.println(" Attendant with that name exists in meeting at that time ");
                    return 2;
                }
        }

    }

    @Override
    public int removeAttendantFromMeeting(String personToRemove, String meetingName) throws Exception {
           Optional<Meeting> optionalMeetingName = existingMeetings.stream()
                .filter(meeting -> meeting.name().equals(meetingName))
                .findAny();
           if (optionalMeetingName.isEmpty())
              {System.out.println(" there is no such meeting name ");
               return 0;}
           else
           {int foundMeeting = existingMeetings.indexOf(optionalMeetingName.get());
               existingMeetings.get(foundMeeting).removeAttendantToList(personToRemove);
               mapper.writeValue(new File(path), existingMeetings);
               return 1;
           }
    }

    @Override
    public ArrayList<Meeting> filterByDescription(String description) {
        return existingMeetings.stream()
                .filter(meeting -> {
                    return meeting.description().contains(description);
                })
                .collect(Collectors.toCollection(ArrayList::new));
    }

    @Override
    public ArrayList<Meeting> filterByResponsiblePerson(String responsiblePerson) {
        return existingMeetings.stream()
                .filter(meeting -> {
                    return meeting.responsiblePerson().equals(responsiblePerson);
                })
                .collect(Collectors.toCollection(ArrayList::new));
    }

    @Override
    public ArrayList<Meeting> filterByCategory(String category) {
        Meeting.Category enumCategory = Meeting.Category.valueOf(category);
        return existingMeetings.stream()
                .filter(meeting -> {
                    return meeting.category().equals(enumCategory);
                })
                .collect(Collectors.toCollection(ArrayList::new));
    }

    @Override
    public ArrayList<Meeting> filterByType(String type) {
        Meeting.Type enumType = Meeting.Type.valueOf(type);
        return existingMeetings.stream()
                .filter(meeting -> {
                    return meeting.type().equals(enumType);
                })
                .collect(Collectors.toCollection(ArrayList::new));
    }

    @Override
    public ArrayList<Meeting> filterByDate(String date) throws ParseException {
        Date dateObj = formatter.parse(date);
        return existingMeetings.stream()
                .filter(meeting -> (meeting.startDate().after(dateObj) || meeting.startDate().equals(dateObj)))
                .collect(Collectors.toCollection(ArrayList::new));
    }

    @Override
    public ArrayList<Meeting> filterByDates(String dateStarts, String dateEnds) throws ParseException {
        Date startDateObj = formatter.parse(dateStarts);
        Date endDateObj = formatter.parse(dateEnds);
        return existingMeetings.stream()
                .filter(meeting -> (meeting.startDate().after(startDateObj) || meeting.startDate().equals(startDateObj))
                        && (meeting.endDate().before(endDateObj) || meeting.endDate().equals(startDateObj)))
                .collect(Collectors.toCollection(ArrayList::new));
    }

    @Override
    public ArrayList<Meeting> filterByNumberOfAttendees(String numberOfAttendees) {
        int intNumberOfAttendees = Integer.parseInt(numberOfAttendees);
        return existingMeetings.stream()
                .filter(meeting -> (meeting.listOfAttendants().size() > intNumberOfAttendees))
                .collect(Collectors.toCollection(ArrayList::new));
    }
}
