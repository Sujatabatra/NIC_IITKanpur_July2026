package com.sujata.customerservice;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class MyDummyTestCase {

	@BeforeAll
	static void setUpBeforeClass() throws Exception {
		System.out.println("Hi I am @BeforeAll");
	}

	@AfterAll
	static void tearDownAfterClass() throws Exception {
		System.out.println("Hi I am @AfterAll");
	}

	@BeforeEach
	void setUp() throws Exception {
		System.out.println("Hi I am @BeforeEach");
	}

	@AfterEach
	void tearDown() throws Exception {
		System.out.println("Hi I am @AfterEach");
	}

	@Test
	@DisplayName("Not testing anything just a message from test 1")
	void test1() {
		System.out.println("Hi I am testcase 1");
	}
	
	@Test
	@DisplayName("Test the addition of 10+20 = 40")
	void test2() {
		System.out.println("Hi I am testcase 2");
		assertEquals(40, 10+20);
		
//		fail("Intentionally failing test case");
	}

	@Test
	@DisplayName("@DisplayName(\"Not testing anything just a message from test 2")
	void test3() {
		System.out.println("Hi I am testcase 3");
	}
}
