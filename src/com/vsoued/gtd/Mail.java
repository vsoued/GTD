package com.vsoued.gtd;
import java.io.IOException;
import java.util.Date; 
import java.util.Properties; 
import javax.activation.CommandMap; 
import javax.activation.DataHandler; 
import javax.activation.DataSource; 
import javax.activation.FileDataSource; 
import javax.activation.MailcapCommandMap; 
import javax.mail.Authenticator;
import javax.mail.BodyPart; 
import javax.mail.Folder;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Multipart; 
import javax.mail.NoSuchProviderException;
import javax.mail.PasswordAuthentication; 
import javax.mail.Session; 
import javax.mail.Transport; 
import javax.mail.URLName;
import javax.mail.internet.InternetAddress; 
import javax.mail.internet.MimeBodyPart; 
import javax.mail.internet.MimeMessage; 
import javax.mail.internet.MimeMultipart;

import com.sun.mail.imap.IMAPSSLStore;
import com.sun.mail.imap.IMAPStore;

import android.os.AsyncTask;
 
 
public class Mail extends javax.mail.Authenticator {
    public String _user; 
    private String _pass; 
   
    private String[] _to; 
    private String _from; 
   
    private String _port; 
    private String _sport; 
   
    private String _host; 
   
    private String _subject; 
    private String _body; 
   
    private boolean _auth; 
     
    private boolean _debuggable; 
   
    private Multipart _multipart; 
    
    int topmessage;
    
    int botmessage;
   
   
    public Mail() { 
      _host = "smtp.gmail.com"; // default smtp server 
      _port = "465"; // default smtp port 
      _sport = "465"; // default socketfactory port 
   
      _user = ""; // username 
      _pass = ""; // password 
      _from = ""; // email sent from 
      _subject = ""; // email subject 
      _body = ""; // email body 
   
      _debuggable = false; // debug mode on or off - default off 
      _auth = true; // smtp authentication - default on 
   
      _multipart = new MimeMultipart(); 
   
      // There is something wrong with MailCap, javamail can not find a handler for the multipart/mixed part, so this bit needs to be added. 
      MailcapCommandMap mc = (MailcapCommandMap) CommandMap.getDefaultCommandMap(); 
      mc.addMailcap("text/html;; x-java-content-handler=com.sun.mail.handlers.text_html"); 
      mc.addMailcap("text/xml;; x-java-content-handler=com.sun.mail.handlers.text_xml"); 
      mc.addMailcap("text/plain;; x-java-content-handler=com.sun.mail.handlers.text_plain"); 
      mc.addMailcap("multipart/*;; x-java-content-handler=com.sun.mail.handlers.multipart_mixed"); 
      mc.addMailcap("message/rfc822;; x-java-content-handler=com.sun.mail.handlers.message_rfc822"); 
      CommandMap.setDefaultCommandMap(mc); 
    } 
   
    public Mail(String user, String pass) { 
        super();
   
      _user = user; 
      _pass = pass; 
      topmessage = 50;
      botmessage = 50;
      
    } 
   
    public boolean send() throws Exception { 
      Properties props = _setProperties(); 
   
      if(!_user.equals("") && !_pass.equals("") && _to.length > 0 && !_from.equals("") && !_subject.equals("") && !_body.equals("")) { 
        Session session = Session.getInstance(props, this); 
   
        MimeMessage msg = new MimeMessage(session); 
   
        msg.setFrom(new InternetAddress(_from)); 
         
        InternetAddress[] addressTo = new InternetAddress[_to.length]; 
        for (int i = 0; i < _to.length; i++) { 
          addressTo[i] = new InternetAddress(_to[i]); 
        } 
          msg.setRecipients(MimeMessage.RecipientType.TO, addressTo); 
   
        msg.setSubject(_subject); 
        msg.setSentDate(new Date()); 
   
        // setup message body 
        BodyPart messageBodyPart = new MimeBodyPart(); 
        messageBodyPart.setText(_body); 
        _multipart.addBodyPart(messageBodyPart); 
   
        // Put parts in message 
        msg.setContent(_multipart); 
   
        // send email 
        new SendMail().execute(msg);
   
        return true; 
      } else { 
        return false; 
      } 
    } 
   
