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
import android.content.res.TypedArray;
import android.graphics.BlendMode;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.util.TypedValue;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewDebug;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.LinearLayout;

import androidx.annotation.ColorInt;
import androidx.annotation.DrawableRes;
import androidx.annotation.InspectableProperty;
import androidx.annotation.LayoutRes;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatDialog;
import androidx.coordinatorlayout.widget.CoordinatorLayout;
import androidx.core.view.AccessibilityDelegateCompat;
import androidx.core.view.ViewCompat;
import androidx.core.view.accessibility.AccessibilityNodeInfoCompat;

import com.google.android.material.bottomsheet.BottomSheetBehavior;
import com.google.android.material.bottomsheet.BottomSheetDialog;

import org.jetbrains.annotations.NotNull;

import io.github.sdailover.hekstaroid.R;
import io.github.sdailover.hekstaroid.annotation.RemotableViewMethod;
import io.github.sdailover.hekstaroid.annotation.UnsupportedAppUsage;
import io.github.sdailover.hekstaroid.app.SDException;
import io.github.sdailover.hekstaroid.maps.SDDialogInterface;
import io.github.sdailover.hekstaroid.maps.SDScreenChangeListener;
import io.github.sdailover.hekstaroid.utils.SDDeviceManager;
import io.github.sdailover.hekstaroid.utils.SDDisplayMetrics;
import io.github.sdailover.hekstaroid.utils.SDScreenManager;
import io.github.sdailover.hekstaroid.utils.SDScreenMode;

/**
 * <p>SDBottomSheetDialog class for displaying bottom sheet on the application.</p>
 *
 * <p>For initalize, you can use:</p>
 * <p> - {@link #SDBottomSheetDialog(Context)}
 * Creates a dialog window that uses the default dialog theme.</p>
 * <p> - {@link #SDBottomSheetDialog(Context context, int themeResId)}
 * Creates a dialog window that uses a custom dialog style.</p>
 *
 * @author Stephanus Bagus Saputra (wiefunk@stephanusdai.web.id)
 *       and other contributors. See credits file.
 */
public class SDBottomSheetDialog extends AppCompatDialog implements SDDialogInterface {

    private @ColorInt int dialogBackgroundColor = Color.TRANSPARENT;

    private PorterDuff.Mode dialogBackgroundTintMode;
    private ColorStateList dialogBackgroundTintColor;
    private BlendMode dialogBackgroundTintBlendMode;
    private Drawable resBackground;
    private Drawable resCloseImage;
    private boolean closeImageVisibled = true;
    private boolean isForceExists = false;
    private boolean isPaddingRelativeDialog = false;
    private boolean dismissCancelled = false;
    private boolean hasBeforeDismiss = false;
    private boolean hasAfterCancelled = false;
    private boolean canceledOnTouchOutside = true;
    private boolean canceledOnTouchOutsideSet;
    private BottomSheetBehavior<FrameLayout> behavior;
    private FrameLayout container;
    private FrameLayout bottomSheet;
    private @LayoutRes int contentViewResId = 0;
    private Bundle savedInstanceState;
    private View contentViewLayout;
    private ViewGroup.LayoutParams contentViewParams;
    private SDScreenMode lastOrientation = SDScreenMode.ORIENTATION_UNDEFINED;
    private SDDialogInterface.OnBeforeDismissListener beforeDismissListener;
    private SDScreenChangeListener screenChangeListener;
    boolean dismissWithAnimation = true;
    boolean cancelable = true;

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
    protected int dialogPaddingTop = 0;
    /**
     * The final computed bottom padding in pixels that is used for drawing. This is the distance in
     * pixels between the bottom edge of this view and the bottom edge of its content.
     * {@hide}
     */
    @ViewDebug.ExportedProperty(category = "padding")
    @UnsupportedAppUsage
    protected int dialogPaddingBottom = 0;

    @ViewDebug.ExportedProperty(category = "padding")
    @UnsupportedAppUsage
    protected int dialogPaddingStart = 0;

    @ViewDebug.ExportedProperty(category = "padding")
    @UnsupportedAppUsage
    protected int dialogPaddingEnd = 0;

