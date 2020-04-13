package net.class101.repository;

import net.class101.exception.SoldOutException;

//checkout시기를 위한 인터페이스 
//새로운 데이터베이스를 위한 인터페이스
public interface OrderedProductsRepo {
	
	public boolean updateInventory(int itemNumber, int amount) throws SoldOutException;

}
