/*
 * MIT License
 *
 * Copyright (c) 2020 Giorgos Neokleous
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all
 * copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 */

package com.giorgosneokleous.fullscreenintentexample

import android.app.AlarmManager
import android.app.PendingIntent
import android.content.Context
import android.os.Build
import kotlin.time.Duration.Companion.seconds

fun Context.scheduleNotification(isLockScreen: Boolean) {
    val alarmManager = getSystemService(Context.ALARM_SERVICE) as AlarmManager
    val timeInMillis = System.currentTimeMillis() + 5.seconds.inWholeMilliseconds

    with(alarmManager) {
        if (if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) canScheduleExactAlarms() else true) {
            println("coucou alarm setup")
            setExact(AlarmManager.RTC_WAKEUP, timeInMillis, getReceiver(isLockScreen))
        }
    }
}

private fun Context.getReceiver(isLockScreen: Boolean): PendingIntent {
    // for demo purposes no request code and no flags
    println("coucou recevied")
    return PendingIntent.getBroadcast(
        this,
        0,
        NotificationReceiver.build(this, isLockScreen),
        PendingIntent.FLAG_IMMUTABLE
    )
}
