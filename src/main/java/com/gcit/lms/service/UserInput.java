package com.gcit.lms.service;

import java.util.Scanner;

public abstract class UserInput {

	public Integer getInput(String prompt, String exit, Scanner scanner) {

		Integer input = null;

		while (true) {
			System.out.print(prompt);
			if (scanner.hasNextInt()) {
				input = scanner.nextInt();
			} else {
				String checkExit = scanner.next();
				if (checkExit.equalsIgnoreCase(exit)) {
					return -1;
				}
				continue;
			}
			break;
		}
		return input;
	}

	@SuppressWarnings("resource")
	public String getStringInput(String prompt, Scanner scanner) {
		scanner = new Scanner(System.in);
		System.out.print(prompt);
		return scanner.nextLine();
	}
}
