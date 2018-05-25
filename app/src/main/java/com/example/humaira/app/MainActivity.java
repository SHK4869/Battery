package com.example.humaira.app;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.BatteryManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    TextView status;
    TextView level;
    TextView technology;
    TextView temperature;
    TextView voltage;
    TextView plug;
    TextView health;
    IntentFilter inf;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setUI();
        inf = new IntentFilter(Intent.ACTION_BATTERY_CHANGED);
        BroadcastReceiver br = new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {
                int stat = intent.getIntExtra(BatteryManager.EXTRA_STATUS,-1);
                Boolean isCharging = stat == BatteryManager.BATTERY_STATUS_CHARGING || stat == BatteryManager.BATTERY_STATUS_FULL;
                if(isCharging){
                    status.setText("Charging");
                }
                else{
                    status.setText("Discharging");
                }

                int plugged = intent.getIntExtra(BatteryManager.EXTRA_PLUGGED,-1);
                Boolean isPlugged = plugged == BatteryManager.BATTERY_PLUGGED_USB;
                if(isPlugged){
                    plug.setText("Battery source is USB");
                }
                else{
                    plug.setText("Battery source is AC");
                }
                int volt = intent.getIntExtra(BatteryManager.EXTRA_VOLTAGE,-1);
                voltage.setText(String.valueOf(volt));
                int temp = intent.getIntExtra(BatteryManager.EXTRA_TEMPERATURE,-1);
                //temp-=273;
                temperature.setText(String.valueOf(temp));
                String techno = intent.getStringExtra(BatteryManager.EXTRA_TECHNOLOGY);
                technology.setText(techno);
                int helth = intent.getIntExtra(BatteryManager.EXTRA_HEALTH,-1);
                switch (helth){
                    case BatteryManager.BATTERY_HEALTH_COLD:
                        health.setText("Battery COLD");
                        break;
                    case BatteryManager.BATTERY_HEALTH_DEAD:
                        health.setText("Battery DEAD");
                        break;
                    case BatteryManager.BATTERY_HEALTH_GOOD:
                        health.setText("Battery GOOD");
                        break;
                    case BatteryManager.BATTERY_HEALTH_OVER_VOLTAGE:
                        health.setText("Battery OVER VOLTAGE");
                        break;
                    case BatteryManager.BATTERY_HEALTH_OVERHEAT:
                        health.setText("Battery OVERHEAT");
                        break;
                    case BatteryManager.BATTERY_HEALTH_UNKNOWN:
                        health.setText("Battery HEALTH UNKNOWN");
                        break;
                }
                int lev = intent.getIntExtra(BatteryManager.EXTRA_LEVEL,-1);
                int scale = intent.getIntExtra(BatteryManager.EXTRA_SCALE,-1);
                float battery = (lev/(float)scale)*100;
                level.setText(String.valueOf(
                        battery
                ));
            }
        };
        MainActivity.this.registerReceiver(br,inf);
    }

    public void setUI(){
        status = (TextView)findViewById(R.id.Status);
        level = (TextView)findViewById(R.id.Level);
        technology = (TextView)findViewById(R.id.Tech);
        temperature = (TextView)findViewById(R.id.Temp);
        health = (TextView)findViewById(R.id.Hl);
        plug = (TextView)findViewById(R.id.Pl);
        voltage = (TextView)findViewById(R.id.Vl);
    }
}