    public boolean receive() throws Exception {
        Properties props = new Properties(); 
                
        props.put("mail.imap.host", "imap.gmail.com");
     
        if(_debuggable) { 
          props.put("mail.debug", "true"); 
        } 
     
        if(_auth) { 
          props.put("mail.imap.auth", "true"); 
        } 
     
        
        
        props.put("mail.imap.user", "vsoued@gmail.com");
        
        
        
        props.put("mail.imap.port", "993"); 
        props.put("mail.imap.socketFactory.port", "993"); 
        props.put("mail.imap.socketFactory.class", "javax.net.ssl.SSLSocketFactory"); 
        props.put("mail.imap.socketFactory.fallback", "false"); 
        
        if(!_user.equals("") && !_pass.equals("")) { 
          Session session = Session.getInstance(props, this); 
     
          IMAPSSLStore store =  new IMAPSSLStore(session, new URLName("imap.gmail.com"));
          
          store.connect("vsoued@gmail.com", "hek:190688");
          
          Folder inbox = store.getFolder("INBOX");
          inbox.open(Folder.READ_ONLY);
          
          int newcount = inbox.getMessageCount();
          Message[] msgs = inbox.getMessages(0,newcount);
          for (int i = 0; i < msgs.length; i++){
              System.out.println("--------------------------");
              System.out.println("Number: "+ i);
              System.out.println("From: "+ msgs[i].getFrom()[0]);
              System.out.println("Subject: "+ msgs[i].getSubject());
              System.out.println("Content: "+ msgs[i].getContent().toString());

              
          }
     
          return true; 
        } else { 
          return false; 
        } 
    }
    public void addAttachment(String filename) throws Exception { 
      BodyPart messageBodyPart = new MimeBodyPart(); 
      DataSource source = new FileDataSource(filename); 
      messageBodyPart.setDataHandler(new DataHandler(source)); 
      messageBodyPart.setFileName(filename); 
   
      _multipart.addBodyPart(messageBodyPart); 
    } 
   
    @Override 
    public PasswordAuthentication getPasswordAuthentication() { 
      return new PasswordAuthentication(_user, _pass); 
    } 
   
    private Properties _setProperties() { 
      Properties props = new Properties(); 
   
      props.put("mail.smtp.host", _host); 
   
      if(_debuggable) { 
        props.put("mail.debug", "true"); 
      } 
   
      if(_auth) { 
        props.put("mail.smtp.auth", "true"); 
      } 
   
      props.put("mail.smtp.port", _port); 
      props.put("mail.smtp.socketFactory.port", _sport); 
      props.put("mail.smtp.socketFactory.class", "javax.net.ssl.SSLSocketFactory"); 
      props.put("mail.smtp.socketFactory.fallback", "false"); 
   
      return props; 
    } 
   
    // the getters and setters 
    public String getBody() { 
      return _body; 
    } 
   
    public void setBody(String _body) { 
      this._body = _body; 
    } 
    
    public void setTo(String[] _to) {
        this._to = _to; 
    } 
     
    public void setFrom(String _from) { 
        this._from = _from; 
    } 
    
    public void setSubject(String _subject) { 
        this._subject = _subject; 
    } 
    
    public class SendMail extends AsyncTask<MimeMessage, Void, Boolean> {

        @Override
        protected Boolean doInBackground(MimeMessage... params) {
            try {
                Transport.send(params[0]);
            } catch (MessagingException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }// TODO Auto-generated method stub
            return null;
        }
        
    }
    
    public class GetMail extends AsyncTask<Void, Void, String[]> {

        @Override
        protected String[] doInBackground(Void... params) {
            Properties props = new Properties(); 
            
            props.put("mail.imaps.host", "imap.gmail.com");
         
            if(_debuggable) { 
              props.put("mail.debug", "true"); 
            } 
         
            if(_auth) { 
              props.put("mail.imaps.auth", "true"); 
            } 
            //props.put("mail.imap.user", "vsoued@gmail.com");

         
            props.put("mail.imaps.port", "993"); 
            props.put("mail.imaps.socketFactory.port", "993"); 
            props.put("mail.imaps.socketFactory.class", "javax.net.ssl.SSLSocketFactory"); 
            props.put("mail.imaps.socketFactory.fallback", "false"); 
            
            if(!_user.equals("") && !_pass.equals("")) { 
              Session session = Session.getInstance(props, Mail.this); 
         
              IMAPSSLStore store =  new IMAPSSLStore(session, new URLName("imap.gmail.com"));
            
              try {
                //store.connect();
                store.connect("imap.gmail.com", 993, _user, _pass);
                Folder inbox = store.getFolder("INBOX");
                inbox.open(Folder.READ_ONLY);
                
                int count = inbox.getMessageCount();
                //int newcount = inbox.getMessageCount();
//                System.out.println("COUNT "+newcount);
                Message[] msgs = inbox.getMessages(count-10, count);
                
                topmessage = msgs[msgs.length-1].getMessageNumber();
//                for (int i = 0; i < msgs.length; i++){
//                    System.out.println("--------------------------");
//                    System.out.println("Number: "+ i);
//                    System.out.println("From: "+ msgs[i].getFrom()[0]);
//                    System.out.println("Subject: "+ msgs[i].getSubject());
//                   // System.out.println("Content: "+ msgs[i].getContent().toString());
//
//                   
//                }
                
                String[] readable = new String[msgs.length]; 
                for (int i = 0; i < msgs.length; i++){
                    String from_name = msgs[i].getFrom()[0].toString();
                    int index = from_name.indexOf("<");
                    //from_name = from_name.substring(index);
                    readable[readable.length-i-1] = from_name +"\n"+ msgs[i].getSubject();
                }
                return readable;
              } catch (MessagingException e) {
                  // TODO Auto-generated catch block
                  e.printStackTrace();
              }
              
            } else {
                System.out.println("no account");
            }
         // TODO Auto-generated method stub
            return null;
        }

        
    }
    
    
   
    // more of the getters and setters �..  

}
