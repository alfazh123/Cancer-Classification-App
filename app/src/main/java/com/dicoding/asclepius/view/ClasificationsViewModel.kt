package com.dicoding.asclepius.view

import android.net.Uri
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.dicoding.asclepius.BuildConfig
import com.dicoding.asclepius.data.local.Repository
import com.dicoding.asclepius.data.local.entity.ClasificationEntity
import com.dicoding.asclepius.data.remote.response.ArticlesItem
import com.dicoding.asclepius.data.remote.response.NewsResponse
import com.dicoding.asclepius.data.remote.retrofit.ApiConfig
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

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

    private var _news = MutableLiveData<List<ArticlesItem>>()
    val news: LiveData<List<ArticlesItem>> = _news

    fun getNews() {
        val client = ApiConfig.getApiService().getNews(BuildConfig.API_KEY)
        client.enqueue(object : Callback<NewsResponse> {
            override fun onResponse(call: Call<NewsResponse>, response: Response<NewsResponse>) {
                if (response.isSuccessful) {
                    if (response.body() != null) {
                        _news.value = response.body()?.articles
                    } else {
                        _news.value = null
                    }
                }
            }

            override fun onFailure(call: Call<NewsResponse>, t: Throwable) {
                Log.e("ClasificationsViewModel", "onFailure: ${t.message}")
            }

        })
    }

}