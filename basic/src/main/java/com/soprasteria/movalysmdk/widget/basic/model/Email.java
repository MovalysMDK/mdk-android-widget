/**
 * Copyright (C) 2010 Sopra Steria Group (movalys.support@soprasteria.com)
 *
 * This file is part of Movalys MDK.
 * Movalys MDK is free software: you can redistribute it and/or modify
 * it under the terms of the GNU Lesser General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 * Movalys MDK is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 * GNU Lesser General Public License for more details.
 * You should have received a copy of the GNU Lesser General Public License
 * along with Movalys MDK. If not, see <http://www.gnu.org/licenses/>.
 */
package com.soprasteria.movalysmdk.widget.basic.model;

/**
 * Email class.
 * <p>Value object representing an email object to pass to the android system.</p>
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
    private CharSequence body;

    /**
     * Constructor.
     * @param to A String[] holding e-mail addresses that should be delivered to
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
     * Get list of email addresses to send email.
     * @return A clone of holding e-mail addresses that should be delivered to
     */
    public String[] getTo() {
        return to;
    }

    /**
     * Set list of email addresses to send email.
     * @param to A String[] holding e-mail addresses that should be delivered to
     */
    public void setTo(String[] to) {
        this.to = to.clone();
    }

    /**
     * Get list of email addresses for carbon copy.
     * @return a clone of the list of email address on carbon copy
     */
    public String[] getCc() {
        return this.cc;
    }

    /**
     *  Set list of email addresses for carbon copy.
     * @param cc List of email address for cc
     */
    public void setCc(String[] cc) {
        this.cc = cc.clone();
    }

    /**
     *  Get list of email addresses for blind carbon copy.
     * @return a clone of list of email addresses for blind carbon copy
     */
    public String[] getBcc() {
        return bcc;
    }

    /**
     * Set list of email addresses for blind carbon copy.
     * @param bcc List of email address for bcc
     */
    public void setBcc(String[] bcc) {
        this.bcc = bcc.clone();
    }

    /**
     * Get the subject of the mail.
     * @return A String holding the desired subject of the message
     */
    public String getSubject() {
        return subject;
    }

    /**
     * Set the subject of the mail.
     * @param subject A String holding the desired subject of the message
     */
    public void setSubject(String subject) {
        this.subject = subject;
    }

    /**
     * Get email text body.
     * @return A CharSequence holding the content of the email. This CharSequence may be styled.
     */
    public CharSequence getBody() {
        return body;
    }

    /**
     * Set email text body.
     * //FIXME: improove javadoc. Email format ? text, html ?
     * @param body A Charsequence that can be styled to send 'plain/text' type email.
     */
    public void setBody(CharSequence body) {
        this.body = body;
    }
}