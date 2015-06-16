package com.soprasteria.movalysmdk.widget.standard.model;

/**
 * class Email
 * Created by abelliard on 05/06/2015.
 */
public class Email {

    private String[] to = null;
    private String[] cc = null;
    private String[] bcc = null;

    private String subject = null;
    private String body = null;

    public Email(String... to) {
        this.to = to;
    }

    public String[] getTo() {
        return to.clone();
    }

    public void setTo(String[] to) {
        this.to = to.clone();
    }

    public String[] getCc() {
        return this.cc.clone();
    }

    public void setCc(String[] cc) {
        this.cc = cc.clone();
    }

    public String[] getBcc() {
        return bcc.clone();
    }

    public void setBcc(String[] bcc) {
        this.bcc = bcc.clone();
    }

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }
}
