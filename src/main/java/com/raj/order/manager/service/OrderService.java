package com.raj.order.manager.service;

import java.util.Collections;
import java.util.Map;

import org.activiti.engine.RuntimeService;
import org.activiti.engine.runtime.ProcessInstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.raj.order.manager.domain.OrderEntity;
import com.raj.order.manager.repository.OrderEntityRepository;

@Service
public class OrderService {

	@Autowired
	private OrderEntityRepository orderEntityRepository;

	@Autowired
	private RuntimeService runtimeService;

	public void createOrder(OrderEntity orderEntity) {
		System.out.println("Create OrderEntity request: " + orderEntity.toString());
		OrderEntity savedOrderEntity = orderEntityRepository.save(orderEntity);
		System.out.println(savedOrderEntity.toString());
	}

	public void startProcess(Long orderId, String vendorUid, boolean isAssigned) {
		OrderEntity orderEntity = orderEntityRepository.findOne(orderId);

		Map<String, Object> vars = Collections.singletonMap("orderEntity", orderEntity);
		ProcessInstance instance = runtimeService.startProcessInstanceByKey("OrderProcess", vars);
		System.out.println("Process started...");
		System.out.println("Process details: " + instance.toString());
		System.out.println("Process Variables: " + instance.getProcessVariables().toString());

		if (isAssigned) {
			orderEntity.setVendorUid(vendorUid);
			orderEntityRepository.save(orderEntity);
		}
	}

	public void updateVendorDetails() {
		System.out.println("In porgress...");
	}
	
	public void orderCompletedGreetings() {
		System.out.println("Order has been succufully completed...");
	}

}
