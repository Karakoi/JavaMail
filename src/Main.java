import com.sun.mail.util.MailSSLSocketFactory;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.security.GeneralSecurityException;
import java.util.Date;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Main {

    public static void main(String[] args) {
        sendMessage();
        System.out.println("All will be fine!");
    }

    public static void sendMessage(){

        try {
            String email = "******@gmail.com";
            String password = "***********";


            MailSSLSocketFactory socketFactory = null;
            try {
                socketFactory = new MailSSLSocketFactory();
                socketFactory.setTrustedHosts(new String[] {"my-server"});
                socketFactory.setTrustAllHosts(true);
            } catch (GeneralSecurityException | NullPointerException e) {
                e.printStackTrace();
            }

            Properties props = new Properties();
            props.put("mail.smtps.socketFactory", socketFactory);
            props.put("mail.transport.protocol", "smtps");
            props.put("mail.smtps.host", email);
            props.put("mail.smtps.auth", "true");
            props.put("mail.smtp.sendpartial", "true");
            props.put("mail.smtps.ssl.checkserveridentity", "false");
            props.put("mail.smtp.ssl.trust", "*");

            Session session = Session.getDefaultInstance(props);
            session.setDebug(true);
            Transport transport = session.getTransport();
            transport.connect("smtp.gmail.com", 465, email, password);

            InternetAddress address = new InternetAddress("*******@yandex.ru");

            MimeMessage message = new MimeMessage(session);
            message.setSubject("Hi, bro :)");
            message.setText("Hi, it's your first sended message from Java app");
            message.addRecipient(Message.RecipientType.TO, address);
            message.setSentDate(new Date());
            transport.sendMessage(message, message.getRecipients(Message.RecipientType.TO));

        } catch (MessagingException ex) {
            Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
        }

    }
}
