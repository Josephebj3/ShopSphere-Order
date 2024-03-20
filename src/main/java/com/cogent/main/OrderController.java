package com.cogent.main;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;



@RestController
@RequestMapping("/orders")
public class OrderController
{
	@Autowired
	private OrderService service;
	
	@GetMapping("/{userId}")
	public List<OrderDao> getOrders(@PathVariable int userId, @RequestHeader("Authorization") String token) 
	{
		return service.getOrders(userId, token);
	}
	
	@PostMapping("/{userId}")
	public List<OrderDao> postOrder(@PathVariable int userId, @RequestHeader("Authorization") String token) 
	{
		return service.postOrder(userId, token);
	}
	
	@GetMapping("{userId}/detail/{orderId}")
	public List<OrderDao> getOrder(@PathVariable int userId, @PathVariable int orderId, @RequestHeader("Authorization") String token) 
	{
		return service.getOrder(userId, orderId, token);
	}
	
	@DeleteMapping("/delete/{userId}")
	public boolean deleteOrdersByUserId(@PathVariable int userId, @RequestHeader("Authorization") String token)
	{
		return service.deleteOrdersByUserId(userId, token);
	}
	
	
}
