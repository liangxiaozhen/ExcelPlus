package com.walmart.email;

import java.security.Security;
import java.util.Date;
import java.util.Properties;

import javax.mail.Authenticator;
import javax.mail.Message;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class EmailUtil {
	
	@Value("${spring.mail.username}")
    private String from;
    @Value("${spring.mail.password}")
    private String psw;

    @Value("${spring.mail.host}")
    private String host;
	
	  /**
     * ʹ�ü��ܵķ�ʽ,����465�˿ڽ��д����ʼ�,����ssl
     * @param to    Ϊ�ռ�������
     * @param message    ���͵���Ϣ
     */
    public  Boolean sendSimpleMail(String to,String subject, String message) {
        try {
            Security.addProvider(new com.sun.net.ssl.internal.ssl.Provider());
            final String SSL_FACTORY = "javax.net.ssl.SSLSocketFactory";
            //�����ʼ��Ự����
            Properties props = new Properties();
            //����ķ��ͷ�������ַ
            props.setProperty("mail.smtp.host", host);
            props.setProperty("mail.smtp.socketFactory.class", SSL_FACTORY);
            props.setProperty("mail.smtp.socketFactory.fallback", "false");
            //���䷢�ͷ������˿�,��������Ϊ465�˿�
            props.setProperty("mail.smtp.port", "465");
            props.setProperty("mail.smtp.socketFactory.port", "465");
            props.put("mail.smtp.auth", "true");
            final String username = from;
            final String password = psw;
            //��ȡ������Ự,���������ڲ���ķ�ʽ,�������������û�����������Ȩ��jvm
            Session session = Session.getDefaultInstance(props, new Authenticator() {
                protected PasswordAuthentication getPasswordAuthentication() {
                    return new PasswordAuthentication(username, password);
                }
            });
            //ͨ���Ự,�õ�һ���ʼ�,���ڷ���
            Message msg = new MimeMessage(session);
            //���÷�����
            msg.setFrom(new InternetAddress(from));
            //�����ռ���,toΪ�ռ���,ccΪ����,bccΪ����
            msg.setRecipients(Message.RecipientType.TO, InternetAddress.parse(to, false));
            msg.setRecipients(Message.RecipientType.CC, InternetAddress.parse(to, false));
            msg.setRecipients(Message.RecipientType.BCC, InternetAddress.parse(to, false));
            msg.setSubject(subject);
            //�����ʼ���Ϣ
            msg.setText(message);
            //���÷��͵�����
            msg.setSentDate(new Date());

            //����Transport��send����ȥ�����ʼ�
            Transport.send(msg);
            return true;

        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }

    }

}
