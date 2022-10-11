package com.GroupOfMe.TestApp;

import com.GroupOfMe.TestApp.Model.Meeting;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.io.File;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

@SpringBootTest
class TestAppApplicationTests {

	private final String path = "./src/main/java/com/GroupOfMe/TestApp/Json/Meetings.json";
	public final SimpleDateFormat formatter=new SimpleDateFormat("yyyy-MM-dd");
	private final ObjectMapper mapper = new ObjectMapper();
	private List<Meeting> existingMeetings = new ArrayList<>(List.of(mapper.readValue(
			new File(path),
			Meeting[].class)));

	TestAppApplicationTests() throws IOException {
	}

	@Test
	void ReadingFromJson() {
		existingMeetings.forEach(s -> System.out.println("forEach: " + s.endDate()));
		existingMeetings.forEach(s -> s.listOfAttendants().forEach(attendant ->System.out.println(attendant.time())));
	}


	@Test
	void filterByNumberOfAttendees() {
		int intNumberOfAttendees = Integer.parseInt("2");
		ArrayList<Meeting> meetingsBiggerThan = existingMeetings.stream()
				.filter(meeting -> (meeting.listOfAttendants().size() > intNumberOfAttendees))
				.collect(Collectors.toCollection(ArrayList::new));
		meetingsBiggerThan.forEach(meet->System.out.println(meet.name()));
		assertThat(meetingsBiggerThan.get(0).listOfAttendants().size()).isEqualTo(3);
	}

	@Test
	void filterByDescription()  {
		String description = "cript";
		List<Meeting> filteredByDescription = existingMeetings.stream()
				.filter(meeting -> { if (meeting.description().contains(description)) {
					System.out.println("true : " + meeting.description()+" indexOf "+ meeting.description().indexOf(description));
					return true; }
				else { System.out.println("false : " + meeting.description()+" indexOf "+ meeting.description().indexOf(description));
					return false; }
									})
				.collect(Collectors.toList());

		filteredByDescription.forEach(meeting -> System.out.println(meeting.description()));
		assertThat(filteredByDescription.size()).isEqualTo(4);
	}

	@Test
	void filterByDates() throws ParseException {
		Date startDateObj = formatter.parse("2022-03-04");
		Date endDateObj = formatter.parse("2022-12-04");
		ArrayList<Meeting> meetingsBetween = existingMeetings.stream()
				.filter(meeting -> (meeting.startDate().after(startDateObj) || meeting.startDate().equals(startDateObj))
						&& (meeting.endDate().before(endDateObj) || meeting.endDate().equals(startDateObj)))
				.collect(Collectors.toCollection(ArrayList::new));

		meetingsBetween.forEach(meeting -> System.out.println(meeting.startDate()+"   "+meeting.endDate()));
		assertThat(meetingsBetween.size()).isEqualTo(4);
	}
	@Test
	 void filterByType() {
		Meeting.Type type = Meeting.Type.valueOf("Live");
		ArrayList<Meeting> meetingByType = existingMeetings.stream()
				.filter(meeting -> {
					return meeting.type().equals(type);
				})
				.collect(Collectors.toCollection(ArrayList::new));
		meetingByType.forEach(meeting -> System.out.println(meeting.type()));
	}
	@Test
	 void filterByResponsiblePerson() {
		String responsiblePerson = "responsiblePerson1";
		ArrayList<Meeting> listByResponsiblePerson = existingMeetings.stream()
				.filter(meeting -> {
					return meeting.responsiblePerson().equals(responsiblePerson);
				})
				.collect(Collectors.toCollection(ArrayList::new));
		listByResponsiblePerson.forEach(meeting -> System.out.println(meeting.responsiblePerson()));
		assertThat(listByResponsiblePerson.get(0).responsiblePerson()).isEqualTo(responsiblePerson);
	}
}
