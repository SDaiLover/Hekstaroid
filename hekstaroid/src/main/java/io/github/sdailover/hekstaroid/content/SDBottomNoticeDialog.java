/*
 * Hekstaroid Android Libraries by StephanusDai Developer
 * @link http://sdailover.github.io/
 * @mail team@sdailover.web.id
 * @copyright Copyright (c) 2023 StephanusDai Developer
 * @license http://sdailover.github.io/license.html
 */

package io.github.sdailover.hekstaroid.content;

/*
 * Hekstaroid Library Packages for Android by StephanusDai Developer
 * Email   : team@sdailover.web.id
 * Website : http://www.sdailover.web.id
 * (C) ID 2023 Stephanus Dai Developer (sdailover.github.io)
 * All rights reserved.
 *
 * Licensed under the Clause BSD License, Version 3.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://sdailover.github.io/license.html
 *
 * This software is provided by the STEPHANUS DAI DEVELOPER and
 * CONTRIBUTORS "AS IS" and Any Express or IMPLIED WARRANTIES, INCLUDING,
 * BUT NOT LIMITED TO, the implied warranties of merchantability and
 * fitness for a particular purpose are disclaimed in no event shall the
 * Stephanus Dai Developer or Contributors be liable for any direct,
 * indirect, incidental, special, exemplary, or consequential damages
 * arising in anyway out of the use of this software, even if advised
 * of the possibility of such damage.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
import android.content.Context;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.graphics.Typeface;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.text.PrecomputedText;
import android.view.View;
import android.view.ViewDebug;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.ColorInt;
import androidx.annotation.DrawableRes;
import androidx.annotation.InspectableProperty;
import androidx.annotation.LayoutRes;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.StringRes;

import io.github.sdailover.hekstaroid.R;
import io.github.sdailover.hekstaroid.annotation.RemotableViewMethod;
import io.github.sdailover.hekstaroid.annotation.UnsupportedAppUsage;
import io.github.sdailover.hekstaroid.maps.SDDialogInterface;
import io.github.sdailover.hekstaroid.utils.SDDeviceManager;

/**
 * <p>SDBottomNoticeDialog class for displaying bottom notice on the application.</p>
 *
 * <p>For initalize, you can use:</p>
 * <p> - {@link #SDBottomNoticeDialog(Context)}
 * Creates a dialog window that uses the default dialog theme.</p>
 * <p> - {@link #SDBottomNoticeDialog(Context context, int themeResId)}
 * Creates a dialog window that uses a custom dialog style.</p>
 *
 * <p>Initialize above requires prior settings in setting the
 * {@link #setBackground(String resName)}, {@link #setIconImage(String resName)},
 * {@link #setTitle(CharSequence)}, {@link #setMessage(CharSequence)}
 * <p>For make calling Dialog easier, you can use a function calling method such as
 * {@link android.widget.Toast}  with the following example:</p>
 *
 * <pre>
 *     SDBottomNoticeDialog(this).show("Title", "Message",
 *     "@drawable/ic_icon", "@drawable/ic_background");
 * </pre>
 *
 * <p>You can also set a callback function when the
 * {@link #setOnDismissListener(SDDialogInterface.OnDismissListener)} dialog event occurs.
 * If the message is an error, and you want to forcibly remove the program when dismissing.
 * Set forceExists true on the {@link #show} method to run it.</p>
 *
 * <p>See {@link #show(String title, String message,
 * String iconResName, String backgroundResName, boolean forceExists)} </p>
 *
 * @author Stephanus Bagus Saputra (wiefunk@stephanusdai.web.id)
 *       and other contributors. See credits file.
 */
public class SDBottomNoticeDialog extends SDBottomSheetDialog {
    private CharSequence titleViewText = DEFAULT_TITLE_TEXT;
    private CharSequence messageViewText = DEFAULT_MESSAGE_TEXT;
    private CharSequence copyrightViewText = DEFAULT_COPYRIGHT_TEXT;

    private @ColorInt int titleViewColor = DEFAULT_TITLE_COLOR;
    private @ColorInt int messageViewColor = DEFAULT_MESSAGE_COLOR;
    private @ColorInt int copyrightViewColor = DEFAULT_COPYRIGHT_COLOR;
    private ColorStateList titleViewColorList;
    private ColorStateList messageViewColorList;
    private ColorStateList copyrightViewColorList;
    private Typeface titleViewTypeface = Typeface.DEFAULT_BOLD;
    private Typeface messageViewTypeface = Typeface.DEFAULT;
    private Typeface copyrightViewTypeface = Typeface.DEFAULT;
    private boolean titleViewVisibled = true;
    private boolean messageViewVisibled = true;
    private boolean copyrightViewVisibled = true;
    private Drawable iconImageResDrawable;
    private boolean iconImageViewVisibled = true;
    private FrameLayout bindingLayout;
    private static final String DEFAULT_TITLE_TEXT = "Attention!";
    private static final String DEFAULT_MESSAGE_TEXT = "An error has occurred in the " +
            "application system, please contact the administrator to handle it.";
    private static final String DEFAULT_COPYRIGHT_TEXT = "Copyright (C) ID 2023 " +
            "Stephanus Dai Developer (sdailover.github.io)";
    private static final @ColorInt int DEFAULT_TITLE_COLOR = Color.parseColor("#FF1744");
    private static final @ColorInt int DEFAULT_MESSAGE_COLOR = Color.parseColor("#616161");
    private static final @ColorInt int DEFAULT_COPYRIGHT_COLOR = Color.parseColor("#7D7C7C");
    private static final String DEFAULT_RES_BACKGROUND = "@drawable/hekstaroid_screen_background_topround_light";
    private static final String DEFAULT_RES_ICON = "@android:drawable/ic_dialog_alert";

    /**
     * Creates a dialog window that uses the default dialog theme.
     * <p>
     * The supplied {@code context} is used to obtain the window manager and
     * base theme used to present the dialog.
     *
     * @param context the context in which the dialog should run
     */
    public SDBottomNoticeDialog(@NonNull Context context) {
        super(context);
    }

    /**
     * Creates a dialog window that uses a custom dialog style.
     * <p>
     * The supplied {@code context} is used to obtain the window manager and
     * base theme used to present the dialog.
     * <p>
     * The supplied {@code theme} is applied on top of the context's theme. See
     * <a href="{@docRoot}guide/topics/resources/available-resources.html#stylesandthemes">
     * Style and Theme Resources</a> for more information about defining and
     * using styles.
     *
     * @param context    the context in which the dialog should run
     * @param themeResId a style resource describing the theme to use for the
     *                   window, or {@code 0} to use the default dialog theme
     */
    public SDBottomNoticeDialog(@NonNull Context context, int themeResId) {
        super(context, themeResId);
    }

    protected SDBottomNoticeDialog(@NonNull Context context, boolean cancelable, OnCancelListener cancelListener) {
        super(context, cancelable, cancelListener);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.setContentViewInternal();
    }

    /**
     * not support method for SDBottomNoticeDialog
     */
    @Deprecated
    @UnsupportedAppUsage
    @Override
    public void setContentView(@LayoutRes int layoutResId) {
        this.setContentViewInternal();
    }

    /**
     * not support method for SDBottomNoticeDialog
     */
    @Deprecated
    @UnsupportedAppUsage
    @Override
    public void setContentView(View view) {
        this.setContentViewInternal();
    }

