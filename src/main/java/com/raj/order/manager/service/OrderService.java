package com.raj.order.manager.service;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

import org.activiti.engine.RuntimeService;
import org.activiti.engine.runtime.ProcessInstance;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.raj.order.manager.domain.OrderEntity;
import com.raj.order.manager.repository.OrderEntityRepository;

@Service
public class OrderService {

	private static final String CREATED_STATE = "Created";

	@Autowired
	private OrderEntityRepository orderEntityRepository;

	@Autowired
	private RuntimeService runtimeService;

	public Map<String, String> createOrder(OrderEntity orderEntity) {
		System.out.println("Create OrderEntity request: " + orderEntity.toString());
		orderEntity.setStatuCode(CREATED_STATE);
		OrderEntity savedOrderEntity = orderEntityRepository.save(orderEntity);
		System.out.println(savedOrderEntity.toString());
		if (StringUtils.isNotEmpty(orderEntity.getVendorUid()))
			return startProcess(savedOrderEntity.getId(), orderEntity.getVendorUid());

		Map<String, String> data = new HashMap<>();
		data.put("orderId", savedOrderEntity.getId().toString());
		data.put("statusCode", orderEntity.getStatuCode());
		data.put("message", "Order successfully created.");
		return data;
	}

	public Map<String, String> startProcess(Long orderId, String vendorUid) {
		OrderEntity orderEntity = orderEntityRepository.findOne(orderId);
		Map<String, String> data = new HashMap<>();
		data.put("orderId", orderId.toString());
		String message;

		if (null == orderEntity) {
			message = "Order not found.";
			data.put("message", message);
			return data;
		}

		if (!CREATED_STATE.equals(orderEntity.getStatuCode())) {
			message = "Couldn't start process as order isn't in Created state.";
			data.put("message", message);
			return data;
		}

		Map<String, Object> vars = Collections.singletonMap("orderEntity", orderEntity);
		ProcessInstance instance = runtimeService.startProcessInstanceByKey("OrderProcess", vars);

		System.out.println("Process started...");
		System.out.println("Process details: " + instance.toString());
		System.out.println("Process Variables: " + instance.getProcessVariables().toString());

		if (StringUtils.isNotEmpty(vendorUid)) {
			orderEntity.setVendorUid(vendorUid);
			orderEntity.setStatuCode("Assigned");
			message = "Order process initiated! Assigned to " + vendorUid;
		} else {
			orderEntity.setStatuCode("Manual Assignment");
			message = "Order process initiated! Manual assignment to vendor is required.";
		}
		orderEntity.setOrderId(orderId.toString());
		orderEntityRepository.save(orderEntity);

		data.put("statusCode", orderEntity.getStatuCode());
		data.put("message", message);
		return data;
	}

	public Map<String, Object> searchOrder(String orderId) {
		OrderEntity orderEntity = orderEntityRepository.findOne(Long.valueOf(orderId));
		String message;
		Map<String, Object> data = new HashMap<>();
		data.put("orderEntity", orderEntity);

		message = (null == orderEntity) ? "Order not found." : "Matching order found.";
		data.put("message", message);
		return data;
	}

	public void updateVendorDetails() {
		System.out.println("In porgress...");
	}

	public void orderCompletedGreetings() {
		System.out.println("Order has been succufully completed...");
	}

}
