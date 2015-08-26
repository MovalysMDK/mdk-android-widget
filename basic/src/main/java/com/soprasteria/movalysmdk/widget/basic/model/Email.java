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
     * <p>
     *     The Charsequence is plain text for the email content by can contains simple styled
     *     properites (such as bold, italic, underline and text color)
     * </p>
     * @param body A Charsequence that can be styled to send 'plain/text' type email.
     */
    public void setBody(CharSequence body) {
        this.body = body;
    }


    /**
     * Transforms an array of strings to an {@link Email} object.
     * @param emailArray the string of array
     * @return the {@link Email} object
     */
    public static Email stringArrayToEmail(String[] emailArray) {
        Email emailObject = new Email();

        if (emailArray != null && emailArray.length == 5) {
            if (emailArray[0] != null) {
                emailObject.setTo(new String[]{emailArray[0]});
            }

            if (emailArray[1] != null) {
                emailObject.setCc(new String[]{emailArray[1]});
            }

            if (emailArray[2] != null) {
                emailObject.setBcc(new String[]{emailArray[2]});
            }

            emailObject.setSubject(emailArray[3]);
            emailObject.setBody(emailArray[4]);
        }

        return emailObject;

    }

    /**
     * Transforms an {@link Email} object to an array of strings.
     * @param emailObject the {@link Email} object
     * @return the string of array
     */
    public static String[] emailToStringArray(Email emailObject) {
        String[] emailArray = new String[5];

        if (emailObject.getTo() != null && emailObject.getTo().length > 0) {
            emailArray[0] = emailObject.getTo()[0];
        } else {
            emailArray[0] = null;
        }

        if (emailObject.getCc() != null && emailObject.getCc().length > 0) {
            emailArray[1] = emailObject.getCc()[0];
        } else {
            emailArray[1] = null;
        }

        if (emailObject.getBcc() != null && emailObject.getBcc().length > 0) {
            emailArray[2] = emailObject.getBcc()[0];
        } else {
            emailArray[2] = null;
        }

        emailArray[3] = emailObject.getSubject();
        emailArray[4] = (String)emailObject.getBody();

        return emailArray;
    }
}