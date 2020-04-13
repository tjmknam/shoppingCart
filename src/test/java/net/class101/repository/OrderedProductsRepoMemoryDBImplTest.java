package net.class101.repository;

import static org.junit.Assert.*;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.util.Map;
import java.util.concurrent.Callable;
import java.util.concurrent.CompletionService;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorCompletionService;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

import org.junit.Before;
import org.junit.Test;

import net.class101.exception.SoldOutException;
import net.class101.model.Item;
import net.class101.utility.GenerateTestData;

public class OrderedProductsRepoMemoryDBImplTest {
	
	


	private OrderedProductsRepo orderedProductsRepoImpl = null;

	private GenerateTestData testData = null;

	private static ExecutorService threadPool = null;

	private CompletionService<Boolean> completionService = null;



	@Before
	public void setup() {
		threadPool = Executors.newFixedThreadPool(3);
		completionService = new ExecutorCompletionService<Boolean>(threadPool);
		testData = new GenerateTestData();
		testData.initialize();
		orderedProductsRepoImpl = new OrderedProductsRepoMemoryDBImpl(testData);

	}

	@Test(expected = SoldOutException.class)
	public void testUpdateInventoryException() throws SoldOutException {
		orderedProductsRepoImpl.updateInventory(9236, 23);
	}

	@Test
	public void testUpdateInventory() throws SoldOutException {
		orderedProductsRepoImpl.updateInventory(16374, 3);
		orderedProductsRepoImpl.updateInventory(9236, 20);
		Map<Integer, Item> data = testData.getData();
		assertEquals(data.get(9236).getInventory(), 2);
	}

	
	//multi thread 요청으로 SoldOutException 이 정상 동작하는지 확인하는 Test
	//9236아이템은 총수량 22일때,
	//3개의 thread가 9236 아이템에 수량 12개로 요청되었을때, 2개의 thread는 재고가 부족합니다라고 메세지를 전달해야합니다
	@Test
	public void testUpdateInventoryMultiThreaded() {
		ByteArrayOutputStream outContent = new ByteArrayOutputStream();
	    System.setOut(new PrintStream(outContent));
		try {
			boolean allOrdered = updateInventoryMultiThreaded();
			assertFalse(allOrdered);
			assertEquals(10, testData.getData().get(9236).getInventory());
		} catch (SoldOutException e) {
			e.printStackTrace();
		}
		assertEquals("주문하신 9236은/는 재고가 부족합니다\n주문하신 9236은/는 재고가 부족합니다\n", outContent.toString());

	}


	private boolean updateInventoryMultiThreaded() throws SoldOutException {
		for (int i = 0; i < 3; i++) {
			Callable<Boolean> c = new Callable<Boolean>() {
				@Override
				public Boolean call() {
					try {
						orderedProductsRepoImpl.updateInventory(9236, 12);
					}
					catch (SoldOutException e) {
						return false;
					}
					return true;
				}
			};
			completionService.submit(c);
		}
		
		boolean allOrdered = true;

		for(int j = 0; j < 3; j++) {
			try {
				Future<Boolean> f = completionService.take();
				boolean ordered = f.get();
				if(!ordered) {
					allOrdered = false;
				}
			} catch (InterruptedException | ExecutionException e) {
				e.getMessage();
			}
		}
		
		return allOrdered;

	}
}
