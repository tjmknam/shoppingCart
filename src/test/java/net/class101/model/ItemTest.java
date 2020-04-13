package net.class101.model;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

public class ItemTest {
	
	private Item item = null;
	
	
	@Before
	public void setup() {
		item = new Item();
	}

	@Test
	public void testGetterAndSetter() {
		item.setItemNumber(123);
		item.setType("class");
		item.setItemDescription("new product");
		item.setPrice(50000);
		item.setInventory(100);
		assertEquals(item.getItemNumber(), 123);
		assertEquals(item.getType(), "class");
		assertEquals(item.getItemDescription(), "new product");
		assertEquals(item.getPrice(), 50000);
		assertEquals(item.getInventory(), 100);

		
	}

}