    /**
     * Creates a dialog window that uses the default dialog theme.
     * <p>
     * The supplied {@code context} is used to obtain the window manager and
     * base theme used to present the dialog.
     *
     * @param context the context in which the dialog should run
     */
    public SDBottomSheetDialog(@NonNull Context context) {
        this(context, R.style.Hekstaroid_ThemeOverlay_MaterialComponents_BottomNoticeDialog);
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
    public SDBottomSheetDialog(@NonNull Context context, int themeResId) {
        super(context, getThemeResId(context, themeResId));
        // We hide the title bar for any style configuration. Otherwise, there will be a gap
        // above the bottom sheet when it is expanded.
        supportRequestWindowFeature(Window.FEATURE_NO_TITLE);
    }

    protected SDBottomSheetDialog(@NonNull Context context, boolean cancelable, OnCancelListener cancelListener) {
        super(context, cancelable, cancelListener);
        supportRequestWindowFeature(Window.FEATURE_NO_TITLE);
        this.cancelable = cancelable;
    }

    /**
     * This is initalize of component {@link android.app.Dialog#onCreate}, here it
     * is not necessary including calling {@link #setContentView} for display content.
     *
     * <p>Differences base {@link android.app.Dialog} with {@link SDBottomSheetDialog} class lies in the components that have been
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
        if (this.savedInstanceState == null) {
            this.savedInstanceState = savedInstanceState;
        }
        if (contentViewResId != 0) {
            setContentView(contentViewResId);
        } else if (contentViewLayout != null) {
            setContentView(contentViewLayout, contentViewParams);
        }
        Window window = getWindow();
        if (window != null) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
                window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            }
            window.setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
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
        if (resBackground == null) {
            resBackground = SDDeviceManager.getDrawable(getContext(),
                    R.drawable.hekstaroid_screen_background_topround_light);
        }
        return resBackground;
    }

    /**
     * Sets the background image by name's resource for this view.
     *
     * @param name the string name to getIdentifier {@link android.content.res.Resources}.
     * @author Stephanus Bagus Saputra (wiefunk@stephanusdai.web.id)
     */
    @RemotableViewMethod
    public void setBackground(@NonNull String name) {
        @DrawableRes int resID = SDDeviceManager.getResourceID(getContext(), name);
        if (resID == 0) {
            resBackground = SDDeviceManager.getDrawable(getContext(), resID);
        } else {
            resBackground = SDDeviceManager.getDrawable(getContext(),
                    R.drawable.hekstaroid_screen_background_topround_light);
        }
        UpdateBehaviorComponent();
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
        //noinspection deprecation
        this.setBackgroundDrawable(background);
    }

    /**
     * @deprecated use {@link #setBackground(Drawable)} instead
     */
    @Deprecated
    public void setBackgroundDrawable(@NonNull Drawable background) {
        resBackground = background;
        UpdateBehaviorComponent();
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
        if (resid == 0) {
            resBackground = SDDeviceManager.getDrawable(getContext(),
                    R.drawable.hekstaroid_screen_background_topround_light);
        } else {
            resBackground = SDDeviceManager.getDrawable(getContext(), resid);
        }
        UpdateBehaviorComponent();
    }

    /**
     * Get the background color for this view.
     *
     * @author Stephanus Bagus Saputra (wiefunk@stephanusdai.web.id)
     */
    @InspectableProperty(name = "backgroundColor")
    public @ColorInt int getBackgroundColor() {
        return dialogBackgroundColor;
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
        UpdateBehaviorComponent();
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
        if (bottomSheet != null) {
            dialogBackgroundTintColor = bottomSheet.getBackgroundTintList();
        }
        return dialogBackgroundTintColor;
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
        UpdateBehaviorComponent();
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
        if (bottomSheet != null) {
            dialogBackgroundTintMode = bottomSheet.getBackgroundTintMode();
        }
        return dialogBackgroundTintMode;
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
        UpdateBehaviorComponent();
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
            return dialogBackgroundTintBlendMode;
        } else {
            throw new SDException("Requires API version " + Build.VERSION_CODES.Q + " to use this method.");
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
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
            dialogBackgroundTintBlendMode = blendMode;
            UpdateBehaviorComponent();
        } else {
            throw new SDException("Requires API version " + Build.VERSION_CODES.Q + " to use this method.");
        }
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
        if (bottomSheet != null) {
            dialogPaddingTop = bottomSheet.getPaddingTop();
        }
        return dialogPaddingTop;
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
        if (bottomSheet != null) {
            dialogPaddingBottom = bottomSheet.getPaddingBottom();
        }
        return dialogPaddingBottom;
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
        if (bottomSheet != null) {
            dialogPaddingLeft = bottomSheet.getPaddingLeft();
        }
        return dialogPaddingLeft;
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
        if (bottomSheet != null) {
            dialogPaddingStart = bottomSheet.getPaddingStart();
        }
        return dialogPaddingStart;
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
        if (bottomSheet != null) {
            dialogPaddingRight = bottomSheet.getPaddingRight();
        }
        return dialogPaddingRight;
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
        if (bottomSheet != null) {
            dialogPaddingEnd = bottomSheet.getPaddingEnd();
        }
        return dialogPaddingEnd;
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
        dialogPaddingLeft = left;
        dialogPaddingRight = right;
        dialogPaddingBottom = bottom;
        dialogPaddingTop = top;
        UpdateBehaviorComponent();
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
        if (bottomSheet != null) {
            isPaddingRelativeDialog = bottomSheet.isPaddingRelative();
        }
        return isPaddingRelativeDialog;
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
        dialogPaddingStart = start;
        dialogPaddingTop = top;
        dialogPaddingEnd = end;
        dialogPaddingBottom = bottom;
        UpdateBehaviorComponent();
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
        if (bottomSheet != null) {
            CoordinatorLayout coordinator = (CoordinatorLayout) container.findViewById(R.id.hekstaroid_coordinator);
            ImageButton closeButtonLayout = (ImageButton) coordinator.findViewById(R.id.hekstaroid_close_button);
            resCloseImage = closeButtonLayout.getBackground();
        }
        return resCloseImage;
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
        UpdateBehaviorComponent();
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
        UpdateBehaviorComponent();
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
        UpdateBehaviorComponent();
    }

