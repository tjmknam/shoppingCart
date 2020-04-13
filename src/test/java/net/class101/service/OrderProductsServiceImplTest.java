package net.class101.service;

import static org.junit.Assert.*;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.io.PrintStream;

import org.junit.Before;
import org.junit.Test;

import net.class101.utility.GenerateTestData;

public class OrderProductsServiceImplTest {

	private GenerateTestData testData = null;

	private OrderProductsService orderPorudctsService = null;
	
	
	@Before
	public void setup() {
		testData = new GenerateTestData();
		testData.initialize();
		orderPorudctsService = new OrderProductsServiceImpl(testData);

	}
	
	//class가 한번이상 주문되었을때, 한번만 적용
	@Test
	public void testOrderClass() {
		InputStream order = new ByteArrayInputStream("o\n26825\n5\n \no\n26825\n3\n \nq".getBytes());
		System.setIn(order);
		ByteArrayOutputStream outContent = new ByteArrayOutputStream();
	    System.setOut(new PrintStream(outContent));
		orderPorudctsService.order();
		assertEquals(99999, testData.getData().get(26825).getInventory());
		assertTrue(outContent.toString().contains("114,500"));
	}
	
	//50,000 이상일때, 배송료무료 
	@Test
	public void testOrderKitWithoutDeliveryFee() {
		InputStream order = new ByteArrayInputStream("o\n9236\n5\n9236\n3\n \nq".getBytes());
		System.setIn(order);
		ByteArrayOutputStream outContent = new ByteArrayOutputStream();
	    System.setOut(new PrintStream(outContent));
		orderPorudctsService.order();
		assertEquals(14, testData.getData().get(9236).getInventory());
		assertTrue(outContent.toString().contains("79,200"));
	}
	
	//50,000 미만일때, 배송료 5,000원 
	@Test
	public void testOrderKitWithDeliveryFee() {
		InputStream order = new ByteArrayInputStream("o\n9236\n5\n \nq".getBytes());
		System.setIn(order);
		ByteArrayOutputStream outContent = new ByteArrayOutputStream();
	    System.setOut(new PrintStream(outContent));
		orderPorudctsService.order();
		assertEquals(17, testData.getData().get(9236).getInventory());
		assertTrue(outContent.toString().contains("54,500"));
	}



}
