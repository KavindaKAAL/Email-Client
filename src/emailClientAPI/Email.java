package emailClientAPI;

import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.io.Serializable;
import java.time.LocalDate;
import java.util.Properties;

@SuppressWarnings("serial")
public class Email implements Serializable{
	
	private String recipientEmailAddress;
	private String subject;
	private String content;
	private LocalDate date;
	
	/*
	 * This is the constructor of the Email class
	 * @param to : Email address
	 * @param subject : Subject of the email
	 * @param content : Content of the email
	 */
	public Email(String recipientEmailAddress, String subject, String content) {
		this.recipientEmailAddress = recipientEmailAddress;
		this.subject = subject;
		this.content = content;
		this.date=LocalDate.now();
	}
	
	 /*
	 * This method is to get the date of mail was sent
	 * Return the e-mail sent date
	 */
	
	public LocalDate getDate() {
		return date;
	}
	
	 /*
	 * This method is to get the email address of the person who received the mail
	 * Return the email address of the person who received the mail
	 */
	public String getEmailAddress() {
		return recipientEmailAddress;
	}
	
	 /*
	 * This method is to get the subject of the email
	 * Return the subject of the e-mail
	 */
	public String getSubject() {
		return subject;
	}
	
	 /*
	 * This method is to get the content of the email
	 * Return the content of the email
	 */
	public String getContent() {
		return content;
	}
	
	 /*
	 * This method is for sending a message as an email
	 * Does not accept anything
	 * Does not return anything
	 */
	public void sendMail(String recipientEmailAddress ) throws Exception{

		System.out.println("Preparing to send mail, to "+recipientEmailAddress);
		Properties properties = new Properties();
		
		properties.put("mail.smtp.ssl.trust","smtp.gmail.com");
		properties.put("mail.smtp.auth","true");
		properties.put("mail.smtp.starttls.enable","true");
		properties.put("mail.smtp.host","smtp.gmail.com");
		properties.put("mail.smtp.port","587");
		
		final String myAccountEmail ="lahirukavinda400@gmail.com"; //Here use your(user) e-mail address. 
		final String password ="ytyheygfsjjhmqbp";//Here use your(user) e-mail address password.
		
		Session session = Session.getInstance(properties,new Authenticator(){
			@Override
			protected PasswordAuthentication getPasswordAuthentication() {
				return new PasswordAuthentication(myAccountEmail,password);
			}
		});
		
		Message message = prepareMessage(session,myAccountEmail,recipientEmailAddress);
		Transport.send(message);
		System.out.println("Email sent successfully.");
	}
	
	private Message prepareMessage(Session session,String myAccountEmail,String recipientEmailAddress) {
		try {
			Message message = new MimeMessage(session);
			message.setFrom(new InternetAddress(myAccountEmail));
			message.setRecipient(Message.RecipientType.TO, new InternetAddress(recipientEmailAddress));
			message.setSubject(subject);
			message.setText(content);
			return message;
			
		}catch (MessagingException ex) {
			ex.printStackTrace();
		}
		return null;
	}
}
