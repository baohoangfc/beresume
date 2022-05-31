package com.bao.doan.controller;

import java.util.Map;

import javax.mail.MessagingException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.bao.doan.entity.Orders;
import com.bao.doan.entity.Payment;
import com.bao.doan.service.MailService;
import com.bao.doan.service.PaymentService;

@RestController
@RequestMapping("/admin/payment")
public class PaymentController {
	
	@Autowired
	private PaymentService paymentService;
	
	@Autowired
	private MailService mailService;
	
	@PostMapping(value = "/themmoi", produces = { MediaType.APPLICATION_JSON_VALUE })
	@ResponseBody
	@CrossOrigin(origins = { "*" })
	public Map<String, Object> themMoiPayment(@RequestBody Payment payment) {
		return paymentService.themMoiPayment(payment);
	}
	
	@PostMapping(value = "/order", produces = { MediaType.APPLICATION_JSON_VALUE })
	@ResponseBody
	@CrossOrigin(origins = { "*" })
	public Map<String, Object> themMoiOrder(@RequestBody Orders order) {
		return paymentService.themMoiOrder(order);
	}
	
	@ResponseBody
	@GetMapping(value = "/order/ds", produces = { MediaType.APPLICATION_JSON_VALUE })
	@CrossOrigin(origins = { "*" })
	public Map<String, Object> getDanhSachOrder() {		
		return paymentService.getListOrder();
	}
	
	@ResponseBody
	@PostMapping(value = "/order/user", produces = { MediaType.APPLICATION_JSON_VALUE })
	@CrossOrigin(origins = { "*" })
	public Map<String, Object> getOrderByUser(@RequestBody Map<String,Object> thongtin) {
		String email = thongtin.get("email").toString();
		return paymentService.getOrderByUser(email);
	}
	
	@ResponseBody
	@PostMapping(value = "/send-mail", produces = { MediaType.APPLICATION_JSON_VALUE })
	@CrossOrigin(origins = { "*" })
	public String sendMail(@RequestBody Map<String,Object> thongtin) {
		String email = thongtin.get("email").toString();
		try {
			return mailService.sendEmailHtml(email);
		} catch (MessagingException e) {
			e.printStackTrace();
		}
		return email;
	}
}
