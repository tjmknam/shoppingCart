package net.class101;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ImportResource;

import net.class101.service.OrderProductsService;
import net.class101.service.OrderProductsServiceImpl;
import net.class101.utility.ItemsData;

@SpringBootApplication
@ImportResource({"classpath:applicationContext.xml"})
public class App {
	
	public static void main(String[] args) {
		SpringApplication.run(App.class, args);	
		ItemsData data = new ItemsData();
		data.initialize();
		OrderProductsService orderProductsServiceImpl = new OrderProductsServiceImpl(data);
		orderProductsServiceImpl.order();
		
	}
}
