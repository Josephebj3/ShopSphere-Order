package com.cogent.main;

import java.util.LinkedList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import jakarta.transaction.Transactional;

@Service
public class OrderService 
{
	@Autowired
	private OrderRepository repository;
	
	@Autowired
	private UserClient userClient;
	
	@Autowired
	private CartClient cartClient;

	public List<OrderDao> getOrders(int userId, String token)
	{
		if(!userClient.validUserToken(userId, token)) return null;
		
		List<OrderEntity> oeList = repository.getByUserId(userId);
		List<OrderDao> odList = new LinkedList<OrderDao>();
		
		for(OrderEntity oe: oeList)
		{
			odList.add(OrderDao.builder()
					.id(oe.getId())
					.userId(userId)
					.productId(oe.getProductId())
					.orderId(oe.getOrderId())
					.name(oe.getName())
					.description(oe.getDescription())
					.price(oe.getPrice())
					.category(oe.getCategory())
					.quantity(oe.getQuantity())
					.image(oe.getImage())
					.build());
		}
		
		return odList;
	}

	public List<OrderDao> postOrder(int userId, String token)
	{
		if(!userClient.validUserToken(userId, token)) return null;
		List<CartProduct> cpList = cartClient.getCartProducts(userId, token);
		List<OrderDao> odList = new LinkedList<OrderDao>();
		int orderId;
		if(!repository.getByUserId(userId).isEmpty()) 
		{
			orderId = repository.getByUserId(userId).stream().map((a) -> a.getOrderId()).max(Integer::compare).get()+1;
		}
		else orderId = 1;
		
		for(CartProduct cp: cpList)
		{
			repository.save(OrderEntity.builder()
					.userId(userId)
					.productId(cp.getProductId())
					.orderId(orderId)
					.name(cp.getName())
					.description(cp.getDescription())
					.price(cp.getPrice())
					.category(cp.getCategory())
					.quantity(cp.getQuantity())
					.image(cp.getImage())
					.build());
			
			odList.add(OrderDao.builder()
					.userId(userId)
					.productId(cp.getProductId())
					.orderId(orderId)
					.name(cp.getName())
					.description(cp.getDescription())
					.price(cp.getPrice())
					.category(cp.getCategory())
					.quantity(cp.getQuantity())
					.image(cp.getImage())
					.build());
			
			cartClient.deleteProductFromCart(userId, cp.getProductId(), cp.getQuantity(), token);
		}
		return odList;
	}

	public List<OrderDao> getOrder(int userId, int orderId, String token) 
	{
		if(!userClient.validUserToken(userId, token)) return null;
		
		List<OrderEntity> oeList = repository.findByOrderIdAndUserId(userId, orderId);
		List<OrderDao> odList = new LinkedList<OrderDao>();
		
		for(OrderEntity oe: oeList)
		{
			odList.add(OrderDao.builder()
					.id(oe.getId())
					.userId(oe.getUserId())
					.productId(oe.getProductId())
					.orderId(oe.getOrderId())
					.name(oe.getName())
					.description(oe.getDescription())
					.price(oe.getPrice())
					.category(oe.getCategory())
					.quantity(oe.getQuantity())
					.image(oe.getImage())
					.build());
		}
		if(!odList.isEmpty())
			if(odList.get(0).getUserId() != userId ) return null;
		return odList;
	}

	@Transactional
	public boolean deleteOrdersByUserId(int userId, String token) 
	{
		if(!userClient.validAdminToken(token)) return false;
		if(repository.findByUserId(userId).size()<1) return false;
		repository.deleteByUserId(userId);
		return true;
	}
	
}
