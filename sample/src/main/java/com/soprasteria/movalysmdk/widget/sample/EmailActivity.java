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
package com.soprasteria.movalysmdk.widget.sample;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.support.v4.content.LocalBroadcastManager;
import android.view.Menu;
import android.view.MenuItem;

import com.soprasteria.movalysmdk.widget.basic.delegate.MDKCommandDelegate;
import com.soprasteria.movalysmdk.widget.core.delegate.MDKWidgetDelegate;

/**
 * Test activity for the MDKRichEmail widget.
 */
public class EmailActivity extends AbstractWidgetTestableActivity {

    /** Broadcast reciver for disable action button. */
    private BroadcastReceiver receiver;
    /** active button. */
    private boolean actionActivated = false;

    @Override
    protected int[] getWidgetIds() {
        return new int[] {
                R.id.mdkRichEmail_withLabelAndError,
                R.id.mdkEmail_withErrorAndCommandOutside,
                R.id.mdkRichEmail_withoutLabel,
                R.id.mdkRichEmail_withCustomLayout,
                R.id.mdkRichEmail1_withSharedError,
                R.id.mdkRichEmail2_withSharedError,
                R.id.mdkRichEmail_withHelper
        };
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_email);

        receiver = new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {
                if (intent != null && intent.getIntExtra(MDKWidgetDelegate.EXTRA_WIDGET_ID, 0) == R.id.mdkEmail_withErrorAndCommandOutside) {
                    actionActivated = intent.getBooleanExtra(MDKWidgetDelegate.EXTRA_VALID, false);
                    invalidateOptionsMenu();
                }
            }
        };

        LocalBroadcastManager.getInstance(this).registerReceiver(receiver, new IntentFilter(getString(R.string.mdkwidget_enableboadcast)));

    }

    @Override
    protected void onDestroy() {
        LocalBroadcastManager.getInstance(this).unregisterReceiver(receiver);
        super.onDestroy();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        this.getMenuInflater().inflate(R.menu.menu_email, menu);
        menu.findItem(R.id.send_email).setEnabled(actionActivated);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId()== R.id.send_email) {
            Intent intent = new Intent(this.getString(R.string.mdkcommand_email_action));
            intent.putExtra(MDKCommandDelegate.REFERENCE_WIDGET, R.id.mdkEmail_withErrorAndCommandOutside);
            intent.putExtra(MDKCommandDelegate.COMMAND_WIDGET, "primary");
            LocalBroadcastManager.getInstance(this).sendBroadcast(intent);
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
