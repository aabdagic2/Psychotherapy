package com.nwt.tim2.AppointmentManagement.Controllers;
import com.nwt.tim2.AppointmentManagement.Service.*;
import com.google.api.client.auth.oauth2.TokenResponse;
import com.google.api.services.calendar.CalendarScopes;
import com.nwt.tim2.AppointmentManagement.Configuration.GoogleConfiguration;
import com.nwt.tim2.AppointmentManagement.Service.GoogleCalendarService;
import com.nwt.tim2.AppointmentManagement.Service.StateService;
import lombok.AllArgsConstructor;
import lombok.val;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.beans.factory.annotation.Value;

import java.time.DayOfWeek;
import java.time.LocalTime;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@RestController
@AllArgsConstructor
@RequestMapping("/route")
public class RouteController {

    private final GoogleConfiguration configuration;
    private final StateService state;
    private final GoogleCalendarService calendarService;

    @GetMapping("/")
    public Map<String, Object> index() {
        Map<String, Object> model = new HashMap<>();
        if (state.token.isPresent()) {
            TokenResponse tokenResponse = state.token.get();
            DayOfWeek sessionDay = DayOfWeek.MONDAY;
            LocalTime sessionTime = LocalTime.of(10, 0);
            String selectedPsychologistEmail = "aminaa.abdagic@gmail.com";

            Optional<String> entriesOpt =calendarService.createEntryy(tokenResponse, sessionDay, sessionTime, selectedPsychologistEmail);

            if(entriesOpt.isEmpty()) {
                model.put("error", "Could not load calendar entries");
                model.put("viewName", "error");
            } else {
                val entries = entriesOpt.get();
                model.put("entries", entries);
                model.put("viewName", "index");
            }
        } else {
            val googleUrl = String.format("https://accounts.google.com/o/oauth2/auth?client_id=%s&redirect_uri=%s&scope=%s&response_type=code&access_type=offline",
                    configuration.getClientId(),
                    configuration.getRedirectUri(),
                    CalendarScopes.CALENDAR);
            model.put("googleUrl", googleUrl);
            model.put("viewName", "login");
        }
        return model;
    }


    @GetMapping("/token")
    public void token(@RequestParam String code, @RequestParam String scope) {
        // Your logic to handle the code and scope parameters
        // For example:
        val tokenOpt = calendarService.authorize(code);
        if(tokenOpt.isEmpty()) {

            throw new IllegalStateException("There is no token available");
        }
        val token = tokenOpt.get();
        state.token = Optional.of(token);
    }
}
