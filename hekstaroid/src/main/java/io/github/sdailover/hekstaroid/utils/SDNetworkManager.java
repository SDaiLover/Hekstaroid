/*
 * Hekstaroid Android Libraries by StephanusDai Developer
 * @link http://sdailover.github.io/
 * @mail team@sdailover.web.id
 * @copyright Copyright (c) 2023 StephanusDai Developer
 * @license http://sdailover.github.io/license.html
 */

package io.github.sdailover.hekstaroid.utils;

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
import static android.Manifest.permission.ACCESS_FINE_LOCATION;
import static android.Manifest.permission.ACCESS_WIFI_STATE;

import android.Manifest;
import android.app.Service;
import android.content.Context;
import android.net.ConnectivityManager;
import android.net.DhcpInfo;
import android.net.Network;
import android.net.NetworkCapabilities;
import android.net.NetworkInfo;
import android.net.wifi.WifiConfiguration;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.os.Build;
import android.os.StrictMode;
import android.telephony.TelephonyManager;
import android.text.TextUtils;
import android.text.format.Formatter;
import android.util.Log;
import android.util.Patterns;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresPermission;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.InetAddress;
import java.net.MalformedURLException;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.net.URL;
import java.net.URLConnection;
import java.util.Enumeration;
import java.util.List;

import javax.net.ssl.HttpsURLConnection;

/**
 * Provides information about device networks such as internet connections
 * with WiFi or cellular operators.
 *
 * <p>The main network can change under certain conditions,
 * therefore this class is needed to provide accurate
 * information when device network changes occur.</p>
 *
 * @author Stephanus Bagus Saputra (wiefunk@stephanusdai.web.id)
 *       and other contributors. See credits file.
 */
public abstract class SDNetworkManager {

    public static final int Network2GList[] = {TelephonyManager.NETWORK_TYPE_GPRS,
            TelephonyManager.NETWORK_TYPE_EDGE,
            TelephonyManager.NETWORK_TYPE_CDMA,
            TelephonyManager.NETWORK_TYPE_1xRTT,
            TelephonyManager.NETWORK_TYPE_IDEN,
            TelephonyManager.NETWORK_TYPE_GSM};

    public static final int Network3GList[] = {TelephonyManager.NETWORK_TYPE_UMTS,
    TelephonyManager.NETWORK_TYPE_EVDO_0,
    TelephonyManager.NETWORK_TYPE_EVDO_A,
    TelephonyManager.NETWORK_TYPE_HSDPA,
    TelephonyManager.NETWORK_TYPE_HSUPA,
    TelephonyManager.NETWORK_TYPE_HSPA,
    TelephonyManager.NETWORK_TYPE_EVDO_B,
    TelephonyManager.NETWORK_TYPE_EHRPD,
    TelephonyManager.NETWORK_TYPE_HSPAP,
    TelephonyManager.NETWORK_TYPE_TD_SCDMA};
    public static final int Network4GList[] = {
            TelephonyManager.NETWORK_TYPE_LTE,
            TelephonyManager.NETWORK_TYPE_IWLAN, 19};
    public static final int Network5GList[] = {TelephonyManager.NETWORK_TYPE_NR};

    @RequiresPermission(Manifest.permission.ACCESS_NETWORK_STATE)
    public final static boolean hasConnected(@NonNull Context context) {
        //noinspection deprecation
        return SDNetworkManager.isAvailableNetwork(context);
    }