    /**
     * Returns the visibility status for this view.
     *
     * @return the true if visible and false if not visible.
     */
    @InspectableProperty
    public boolean getCloseImageVisible() {
        if (bottomSheet != null) {
            CoordinatorLayout coordinator = (CoordinatorLayout) container.findViewById(R.id.hekstaroid_coordinator);
            ImageButton closeButtonLayout = (ImageButton) coordinator.findViewById(R.id.hekstaroid_close_button);
            int isViewVisibled = closeButtonLayout.getVisibility();
            if (isViewVisibled == View.GONE) {
                closeImageVisibled = false;
            } else {
                closeImageVisibled = true;
            }
        }
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
        UpdateBehaviorComponent();
    }

    /**
     * Set the activity content from a layout resource.  The resource will be
     * inflated, adding all top-level views to the activity.
     *
     * @param layoutResID Resource ID to be inflated.
     *
     * @see #setContentView(android.view.View)
     * @see #setContentView(android.view.View, android.view.ViewGroup.LayoutParams)
     * @author Stephanus Bagus Saputra (wiefunk@stephanusdai.web.id)
     */
    @UnsupportedAppUsage
    @Override
    public void setContentView(@LayoutRes int layoutResID) {
        contentViewResId = layoutResID;
        super.setContentView(wrapInBottomSheet(layoutResID, null, null));
    }

