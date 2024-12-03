package com.example.test.user.ui.sensor;


import android.annotation.SuppressLint;
import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.example.test.databinding.FragmentSensorBinding;

public class SensorFragment extends Fragment {

    FragmentSensorBinding binding;
    private SensorManager sensorManager;
    private Sensor lightSensor;
    private Sensor pressureSensor;
    private Sensor relativeHumiditySensor;
    private SensorEventListener lightSensorListener;
    private SensorEventListener pressureSensorListener;
    private SensorEventListener relativeHumiditySensorListener;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        binding = FragmentSensorBinding.inflate(inflater, container, false);
        sensorManager = (SensorManager) requireContext().getSystemService(Context.SENSOR_SERVICE);
        if (sensorManager != null) {
            initLightSensor();
            initPressureSensor();
            initRelativeHumiditySensor();
        }
        setEvents();
        observeData();
        return binding.getRoot();
    }

    private void initPressureSensor() {
        pressureSensor = sensorManager.getDefaultSensor(Sensor.TYPE_PRESSURE);
        if (pressureSensor == null) {
            Toast.makeText(requireActivity(), "Điện thoại không hỗ trợ cảm biến", Toast.LENGTH_SHORT).show();
        } else {
            pressureSensorListener = new SensorEventListener() {
                @SuppressLint("SetTextI18n")
                @Override
                public void onSensorChanged(SensorEvent event) {
                    if (event.sensor.getType() == Sensor.TYPE_PRESSURE) {
                        float pressure = event.values[0];
                        binding.textView2.setText("Áp suất: " + pressure + " hPa");
                    }
                }

                @Override
                public void onAccuracyChanged(Sensor sensor, int accuracy) {
                    System.out.println("accuracy: " + accuracy);
                }
            };
        }
    }

    private void initRelativeHumiditySensor() {
        relativeHumiditySensor = sensorManager.getDefaultSensor(Sensor.TYPE_RELATIVE_HUMIDITY);
        if (relativeHumiditySensor == null) {
            Toast.makeText(requireActivity(), "Điện thoại không hỗ trợ cảm biến", Toast.LENGTH_SHORT).show();
        } else {
            relativeHumiditySensorListener = new SensorEventListener() {
                @SuppressLint("SetTextI18n")
                @Override
                public void onSensorChanged(SensorEvent event) {
                    if (event.sensor.getType() == Sensor.TYPE_RELATIVE_HUMIDITY) {
                        float humidity = event.values[0];
                        binding.textView3.setText("Độ ẩm: " + humidity + " %");
                    }
                }

                @Override
                public void onAccuracyChanged(Sensor sensor, int accuracy) {
                    System.out.println("accuracy: " + accuracy);
                }
            };
        }
    }

    private void initLightSensor() {

        lightSensor = sensorManager.getDefaultSensor(Sensor.TYPE_LIGHT);

        if (lightSensor == null) {
            Toast.makeText(requireActivity(), "Điện thoại không hỗ trợ cảm biến", Toast.LENGTH_SHORT).show();
        } else {
            lightSensorListener = new SensorEventListener() {
                @SuppressLint("SetTextI18n")
                @Override
                public void onSensorChanged(SensorEvent event) {
                    if (event.sensor.getType() == Sensor.TYPE_LIGHT) {
                        float lux = event.values[0];
                        binding.textView.setText("Cường độ ánh sáng: " + lux + " lux");
                    }
                }

                @Override
                public void onAccuracyChanged(Sensor sensor, int accuracy) {
                    System.out.println("accuracy: " + accuracy);
                }
            };
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        if (sensorManager != null) {
            if (lightSensor != null && lightSensorListener != null) {
                sensorManager.registerListener(lightSensorListener, lightSensor, SensorManager.SENSOR_DELAY_UI);
            }
            if (pressureSensor != null && pressureSensorListener != null) {
                sensorManager.registerListener(pressureSensorListener, pressureSensor, SensorManager.SENSOR_DELAY_UI);
            }
            if (relativeHumiditySensor != null && relativeHumiditySensorListener != null) {
                sensorManager.registerListener(relativeHumiditySensorListener, relativeHumiditySensor, SensorManager.SENSOR_DELAY_UI);
            }
        }
    }

    @Override
    public void onPause() {
        super.onPause();
        if (sensorManager != null && lightSensorListener != null) {
            sensorManager.unregisterListener(lightSensorListener);
        }
    }

    private void setEvents() {

    }

    private void observeData() {


    }

}