    @Deprecated
    @RequiresPermission(Manifest.permission.ACCESS_NETWORK_STATE)
    final static boolean isAvailableNetwork(@NonNull Context context) {
        ConnectivityManager connectivityManager =
                (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            Network activeNetwork = (Network) connectivityManager.getActiveNetwork();
            if (activeNetwork != null) {
                NetworkCapabilities networkCapabilities =
                        (NetworkCapabilities) connectivityManager.getNetworkCapabilities(activeNetwork);
                if (networkCapabilities.hasTransport(NetworkCapabilities.TRANSPORT_WIFI) ||
                        networkCapabilities.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR) ||
                        networkCapabilities.hasTransport(NetworkCapabilities.TRANSPORT_ETHERNET)) {
                    return true;
                }
            }
        } else {
            NetworkInfo activeNetwork = connectivityManager.getActiveNetworkInfo();
            return activeNetwork != null && activeNetwork.isConnectedOrConnecting();
        }
        return false;
    }

    @RequiresPermission(Manifest.permission.ACCESS_NETWORK_STATE)
    public final static SDNetworkType getNetworkType(@NonNull Context context) {
        ConnectivityManager connectivityManager =
                (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
        if (activeNetworkInfo != null) {
            if (activeNetworkInfo.getType() == ConnectivityManager.TYPE_ETHERNET) {
                return SDNetworkType.CONNECTION_ETHERNET;
            } else if (activeNetworkInfo.getType() == ConnectivityManager.TYPE_WIFI) {
                return SDNetworkType.CONNECTION_WIFI;
            } else if (activeNetworkInfo.getType() == ConnectivityManager.TYPE_MOBILE) {
                return SDNetworkType.CONNECTION_CELLULAR;
            } else {
                return SDNetworkType.CONNECTION_UNDEFINED;
            }
        }
        return SDNetworkType.CONNECTION_NONE;
    }

    @RequiresPermission(allOf = {Manifest.permission.INTERNET, Manifest.permission.ACCESS_NETWORK_STATE})
    public final static boolean isAvailableWebsite(@NonNull Context context, @NonNull CharSequence hostName) {
        SDNetworkManager.allowThreadPolicy();
        ConnectivityManager connectivityManager =
                (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        Network activeNetwork = (Network) connectivityManager.getActiveNetwork();
        if (activeNetwork!=null) {
            int responseCode = 0;
            URL urlHost = null;
            try {
                String newHostName = hostName.toString();
                if (isValidUrl(newHostName)) {
                    if (!newHostName.startsWith("http://")
                            && !newHostName.startsWith("https://")) {
                        newHostName = "https://" + hostName;
                    }
                }
                urlHost = new URL(newHostName);
            } catch (MalformedURLException e) {
                e.printStackTrace();
            }
            if (urlHost != null) {
                String urlProtocol = urlHost.getProtocol();
                try {
                    URLConnection urlConnection = activeNetwork.openConnection(urlHost);
                    if (urlConnection instanceof HttpURLConnection) {
                        HttpURLConnection httpConnection = (HttpURLConnection) urlConnection;
                        httpConnection.setInstanceFollowRedirects(true);
                        httpConnection.connect();
                        responseCode = httpConnection.getResponseCode();
                        httpConnection.disconnect();
                    } else if (urlConnection instanceof HttpsURLConnection) {
                        HttpsURLConnection httpsConnection = (HttpsURLConnection) urlConnection;
                        httpsConnection.setInstanceFollowRedirects(true);
                        httpsConnection.connect();
                        responseCode = httpsConnection.getResponseCode();
                        httpsConnection.disconnect();
                    }
                } catch (IOException e) {
                    throw new RuntimeException(e);
                } finally {
                    return responseCode == HttpsURLConnection.HTTP_OK;
                }
            }
        }
        return false;
    }

    @Nullable
    @RequiresPermission(allOf = {ACCESS_WIFI_STATE,
            Manifest.permission.ACCESS_NETWORK_STATE,
            ACCESS_FINE_LOCATION})
    public static SDNetworkInfo getRouteInfo(Context context) throws IOException {
        //noinspection deprecation
        ConnectivityManager connectivityManager = getConnectivityManager(context);
        SDNetworkInfo networkInfo = null;
        NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
        if (activeNetworkInfo != null) {
            if (activeNetworkInfo.getType() == ConnectivityManager.TYPE_WIFI) {
                //noinspection deprecation
                return getWifiInfo(context);
            } else if (activeNetworkInfo.getType() == ConnectivityManager.TYPE_MOBILE) {
                try {
                    String ssid = null;
                    String ipAddress = null;
                    String gateway = null;
                    String netmask = null;
                    String macAddress = null;
                    String dns1 = null;
                    String dns2 = null;
                    String cellName = null;
                    SDCellularType cellType = null;
                    for (Enumeration<NetworkInterface> networkInterfaceList =
                         NetworkInterface.getNetworkInterfaces();
                         networkInterfaceList.hasMoreElements();) {
                        NetworkInterface networkInterface = networkInterfaceList.nextElement();
                        for (Enumeration<InetAddress> inetAddressList =
                             networkInterface.getInetAddresses();
                             inetAddressList.hasMoreElements();) {
                            InetAddress inetAddress = inetAddressList.nextElement();
                            if (!inetAddress.isLoopbackAddress()) {
                                TelephonyManager telephonyManager =
                                        (TelephonyManager) context.getSystemService(Context.TELEPHONY_SERVICE);
                                ipAddress = inetAddress.getHostAddress().toString();
                                byte[] macAddressBytes = networkInterface.getHardwareAddress();
                                if (macAddressBytes != null) {
                                    StringBuilder resMacAddress = new StringBuilder();
                                    for (byte macByte : macAddressBytes) {
                                        resMacAddress.append(String.format("%02X:", macByte));
                                    }
                                    if (resMacAddress.length() > 0) {
                                        resMacAddress.deleteCharAt(resMacAddress.length() - 1);
                                    }
                                    macAddress = resMacAddress.toString();
                                }
                                if (telephonyManager != null) {
                                    cellName = telephonyManager.getSimOperatorName();
                                }
                                cellType = findCellularType(activeNetworkInfo.getSubtype());
                            }
                        }
                    }
                    //noinspection ReassignedVariable
                    networkInfo = createNetworkInfo(ssid, macAddress, ipAddress,
                            netmask, gateway, dns1, dns2, cellName, cellType);
                    Log.e("info", networkInfo.toString());
                } catch (SocketException ex) {
                    ex.printStackTrace();
                }
            }
        }
        return networkInfo;
    }

    public static SDCellularType findCellularType(int dataType) {
        for(int i=0; i < Network2GList.length; i++) {
            if (Network2GList[i] == dataType) {
                return SDCellularType.TYPE_2G;
            }
        }
        for(int i=0; i < Network3GList.length; i++) {
            if (Network3GList[i] == dataType) {
                return SDCellularType.TYPE_3G;
            }
        }
        for(int i=0; i < Network4GList.length; i++) {
            if (Network4GList[i] == dataType) {
                return SDCellularType.TYPE_4G;
            }
        }
        for(int i=0; i < Network5GList.length; i++) {
            if (Network5GList[i] == dataType) {
                return SDCellularType.TYPE_5G;
            }
        }
        return SDCellularType.TYPE_UNKNOWN;
    }

    @Deprecated
    @RequiresPermission(allOf = {ACCESS_WIFI_STATE,
            Manifest.permission.ACCESS_NETWORK_STATE,
            ACCESS_FINE_LOCATION})
    public static SDNetworkInfo getWifiInfo(Context context) throws IOException {
        //noinspection deprecation
        ConnectivityManager connectivityManager = getConnectivityManager(context);
        SDNetworkInfo networkInfo = null;
        //SDNetworkRouteInfo routeInfo = SDNetworkRouteInfo();
        NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
        if (activeNetworkInfo != null) {
            if (activeNetworkInfo.getType() == ConnectivityManager.TYPE_WIFI) {
                WifiManager wifiManager =
                        (WifiManager) context.getSystemService(Service.WIFI_SERVICE);
                WifiInfo wifiInfo = wifiManager.getConnectionInfo();
                String ssid = getSSIDWifiName(context);
                //noinspection deprecation
                String ipAddress = Formatter.formatIpAddress(wifiInfo.getIpAddress());
                //noinspection deprecation
                String gateway = Formatter.formatIpAddress(wifiInfo.getNetworkId());
                String netmask = null;
                String macAddress = wifiInfo.getMacAddress();
                String dns1 = null;
                String dns2 = null;
                String cellName = null;
                SDCellularType cellType = null;
                if (wifiManager.getDhcpInfo() != null) {
                    DhcpInfo dhcpInfo = wifiManager.getDhcpInfo();
                    //noinspection deprecation
                    ipAddress = Formatter.formatIpAddress(dhcpInfo.ipAddress);
                    //noinspection deprecation
                    gateway = Formatter.formatIpAddress(dhcpInfo.gateway);
                    //noinspection deprecation
                    netmask = Formatter.formatIpAddress(dhcpInfo.netmask);
                    //noinspection deprecation
                    dns1 = Formatter.formatIpAddress(dhcpInfo.dns1);
                    //noinspection deprecation
                    dns2 = Formatter.formatIpAddress(dhcpInfo.dns2);
                }
                //noinspection ReassignedVariable
                networkInfo = createNetworkInfo(ssid, macAddress, ipAddress,
                        netmask, gateway, dns1, dns2, cellName, cellType);
            }
        }
        return networkInfo;
    }

    @Nullable
    @RequiresPermission(allOf = {Manifest.permission.ACCESS_WIFI_STATE,
            Manifest.permission.ACCESS_NETWORK_STATE,
            Manifest.permission.ACCESS_FINE_LOCATION})
    public static String getSSIDWifiName(Context context) throws IOException {
        //noinspection deprecation
        ConnectivityManager connectivityManager = getConnectivityManager(context);
        NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
        if (activeNetworkInfo != null) {
            if (activeNetworkInfo.getType() == ConnectivityManager.TYPE_WIFI) {
                WifiManager wifiManager =
                        (WifiManager) context.getSystemService(Service.WIFI_SERVICE);
                List<WifiConfiguration> wifiConfigList = wifiManager.getConfiguredNetworks();
                for (WifiConfiguration configs : wifiConfigList) {
                    //noinspection deprecation
                    if (configs.SSID != null || configs.BSSID != null) {
                        //noinspection deprecation
                        String wifiName = configs.BSSID == null ? configs.SSID : configs.BSSID;
                        return wifiName.replace("\"", "");
                    }
                }
            }
        }
        return null;
    }

    private static SDNetworkInfo createNetworkInfo(String ssidWifi, String macAddress, String ipAddress,  String netmask, String gateway, String dns1, String dns2, String cellName, SDCellularType cellType) {
        SDNetworkInfo networkInfo = new SDNetworkInfo();
        networkInfo.ssidWifi =ssidWifi;
        networkInfo.macAddress = macAddress;
        networkInfo.netmask = netmask;
        networkInfo.ipAddress = ipAddress;
        networkInfo.gateway = gateway;
        networkInfo.dns1 = dns1;
        networkInfo.dns2 = dns2;
        networkInfo.cellularName = cellName;
        networkInfo.cellularType = cellType;
        return networkInfo;
    }

    public final static String getHostName(@NonNull String hostPath) {
        return hostPath.replaceAll("http(s)?://|www\\.|/.*", "");
    }

    public static boolean isHttpUrl(@NonNull String hostPath) {
        String regexExpression = "http://([\\w\\WW-]+\\.)+[\\w\\W-]+(/[\\w\\W- ./?%&=]*)?";
        if (hostPath.matches(regexExpression)) {
            return true;
        }
        return false;
    }

    public final static boolean isValidUrl(@NonNull CharSequence hostPath) {
        return !TextUtils.isEmpty(hostPath) && Patterns.WEB_URL.matcher(hostPath).matches();
    }

    @Deprecated
    final static ConnectivityManager getConnectivityManager(Context context) throws IOException {
        ConnectivityManager connectivityManager =
                (ConnectivityManager) context.getSystemService(Service.CONNECTIVITY_SERVICE);
        if (connectivityManager == null) {
            throw new IOException("No ConnectivityManager yet constructed, please construct one");
        }
        return connectivityManager;
    }

    @RequiresPermission(Manifest.permission.ACCESS_NETWORK_STATE)
    private static void allowThreadPolicy() {
        if (Build.VERSION.SDK_INT > Build.VERSION_CODES.FROYO) {
            StrictMode.ThreadPolicy.Builder policyBuilder = new StrictMode.ThreadPolicy.Builder();
            StrictMode.ThreadPolicy threadPolicy = policyBuilder.permitAll().build();
            StrictMode.setThreadPolicy(threadPolicy);
        }
    }
}
