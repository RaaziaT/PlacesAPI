package com.raaziat.pagingexample.ui.stepcounter

import android.content.Context
import android.hardware.Sensor
import android.hardware.SensorEvent
import android.hardware.SensorEventListener
import android.hardware.SensorManager
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.raaziat.pagingexample.R
import com.raaziat.pagingexample.utils.toast
import kotlinx.android.synthetic.main.activity_step_counter.*

class StepCounterActivity : AppCompatActivity(), SensorEventListener {

    var running = false
    private lateinit var sensorManager: SensorManager
    private lateinit  var stepsSensor:Sensor
    private lateinit  var stepsSensorTilt:Sensor
    var count = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_step_counter)

        sensorManager = getSystemService(Context.SENSOR_SERVICE) as SensorManager
        stepsSensor = sensorManager.getDefaultSensor(Sensor.TYPE_STEP_DETECTOR)
        stepsSensorTilt = sensorManager.getDefaultSensor(Sensor.TYPE_GRAVITY)
    }

    override fun onResume() {
        super.onResume()
        running = true

        if (stepsSensor == null) {
            Toast.makeText(this, "No Step Counter Sensor !", Toast.LENGTH_SHORT).show()
        } else {
            sensorManager.registerListener(this, stepsSensor, SensorManager.SENSOR_DELAY_UI)
            sensorManager.registerListener(this, stepsSensorTilt, SensorManager.SENSOR_DELAY_UI)
        }
    }

    override fun onPause() {
        super.onPause()
        running = false
        sensorManager.unregisterListener(this)
    }

    override fun onAccuracyChanged(p0: Sensor?, p1: Int) {
    }

    override fun onSensorChanged(event: SensorEvent) {
        if (running) {
            val sensor = event.sensor
            val values = event.values
            var value = -1

            if(values.isNotEmpty()) value = values[0].toInt()

            if(sensor.type == Sensor.TYPE_STEP_DETECTOR) count++
//            if(sensor.type == Sensor.TYPE_GRAVITY) toast("Woho...",this)

            stepsValue.text = "Count: " + count
        }
    }
}
