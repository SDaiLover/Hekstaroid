/*
 * Hekstaroid Android Libraries by StephanusDai Developer
 * @link http://sdailover.github.io/
 * @mail team@sdailover.web.id
 * @copyright Copyright (c) 2023 StephanusDai Developer
 * @license http://sdailover.github.io/license.html
 */

package io.github.sdailover.hekstaroid.content;

import android.app.Dialog;
import android.content.Context;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.util.TypedValue;
import android.view.KeyEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.FrameLayout;

import androidx.annotation.LayoutRes;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatDialog;
import androidx.coordinatorlayout.widget.CoordinatorLayout;

import com.google.android.material.bottomsheet.BottomSheetBehavior;
import com.google.android.material.bottomsheet.BottomSheetDialog;

import io.github.sdailover.hekstaroid.R;
import io.github.sdailover.hekstaroid.annotation.UnsupportedAppUsage;
import io.github.sdailover.hekstaroid.maps.SDDialogInterface;
import io.github.sdailover.hekstaroid.maps.SDScreenChangeListener;
import io.github.sdailover.hekstaroid.utils.SDDisplayMetrics;
import io.github.sdailover.hekstaroid.utils.SDScreenManager;
import io.github.sdailover.hekstaroid.utils.SDScreenMode;

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
public class SDLaunchSheetDialog extends AppCompatDialog implements SDDialogInterface {
    private boolean dismissCancelled = false;
    private boolean hasBeforeDismiss = false;
    private boolean hasAfterCancelled = false;
    private boolean isForceExists = false;

    private boolean isHideStateBehavior = false;
    private Bundle savedInstanceState;
    private FrameLayout behavior;
    private FrameLayout container;
    private FrameLayout bindingLayout;
    private SDDialogInterface.OnBeforeDismissListener beforeDismissListener;
    private SDScreenChangeListener screenChangeListener;
    boolean dismissWithAnimation = true;
    boolean cancelable = true;

    public SDLaunchSheetDialog(@NonNull Context context) {
        this(context, R.style.Hekstaroid_Theme_MaterialComponents_Light_FullScreen);
    }

    public SDLaunchSheetDialog(@NonNull Context context, int themeResId) {
        super(context, getThemeResId(context, themeResId));
        // We hide the title bar for any style configuration. Otherwise, there will be a gap
        // above the bottom sheet when it is expanded.
        supportRequestWindowFeature(Window.FEATURE_NO_TITLE);
    }

    protected SDLaunchSheetDialog(@NonNull Context context, boolean cancelable, OnCancelListener cancelListener) {
        super(context, cancelable, cancelListener);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (this.savedInstanceState == null) {
            this.savedInstanceState = savedInstanceState;
        }
        Window window = getWindow();
        if (window != null) {
            SDScreenManager.hideWindowUI(window, false, false);
            window.setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
        }
        this.setContentViewInternal();
    }

    /**
     * not support method for SDLaunchSheetDialog
     */
    @Deprecated
    @UnsupportedAppUsage
    @Override
    public void setContentView(@LayoutRes int layoutResId) {
        this.setContentViewInternal();
    }

    /**
     * not support method for SDLaunchSheetDialog
     */
    @Deprecated
    @UnsupportedAppUsage
    @Override
    public void setContentView(View view) {
        this.setContentViewInternal();
    }

