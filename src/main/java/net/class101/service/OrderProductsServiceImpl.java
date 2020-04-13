package net.class101.service;

import java.util.Locale;
import java.util.Map;
import java.util.Scanner;
import java.util.concurrent.CompletionService;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ExecutorCompletionService;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.text.NumberFormat;

import org.apache.commons.lang3.StringUtils;

import net.class101.exception.SoldOutException;
import net.class101.model.Item;
import net.class101.repository.OrderedProductsRepo;
import net.class101.repository.OrderedProductsRepoMemoryDBImpl;
import net.class101.utility.ItemsData;

public class OrderProductsServiceImpl implements OrderProductsService {

	private ItemsData data;

	public OrderProductsServiceImpl(ItemsData data) {
		this.data = data;
	}

	@Override
	public void order() {
		boolean isOrderFinished = false;
		//현재 재고 
		Map<Integer, Item> inventoryMap = data.getData();
		Scanner scan = new Scanner(System.in);

		//유저 오더 시작
		Map<Integer, Integer> trackOrder = new ConcurrentHashMap<>();
		while(!isOrderFinished) {
			int totalAmount = 0;
			System.out.print("입력(o[order]: 주문, q[quit]: 종료) : ");
			//클라이언트의 주문 번호 + 주문 갯수 
			Map<Integer, Integer> orders = new ConcurrentHashMap<>();
			//주문내역 
			StringBuilder str = new StringBuilder();
			String orderOrQuitOrCompleted = scan.nextLine();
			if(StringUtils.equalsIgnoreCase("o", orderOrQuitOrCompleted)) {
				System.out.println("상품번호                        상품명                  판매가격           재고수");
				inventoryMap.forEach((key, value) ->
				System.out.println(key + " " + value.getItemDescription() + "      " + value.getPrice() + "      " + value.getInventory()));
				while(true) {
					//Map<Integer, Integer> newOrder = new HashMap<>();
					System.out.print("상품번호 : ");
					String itemNumberOrBlank = scan.nextLine();
					if(StringUtils.isBlank(itemNumberOrBlank) || !StringUtils.isNumeric(itemNumberOrBlank)) {
						break;
					}

					int itemNumber = Integer.parseInt(itemNumberOrBlank);
					System.out.print("수량 : ");
					String numOfItemsOrBlank = scan.nextLine();
					if(StringUtils.isBlank(numOfItemsOrBlank) || !StringUtils.isNumeric(numOfItemsOrBlank)) {
						break;
					}

					int numOfItems = Integer.parseInt(numOfItemsOrBlank);
					Item item = inventoryMap.get(itemNumber);
					//재고에 있는 상품번호만 추가
					if(item != null) {
						//클래스는 맥시멈 수량 1 
						if(numOfItems > 1 && StringUtils.equalsIgnoreCase(item.getType(), "class")) {
							numOfItems = 1;
						}
						if(trackOrder.get(itemNumber) != null && StringUtils.equalsIgnoreCase(item.getType(), "class")) {
							continue;
						}
						//반복되는 물품이 kit 일때만 주문에 추가 
						else if (trackOrder.get(itemNumber) != null && orders.get(itemNumber) != null) {
							orders.put(itemNumber, orders.get(itemNumber) + numOfItems);
							//totalAmount += inventoryMap.get(itemNumber).getPrice() * numOfItems;
						}
						//새로운 주문이 시작 + kit 일때
						else if (trackOrder.get(itemNumber) != null && orders.get(itemNumber) == null) {
							orders.put(itemNumber, numOfItems);
							//totalAmount += inventoryMap.get(itemNumber).getPrice() * numOfItems;
						}
						//새로운 주문이 시작, class 일때
						else {
							trackOrder.put(itemNumber, numOfItems);
							orders.put(itemNumber, numOfItems);
							//totalAmount += inventoryMap.get(itemNumber).getPrice() * numOfItems;
						}
					}
				}
				boolean isUpdated = false;
				
				for(Map.Entry<Integer, Integer> map : orders.entrySet()) {
					Item item = inventoryMap.get(map.getKey());
					OrderedProductsRepo orderedProductsRepoMemoryImpl = new OrderedProductsRepoMemoryDBImpl(data);
					//kit 일때만 재고 수 정리 
					if(StringUtils.equalsIgnoreCase(item.getType(), "kit")) {
						try {
							boolean updated = orderedProductsRepoMemoryImpl.updateInventory(map.getKey(), map.getValue());
							if(updated) {
								str.append("\n"+item.getItemDescription() + "- " + map.getValue() + "개");
								isUpdated = true;
								totalAmount += item.getPrice() * map.getValue();
							}
						} catch (SoldOutException e) {
							e.printStackTrace();
						}
					}
					else {
						str.append("\n"+item.getItemDescription() + "- " + map.getValue() + "개");
						isUpdated = true;
						totalAmount += item.getPrice() * map.getValue();
					}
				}
				
				//상품이 하나라도 재고에 있으면 주문 프로세스
				if(isUpdated) {
					System.out.println("주문 내역:\n----------------" + str.toString() + "\n----------------");

					String total = NumberFormat.getInstance(Locale.US).format(totalAmount);
					System.out.println("주문금액: " + total);
					int finalAmount = totalAmount;
					//50000원 미만일때 배송비 추가
					if(totalAmount < 50000 && orders.size() > 0) {
						System.out.println("배송비: 5,000원");
						finalAmount += 5000;
					}
					String finalAmountFormatted = NumberFormat.getInstance(Locale.US).format(finalAmount);
					System.out.println("--------------\n지불금액: " + finalAmountFormatted + "\n--------------");
				}		
			}
			else if(StringUtils.equalsIgnoreCase("q", orderOrQuitOrCompleted)) {
				System.out.println("class101을 이용해주셔서 감사합니다");
				isOrderFinished = true;
			}
			else {
				System.out.println("주문 또는 종료를 위해 'o' 혹은 'q'를 입력해주시기 바랍니다");
			}
		}



	}

}
