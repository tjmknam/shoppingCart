package net.class101.utility;

import net.class101.model.Item;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class GenerateTestData extends ItemsData {


	private Map<Integer, Item> map = null;


	//singleton
	public void initialize() {
		if(map == null) {
			synchronized(this) {
				map = new ConcurrentHashMap<Integer, Item>();
				Item item1 = new Item(16374, "class", "스마트스토어로 월 100만원 만들기, 평범한 사람이 돈을 만드는 비법", 151950, 99999);
				Item item2 = new Item(26825, "class", "해금, 특별하고 아름다운 나만의 반려악기", 114500, 99999);
				Item item3 = new Item(9236, "kit", "하루의 시작과 끝, 욕실의 포근함을 선사하는 천연 비누", 9900, 22);
				map.put(16374, item1);
				map.put(26825, item2);
				map.put(9236, item3);
			}
		}
	}

	public Map<Integer, Item> getData() {
		return map;
	}

}