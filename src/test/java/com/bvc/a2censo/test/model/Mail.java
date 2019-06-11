package com.bvc.a2censo.test.model;

public class Mail {

    private String from;
    private String to;
    private String subject;
    private String date;

    public Mail(String from, String to, String subject, String date) {
        this.from = from;
        this.to = to;
        this.subject = subject;
        this.date = date;
    }

    public String toString(){
        return String.format("Mail sent from \"%s\" to \"%s\" on \"%s\" with the subject: \"%s\"",this.from,this.to,this.date,this.subject);
    }

    public String getFrom() {
        return from;
    }

    public void setFrom(String from) {
        this.from = from;
    }

    public String getTo() {
        return to;
    }

    public void setTo(String to) {
        this.to = to;
    }

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }
}