    /**
     * not support method for SDBottomNoticeDialog
     */
    @Deprecated
    @UnsupportedAppUsage
    @Override
    public void setContentView(View view, ViewGroup.LayoutParams params) {
        this.setContentViewInternal();
    }

    /**
     * Gets the icon image drawable
     *
     * <p>Reference see {@link ImageView#getDrawable()}
     *
     * @return The drawable used as the icon image for this view, if any. default is null.
     * @see #setIconImage(Drawable)
     */
    @Nullable
    @InspectableProperty
    public Drawable getIconImage() {
        if (bindingLayout != null) {
            ImageView iconImageLayout = (ImageView) bindingLayout.findViewById(R.id.hekstaroid_notice_icon);
            iconImageResDrawable = iconImageLayout.getDrawable();
        }
        return iconImageResDrawable;
    }

    /**
     * Sets the icon image by name's resource for this view.
     *
     * @param name the string name to getIdentifier {@link android.content.res.Resources}.
     * @author Stephanus Bagus Saputra (wiefunk@stephanusdai.web.id)
     */
    @RemotableViewMethod
    public void setIconImage(@NonNull String name) {
        Drawable drawable = SDDeviceManager.getDrawable(getContext(), name);
        iconImageResDrawable = drawable;
        UpdateContentViewComponents();
    }

    /**
     * Set the icon image dialog to a given resource. The resource should refer to
     * a Drawable object or 0 to remove the background.
     *
     * <p>Reference see {@link ImageView#setImageResource(int)}
     *
     * @param resid The identifier of the resource.
     *
     * @attr ref android.R.styleable#View_background
     */
    @RemotableViewMethod
    public void setIconImageResource(@DrawableRes int resid) {
        Drawable drawable = SDDeviceManager.getDrawable(getContext(), resid);
        iconImageResDrawable = drawable;
        UpdateContentViewComponents();
    }

    /**
     * Set the icon image dialog to a given Drawable, or remove the icon image.
     *
     * <p>Reference see {@link ImageView#setImageDrawable(Drawable)}
     *
     * @param drawable The Drawable to use as the background, or null to remove the
     *        background
     */
    @Deprecated
    public void setIconImage(@NonNull Drawable drawable) {
        iconImageResDrawable = drawable;
        //noinspection deprecation
        this.setIconImageDrawable(drawable);
    }

    /**
     * @deprecated use {@link #setIconImage(Drawable)} instead
     */
    @Deprecated
    public void setIconImageDrawable(@NonNull Drawable drawable) {
        iconImageResDrawable = drawable;
        UpdateContentViewComponents();
    }

    /**
     * Returns the visibility status for this view.
     *
     * @return the true if visible and false if not visible.
     */
    @InspectableProperty
    public boolean getIconImageVisible() {
        if (bindingLayout != null) {
            ImageView iconImageLayout = (ImageView) bindingLayout.findViewById(R.id.hekstaroid_notice_icon);
            int isViewVisibled = iconImageLayout.getVisibility();
            if (isViewVisibled == View.GONE) {
                iconImageViewVisibled = false;
            } else {
                iconImageViewVisibled = true;
            }
        }
        return iconImageViewVisibled;
    }

    /**
     * Set the visibility state of this view.
     *
     * @param visibled the true if visible and false if to hide.
     */
    @RemotableViewMethod
    public void setIconImageVisible(boolean visibled) {
        iconImageViewVisibled = visibled;
        UpdateContentViewComponents();
    }

    /**
     * Gets the text from Title of this view.
     *
     * <p>Reference see {@link TextView#getText()}
     *
     * @return The string used as the title text for this view, if any. default is null.
     *
     * @see #setTitle(CharSequence)
     */
    @Nullable
    @InspectableProperty
    public CharSequence getTitle() {
        if (bindingLayout != null) {
            TextView titleTextLayout = (TextView) bindingLayout.findViewById(R.id.hekstaroid_notice_title);
            titleViewText = titleTextLayout.getText();
        }
        return titleViewText;
    }

    /**
     * Sets the text Title to be displayed.
     *
     * @param caption text to be displayed
     * @author Stephanus Bagus Saputra (wiefunk@stephanusdai.web.id)
     */
    @RemotableViewMethod
    public final void setTitle(String caption) {
        titleViewText = caption;
        UpdateContentViewComponents();
    }

    /**
     * Sets the text to be displayed. Title <em>does not</em> accept
     * HTML-like formatting, which you can do with text strings in XML resource files.
     * To style your strings, attach android.text.style.* objects to a
     * {@link android.text.SpannableString}, or see the
     * <a href="{@docRoot}guide/topics/resources/available-resources.html#stringresources">
     * Available Resource Types</a> documentation for an example of setting
     * formatted text in the XML resource file.
     *
     * <p>When required, Title will use {@link android.text.Spannable.Factory} to create final or
     * intermediate {@link android.text.Spannable Spannables}. Likewise it will use
     * {@link android.text.Editable.Factory} to create final or intermediate
     * {@link android.text.Editable Editables}.
     *
     * <p>If the passed text is a {@link android.text.PrecomputedText} but the parameters used to create the
     * PrecomputedText mismatches with this Title, {@link IllegalArgumentException} is thrown. To ensure
     * the parameters match, you can call {@link TextView#setTextMetricsParams(PrecomputedText.Params)} before calling this.
     *
     * <p>Reference see {@link android.widget.TextView#setText(CharSequence)}
     *
     * @param text text to be displayed
     *
     * @attr ref android.R.styleable#Title_text
     * @throws IllegalArgumentException if the passed text is a {@see PrecomputedText} but the
     *                                  parameters used to create the PrecomputedText mismatches
     *                                  with this Title.
     */
    @RemotableViewMethod
    public final void setTitle(CharSequence text) {
        titleViewText = text;
        UpdateContentViewComponents();
    }

    /**
     * Sets the text to be displayed and the {@link android.widget.TextView.BufferType}.
     *
     * <p>When required, Title will use {@link android.text.Spannable.Factory} to create final or
     * intermediate {@link android.text.Spannable Spannables}. Likewise it will use
     * {@link android.text.Editable.Factory} to create final or intermediate
     * {@link android.text.Editable Editables}.
     *
     * <p>Subclasses overriding this method should ensure that the following post condition holds,
     * in order to guarantee the safety of the view's measurement and layout operations:
     * regardless of the input, after calling #setTitle both {@code mText} and {@code mTransformed}
     * will be different from {@code null}.
     *
     * <p>Reference see {@link android.widget.TextView#setText(CharSequence, TextView.BufferType)}
     *
     * @param text text to be displayed
     * @param type a {@link android.widget.TextView.BufferType} which defines whether the text is
     *              stored as a static text, styleable/spannable text, or editable text
     *
     * @see #setTitle(CharSequence)
     * @see android.widget.TextView.BufferType
     *
     * @attr ref android.R.styleable#Title_text
     * @attr ref android.R.styleable#Title_bufferType
     */
    public void setTitle(CharSequence text, TextView.BufferType type) {
        TextView renderText = new TextView(getContext());
        renderText.setText(text, type);
        titleViewText = renderText.getText();
    }

