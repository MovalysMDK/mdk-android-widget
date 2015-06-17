package com.soprasteria.movalysmdk.widget.standard.command;

import android.content.Context;
import android.content.Intent;

import com.soprasteria.movalysmdk.widget.core.command.Command;
import com.soprasteria.movalysmdk.widget.standard.R;
import com.soprasteria.movalysmdk.widget.standard.model.Email;

/**
 * Created by abelliard on 03/06/2015.
 */
public class EmailCommand implements Command<Email, Void> {

    /** Application context. */
    private final Context context;

    /**
     * Constructor.
     * @param context the context
     */
    public EmailCommand(Context context) {
        this.context = context;
    }

    /**
     * Email launcher.
     * @param email email information
     * @return
     */
    @Override
    public Void execute(Email... email) {
        Intent mailIntent = new Intent(Intent.ACTION_SEND);

        Email currentMail = email[0];
        mailIntent.putExtra(Intent.EXTRA_EMAIL  , currentMail.getTo());
        mailIntent.putExtra(android.content.Intent.EXTRA_CC, currentMail.getCc());
        mailIntent.putExtra(android.content.Intent.EXTRA_BCC, currentMail.getBcc());

        mailIntent.putExtra(Intent.EXTRA_SUBJECT, currentMail.getSubject());
        mailIntent.putExtra(Intent.EXTRA_TEXT, currentMail.getBody());

        mailIntent.setType("plain/text");

        this.context.startActivity( Intent.createChooser(mailIntent, this.context.getString(R.string.email_chooser_label)) );

        return null;
    }
}
