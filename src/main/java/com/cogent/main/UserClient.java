package com.cogent.main;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(name = "User", url = "localhost:8081/auth")
public interface UserClient 
{
	@GetMapping("/validate")
	public boolean validToken(@RequestHeader("Authorization") String token);
	
	@GetMapping("/validateUser")
	public boolean validUserToken(@RequestParam int userId, @RequestHeader("Authorization") String token);
	
	@GetMapping("/validateAdmin")
	public boolean validAdminToken(@RequestHeader("Authorization") String token);
}