package com.example.postservice.configuration;

import org.springframework.amqp.core.*;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.amqp.support.converter.MessageConverter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RabbitMQConfiguration {

    public static final String COMMENT_ROUTING_KEY = "comment-routing-key";
    private static final String LIKES_ROUTING_KEY = "likes-routing-key";

    @Bean
    Queue commentQueue(){
        return new Queue("comment-queue", false);
    }

    @Bean
    Queue likesQueue(){
        return new Queue("likes-queue", false);
    }


    @Bean
    DirectExchange exchange(){
        return new DirectExchange("comment-exchange");
    }

    @Bean
    Binding commentBinding(Queue commentQueue,DirectExchange exchange){
        return BindingBuilder.bind(commentQueue)
                .to(exchange)
                .with(COMMENT_ROUTING_KEY);
    }

    @Bean
    Binding likesBinding(Queue likesQueue,DirectExchange exchange){
        return BindingBuilder.bind(likesQueue)
                .to(exchange)
                .with(LIKES_ROUTING_KEY);
    }

    @Bean
    MessageConverter messageConverter(){
        return new Jackson2JsonMessageConverter();
    }

    @Bean
    AmqpTemplate template(ConnectionFactory factory){
        RabbitTemplate rabbitTemplate= new RabbitTemplate(factory);
        rabbitTemplate.setMessageConverter(messageConverter());
        return rabbitTemplate;
    }
}
