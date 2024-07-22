package com.example.composesample.ui.common

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.flowWithLifecycle
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.mapNotNull

/**
 * Remembers the result of [flowWithLifecycle]. Updates the value if the [flow]
 * or [lifecycleOwner] changes. Cancels collection in onStop() and start it in onStart()
 *
 * @param flow The [Flow] that is going to be collected.
 * @param lifecycleOwner The [LifecycleOwner] to validate the [Lifecycle.State] from
 *
 * @return [Flow] with the remembered value of type [T]
 */
@Composable
fun <T> rememberFlowWithLifecycle(
  flow: Flow<T>,
  lifecycleOwner: LifecycleOwner
): Flow<T> {
  return remember(flow, lifecycleOwner) {
    flow.flowWithLifecycle(
      lifecycleOwner.lifecycle,
      Lifecycle.State.STARTED
    )
  }
}

/**
 * Adds a LaunchedEffect to the composition, that will relaunch only if the flow changes.
 * This function uses flowWithLifecycle() to collect the flows in a lifecycle-aware manner.
 *
 * @param flow The [Flow] that is going to be collected.
 * @param onEffectConsumed Callback to mark the event as consumed
 * @param function The block that will get executed on flow collection
 */
@Composable
fun <T> LaunchedEffectAndCollect(
  flow: Flow<T?>,
  function: suspend (value: T) -> Unit
) {
  val effectFlow =
    rememberFlowWithLifecycle(flow, LocalLifecycleOwner.current)

  LaunchedEffect(effectFlow) {
    effectFlow.mapNotNull { it }.collect(function)
  }
}