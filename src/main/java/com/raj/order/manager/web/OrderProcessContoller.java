package com.raj.order.manager.web;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.raj.order.manager.domain.OrderEntity;
import com.raj.order.manager.service.OrderService;

@RestController
public class OrderProcessContoller {

	@Autowired
	private OrderService orderService;

	@ResponseStatus(value = HttpStatus.OK)
	@RequestMapping(value = "/order", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
	public Map<String, String> createOrder(@RequestBody Map<String, String> data) {
		System.out.println("Request data: " + data.toString());
		OrderEntity orderEntity = new OrderEntity(data.get("orderName"), data.get("productName"), data.get("createdBy"),
				data.get("clientUid"), data.get("vendorUid"));
		return orderService.createOrder(orderEntity);
	}

	@ResponseStatus(value = HttpStatus.OK)
	@RequestMapping(value = "/start-order-process", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
	public Map<String, String> startOrderProcess(@RequestBody Map<String, String> data) {
		Long orderId = Long.valueOf(data.get("orderId"));
		String vendorUid = data.get("vendorUid");
		return orderService.startProcess(orderId, vendorUid);
	}

	@ResponseStatus(value = HttpStatus.OK)
	@RequestMapping(value = "/order/{id}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public Map<String, Object> searchOrder(@PathVariable String id) {
		System.out.println("Order Search Request arrived for orderId: " + id);
		Map<String, Object> data = orderService.searchOrder(id);
		System.out.println(data.toString());
		return data;
	}

}
