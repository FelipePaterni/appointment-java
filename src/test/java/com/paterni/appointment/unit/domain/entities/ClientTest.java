package com.paterni.appointment.unit.domain.entities;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

import java.time.LocalDate;

import org.junit.jupiter.api.Test;

import com.paterni.appointment.domain.entities.Client;

public class ClientTest {

	@Test
	void getDateOfBirthShouldReturnNull() {
		Client client = new Client();
		assertNull(client.getDateOfBirth());
	}

	@Test
	void getDateOfBirthShouldReturnLocalDate() {
		Client client = new Client();
		LocalDate expDate = LocalDate.parse("2006-08-16");

		client.setDateOfBirth(expDate);
		assertEquals(expDate, client.getDateOfBirth());

	}

	@Test
	void constructorShouldSetAllAttributes() {
		String expName = "John Doe";
		String expPhone = "1234567890";
		LocalDate expDate = LocalDate.parse("2006-08-16");
		
		Client client = new Client(expName, expPhone, expDate);
		
		assertEquals(expName, client.getName());
		assertEquals(expPhone, client.getPhone());
		assertEquals(expDate, client.getDateOfBirth());
		assertNull(client.getId());

	}

}