    /**
     * Sets the Title to display the specified slice of the specified
     * char array. You must promise that you will not change the contents
     * of the array except for right before another call to setTitle(),
     * since the Title has no way to know that the text
     * has changed and that it needs to invalidate and re-layout.
     *
     * <p>Reference see {@link android.widget.TextView#setText(char[], int, int)}
     *
     * @param text char array to be displayed
     * @param start start index in the char array
     * @param len length of char count after {@code start}
     */
    public final void setTitle(char[] text, int start, int len) {
        TextView renderText = new TextView(getContext());
        renderText.setText(text, start, len);
        titleViewText = renderText.getText();
        UpdateContentViewComponents();
    }

    /**
     * Sets the text to be displayed using a string resource identifier.
     *
     * <p>Reference see {@link android.widget.TextView#setText(int)}
     *
     * @param resid the resource identifier of the string resource to be displayed
     *
     * @see #setTitle(CharSequence)
     *
     * @attr ref android.R.styleable#Title_text
     */
    @RemotableViewMethod
    public final void setTitle(@StringRes int resid) {
        titleViewText = SDDeviceManager.getResourceText(getContext(), resid);
        UpdateContentViewComponents();
    }

    /**
     * <p/>
     * Sets the text to be displayed using a string resource identifier and the
     * {@link android.widget.TextView.BufferType}.
     *
     * <p>When required, Title will use {@link android.text.Spannable.Factory} to create final or
     * intermediate {@link android.text.Spannable Spannables}. Likewise it will use
     * {@link android.text.Editable.Factory} to create final or intermediate
     * {@link android.text.Editable Editables}.
     *
     * <p>Reference see {@link android.widget.TextView#setText(int, TextView.BufferType)}
     *
     * @param resid the resource identifier of the string resource to be displayed
     * @param type a {@link android.widget.TextView.BufferType} which defines whether the text is
     *              stored as a static text, styleable/spannable text, or editable text
     *
     * @see #setTitle(int)
     * @see #setTitle(CharSequence)
     * @see android.widget.TextView.BufferType
     *
     * @attr ref android.R.styleable#Title_text
     * @attr ref android.R.styleable#Title_bufferType
     */
    public final void setTitle(@StringRes int resid, TextView.BufferType type) {
        TextView renderText = new TextView(getContext());
        renderText.setText(resid, type);
        titleViewText = renderText.getText();
        UpdateContentViewComponents();
    }

    /**
     * Return the current color selected for normal text.
     *
     * <p>Reference see {@link TextView#getCurrentTextColor()}
     *
     * @return Returns the current text color.
     */
    @ColorInt
    public final int getCurrentTitleColor() {
        if (bindingLayout != null) {
            TextView titleTextLayout = (TextView) bindingLayout.findViewById(R.id.hekstaroid_notice_title);
            titleViewColor = titleTextLayout.getCurrentTextColor();
        }
        return titleViewColor;
    }

    /**
     * Gets the text colors for the different states (normal, selected, focused) of the Title.
     *
     * <p>Reference see {@link TextView#getTextColors()}
     *
     * @see #setTitleColor(ColorStateList)
     * @see #setTitleColor(int)
     *
     * @attr ref android.R.styleable#Title_textColor
     */
    @Nullable
    @InspectableProperty(name = "textColor")
    public final ColorStateList getTitleColors() {
        if (bindingLayout != null) {
            TextView titleTextLayout = (TextView) bindingLayout.findViewById(R.id.hekstaroid_notice_title);
            titleViewColorList = titleTextLayout.getTextColors();
        }
        return titleViewColorList;
    }

    /**
     * Sets the text color for all the states (normal, selected,
     * focused) to be this color.
     *
     * <p>Reference see {@link TextView#setTextColor(int)}
     *
     * @param color A color value in the form 0xAARRGGBB.
     * Do not pass a resource ID. To get a color value from a resource ID, call
     * {@see android.support.v4.content.ContextCompat#getColor(Context, int) getColor}.
     *
     * @see #setTitleColor(ColorStateList)
     * @see #getTitleColors()
     *
     * @attr ref android.R.styleable#Title_textColor
     */
    @RemotableViewMethod
    public void setTitleColor(@ColorInt int color) {
        titleViewColor = color;
        UpdateContentViewComponents();
    }

    /**
     * Sets the text color.
     *
     * <p>Reference see {@link TextView#setTextColor(ColorStateList)}
     *
     * @see #setTitleColor(int)
     * @see #getTitleColors()
     *
     * @attr ref android.R.styleable#Title_textColor
     */
    @RemotableViewMethod
    public void setTitleColor(ColorStateList colors) {
        titleViewColor = colors.getDefaultColor();
        titleViewColorList = colors;
        UpdateContentViewComponents();
    }

    /** @hide */
    @ViewDebug.ExportedProperty(category = "text", mapping = {
            @ViewDebug.IntToString(from = Typeface.NORMAL, to = "NORMAL"),
            @ViewDebug.IntToString(from = Typeface.BOLD, to = "BOLD"),
            @ViewDebug.IntToString(from = Typeface.ITALIC, to = "ITALIC"),
            @ViewDebug.IntToString(from = Typeface.BOLD_ITALIC, to = "BOLD_ITALIC")
    })
    public int getTitleTypefaceStyle() {
        if (bindingLayout != null) {
            TextView titleTextLayout = (TextView) bindingLayout.findViewById(R.id.hekstaroid_notice_title);
            titleViewTypeface = titleTextLayout.getTypeface();
            return titleViewTypeface != null ? titleViewTypeface.getStyle() : Typeface.NORMAL;
        }
        return Typeface.NORMAL;
    }

    /**
     * Gets the current {@link Typeface} that is used to style the text.
     * @return The current Typeface. Default is null.
     *
     * <p>Reference see {@link TextView#getTypeface()}
     * @see #setTitleTypeface(Typeface)     *
     * @attr ref android.R.styleable#TextView_fontFamily
     * @attr ref android.R.styleable#TextView_typeface
     * @attr ref android.R.styleable#TextView_textStyle
     */
    @Nullable
    @InspectableProperty
    public Typeface getTitleTypeface() {
        if (bindingLayout != null) {
            TextView titleTextLayout = (TextView) bindingLayout.findViewById(R.id.hekstaroid_notice_title);
            titleViewTypeface = titleTextLayout.getTypeface();
        }
        return titleViewTypeface;
    }

    /**
     * Sets the typeface and style in which the text should be displayed.
     * Note that not all Typeface families actually have bold and italic
     * variants, so you may need to use
     * {@link #setTitleTypeface(Typeface, int)} to get the appearance
     * that you actually want.
     *
     * <p>Reference see {@link TextView#setTypeface(Typeface)}
     * @see #getTitleTypeface()
     * @attr ref android.R.styleable#TextView_fontFamily
     * @attr ref android.R.styleable#TextView_typeface
     * @attr ref android.R.styleable#TextView_textStyle
     */
    public void setTitleTypeface(@Nullable Typeface tf) {
        titleViewTypeface = tf;
        UpdateContentViewComponents();
    }

    /**
     * Sets the typeface and style in which the text should be displayed,
     * and turns on the fake bold and italic bits in the Paint if the
     * Typeface that you provided does not have all the bits in the
     * style that you specified.
     *
     * <p>Reference see {@link TextView#setTypeface(Typeface,int)}
     * @attr ref android.R.styleable#TextView_typeface
     * @attr ref android.R.styleable#TextView_textStyle
     */
    public void setTitleTypeface(@Nullable Typeface tf, int style) {
        TextView renderText = new TextView(getContext());
        renderText.setTypeface(tf, style);
        titleViewTypeface = renderText.getTypeface();
        UpdateContentViewComponents();
    }

