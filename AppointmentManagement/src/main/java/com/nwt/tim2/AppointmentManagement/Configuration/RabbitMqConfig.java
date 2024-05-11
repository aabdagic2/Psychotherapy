package com.nwt.tim2.AppointmentManagement.Configuration;

import org.springframework.amqp.core.*;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.beans.factory.annotation.Qualifier;

@Configuration
public class RabbitMqConfig {

    public static final String EXCHANGE_NAME = "patient-exchange";
    public static final String PSYCHOLOGIST_EXCHANGE_NAME= "psychologist-exchange";
    public static final String PATIENT_QUEUE = "patient-queue";
    public static final String ROUTING_KEY = "patient";

    public static final String PSYCHOLOGIST_QUEUE = "psychologist-queue";

    @Bean
    public Queue patientQueue() {
        return new Queue(PATIENT_QUEUE, true);
    }

    @Bean
    public Queue psychologistQueue() {
        return new Queue(PSYCHOLOGIST_QUEUE, true);
    }

    @Bean
    public TopicExchange exchange() {
        return new TopicExchange(EXCHANGE_NAME);
    }
    @Bean
    public TopicExchange exchangeRegisterPsychologist() {
        return new TopicExchange(PSYCHOLOGIST_EXCHANGE_NAME);
    }
    @Bean
    public Binding binding(@Qualifier("patientQueue") Queue patientQueue) {
        return BindingBuilder.bind(patientQueue).to(exchange()).with(PATIENT_QUEUE);
    }
    @Bean
    public Binding psychologistBinding(@Qualifier("psychologistQueue") Queue psychologistQueue) {
        return BindingBuilder.bind(psychologistQueue).to(exchangeRegisterPsychologist()).with(PSYCHOLOGIST_QUEUE);
    }
    @Bean
    public Jackson2JsonMessageConverter messageConverter() {
        return new Jackson2JsonMessageConverter();
    }

    @Bean
    public RabbitTemplate rabbitTemplate(ConnectionFactory connectionFactory, Jackson2JsonMessageConverter messageConverter) {
        RabbitTemplate rabbitTemplate = new RabbitTemplate(connectionFactory);
        rabbitTemplate.setMessageConverter(messageConverter);
        return rabbitTemplate;
    }
}
