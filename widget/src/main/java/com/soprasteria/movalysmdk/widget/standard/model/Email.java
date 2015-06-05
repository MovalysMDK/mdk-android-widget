package com.soprasteria.movalysmdk.widget.standard.model;

/**
 * Created by abelliard on 05/06/2015.
 */
public class Email {

    public String[] to;
    public String[] cc;
    public String[] bcc;

    public String subject;
    public String body;

    public Email(String... to) {
        this.to = to;
    }
}