    /**
     * Returns the visibility status for this view.
     *
     * @return the true if visible and false if not visible.
     */
    @InspectableProperty
    public boolean getTitleVisible() {
        if (bindingLayout != null) {
            TextView titleTextLayout = (TextView) bindingLayout.findViewById(R.id.hekstaroid_notice_title);
            int isViewVisibled = titleTextLayout.getVisibility();
            if (isViewVisibled == View.GONE) {
                titleViewVisibled = false;
            } else {
                titleViewVisibled = true;
            }
        }
        return titleViewVisibled;
    }

    /**
     * Set the visibility state of this view.
     *
     * @param visibled the true if visible and false if to hide.
     */
    @RemotableViewMethod
    public void setTitleVisible(boolean visibled) {
        titleViewVisibled = visibled;
        UpdateContentViewComponents();
    }

    /**
     * Gets the text from Message of this view.
     *
     * <p>Reference see {@link TextView#getText()}
     *
     * @return The string used as the message text for this view, if any. default is null.
     *
     * @see #setMessage(CharSequence)
     */
    @Nullable
    @InspectableProperty
    public CharSequence getMessage() {
        if (bindingLayout != null) {
            TextView messageTextLayout = (TextView) bindingLayout.findViewById(R.id.hekstaroid_notice_message);
            messageViewText = messageTextLayout.getText();
        }
        return messageViewText;
    }

    /**
     * Sets the text Message to be displayed.
     *
     * @param description text to be displayed
     * @author Stephanus Bagus Saputra (wiefunk@stephanusdai.web.id)
     */
    @RemotableViewMethod
    public final void setMessage(String description) {
        messageViewText = description;
        UpdateContentViewComponents();
    }

    /**
     * Sets the text to be displayed. Message <em>does not</em> accept
     * HTML-like formatting, which you can do with text strings in XML resource files.
     * To style your strings, attach android.text.style.* objects to a
     * {@link android.text.SpannableString}, or see the
     * <a href="{@docRoot}guide/topics/resources/available-resources.html#stringresources">
     * Available Resource Types</a> documentation for an example of setting
     * formatted text in the XML resource file.
     *
     * <p>When required, Message will use {@link android.text.Spannable.Factory} to create final or
     * intermediate {@link android.text.Spannable Spannables}. Likewise it will use
     * {@link android.text.Editable.Factory} to create final or intermediate
     * {@link android.text.Editable Editables}.
     *
     * <p>If the passed text is a {@see PrecomputedText} but the parameters used to create the
     * PrecomputedText mismatches with this Message, IllegalArgumentException is thrown. To ensure
     * the parameters match, you can call {@see TextView#setMessageMetricsParams} before calling this.
     *
     * <p>Reference see {@link android.widget.TextView#setText(CharSequence)}
     *
     * @param text text to be displayed
     *
     * @attr ref android.R.styleable#Message_text
     * @throws IllegalArgumentException if the passed text is a {@see PrecomputedText} but the
     *                                  parameters used to create the PrecomputedText mismatches
     *                                  with this Message.
     */
    @RemotableViewMethod
    public final void setMessage(CharSequence text) {
        messageViewText = text;
        UpdateContentViewComponents();
    }

    /**
     * Sets the text to be displayed and the {@link android.widget.TextView.BufferType}.
     *
     * <p>When required, Message will use {@link android.text.Spannable.Factory} to create final or
     * intermediate {@link android.text.Spannable Spannables}. Likewise it will use
     * {@link android.text.Editable.Factory} to create final or intermediate
     * {@link android.text.Editable Editables}.
     *
     * <p>Subclasses overriding this method should ensure that the following post condition holds,
     * in order to guarantee the safety of the view's measurement and layout operations:
     * regardless of the input, after calling #setMessage both {@code mText} and {@code mTransformed}
     * will be different from {@code null}.
     *
     * <p>Reference see {@link android.widget.TextView#setText(CharSequence, TextView.BufferType)}
     *
     * @param text text to be displayed
     * @param type a {@link android.widget.TextView.BufferType} which defines whether the text is
     *              stored as a static text, styleable/spannable text, or editable text
     *
     * @see #setMessage(CharSequence)
     * @see android.widget.TextView.BufferType
     *
     * @attr ref android.R.styleable#Message_text
     * @attr ref android.R.styleable#Message_bufferType
     */
    public void setMessage(CharSequence text, TextView.BufferType type) {
        TextView renderText = new TextView(getContext());
        renderText.setText(text, type);
        messageViewText = renderText.getText();
        UpdateContentViewComponents();
    }

    /**
     * Sets the Message to display the specified slice of the specified
     * char array. You must promise that you will not change the contents
     * of the array except for right before another call to setMessage(),
     * since the Message has no way to know that the text
     * has changed and that it needs to invalidate and re-layout.
     *
     * <p>Reference see {@link android.widget.TextView#setText(char[], int, int)}
     *
     * @param text char array to be displayed
     * @param start start index in the char array
     * @param len length of char count after {@code start}
     */
    public final void setMessage(char[] text, int start, int len) {
        TextView renderText = new TextView(getContext());
        renderText.setText(text, start, len);
        messageViewText = renderText.getText();
        UpdateContentViewComponents();
    }

    /**
     * Sets the text to be displayed using a string resource identifier.
     *
     * <p>Reference see {@link android.widget.TextView#setText(int)}
     *
     * @param resid the resource identifier of the string resource to be displayed
     *
     * @see #setMessage(CharSequence)
     *
     * @attr ref android.R.styleable#Message_text
     */
    @RemotableViewMethod
    public final void setMessage(@StringRes int resid) {
        messageViewText = SDDeviceManager.getResourceText(getContext(), resid);
        UpdateContentViewComponents();
    }

    /**
     * Sets the text to be displayed using a string resource identifier and the
     * {@link android.widget.TextView.BufferType}.
     *
     * <p>When required, Message will use {@link android.text.Spannable.Factory} to create final or
     * intermediate {@link android.text.Spannable Spannables}. Likewise it will use
     * {@link android.text.Editable.Factory} to create final or intermediate
     * {@link android.text.Editable Editables}.
     *
     * <p>Reference see {@link android.widget.TextView#setText(int, TextView.BufferType)}
     *
     * @param resid the resource identifier of the string resource to be displayed
     * @param type a {@link android.widget.TextView.BufferType} which defines whether the text is
     *              stored as a static text, styleable/spannable text, or editable text
     *
     * @see #setMessage(int)
     * @see #setMessage(CharSequence)
     * @see android.widget.TextView.BufferType
     *
     * @attr ref android.R.styleable#Message_text
     * @attr ref android.R.styleable#Message_bufferType
     */
    public final void setMessage(@StringRes int resid, TextView.BufferType type) {
        TextView renderText = new TextView(getContext());
        renderText.setText(resid, type);
        messageViewText = renderText.getText();
        UpdateContentViewComponents();
    }