    /**
     * Set the activity content to an explicit view.  This view is placed
     * directly into the activity's view hierarchy.  It can itself be a complex
     * view hierarchy.  When calling this method, the layout parameters of the
     * specified view are ignored.  Both the width and the height of the view are
     * set by default to {@link ViewGroup.LayoutParams#MATCH_PARENT}. To use
     * your own layout parameters, invoke
     * {@link #setContentView(android.view.View, android.view.ViewGroup.LayoutParams)}
     * instead.
     *
     * @param view The desired content to display.
     *
     * @see #setContentView(int)
     * @see #setContentView(android.view.View, android.view.ViewGroup.LayoutParams)
     * @author Stephanus Bagus Saputra (wiefunk@stephanusdai.web.id)
     */
    @UnsupportedAppUsage
    @Override
    public void setContentView(View view) {
        contentViewLayout = view;
        super.setContentView(wrapInBottomSheet(0, view, null));
    }

    /**
     * Set the activity content to an explicit view.  This view is placed
     * directly into the activity's view hierarchy.  It can itself be a complex
     * view hierarchy.
     *
     * @param view The desired content to display.
     * @param params Layout parameters for the view.
     *
     * @see #setContentView(android.view.View)
     * @see #setContentView(int)
     * @author Stephanus Bagus Saputra (wiefunk@stephanusdai.web.id)
     */
    @UnsupportedAppUsage
    @Override
    public void setContentView(View view, ViewGroup.LayoutParams params) {
        contentViewLayout = view;
        contentViewParams = params;
        super.setContentView(wrapInBottomSheet(0, view, params));
    }

