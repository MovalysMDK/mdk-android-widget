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

    /**
     * Constructor.
     * @param to to
     */
    public Email(String... to) {
        this.to = to;
    }

    /**
     * Getter.
     * @return to a clone version of to
     */
    public String[] getTo() {
        return to.clone();
    }

    /**
     * Setter.
     * @param to to
     */
    public void setTo(String[] to) {
        this.to = to.clone();
    }

    /**
     * Getter.
     * @return cc a clone version of cc
     */
    public String[] getCc() {
        return this.cc.clone();
    }

    /**
     * Setter.
     * @param cc cc
     */
    public void setCc(String[] cc) {
        this.cc = cc.clone();
    }

    /**
     * Getter.
     * @return bcc a clone version of bcc
     */
    public String[] getBcc() {
        return bcc.clone();
    }

    /**
     * Setter.
     * @param bcc bcc
     */
    public void setBcc(String[] bcc) {
        this.bcc = bcc.clone();
    }

    /**
     * Getter.
     * @return subject subject
     */
    public String getSubject() {
        return subject;
    }

    /**
     * Setter.
     * @param subject subject
     */
    public void setSubject(String subject) {
        this.subject = subject;
    }

    /**
     * Getter.
     * @return body the body
     */
    public String getBody() {
        return body;
    }

    /**
     * Setter.
     * @param body the new body
     */
    public void setBody(String body) {
        this.body = body;
    }
}
