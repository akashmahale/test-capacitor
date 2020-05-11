declare global {
  interface PluginRegistry {
      testCapacitor: testCapacitorPlugin;
  }
}
export interface testCapacitorPlugin {
  getInfo(): Promise<DeviceInfo>;
}
interface DeviceInfo {
  /**
   * A percentage (0 to 1) indicating how much the battery is charged
   */
  batteryLevel?: number;
  /**
   * Whether the device is charging
   */
  isCharging?: boolean;
  /**
   * The device locale.
   */
  locale?: string;
  /**
   * The device model. For example, "iPhone"
   */
  model: string;
  /**
   * The device platform (lowercase).
   */
  platform: 'ios' | 'android' | 'electron' | 'web';
  /**
   * The UUID of the device as available to the app. This identifier may change
   * on modern mobile platforms that only allow per-app install UUIDs.
   */
  uuid: string;
  /**
   * The current bundle verison of the app
   */
  appVersion: string;
  /**
   * The current bundle build of the app
   */
  appBuild: string;
  /**
   * The operating system of the device
   */
  operatingSystem: 'ios' | 'android' | 'windows' | 'mac' | 'unknown';
  /**
   * The version of the device OS
   */
  osVersion: string;
  /**
   * The manufacturer of the device
   */
  manufacturer: string;
  /**
   * Whether the app is running in a simulator/emulator
   */
  isVirtual: boolean;
  /**
   * Approximate memory used by the current app, in bytes. Divide by
   * 1048576 to get the number of MBs used.
   */
  memUsed?: number;
  /**
   * How much free disk space is available on the the normal data storage
   * path for the os, in bytes
   */
  diskFree?: number;
  /**
   * The total size of the normal data storage path for the OS, in bytes
   */
  diskTotal?: number;
}
export {};
