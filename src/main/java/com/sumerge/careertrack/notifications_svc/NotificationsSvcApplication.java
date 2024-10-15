package com.sumerge.careertrack.notifications_svc;

import com.sumerge.careertrack.notifications_svc.entities.*;
import com.sumerge.careertrack.notifications_svc.entities.enums.ActionEnum;
import com.sumerge.careertrack.notifications_svc.entities.enums.EntityTypeEnum;
import com.sumerge.careertrack.notifications_svc.repositories.ActionsRepository;
import com.sumerge.careertrack.notifications_svc.repositories.EntitiesTypeRepository;
import com.sumerge.careertrack.notifications_svc.repositories.NotificationDataRepository;
import com.sumerge.careertrack.notifications_svc.repositories.NotificationRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;


import java.util.Date;
import java.util.UUID;

@SpringBootApplication(scanBasePackages = "com.sumerge.careertrack.notifications_svc")
public class NotificationsSvcApplication {

	public static void main(String[] args) {
		SpringApplication.run(NotificationsSvcApplication.class, args);
	}



//
//@Bean
//public CommandLineRunner testEntities(ActionsRepository actionsRepository,
//									  EntitiesTypeRepository entitiesTypeRepository,
//									  NotificationRepository notificationRepository,
//									  NotificationDataRepository notificationDataRepository) {
//
//	return args -> {
//		// Step 1: Create and persist an Action
//		Actions action = new Actions(UUID.randomUUID(), ActionEnum.APPROVAL);
//		actionsRepository.save(action);
//		Actions action1 = new Actions(UUID.randomUUID(), ActionEnum.REJECTION);
//		actionsRepository.save(action1);
//		Actions action2 = new Actions(UUID.randomUUID(), ActionEnum.SUBMISSION);
//		actionsRepository.save(action2);
//
//		// Step 2: Create and persist an EntitiesType
//		EntitiesType entitiesType = new EntitiesType(EntityTypeEnum.LEARNING);
//		entitiesTypeRepository.save(entitiesType);
//		EntitiesType entitiesType1 = new EntitiesType(EntityTypeEnum.NOTIFICATION);
//		entitiesTypeRepository.save(entitiesType1);
//		EntitiesType entitiesType2 = new EntitiesType(EntityTypeEnum.WIKI);
//		entitiesTypeRepository.save(entitiesType2);
//		EntitiesType entitiesType3 = new EntitiesType(EntityTypeEnum.BLOG);
//		entitiesTypeRepository.save(entitiesType3);
//
//
//	};
//}

}
