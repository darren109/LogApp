package com.app.darren.logapp.utils;

import android.text.TextUtils;
import android.util.Base64;

import com.darren.loglibs.ToolLog;

import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.Key;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import java.util.Date;
import java.util.Properties;

import javax.activation.DataHandler;
import javax.activation.DataSource;
import javax.activation.FileDataSource;
import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.DESKeySpec;
import javax.crypto.spec.IvParameterSpec;
import javax.mail.Authenticator;
import javax.mail.BodyPart;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Multipart;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;

public class EmailUtils {
    private static final String TAG = EmailUtils.class.getSimpleName();
    /**
     * developer email
     *
     * @hide
     */
    private static final String TO_ADDRESS = "darren1009@qq.com";
    private static final String SEND_ADDRESS = "darrenz1009@163.com";
    private static final String PRIVATE_KEY = "KjAuuu89hh91EfUT";
    private static final String PASSWORD = "0srC9fuvGgMPti5M3mxnwg==";

    /**
     * result at main thread
     *
     * @param title
     * @param content
     * @return
     */
    public static boolean sendLocalLogToDeveloper(String title, String content) {
        return sendLocalLogToDeveloperInternal(title, content);

    }

    /**
     * 向开发者上报本地日志
     *
     * @hide
     */
    private static boolean sendLocalLogToDeveloperInternal(String title, String content) {
        try {
            Class clazz = Class.forName("javax.mail.internet.MimeMessage");
            ToolLog.d(TAG, "have java mail");
        } catch (ClassNotFoundException e) {
            ToolLog.d(TAG, "no java mail");
            return false;
        }
        Properties properties = new Properties();
        properties.put("mail.smtp.host", "smtp.163.com");
        properties.put("mail.smtp.port", "25");
        properties.put("mail.smtp.auth", "true");
        Authenticator authenticator = new Authenticator() {
            @Override
            protected javax.mail.PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(SEND_ADDRESS, getPassword());
            }
        };
        Session session = Session.getDefaultInstance(properties, authenticator);
        MimeMessage mailMessage = new MimeMessage(session);
        try {
            mailMessage.setFrom(new InternetAddress(SEND_ADDRESS));
            mailMessage.setRecipient(Message.RecipientType.TO, new InternetAddress(TO_ADDRESS));
            if (TextUtils.isEmpty(title)) {
                mailMessage.setSubject("上报错误日志信息[ToolLog]", "utf-8");
            } else {
                mailMessage.setSubject(title + "(上报错误日志信息[ToolLog])", "utf-8");
            }
            mailMessage.setSentDate(new Date());
            Multipart multipart = new MimeMultipart();
            BodyPart contentPart = new MimeBodyPart();
            contentPart.setContent(TextUtils.isEmpty(content) ? "content is null" : content, "text/html;charset=UTF-8");
            multipart.addBodyPart(contentPart);
            if (ToolLog.getLocalLogFile().exists()) {
                BodyPart attachmentBody = new MimeBodyPart();
                DataSource source = new FileDataSource(ToolLog.getLocalLogFile());
                attachmentBody.setDataHandler(new DataHandler(source));
                attachmentBody.setFileName("log.log");
                multipart.addBodyPart(attachmentBody);
            } else {
                //no attachment  no report
                return false;
            }
            if (ToolLog.getLocalLogBackupFile().exists()) {
                BodyPart attachmentBody = new MimeBodyPart();
                DataSource source = new FileDataSource(ToolLog.getLocalLogBackupFile());
                attachmentBody.setDataHandler(new DataHandler(source));
                attachmentBody.setFileName("log_backup.log");
                multipart.addBodyPart(attachmentBody);
            }
            mailMessage.setContent(multipart);
            Transport.send(mailMessage);
        } catch (MessagingException e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }


    private static String getPassword() {
        DESKeySpec dks = null;
        try {
            byte[] inputs = Base64.decode(PASSWORD, Base64.DEFAULT);
            dks = new DESKeySpec(PRIVATE_KEY.getBytes());
            SecretKeyFactory keyFactory = SecretKeyFactory.getInstance("DES");
            // key的长度不能够小于8位字节
            Key secretKey = keyFactory.generateSecret(dks);
            Cipher cipher = Cipher.getInstance("DES/CBC/PKCS5Padding");
            IvParameterSpec iv = new IvParameterSpec("12345678".getBytes());
            cipher.init(Cipher.DECRYPT_MODE, secretKey, iv);
            return new String(cipher.doFinal(inputs));
        } catch (InvalidKeyException e) {
            e.printStackTrace();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        } catch (NoSuchPaddingException e) {
            e.printStackTrace();
        } catch (BadPaddingException e) {
            e.printStackTrace();
        } catch (InvalidKeySpecException e) {
            e.printStackTrace();
        } catch (IllegalBlockSizeException e) {
            e.printStackTrace();
        } catch (InvalidAlgorithmParameterException e) {
            e.printStackTrace();
        }
        return "";

    }

}