    /**
     * Return the current color selected for normal text.
     *
     * <p>Reference see {@link TextView#getCurrentTextColor()}
     *
     * @return Returns the current text color.
     */
    @ColorInt
    public final int getCurrentMessageColor() {
        if (bindingLayout != null) {
            TextView messageTextLayout = (TextView) bindingLayout.findViewById(R.id.hekstaroid_notice_message);
            messageViewColor = messageTextLayout.getCurrentTextColor();
        }
        return messageViewColor;
    }

    /**
     * Sets the text color for all the states (normal, selected,
     * focused) to be this color.
     *
     * <p>Reference see {@link android.widget.TextView#setTextColor(int)}
     *
     * @param color A color value in the form 0xAARRGGBB.
     * Do not pass a resource ID. To get a color value from a resource ID, call
     * {@see android.support.v4.content.ContextCompat#getColor(Context, int) getColor}.
     *
     * @see #setMessageColor(ColorStateList)
     * @see #getMessageColors()
     *
     * @attr ref android.R.styleable#Title_textColor
     */
    @RemotableViewMethod
    public void setMessageColor(@ColorInt int color) {
        messageViewColor = color;
        UpdateContentViewComponents();
    }

    /**
     * Gets the text colors for the different states (normal, selected, focused) of the Title.
     *
     * <p>Reference see {@link TextView#getTextColors()}
     *
     * @see #setMessageColor(ColorStateList)
     * @see #setMessageColor(int)
     *
     * @attr ref android.R.styleable#Title_textColor
     */
    @Nullable
    @InspectableProperty(name = "textColor")
    public final ColorStateList getMessageColors() {
        if (bindingLayout != null) {
            TextView messageTextLayout = (TextView) bindingLayout.findViewById(R.id.hekstaroid_notice_message);
            messageViewColorList = messageTextLayout.getTextColors();
        }
        return messageViewColorList;
    }

    /**
     * Sets the text color.
     *
     * <p/>
     * Reference see {@link android.widget.TextView#setTextColor(ColorStateList)}
     *
     * @see #setMessageColor(int)
     * @see #getMessageColors()
     *
     * @attr ref android.R.styleable#Title_textColor
     */
    @RemotableViewMethod
    public void setMessageColor(ColorStateList colors) {
        messageViewColor = colors.getDefaultColor();
        messageViewColorList = colors;
        UpdateContentViewComponents();
    }

    /** @hide */
    @ViewDebug.ExportedProperty(category = "text", mapping = {
            @ViewDebug.IntToString(from = Typeface.NORMAL, to = "NORMAL"),
            @ViewDebug.IntToString(from = Typeface.BOLD, to = "BOLD"),
            @ViewDebug.IntToString(from = Typeface.ITALIC, to = "ITALIC"),
            @ViewDebug.IntToString(from = Typeface.BOLD_ITALIC, to = "BOLD_ITALIC")
    })
    public int getMessageTypefaceStyle() {
        if (bindingLayout != null) {
            TextView messageTextLayout = (TextView) bindingLayout.findViewById(R.id.hekstaroid_notice_message);
            messageViewTypeface = messageTextLayout.getTypeface();
            return messageViewTypeface != null ? messageViewTypeface.getStyle() : Typeface.NORMAL;
        }
        return Typeface.NORMAL;
    }

    /**
     * Gets the current {@link Typeface} that is used to style the text.
     * @return The current Typeface. Default is null.
     *
     * <p>Reference see {@link TextView#getTypeface()}
     * @see #setMessageTypeface(Typeface)     *
     * @attr ref android.R.styleable#TextView_fontFamily
     * @attr ref android.R.styleable#TextView_typeface
     * @attr ref android.R.styleable#TextView_textStyle
     */
    @Nullable
    @InspectableProperty
    public Typeface getMessageTypeface() {
        if (bindingLayout != null) {
            TextView messageTextLayout = (TextView) bindingLayout.findViewById(R.id.hekstaroid_notice_message);
            messageViewTypeface = messageTextLayout.getTypeface();
        }
        return messageViewTypeface;
    }

    /**
     * Sets the typeface and style in which the text should be displayed.
     * Note that not all Typeface families actually have bold and italic
     * variants, so you may need to use
     * {@link #setMessageTypeface(Typeface, int)} to get the appearance
     * that you actually want.
     *
     * <p>Reference see {@link TextView#setTypeface(Typeface)}
     * @see #getMessageTypeface()
     * @attr ref android.R.styleable#TextView_fontFamily
     * @attr ref android.R.styleable#TextView_typeface
     * @attr ref android.R.styleable#TextView_textStyle
     */
    public void setMessageTypeface(@Nullable Typeface tf) {
        messageViewTypeface = tf;
        UpdateContentViewComponents();
    }

    /**
     * Sets the typeface and style in which the text should be displayed,
     * and turns on the fake bold and italic bits in the Paint if the
     * Typeface that you provided does not have all the bits in the
     * style that you specified.
     *
     * <p>Reference see {@link TextView#setTypeface(Typeface,int)}
     * @attr ref android.R.styleable#TextView_typeface
     * @attr ref android.R.styleable#TextView_textStyle
     */
    public void setMessageTypeface(@Nullable Typeface tf, int style) {
        TextView renderText = new TextView(getContext());
        renderText.setTypeface(tf, style);
        messageViewTypeface = renderText.getTypeface();
        UpdateContentViewComponents();
    }

    /**
     * Returns the visibility status for this view.
     *
     * @return the true if visible and false if not visible.
     */
    @InspectableProperty
    public boolean getMessageVisible() {
        if (bindingLayout != null) {
            TextView messageTextLayout = (TextView) bindingLayout.findViewById(R.id.hekstaroid_notice_message);
            int isViewVisibled = messageTextLayout.getVisibility();
            if (isViewVisibled == View.GONE) {
                messageViewVisibled = false;
            } else {
                messageViewVisibled = true;
            }
        }
        return messageViewVisibled;
    }

    /**
     * Set the visibility state of this view.
     *
     * @param visibled the true if visible and false if to hide.
     */
    @RemotableViewMethod
    public void setMessageVisible(boolean visibled) {
        messageViewVisibled = visibled;
        UpdateContentViewComponents();
    }

    /**
     * Gets the text from Copyright of this view.
     *
     * <p>Reference see {@link TextView#getText()}
     *
     * @return The string used as the copyright text for this view, if any. default is null.
     *
     * @see #setCopyright(CharSequence)
     */
    @Nullable
    @InspectableProperty
    public CharSequence getCopyright() {
        if (bindingLayout != null) {
            TextView copyrightTextLayout = (TextView) bindingLayout.findViewById(R.id.hekstaroid_notice_copyright);
            copyrightViewText = copyrightTextLayout.getText();
        }
        return copyrightViewText;
    }

    /**
     * Sets the text Copyright to be displayed.
     *
     * @param marked text to be displayed
     * @author Stephanus Bagus Saputra (wiefunk@stephanusdai.web.id)
     */
    @RemotableViewMethod
    public final void setCopyright(String marked) {
        copyrightViewText = marked;
        UpdateContentViewComponents();
    }

