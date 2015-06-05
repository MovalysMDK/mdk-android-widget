package com.soprasteria.movalysmdk.widget.standard.command;

import android.content.Context;
import android.content.Intent;

import com.soprasteria.movalysmdk.widget.standard.R;

/**
 * Created by abelliard on 03/06/2015.
 */
public class EmailCommand implements com.soprasteria.movalysmdk.widget.core.command.Command {

    private final String[] recipients;
    private final Context context;
    private String[] recipientsCC;
    private String[] recipientsBCC;
    private String subject;
    private String body;

    public EmailCommand(Context context, String... recipients) {
        this.context = context;
        this.recipients = recipients.clone();
    }

    @Override
    public void execute() {
        Intent mailIntent = new Intent(Intent.ACTION_SEND);

        mailIntent.putExtra(Intent.EXTRA_EMAIL  , this.recipients);
        mailIntent.putExtra(android.content.Intent.EXTRA_CC, this.recipientsCC);
        mailIntent.putExtra(android.content.Intent.EXTRA_BCC, this.recipientsBCC);

        mailIntent.putExtra(Intent.EXTRA_SUBJECT, this.subject);
        mailIntent.putExtra(Intent.EXTRA_TEXT, this.body);

        mailIntent.setType("plain/text");

        this.context.startActivity( Intent.createChooser(mailIntent, this.context.getString(R.string.email_chooser_label)) );
    }
}
