package com.dicoding.asclepius.view

import android.net.Uri
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.dicoding.asclepius.data.local.Repository
import com.dicoding.asclepius.data.local.entity.ClasificationEntity
import kotlinx.coroutines.launch

class ClasificationsViewModel(private val repository: Repository): ViewModel() {
    private val _currentImageUri = MutableLiveData<Uri>()
    var currentImageUri: LiveData<Uri> = _currentImageUri

    private val _listClasification = MutableLiveData<List<ClasificationEntity>>()
    var listClasification: LiveData<List<ClasificationEntity>> = _listClasification

    fun updateCurrectImageUri(uri: Uri) {
        _currentImageUri.value = uri
    }

//    fun getAllClasification() = repository.getAllClasification()

    suspend fun getAllClasification(): LiveData<List<ClasificationEntity>> {
        return repository.getAllClasification()
    }

    fun insertClassification(entity: ClasificationEntity) = repository.insertClassification(entity)


    fun deleteClassification(clasificationId: Int) = repository.deleteClassification(clasificationId)

    fun deleteAllClassification() = repository.deleteAllClassification()

}