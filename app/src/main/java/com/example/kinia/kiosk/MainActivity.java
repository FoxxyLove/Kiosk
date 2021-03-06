package com.example.kinia.kiosk;

import android.app.ActionBar;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import java.util.List;

public class MainActivity extends Activity
{
    private Button hiddenExitButton;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_DISMISS_KEYGUARD);
        setContentView(R.layout.activity_main);

        PrefUtils.setKioskModeActive(true, getApplicationContext());

        hiddenExitButton = (Button) findViewById(R.id.hiddenExitButton);
        hiddenExitButton.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                PackageManager pm = getPackageManager();
                Intent i = new Intent("android.intent.action.MAIN");
                i.addCategory("android.intent.category.HOME");
                List<ResolveInfo> lst = pm.queryIntentActivities(i, 0);
                if (lst != null)
                {
                    for (ResolveInfo resolveInfo : lst)
                    {
                        try
                        {
                            Intent home = new Intent("android.intent.action.MAIN");
                            home.addCategory("android.intent.category.HOME");
                            home.setClassName(resolveInfo.activityInfo.packageName, resolveInfo.activityInfo.name);
                            startActivity(home);
                            break;
                        }
                        catch (Throwable t)
                        {
                            t.printStackTrace();
                        }
                    }
                }
            }
        });
    }

    @Override
    public void onWindowFocusChanged(boolean hasFocus)
    {
        super.onWindowFocusChanged(hasFocus);
        if(!hasFocus)
        {
            Intent closeDialog = new Intent(Intent.ACTION_CLOSE_SYSTEM_DIALOGS);
            sendBroadcast(closeDialog);
        }
        View decorView = getWindow().getDecorView();
        int uiOptions = View.SYSTEM_UI_FLAG_FULLSCREEN;
        decorView.setSystemUiVisibility(uiOptions);
        ActionBar actionBar = getActionBar();
        actionBar.hide();
    }

    @Override
    public void onBackPressed() {}

}