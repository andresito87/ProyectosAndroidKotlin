package com.example.reply.ui.utils

import android.annotation.SuppressLint
import android.app.Activity
import android.content.Context
import android.content.ContextWrapper
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.remember
import androidx.compose.ui.platform.LocalContext
import androidx.window.layout.FoldingFeature
import androidx.window.layout.WindowInfoTracker
import androidx.window.layout.WindowLayoutInfo

private tailrec fun Context.findActivity(): Activity =
    when (this) {
        is Activity -> this
        is ContextWrapper -> baseContext.findActivity()
        else -> error("Activity not found")
    }

/** Devuelve true cuando el dispositivo es foldable y está DESPLEGADO (separando áreas o “book”). */
@SuppressLint("RestrictedApi")
@Composable
fun rememberIsUnfoldedFoldable(): Boolean {
    val context = LocalContext.current
    val activity = context.findActivity()
    val tracker = remember { WindowInfoTracker.getOrCreate(context) }
    val info: WindowLayoutInfo = tracker.windowLayoutInfo(activity)
        .collectAsState(initial = WindowLayoutInfo(emptyList())).value

    val folds = info.displayFeatures.filterIsInstance<FoldingFeature>()
    if (folds.isEmpty()) return false
    return folds.any { it.isSeparating || it.state == FoldingFeature.State.HALF_OPENED }
}

/** Devuelve true si existe un pliegue (foldable), sin importar postura. */
@SuppressLint("RestrictedApi")
@Composable
fun rememberIsFoldableDevice(): Boolean {
    val context = LocalContext.current
    val activity = context.findActivity()
    val tracker = remember { WindowInfoTracker.getOrCreate(context) }
    val info: WindowLayoutInfo = tracker.windowLayoutInfo(activity)
        .collectAsState(initial = WindowLayoutInfo(emptyList())).value

    return info.displayFeatures.any { it is FoldingFeature }
}