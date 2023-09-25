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
import android.app.Dialog;
import android.content.Context;
import android.content.res.ColorStateList;
import android.content.res.Resources;
import android.content.res.TypedArray;
import android.graphics.BlendMode;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.graphics.Typeface;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.text.PrecomputedText;
import android.util.TypedValue;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewDebug;
import android.view.ViewGroup;
import android.view.Window;

import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.google.android.material.card.MaterialCardView;

import android.view.WindowManager;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.ColorInt;
import androidx.annotation.DrawableRes;
import androidx.annotation.InspectableProperty;
import androidx.annotation.LayoutRes;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.annotation.StringRes;
import androidx.appcompat.app.AppCompatDialog;
import androidx.coordinatorlayout.widget.CoordinatorLayout;
import androidx.core.view.AccessibilityDelegateCompat;
import androidx.core.view.ViewCompat;
import androidx.core.view.accessibility.AccessibilityNodeInfoCompat;

import com.google.android.material.bottomsheet.BottomSheetBehavior;
import com.google.android.material.shape.MaterialShapeDrawable;
import com.google.android.material.shape.ShapeAppearanceModel;

import org.jetbrains.annotations.NotNull;

import io.github.sdailover.hekstaroid.annotation.RemotableViewMethod;
import io.github.sdailover.hekstaroid.annotation.UnsupportedAppUsage;
import io.github.sdailover.hekstaroid.app.SDException;
import io.github.sdailover.hekstaroid.maps.SDScreenChangeListener;
import io.github.sdailover.hekstaroid.utils.SDDeviceManager;
import io.github.sdailover.hekstaroid.maps.SDDialogInterface;
import io.github.sdailover.hekstaroid.utils.SDDisplayMetrics;
import io.github.sdailover.hekstaroid.utils.SDScreenManager;
import io.github.sdailover.hekstaroid.utils.SDScreenMode;

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
public class SDBottomNoticeDialog extends AppCompatDialog implements SDDialogInterface {

    private CharSequence titleText = DEFAULT_TITLE_TEXT;
    private CharSequence messageText = DEFAULT_MESSAGE_TEXT;
    private CharSequence copyrightText = DEFAULT_COPYRIGHT_TEXT;

    private @ColorInt int titleColor = DEFAULT_TITLE_COLOR;
    private @ColorInt int messageColor = DEFAULT_MESSAGE_COLOR;
    private @ColorInt int copyrightColor = DEFAULT_COPYRIGHT_COLOR;
    private Typeface titleTypeface = Typeface.DEFAULT_BOLD;
    private Typeface messageTypeface = Typeface.DEFAULT;
    private Typeface copyrightTypeface = Typeface.DEFAULT;

    private @ColorInt int dialogBackgroundColor = Color.TRANSPARENT;

    private PorterDuff.Mode dialogBackgroundTintMode;
    private ColorStateList dialogBackgroundTintColor;
    private BlendMode dialogBackgroundTintBlendMode;
    private Drawable resBackground;
    private Drawable resIconImage;
    private Drawable resCloseImage;
    private boolean titleVisibled = true;
    private boolean messageVisibled = true;
    private boolean copyrightVisibled = true;
    private boolean iconImageVisibled = true;
    private boolean closeImageVisibled = true;
    private boolean cancelable = true;
    private boolean isForceExists = false;
    private boolean dismissCancelled = false;
    private boolean hasBeforeDismiss = false;
    private boolean dismissWithAnimation = true;
    private boolean canceledOnTouchOutside = true;
    private boolean canceledOnTouchOutsideSet;
    private int rotation = ROTATION_UNKNOWN;
    private BottomSheetBehavior<FrameLayout> behavior;
    private FrameLayout container;
    private View bottomContentView;
    private SDDisplayMetrics displayMetrics;
    private SDScreenMode orientation = SDScreenMode.ORIENTATION_UNDEFINED;
    private OnBeforeDismissListener beforeDismissListener;
    private SDScreenChangeListener onScreenChangeListener;
    private static int resThemeId = 0;
    private static final String DEFAULT_TITLE_TEXT = "Attention!";
    private static final String DEFAULT_MESSAGE_TEXT = "An error has occurred in the " +
            "application system, please contact the administrator to handle it.";
    private static final String DEFAULT_COPYRIGHT_TEXT = "Copyright (C) ID 2023 " +
            "Stephanus Dai Developer (sdailover.github.io)";
    private static final @ColorInt int DEFAULT_TITLE_COLOR = Color.parseColor("#FF1744");
    private static final @ColorInt int DEFAULT_MESSAGE_COLOR = Color.parseColor("#616161");
    private static final @ColorInt int DEFAULT_COPYRIGHT_COLOR = Color.parseColor("#7D7C7C");
    private static final String DEFAULT_RES_BACKGROUND = "@drawable/ic_topcorner_rounded_dialog";
    private static final String DEFAULT_RES_ICON = "@android:drawable/ic_dialog_alert";
    private static final String DEFAULT_RES_CLOSE = "@android:drawable/ic_delete";
    public static final int ROTATION_UNKNOWN = -1;


    /**
     * The final computed left padding in pixels that is used for drawing. This is the distance in
     * pixels between the left edge of this view and the left edge of its content.
     * {@hide}
     */
    @ViewDebug.ExportedProperty(category = "padding")
    @UnsupportedAppUsage
    protected int dialogPaddingLeft = 0;
    /**
     * The final computed right padding in pixels that is used for drawing. This is the distance in
     * pixels between the right edge of this view and the right edge of its content.
     * {@hide}
     */
    @ViewDebug.ExportedProperty(category = "padding")
    @UnsupportedAppUsage
    protected int dialogPaddingRight = 0;

    @ViewDebug.ExportedProperty(category = "padding")
    @UnsupportedAppUsage
    protected int dialogPaddingTop;
    /**
     * The final computed bottom padding in pixels that is used for drawing. This is the distance in
     * pixels between the bottom edge of this view and the bottom edge of its content.
     * {@hide}
     */
    @ViewDebug.ExportedProperty(category = "padding")
    @UnsupportedAppUsage
    protected int dialogPaddingBottom;

