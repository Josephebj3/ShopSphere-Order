package com.cogent.main;

import java.util.List;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(name = "Cart", url = "localhost:8083/cart")
public interface CartClient 
{
	@GetMapping("/{userId}")
	public List<CartProduct> getCartProducts(@PathVariable int userId, @RequestHeader("Authorization") String token);
	
	@DeleteMapping("/{userId}/remove/{productId}")
	public List<CartProduct> deleteProductFromCart(@PathVariable int userId, @PathVariable int productId, @RequestParam int quantity, @RequestHeader("Authorization") String token);

	@GetMapping("/validateAdmin")
	public boolean validAdminToken(@RequestHeader("Authorization") String token);
}
