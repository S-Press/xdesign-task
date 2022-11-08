package com.xdesign.takehome.ui.home

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.xdesign.takehome.api.wrappers.ResourceFactory
import com.xdesign.takehome.api.wrappers.Status
import com.xdesign.takehome.data.models.ApiCharacter
import com.xdesign.takehome.data.repositories.CharacterRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val characterRepository: CharacterRepository
) : ViewModel() {

    private val _errorLiveData = MutableLiveData<String?>()
    val errorLiveData: LiveData<String?>
        get() = _errorLiveData

    private val _characterListLiveData = MutableLiveData<List<ApiCharacter>>()
    val characterListLiveData: LiveData<List<ApiCharacter>>
        get() = _characterListLiveData

    private val characterListResourceFactory = ResourceFactory<List<ApiCharacter>>()

    fun getCharacters() {
        viewModelScope.launch(Dispatchers.Default) {

            val characterListResponse = characterRepository.getCharacters()

            characterListResponse.let { response ->
                characterListResourceFactory.getResource(response).let { resource ->
                    when (resource.status) {
                        Status.SUCCESS -> {
                            resource.data?.apply {
                                _characterListLiveData.postValue(this)
                            }
                        }
                        Status.ERROR -> {
                            resource.message?.let {
                                _errorLiveData.postValue(it)
                            }
                        }
                        Status.LOADING -> { }
                    }
                }
            }
        }
    }
}