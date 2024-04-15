package com.helloz.app.utils

import android.util.Log

class LogUtils {

    companion object {
        val tagPrefix: String = "Hello_Z_"
        val isDebug: Boolean = true

        @JvmStatic
        fun v(tag: String, msg: String?) {
            if (isDebug) {
                Log.v(tagPrefix + tag, msg!!)
            }
        }

        @JvmStatic
        fun v(tag: String, msg: String?, tr: Throwable?) {
            if (isDebug) {
                Log.v(tagPrefix + tag, msg, tr)
            }
        }

        @JvmStatic
        fun d(tag: String, msg: String?) {
            if (isDebug) {
                Log.d(tagPrefix + tag, msg!!)
            }
        }

        @JvmStatic
        fun d(tag: String, msg: String?, tr: Throwable?) {
            if (isDebug) {
                Log.d(tagPrefix + tag, msg, tr)
            }
        }

        @JvmStatic
        fun i(tag: String, msg: String?) {
            if (isDebug) {
                Log.i(tagPrefix + tag, msg!!)
            }
        }

        @JvmStatic
        fun i(tag: String, msg: String?, tr: Throwable?) {
            if (isDebug) {
                Log.i(tagPrefix + tag, msg, tr)
            }
        }

        @JvmStatic
        fun w(tag: String, msg: String?) {
            if (isDebug) {
                Log.w(tagPrefix + tag, msg!!)
            }
        }

        @JvmStatic
        fun w(tag: String, msg: String?, tr: Throwable?) {
            if (isDebug) {
                Log.w(tagPrefix + tag, msg, tr)
            }
        }

        @JvmStatic
        fun e(tag: String, msg: String?) {
            if (isDebug) {
                Log.e(tagPrefix + tag, msg!!)
            }
        }

        @JvmStatic
        fun e(tag: String, msg: String?, tr: Throwable?) {
            if (isDebug) {
                Log.e(tagPrefix + tag, msg, tr)
            }
        }
    }
}