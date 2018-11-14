package com.spl.lab;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class StvjsdkApplication {

	public static void main(String[] args) {
		SpringApplication.run(StvjsdkApplication.class, args);
		Service.WakeOnWirelessLan("d8:e0:e1:92:36:83");
//		Service.WakeOnWirelessLan("CC:6E:A4:D0:B8:CC");
	}
}
