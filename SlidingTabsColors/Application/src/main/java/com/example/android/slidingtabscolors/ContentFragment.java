/*
 * Copyright 2013 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *       http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.example.android.slidingtabscolors;

import android.app.Application;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.content.Context;
import android.hardware.Sensor;
import java.util.List;
import android.widget.Toast;

/**
 * Simple Fragment used to display some meaningful content for each page in the sample's
 * {@link android.support.v4.view.ViewPager}.
 */
public class ContentFragment extends Fragment implements SensorEventListener{

    private static final String KEY_TITLE = "title";
    private static final String KEY_INDICATOR_COLOR = "indicator_color";
    private static final String KEY_DIVIDER_COLOR = "divider_color";

    private final float lux = 0;
    private long lastUpdate;

    public TextView title1;

   //TextView title1 = (TextView) view.findViewById(R.id.item_title);

        @Override
        public void onSensorChanged(SensorEvent event) {

            if(event.sensor.getType() == Sensor.TYPE_LIGHT) {
                getSensorOneValue(event, "Lightness");
            }
            else if(event.sensor.getType() == Sensor.TYPE_AMBIENT_TEMPERATURE) {
                getSensorOneValue(event, "Temperature");
            }
            else if(event.sensor.getType() == Sensor.TYPE_GRAVITY) {
                getSensorOneValue(event, "Gravity");
            }
            else if(event.sensor.getType() == Sensor.TYPE_RELATIVE_HUMIDITY) {
                getSensorOneValue(event, "Humidity");
            }
            else if(event.sensor.getType() == Sensor.TYPE_PRESSURE) {
                getSensorOneValue(event, "Pressure");
            }
            else if(event.sensor.getType() == Sensor.TYPE_PROXIMITY) {
                getSensorOneValue(event, "Proximity");
            }
        }

        @Override
        public void onAccuracyChanged(Sensor sensor, int accuracy) {}


    private void getSensorOneValue(SensorEvent event, String title) {
        float[] values = event.values;
        float x = values[0];

        long actualTime = event.timestamp;

        if (actualTime - lastUpdate < 200) {
            return;
        }
        lastUpdate = actualTime;
        title1.setText(title +": " + String.valueOf(x));
    }
    /**
     * @return a new instance of {@link ContentFragment}, adding the parameters into a bundle and
     * setting them as arguments.
     */
    public static ContentFragment newInstance(CharSequence title, int indicatorColor,
            int dividerColor) {
        Bundle bundle = new Bundle();
        bundle.putCharSequence(KEY_TITLE, title);
        bundle.putInt(KEY_INDICATOR_COLOR, indicatorColor);
        bundle.putInt(KEY_DIVIDER_COLOR, dividerColor);

        ContentFragment fragment = new ContentFragment();
        fragment.setArguments(bundle);


        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState) {
        return inflater.inflate(R.layout.pager_item, container, false);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        Bundle args = getArguments();
        TextView title = (TextView) view.findViewById(R.id.item_title);
        TextView uom = (TextView) view.findViewById(R.id.item_indicator_color);
        title1 = title;
        Context context = title.getContext();
        SensorManager mgr = (SensorManager) context.getSystemService(Context.SENSOR_SERVICE);

        if (args != null) {

            if(args.getCharSequence(KEY_TITLE).equals("Lightness")) {

                Sensor light_s = mgr.getDefaultSensor(Sensor.TYPE_LIGHT);
                List<Sensor> list = mgr.getSensorList(Sensor.TYPE_LIGHT);

                mgr.registerListener(this, light_s, SensorManager.SENSOR_DELAY_UI);
                uom.setText("Unit of Measurement: " + "Lux");

            }
            else if(args.getCharSequence(KEY_TITLE).equals("Temperature")) {

                Sensor temp_s = mgr.getDefaultSensor(Sensor.TYPE_AMBIENT_TEMPERATURE);
                List<Sensor> list = mgr.getSensorList(Sensor.TYPE_AMBIENT_TEMPERATURE);

                mgr.registerListener(this, temp_s, SensorManager.SENSOR_DELAY_UI);
                uom.setText("Unit of Measurement: " + "Degree Celcius");

            }
            else if(args.getCharSequence(KEY_TITLE).equals("Gravity")) {

                Sensor grvt_s = mgr.getDefaultSensor(Sensor.TYPE_GRAVITY);
                List<Sensor> list = mgr.getSensorList(Sensor.TYPE_GRAVITY);

                mgr.registerListener(this, grvt_s, SensorManager.SENSOR_DELAY_UI);
                uom.setText("Unit of Measurement: " + "m*s^2");

            }
            else if(args.getCharSequence(KEY_TITLE).equals("Humidity")) {

                Sensor hmdt_s = mgr.getDefaultSensor(Sensor.TYPE_RELATIVE_HUMIDITY);
                List<Sensor> list = mgr.getSensorList(Sensor.TYPE_RELATIVE_HUMIDITY);

                mgr.registerListener(this, hmdt_s, SensorManager.SENSOR_DELAY_UI);
                uom.setText("Unit of Measurement: " + "%");

            }
            else if(args.getCharSequence(KEY_TITLE).equals("Proximity")) {

                Sensor hmdt_s = mgr.getDefaultSensor(Sensor.TYPE_PROXIMITY);
                List<Sensor> list = mgr.getSensorList(Sensor.TYPE_PROXIMITY);

                mgr.registerListener(this, hmdt_s, SensorManager.SENSOR_DELAY_UI);
                uom.setText("Unit of Measurement: " + "cm");

            }
            else if(args.getCharSequence(KEY_TITLE).equals("Pressure")) {

                Sensor hmdt_s = mgr.getDefaultSensor(Sensor.TYPE_PRESSURE);
                List<Sensor> list = mgr.getSensorList(Sensor.TYPE_PRESSURE);

                mgr.registerListener(this, hmdt_s, SensorManager.SENSOR_DELAY_UI);
                uom.setText("Unit of Measurement: " + "mBar");

            }
            else{

                title.setText("Title: " + args.getCharSequence(KEY_TITLE));

            }

            int indicatorColor = args.getInt(KEY_INDICATOR_COLOR);
            TextView indicatorColorView = (TextView) view.findViewById(R.id.item_indicator_color);
            indicatorColorView.setText("Indicator: #" + Integer.toHexString(indicatorColor));
            indicatorColorView.setTextColor(indicatorColor);

            int dividerColor = args.getInt(KEY_DIVIDER_COLOR);
            TextView dividerColorView = (TextView) view.findViewById(R.id.item_divider_color);
            dividerColorView.setText("Divider: #" + Integer.toHexString(dividerColor));
            dividerColorView.setTextColor(dividerColor);
        }
    }
}
