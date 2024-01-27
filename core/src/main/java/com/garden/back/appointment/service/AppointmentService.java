package com.garden.back.appointment.service;

import com.garden.back.appointment.domain.AppointmentRepository;
import com.garden.back.appointment.service.response.AppointmentResponse;
import org.springframework.stereotype.Service;

@Service
public class AppointmentService {

    private final AppointmentRepository appointmentRepository;

    public AppointmentService(AppointmentRepository appointmentRepository) {
        this.appointmentRepository = appointmentRepository;
    }

    public AppointmentResponse appoint(AppointmentServiceRequest request) {
        return AppointmentResponse.from(appointmentRepository.save(request.toEntity()));
    }
}
