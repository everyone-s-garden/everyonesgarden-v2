package com.garden.back.appointment;

import com.garden.back.appointment.request.AppointRequest;
import com.garden.back.appointment.service.AppointmentService;
import com.garden.back.appointment.service.response.AppointmentResponse;
import com.garden.back.global.loginuser.CurrentUser;
import com.garden.back.global.loginuser.LoginUser;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/v1/appointments")
public class AppointmentController {

    private final AppointmentService appointmentService;

    public AppointmentController(AppointmentService appointmentService) {
        this.appointmentService = appointmentService;
    }

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    ResponseEntity<AppointmentResponse> appoint(
        @RequestBody @Valid AppointRequest request,
        @CurrentUser LoginUser loginUser
    ) {
        return new ResponseEntity<>(appointmentService.appoint(request.toServiceRequest(loginUser.memberId())), HttpStatus.CREATED);
    }
}
