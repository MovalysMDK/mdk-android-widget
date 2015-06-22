package com.soprasteria.movalysmdk.widget.standard.command;

import android.content.Context;
import android.content.Intent;

import com.soprasteria.movalysmdk.widget.core.command.Command;
import com.soprasteria.movalysmdk.widget.standard.R;
import com.soprasteria.movalysmdk.widget.standard.model.Email;

/**
 * Class handling email message building: to, cc, bcc, subject, body.
 * Ask for what application will be used to open the mail on the device.
 *
 * The email command uses the Intent.ACTION_SEND intent and fill the extras :
 * Intent.EXTRAS_EMAIL with Email.getTo() value
 * Intent.EXTRAS_CC with Email.getCc() value
 * Intent.EXTRAS_BCC with Email.getBcc() value
 * Intent.EXTRAS_SUBJECT with Email.getSubject() value
 * Intent.EXTRAS_TEXT with Email.getBody() value
 *
 * The command create a chooser by default (no direct call to the email application
 * is made in this command).
 *
 */
public abstract class EmailCommand implements Command<Email, Void> {

    /**
     * Send an email using the email parameters.
     * This method call the ACTION_SEND Intent.
     *
     * @param context the Android context
     * @param email email information
     * @return null
     */
    @Override
    public Void execute(Context context, Email... email) {
        Intent mailIntent = new Intent(Intent.ACTION_SEND);

        if (email.length <= 0 || email.length > 1 || email[0] == null) {
            throw new IllegalArgumentException("email command should only have one Email in parameter.");
        } else {
            Email currentMail = email[0];
            mailIntent.putExtra(Intent.EXTRA_EMAIL, currentMail.getTo());
            mailIntent.putExtra(android.content.Intent.EXTRA_CC, currentMail.getCc());
            mailIntent.putExtra(android.content.Intent.EXTRA_BCC, currentMail.getBcc());

            mailIntent.putExtra(Intent.EXTRA_SUBJECT, currentMail.getSubject());
            mailIntent.putExtra(Intent.EXTRA_TEXT, currentMail.getBody());

            mailIntent.setType(context.getString(R.string.plain_text));

            context.startActivity(Intent.createChooser(mailIntent, context.getString(R.string.email_chooser_label)));
        }

        return null;
    }
}
