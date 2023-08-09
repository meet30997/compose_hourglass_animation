package com.learn.gamesearchcompose.presentation.ui.hourglass

import android.os.CountDownTimer
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.launch

class HourGlassViewModel : ViewModel() {
    // Represents the ongoing CountDownTimer instance
    private var countDownTimer: CountDownTimer? = null

    // A MutableSharedFlow that emits tick values representing animation progress
    private val tick = MutableSharedFlow<Float>()

    // Exposes the tick flow for observing animation progress
    val getTick = tick

    // Starts the countdown timer with the given duration in seconds
    fun startTimer(durationInSecond: Long) {
        // If a timer is already running, do nothing
        if (countDownTimer != null) {
            return
        }

        // Convert duration to milliseconds
        val duration = durationInSecond * 1000L

        // Create and start the CountDownTimer
        countDownTimer = object : CountDownTimer(
            duration,
            1
        ) {
            // This function is called on every tick (every millisecond)
            override fun onTick(millisUntilFinished: Long) {
                // Calculate animation progress and emit it using coroutine
                viewModelScope.launch {
                    tick.emit((duration - millisUntilFinished).toFloat() / duration)
                }
            }

            // This function is called when the timer finishes
            override fun onFinish() {
                // Emit a final progress value of 1.0 (100%)
                viewModelScope.launch {
                    tick.emit(1f)
                }
            }
        }.start()
    }

    // Stops the timer and resets the progress to 0
    fun stopTimer() {
        // Emit a progress value of 0 to reset animation
        viewModelScope.launch {
            tick.emit(0f)
        }

        // Cancel the ongoing timer, if any
        countDownTimer?.cancel()

        // Set the timer instance to null
        countDownTimer = null
    }
}
