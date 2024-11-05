package com.example.mystopwatch

import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import java.util.Timer
import kotlin.concurrent.timer

class MainActivity : AppCompatActivity(), View.OnClickListener {
    private lateinit var btn_start: Button
    private lateinit var btn_restart: Button
    private lateinit var tv_min: TextView
    private lateinit var tv_sec: TextView
    private lateinit var tv_millisec: TextView

    private var timer: Timer? = null;
    private var isRunning = false
    private var time = 0

    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        btn_start = findViewById<Button>(R.id.btn_str)
        btn_restart = findViewById<Button>(R.id.btn_restart)
        tv_min = findViewById<TextView>(R.id.tv_minute)
        tv_sec = findViewById<TextView>(R.id.tv_seconds)
        tv_millisec = findViewById<TextView>(R.id.tv_millisec)

        btn_start.setOnClickListener(this)
        btn_restart.setOnClickListener(this)
    }

    override fun onClick(view: View?) {
        when (view?.id) {
            R.id.btn_str -> {
                if (isRunning) {
                    pause()
                } else {
                    start()
                }
            }

            R.id.btn_restart -> {
                refresh()
            }
        }
    }

    private fun start() {
        btn_start.text = getString(R.string.button_stop)
        btn_start.setBackgroundColor(getColor(R.color.button_stop))
        isRunning = true

        timer = timer(period = 10) {
            // 1000 ms = 1 s
            time++
            val miliSec = time % 100
            val sec = (time % 6000) / 100
            val min = time / 6000

            runOnUiThread {
                if (isRunning) {
                    tv_millisec.text = if (miliSec < 10) ".0${miliSec}" else ".${miliSec}"
                    tv_sec.text = if (sec < 10) ":0${sec}" else ":${sec}"
                    tv_min.text = "${min}"
                }
            }
        }
    }

    private fun pause() {
        btn_start.text = getString(R.string.button_start)
        btn_start.setBackgroundColor(getColor(R.color.button_start))

        isRunning = false
        timer?.cancel()
    }

    private fun refresh() {
        timer?.cancel()
        btn_start.text = getString(R.string.button_start)
        btn_start.setBackgroundColor(getColor(R.color.button_start))
        isRunning = false

        time = 0
        tv_min.text = "00"
        tv_sec.text = ":00"
        tv_millisec.text = ",00"
    }
}
