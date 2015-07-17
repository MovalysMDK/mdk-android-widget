package com.soprasteria.movalysmdk.espresso.matcher;

import android.content.res.Resources;
import android.support.annotation.IntDef;
import android.support.annotation.Nullable;
import android.support.annotation.StringRes;
import android.support.test.espresso.matcher.BoundedMatcher;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import org.hamcrest.Description;
import org.hamcrest.Matcher;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

/**
 * Espresso matchers for checking labels.
 */
public class MdkLabelMatchers {

    /**
     * Log tag.
     */
    private static final String LOG_TAG = "MdkLabelMatchers";

    /**
     * Create a matcher to check the label in the text of a view.
     * <p>If the view is mandatory, the mandatory string (*) is added to the string comparison.</p>
     * @param labelId label id.
     * @return matcher.
     */
    public static Matcher<View> withTextLabel(@StringRes final int labelId) {
        return withLabel(labelId, null, LABEL_TEXT);
    }

    /**
     * Create a matcher to check the label in the text of a view.
     * <p>If the view is mandatory, the mandatory string (*) is added to the string comparison.</p>
     * @param labelId label id.
     * @param mandatoryChar mandatory symbol if field is mandatory.
     * @return matcher.
     */
    public static Matcher<View> withTextLabel(@StringRes final int labelId, @StringRes final int mandatoryChar) {
        return withLabel(labelId, mandatoryChar, LABEL_TEXT);
    }

    /**
     * Create a matcher to check the label in the hint of a view.
     * <p>If the view is mandatory, the mandatory string (*) is added to the string comparison.</p>
     * @param labelId label id.
     * @return matcher.
     */
    public static Matcher<View> withHintLabel(@StringRes final int labelId ) {
        return withLabel(labelId, null, LABEL_HINT);
    }

    /**
     * Create a matcher to check the label in the hint of a view.
     * <p>If the view is mandatory, the mandatory string (*) is added to the string comparison.</p>
     * @param labelId label id.
     * @param mandatoryChar mandatory symbol if field is mandatory.
     * @return matcher.
     */
    public static Matcher<View> withHintLabel(@StringRes final int labelId, @StringRes final int mandatoryChar) {
        return withLabel(labelId, mandatoryChar, LABEL_HINT);
    }

    /**
     * Create a matcher to check the label.
     * <p>If the view is mandatory, the mandatory string (*) is added to the string comparison.</p>
     * @param labelId label id.
     * @param mandatoryChar mandatory symbol if field is mandatory.
     * @param labelLocation location of label (in text or hint)
     * @return matcher.
     */
    private static Matcher<View> withLabel(@StringRes final int labelId, @Nullable @StringRes final Integer mandatoryChar, @LabelLocation final int labelLocation) {
        return new LabelMatcher(labelId, mandatoryChar, labelLocation);
    }

    /**
     * Define the list of possible label locations
     */
    @IntDef({LABEL_TEXT, LABEL_HINT})
    @Retention(RetentionPolicy.SOURCE)
    public @interface LabelLocation {
        // nothint in the annotation interface
    }

    /**
     * Label is located in the text of the view.
     */
    public static final int LABEL_TEXT = 0;

    /**
     * Label is located in the hint of the view.
     */
    public static final int LABEL_HINT = 1;

    /**
     * Matcher for labels.
     */
    private static class LabelMatcher extends BoundedMatcher {

        /**
         * Label id.
         */
        private final int labelId;

        /**
         * Mandatory char.
         */
        private final Integer mandatoryChar;

        /**
         * Label location.
         */
        private final int labelLocation;

        /**
         * Resource names.
         */
        private String resourceName ;

        /**
         * Concat text.
         */
        private String expectedText ;

        /**
         * Constructor.
         * @param labelId string resource corresponding to the label.
         * @param mandatoryChar string resource representing the mandatory aspect.
         * @param labelLocation label location (hint or label).
         */
        public LabelMatcher(@StringRes int labelId, @Nullable @StringRes Integer mandatoryChar, @LabelLocation int labelLocation) {
            super(TextView.class);
            this.labelId = labelId;
            this.mandatoryChar = mandatoryChar;
            this.labelLocation = labelLocation;
        }

        @Override
        public void describeTo(Description description) {
            description.appendText("with string from label id: ");
            description.appendText(Integer.toString(labelId));

            if ( this.resourceName != null ) {
                description.appendText("[");
                description.appendText(this.resourceName);
                description.appendText(", mandatoryChar:");
                description.appendText(Integer.toString(mandatoryChar));
                description.appendText("]");
            }

            if ( this.expectedText != null ) {
                description.appendText(" value: ");
                description.appendText(this.expectedText);
            }

        }

        @Override
        public boolean matchesSafely(Object object) {
            TextView textView = (TextView) object;
            if ( this.expectedText == null ) {
                try {
                    StringBuilder text = new StringBuilder(textView.getResources().getString(labelId));
                    if ( mandatoryChar != null) {
                        text.append(textView.getResources().getString(mandatoryChar));
                    }

                    this.expectedText = text.toString();
                    this.resourceName = textView.getResources().getResourceEntryName(labelId);
                } catch (Resources.NotFoundException e) {
                    Log.e(LOG_TAG, "MdkViewMatchers.withCharSequence failure", e);
                }
            }

            CharSequence actualText = getActualText(textView);

            return null != this.expectedText && null != actualText?this.expectedText.equals(actualText.toString()):false;
        }

        /**
         * Return actual text depending on location (use either getHint() or getText()).
         * @param textView view.
         * @return actual text.
         */
        private CharSequence getActualText(TextView textView) {
            CharSequence actualText;
            if ( labelLocation == LABEL_TEXT ) {
                actualText = textView.getText();
            } else {
                actualText = textView.getHint();
            }
            return actualText;
        }
    }
}
