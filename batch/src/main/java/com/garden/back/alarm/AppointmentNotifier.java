package com.garden.back.alarm;

import com.garden.back.appointment.domain.Appointment;
import com.garden.back.appointment.domain.AppointmentRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.List;

@Service
@Slf4j
public class AppointmentNotifier {

    private final AppointmentRepository appointmentRepository;

    public AppointmentNotifier(AppointmentRepository appointmentRepository) {
        this.appointmentRepository = appointmentRepository;
    }

    @Scheduled(fixedRate = 1000 * 60)
    public void sendAlertToAppointmentUsers() {
        LocalDateTime now = ZonedDateTime.now(ZoneId.of("Asia/Seoul")).toLocalDateTime().withSecond(0).withNano(0);
        List<Appointment> appointmentList = appointmentRepository.findAllByAlertTime(now);
        log.info("현재시간: {}", now);
        appointmentList.parallelStream()
            .forEach((appointment -> {
                log.info("{} 사용자에게 알람 보내기", appointment.getAssignerId());
                log.info("{} 사용자에게 알람 보내기", appointment.getReceiverId());
            }));
    }
}