    /**
     * Sets the text to be displayed. Copyright <em>does not</em> accept
     * HTML-like formatting, which you can do with text strings in XML resource files.
     * To style your strings, attach android.text.style.* objects to a
     * {@link android.text.SpannableString}, or see the
     * <a href="{@docRoot}guide/topics/resources/available-resources.html#stringresources">
     * Available Resource Types</a> documentation for an example of setting
     * formatted text in the XML resource file.
     *
     * <p>When required, Copyright will use {@link android.text.Spannable.Factory} to create final or
     * intermediate {@link android.text.Spannable Spannables}. Likewise it will use
     * {@link android.text.Editable.Factory} to create final or intermediate
     * {@link android.text.Editable Editables}.
     *
     * If the passed text is a {@see PrecomputedText} but the parameters used to create the
     * PrecomputedText mismatches with this Copyright, IllegalArgumentException is thrown. To ensure
     * the parameters match, you can call {@see TextView#setCopyrightMetricsParams} before calling this.
     *
     * <p>Reference see {@link android.widget.TextView#setText(CharSequence)}
     * @param text text to be displayed
     * @attr ref android.R.styleable#Copyright_text
     * @throws IllegalArgumentException if the passed text is a {@see PrecomputedText} but the
     *                                  parameters used to create the PrecomputedText mismatches
     *                                  with this Copyright.
     */
    @RemotableViewMethod
    public final void setCopyright(CharSequence text) {
        copyrightViewText = text;
        UpdateContentViewComponents();
    }

    /**
     * Sets the text to be displayed and the {@link android.widget.TextView.BufferType}.
     *
     * <p>When required, Copyright will use {@link android.text.Spannable.Factory} to create final or
     * intermediate {@link android.text.Spannable Spannables}. Likewise it will use
     * {@link android.text.Editable.Factory} to create final or intermediate
     * {@link android.text.Editable Editables}.
     *
     * <p>Subclasses overriding this method should ensure that the following post condition holds,
     * in order to guarantee the safety of the view's measurement and layout operations:
     * regardless of the input, after calling #setCopyright both {@code mText} and {@code mTransformed}
     * will be different from {@code null}.
     *
     * <p>Reference see {@link android.widget.TextView#setText(CharSequence, TextView.BufferType)}
     *
     * @param text text to be displayed
     * @param type a {@link android.widget.TextView.BufferType} which defines whether the text is
     *              stored as a static text, styleable/spannable text, or editable text
     *
     * @see #setCopyright(CharSequence)
     * @see android.widget.TextView.BufferType
     *
     * @attr ref android.R.styleable#Copyright_text
     * @attr ref android.R.styleable#Copyright_bufferType
     */
    public void setCopyright(CharSequence text, TextView.BufferType type) {
        TextView renderText = new TextView(getContext());
        renderText.setText(text, type);
        copyrightViewText = renderText.getText();
        UpdateContentViewComponents();
    }

    /**
     * Sets the Copyright to display the specified slice of the specified
     * char array. You must promise that you will not change the contents
     * of the array except for right before another call to setCopyright(),
     * since the Copyright has no way to know that the text
     * has changed and that it needs to invalidate and re-layout.
     *
     * <p>Reference see {@link android.widget.TextView#setText(char[], int, int)}
     * @param text char array to be displayed
     * @param start start index in the char array
     * @param len length of char count after {@code start}
     */
    public final void setCopyright(char[] text, int start, int len) {
        TextView renderText = new TextView(getContext());
        renderText.setText(text, start, len);
        copyrightViewText = renderText.getText();
        UpdateContentViewComponents();
    }

    /**
     * Sets the text to be displayed using a string resource identifier.
     *
     * <p>Reference see {@link android.widget.TextView#setText(int)}
     *
     * @param resid the resource identifier of the string resource to be displayed
     * @see #setCopyright(CharSequence)
     * @attr ref android.R.styleable#Copyright_text
     */
    @RemotableViewMethod
    public final void setCopyright(@StringRes int resid) {
        copyrightViewText = SDDeviceManager.getResourceText(getContext(), resid);
        UpdateContentViewComponents();
    }

    /**
     * Sets the text to be displayed using a string resource identifier and the
     * {@link android.widget.TextView.BufferType}.
     *
     * <p>When required, Copyright will use {@link android.text.Spannable.Factory} to create final or
     * intermediate {@link android.text.Spannable Spannables}. Likewise it will use
     * {@link android.text.Editable.Factory} to create final or intermediate
     * {@link android.text.Editable Editables}.
     *
     * <p>Reference see {@link android.widget.TextView#setText(int, TextView.BufferType)}
     *
     * @param resid the resource identifier of the string resource to be displayed
     * @param type a {@link android.widget.TextView.BufferType} which defines whether the text is
     *              stored as a static text, styleable/spannable text, or editable text
     *
     * @see #setCopyright(int)
     * @see #setCopyright(CharSequence)
     * @see android.widget.TextView.BufferType
     *
     * @attr ref android.R.styleable#Copyright_text
     * @attr ref android.R.styleable#Copyright_bufferType
     */
    public final void setCopyright(@StringRes int resid, TextView.BufferType type) {
        TextView renderText = new TextView(getContext());
        renderText.setText(resid, type);
        copyrightViewText = renderText.getText();
        UpdateContentViewComponents();
    }

    /**
     * Return the current color selected for normal text.
     *
     * <p>Reference see {@link TextView#getCurrentTextColor()}
     *
     * @return Returns the current text color.
     */
    @ColorInt
    public final int getCurrentCopyrightColor() {
        if (bindingLayout != null) {
            TextView copyrightTextLayout = (TextView) bindingLayout.findViewById(R.id.hekstaroid_notice_copyright);
            copyrightViewColor = copyrightTextLayout.getCurrentTextColor();
        }
        return copyrightViewColor;
    }

    /**
     * Sets the text color for all the states (normal, selected,
     * focused) to be this color.
     *
     * <p>Reference see {@link android.widget.TextView#setTextColor(int)}
     *
     * @param color A color value in the form 0xAARRGGBB.
     * Do not pass a resource ID. To get a color value from a resource ID, call
     * {@see android.support.v4.content.ContextCompat#getColor(Context, int) getColor}.
     *
     * @see #setCopyrightColor(ColorStateList)
     * @see #getCopyrightColors()
     *
     * @attr ref android.R.styleable#Title_textColor
     */
    @RemotableViewMethod
    public void setCopyrightColor(@ColorInt int color) {
        copyrightViewColor = color;
        UpdateContentViewComponents();
    }

    /**
     * Gets the text colors for the different states (normal, selected, focused) of the Title.
     *
     * <p>Reference see {@link TextView#getTextColors()}
     *
     * @see #setCopyrightColor(ColorStateList)
     * @see #setCopyrightColor(int)
     *
     * @attr ref android.R.styleable#Title_textColor
     */
    @Nullable
    @InspectableProperty(name = "textColor")
    public final ColorStateList getCopyrightColors() {
        if (bindingLayout != null) {
            TextView copyrightTextLayout = (TextView) bindingLayout.findViewById(R.id.hekstaroid_notice_copyright);
            copyrightViewColorList = copyrightTextLayout.getTextColors();
        }
        return copyrightViewColorList;
    }

    /**
     * Sets the text color.
     *
     * <p>Reference see {@link android.widget.TextView#setTextColor(ColorStateList)}
     *
     * @see #setCopyrightColor(int)
     * @see #getCopyrightColors()
     *
     * @attr ref android.R.styleable#Title_textColor
     */
    @RemotableViewMethod
    public void setCopyrightColor(ColorStateList colors) {
        copyrightViewColor = colors.getDefaultColor();
        copyrightViewColorList = colors;
        UpdateContentViewComponents();
    }

