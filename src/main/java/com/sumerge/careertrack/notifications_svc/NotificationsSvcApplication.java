package com.sumerge.careertrack.notifications_svc;

import com.sumerge.careertrack.notifications_svc.entities.Notification;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.kafka.core.KafkaTemplate;

import java.util.UUID;

@SpringBootApplication
public class NotificationsSvcApplication {

	public static void main(String[] args) {
		SpringApplication.run(NotificationsSvcApplication.class, args);
	}

//	@Bean
//	CommandLineRunner runner(KafkaTemplate<String, Notification> kafkaTemplate) {
//		UUID uuid = UUID.randomUUID();
//		System.out.println("UUID: " + uuid);
//		return args -> {
//			kafkaTemplate.send("notification", new Notification("1st Notification!", uuid, false)).get();
//			kafkaTemplate.flush();
//		};
//	}

}
