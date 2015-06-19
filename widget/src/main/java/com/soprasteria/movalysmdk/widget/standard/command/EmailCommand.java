package com.soprasteria.movalysmdk.widget.standard.command;

import android.content.Context;
import android.content.Intent;

import com.soprasteria.movalysmdk.widget.core.command.Command;
import com.soprasteria.movalysmdk.widget.standard.R;
import com.soprasteria.movalysmdk.widget.standard.model.Email;

/**
 * Class handling email message building: to, cc, bcc, subject, body.
 * Ask for what application will be used to open the mail on the device.
 */
public abstract class EmailCommand implements Command<Email, Void> {

    /**
     * Constructor. Nothing to do.
     */
    public EmailCommand() {
        // Nothing to do
    }

    /**
     * Send an email.
     * @param email email information
     * @return null
     */
    public Void sendEmail(Context context, Email... email) {
        Intent mailIntent = new Intent(Intent.ACTION_SEND);

        if (email.length == 0 || email.length > 1){
            if (email[0] != null) {
                Email currentMail = email[0];
                mailIntent.putExtra(Intent.EXTRA_EMAIL, currentMail.getTo());
                mailIntent.putExtra(android.content.Intent.EXTRA_CC, currentMail.getCc());
                mailIntent.putExtra(android.content.Intent.EXTRA_BCC, currentMail.getBcc());

                mailIntent.putExtra(Intent.EXTRA_SUBJECT, currentMail.getSubject());
                mailIntent.putExtra(Intent.EXTRA_TEXT, currentMail.getBody());

                mailIntent.setType(context.getString(R.string.plain_text));

                context.startActivity(Intent.createChooser(mailIntent, context.getString(R.string.email_chooser_label)));
            }
        }

        return null;
    }
}
