package com.androidshibui.capacitor

import com.getcapacitor.*
import android.content.Intent;
import android.content.IntentFilter;
import android.os.BatteryManager;

@NativePlugin
class testCapacitor : Plugin() {

    @PluginMethod
    fun getBatteryInfo(call: PluginCall) {
        var r = JSObject()
        r.put("batteryLevel", getBatteryLevel())
        r.put("isCharging", isCharging())
        call.success(r)
    }

    private fun getBatteryLevel(): Float {
        var ifilter = IntentFilter(Intent.ACTION_BATTERY_CHANGED)
        var batteryStatus: Intent = getContext().registerReceiver(null, ifilter)
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
        var batteryStatus: Intent = getContext().registerReceiver(null, ifilter)
        if (batteryStatus != null) {
            val status: Int = batteryStatus.getIntExtra(BatteryManager.EXTRA_STATUS, -1)
            return status == BatteryManager.BATTERY_STATUS_CHARGING || status == BatteryManager.BATTERY_STATUS_FULL
        }
        return false
    }

}