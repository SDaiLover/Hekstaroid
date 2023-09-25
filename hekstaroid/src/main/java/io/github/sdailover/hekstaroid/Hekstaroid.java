/*
 * Hekstaroid Android Libraries by StephanusDai Developer
 * @link http://sdailover.github.io/
 * @mail team@sdailover.web.id
 * @copyright Copyright (c) 2023 StephanusDai Developer
 * @license http://sdailover.github.io/license.html
 */

package io.github.sdailover.hekstaroid;

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
import android.app.Activity;

import androidx.annotation.NonNull;
import androidx.annotation.InspectableProperty;
import androidx.annotation.Nullable;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.Dictionary;
import java.util.Hashtable;

import io.github.sdailover.hekstaroid.annotation.RemotableViewMethod;
import io.github.sdailover.hekstaroid.components.SDDisplayManagerComponent;

/**
 *
 */
public final class Hekstaroid {
    private static Hekstaroid _self = null;
    private static Activity _app = null;
    private Activity app;

    private @Nullable Dictionary<String, Object> components= new Hashtable<>();

    public Hekstaroid(@NonNull Activity app) {
        this.app = app;
    }

    @RemotableViewMethod
    public static Hekstaroid Instance(@NonNull Activity app) {
        if (_self == null || _app != app) {
            _app = app;
            //noinspection deprecation
            _self = createInstance(_app);
        }
        return _self;
    }

    @Deprecated
    @RemotableViewMethod
    public static Hekstaroid createInstance(@NonNull Activity app) {
        return new Hekstaroid(app);
    }

    private SDDisplayManagerComponent displayManager = null;
    public SDDisplayManagerComponent getDisplayManager() {
        if (displayManager == null) {
            displayManager = new SDDisplayManagerComponent(app);
        }
        return displayManager;
    }

    protected @Nullable Object getComponents(@NonNull String className) {
        return components;
    }

    protected Object createObject(@NonNull String className) {
        return createObject(className, null);
    }

    protected Object createObject(@NonNull String className, @Nullable Object[] arguments) {
        Object object = null;
        try {
            Class classDefinition = Class.forName(className);
            Constructor constructor = classDefinition.getDeclaredConstructor();
            object = constructor.newInstance(arguments);
        } catch (InstantiationException e) {
            System.out.println(e);
        } catch (IllegalAccessException e) {
            System.out.println(e);
        } catch (ClassNotFoundException e) {
            System.out.println(e);
        } catch (InvocationTargetException e) {
            System.out.println(e);
        } catch (NoSuchMethodException e) {
            System.out.println(e);
        }
        return object;
    }

    @InspectableProperty
    public String getCopyright() {
        return "Copyright (c) 2023 StephanusDai Developer";
    }
}
