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

import android.app.Activity;
import android.app.Instrumentation;
import android.content.Intent;
import android.content.res.AssetManager;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;
import android.test.suitebuilder.annotation.LargeTest;
import android.util.AttributeSet;
import android.util.Log;
import android.util.Xml;

import com.soprasteria.movalysmdk.espresso.matcher.MdkMediaMatchers;
import com.soprasteria.movalysmdk.widget.media.MDKMedia;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.xmlpull.v1.XmlPullParser;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.action.ViewActions.scrollTo;
import static android.support.test.espresso.assertion.ViewAssertions.doesNotExist;
import static android.support.test.espresso.intent.Intents.intending;
import static android.support.test.espresso.intent.matcher.IntentMatchers.hasAction;
import static android.support.test.espresso.intent.matcher.IntentMatchers.hasData;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withText;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.allOf;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.notNullValue;

/**
 * Tests for MDKEnumImage widget.
 */
@RunWith(AndroidJUnit4.class)
@LargeTest
public class MediaTest {

    /**
     * Image test resource name.
     */
    public static final String MEDIA_TEST_ASSET = "mediaImageTest.png";


    /**
     * Activity used for these tests.
     */
    @Rule
    public ActivityTestRule<MediaActivity> mActivityRule = new ActivityTestRule<>(MediaActivity.class);

    /**
     * Tests that the image of a photo mdk media can be picked in the gallery.
     *//*
    @Test
    public void testImageFromGallery(){
        Intents.init();
        assertThat(mActivityRule.getActivity(), is(notNullValue()));

        //copy image asset to test device
        Uri uri = copyTestAsset();

        //prepare intent mock response
        pickPhotoIntentStub(uri);


        onView(allOf(withId(R.id.component_internal), isDescendantOfA(withId(R.id.mdkRichMedia_withLabelAndError)))).perform(click());
        onView(withText(R.string.mdkwidget_mdkmedia_choose_photo)).perform(click());

        Intents.release();

        onView(allOf(withId(R.id.component_internal), isDescendantOfA(withId(R.id.mdkRichMedia_withLabelAndError))))
                .check(matches(MdkMediaMatchers.mdkMediaWithUri(uri)));

    }
*/
    /**
     * Tests that the image of a photo mdk media can be captured by the camera.
     *//*
    @Test
    public void testImageFromCamera(){
        Intents.init();
        assertThat(mActivityRule.getActivity(), is(notNullValue()));

        //prepare intent mock response
        takePhotoIntentStub();

        onView(allOf(withId(R.id.component_internal), isDescendantOfA(withId(R.id.mdkRichMedia_withLabelAndError)))).perform(click());
        onView(withText(R.string.mdkwidget_mdkmedia_take_photo)).perform(click());

        Intents.release();

        onView(allOf(withId(R.id.component_internal), isDescendantOfA(withId(R.id.mdkRichMedia_withLabelAndError))))
                .check(matches(MdkMediaMatchers.mdkMediaHasUri()));

    }
*/
    /**
     * Tests that a read only mdkmedia does not trigger a menu on click.
     */
    @Test
    public void testReadOnly(){
        assertThat(mActivityRule.getActivity(), is(notNullValue()));
        onView(withId(R.id.mdkMedia_readonly)).perform(scrollTo(),click());
        onView(withText(R.string.mdkwidget_mdkmedia_choose_photo)).check(doesNotExist());
    }

    /**
     * Tests that the placeholder of a MDKMedia can be changed.
     */
    @Test
    public void testChangePlaceholder(){
        assertThat(mActivityRule.getActivity(), is(notNullValue()));

        XmlPullParser parser = mActivityRule.getActivity().getResources().getLayout(R.layout.test_mdkmedia);
        AttributeSet attributes = Xml.asAttributeSet(parser);

        MDKMedia media = new MDKMedia(mActivityRule.getActivity(), attributes);

        //Change placeholder
        media.setPlaceholder(R.drawable.enum_mobileos_android);

        //Check placeholder
        assertThat(media,MdkMediaMatchers.mdkMediaWithPlaceHolderRes(R.drawable.enum_mobileos_android));
    }


    /**
     * Stub for a gallery pick intent.
     * @param uri the uri of the file to pick
     */
    private void pickPhotoIntentStub(Uri uri) {
        Intent intent = new Intent();
        intent.setData(uri);
        Instrumentation.ActivityResult result = new Instrumentation.ActivityResult(Activity.RESULT_OK, intent);

        intending(allOf(
                hasData(MediaStore.Images.Media.EXTERNAL_CONTENT_URI),
                hasAction(Intent.ACTION_PICK)))
            .respondWith(result);
    }

    /**
     * Stub for a camera image capture intent.
     */
    private void takePhotoIntentStub() {
        Instrumentation.ActivityResult result = new Instrumentation.ActivityResult(Activity.RESULT_OK, null);

        intending(hasAction(MediaStore.ACTION_IMAGE_CAPTURE)).respondWith(result);
    }

    /**
     * Copies the image asset to the device's external storage folder.
     * @return the uri of the created file
     */
    private Uri copyTestAsset() {
        AssetManager assetManager = mActivityRule.getActivity().getAssets();
        String inFile = MEDIA_TEST_ASSET;

        InputStream in = null;
        OutputStream out = null;

        Uri outUri = null;

        try {
            in = assetManager.open(inFile);
            File outFile = new File(mActivityRule.getActivity().getExternalFilesDir(null), inFile);
            out = new FileOutputStream(outFile);
            copyFile(in, out);

            outUri = Uri.fromFile(outFile);
        } catch(IOException e) {
            Log.e("tag", "Failed to copy asset file: " + inFile, e);
        } finally {
            if (in != null) {
                try {
                    in.close();
                } catch (IOException e) {
                    Log.e("tag", "Failed to close input file: " + inFile, e);
                }
            }
            if (out != null) {
                try {
                    out.close();
                } catch (IOException e) {
                    Log.e("tag", "Failed to close output file: " + inFile, e);
                }
            }
        }
        return outUri;
    }

    /**
     * Executes the copy.
     * @param in the input stream
     * @param out the output stream
     * @throws IOException when the copy has failed
     */
    private void copyFile(InputStream in, OutputStream out) throws IOException {
        byte[] buffer = new byte[1024];
        int read;
        while((read = in.read(buffer)) != -1){
            out.write(buffer, 0, read);
        }
    }
}
