package com.ssjit.papertrading.other

import androidx.appcompat.widget.SearchView
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.coroutineScope
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

internal class DebounceQueryTextListener(
    lifecycle:Lifecycle,
    private val onDebounceQueryTextChange: (String?) -> Unit
): SearchView.OnQueryTextListener {
    var period= 500L
    private val coroutineScope = lifecycle.coroutineScope
    private var searchJob: Job? = null

    override fun onQueryTextSubmit(query: String?): Boolean {
        return false
    }

    override fun onQueryTextChange(newText: String?): Boolean {
        searchJob?.cancel()
        searchJob = coroutineScope.launch {
            newText?.let {
                delay(period)
                onDebounceQueryTextChange(newText)
            }
        }
        return false
    }
}