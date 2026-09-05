package com.hutch9910.chessboardPatterns;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class ChessboardPatternsApplication {

	public static void main(String[] args) {
		if (System.getProperty("java.awt.headless") == null) {
			System.setProperty("java.awt.headless", "false");
		}
		SpringApplication.run(ChessboardPatternsApplication.class, args);
	}

}
