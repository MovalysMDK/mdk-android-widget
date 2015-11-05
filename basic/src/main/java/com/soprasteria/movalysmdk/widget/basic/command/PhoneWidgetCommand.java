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
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.net.Uri;
import android.telephony.TelephonyManager;
import android.util.Log;
import android.widget.Toast;

import com.soprasteria.movalysmdk.widget.basic.R;
import com.soprasteria.movalysmdk.widget.core.command.WidgetCommand;

import java.util.List;

/**
 * <p>Class handling phone dialing.
 * Launch the phone application used on the device.</p>
 *
 * <p>The email command checks that the telephony state is valid and uses the Intent.ACTION_DIAL intent to initiate the call.
 */
public class PhoneWidgetCommand implements WidgetCommand<String, Void> {

    /**
     * Initiates a call with the given phone number.
     * <p>This method call the ACTION_DIAL Intent.</p>
     * @param context the Android context
     * @param number phone numbers
     * @return null
     */
    @Override
    public Void execute(Context context, String number) {

        TelephonyManager oTelephonyManager=(TelephonyManager) context.getSystemService(Context.TELEPHONY_SERVICE);

        // check that the phone module is available
        if (TelephonyManager.CALL_STATE_IDLE==oTelephonyManager.getCallState()){
            String sToDial = "tel:" + number;

            Intent dial = new Intent(Intent.ACTION_DIAL, Uri.parse(sToDial));

            PackageManager manager = context.getPackageManager();
            List<ResolveInfo> info = manager.queryIntentActivities(dial, 0);
            if (!info.isEmpty()) {
                context.startActivity(dial);
            } else {
                Log.e("PhoneWidgetCommand", context.getResources().getText(R.string.mdkcommand_phone_error_nophoneapp).toString());
            }
        } else{
            Toast.makeText(context, context.getResources().getText(R.string.mdkcommand_phone_error_notiddle), Toast.LENGTH_SHORT).show();
        }

        return null;
    }

    @Override
    public void cancel() {
        // nothing to do
    }
}
