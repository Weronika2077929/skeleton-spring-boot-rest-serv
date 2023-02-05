package com.firstClientServer.firstApp;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.Bean;
import org.springframework.scheduling.annotation.EnableScheduling;

import java.time.Clock;
import java.time.ZoneId;

@SpringBootApplication
@EnableFeignClients
@EnableScheduling
@Slf4j
public class FirstAppApplication {

	public static void main(String[] args) {
		SpringApplication.run(FirstAppApplication.class, args);
	}

	@Bean
	public Clock clock() {
		return Clock.system(ZoneId.of("UTC"));
	}

//	@PostConstruct
//	private void postInit(){
//		System.out.println("All available tracks: " + trackRepo.findAll());
//	}

//	@Bean
//	public CommandLineRunner repoDemo(TrackRepository trackRepo) {
//		return (args) -> {
//			trackRepo.save(new TrackEntity(1l, "Video Killed The Radio Star","The Buggles", 1980));
//			trackRepo.save(new TrackEntity(2l, "Wish I didn't Miss You","Angie Stone", 2002));
//			trackRepo.save(new TrackEntity(3l, "Sing It Back","Moloko", 1998));
//
//			for( TrackEntity track : trackRepo.findAll()) {
//				log.info("The track is:" + track.toString());
//			}
//		};
//	}

}
