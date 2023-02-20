package com.project.util;

import java.io.File;
import java.nio.file.Files;
import java.util.Properties;
import java.util.Random;
import java.util.UUID;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.mail.Authenticator;
import javax.mail.Message;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Component;

import com.project.enums.ConstantEnum;

@Component
public class BaseMethods {
	private static final Logger LOGGER = LogManager.getLogger(BaseMethods.class);

	@Value("${email.from}")
	private String from;

	@Value("${email.password}")
	private String password;

	@Value("${email.smtp.host}")
	private String smtpHost;

	public String getUsername() {
		User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		return user.getUsername();
	}

	public Pageable getRequestedPage(int page, int size, String sort, String sortBy) {
		Pageable requestedPage;

		if (sort != null && sortBy != null) {
			if (sort.equals("ASC")) {
				requestedPage = new PageRequest(page, size, Sort.Direction.ASC, sortBy);
			} else if (sort.equals("DESC")) {
				requestedPage = new PageRequest(page, size, Sort.Direction.DESC, sortBy);
			} else {
				requestedPage = new PageRequest(page, size, Sort.DEFAULT_DIRECTION, sortBy);
			}
		} else {
			requestedPage = new PageRequest(page, size);
		}

		return requestedPage;
	}

	public String allLetterCaps(String target) {
		Matcher m = Pattern.compile("^(?:\\w|/[A-Z]|\\b\\w)").matcher(target);

		StringBuilder sb = new StringBuilder();

		int last = 0;
		while (m.find()) {
			sb.append(target.substring(last, m.start()));
			sb.append(m.group(0).toUpperCase());
			last = m.end();
		}
		sb.append(target.substring(last));

		return sb.toString().replaceAll("\\s+", "");
	}

	public String camelize(String target) {

		String allCapital = this.allLetterCaps(target);

		return Character.toLowerCase(allCapital.charAt(0)) + allCapital.substring(1);
	}

	public String reverseCamelize(String target) {
		StringBuilder sb = new StringBuilder();
		target = target.replaceAll("([A-Z])", " $1");
		String[] words = target.split(" ");

		for (String word : words) {
			if (word.length() > 0)
				sb.append(Character.toUpperCase(word.charAt(0)) + word.substring(1) + " ");
		}
		return sb.toString();
	}

	public String generateuuid() {
		UUID uuid = UUID.randomUUID();
		return uuid.toString();
	}

	public String generateotp() {
		int otp = new Random().nextInt(9999);
		return String.format("%04d", otp);
	}

	public String getMessageText(String fileName) {
		String messagetext = "";

		ClassLoader classLoader = getClass().getClassLoader();

		try {
			File file = new File(classLoader.getResource(fileName).getFile());

			// Read File Content
			messagetext = new String(Files.readAllBytes(file.toPath()));

		} catch (Exception e) {
			LOGGER.error(ConstantEnum.EXCEPTION_MESSAGE, e);
		}

		return messagetext;
	}

	public void sendVerifyMail(String recipient, String firstName, String lastName, String link) {

		String messagetext = this.getMessageText("static/verifyemail.html");

		// content of mail and sender
		String subject = "Please verify your email address";

		if (!messagetext.isEmpty()) {
			messagetext = messagetext.replace("[FIRSTNAME]", firstName);
			messagetext = messagetext.replace("[LASTNAME]", lastName);
			messagetext = messagetext.replace("[LINK]", link);

		}

		this.sendMail(subject, messagetext, recipient);
	}

	public void sendOTPMail(String recipient, String otp) {

		String messagetext = this.getMessageText("static/forgotpassword.html");

		// content of mail and sender
		String subject = "OTP to reset your password";

		if (!messagetext.isEmpty()) {
			messagetext = messagetext.replace("[OTP]", otp);
		}

		this.sendMail(subject, messagetext, recipient);
	}

	public void sendMail(String subject, String messagetext, String recipient) {

		// Getting properties object
		Properties properties = new Properties();

		// setting smtp config
		properties.put("mail.smtp.auth", "true");
		properties.put("mail.smtp.starttls.enable", "true");
		properties.put("mail.smtp.host", smtpHost);
		properties.put("mail.smtp.port", "587");

		// step:1 Get The Session

		Session session = Session.getInstance(properties, new Authenticator() {

			@Override
			protected PasswordAuthentication getPasswordAuthentication() {
				return new PasswordAuthentication(from, password);
			}
		});

		// step:2 compose the message
		try {
			MimeMessage message = new MimeMessage(session);

			// from email
			message.setFrom(new InternetAddress(from));

			// to email
			message.setRecipient(Message.RecipientType.TO, new InternetAddress(recipient));

			// set subject
			message.setSubject(subject);

			// set content
			message.setContent(messagetext, "text/html");

			// step:3 send the message
			Transport.send(message);

		} catch (Exception e) {
			LOGGER.error(ConstantEnum.EXCEPTION_MESSAGE, e);
		}
	}
}
