package com.example.kinia.kiosk;


import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.PowerManager;

public class OnScreenOffReceiver extends BroadcastReceiver
{
    @Override
    public void onReceive(Context context, Intent intent)
    {
        if(Intent.ACTION_SCREEN_OFF.equals(intent.getAction()))
        {
            AppContext ctx = (AppContext) context.getApplicationContext();

            if(PrefUtils.isKioskModeActive(ctx))
            {
                wakeUpDevice(ctx);
            }
        }
    }

    private void wakeUpDevice(AppContext context)
    {
        PowerManager.WakeLock wakeLock = context.getWakeLock();
        if (wakeLock.isHeld())
        {
            wakeLock.release();
        }
        wakeLock.acquire();

        wakeLock.release();
    }
}