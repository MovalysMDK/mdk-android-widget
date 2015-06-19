package com.soprasteria.movalysmdk.widget.standard.model;

/**
 * Email class.
 */
public class Email {

    /**
     * List of email address to send email.
     */
    private String[] to;

    /**
     * List of email address to send email on carbon copy.
     */
    private String[] cc;

    /**
     * List of email address to send email on blind carbon copy.
     */
    private String[] bcc;

    /**
     * Subject of the email.
     */
    private String subject;

    /**
     * Body of the email.
     */
    private String body;

    /**
     * Constructor.
     * @param to to
     */
    public Email(String... to) {
        init();
        this.to = to;
    }

    /**
     * Initialize data.
     */
    private void init() {
        to = null;
        cc = null;
        bcc = null;
        subject = null;
        body = null;
    }

    /**
     * Get list of email address to send email.
     * @return a list of email address
     */
    public String[] getTo() {
        return to.clone();
    }

    /**
     * Set list of email address to send email.
     * @param to List of email address
     */
    public void setTo(String[] to) {
        this.to = to.clone();
    }

    /**
     * Get list of email address for carbon copy.
     * @return a list of email address on carbon copy
     */
    public String[] getCc() {
        return this.cc.clone();
    }

    /**
     *  Set list of email address for carbon copy.
     * @param cc List of email address for cc
     */
    public void setCc(String[] cc) {
        this.cc = cc.clone();
    }

    /**
     *  Get list of email address for blind carbon copy
     * @return a list of email address for blind carbon copy
     */
    public String[] getBcc() {
        return bcc.clone();
    }

    /**
     * Set list of email address for blind carbon copy
     * @param bcc List of email address for bcc
     */
    public void setBcc(String[] bcc) {
        this.bcc = bcc.clone();
    }

    /**
     * Get the subject of the mail.
     * @return subject of the mail
     */
    public String getSubject() {
        return subject;
    }

    /**
     * Set the subject of the mail.
     * @param subject email subject to set
     */
    public void setSubject(String subject) {
        this.subject = subject;
    }

    /**
     * Get email text body.
     * @return body the body
     */
    public String getBody() {
        return body;
    }

    /**
     * Set email text body.
     * @param body email body to set: can
     */
    public void setBody(String body) {
        this.body = body;
    }
}