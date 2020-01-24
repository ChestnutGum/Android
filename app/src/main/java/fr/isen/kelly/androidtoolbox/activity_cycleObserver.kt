package fr.isen.kelly.androidtoolbox

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.lifecycle.LifecycleObserver
import androidx.lifecycle.*

class activity_cycleObserver : LifecycleObserver {
    var TAG:String="activity_cycleObserver"
    @OnLifecycleEvent(Lifecycle.Event.ON_CREATE)
    fun onCreateEvent() {
        Log.i(TAG, "Observer ON_CREATE")
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_START)
    fun onStartEvent() {
        Log.i(TAG, "Observer ON_START")
        //activity_cycle.onStart()
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_RESUME)
    fun onResumeEvent() {
        Log.i(TAG, "Observer ON_RESUME")
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_PAUSE)
    fun onPauseEvent() {
        Log.i(TAG, "Observer ON_PAUSE")
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_STOP)
    fun onStopEvent() {
        Log.i(TAG, "Observer ON_STOP")
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_DESTROY)
    fun onDestroyEvent() {
        Log.i(TAG, "Observer ON_DESTROY")
    }
}