    /**
     * Get Behavior to an interaction behavior plugin for a child view
     * of {@link CoordinatorLayout} to make it work as a bottom sheet.
     *
     * <p>Reference see {@link BottomSheetDialog#getBehavior()}
     * @return BottomSheetBehavior
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
     *
     * <p>Reference see {@link BottomSheetDialog#cancel()}
     */
    @Override
    public void cancel() {
        BottomSheetBehavior<FrameLayout> behavior = getBehavior();

        if (!dismissWithAnimation || behavior.getState() == BottomSheetBehavior.STATE_HIDDEN) {
            if (!hasAfterCancelled && (!hasBeforeDismiss || getCancelDismiss())) {
                super.cancel();
            } else {
                if (getCancelDismiss() != false) {
                    behavior.setState(BottomSheetBehavior.STATE_EXPANDED);
                    hasAfterCancelled = false;
                }
            }
        } else {
            behavior.setState(BottomSheetBehavior.STATE_HIDDEN);
        }
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
     * Sets whether this dialog is canceled when touched outside the window's
     * bounds. If setting to true, the dialog is set to be cancelable if not
     * already set.
     *
     * <p>Reference see {@link BottomSheetDialog#setCanceledOnTouchOutside(boolean)}
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
     * Running the {@link SDDialogInterface.OnBeforeDismissListener} event will be invoked when before the dialog is dismissed.
     *
     * @author Stephanus Bagus Saputra (wiefunk@stephanusdai.web.id)
     */
    protected void beforeDismiss() {
        if (beforeDismissListener != null) {
            beforeDismissListener.onBeforeDismiss(this);
        }
        hasAfterCancelled = true;
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
     * Set a listener to be invoked when before dialog is dismissed.
     * @param listener The {@link SDDialogInterface.OnBeforeDismissListener} to use.
     */
    public void setOnBeforeDismissListener(@Nullable SDDialogInterface.OnBeforeDismissListener listener) {
        beforeDismissListener = listener;
    }

    /**
     * Set to perform the swipe down animation when dismissing instead of the window animation for the
     * dialog.
     *
     * <p>Reference see {@link BottomSheetDialog}
     * @param dismissWithAnimation True if swipe down animation should be used when dismissing.
     */
    public void setDismissWithAnimation(boolean dismissWithAnimation) {
        this.dismissWithAnimation = dismissWithAnimation;
    }

    /**
     * Returns if dismissing will perform the swipe down animation on the bottom sheet, rather than
     * the window animation for the dialog.
     * <p>Reference see {@link BottomSheetDialog}
     */
    public boolean getDismissWithAnimation() {
        return dismissWithAnimation;
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
        if (screenChangeListener != null) {
            screenChangeListener.onScreenChanged(rotation, orientation, displayMetrics);
        } else {
            this.onCreate(savedInstanceState);
        }
    }

    /**
     * Handles screen size, orientation, rotation events when the screen changes.
     * @param listener The {@link SDScreenChangeListener} instance class.
     *
     * @author Stephanus Bagus Saputra (wiefunk@stephanusdai.web.id)
     */
    public void setOnScreenChangeListener(@Nullable SDScreenChangeListener listener) {
        this.screenChangeListener = listener;
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
        this.onCreate(savedInstanceState);
        if (behavior != null && behavior.getState() == BottomSheetBehavior.STATE_HIDDEN) {
            behavior.setState(BottomSheetBehavior.STATE_COLLAPSED);
        } else {
            if (behavior != null) {
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
    }

    /**
     * Creates the container layout which must exist to find the behavior.
     * Redirection from {@link BottomSheetDialog} resource to {@link SDBottomSheetDialog} resource.
     * Add close button and style dialog modified in package.
     *
     * @author Stephanus Bagus Saputra (wiefunk@stephanusdai.web.id)
     */
    private FrameLayout ensureContainerAndBehavior() {
        if (container == null) {
            container =
                    (FrameLayout) View.inflate(getContext(),
                            R.layout.hekstaroid_design_bottom_sheet_dialog, null);

            bottomSheet = (FrameLayout) container.findViewById(R.id.hekstaroid_design_bottom_sheet);
            bottomSheet.addOnLayoutChangeListener(bottomSheetLayoutChangeListener);
            behavior = BottomSheetBehavior.from(bottomSheet);
            behavior.addBottomSheetCallback(bottomSheetCallback);
            behavior.setPeekHeight(0);
            behavior.setSkipCollapsed(true);
            behavior.setFitToContents(true);
            behavior.setHideable(cancelable);
        }
        return container;
    }

    private View wrapInBottomSheet(
            int layoutResId, @Nullable View view, @Nullable ViewGroup.LayoutParams params) {
        ensureContainerAndBehavior();
        CoordinatorLayout coordinator = (CoordinatorLayout) container.findViewById(R.id.hekstaroid_coordinator);
        FrameLayout contentWrapper = (FrameLayout) bottomSheet.findViewById(R.id.hekstaroid_content_wrapper);
        if (layoutResId != 0 && view == null) {
            view = getLayoutInflater().inflate(layoutResId, coordinator, false);
        }
        contentWrapper.removeAllViews();
        if (params == null) {
            contentWrapper.addView(view);
        } else {
            contentWrapper.addView(view, params);
        }
        ViewGroup.LayoutParams contentWrapperParams = (ViewGroup.LayoutParams) contentWrapper.getLayoutParams();
        SDDisplayMetrics displayMetrics = SDScreenManager.getDisplayMetrics(getContext());
        if (displayMetrics.metrics.widthPixels <= 1200) {
            contentWrapperParams.width = ViewGroup.LayoutParams.MATCH_PARENT;
            contentWrapperParams.height = ViewGroup.LayoutParams.WRAP_CONTENT;
        } else {
            contentWrapperParams.width = 1200;
            contentWrapperParams.height = ViewGroup.LayoutParams.WRAP_CONTENT;
        }
        // We treat the CoordinatorLayout as outside the dialog though it is technically inside
        coordinator
                .findViewById(R.id.hekstaroid_touch_outside)
                .setOnClickListener(
                        new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                if (cancelable && isShowing() && shouldWindowCloseOnTouchOutside()) {
                                    cancel();
                                }
                            }
                        });
        coordinator
                .findViewById(R.id.hekstaroid_close_button)
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
        UpdateBehaviorComponent();
        return container;
    }

    private static int getThemeResId(@NonNull Context context, int themeId) {
        if (themeId == 0) {
            // If the provided theme is 0, then retrieve the dialogTheme from our theme
            TypedValue outValue = new TypedValue();
            if (context.getTheme().resolveAttribute(R.attr.bottomSheetDialogTheme, outValue, true)) {
                themeId = outValue.resourceId;
            } else {
                // bottomSheetDialogTheme is not provided; we default to our hekstaroid bottom notice theme
                themeId = R.style.Hekstaroid_ThemeOverlay_MaterialComponents_BottomNoticeDialog;
            }
        }
        return themeId;
    }

    private View.OnLayoutChangeListener bottomSheetLayoutChangeListener = new View.OnLayoutChangeListener() {
        @Override
        public void onLayoutChange(View v, int left, int top, int right, int bottom, int oldLeft, int oldTop, int oldRight, int oldBottom) {
            if (lastOrientation != SDScreenManager.getOrientation(getContext())) {
                SDScreenMode orientation = SDScreenManager.getOrientation(getContext());
                if (lastOrientation != SDScreenMode.ORIENTATION_UNDEFINED) {
                    int rotation = SDScreenManager.getRotation(getContext());
                    SDDisplayMetrics displayMetrics =
                            SDScreenManager.getDisplayMetrics(getContext());
                    SDBottomSheetDialog.this.onScreenChanged(rotation, orientation, displayMetrics);
                }
                lastOrientation = orientation;
            }
        }
    };

    private BottomSheetBehavior.BottomSheetCallback bottomSheetCallback = new BottomSheetBehavior.BottomSheetCallback() {
        @Override
        public void onStateChanged(@NonNull View bottomSheet, int newState) {
            if (newState == BottomSheetBehavior.STATE_HIDDEN) {
                cancel();
            } else if (newState == BottomSheetBehavior.STATE_EXPANDED) {
                bottomSheet.setBackgroundColor(Color.TRANSPARENT);
                if (resBackground != null) {
                    bottomSheet.setBackground(resBackground);
                } else {
                    bottomSheet.setBackgroundResource(R.drawable.hekstaroid_screen_background_topround_light);
                }
            }
        }

        @Override
        public void onSlide(@NonNull View bottomSheet, float slideOffset) {

        }
    };

    private void UpdateBehaviorComponent() {
        if (bottomSheet != null) {
            CoordinatorLayout coordinator = (CoordinatorLayout) container.findViewById(R.id.hekstaroid_coordinator);

            bottomSheet.setPadding(dialogPaddingLeft, dialogPaddingTop, dialogPaddingRight, dialogPaddingBottom);
            bottomSheet.setPaddingRelative(dialogPaddingStart, dialogPaddingTop, dialogPaddingEnd, dialogPaddingBottom);
            if (dialogBackgroundColor != 0) {
                bottomSheet.setBackgroundColor(dialogBackgroundColor);
            } else {
                bottomSheet.setBackgroundColor(Color.TRANSPARENT);
            }
            if (resBackground != null) {
                bottomSheet.setBackground(resBackground);
            } else {
                bottomSheet.setBackgroundResource(R.drawable.hekstaroid_screen_background_topround_light);
            }
            if (dialogBackgroundTintColor != null) {
                bottomSheet.setBackgroundTintList(dialogBackgroundTintColor);
            }
            if (dialogBackgroundTintMode != null) {
                bottomSheet.setBackgroundTintMode(dialogBackgroundTintMode);
            }
            if (dialogBackgroundTintBlendMode != null) {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
                    bottomSheet.setBackgroundTintBlendMode(dialogBackgroundTintBlendMode);
                }
            }
            ImageButton closeButtonLayout = (ImageButton) coordinator.findViewById(R.id.hekstaroid_close_button);
            closeButtonLayout.setBackgroundColor(Color.TRANSPARENT);
            if (resCloseImage != null) {
                closeButtonLayout.setBackground(resCloseImage);
            } else {
                closeButtonLayout.setImageResource(android.R.drawable.ic_delete);
            }
            if (closeImageVisibled != false) {
                closeButtonLayout.setVisibility(View.VISIBLE);
            } else {
                closeButtonLayout.setVisibility(View.GONE);
            }
        }
    }

    private Drawable getDefaultDrawable(@NotNull String resName) {
        @DrawableRes int defResId = SDDeviceManager.getResourceID(getContext(), resName);
        return SDDeviceManager.getDrawable(getContext(), defResId);
    }

    void removeDefaultCallback() {
        behavior.removeBottomSheetCallback(bottomSheetCallback);
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
}
