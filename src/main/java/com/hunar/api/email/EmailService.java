package com.hunar.api.email;

import java.io.IOException;
import java.util.Map;

import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import jakarta.mail.internet.InternetAddress;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.stereotype.Service;
import org.springframework.ui.freemarker.FreeMarkerTemplateUtils;
import freemarker.template.Configuration;
import freemarker.template.Template;
import freemarker.template.TemplateException;

@EnableAsync
@Service
public class EmailService {

	@Autowired
	private JavaMailSender sender;

	@Autowired
	private Configuration configuration;
	
	@Value("${spring.mail.username}")
	private String senderEmail;

	@Value("${owner.user.email}")
	private String ownerEmail;
	

	@Async
	public void sendEmail(EmailRequest request, Map<String, Object> model) {
		EmailResponse response = new EmailResponse();
		MimeMessage message = sender.createMimeMessage();
		
 		try {
			MimeMessageHelper helper = new MimeMessageHelper(message);
			Template template = configuration.getTemplate("email-template.ftl");
			String html = FreeMarkerTemplateUtils.processTemplateIntoString(template, model);

			helper.setTo(request.getTo());
			helper.setText(html, true);
			helper.setCc(ownerEmail);
			helper.setSubject("Order Confirmation.");
			helper.setFrom(senderEmail);
			Thread.sleep(2000);
			sender.send(message);
			response.setMessage("mail send to : " + request.getTo());
			response.setStatus(Boolean.TRUE);

		} catch (MessagingException | IOException | TemplateException e) {
			response.setMessage("Mail Sending failure : " + e.getMessage());
			response.setStatus(Boolean.FALSE);
		} catch (InterruptedException e) {
            throw new RuntimeException(e);
        }

//		return response;
	}

	@Async
	public void sendEmailForDelivery(String sendTo, Map<String, Object> model) {
		EmailResponse response = new EmailResponse();
		MimeMessage message = sender.createMimeMessage();

		try {
			MimeMessageHelper helper = new MimeMessageHelper(message);
			Template template = configuration.getTemplate("delivery-email-template.ftl");
			String html = FreeMarkerTemplateUtils.processTemplateIntoString(template, model);

			helper.setTo(sendTo);
			helper.setText(html, true);
			helper.setCc(ownerEmail);
			helper.setSubject("Orders to be delivered");
			helper.setFrom(senderEmail);
			sender.send(message);

		} catch (MessagingException | IOException | TemplateException e) {
			response.setMessage("Mail Sending failure : " + e.getMessage());
			response.setStatus(Boolean.FALSE);
		}

//		return response;
	}

}
