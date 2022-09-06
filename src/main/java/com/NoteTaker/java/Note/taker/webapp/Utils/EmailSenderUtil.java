package com.NoteTaker.java.Note.taker.webapp.Utils;

import com.NoteTaker.java.Note.taker.webapp.Dto.Emails;
import org.apache.commons.mail.DefaultAuthenticator;
import org.apache.commons.mail.Email;
import org.apache.commons.mail.EmailException;
import org.apache.commons.mail.SimpleEmail;
import org.springframework.stereotype.Component;

@Component
public class EmailSenderUtil {
    public void emailSender(Emails emailSenderUtils) {
        try {
            Email email = new SimpleEmail();
            email.setHostName("smtp.googlemail.com");
            email.setSmtpPort(465);
            email.setAuthenticator(new DefaultAuthenticator("jaykapadiya8899@gmail.com", "elhkwmhrqtlnqlfr"));
            email.setSSLOnConnect(true);
            email.setFrom("jaykapadiya8899@gmail.com");
            email.setSubject(emailSenderUtils.getSubject());
            email.setMsg(emailSenderUtils.getContent());
            email.addTo(emailSenderUtils.getEmail());
            email.send();
        } catch (EmailException e) {
            throw new RuntimeException(e);
        }
    }
}
