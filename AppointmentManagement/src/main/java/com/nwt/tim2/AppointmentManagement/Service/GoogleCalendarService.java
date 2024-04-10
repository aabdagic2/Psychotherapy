package com.nwt.tim2.AppointmentManagement.Service;
import com.google.api.client.auth.oauth2.AuthorizationCodeTokenRequest;
import com.google.api.client.auth.oauth2.TokenResponse;
import com.google.api.client.http.GenericUrl;
import com.google.api.client.http.javanet.NetHttpTransport;
import com.google.api.client.json.gson.GsonFactory;
import com.google.api.client.util.DateTime;
import com.google.api.services.calendar.Calendar;
import com.google.api.services.calendar.model.*;
import com.nwt.tim2.AppointmentManagement.Configuration.GoogleConfiguration;
import lombok.AllArgsConstructor;
import lombok.val;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;

import java.io.IOException;
import java.time.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

@Controller
@AllArgsConstructor
public class GoogleCalendarService {
    private final Logger logger = LoggerFactory.getLogger(GoogleCalendarService.class);
    private final GoogleConfiguration config;

    public Optional<TokenResponse> authorize(String code) {
        System.out.println(code);
        val scopes = new ArrayList<String>();
        scopes.add("https://www.googleapis.com/auth/calendar");
        try {
            return Optional.of(new AuthorizationCodeTokenRequest(new NetHttpTransport(), new GsonFactory(),
                    new GenericUrl("https://oauth2.googleapis.com/token"), code)
                    .setRedirectUri(config.getRedirectUri())
                    .setCode(code)
                    .setScopes(scopes)
                    .set("client_id",config.getClientId())
                    .set("client_secret",config.getClientSecret())
                    .set("project_id",config.getProjectId())
                    .set("access_type", "offline")
                    .set("prompt", "consent")
                    .execute());
        } catch (IOException e) {
            logger.error("Error while logging in to google",e);
            return Optional.empty();
        }
    }


    public Optional<String> createEntryy(TokenResponse tokenResponse, DayOfWeek sessionDay, LocalTime sessionTime, String selectedPsychologistEmail) {
        try {
            ZoneId psychologistTimeZone = ZoneId.systemDefault();

            ZonedDateTime startDateTime = ZonedDateTime.of(LocalDate.now(), sessionTime, psychologistTimeZone);
            ZonedDateTime endDateTime = startDateTime.plusHours(1);

            Calendar calendar = new Calendar.Builder(new NetHttpTransport(), new GsonFactory(), null)
                    .setApplicationName(config.getProjectId())
                    .setHttpRequestInitializer(request -> {
                        request.getHeaders().setAuthorization("Bearer " + tokenResponse.getAccessToken());
                        request.getHeaders().setContentType("application/json");
                    })
                    .build();
            Event event = new Event();
            event.setSummary("Psychology session");
            EventDateTime start = new EventDateTime().setDateTime(new DateTime(startDateTime.toInstant().toEpochMilli()))
                    .setTimeZone(psychologistTimeZone.toString());
            EventDateTime end = new EventDateTime().setDateTime(new DateTime(endDateTime.toInstant().toEpochMilli()))
                    .setTimeZone(psychologistTimeZone.toString());
            event.setStart(start);
            event.setEnd(end);
            // Set attendees
            List<EventAttendee> attendees = new ArrayList<>();
            EventAttendee a = new EventAttendee().setEmail(selectedPsychologistEmail);
            attendees.add(a);
            event.setAttendees(attendees);
            String[] recurrence = {"RRULE:FREQ=WEEKLY;COUNT=5;BYDAY=" + sessionDay.toString().substring(0, 2)};
            event.setRecurrence(Arrays.asList(recurrence));
            Event createdEvent = calendar.events().insert("primary", event).execute();
            addConferenceToEvent(tokenResponse, createdEvent.getId());

            return Optional.ofNullable(createdEvent.getId());
        } catch (Exception e) {
            logger.error("Could not schedule event", e);
            return Optional.empty();
        }
    }
    public Optional<String> addConferenceToEvent(TokenResponse tokenResponse, String eventId) {
        try {
            val calendar = new Calendar.Builder(new NetHttpTransport(), new GsonFactory(), null)
                    .setApplicationName(config.getProjectId())
                    .setHttpRequestInitializer(request -> {
                        request.getHeaders().setAuthorization("Bearer " + tokenResponse.getAccessToken());
                        request.getHeaders().setContentType("application/json");
                    })
                    .build();

            val eventPatch = new Event();
            val conferenceData = new ConferenceData();
            val createRequest = new CreateConferenceRequest();
            createRequest.setRequestId("sample123");
            val conferenceSolutionKey = new ConferenceSolutionKey();
            conferenceSolutionKey.setType("hangoutsMeet");
            createRequest.setConferenceSolutionKey(conferenceSolutionKey);
            conferenceData.setCreateRequest(createRequest);
            eventPatch.setConferenceData(conferenceData);

            val patchRequest = calendar.events().patch("primary", eventId, eventPatch);
            patchRequest.setSendNotifications(true);
            patchRequest.setConferenceDataVersion(1);
            val updatedEvent = patchRequest.execute();

            return Optional.ofNullable(updatedEvent.getHtmlLink()); // Return the link to the updated event
        } catch (Exception e) {
            logger.error("Could not add conference to event", e);
            return Optional.empty();
        }
    }

}