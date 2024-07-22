package com.example.composesample.ui.common

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.runtime.Composable

@Composable
fun ShowError(isError: Boolean, onClick: () -> Unit) {
  AnimatedVisibility(visible = isError) {
    NetworkErrorView(onClick)
  }
}
