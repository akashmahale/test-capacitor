
declare global {
  interface PluginRegistry {
    testCapacitor: testCapacitorPlugin;
  }
}
export interface testCapacitorPlugin {
  getBatteryLevel(): Promise<DeviceBatteryInfo>;
}

interface DeviceBatteryInfo {

  batteryLevel?: number;

  isCharging?: boolean;
}