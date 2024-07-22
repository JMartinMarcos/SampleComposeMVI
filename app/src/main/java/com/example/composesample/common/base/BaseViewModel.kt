package com.example.composesample.common.base

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

interface ViewEvent

interface ViewState {
  val isLoading: Boolean
  val isError: Boolean
}

interface ViewSideEffect

abstract class BaseViewModel<UiState : ViewState, Event : ViewEvent, Effect : ViewSideEffect>(
  initialState: UiState
) : ViewModel() {

  abstract fun handleEvents(event: Event)

  private val _viewState: MutableStateFlow<UiState> = MutableStateFlow(initialState)
  val viewState: StateFlow<UiState> = _viewState

  private val _event: MutableSharedFlow<Event> = MutableSharedFlow()

  private val _effect: Channel<Effect> = Channel()
  val effect = _effect.receiveAsFlow()

  init {
    subscribeToEvents()
  }

  private fun subscribeToEvents() {
    execute {
      _event.collect {
        handleEvents(it)
      }
    }
  }

  fun setEvent(event: Event) {
    execute { _event.emit(event) }
  }

  protected fun setState(reducer: UiState.() -> UiState) {
    _viewState.update { viewState.value.reducer() }
  }

  protected fun setEffect(builder: () -> Effect) {
    val effectValue = builder()
    execute { _effect.send(effectValue) }
  }

  protected fun execute(suspendFunction: suspend () -> Unit) {
    viewModelScope.launch {
      suspendFunction()
    }
  }
}