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
import androidx.annotation.Nullable;

/**
 * Network information structure, such as WiFi SSID name,
 * IP address, MAC address, netmask, gateway, DNS, cellular name.
 *
 * @author Stephanus Bagus Saputra (wiefunk@stephanusdai.web.id)
 *       and other contributors. See credits file.
 */
public class SDNetworkInfo {
    /**
     * Information regarding the SSID name when in a WiFi connection.
     */
    public @Nullable String ssidWifi;
    /**
     * IP address information in device network connectivity.
     */
    public @Nullable String ipAddress;
    /**
     * Get the MAC address of the device.
     */
    public @Nullable String macAddress;
    /**
     * The broadcast value of the connected network.
     */
    public @Nullable String netmask;
    /**
     * Get the IP address of the network connection center.
     */
    public @Nullable String gateway;
    /**
     * Find out the IP address of the network server when the network is connected to WiFi or cellular.
     */
    public @Nullable String dns1;
    /**
     * Find out the IP address of the network server when the network is connected to WiFi or cellular.
     */
    public @Nullable String dns2;
    /**
     * The network device name in cellular mode.
     */
    public @Nullable String cellularName;
    /**
     * Type of device connection mode.
     */
    public SDCellularType cellularType = SDCellularType.TYPE_UNKNOWN;

    public SDNetworkInfo() {}

    public void setTo(SDNetworkInfo o) {
        if (this == o) {
            return;
        }
        ssidWifi = o.ssidWifi;
        macAddress = o.macAddress;
        ipAddress = o.ipAddress;
        netmask = o.netmask;
        gateway = o.gateway;
        cellularName = o.cellularName;
        cellularType = o.cellularType;
        dns1 = o.dns1;
        dns2 = o.dns2;
    }

    public void setToDefaults() {
        ssidWifi = null;
        ipAddress = null;
        netmask = null;
        gateway = null;
        cellularName = null;
        cellularType = SDCellularType.TYPE_UNKNOWN;
        dns1 = null;
        dns2 = null;
    }

    @Override
    public boolean equals(Object o) {
        return o instanceof SDNetworkInfo && equals((SDNetworkInfo)o);
    }

    @Override
    public String toString() {
        return "SDNetworkInfo{ssidWifi=" + ssidWifi + ", macAddress=" + macAddress +
                ", ipAddress=" + ipAddress + ", netmask=" + netmask + ", gateway=" + gateway +
                ", cellularName=" + cellularName + ", cellularType=" + cellularType  +
                ", dns1=" + dns1 + ", dns2=" + dns2 + "}";
    }

}
