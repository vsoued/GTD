package com.vsoued.gtd;

import java.io.IOException;
import java.util.Date;

import javax.mail.Address;
import javax.mail.Message;
import javax.mail.MessagingException;

public class MyMsg {
    Address[] recipients;
    String contentType;
    Object content;
    String subject;
    String description;
    Address[] from;
    Address[] replyTo;
    Date date;

    public MyMsg(Message msg) throws MessagingException, IOException{
        recipients = msg.getAllRecipients();
        contentType = msg.getContentType();
        content = msg.getContent();
        subject = msg.getSubject();
        description = msg.getDescription();
        from = msg.getFrom();
        date = msg.getReceivedDate();
        replyTo = msg.getReplyTo();
        
    }
    
    public String toString(){
        int index = from.toString().indexOf("<");
        String from_name = from.toString().substring(0, index);
        return from_name+"\n"+ subject;
        
    }
}
