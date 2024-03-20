package com.cogent.main;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class OrderDao 
{
	private int id;
	private int userId;
	private int productId;
	private int orderId;
	private String name;
	private String description;
	private float price;
	private String category;
	private int quantity;
	private String image;
}
