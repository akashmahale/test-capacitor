import { WebPlugin } from '@capacitor/core';
import { testCapacitorPlugin } from './definitions';

export class testCapacitorWeb extends WebPlugin implements testCapacitorPlugin {
  constructor() {
    super({
      name: 'testCapacitor',
      platforms: ['web']
    });
  }

  async getBatteryLevel(): Promise<DeviceBatteryInfo> {
    
    return null;
  };


}

const testCapacitor = new testCapacitorWeb();

export { testCapacitor };

import { registerWebPlugin } from '@capacitor/core';
registerWebPlugin(testCapacitor);

interface DeviceBatteryInfo {

  batteryLevel?: number;

  isCharging?: boolean;
}