    /**
     * Gets the current {@link Typeface} that is used to style the text.
     * @return The current Typeface. Default is null.
     *
     * <p>Reference see {@link TextView#getTypeface()}
     * @see #setCopyrightTypeface(Typeface)     *
     * @attr ref android.R.styleable#TextView_fontFamily
     * @attr ref android.R.styleable#TextView_typeface
     * @attr ref android.R.styleable#TextView_textStyle
     */
    @Nullable
    @InspectableProperty
    public Typeface getCopyrightTypeface() {
        if (bindingLayout != null) {
            TextView copyrightTextLayout = (TextView) bindingLayout.findViewById(R.id.hekstaroid_notice_copyright);
            copyrightViewTypeface = copyrightTextLayout.getTypeface();
        }
        return copyrightViewTypeface;
    }

    /** @hide */
    @ViewDebug.ExportedProperty(category = "text", mapping = {
            @ViewDebug.IntToString(from = Typeface.NORMAL, to = "NORMAL"),
            @ViewDebug.IntToString(from = Typeface.BOLD, to = "BOLD"),
            @ViewDebug.IntToString(from = Typeface.ITALIC, to = "ITALIC"),
            @ViewDebug.IntToString(from = Typeface.BOLD_ITALIC, to = "BOLD_ITALIC")
    })
    public int getCopyrightTypefaceStyle() {
        if (bindingLayout != null) {
            TextView copyrightTextLayout = (TextView) bindingLayout.findViewById(R.id.hekstaroid_notice_copyright);
            copyrightViewTypeface = copyrightTextLayout.getTypeface();
            return copyrightViewTypeface != null ? copyrightViewTypeface.getStyle() : Typeface.NORMAL;
        }
        return Typeface.NORMAL;
    }

    /**
     * Sets the typeface and style in which the text should be displayed.
     * Note that not all Typeface families actually have bold and italic
     * variants, so you may need to use
     * {@link #setCopyrightTypeface(Typeface, int)} to get the appearance
     * that you actually want.
     *
     * <p>Reference see {@link TextView#setTypeface(Typeface)}
     * @see #getCopyrightTypeface()
     * @attr ref android.R.styleable#TextView_fontFamily
     * @attr ref android.R.styleable#TextView_typeface
     * @attr ref android.R.styleable#TextView_textStyle
     */
    public void setCopyrightTypeface(@Nullable Typeface tf) {
        copyrightViewTypeface = tf;
        UpdateContentViewComponents();
    }

    /**
     * Sets the typeface and style in which the text should be displayed,
     * and turns on the fake bold and italic bits in the Paint if the
     * Typeface that you provided does not have all the bits in the
     * style that you specified.
     *
     * <p>Reference see {@link TextView#setTypeface(Typeface,int)}
     * @attr ref android.R.styleable#TextView_typeface
     * @attr ref android.R.styleable#TextView_textStyle
     */
    public void setCopyrightTypeface(@Nullable Typeface tf, int style) {
        TextView renderText = new TextView(getContext());
        renderText.setTypeface(tf, style);
        copyrightViewTypeface = renderText.getTypeface();
        UpdateContentViewComponents();
    }

    /**
     * Returns the visibility status for this view.
     *
     * @return the true if visible and false if not visible.
     */
    @InspectableProperty
    public boolean getCopyrightVisible() {
        if (bindingLayout != null) {
            TextView copyrightTextLayout = (TextView) bindingLayout.findViewById(R.id.hekstaroid_notice_copyright);
            int isViewVisibled = copyrightTextLayout.getVisibility();
            if (isViewVisibled == View.GONE) {
                copyrightViewVisibled = false;
            } else {
                copyrightViewVisibled = true;
            }
        }
        return copyrightViewVisibled;
    }

    /**
     * Set the visibility state of this view.
     *
     * @param visibled the true if visible and false if to hide.
     */
    @RemotableViewMethod
    public void setCopyrightVisible(boolean visibled) {
        copyrightViewVisibled = visibled;
        UpdateContentViewComponents();
    }

    /**
     * Start the dialog and display it on screen.  The window is placed in the
     * application layer and opaque.  Note that you should not override this
     * method to do initialization when the dialog is shown, instead implement
     * that in {@link #onStart}.
     */
    @Override
    public void show() {
        super.show();
    }

    /**
     * Start the dialog and display it on screen.  The window is placed in the
     * application layer and opaque.  Note that you should not override this
     * method to do initialization when the dialog is shown, instead implement
     * that in {@link #onStart}.
     *
     * @param title the title displayed in the {@link SDBottomNoticeDialog}.
     *
     * @author Stephanus Bagus Saputra (wiefunk@stephanusdai.web.id)
     */
    public void show(@NonNull String title) {
        setTitle(title);
        super.show();
    }

    /**
     * Start the dialog and display it on screen.  The window is placed in the
     * application layer and opaque.  Note that you should not override this
     * method to do initialization when the dialog is shown, instead implement
     * that in {@link #onStart}.
     *
     * @param title the title displayed in the {@link SDBottomNoticeDialog}.
     * @param message the message displayed in the {@link SDBottomNoticeDialog}.
     *
     * @author Stephanus Bagus Saputra (wiefunk@stephanusdai.web.id)
     */
    public void show(@NonNull String title, @NonNull String message) {
        setTitle(title);
        setMessage(message);
        super.show();
    }

    /**
     * Start the dialog and display it on screen.  The window is placed in the
     * application layer and opaque.  Note that you should not override this
     * method to do initialization when the dialog is shown, instead implement
     * that in {@link #onStart}.
     *
     * @param title the title displayed in the {@link SDBottomNoticeDialog}.
     * @param message the message displayed in the {@link SDBottomNoticeDialog}.
     * @param forceExists the optional for allow or not to exit application when dismissed.
     *
     * @author Stephanus Bagus Saputra (wiefunk@stephanusdai.web.id)
     */
    public void show(@NonNull String title, @NonNull String message, boolean forceExists) {
        setTitle(title);
        setMessage(message);
        setExistsAppOnDismiss(forceExists);
        super.show();
    }

    /**
     * Start the dialog and display it on screen.  The window is placed in the
     * application layer and opaque.  Note that you should not override this
     * method to do initialization when the dialog is shown, instead implement
     * that in {@link #onStart}.
     *
     * @param title the title displayed in the {@link SDBottomNoticeDialog}.
     * @param message the message displayed in the {@link SDBottomNoticeDialog}.
     * @param forceExists the optional for allow or not to exit application when dismissed.
     *
     * @author Stephanus Bagus Saputra (wiefunk@stephanusdai.web.id)
     */
    public void show(@NonNull String title, @NonNull String message,
                     boolean forceExists, @Nullable OnBeforeDismissListener callback) {
        setTitle(title);
        setMessage(message);
        setExistsAppOnDismiss(forceExists);
        setOnBeforeDismissListener(callback);
        super.show();
    }