    /**
     * not support method for SDLaunchSheetDialog
     */
    @Deprecated
    @UnsupportedAppUsage
    @Override
    public void setContentView(View view, ViewGroup.LayoutParams params) {
        this.setContentViewInternal();
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

    /**
     * Get result from event {@link OnBeforeDismissListener} which will prevent
     * the dismissal process so that it can handle other code processes and
     * if there is a {@link Dialog} instance on
     * inside {@link OnBeforeDismissListener},
     * it can bring it up first and without skipping it.
     *
     * @return boolean the cancel or return to prosess {@link #dismiss()}.
     * @author Stephanus Bagus Saputra (wiefunk@stephanusdai.web.id)
     */
    @Override
    public boolean getCancelDismiss() {
        return dismissCancelled;
    }

    /**
     * Set cancel or not {@link #dismiss()} when you want to proses run another
     * {@link SDDialogInterface} or {@link Thread} before dismissed.
     * <p/>
     * <div class="special reference">
     * <h3>Developer Guides</h3>
     * <p>To handle another {@link SDDialogInterface} or proses {@link Thread}, use
     * <code>setCancelDismiss(true)</code> in section
     * {@link OnBeforeDismissListener#onBeforeDismiss(SDDialogInterface)}. And then
     * <code>setCancelDismiss(false)</code> in section another
     * {@link SDDialogInterface} or proses {@link Thread}</p>
     * </div>
     *
     * @param allowed the set is true or false to prevent {@link #dismiss()}.
     * @author Stephanus Bagus Saputra (wiefunk@stephanusdai.web.id)
     */
    @Override
    public void setCancelDismiss(boolean allowed) {
        dismissCancelled = allowed;
    }

    /**
     * This method will be called when the screen device has been changed.
     *
     * @param listener The {@link OnBeforeDismissListener} instance.
     * @author Stephanus Bagus Saputra (wiefunk@stephanusdai.web.id)
     */
    @Override
    public void setOnBeforeDismissListener(@Nullable OnBeforeDismissListener listener) {
        beforeDismissListener = listener;
    }

    @Override
    public void cancel() {
        FrameLayout behavior = getBehavior();

        if (!dismissWithAnimation || isHideStateBehavior) {
            if (!hasAfterCancelled && (!hasBeforeDismiss || getCancelDismiss())) {
                super.cancel();
            } else {
                if (getCancelDismiss() != false) {
                    hasAfterCancelled = false;
                }
            }
        } else {
            behavior.animate().scaleX(0f).scaleY(0f).alpha(0f).setDuration(1000).withEndAction(new Runnable() {
                @Override
                public void run() {
                    isHideStateBehavior = true;
                    cancel();
                }
            }).start();
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
                if (cancelable) {
                    super.cancel();
                }
            }
        }
    }

    /**
     * Get Behavior to an interaction behavior plugin for a child view
     * of {@link CoordinatorLayout} to make it work as a bottom sheet.
     *
     * <p>Reference see {@link SDLaunchSheetDialog#getBehavior()}
     * @return FrameLayout SDLaunchSheetDialog
     */
    @NonNull
    public FrameLayout getBehavior() {
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

    @Override
    protected void onStart() {
        if (savedInstanceState != null) {
            this.onCreate(savedInstanceState);
        }
    }

    private FrameLayout ensureContainerAndBehavior() {
        if (container == null) {
            container =
                    (FrameLayout) View.inflate(getContext(),
                            R.layout.hekstaroid_design_launch_sheet_dialog, null);

            behavior = (FrameLayout) container.findViewById(R.id.hekstaroid_design_launch_sheet);
        }
        return container;
    }

    private View wrapInLaunchSheet(
            int layoutResId, @Nullable View view, @Nullable ViewGroup.LayoutParams params) {
        ensureContainerAndBehavior();
        CoordinatorLayout coordinator = (CoordinatorLayout) container.findViewById(R.id.hekstaroid_coordinator);
        FrameLayout launchSheet = (FrameLayout) container.findViewById(R.id.hekstaroid_design_launch_sheet);

        if (layoutResId != 0 && view == null) {
            view = getLayoutInflater().inflate(layoutResId, coordinator, false);
        }
        launchSheet.removeAllViews();
        if (params == null) {
            launchSheet.addView(view);
        } else {
            launchSheet.addView(view, params);
        }
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                if (cancelable && isShowing()) {
                    cancel();
                }
            }
        }, 3000);

        return container;
    }

    private static int getThemeResId(@NonNull Context context, int themeId) {
        if (themeId == 0) {
            // If the provided theme is 0, then retrieve the dialogTheme from our theme
            TypedValue outValue = new TypedValue();
            if (context.getTheme().resolveAttribute(R.attr.dialogTheme, outValue, true)) {
                themeId = outValue.resourceId;
            } else {
                themeId = R.style.Hekstaroid_Theme_MaterialComponents_Light_FullScreen;
            }
        }
        return themeId;
    }

    private void setContentViewInternal() {
        bindingLayout = (FrameLayout) View.inflate(getContext(),
                R.layout.hekstaroid_design_launch_sheet_content, null);
        super.setContentView(wrapInLaunchSheet(0, bindingLayout, null));
    }
}
