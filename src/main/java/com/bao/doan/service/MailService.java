package com.bao.doan.service;


import java.text.SimpleDateFormat;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.bao.doan.dao.PaymentDao;
import com.bao.doan.dto.DataPayment;

@Service
@Transactional
public class MailService {

	@Autowired
	private JavaMailSender javaMailSender;
	
	@Autowired
	private PaymentDao paymentDao;
	
	private static final Log log = LogFactory.getLog(DataPayment.class);
	
	public String sendEmailHtml(String email) throws MessagingException {
//		int code = UniConstant.ErrorCode.SUCCESS;
//		String message = UniConstant.Message.INSERT_SUCCESS;
		DataPayment dataPayments = paymentDao.getOrderByUser(email);		
		if(null != dataPayments) {
			MimeMessage minemessage = javaMailSender.createMimeMessage();	 
	        boolean multipart = true;         
	        MimeMessageHelper helper = new MimeMessageHelper(minemessage, multipart, "UTF-8");
	        SimpleDateFormat formatter = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss");
	        
	        String htmlMsg = "<h3>Thông Tin Thanh Toán Tài Khoản FlixGo</h3>"
	                		+"<img src='https://live.staticflickr.com/65535/49941283898_4e4eb30e57_m.jpg'>"
	        				+ "<ul>"
		        				+ "<li>Tài khoản: " + email + "</li>"
		        				+ "<li>Số tiền: " + 9 + "$</li>"
		        				+ "<li>Thời gian đăng kí: " + formatter.format(dataPayments.getNgaytao()) + "</li>"
		        				+ "<li>Thời gian hết hạn: " + formatter.format(dataPayments.getNgayhethan()) + "</li>"
	        				+ "</ul>";
	         
	        minemessage.setContent(htmlMsg, "text/html");        
	        helper.setTo(email);        
	        helper.setSubject("Test send HTML email");         
	        javaMailSender.send(minemessage);
		} else {
			log.error("Không tìm thấy email: " + email);
		}
        
        return "Email Sent !!!";
	}
}