    /**
     * Start the dialog and display it on screen.  The window is placed in the
     * application layer and opaque.  Note that you should not override this
     * method to do initialization when the dialog is shown, instead implement
     * that in {@link #onStart}.
     *
     * @param title the title displayed in the {@link SDBottomNoticeDialog}.
     * @param message the message displayed in the {@link SDBottomNoticeDialog}.
     * @param iconResName the icon image displayed in the {@link SDBottomNoticeDialog}.
     *
     * @author Stephanus Bagus Saputra (wiefunk@stephanusdai.web.id)
     */
    public void show(@NonNull String title, @NonNull String message,
                     @NonNull String iconResName) {
        setTitle(title);
        setMessage(message);
        setIconImage(iconResName);
        super.show();
    }

    /**
     * Start the dialog and display it on screen.  The window is placed in the
     * application layer and opaque.  Note that you should not override this
     * method to do initialization when the dialog is shown, instead implement
     * that in {@link #onStart}.
     *
     * @param title the title displayed in the {@link SDBottomNoticeDialog}.
     * @param message the message displayed in the {@link SDBottomNoticeDialog}.
     * @param iconResName the icon image displayed in the {@link SDBottomNoticeDialog}.
     * @param backgroundResName the background displayed in the {@link SDBottomNoticeDialog}.
     *
     * @author Stephanus Bagus Saputra (wiefunk@stephanusdai.web.id)
     */
    public void show(@NonNull String title, @NonNull String message,
                     @NonNull String iconResName, @NonNull String backgroundResName) {
        setTitle(title);
        setMessage(message);
        setIconImage(iconResName);
        setBackground(backgroundResName);
        super.show();
    }

    /**
     * Start the dialog and display it on screen.  The window is placed in the
     * application layer and opaque.  Note that you should not override this
     * method to do initialization when the dialog is shown, instead implement
     * that in {@link #onStart}.
     *
     * @param title the title displayed in the {@link SDBottomNoticeDialog}.
     * @param message the message displayed in the {@link SDBottomNoticeDialog}.
     * @param iconResName the icon image displayed in the {@link SDBottomNoticeDialog}.
     * @param backgroundResName the background displayed in the {@link SDBottomNoticeDialog}.
     * @param forceExists the optional for allow or not to exit application when dismissed.
     *
     * @author Stephanus Bagus Saputra (wiefunk@stephanusdai.web.id)
     */
    public void show(@NonNull String title, @NonNull String message,
                     @NonNull String iconResName, @NonNull String backgroundResName,
                     boolean forceExists) {
        setTitle(title);
        setMessage(message);
        setIconImage(iconResName);
        setBackground(backgroundResName);
        setExistsAppOnDismiss(forceExists);
        super.show();
    }

    /**
     * Start the dialog and display it on screen.  The window is placed in the
     * application layer and opaque.  Note that you should not override this
     * method to do initialization when the dialog is shown, instead implement
     * that in {@link #onStart}.
     *
     * @param title the title displayed in the {@link SDBottomNoticeDialog}.
     * @param message the message displayed in the {@link SDBottomNoticeDialog}.
     * @param iconResName the icon image displayed in the {@link SDBottomNoticeDialog}.
     * @param backgroundResName the background displayed in the {@link SDBottomNoticeDialog}.
     * @param forceExists the optional for allow or not to exit application when dismissed.
     * @param callback The {@link SDDialogInterface.OnBeforeDismissListener} to use.
     *
     * @author Stephanus Bagus Saputra (wiefunk@stephanusdai.web.id)
     */
    public void show(@NonNull String title, @NonNull String message,
                     @NonNull String iconResName, @NonNull String backgroundResName,
                     boolean forceExists, @Nullable OnBeforeDismissListener callback) {
        setTitle(title);
        setMessage(message);
        setIconImage(iconResName);
        setBackground(backgroundResName);
        setExistsAppOnDismiss(forceExists);
        setOnBeforeDismissListener(callback);
        super.show();
    }

    private void UpdateContentViewComponents() {
        if (bindingLayout != null) {
            ImageView iconImageLayout = (ImageView) bindingLayout.findViewById(R.id.hekstaroid_notice_icon);
            iconImageLayout.setBackgroundColor(Color.TRANSPARENT);
            if (iconImageResDrawable != null) {
                iconImageLayout.setBackground(iconImageResDrawable);
            } else {
                iconImageLayout.setImageResource(android.R.drawable.ic_dialog_alert);
            }
            if (iconImageViewVisibled != false) {
                iconImageLayout.setVisibility(View.VISIBLE);
            } else {
                iconImageLayout.setVisibility(View.GONE);
            }
            TextView titleTextLayout = (TextView) bindingLayout.findViewById(R.id.hekstaroid_notice_title);
            if (titleViewTypeface != null) {
                titleTextLayout.setTypeface(titleViewTypeface);
            }
            if (titleViewText != null) {
                titleTextLayout.setText(titleViewText);
            } else {
                titleTextLayout.setText(DEFAULT_TITLE_TEXT);
            }
            if (titleViewColorList != null) {
                titleTextLayout.setTextColor(titleViewColorList);
            } else if (titleViewColor != 0) {
                titleTextLayout.setTextColor(titleViewColor);
            } else {
                titleTextLayout.setTextColor(DEFAULT_TITLE_COLOR);
            }
            if (titleViewVisibled != false) {
                titleTextLayout.setVisibility(View.VISIBLE);
            } else {
                titleTextLayout.setVisibility(View.GONE);
            }
            TextView messageTextLayout = (TextView) bindingLayout.findViewById(R.id.hekstaroid_notice_message);
            if (messageViewTypeface != null) {
                messageTextLayout.setTypeface(messageViewTypeface);
            }
            if (messageViewText != null) {
                messageTextLayout.setText(messageViewText);
            } else {
                messageTextLayout.setText(DEFAULT_MESSAGE_TEXT);
            }
            if (messageViewColorList != null) {
                messageTextLayout.setTextColor(messageViewColorList);
            } else if (messageViewColor != 0) {
                messageTextLayout.setTextColor(messageViewColor);
            } else {
                messageTextLayout.setTextColor(DEFAULT_MESSAGE_COLOR);
            }
            if (messageViewVisibled != false) {
                messageTextLayout.setVisibility(View.VISIBLE);
            } else {
                messageTextLayout.setVisibility(View.GONE);
            }
            TextView copyrightTextLayout = (TextView) bindingLayout.findViewById(R.id.hekstaroid_notice_copyright);
            if (copyrightViewTypeface != null) {
                copyrightTextLayout.setTypeface(copyrightViewTypeface);
            }
            if (copyrightViewText != null) {
                copyrightTextLayout.setText(copyrightViewText);
            } else {
                copyrightTextLayout.setText(DEFAULT_COPYRIGHT_TEXT);
            }
            if (copyrightViewColorList != null) {
                copyrightTextLayout.setTextColor(copyrightViewColorList);
            } else if (copyrightViewColor != 0) {
                copyrightTextLayout.setTextColor(copyrightViewColor);
            } else {
                copyrightTextLayout.setTextColor(DEFAULT_COPYRIGHT_COLOR);
            }
            if (copyrightViewVisibled != false) {
                copyrightTextLayout.setVisibility(View.VISIBLE);
            } else {
                copyrightTextLayout.setVisibility(View.GONE);
            }
        }
    }

    private void setContentViewInternal() {
        bindingLayout = (FrameLayout) View.inflate(getContext(),
                R.layout.hekstaroid_design_bottom_notice_content, null);
        super.setContentView(bindingLayout);
        UpdateContentViewComponents();
    }
}
