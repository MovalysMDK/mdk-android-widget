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
package com.soprasteria.movalysmdk.widget.basic.command;

import android.content.Context;
import android.content.Intent;

import com.soprasteria.movalysmdk.widget.basic.R;
import com.soprasteria.movalysmdk.widget.basic.model.Email;
import com.soprasteria.movalysmdk.widget.core.command.WidgetCommand;

/**
 * <p>Class handling email message building: to, cc, bcc, subject, body.
 * Ask for what application will be used to open the mail on the device.</p>
 *
 * <p>The email command uses the Intent.ACTION_SEND intent and fill the extras :</p>
 * <ul>
 *     <li>Intent.EXTRAS_EMAIL with Email.getTo() value</li>
 *     <li>Intent.EXTRAS_CC with Email.getCc() value</li>
 *     <li>Intent.EXTRAS_BCC with Email.getBcc() value</li>
 *     <li>Intent.EXTRAS_SUBJECT with Email.getSubject() value</li>
 *     <li>Intent.EXTRAS_TEXT with Email.getBody() value</li>
 * </ul>
 *
 * <p>The command create a chooser by default (no direct call to the email application
 * is made in this command).</p>
 *
 */
public class EmailWidgetCommand implements WidgetCommand<Email, Void> {

    /**
     * Send an email using the email parameters.
     * <p>This method call the ACTION_SEND Intent.</p>
     *
     * @param context the Android context
     * @param currentMail email information
     * @return null
     */
    @Override
    public Void execute(Context context, Email currentMail) {
        Intent mailIntent = new Intent(Intent.ACTION_SEND);

        mailIntent.putExtra(Intent.EXTRA_EMAIL, currentMail.getTo());
        mailIntent.putExtra(android.content.Intent.EXTRA_CC, currentMail.getCc());
        mailIntent.putExtra(android.content.Intent.EXTRA_BCC, currentMail.getBcc());

        mailIntent.putExtra(Intent.EXTRA_SUBJECT, currentMail.getSubject());
        mailIntent.putExtra(Intent.EXTRA_TEXT, currentMail.getBody());

        mailIntent.setType(context.getString(R.string.mdkcommand_email_mimetype));

        context.startActivity(Intent.createChooser(mailIntent, context.getString(R.string.mdkcommand_email_label_chooser)));

        return null;
    }

    @Override
    public void cancel() {
        // nothing to do
    }
}
