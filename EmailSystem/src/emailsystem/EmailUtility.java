/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package emailsystem;

import java.io.File;
import java.io.IOException;
import java.util.Date;
import java.util.Properties;
 
import javax.mail.Authenticator;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Multipart;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;
 
public class EmailUtility {
    public static void sendEmail(Properties smtpProperties, String[] toAddress,
            String subject, String message, File[] attachFiles)
            throws AddressException, MessagingException, IOException {
                System.out.println("Message");
        final String userName = smtpProperties.getProperty("mail.user");
        final String password = smtpProperties.getProperty("mail.password");
        final String host=smtpProperties.getProperty("mail.smtp.host");
        // creates a new session with an authenticator
        Authenticator auth = new Authenticator() {
            public PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(userName, password);
            }
        };
        Session session = Session.getInstance(smtpProperties, auth);
 
        // creates a new e-mail message
        Message msg = new MimeMessage(session);
 
        msg.setFrom(new InternetAddress(userName));
        InternetAddress[] toAddresses = new InternetAddress[toAddress.length];
        for(int i=0;i<toAddress.length;i++)
        {
            toAddresses[i]=new InternetAddress(toAddress[i]);
        }
        msg.setRecipients(Message.RecipientType.TO, toAddresses);
        msg.setSubject(subject);
        msg.setSentDate(new Date());
        try
        {
        // creates message part
        MimeBodyPart messageBodyPart = new MimeBodyPart();
        messageBodyPart.setContent(message, "text/html");
 
        // creates multi-part
        Multipart multipart = new MimeMultipart();
        multipart.addBodyPart(messageBodyPart);
 
        // adds attachments
        if (attachFiles != null && attachFiles.length > 0) {
            for (File aFile : attachFiles) {
                MimeBodyPart attachPart = new MimeBodyPart();
 
                try {
                    attachPart.attachFile(aFile);
                } catch (IOException ex) {
                    throw ex;
                }
 
                multipart.addBodyPart(attachPart);
            }
        }
         session.setDebug(true);
    System.out.println(msg+" memmm");
     
        // sets the multi-part as e-mail's content
        msg.setContent(multipart);
 
        // sends the e-mail
        Transport transport = session.getTransport("smtp"); 
transport.connect(host, userName, password); 
transport.send(msg); 
transport.close(); 
//  Transport.send(msg);
       
        System.out.println("Sent message successfully....");
}catch (MessagingException mex) {
mex.printStackTrace();
}
    }
}