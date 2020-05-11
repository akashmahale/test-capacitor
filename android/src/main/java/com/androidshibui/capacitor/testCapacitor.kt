package com.androidshibui.capacitor

import com.getcapacitor.*
import android.content.Intent;
import android.content.IntentFilter;
import android.os.BatteryManager;
import java.util.Locale;
import android.os.StatFs;
import android.provider.Settings;
import android.os.Environment;
import android.content.pm.PackageInfo

@NativePlugin
class testCapacitor : Plugin() {

    @PluginMethod
    fun getInfo(call: PluginCall) {
        var r = JSObject()
        r.put("batteryLevel", getBatteryLevel())
        r.put("isCharging", isCharging())
        r.put("locale", Locale.getDefault().getLanguage());
        r.put("memUsed", getMemUsed());
        r.put("diskFree", getDiskFree());
        r.put("diskTotal", getDiskTotal());
        r.put("model", android.os.Build.MODEL);
        r.put("operatingSystem", "android");
        r.put("osVersion", android.os.Build.VERSION.RELEASE);
        r.put("appVersion", getAppVersion());
        r.put("appBuild", getAppBuild());
        r.put("platform", getPlatform());
        r.put("manufacturer", android.os.Build.MANUFACTURER);
        r.put("uuid", getUuid());
        r.put("isVirtual", isVirtual());

        call.success(r)
    }

    private fun getBatteryLevel(): Float {
        var ifilter = IntentFilter(Intent.ACTION_BATTERY_CHANGED)
        var batteryStatus: Intent? = getContext().registerReceiver(null, ifilter)
        var level = -1
        var scale = -1
        if (batteryStatus != null) {
            level = batteryStatus.getIntExtra(BatteryManager.EXTRA_LEVEL, -1)
            scale = batteryStatus.getIntExtra(BatteryManager.EXTRA_SCALE, -1)
        }
        return level / scale.toFloat()
    }

    private fun isCharging(): Boolean {
        var ifilter = IntentFilter(Intent.ACTION_BATTERY_CHANGED)
        var batteryStatus: Intent? = getContext().registerReceiver(null, ifilter)
        if (batteryStatus != null) {
            val status: Int = batteryStatus.getIntExtra(BatteryManager.EXTRA_STATUS, -1)
            return status == BatteryManager.BATTERY_STATUS_CHARGING || status == BatteryManager.BATTERY_STATUS_FULL
        }
        return false
    }

    private fun getMemUsed(): Long {
        val runtime: java.lang.Runtime = java.lang.Runtime.getRuntime()
        return runtime.totalMemory() - runtime.freeMemory()
    }

    private fun getDiskFree(): Long {
        val statFs = StatFs(Environment.getRootDirectory().getAbsolutePath())
        return statFs.getAvailableBlocksLong() * statFs.getBlockSizeLong()
    }

    private fun getDiskTotal(): Long {
        val statFs = StatFs(Environment.getRootDirectory().getAbsolutePath())
        return statFs.getBlockCountLong() * statFs.getBlockSizeLong()
    }

    private fun getAppVersion(): String? {
        return try {
            val pinfo: PackageInfo = getContext().getPackageManager().getPackageInfo(getContext().getPackageName(), 0)
            pinfo.versionName
        } catch (ex: java.lang.Exception) {
            ""
        }
    }

    private fun getAppBuild(): String? {
        return try {
            val pinfo: PackageInfo = getContext().getPackageManager().getPackageInfo(getContext().getPackageName(), 0)
            java.lang.Integer.toString(pinfo.versionCode)
        } catch (ex: java.lang.Exception) {
            ""
        }
    }

    private fun getPlatform(): String? {
        return "android"
    }

    private fun getUuid(): String? {
        return Settings.Secure.getString(this.bridge.getContext().getContentResolver(), android.provider.Settings.Secure.ANDROID_ID)
    }

    private fun isVirtual(): Boolean {
        return android.os.Build.FINGERPRINT.contains("generic") || android.os.Build.PRODUCT.contains("sdk")
    }

}