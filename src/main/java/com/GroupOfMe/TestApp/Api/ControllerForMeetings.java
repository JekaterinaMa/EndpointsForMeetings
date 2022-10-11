package com.GroupOfMe.TestApp.Api;

import com.GroupOfMe.TestApp.Model.Attendant;
import com.GroupOfMe.TestApp.Model.Meeting;
import com.GroupOfMe.TestApp.ServiceLayer.MeetingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RequestMapping("api/meeting")
@RestController
public class ControllerForMeetings {

    private final MeetingService meetingService;

    @Autowired
    public ControllerForMeetings(MeetingService meetingService) {
        this.meetingService = meetingService;
    }

    @PostMapping
    public String createMeeting(@RequestBody Meeting meeting) throws IOException {
        meetingService.createMeeting(meeting);
        return "You have created meeting";
    }

    @DeleteMapping(path="{name}/{responsiblePerson}")
    public Optional<Meeting> deleteMeeting(@PathVariable String name, @PathVariable String responsiblePerson) throws Exception {
        return meetingService.deleteMeeting(name, responsiblePerson);
    }

    @PutMapping(path = "add")
    public String addAttendantToMeeting(@RequestBody Attendant attendant) throws IOException {
        int stateSwitch = meetingService.addAttendantToMeeting(attendant);
        String warning = new String();
        switch (stateSwitch) {
            case 0:
                 warning = " There are no meetings at that time ";
                break;
            case 1:
                 warning = " Attendant has been added ";
                break;
            case 2:
                 warning = " Attendant with that name exists in meeting at that time ";
                break;
        }
        return warning;
    }

    @PutMapping(path = "remove/{personToRemove}/{meetingName}")
    public int removeAttendantFromMeeting(@PathVariable String personToRemove, @PathVariable String meetingName) throws Exception {
       return meetingService.removeAttendantFromMeeting(personToRemove, meetingName);
    }

    @GetMapping(path="description/{description}")
    public String filterByDescription(@PathVariable String description) {
        ArrayList<Meeting> filteredList = meetingService.filterByDescription(description);
        String SfilteredList = filteredList.stream()
                .map(meeting -> meeting.description())
                .collect(Collectors.joining("<br>","<html>","</html>"));
        return SfilteredList;
    }

    @GetMapping(path="responsiblePerson/{responsiblePerson}")
    public ArrayList<Meeting> filterByResponsiblePerson(@PathVariable String responsiblePerson) {
        return meetingService.filterByResponsiblePerson(responsiblePerson);
    }

    @GetMapping(path="category/{category}")
    public ArrayList<Meeting> filterByCategory(@PathVariable String category) {
        return meetingService.filterByCategory(category);
    }

    @GetMapping(path="type/{type}")
    public ArrayList<Meeting> filterByType(@PathVariable String type) {
        return meetingService.filterByType(type);
    }
    @GetMapping(path="date/{date}")
    public ArrayList<Meeting> filterByDate(@PathVariable String date) throws ParseException {
        return meetingService.filterByDate(date);
    }
    @GetMapping(path="dates/{dateStarts}/{dateEnds}")
    public ArrayList<Meeting> filterByDates(@PathVariable String dateStarts, @PathVariable String dateEnds) throws ParseException {
        return meetingService.filterByDates(dateStarts, dateEnds);
    }
    @GetMapping(path="number/{numberOfAttendees}")
    public ArrayList<Meeting> filterByNumberOfAttendees(@PathVariable String numberOfAttendees) {
        return meetingService.filterByNumberOfAttendees(numberOfAttendees);
    }


}
