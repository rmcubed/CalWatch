/*
 * Copyright (C) 2015 Dan Wallach <dwallach@rice.edu>
 *
 * (Note: license for this specific file differs from the rest
 *  of the CalWatch project.)
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.dwallach.calwatch

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import org.jetbrains.anko.*

/**
 * Simple code to watch for updates (via broadcast intents) to the shared x.stopwatch and
 * x.timer state, transmitted by any compliant stopwatch or countdown timer apps.

 * Note that this just dumps out its state into a series of public static variables,
 * which you should feel free to read (and re-read) in your watchface's onDraw() handler.

 * If you want to get some sort of notification, then you should hack that behavior into the onReceive
 * method somewhere.
 *
 * This class is instantiated by putting it into your AndroidManifest.xml file, e.g.
 *
 *         <receiver android:name=".XWatchfaceReceiver">
 *           <intent-filter>
 *           <action android:name="org.dwallach.x.stopwatch.update" />
 *           <action android:name="org.dwallach.x.timer.update" />
 *           </intent-filter>
 *           </receiver>
 */
class XWatchfaceReceiver : BroadcastReceiver(), AnkoLogger {
    override fun onReceive(context: Context, intent: Intent) {
        verbose { "got intent: ${intent.action}" }

        with (intent.extras) {
            when (intent.action) {
                STOPWATCH_UPDATE_INTENT -> {
                    // TODO: fix the IntelliJ warnings about version numbers below (which seem fine in the docs)
                    stopwatchStart = getLong(PREF_STOPWATCH_START_TIME)
                    stopwatchBase = getLong(PREF_STOPWATCH_BASE_TIME)
                    stopwatchIsRunning = getBoolean(PREF_STOPWATCH_RUNNING)
                    stopwatchIsReset = getBoolean(PREF_STOPWATCH_RESET)
                    stopwatchUpdateTimestamp = getLong(PREF_STOPWATCH_UPDATE_TIMESTAMP)

                    verbose { "stopwatch update:: start($stopwatchStart), base($stopwatchBase), isRunning($stopwatchIsRunning), isReset($stopwatchIsReset), updateTimestamp($stopwatchUpdateTimestamp)" }
                }

                TIMER_UPDATE_INTENT -> {
                    timerStart = getLong(PREF_TIMER_START_TIME)
                    timerPauseElapsed = getLong(PREF_TIMER_PAUSE_ELAPSED)
                    timerDuration = getLong(PREF_TIMER_DURATION)
                    timerIsRunning = getBoolean(PREF_TIMER_RUNNING)
                    timerIsReset = getBoolean(PREF_TIMER_RESET)
                    timerUpdateTimestamp = getLong(PREF_TIMER_UPDATE_TIMESTAMP)

                    // sanity checking: if we're coming back from whatever and a formerly running
                    // timer has gotten way past the deadline, then just reset things.
                    val currentTime = System.currentTimeMillis()
                    if (timerIsRunning && timerStart + timerDuration < currentTime) {
                        timerIsReset = true
                        timerIsRunning = false
                    }

                    verbose { "timer update:: start($timerStart), elapsed($timerPauseElapsed), duration($timerDuration), isRunning($timerIsRunning), isReset($timerIsReset), updateTimestamp($timerUpdateTimestamp)" }
                }
            }
        }
    }

    companion object: AnkoLogger {
        const val PREF_STOPWATCH_RUNNING = "running"
        const val PREF_STOPWATCH_RESET = "reset"
        const val PREF_STOPWATCH_START_TIME = "start"
        const val PREF_STOPWATCH_BASE_TIME = "base"
        const val PREF_STOPWATCH_UPDATE_TIMESTAMP = "updateTimestamp"

        const val PREF_TIMER_RUNNING = "running"
        const val PREF_TIMER_RESET = "reset"
        const val PREF_TIMER_START_TIME = "start"
        const val PREF_TIMER_PAUSE_ELAPSED = "elapsed"
        const val PREF_TIMER_DURATION = "duration"
        const val PREF_TIMER_UPDATE_TIMESTAMP = "updateTimestamp"

        const val STOPWATCH_UPDATE_INTENT = "org.dwallach.x.stopwatch.update"
        const val STOPWATCH_QUERY_INTENT = "org.dwallach.x.stopwatch.query"

        const val TIMER_UPDATE_INTENT = "org.dwallach.x.timer.update"
        const val TIMER_QUERY_INTENT = "org.dwallach.x.timer.query"

        /**
         * the time (GMT) when the user clicked "start" on the stopwatch
         */
        var stopwatchStart: Long = 0

        /**
         * base time to add in to account for when the stopwatch wasn't running
         */
        var stopwatchBase: Long = 0

        /**
         * is the stopwatch running?
         */
        var stopwatchIsRunning: Boolean = false

        /**
         * Is the stopwatch reset? If so, you can assume it's not running and the value is zero.
         * You can furthermore not bother rendering it at all.
         */
        var stopwatchIsReset = true // default until we get a useful broadcast message

        /**
         * The last time at which we received an update to the stopwatch status. If the user is,
         * for some reason, running multiple stopwatches, all of which are generating the same
         * intent messages, the latest one that the user touched will have the most recent timestamp
         * on its messages, and that's the one you'll be displaying.
         */
        var stopwatchUpdateTimestamp: Long = 0

        /**
         * The time (GMT) when the user began running the countdown timer. If the users pauses
         * the timer and later restarts it, this value will change. If the current time is
         * equal to timerStart, then the countdown timer should read the duration value.
         * If the current time is greater than or equal to timerStart + duration, then the
         * timer has completed. (The timer should then reset itself.)
         */
        var timerStart: Long = 0

        /**
         * If the timer is paused, this is how much time has elapsed; subtract from
         * duration to know how much time is left. Ignore this if the timer is running.
         */
        var timerPauseElapsed: Long = 0

        /**
         * The total amount of time for the timer.
         */
        var timerDuration: Long = 0


        /**
         * Is the timer running?
         */
        var timerIsRunning: Boolean = false

        /**
         * Is the timer reset? If so, you can assume its value is equal to the duration.
         * You can also assume it's not running and you may choose not to display anything at all.
         */
        var timerIsReset = true // default until we get a useful broadcast message

        /**
         * The last time at which we received an update to the timer status. If the user is,
         * for some reason, running multiple timers, all of which are generating the same
         * intent messages, the latest one that the user touched will have the most recent timestamp
         * on its messages, and that's the one you'll be displaying.
         */
        var timerUpdateTimestamp: Long = 0

        /**
         * Call this and it will ask external stopwatches and timers to report back with their
         * state. If we already *have* state locally and nothing has changed remotely, then this
         * is just a no-op.

         * If you've called this and the state in the relevant variables here is still empty,
         * then it's possible there are no stopwatches running at all. If one of them shows up,
         * then the messages will be received here and written into the relevant public static fields.

         * Once you're up and running, you don't need to call this again, as you'll presumably be registered to
         * receive these messages automatically.

         * Best called in places like onCreate(). Best not called in onDraw().

         * @param context
         */
        fun pingExternalStopwatches(context: Context) {
            if (stopwatchUpdateTimestamp == 0L) {
                verbose("sending broadcast query for external stopwatches")
                context.sendBroadcast(Intent(STOPWATCH_QUERY_INTENT))
            }
            if (timerUpdateTimestamp == 0L) {
                verbose("sending broadcast query for external timers")
                context.sendBroadcast(Intent(TIMER_QUERY_INTENT))
            }
        }
    }
}
