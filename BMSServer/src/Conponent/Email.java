package Conponent;

import javax.mail.MessagingException;
import javax.mail.NoSuchProviderException;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.util.Date;
import java.util.Properties;

public class Email {
    private String sourceEmailAddress;
    private String EmailPHost;
    private String destEmailAddress;
    private String permissionCode;
    private String smtpPort;

    private Properties properties;

    public void SendEmail(String code) {
        // 根据配置创建会话对象, 用于和邮件服务器交互
        Session session = Session.getDefaultInstance(properties);
        session.setDebug(true);                                 // 设置为debug模式, 可以查看详细的发送 log
        // 创建一封邮件
        MimeMessage message = null;
        try {
            message = createMimeMessage(session, sourceEmailAddress, destEmailAddress, code);
        } catch (Exception e) {
            e.printStackTrace();
        }


        // 根据 Session 获取邮件传输对象
        Transport transport = null;
        try {
            transport = session.getTransport();
        } catch (NoSuchProviderException e) {
            e.printStackTrace();
        }

        try {
            transport.connect(sourceEmailAddress, "hmzwxuyaxodbbigd");//连接，XXXXXXXX填的是qq邮箱的授权码，登录QQ邮箱，然后在设置里面p0p3/smtp哪一块可以看到，协议必须打开。
        } catch (MessagingException e) {
            e.printStackTrace();
        }
        //登录qq邮箱---设置----账户-----然后下面可以看到，然后生成授权码。


        // 发送邮件, 发到所有的收件地址, message.getAllRecipients() 获取到的是在创建邮件对象时添加的所有收件人, 抄送人, 密送人
        //   for(int i=0;i<100;i++){
        try {
            assert message != null;
            transport.sendMessage(message, message.getAllRecipients());
        } catch (MessagingException e) {
            e.printStackTrace();
        }
        // }
        // 关闭连接
        try {
            transport.close();
        } catch (MessagingException e) {
            e.printStackTrace();
        }
    }

    public Email(String srcEmailAddress, String dstEmailAddress) {
        sourceEmailAddress = srcEmailAddress;
        destEmailAddress = dstEmailAddress;
        EmailPHost = "smtp.qq.com";
        smtpPort = "465";
        properties = new Properties();
        // 1. 创建参数配置, 用于连接邮件服务器的参数配置
        properties.setProperty("mail.transport.protocol", "smtp");   // 使用的协议（JavaMail规范要求）
        properties.setProperty("mail.smtp.host", EmailPHost );   // 发件人的邮箱的 SMTP 服务器地址
        properties.setProperty("mail.smtp.auth", "true");            // 需要请求认证
        //开启ssl安全验证
        properties.setProperty("mail.smtp.port", smtpPort);
        properties.setProperty("mail.smtp.socketFactory.class", "javax.net.ssl.SSLSocketFactory");
        properties.setProperty("mail.smtp.socketFactory.fallback", "false");
        properties.setProperty("mail.smtp.socketFactory.port", smtpPort);
    }

    public void setSourceEmailAddress(String emailAddress){
        this.sourceEmailAddress = emailAddress;
    }

    public void setEmailPHost(String emailPHost) {
        this.EmailPHost = emailPHost;
    }

    public void setPermissionCode(String permissionCode) {
        this.permissionCode = permissionCode;
    }

    public void setDestEmailAddress(String emailAddress) {
        this.destEmailAddress = emailAddress;
    }
    /**
     *
     * @param session
     * @param sendMail 发件人邮箱
     * @param receiveMail 收件人邮箱
     * @return
     * @throws Exception
     */
    public static MimeMessage createMimeMessage(Session session, String sendMail, String receiveMail, String code) throws Exception {
        // 1. 创建一封邮件
        MimeMessage message = new MimeMessage(session);


        // 2. From: 发件人
        message.setFrom(new InternetAddress(sendMail, "图书管理系统", "UTF-8"));


        // 3. To: 收件人（可以增加多个收件人、抄送、密送）
        message.setRecipient(MimeMessage.RecipientType.TO, new InternetAddress(receiveMail, "尊敬的用户", "UTF-8"));


        // 4. Subject: 邮件主题
        message.setSubject("图书管理系统注册验证码", "UTF-8");


        // 5. Content: 邮件正文（可以使用html标签）
        message.setContent("尊敬的用户，欢迎使用华中科技大学图书管理系统，您的本次操作为修改密码，操作验证码是：" + code +"。", "text/html;charset=UTF-8");


        // 6. 设置发件时间
        message.setSentDate(new Date());


        // 7. 保存设置
        message.saveChanges();


        return message;
    }

}
