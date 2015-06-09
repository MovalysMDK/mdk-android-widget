package com.soprasteria.movalysmdk.widget.sample;

import android.test.ActivityInstrumentationTestCase2;

import com.soprasteria.movalysmdk.widget.standard.MDKEmail;
import com.soprasteria.movalysmdk.widget.standard.MDKRichEmail;

/**
 * Created by abelliard on 09/06/2015.
 */
public class EmailTest extends ActivityInstrumentationTestCase2<EmailActivity> {


    private EmailActivity mEmailActivity;
    private MDKRichEmail mRichEmail;
    private MDKEmail mEmail;

    public EmailTest() {
        super(EmailActivity.class);
    }

    @Override
    protected void setUp() throws Exception {
        super.setUp();
        mEmailActivity = getActivity();
        mRichEmail = (MDKRichEmail) mEmailActivity.findViewById(R.id.view);
        mEmail = (MDKEmail) mEmailActivity.findViewById(R.id.view2);
    }

    public void testPreconditions() {
        assertNotNull("Activity is null", mEmailActivity);
        assertNotNull("RichEmail widget is null", mRichEmail);
        assertNotNull("Email widget is null", mEmail);
    }

}
