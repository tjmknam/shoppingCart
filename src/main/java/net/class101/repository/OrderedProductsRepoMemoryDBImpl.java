package net.class101.repository;

import java.util.Map;

import net.class101.exception.SoldOutException;
import net.class101.model.Item;
import net.class101.utility.ItemsData;

public class OrderedProductsRepoMemoryDBImpl implements OrderedProductsRepo {
		
	private ItemsData data;
	
	public OrderedProductsRepoMemoryDBImpl(ItemsData data) {
		this.data = data;
	}


	
	@Override
	public synchronized boolean updateInventory(int itemNumber, int amount) throws SoldOutException {
		boolean updateSuccessful = true;
		Map<Integer, Item> inventory = data.getData();
		Item item = inventory.get(itemNumber);
		if (item.getInventory() < amount) {
			updateSuccessful = false;
			System.out.println("주문하신 " + itemNumber + "은/는 재고가 부족합니다");
			throw new SoldOutException("주문하신 " + itemNumber + "은/는 재고가 부족합니다\n");
		}
		else {
			int remaining = item.getInventory() - amount;
			item.setInventory(remaining);
			inventory.put(itemNumber, item);
		}	
		return updateSuccessful;
	}
}
