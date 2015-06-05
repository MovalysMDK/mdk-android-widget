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

    private final Context context;

    public EmailCommand(Context context) {
        this.context = context;
    }

    @Override
    public Void execute(Email... email) {
        Intent mailIntent = new Intent(Intent.ACTION_SEND);

        Email currentMail = email[0];
        mailIntent.putExtra(Intent.EXTRA_EMAIL  , currentMail.to);
        mailIntent.putExtra(android.content.Intent.EXTRA_CC, currentMail.cc);
        mailIntent.putExtra(android.content.Intent.EXTRA_BCC, currentMail.bcc);

        mailIntent.putExtra(Intent.EXTRA_SUBJECT, currentMail.subject);
        mailIntent.putExtra(Intent.EXTRA_TEXT, currentMail.body);

        mailIntent.setType("plain/text");

        this.context.startActivity( Intent.createChooser(mailIntent, this.context.getString(R.string.email_chooser_label)) );

        return null;
    }
}
