package com.dicoding.asclepius.view

import android.net.Uri
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class RootViewModel: ViewModel() {
    private val _currentImageUri = MutableLiveData<Uri>()
    var currentImageUri: LiveData<Uri> = _currentImageUri

    fun updateCurrectImageUri(uri: Uri) {
        _currentImageUri.value = uri
    }

}