    /**
     * Creates a dialog window that uses the default dialog theme.
     * <p>
     * The supplied {@code context} is used to obtain the window manager and
     * base theme used to present the dialog.
     *
     * @param context the context in which the dialog should run
     */
    public SDBottomNoticeDialog(@NonNull Context context) {
        this(context, 0);
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
     * @param context the context in which the dialog should run
     * @param themeResId a style resource describing the theme to use for the
     *              window, or {@code 0} to use the default dialog theme
     */
    public SDBottomNoticeDialog(@NonNull Context context, int themeResId) {
        super(context, getThemeResId(context, themeResId));
        // We hide the title bar for any style configuration. Otherwise, there will be a gap
        // above the bottom sheet when it is expanded.
        supportRequestWindowFeature(Window.FEATURE_NO_TITLE);
    }

    protected SDBottomNoticeDialog(
            @NonNull Context context, boolean cancelable, OnCancelListener cancelListener) {
        super(context, cancelable, cancelListener);
        supportRequestWindowFeature(Window.FEATURE_NO_TITLE);
        this.cancelable = cancelable;
    }

    /**
     * This is initalize of component {@link android.app.Dialog#onCreate}, here it
     * is not necessary including calling {@link #setContentView} for display content.
     *
     * <p>Differences base {@link android.app.Dialog} with {@link SDBottomNoticeDialog} class lies in the components that have been
     * provided and are practical to use as a toolkit.</p>
     *
     * @param savedInstanceState If this dialog is being reinitialized after a
     *     the hosting activity was previously shut down, holds the result from
     *     the most recent call to {@link android.app.Dialog#onSaveInstanceState}, or null if this
     *     is the first time.
     * @author Stephanus Bagus Saputra (wiefunk@stephanusdai.web.id)
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Window window = getWindow();
        if (window != null) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
                window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            }
            window.setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
        }
        if (bottomContentView == null) {
            View newContentView = wrapInBottomSheet(0, createContentView(), null);
            super.setContentView(newContentView);
            updateContentView(orientation);
        }
    }

    /**
     * Called when the dialog is starting.
     *
     * <p>Reference see {@link BottomSheetDialog}
     * @author Stephanus Bagus Saputra (wiefunk@stephanusdai.web.id)
     */
    @Override
    protected void onStart() {
        super.onStart();
        if (behavior != null && behavior.getState() == BottomSheetBehavior.STATE_HIDDEN) {
            behavior.setState(BottomSheetBehavior.STATE_COLLAPSED);
        } else if (behavior != null) {
            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    behavior.setState(BottomSheetBehavior.STATE_EXPANDED);
                    behavior.setPeekHeight(0);
                    behavior.setSkipCollapsed(true);
                    behavior.setFitToContents(true);
                }
            }, 250);
        }
    }

    /**
     * Called when the device orientation or rotation has changed.
     * there are three resulting parameters, an orientation parameter in {@link SDScreenMode},
     * a rotation parameter in degrees ranging from 0 to 359, and a {@link SDDisplayMetrics}
     * parameter that determines the screen size.
     *
     * @param rotation The new rotation of the device.
     * @param orientation The new orientation of the device.
     * @param displayMetrics The new displayMetrics of the device.
     * @author Stephanus Bagus Saputra (wiefunk@stephanusdai.web.id)
     */
    protected void onScreenChanged(int rotation, SDScreenMode orientation, SDDisplayMetrics displayMetrics) {
        if (onScreenChangeListener != null) {
            onScreenChangeListener.onScreenChanged(rotation, orientation, displayMetrics);
        } else {
            if (bottomContentView != null) {
                super.setContentView(bottomContentView);
            } else {
                initBehavior(getBehavior());
                View newContentView = wrapInBottomSheet(0, createContentView(), null);
                super.setContentView(newContentView);
                updateContentView(orientation);
            }
        }
    }

    @Override
    public void setContentView(@LayoutRes int layoutResId) {
        bottomContentView = wrapInBottomSheet(layoutResId, null, null);
        super.setContentView(bottomContentView);
    }

    @Override
    public void setContentView(View view) {
        bottomContentView = wrapInBottomSheet(0, view, null);
        super.setContentView(bottomContentView);
    }

    @Override
    public void setContentView(View view, ViewGroup.LayoutParams params) {
        bottomContentView = wrapInBottomSheet(0, view, params);
        super.setContentView(bottomContentView);
    }

    /**
     * Handles screen size, orientation, rotation events when the screen changes.
     * @param listener The {@link SDScreenChangeListener} instance class.
     *
     * @author Stephanus Bagus Saputra (wiefunk@stephanusdai.web.id)
     */
    public void setOnScreenChangeListener(@Nullable SDScreenChangeListener listener) {
        this.onScreenChangeListener = listener;
    }

    /**
     * This hook is called for make layout to be expanded and not collapsed of dialog.
     *
     * @author Stephanus Bagus Saputra (wiefunk@stephanusdai.web.id)
     */
    protected void initBehavior(BottomSheetBehavior behavior) {
        try {
            behavior.setState(BottomSheetBehavior.STATE_EXPANDED);
            behavior.setPeekHeight(0);
            behavior.setSkipCollapsed(true);
            behavior.setFitToContents(true);
            Window window = getWindow();
            if (window != null) {
                window.setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
                window.setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
            }
        } catch (Exception e) {
            System.out.println(e);
        }
    }

    /**
     * Sets the background image by name's resource for this view.
     *
     * @param name the string name to getIdentifier {@link android.content.res.Resources}.
     * @author Stephanus Bagus Saputra (wiefunk@stephanusdai.web.id)
     */
    @RemotableViewMethod
    public void setBackground(@NonNull String name) {
        if (mainContentLayout != null) {
            @DrawableRes int resID = SDDeviceManager.getResourceID(getContext(), name);
            if (resID != 0) {
                resID = SDDeviceManager.getResourceID(getContext(), DEFAULT_RES_BACKGROUND);
            }
            mainContentLayout.setBackgroundResource(resID);
        }
    }

    /**
     * Set the background to a given resource. The resource should refer to
     * a Drawable object or 0 to remove the background.
     *
     * <p>Reference see {@link LinearLayout#setBackgroundResource(int)}
     *
     * @param resid The identifier of the resource.
     * @attr ref android.R.styleable#View_background
     */
    @RemotableViewMethod
    public void setBackgroundResource(@DrawableRes int resid) {
        Drawable drawable = SDDeviceManager.getDrawable(getContext(), resid);
        resBackground = drawable;
        if (mainContentLayout != null) {
            if (resid == 0) {
                resid = SDDeviceManager.getResourceID(getContext(), DEFAULT_RES_BACKGROUND);
            }
            mainContentLayout.setBackgroundResource(resid);
        }
    }

    /**
     * Set the background to a given Drawable, or remove the background. If the
     * background has padding, this View's padding is set to the background's
     * padding. However, when a background is removed, this View's padding isn't
     * touched. If setting the padding is desired, please use
     * {@link #setPadding(int, int, int, int)}.
     *
     * <p>Reference see {@link LinearLayout#setBackground(Drawable)}
     *
     * @param background The Drawable to use as the background, or null to remove the
     *        background
     */
    @Deprecated
    public void setBackground(@NonNull Drawable background) {
        resBackground = background;
        //noinspection deprecation
        this.setBackgroundDrawable(background);
    }

    /**
     * @deprecated use {@link #setBackground(Drawable)} instead
     */
    @Deprecated
    public void setBackgroundDrawable(@NonNull Drawable background) {
        resBackground = background;
        if (mainContentLayout != null) {
            //noinspection deprecation
            mainContentLayout.setBackgroundDrawable(background);
        }
    }

    /**
     * Gets the background drawable
     *
     * <p>Reference see {@link LinearLayout#getBackground()}
     *
     * @return The drawable used as the background for this view, if any. default is null.
     * @see #setBackground(Drawable)
     * @attr ref android.R.styleable#View_background
     */
    @Nullable
    @InspectableProperty
    public Drawable getBackground() {
        Drawable resDrawable;
        if (mainContentLayout != null) {
            resDrawable = mainContentLayout.getBackground();
        } else {
            @DrawableRes int defResId = SDDeviceManager.getResourceID(getContext(), DEFAULT_RES_BACKGROUND);
            resDrawable = SDDeviceManager.getDrawable(getContext(), defResId);
        }
        return resDrawable;
    }

    /**
     * Sets the background color for this view.
     *
     * <p>Reference see {@link LinearLayout#setBackgroundColor(int)}
     * @param color the color of the background
     */
    @RemotableViewMethod
    public void setBackgroundColor(@ColorInt int color) {
        dialogBackgroundColor = color;
        if (mainContentLayout != null) {
            mainContentLayout.setBackgroundColor(color);
        }
    }

    /**
     * Applies a tint to the background drawable. Does not modify the current tint
     * mode, which is {@link BlendMode#SRC_IN} by default.
     *
     * <p>Subsequent calls to {@link #setBackground(Drawable)} will automatically
     * mutate the drawable and apply the specified tint and tint mode using
     * {@link Drawable#setTintList(ColorStateList)}.
     *
     * <p>Reference see {@link LinearLayout#setBackgroundTintList(ColorStateList)}
     *
     * @param tint the tint to apply, may be {@code null} to clear tint
     * @attr ref android.R.styleable#View_backgroundTint
     * @see #getBackgroundTintList()
     * @see Drawable#setTintList(ColorStateList)
     */
    public void setBackgroundTintList(@Nullable ColorStateList tint) {
        dialogBackgroundTintColor = tint;
        if (mainContentLayout != null) {
            mainContentLayout.setBackgroundTintList(tint);
        }
    }

    /**
     * Return the tint applied to the background drawable, if specified.
     *
     * <p>Reference see {@link LinearLayout#getBackgroundTintList()}
     *
     * @return the tint applied to the background drawable. default is null.
     * @attr ref android.R.styleable#View_backgroundTint
     * @see #setBackgroundTintList(ColorStateList)
     */
    @Nullable
    @InspectableProperty(name = "backgroundTint")
    public ColorStateList getBackgroundTintList() {
        if (mainContentLayout != null) {
            return mainContentLayout.getBackgroundTintList();
        }
        return null;
    }

    /**
     * Specifies the blending mode used to apply the tint specified by
     * {@link #setBackgroundTintList(ColorStateList)}} to the background
     * drawable. The default mode is {@link PorterDuff.Mode#SRC_IN}.
     *
     * <p>Reference see {@link LinearLayout#setBackgroundTintMode(PorterDuff.Mode)}
     *
     * @param tintMode the blending mode used to apply the tint, may be
     *                 {@code null} to clear tint
     *
     * @attr ref android.R.styleable#View_backgroundTintMode
     *
     * @see #getBackgroundTintMode()
     * @see Drawable#setTintMode(PorterDuff.Mode)
     */
    public void setBackgroundTintMode(@Nullable PorterDuff.Mode tintMode) {
        dialogBackgroundTintMode = tintMode;
        if (mainContentLayout != null) {
            mainContentLayout.setBackgroundTintMode(tintMode);
        }
    }

    /**
     * Specifies the blending mode used to apply the tint specified by
     * {@link #setBackgroundTintList(ColorStateList)}} to the background
     * drawable. The default mode is {@link BlendMode#SRC_IN}.
     *
     * <p>Reference see {@link LinearLayout#setBackgroundTintBlendMode(BlendMode)}
     *
     * @param blendMode the blending mode used to apply the tint, may be
     *                 {@code null} to clear tint
     *
     * @attr ref android.R.styleable#View_backgroundTintMode
     *
     * @see #getBackgroundTintMode()
     * @see Drawable#setTintBlendMode(BlendMode)
     */
    @RequiresApi(api = Build.VERSION_CODES.Q)
    public void setBackgroundTintBlendMode(@Nullable BlendMode blendMode) throws SDException {
        dialogBackgroundTintBlendMode = blendMode;
        if (mainContentLayout != null) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
                mainContentLayout.setBackgroundTintBlendMode(blendMode);
            } else {
                throw new SDException("Requires API version " + Build.VERSION_CODES.Q + " to use this method.");
            }
        }
    }

    /**
     * Return the blending mode used to apply the tint to the background
     * drawable, if specified.
     *
     * <p>Reference see {@link LinearLayout#getBackgroundTintMode()}
     *
     * @return the blending mode used to apply the tint to the background
     *         drawable
     * @attr ref android.R.styleable#View_backgroundTintMode
     * @see #setBackgroundTintBlendMode(BlendMode)
     *
     */
    @Nullable
    @InspectableProperty
    public PorterDuff.Mode getBackgroundTintMode() {
        if (mainContentLayout != null) {
            return mainContentLayout.getBackgroundTintMode();
        }
        return null;
    }

    /**
     * Return the blending mode used to apply the tint to the background
     * drawable, if specified.
     *
     * <p>Reference see {@link LinearLayout#getBackgroundTintBlendMode()}
     *
     * @return the blending mode used to apply the tint to the background
     *         drawable, null if no blend has previously been configured
     * @attr ref android.R.styleable#View_backgroundTintMode
     * @see #setBackgroundTintBlendMode(BlendMode)
     */
    @RequiresApi(api = Build.VERSION_CODES.Q)
    public @Nullable BlendMode getBackgroundTintBlendMode() throws SDException {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
            return (mainContentLayout != null) ? mainContentLayout.getBackgroundTintBlendMode() : null;
        } else {
            throw new SDException("Requires API version " + Build.VERSION_CODES.Q + " to use this method.");
        }
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
        resIconImage = drawable;
        if (iconImageLayout != null) {
            @DrawableRes int resID = SDDeviceManager.getResourceID(getContext(), name);
            if (resID == 0) {
                resID = SDDeviceManager.getResourceID(getContext(), DEFAULT_RES_ICON);
            }
            iconImageLayout.setBackgroundColor(Color.TRANSPARENT);
            iconImageLayout.setImageResource(resID);
        }
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
        resIconImage = drawable;
        if (iconImageLayout != null) {
            if (resid == 0) {
                resid = SDDeviceManager.getResourceID(getContext(), DEFAULT_RES_ICON);
            }
            iconImageLayout.setBackgroundResource(resid);
        }
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
        resIconImage = drawable;
        //noinspection deprecation
        this.setIconImageDrawable(drawable);
    }

    /**
     * @deprecated use {@link #setIconImage(Drawable)} instead
     */
    @Deprecated
    public void setIconImageDrawable(@NonNull Drawable drawable) {
        resIconImage = drawable;
        if (iconImageLayout != null) {
            //noinspection deprecation
            iconImageLayout.setBackgroundDrawable(drawable);
        }
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
        Drawable resDrawable;
        if (iconImageLayout != null) {
            resDrawable = iconImageLayout.getDrawable();
        } else {
            @DrawableRes int defResId = SDDeviceManager.getResourceID(getContext(), DEFAULT_RES_ICON);
            resDrawable = SDDeviceManager.getDrawable(getContext(), defResId);
        }
        return resDrawable;
    }

    /**
     * Returns the visibility status for this view.
     *
     * @return the true if visible and false if not visible.
     */
    @InspectableProperty
    public boolean getIconImageVisible() {
        return iconImageVisibled;
    }

    /**
     * Set the visibility state of this view.
     *
     * @param visibled the true if visible and false if to hide.
     */
    @RemotableViewMethod
    public void setIconImageVisible(boolean visibled) {
        iconImageVisibled = visibled;
        if (iconImageLayout != null) {
            if (visibled) {
                iconImageLayout.setVisibility(View.VISIBLE);
            } else {
                iconImageLayout.setVisibility(View.GONE);
            }
        }
    }

    /**
     * Sets the close image by name's resource for close button of this view.
     *
     * @param name the string name to getIdentifier {@link android.content.res.Resources}.
     * @author Stephanus Bagus Saputra (wiefunk@stephanusdai.web.id)
     */
    @RemotableViewMethod
    public void setCloseImage(@NonNull String name) {
        Drawable drawable = SDDeviceManager.getDrawable(getContext(), name);
        resCloseImage = drawable;
        if (closeButtonLayout != null) {
            @DrawableRes int resID = SDDeviceManager.getResourceID(getContext(), name);
            if (resID == 0) {
                resID = SDDeviceManager.getResourceID(getContext(), DEFAULT_RES_ICON);
            }
            closeButtonLayout.setBackgroundColor(Color.TRANSPARENT);
            closeButtonLayout.setImageResource(resID);
        }
    }

    /**
     * Set the close image dialog to a given resource. The resource should refer to
     * a Drawable object or 0 to remove the background.
     *
     * <p>Reference see {@link ImageButton#setImageResource(int)}
     *
     * @param resid The identifier of the resource.
     *
     * @attr ref android.R.styleable#View_background
     */
    @RemotableViewMethod
    public void setCloseImageResource(@DrawableRes int resid) {
        Drawable drawable = SDDeviceManager.getDrawable(getContext(), resid);
        resCloseImage = drawable;
        if (closeButtonLayout != null) {
            if (resid == 0) {
                resid = SDDeviceManager.getResourceID(getContext(), DEFAULT_RES_ICON);
            }
            closeButtonLayout.setBackgroundResource(resid);
        }
    }

    /**
     * Set the close image dialog to a given Drawable, or remove the icon image.
     *
     * <p>Reference see {@link ImageButton#setImageDrawable(Drawable)}
     *
     * @param drawable The Drawable to use as the background, or null to remove the
     *        background
     */
    @Deprecated
    public void setCloseImage(@NonNull Drawable drawable) {
        resCloseImage = drawable;
        //noinspection deprecation
        this.setCloseImageDrawable(drawable);
    }

    /**
     * @deprecated use {@link #setCloseImage(Drawable)} instead
     */
    @Deprecated
    public void setCloseImageDrawable(@NonNull Drawable drawable) {
        resCloseImage = drawable;
        if (closeButtonLayout != null) {
            //noinspection deprecation
            closeButtonLayout.setBackgroundDrawable(drawable);
        }
    }

    /**
     * Gets the close image drawable from close button of this view.
     *
     * <p>Reference see {@link ImageButton#getDrawable()}
     *
     * @return The drawable used as the icon image for this view, if any. default is null.
     *
     * @see #setCloseImage(Drawable)
     */
    @Nullable
    @InspectableProperty
    public Drawable getCloseImage() {
        Drawable resDrawable;
        if (closeButtonLayout != null) {
            resDrawable = closeButtonLayout.getBackground();
        } else {
            @DrawableRes int defResId = SDDeviceManager.getResourceID(getContext(), DEFAULT_RES_ICON);
            resDrawable = SDDeviceManager.getDrawable(getContext(), defResId);
        }
        return resDrawable;
    }

    /**
     * Returns the visibility status for this view.
     *
     * @return the true if visible and false if not visible.
     */
    @InspectableProperty
    public boolean getCloseImageVisible() {
        return closeImageVisibled;
    }

    /**
     * Set the visibility state of this view.
     *
     * @param visibled the true if visible and false if to hide.
     */
    @RemotableViewMethod
    public void setCloseImageVisible(boolean visibled) {
        closeImageVisibled = visibled;
        if (closeButtonLayout != null) {
            if (visibled) {
                closeButtonLayout.setVisibility(View.VISIBLE);
            } else {
                closeButtonLayout.setVisibility(View.GONE);
            }
        }
    }

    /**
     * Sets the text Title to be displayed.
     *
     * @param caption text to be displayed
     * @author Stephanus Bagus Saputra (wiefunk@stephanusdai.web.id)
     */
    @RemotableViewMethod
    public final void setTitle(String caption) {
        titleText = caption;
        if (titleLabelLayout != null) {
            titleLabelLayout.setText(caption);
        }
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
        titleText = text;
        if (titleLabelLayout != null) {
            titleLabelLayout.setText(text);
        }
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
        titleText = text;
        if (titleLabelLayout != null) {
            titleLabelLayout.setText(text, type);
        }
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
        TextView parseText = new TextView(getContext());
        parseText.setText(text, start, len);
        titleText = parseText.getText();
        if (titleLabelLayout != null) {
            titleLabelLayout.setText(text, start, len);
        }
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
        TextView parseText = new TextView(getContext());
        parseText.setText(resid);
        titleText = parseText.getText();
        if (titleLabelLayout != null) {
            titleLabelLayout.setText(resid);
        }
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
        TextView parseText = new TextView(getContext());
        parseText.setText(resid, type);
        titleText = parseText.getText();
        if (titleLabelLayout != null) {
            titleLabelLayout.setText(resid, type);
        }
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
        titleColor = color;
        if (titleLabelLayout != null) {
            titleLabelLayout.setTextColor(color);
        }
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
        titleColor = colors.getDefaultColor();
        if (titleLabelLayout != null) {
            titleLabelLayout.setTextColor(colors);
        }
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
        if (titleLabelLayout != null) {
            return titleLabelLayout.getTextColors();
        }
        return null;
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
        if (titleLabelLayout != null) {
            return titleLabelLayout.getCurrentTextColor();
        }
        return DEFAULT_TITLE_COLOR;
    }

    /** @hide */
    @ViewDebug.ExportedProperty(category = "text", mapping = {
            @ViewDebug.IntToString(from = Typeface.NORMAL, to = "NORMAL"),
            @ViewDebug.IntToString(from = Typeface.BOLD, to = "BOLD"),
            @ViewDebug.IntToString(from = Typeface.ITALIC, to = "ITALIC"),
            @ViewDebug.IntToString(from = Typeface.BOLD_ITALIC, to = "BOLD_ITALIC")
    })
    public int getTitleTypefaceStyle() {
        if (titleLabelLayout != null) {
            Typeface typeface = titleLabelLayout.getTypeface();
            return typeface != null ? typeface.getStyle() : Typeface.NORMAL;
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
        if (titleLabelLayout != null) {
            return titleLabelLayout.getTypeface();
        }
        return null;
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
        titleTypeface = tf;
        if (titleLabelLayout != null) {
            titleLabelLayout.setTypeface(tf);
        }
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
        TextView parseText = new TextView(getContext());
        parseText.setTypeface(tf, style);
        titleTypeface = parseText.getTypeface();
        if (titleLabelLayout != null) {
            titleLabelLayout.setTypeface(tf, style);
        }
    }

    /**
     * Returns the visibility status for this view.
     *
     * @return the true if visible and false if not visible.
     */
    @InspectableProperty
    public boolean getTitleVisible() {
        return titleVisibled;
    }

    /**
     * Set the visibility state of this view.
     *
     * @param visibled the true if visible and false if to hide.
     */
    @RemotableViewMethod
    public void setTitleVisible(boolean visibled) {
        titleVisibled = visibled;
        if (titleLabelLayout != null) {
            if (visibled) {
                titleLabelLayout.setVisibility(View.VISIBLE);
            } else {
                titleLabelLayout.setVisibility(View.GONE);
            }
        }
    }

    /**
     * Sets the text Message to be displayed.
     *
     * @param description text to be displayed
     * @author Stephanus Bagus Saputra (wiefunk@stephanusdai.web.id)
     */
    @RemotableViewMethod
    public final void setMessage(String description) {
        messageText = description;
        if (messageLabelLayout != null) {
            messageLabelLayout.setText(description);
        }
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
        messageText = text;
        if (messageLabelLayout != null) {
            messageLabelLayout.setText(text);
        }
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
        messageText = text;
        if (messageLabelLayout != null) {
            messageLabelLayout.setText(text, type);
        }
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
        TextView parseText = new TextView(getContext());
        parseText.setText(text, start, len);
        messageText = parseText.getText();
        if (messageLabelLayout != null) {
            messageLabelLayout.setText(text, start, len);
        }
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
        TextView parseText = new TextView(getContext());
        parseText.setText(resid);
        messageText = parseText.getText();
        if (messageLabelLayout != null) {
            messageLabelLayout.setText(resid);
        }
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
        TextView parseText = new TextView(getContext());
        parseText.setText(resid, type);
        messageText = parseText.getText();
        if (messageLabelLayout != null) {
            messageLabelLayout.setText(resid, type);
        }
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
        messageColor = color;
        if (messageLabelLayout != null) {
            messageLabelLayout.setTextColor(color);
        }
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
        messageColor = colors.getDefaultColor();
        if (messageLabelLayout != null) {
            messageLabelLayout.setTextColor(colors);
        }
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
        if (messageLabelLayout != null) {
            return messageLabelLayout.getTextColors();
        }
        return null;
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
        if (messageLabelLayout != null) {
            return messageLabelLayout.getCurrentTextColor();
        }
        return DEFAULT_MESSAGE_COLOR;
    }

    /** @hide */
    @ViewDebug.ExportedProperty(category = "text", mapping = {
            @ViewDebug.IntToString(from = Typeface.NORMAL, to = "NORMAL"),
            @ViewDebug.IntToString(from = Typeface.BOLD, to = "BOLD"),
            @ViewDebug.IntToString(from = Typeface.ITALIC, to = "ITALIC"),
            @ViewDebug.IntToString(from = Typeface.BOLD_ITALIC, to = "BOLD_ITALIC")
    })
    public int getMessageTypefaceStyle() {
        if (messageLabelLayout != null) {
            Typeface typeface = messageLabelLayout.getTypeface();
            return typeface != null ? typeface.getStyle() : Typeface.NORMAL;
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
        if (messageLabelLayout != null) {
            return messageLabelLayout.getTypeface();
        }
        return null;
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
        messageTypeface = tf;
        if (messageLabelLayout != null) {
            messageLabelLayout.setTypeface(tf);
        }
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
        messageTypeface = tf;
        if (messageLabelLayout != null) {
            messageLabelLayout.setTypeface(tf, style);
        }
    }

    /**
     * Returns the visibility status for this view.
     *
     * @return the true if visible and false if not visible.
     */
    @InspectableProperty
    public boolean getMessageVisible() {
        return messageVisibled;
    }

    /**
     * Set the visibility state of this view.
     *
     * @param visibled the true if visible and false if to hide.
     */
    @RemotableViewMethod
    public void setMessageVisible(boolean visibled) {
        messageVisibled = visibled;
        if (messageLabelLayout != null) {
            if (visibled) {
                messageLabelLayout.setVisibility(View.VISIBLE);
            } else {
                messageLabelLayout.setVisibility(View.GONE);
            }
        }
    }


    /**
     * Sets the text Copyright to be displayed.
     *
     * @param marked text to be displayed
     * @author Stephanus Bagus Saputra (wiefunk@stephanusdai.web.id)
     */
    @RemotableViewMethod
    public final void setCopyright(String marked) {
        copyrightText = marked;
        if (copyrightLabelLayout != null) {
            copyrightLabelLayout.setText(marked);
        }
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
        copyrightText = text;
        if (copyrightLabelLayout != null) {
            copyrightLabelLayout.setText(text);
        }
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
        copyrightText = text;
        if (copyrightLabelLayout != null) {
            copyrightLabelLayout.setText(text, type);
        }
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
        TextView parseText = new TextView(getContext());
        parseText.setText(text, start, len);
        copyrightText = parseText.getText();
        if (copyrightLabelLayout != null) {
            copyrightLabelLayout.setText(text, start, len);
        }
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
        TextView parseText = new TextView(getContext());
        parseText.setText(resid);
        copyrightText = parseText.getText();
        if (copyrightLabelLayout != null) {
            copyrightLabelLayout.setText(resid);
        }
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
        TextView parseText = new TextView(getContext());
        parseText.setText(resid, type);
        copyrightText = parseText.getText();
        if (copyrightLabelLayout != null) {
            copyrightLabelLayout.setText(resid, type);
        }
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
        copyrightColor = color;
        if (copyrightLabelLayout != null) {
            copyrightLabelLayout.setTextColor(color);
        }
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
        copyrightColor = colors.getDefaultColor();
        if (copyrightLabelLayout != null) {
            copyrightLabelLayout.setTextColor(colors);
        }
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
        if (copyrightLabelLayout != null) {
            return copyrightLabelLayout.getTextColors();
        }
        return null;
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
        if (copyrightLabelLayout != null) {
            return copyrightLabelLayout.getCurrentTextColor();
        }
        return DEFAULT_MESSAGE_COLOR;
    }

    /** @hide */
    @ViewDebug.ExportedProperty(category = "text", mapping = {
            @ViewDebug.IntToString(from = Typeface.NORMAL, to = "NORMAL"),
            @ViewDebug.IntToString(from = Typeface.BOLD, to = "BOLD"),
            @ViewDebug.IntToString(from = Typeface.ITALIC, to = "ITALIC"),
            @ViewDebug.IntToString(from = Typeface.BOLD_ITALIC, to = "BOLD_ITALIC")
    })
    public int getCopyrightTypefaceStyle() {
        if (copyrightLabelLayout != null) {
            Typeface typeface = copyrightLabelLayout.getTypeface();
            return typeface != null ? typeface.getStyle() : Typeface.NORMAL;
        }
        return Typeface.NORMAL;
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
        if (copyrightLabelLayout != null) {
            return copyrightLabelLayout.getTypeface();
        }
        return null;
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
        copyrightTypeface = tf;
        if (copyrightLabelLayout != null) {
            copyrightLabelLayout.setTypeface(tf);
        }
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
        copyrightTypeface = tf;
        if (copyrightLabelLayout != null) {
            copyrightLabelLayout.setTypeface(tf, style);
        }
    }

    /**
     * Returns the visibility status for this view.
     *
     * @return the true if visible and false if not visible.
     */
    @InspectableProperty
    public boolean getCopyrightVisible() {
        return copyrightVisibled;
    }

    /**
     * Set the visibility state of this view.
     *
     * @param visibled the true if visible and false if to hide.
     */
    @RemotableViewMethod
    public void setCopyrightVisible(boolean visibled) {
        copyrightVisibled = visibled;
        if (copyrightLabelLayout != null) {
            if (visibled) {
                copyrightLabelLayout.setVisibility(View.VISIBLE);
            } else {
                copyrightLabelLayout.setVisibility(View.GONE);
            }
        }
    }

    /**
     * Sets the padding. The view may add on the space required to display
     * the scrollbars, depending on the style and visibility of the scrollbars.
     * So the values returned from {@link #getPaddingLeft}, {@link #getPaddingTop},
     * {@link #getPaddingRight} and {@link #getPaddingBottom} may be different
     * from the values set in this call.
     *
     * <p>Reference see {@link LinearLayout#setPadding(int, int, int, int)}
     *
     * @attr ref android.R.styleable#View_padding
     * @attr ref android.R.styleable#View_paddingBottom
     * @attr ref android.R.styleable#View_paddingLeft
     * @attr ref android.R.styleable#View_paddingRight
     * @attr ref android.R.styleable#View_paddingTop
     * @param left the left padding in pixels
     * @param top the top padding in pixels
     * @param right the right padding in pixels
     * @param bottom the bottom padding in pixels
     */
    public void setPadding(int left, int top, int right, int bottom) {
        if (mainContentLayout != null) {
            mainContentLayout.setPadding(left, top, right, bottom);
        }
    }

    /**
     * Sets the relative padding. The view may add on the space required to display
     * the scrollbars, depending on the style and visibility of the scrollbars.
     * So the values returned from {@link #getPaddingStart}, {@link #getPaddingTop},
     * {@link #getPaddingEnd} and {@link #getPaddingBottom} may be different
     * from the values set in this call.
     *
     * <p>Reference see {@link LinearLayout#setPaddingRelative(int, int, int, int)}
     *
     * @attr ref android.R.styleable#View_padding
     * @attr ref android.R.styleable#View_paddingBottom
     * @attr ref android.R.styleable#View_paddingStart
     * @attr ref android.R.styleable#View_paddingEnd
     * @attr ref android.R.styleable#View_paddingTop
     * @param start the start padding in pixels
     * @param top the top padding in pixels
     * @param end the end padding in pixels
     * @param bottom the bottom padding in pixels
     */
    public void setPaddingRelative(int start, int top, int end, int bottom) {
        if (mainContentLayout != null) {
            mainContentLayout.setPaddingRelative(start, top, end, bottom);
        }
    }

    /**
     * A {@link View} can be inflated from an XML layout. For such Views
     * this method returns the resource ID of the source layout.
     *
     * <p>Reference see {@link LinearLayout#getSourceLayoutResId()}
     *
     * @return The layout resource id if this view was inflated from XML, otherwise
     * {@link Resources#ID_NULL}.
     */
    @LayoutRes
    @RequiresApi(api = Build.VERSION_CODES.Q)
    public int getSourceLayoutResId() throws SDException {
        if (mainContentLayout != null) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
                return mainContentLayout.getSourceLayoutResId();
            } else {
                throw new SDException("Requires API version " + Build.VERSION_CODES.Q + " to use this method.");
            }
        }
        return Resources.ID_NULL;
    }

    /**
     * Returns the top padding of this view.
     *
     * <p>Reference see {@link LinearLayout#getPaddingTop()}
     *
     * @return the top padding in pixels
     */
    @InspectableProperty
    public int getPaddingTop() {
        if (mainContentLayout != null) {
            return mainContentLayout.getPaddingTop();
        }
        return 0;
    }

    /**
     * Returns the bottom padding of this view. If there are inset and enabled
     * scrollbars, this value may include the space required to display the
     * scrollbars as well.
     *
     * <p>Reference see {@link LinearLayout#getPaddingBottom()}
     *
     * @return the bottom padding in pixels
     */
    @InspectableProperty
    public int getPaddingBottom() {
        if (mainContentLayout != null) {
            return mainContentLayout.getPaddingBottom();
        }
        return 0;
    }

    /**
     * Returns the left padding of this view. If there are inset and enabled
     * scrollbars, this value may include the space required to display the
     * scrollbars as well.
     *
     * <p>Reference see {@link LinearLayout#getPaddingLeft()}
     *
     * @return the left padding in pixels
     */
    @InspectableProperty
    public int getPaddingLeft() {
        if (mainContentLayout != null) {
            return mainContentLayout.getPaddingLeft();
        }
        return 0;
    }

    /**
     * Returns the start padding of this view depending on its resolved layout direction.
     * If there are inset and enabled scrollbars, this value may include the space
     * required to display the scrollbars as well.
     *
     * <p>Reference see {@link LinearLayout#getPaddingStart()}
     *
     * @return the start padding in pixels
     */
    public int getPaddingStart() {
        if (mainContentLayout != null) {
            return mainContentLayout.getPaddingStart();
        }
        return 0;
    }

    /**
     * Returns the right padding of this view. If there are inset and enabled
     * scrollbars, this value may include the space required to display the
     * scrollbars as well.
     *
     * <p>Reference see {@link LinearLayout#getPaddingRight()}
     *
     * @return the right padding in pixels
     */
    @InspectableProperty
    public int getPaddingRight() {
        if (mainContentLayout != null) {
            return mainContentLayout.getPaddingRight();
        }
        return 0;
    }

    /**
     * Returns the end padding of this view depending on its resolved layout direction.
     * If there are inset and enabled scrollbars, this value may include the space
     * required to display the scrollbars as well.
     *
     * <p>Reference see {@link LinearLayout#getPaddingEnd()}
     *
     * @return the end padding in pixels
     */
    public int getPaddingEnd() {
        if (mainContentLayout != null) {
            return mainContentLayout.getPaddingEnd();
        }
        return 0;
    }

    /**
     * Return if the padding has been set through relative values
     * {@link #setPaddingRelative(int, int, int, int)} or through
     *
     * <p>Reference see {@link LinearLayout#isPaddingRelative()}
     *
     * @attr ref android.R.styleable#View_paddingStart or
     * @attr ref android.R.styleable#View_paddingEnd
     *
     * @return true if the padding is relative or false if it is not.
     */
    public boolean isPaddingRelative() {
        if (mainContentLayout != null) {
            return mainContentLayout.isPaddingRelative();
        }
        return false;
    }

    /**
     * Called to tell you that you're stopping.
     * <p/>
     * Slight changes to the {@link Dialog#onStop()} method,
     * namely by adding an optional close application when dismiss occurs.
     *
     * @author Stephanus Bagus Saputra (wiefunk@stephanusdai.web.id)
     */
    @Override
    protected void onStop() {
        if (isForceExists != false) {
            int id = android.os.Process.myPid();
            android.os.Process.killProcess(id);
        }
        super.onStop();
    }

    /**
     * Dismiss this dialog, removing it from the screen. This method can be
     * invoked safely from any thread.  Note that you should not override this
     * method to do cleanup when the dialog is dismissed, instead implement
     * that in {@link #onStop}.
     *
     * <p>Reference see {@link Dialog#dismiss()}
     *
     * @author Stephanus Bagus Saputra (wiefunk@stephanusdai.web.id)
     */
    @Override
    public void dismiss() {
        if (!hasBeforeDismiss || getCancelDismiss()) {
            beforeDismiss();
        }
        if (getCancelDismiss() == false) {
            super.dismiss();
        }
    }

    /**
     * Running the {@link OnBeforeDismissListener} event will be invoked when before the dialog is dismissed.
     *
     * @author Stephanus Bagus Saputra (wiefunk@stephanusdai.web.id)
     */
    protected void beforeDismiss() {
        if (beforeDismissListener != null) {
            beforeDismissListener.onBeforeDismiss(this);
        }
        hasBeforeDismiss = true;
    }

    @Override
    @InspectableProperty
    public boolean getCancelDismiss() {
        return dismissCancelled;
    }

    @Override
    @RemotableViewMethod
    public void setCancelDismiss(boolean allowed) {
        dismissCancelled = allowed;
    }

    /**
     * Sets whether this dialog is cancelable with the
     * {@link KeyEvent#KEYCODE_BACK BACK} key.
     */
    @Override
    public void setCancelable(boolean cancelable) {
        super.setCancelable(cancelable);
        if (this.cancelable != cancelable) {
            this.cancelable = cancelable;
            if (behavior != null) {
                behavior.setHideable(cancelable);
            }
        }
    }

    /**
     * Set a listener to be invoked when before dialog is dismissed.
     * @param listener The {@link SDDialogInterface.OnBeforeDismissListener} to use.
     */
    public void setOnBeforeDismissListener(@Nullable OnBeforeDismissListener listener) {
        beforeDismissListener = listener;
    }

    /**
     * Start the dialog and display it on screen.  The window is placed in the
     * application layer and opaque.  Note that you should not override this
     * method to do initialization when the dialog is shown, instead implement
     * that in {@link #onStart}.
     */
    @Override
    public void show() {
        this.show(DEFAULT_TITLE_TEXT, DEFAULT_MESSAGE_TEXT,
                DEFAULT_RES_ICON, DEFAULT_RES_BACKGROUND, false, null);
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
        this.show(title, DEFAULT_MESSAGE_TEXT, DEFAULT_RES_ICON, DEFAULT_RES_BACKGROUND, false, null);
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
        this.show(title, message, DEFAULT_RES_ICON, DEFAULT_RES_BACKGROUND, false, null);
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
        this.show(title, message, DEFAULT_RES_ICON, DEFAULT_RES_BACKGROUND, forceExists, null);
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
        this.show(title, message, DEFAULT_RES_ICON, DEFAULT_RES_BACKGROUND, forceExists, callback);
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
        this.show(title, message, iconResName, DEFAULT_RES_BACKGROUND, false, null);
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
        this.show(title, message, iconResName, backgroundResName, false, null);
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
        this.show(title, message, iconResName, backgroundResName, forceExists, null);
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
        if (callback != null) {
            setOnBeforeDismissListener(callback);
        }
        super.show();
    }

    /**
     * When user {@link #dismiss()} this dialog, the default this dialog
     * is not close application. But you can set as true if you want to
     * force exit application.
     *
     * @param value boolean to set allow or not exists application when
     * {@link #dismiss()}. default: false
     *
     * @author Stephanus Bagus Saputra (wiefunk@stephanusdai.web.id)
     */
    public void setExistsAppOnDismiss(boolean value) {
        isForceExists = value;
    }

    /**
     * This function can be called from a few different use cases, including Swiping the dialog down
     * or calling `dismiss()` from a `BottomSheetDialogFragment`, tapping outside a dialog, etc...
     *
     * <p>The default animation to dismiss this dialog is a fade-out transition through a
     * windowAnimation. Call {@link #setDismissWithAnimation(boolean true)} if you want to utilize the
     * BottomSheet animation instead.
     *
     * <p>If this function is called from a swipe down interaction, or dismissWithAnimation is false,
     * then keep the default behavior.
     *
     * <p>Else, since this is a terminal event which will finish this dialog, we override the attached
     * {@link BottomSheetBehavior.BottomSheetCallback} to call this function, after {@link
     * BottomSheetBehavior#STATE_HIDDEN} is set. This will enforce the swipe down animation before
     * canceling this dialog.
     */
    @Override
    public void cancel() {
        BottomSheetBehavior<FrameLayout> behavior = getBehavior();

        if (!dismissWithAnimation || behavior.getState() == BottomSheetBehavior.STATE_HIDDEN) {
            super.cancel();
        } else {
            behavior.setState(BottomSheetBehavior.STATE_HIDDEN);
        }
    }

    /**
     * Sets whether this dialog is canceled when touched outside the window's
     * bounds. If setting to true, the dialog is set to be cancelable if not
     * already set.
     *
     * @param cancel Whether the dialog should be canceled when touched outside
     *            the window.
     */
    @Override
    public void setCanceledOnTouchOutside(boolean cancel) {
        super.setCanceledOnTouchOutside(cancel);
        if (cancel && !cancelable) {
            cancelable = true;
        }
        canceledOnTouchOutside = cancel;
        canceledOnTouchOutsideSet = true;
    }

    /**
     * Get behavior plugin for a child view of {@link CoordinatorLayout}
     * to make it work as a bottom sheet.
     * @return The BottomSheetBehavior of bottomsheet.
     */
    @NonNull
    public BottomSheetBehavior<FrameLayout> getBehavior() {
        if (behavior == null) {
            // The content hasn't been set, so the behavior doesn't exist yet. Let's create it.
            ensureContainerAndBehavior();
        }
        return behavior;
    }

    /**
     * Set to perform the swipe down animation when dismissing instead of the window animation for the
     * dialog.
     *
     * @param dismissWithAnimation True if swipe down animation should be used when dismissing.
     */
    public void setDismissWithAnimation(boolean dismissWithAnimation) {
        this.dismissWithAnimation = dismissWithAnimation;
    }

    /**
     * Returns if dismissing will perform the swipe down animation on the bottom sheet, rather than
     * the window animation for the dialog.
     */
    public boolean getDismissWithAnimation() {
        return dismissWithAnimation;
    }

    /**
     * Rearrange the arrangement of view components so that they can be displayed.
     * @param orientation the screen orientation type.
     * @author Stephanus Bagus Saputra (wiefunk@stephanusdai.web.id)
     */
    protected void updateContentView(SDScreenMode orientation) {
        if (orientation == SDScreenMode.ORIENTATION_PORTRAIT) {
            updateContentToPotrait();
        } else {
            updateContentToLandscape();
        }

        closeButtonLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (cancelable && isShowing()) {
                    SDBottomNoticeDialog.this.cancel();
                }
            }
        });
    }

    boolean shouldWindowCloseOnTouchOutside() {
        if (!canceledOnTouchOutsideSet) {
            TypedArray a =
                    getContext()
                            .obtainStyledAttributes(new int[] {android.R.attr.windowCloseOnTouchOutside});
            canceledOnTouchOutside = a.getBoolean(0, true);
            a.recycle();
            canceledOnTouchOutsideSet = true;
        }
        return canceledOnTouchOutside;
    }

    void removeDefaultCallback() {
        behavior.removeBottomSheetCallback(bottomSheetCallback);
    }

    private void updateContentToPotrait() {
        // initialize create mainContentLayout
        mainContentLayout.setPadding(50, 50, 50, 100);
        mainContentLayout.setOrientation(LinearLayout.VERTICAL);
        ViewGroup.LayoutParams mainContentLayoutParams =
                (ViewGroup.LayoutParams) mainContentLayout.getLayoutParams();
        mainContentLayoutParams.width = displayMetrics.metrics.widthPixels;
        mainContentLayoutParams.height = ViewGroup.LayoutParams.WRAP_CONTENT;
        setBackgroundColor(dialogBackgroundColor);
        if (resBackground == null) {
            setBackground(DEFAULT_RES_BACKGROUND);
        } else {
            //noinspection deprecation
            setBackground(resBackground);
        }


        // initialize create headFrameLayout
        RelativeLayout headFrameLayout = new RelativeLayout(getContext());
        mainContentLayout.addView(headFrameLayout);

        // initialize create closeButtonLayout
        headFrameLayout.addView(closeButtonLayout);
        ViewGroup.MarginLayoutParams closeButtonLayoutMargins =
                (ViewGroup.MarginLayoutParams) headFrameLayout.getLayoutParams();
        closeButtonLayoutMargins.setMargins(10, 0 ,10,0);
        ViewGroup.LayoutParams closeButtonLayoutParams =
                (ViewGroup.LayoutParams) closeButtonLayout.getLayoutParams();
        closeButtonLayout.setPadding(10, 10, 10, 10);
        closeButtonLayoutParams.width = 72;
        closeButtonLayoutParams.height = 72;
        RelativeLayout.LayoutParams closeButtonLayoutRelative = (RelativeLayout.LayoutParams) closeButtonLayout.getLayoutParams();
        closeButtonLayoutRelative.addRule(RelativeLayout.ALIGN_PARENT_TOP);
        closeButtonLayoutRelative.addRule(RelativeLayout.ALIGN_PARENT_END);
        closeButtonLayout.setBackgroundColor(Color.TRANSPARENT);
        closeButtonLayout.setScaleType(ImageView.ScaleType.FIT_XY);
        if (resCloseImage == null) {
            setCloseImage(DEFAULT_RES_CLOSE);
        } else {
            //noinspection deprecation
            setCloseImage(resCloseImage);
        }
        ViewGroup.LayoutParams headFrameLayoutParams =
                (ViewGroup.LayoutParams) headFrameLayout.getLayoutParams();
        headFrameLayoutParams.width  = ViewGroup.LayoutParams.MATCH_PARENT;
        headFrameLayoutParams.height = ViewGroup.LayoutParams.WRAP_CONTENT;

        // initialize create iconImageLayout
        headFrameLayout.addView(iconImageLayout);
        ViewGroup.LayoutParams iconImageLayoutParams =
                (ViewGroup.LayoutParams) iconImageLayout.getLayoutParams();
        iconImageLayoutParams.width  = 240;
        iconImageLayoutParams.height = 240;
        ViewGroup.MarginLayoutParams iconImageLayoutMargins =
                (ViewGroup.MarginLayoutParams) iconImageLayout.getLayoutParams();
        iconImageLayoutMargins.setMargins(30, 70, 30, 10);
        RelativeLayout.LayoutParams iconImageLayoutRelative =
                (RelativeLayout.LayoutParams) iconImageLayout.getLayoutParams();
        iconImageLayoutRelative.addRule(RelativeLayout.ALIGN_PARENT_TOP);
        iconImageLayoutRelative.addRule(RelativeLayout.CENTER_HORIZONTAL);
        iconImageLayout.setBackgroundColor(Color.TRANSPARENT);
        iconImageLayout.setScaleType(ImageView.ScaleType.FIT_XY);
        if (resIconImage == null) {
            setIconImage(DEFAULT_RES_ICON);
        } else {
            //noinspection deprecation
            setIconImage(resIconImage);
        }

        // initialize create titleFrameLayout
        RelativeLayout titleFrameLayout = new RelativeLayout(getContext());
        mainContentLayout.addView(titleFrameLayout);
        ViewGroup.LayoutParams titleFrameLayoutParams =
                (ViewGroup.LayoutParams) titleFrameLayout.getLayoutParams();
        titleFrameLayoutParams.width  = ViewGroup.LayoutParams.MATCH_PARENT;
        titleFrameLayoutParams.height = ViewGroup.LayoutParams.WRAP_CONTENT;
        titleFrameLayout.addView(titleLabelLayout);
        ViewGroup.LayoutParams titleLabelLayoutParams =
                (ViewGroup.LayoutParams) titleLabelLayout.getLayoutParams();
        titleLabelLayoutParams.width  = ViewGroup.LayoutParams.WRAP_CONTENT;
        titleLabelLayoutParams.height = ViewGroup.LayoutParams.WRAP_CONTENT;
        ViewGroup.MarginLayoutParams titleLabelLayoutMargins =
                (ViewGroup.MarginLayoutParams) titleLabelLayout.getLayoutParams();
        titleLabelLayoutMargins.setMargins(30, 10, 30, 10);
        RelativeLayout.LayoutParams titleLabelLayoutRelative =
                (RelativeLayout.LayoutParams) titleLabelLayout.getLayoutParams();
        titleLabelLayoutRelative.addRule(RelativeLayout.ALIGN_PARENT_TOP);
        titleLabelLayoutRelative.addRule(RelativeLayout.CENTER_HORIZONTAL);
        setTitle(titleText);
        setTitleColor(titleColor);
        setTitleTypeface(Typeface.DEFAULT_BOLD, Typeface.BOLD);
        titleLabelLayout.setTextAlignment(TextView.TEXT_ALIGNMENT_CENTER);
        titleLabelLayout.setTextSize(24f);

        // initialize create messageFrameLayout
        RelativeLayout messageFrameLayout = new RelativeLayout(getContext());
        mainContentLayout.addView(messageFrameLayout);
        ViewGroup.LayoutParams messageFrameLayoutParams =
                (ViewGroup.LayoutParams) messageFrameLayout.getLayoutParams();
        messageFrameLayoutParams.width  = ViewGroup.LayoutParams.MATCH_PARENT;
        messageFrameLayoutParams.height = ViewGroup.LayoutParams.WRAP_CONTENT;
        messageFrameLayout.addView(messageLabelLayout);
        ViewGroup.LayoutParams messageLabelLayoutParams =
                (ViewGroup.LayoutParams) messageLabelLayout.getLayoutParams();
        messageLabelLayoutParams.width  = ViewGroup.LayoutParams.WRAP_CONTENT;
        messageLabelLayoutParams.height = ViewGroup.LayoutParams.WRAP_CONTENT;
        ViewGroup.MarginLayoutParams messageLabelLayoutMargins =
                (ViewGroup.MarginLayoutParams) messageLabelLayout.getLayoutParams();
        messageLabelLayoutMargins.setMargins(30, 0, 30, 50);
        RelativeLayout.LayoutParams messageLabelLayoutRelative =
                (RelativeLayout.LayoutParams) messageLabelLayout.getLayoutParams();
        messageLabelLayoutRelative.addRule(RelativeLayout.ALIGN_PARENT_TOP);
        messageLabelLayoutRelative.addRule(RelativeLayout.CENTER_HORIZONTAL);
        setMessage(messageText);
        setMessageColor(messageColor);
        messageLabelLayout.setTextAlignment(TextView.TEXT_ALIGNMENT_CENTER);
        messageLabelLayout.setTextSize(16f);

        // initialize create copyrightFrameLayout
        RelativeLayout copyrightFrameLayout = new RelativeLayout(getContext());
        mainContentLayout.addView(copyrightFrameLayout);
        ViewGroup.LayoutParams copyrightFrameLayoutParams =
                (ViewGroup.LayoutParams) copyrightFrameLayout.getLayoutParams();
        copyrightFrameLayoutParams.width  = ViewGroup.LayoutParams.MATCH_PARENT;
        copyrightFrameLayoutParams.height = ViewGroup.LayoutParams.WRAP_CONTENT;
        copyrightFrameLayout.addView(copyrightLabelLayout);
        ViewGroup.LayoutParams copyrightLabelLayoutParams =
                (ViewGroup.LayoutParams) copyrightLabelLayout.getLayoutParams();
        copyrightLabelLayoutParams.width  = ViewGroup.LayoutParams.WRAP_CONTENT;
        copyrightLabelLayoutParams.height = ViewGroup.LayoutParams.WRAP_CONTENT;
        ViewGroup.MarginLayoutParams copyrightLabelLayoutMargins =
                (ViewGroup.MarginLayoutParams) copyrightLabelLayout.getLayoutParams();
        copyrightLabelLayoutMargins.setMargins(30, 0, 30, 0);
        RelativeLayout.LayoutParams copyrightLabelLayoutRelative =
                (RelativeLayout.LayoutParams) copyrightLabelLayout.getLayoutParams();
        copyrightLabelLayoutRelative.addRule(RelativeLayout.ALIGN_PARENT_TOP);
        copyrightLabelLayoutRelative.addRule(RelativeLayout.CENTER_HORIZONTAL);
        setCopyright(copyrightText);
        setCopyrightColor(copyrightColor);
        setCopyrightVisible(copyrightVisibled);
        copyrightLabelLayout.setTextAlignment(TextView.TEXT_ALIGNMENT_CENTER);
        copyrightLabelLayout.setTextSize(16f);
    }

    private void updateContentToLandscape() {
        // initialize create mainContentLayout
        mainContentLayout.setPadding(50, 50, 50, 100);
        mainContentLayout.setOrientation(LinearLayout.VERTICAL);
        ViewGroup.LayoutParams mainContentLayoutParams =
                (ViewGroup.LayoutParams) mainContentLayout.getLayoutParams();
        mainContentLayoutParams.width = ViewGroup.LayoutParams.MATCH_PARENT;
        mainContentLayoutParams.height = ViewGroup.LayoutParams.WRAP_CONTENT;
        setBackgroundColor(dialogBackgroundColor);
        if (resBackground == null) {
            setBackground(DEFAULT_RES_BACKGROUND);
        } else {
            //noinspection deprecation
            setBackground(resBackground);
        }

        // create close components.
        RelativeLayout closeFrameLayout = new RelativeLayout(getContext());
        mainContentLayout.addView(closeFrameLayout);
        ViewGroup.LayoutParams closeFrameLayoutParams =
                (ViewGroup.LayoutParams) closeFrameLayout.getLayoutParams();
        closeFrameLayoutParams.width = ViewGroup.LayoutParams.MATCH_PARENT;
        closeFrameLayoutParams.height = ViewGroup.LayoutParams.WRAP_CONTENT;
        closeFrameLayout.addView(closeButtonLayout);
        ViewGroup.MarginLayoutParams closeButtonLayoutMargins =
                (ViewGroup.MarginLayoutParams) closeButtonLayout.getLayoutParams();
        closeButtonLayoutMargins.setMargins(10, 0, 10, 0);
        ViewGroup.LayoutParams closeButtonLayoutParams =
                (ViewGroup.LayoutParams) closeButtonLayout.getLayoutParams();
        closeButtonLayoutParams.width = 72;
        closeButtonLayoutParams.height = 72;
        RelativeLayout.LayoutParams closeButtonLayoutRelative =
                (RelativeLayout.LayoutParams) closeButtonLayout.getLayoutParams();
        closeButtonLayoutRelative.addRule(RelativeLayout.ALIGN_PARENT_TOP);
        closeButtonLayoutRelative.addRule(RelativeLayout.ALIGN_PARENT_END);
        closeButtonLayout.setScaleType(ImageView.ScaleType.FIT_XY);
        closeButtonLayout.setBackgroundColor(Color.TRANSPARENT);
        if (resCloseImage == null) {
            setCloseImage(DEFAULT_RES_CLOSE);
        } else {
            //noinspection deprecation
            setCloseImage(resCloseImage);
        }

        // column left components.
        LinearLayout bodyFrameLayout = new LinearLayout(getContext());
        mainContentLayout.addView(bodyFrameLayout);
        bodyFrameLayout.setOrientation(LinearLayout.HORIZONTAL);
        bodyFrameLayout.setPadding(50, 0, 50, 0);
        ViewGroup.LayoutParams bodyFrameLayoutParams =
                (ViewGroup.LayoutParams) bodyFrameLayout.getLayoutParams();
        bodyFrameLayoutParams.width = ViewGroup.LayoutParams.MATCH_PARENT;
        bodyFrameLayoutParams.height = ViewGroup.LayoutParams.WRAP_CONTENT;

        // create icon notice component
        RelativeLayout iconFrameLayout = new RelativeLayout(getContext());
        bodyFrameLayout.addView(iconFrameLayout);
        ViewGroup.LayoutParams iconFrameLayoutParams =
                (ViewGroup.LayoutParams) iconFrameLayout.getLayoutParams();
        iconFrameLayoutParams.width = ViewGroup.LayoutParams.WRAP_CONTENT;
        iconFrameLayoutParams.height = ViewGroup.LayoutParams.MATCH_PARENT;
        iconFrameLayout.addView(iconImageLayout);
        ViewGroup.MarginLayoutParams iconImageLayoutMargins =
                (ViewGroup.MarginLayoutParams) iconImageLayout.getLayoutParams();
        iconImageLayoutMargins.setMargins(0, 0, 0, 0);
        ViewGroup.LayoutParams iconImageLayoutParams =
                (ViewGroup.LayoutParams) iconImageLayout.getLayoutParams();
        iconImageLayoutParams.width = 200;
        iconImageLayoutParams.height = 200;
        RelativeLayout.LayoutParams iconImageLayoutRelative =
                (RelativeLayout.LayoutParams) iconImageLayout.getLayoutParams();
        iconImageLayoutRelative.addRule(RelativeLayout.CENTER_IN_PARENT);
        iconImageLayout.setScaleType(ImageView.ScaleType.FIT_XY);
        iconImageLayout.setBackgroundColor(Color.TRANSPARENT);
        if (resIconImage == null) {
            setIconImage(DEFAULT_RES_ICON);
        } else {
            //noinspection deprecation
            setIconImage(resIconImage);
        }

        // column right component
        LinearLayout itemFrameLayout = new LinearLayout(getContext());
        bodyFrameLayout.addView(itemFrameLayout);
        itemFrameLayout.setOrientation(LinearLayout.VERTICAL);
        ViewGroup.LayoutParams itemFrameLayoutParams =
                (ViewGroup.LayoutParams) itemFrameLayout.getLayoutParams();
        itemFrameLayoutParams.width = ViewGroup.LayoutParams.MATCH_PARENT;
        itemFrameLayoutParams.height = ViewGroup.LayoutParams.WRAP_CONTENT;
        ViewGroup.MarginLayoutParams itemFrameLayoutMargins =
                (ViewGroup.MarginLayoutParams) itemFrameLayout.getLayoutParams();
        itemFrameLayoutMargins.setMargins(30, 0, 0, 0);

        // create title notice component
        RelativeLayout titleFrameLayout = new RelativeLayout(getContext());
        itemFrameLayout.addView(titleFrameLayout);
        ViewGroup.LayoutParams titleFrameLayoutParams =
                (ViewGroup.LayoutParams) titleFrameLayout.getLayoutParams();
        titleFrameLayoutParams.width = ViewGroup.LayoutParams.MATCH_PARENT;
        titleFrameLayoutParams.height = ViewGroup.LayoutParams.WRAP_CONTENT;
        titleFrameLayout.addView(titleLabelLayout);
        ViewGroup.LayoutParams titleLabelLayoutParams =
                (ViewGroup.LayoutParams) titleLabelLayout.getLayoutParams();
        titleLabelLayoutParams.width = ViewGroup.LayoutParams.WRAP_CONTENT;
        titleLabelLayoutParams.height = ViewGroup.LayoutParams.WRAP_CONTENT;
        ViewGroup.MarginLayoutParams titleLabelLayoutMargins =
                (ViewGroup.MarginLayoutParams) titleLabelLayout.getLayoutParams();
        titleLabelLayoutMargins.setMargins(0, 0, 0, 10);
        RelativeLayout.LayoutParams titleLabelLayoutRelative =
                (RelativeLayout.LayoutParams) titleLabelLayout.getLayoutParams();
        titleLabelLayoutRelative.addRule(RelativeLayout.ALIGN_PARENT_TOP);
        titleLabelLayoutRelative.addRule(RelativeLayout.ALIGN_START);
        setTitle(titleText);
        setTitleColor(titleColor);
        setTitleTypeface(Typeface.DEFAULT_BOLD, Typeface.BOLD);
        titleLabelLayout.setTextSize(24f);

        // create message notice component
        RelativeLayout messageFrameLayout = new RelativeLayout(getContext());
        itemFrameLayout.addView(messageFrameLayout);
        ViewGroup.LayoutParams messageFrameLayoutParams =
                (ViewGroup.LayoutParams) messageFrameLayout.getLayoutParams();
        messageFrameLayoutParams.width = ViewGroup.LayoutParams.MATCH_PARENT;
        messageFrameLayoutParams.height = ViewGroup.LayoutParams.WRAP_CONTENT;
        messageFrameLayout.addView(messageLabelLayout);
        ViewGroup.LayoutParams messageLabelLayoutParams =
                (ViewGroup.LayoutParams) messageLabelLayout.getLayoutParams();
        messageLabelLayoutParams.width = ViewGroup.LayoutParams.WRAP_CONTENT;
        messageLabelLayoutParams.height = ViewGroup.LayoutParams.WRAP_CONTENT;
        ViewGroup.MarginLayoutParams messageLabelLayoutMargins =
                (ViewGroup.MarginLayoutParams) messageLabelLayout.getLayoutParams();
        messageLabelLayoutMargins.setMargins(0, 0, 0, 10);
        RelativeLayout.LayoutParams messageLabelLayoutRelative =
                (RelativeLayout.LayoutParams) messageLabelLayout.getLayoutParams();
        messageLabelLayoutRelative.addRule(RelativeLayout.ALIGN_PARENT_TOP);
        messageLabelLayoutRelative.addRule(RelativeLayout.ALIGN_START);
        setMessage(messageText);
        setMessageColor(messageColor);
        messageLabelLayout.setTextSize(16f);

        // create copyright notice component
        RelativeLayout copyrightFrameLayout = new RelativeLayout(getContext());
        mainContentLayout.addView(copyrightFrameLayout);
        ViewGroup.LayoutParams copyrightFrameLayoutParams =
                (ViewGroup.LayoutParams) copyrightFrameLayout.getLayoutParams();
        copyrightFrameLayoutParams.width = ViewGroup.LayoutParams.MATCH_PARENT;
        copyrightFrameLayoutParams.height = ViewGroup.LayoutParams.WRAP_CONTENT;
        copyrightFrameLayout.addView(copyrightLabelLayout);
        ViewGroup.LayoutParams copyrightLabelLayoutParams =
                (ViewGroup.LayoutParams) copyrightLabelLayout.getLayoutParams();
        copyrightLabelLayoutParams.width = ViewGroup.LayoutParams.WRAP_CONTENT;
        copyrightLabelLayoutParams.height = ViewGroup.LayoutParams.WRAP_CONTENT;
        ViewGroup.MarginLayoutParams copyrightLabelLayoutMargins =
                (ViewGroup.MarginLayoutParams) copyrightLabelLayout.getLayoutParams();
        copyrightLabelLayoutMargins.setMargins(30, 30, 30, 0);
        RelativeLayout.LayoutParams copyrightLabelLayoutRelative =
                (RelativeLayout.LayoutParams) copyrightLabelLayout.getLayoutParams();
        copyrightLabelLayoutRelative.addRule(RelativeLayout.ALIGN_PARENT_TOP);
        copyrightLabelLayoutRelative.addRule(RelativeLayout.CENTER_IN_PARENT);
        setCopyright(copyrightText);
        setCopyrightColor(copyrightColor);
        copyrightLabelLayout.setTextSize(16f);


    }

    private View createContentView() {
        // create main view layout
        mainContentLayout = new LinearLayout(getContext());
        //behaviorFrameLayout = new MaterialCardView(getContext());
        //behaviorFrameLayout.addView(mainContentLayout);

        // create all components layout
        closeButtonLayout = new ImageButton(getContext());
        iconImageLayout = new ImageView(getContext());
        titleLabelLayout = new TextView(getContext());
        messageLabelLayout = new TextView(getContext());
        copyrightLabelLayout = new TextView(getContext());

        return mainContentLayout;
    }

    /** Creates the container layout which must exist to find the behavior */
    private FrameLayout ensureContainerAndBehavior() {
        if (container == null) {
            container =
                    (FrameLayout) View.inflate(getContext(), com.google.android.material.R.layout.design_bottom_sheet_dialog, null);

            FrameLayout bottomSheet = (FrameLayout) container.findViewById(com.google.android.material.R.id.design_bottom_sheet);
            bottomSheet.setBackground(new ColorDrawable(Color.TRANSPARENT));
            bottomSheet.addOnLayoutChangeListener(new View.OnLayoutChangeListener() {
                @Override
                public void onLayoutChange(View v, int left, int top, int right, int bottom,
                                           int oldLeft, int oldTop, int oldRight, int oldBottom) {
                    if (orientation != SDScreenManager.getOrientation(getContext())) {
                        orientation = SDScreenManager.getOrientation(getContext());
                        rotation = SDScreenManager.getRotation(getContext());
                        displayMetrics =
                                SDScreenManager.getDisplayMetrics(getContext());
                        SDBottomNoticeDialog.this
                                .onScreenChanged(rotation, orientation, displayMetrics);
                    }
                }
            });
            behavior = BottomSheetBehavior.from(bottomSheet);
            behavior.addBottomSheetCallback(bottomSheetCallback);
            behavior.setHideable(cancelable);
        }
        return container;
    }

    private View wrapInBottomSheet(
            int layoutResId, @Nullable View view, @Nullable ViewGroup.LayoutParams params) {
        ensureContainerAndBehavior();
        CoordinatorLayout coordinator = (CoordinatorLayout) container.findViewById(com.google.android.material.R.id.coordinator);
        if (layoutResId != 0 && view == null) {
            view = getLayoutInflater().inflate(layoutResId, coordinator, false);
        }

        FrameLayout bottomSheet = (FrameLayout) container.findViewById(com.google.android.material.R.id.design_bottom_sheet);
        bottomSheet.setBackground(new ColorDrawable(Color.TRANSPARENT));
        bottomSheet.removeAllViews();
        if (params == null) {
            bottomSheet.addView(view);
        } else {
            bottomSheet.addView(view, params);
        }
        // We treat the CoordinatorLayout as outside the dialog though it is technically inside
        coordinator
                .findViewById(com.google.android.material.R.id.touch_outside)
                .setOnClickListener(
                        new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                if (cancelable && isShowing() && shouldWindowCloseOnTouchOutside()) {
                                    cancel();
                                }
                            }
                        });
        // Handle accessibility events
        ViewCompat.setAccessibilityDelegate(
                bottomSheet,
                new AccessibilityDelegateCompat() {
                    @Override
                    public void onInitializeAccessibilityNodeInfo(
                            View host, @NonNull AccessibilityNodeInfoCompat info) {
                        super.onInitializeAccessibilityNodeInfo(host, info);
                        if (cancelable) {
                            info.addAction(AccessibilityNodeInfoCompat.ACTION_DISMISS);
                            info.setDismissable(true);
                        } else {
                            info.setDismissable(false);
                        }
                    }

                    @Override
                    public boolean performAccessibilityAction(View host, int action, Bundle args) {
                        if (action == AccessibilityNodeInfoCompat.ACTION_DISMISS && cancelable) {
                            cancel();
                            return true;
                        }
                        return super.performAccessibilityAction(host, action, args);
                    }
                });
        bottomSheet.setOnTouchListener(
                new View.OnTouchListener() {
                    @Override
                    public boolean onTouch(View view, MotionEvent event) {
                        // Consume the event and prevent it from falling through
                        return true;
                    }
                });
        return container;
    }

    @NotNull
    private MaterialShapeDrawable createMaterialShapeDrawable(@NonNull View bottomSheet) {
        ShapeAppearanceModel shapeAppearanceModel =

                //Create a ShapeAppearanceModel with the same shapeAppearanceOverlay used in the style
                ShapeAppearanceModel.builder(getContext(), 0, getThemeResId(getContext(), resThemeId))
                        .build();

        //Create a new MaterialShapeDrawable (you can't use the original MaterialShapeDrawable in the BottoSheet)
        MaterialShapeDrawable currentMaterialShapeDrawable = (MaterialShapeDrawable) bottomSheet.getBackground();
        MaterialShapeDrawable newMaterialShapeDrawable = new MaterialShapeDrawable((shapeAppearanceModel));
        //Copy the attributes in the new MaterialShapeDrawable
        newMaterialShapeDrawable.initializeElevationOverlay(getContext());
        newMaterialShapeDrawable.setFillColor(currentMaterialShapeDrawable.getFillColor());
        newMaterialShapeDrawable.setTintList(currentMaterialShapeDrawable.getTintList());
        newMaterialShapeDrawable.setElevation(currentMaterialShapeDrawable.getElevation());
        newMaterialShapeDrawable.setStrokeWidth(currentMaterialShapeDrawable.getStrokeWidth());
        newMaterialShapeDrawable.setStrokeColor(currentMaterialShapeDrawable.getStrokeColor());
        return newMaterialShapeDrawable;
    }

    private static int getThemeResId(@NonNull Context context, int themeId) {
        if (themeId == 0) {
            // If the provided theme is 0, then retrieve the dialogTheme from our theme
            TypedValue outValue = new TypedValue();
            if (context.getTheme().resolveAttribute(com.google.android.material.R.attr.bottomSheetDialogTheme, outValue, true)) {
                themeId = outValue.resourceId;
            } else {
                // bottomSheetDialogTheme is not provided; we default to our light theme
                themeId = com.google.android.material.R.style.Theme_Design_Light_BottomSheetDialog;
            }
        }

        return resThemeId = themeId;
    }

    @NonNull
    private BottomSheetBehavior.BottomSheetCallback bottomSheetCallback =
            new BottomSheetBehavior.BottomSheetCallback() {
                @Override
                public void onStateChanged(
                        @NonNull View bottomSheet, @BottomSheetBehavior.State int newState) {
                    if (newState == BottomSheetBehavior.STATE_HIDDEN) {
                        cancel();
                    } else if (newState == BottomSheetBehavior.STATE_EXPANDED) {
                        //MaterialShapeDrawable newMaterialShapeDrawable = createMaterialShapeDrawable(bottomSheet);
                        //ViewCompat.setBackground(bottomSheet, newMaterialShapeDrawable);
                        ViewCompat.setBackground(bottomSheet, new ColorDrawable(Color.TRANSPARENT));
                    }
                }

                @Override
                public void onSlide(@NonNull View bottomSheet, float slideOffset) {}
            };

    private MaterialCardView behaviorFrameLayout;
    private LinearLayout mainContentLayout;
    private ImageButton closeButtonLayout;
    private ImageView iconImageLayout;

    private TextView titleLabelLayout;
    private TextView messageLabelLayout;
    private TextView copyrightLabelLayout;
}
