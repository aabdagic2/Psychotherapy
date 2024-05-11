package com.management.user.Configuration;

import org.springframework.amqp.core.*;
import
        org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.amqp.support.converter.MessageConverter;
import org.springframework.context.annotation.Bean;

import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RabbitMqConfig {

    public static final String EXCHANGE_NAME = "patient-exchange";

    public static final String QUEUE_NAME = "patient-queue";
    public static final String PSYCHOLOGIST_QUEUE_NAME="psychologist-queue";
    public static final String PSYCHOLOGIST_EXCHANGE_NAME= "psychologist-exchange";
    public static final String ROUTING_KEY = "patient";
    public static final String PSYCHOLOGIST_ROUTING_KEY = "psychologist";
    @Bean
    public Queue patientQueue() {
        return new Queue(QUEUE_NAME, true);
    }

    @Bean
    public Queue psychologistQueue() {
        return new Queue(PSYCHOLOGIST_QUEUE_NAME, true);
    }

    @Bean
    public TopicExchange patientExchange() {
        return new TopicExchange(EXCHANGE_NAME);
    }

    @Bean
    public TopicExchange psychologistExchange() {
        return new TopicExchange(PSYCHOLOGIST_EXCHANGE_NAME);
    }

    @Bean
    public Binding patientBinding(Queue patientQueue, TopicExchange patientExchange) {
        return BindingBuilder.bind(patientQueue).to(patientExchange).with(ROUTING_KEY);
    }

    @Bean
    public Binding psychologistBinding(Queue psychologistQueue, TopicExchange psychologistExchange) {
        return BindingBuilder.bind(psychologistQueue).to(psychologistExchange).with(PSYCHOLOGIST_ROUTING_KEY);
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
