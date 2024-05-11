package com.nwt.tim2.AppointmentManagement.Rabbitmq;

import com.nwt.tim2.AppointmentManagement.Dtos.PatientDto;
import com.nwt.tim2.AppointmentManagement.Dtos.PsychologistDto;
import com.nwt.tim2.AppointmentManagement.Service.PatientService;
import com.nwt.tim2.AppointmentManagement.Service.PsychologistService;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import com.nwt.tim2.AppointmentManagement.Configuration.RabbitMqConfig;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
@Component
public class RabbitmqConsumer {

    @Autowired
    PatientService patientService;
    @Autowired
    PsychologistService psychologistService;
    @Autowired
    private RabbitTemplate template;

    @RabbitListener(queues = RabbitMqConfig.PATIENT_QUEUE)
    public void receive(PatientDto patient) {
        try{
        patientService.savePatient(patient);}
    catch(Exception e){
        System.out.println("Greska u spašavanju pacijenta!");
        System.out.println(e.getMessage());
    }
    }
    @RabbitListener(queues = RabbitMqConfig.PSYCHOLOGIST_QUEUE)
    public void receive(PsychologistDto psychologistDto) {
        try{
            psychologistService.savePsychologist(psychologistDto);}
        catch(Exception e){
            System.out.println("Greska u spašavanju psihologa!");
            System.out.println(e.getMessage());
        }
    }
}