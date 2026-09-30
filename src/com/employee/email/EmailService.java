package com.employee.email;

import com.employee.model.Deparment;
import com.employee.model.Employee;
import com.employee.model.EmployeeStatus;
import com.employee.model.Gender;
import com.employee.service.DepartmentService;
import com.employee.util.Constants;

import jakarta.mail.Message;
import jakarta.mail.MessagingException;
import jakarta.mail.PasswordAuthentication;
import jakarta.mail.Session;
import jakarta.mail.Transport;
import jakarta.mail.internet.InternetAddress;
import jakarta.mail.internet.MimeBodyPart;
import jakarta.mail.internet.MimeMessage;
import jakarta.mail.internet.MimeMultipart;

import java.io.File;
import java.io.IOException;
import java.time.LocalDate;
import java.util.Properties;

import com.employee.file.LogUtil;

public class EmailService {
	
	

	private final Session session;

    public EmailService() {
        Properties props = new Properties();
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.starttls.enable", "true");
        props.put("mail.smtp.host", Constants.SMTP_HOST);
        props.put("mail.smtp.port", Constants.SMTP_PORT);

        this.session = Session.getInstance(props, new jakarta.mail.Authenticator() {
            @Override
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(Constants.EMAIL_FROM, Constants.EMAIL_PASSWORD);
            }
        });
    }

    public void sendPayslip(Employee emp, String payrollMonth, File pdfAttachment) {
        try {
            MimeMessage message = new MimeMessage(session);
            message.setFrom(new InternetAddress(Constants.EMAIL_FROM));
            message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(emp.getEmail()));
            message.setSubject("Monthly Salary Payslip - " + payrollMonth);

            MimeBodyPart textPart = new MimeBodyPart();
            String body = "Dear " + emp.getFullName() + ",\n\n" +
                    "Your salary for " + payrollMonth + " has been processed successfully.\n" +
                    "Please find your payslip attached.\n\n" +
                    "Regards,\nHR Department";
            textPart.setText(body);

            MimeBodyPart attachmentPart = new MimeBodyPart();
            attachmentPart.attachFile(pdfAttachment);

            MimeMultipart multipart = new MimeMultipart();
            multipart.addBodyPart(textPart);
            multipart.addBodyPart(attachmentPart);

            message.setContent(multipart);

            Transport.send(message);
            LogUtil.info("Payslip emailed to " + emp.getEmail() + " for month " + payrollMonth);

        } catch (MessagingException | IOException e) {
            LogUtil.error("Failed to send payslip email to " + emp.getEmail() + ": " + e.getMessage());
            throw new RuntimeException("Failed to send payslip email: " + e.getMessage(), e);
        }
    }
    
    public static void main(String[] args) {
		EmailService emailService=new EmailService();
		Deparment dept=new Deparment(1, "java Devlapar", "java full stack devlapar");
		Employee employee=new Employee(1,"Swapnil","Supekar", "swapnilsupekar9309@gmail.com","9309144435", "pune",Gender.male,dept,"Employee", LocalDate.now() , 20000, EmployeeStatus.active);
		emailService.sendPayslip(employee, "2026-09", null);
	}
     
}

