package com.hunar.api.email;

import java.time.LocalDate;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import com.hunar.api.entity.CustomerEntity;
import com.hunar.api.repository.CustomerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/api")
public class EmailController {
	
	private Long count =1l;
	@Autowired
	private EmailService service;

	@Autowired
	CustomerRepository repo;
	
	@PostMapping("/email")
	public EmailResponse sendEmail(@RequestBody EmailRequest request) {
		Map<String, Object> model = new HashMap<>();
		model.put("name", "IBRAHIM SIDDIQUI");
		model.put("to","ibzysidd@gmail.com");
		model.put("date", new Date().toString());
		model.put("orderNo", "ORD-121213");
		model.put("sdate", LocalDate.now().toString());
		model.put("edate", LocalDate.now().toString());
		model.put("items", "Shirt, Pant");
		model.put("mobile", "9015316599");
//		return service.sendEmail(request, model);
		return  null;
	}

}