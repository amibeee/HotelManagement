package com.example.hotelmanagement;

import com.example.hotelmanagement.service.EmailService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.annotation.Bean;
import org.springframework.context.event.EventListener;

@SpringBootApplication
public class HotelmanagementApplication {

    @Autowired
    private EmailService senderService;

    public static void main(String[] args) {
        SpringApplication.run(HotelmanagementApplication.class, args);
    }

    @Bean
    public ModelMapper modelMapper() {
        return new ModelMapper();
    }

    @EventListener(ApplicationReadyEvent.class)
    public void sendMail(){
        senderService.sendEmail("nawresabbes60@gmail.com",
                "This is Subject",
                "this is Body of Emails");
    }

}
