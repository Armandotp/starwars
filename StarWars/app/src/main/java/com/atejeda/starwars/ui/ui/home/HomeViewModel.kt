package com.atejeda.starwars.ui.ui.home

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.atejeda.starwars.core.ResultType
import com.atejeda.starwars.data.model.Element
import com.atejeda.starwars.domain.HomeUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val useCase: HomeUseCase
): ViewModel() {

    val allElements:MutableLiveData<List<Element>> = MutableLiveData()
    var isApiProgress: MutableLiveData<Boolean> = MutableLiveData()
    var error: MutableLiveData<String> = MutableLiveData()


    fun getAll(){
        isLoadingLiveData(true)
        viewModelScope.launch {
            var response = useCase.getAll()
            if(response.resultType == ResultType.SUCCESS){
                allElements.value = response.data!!
                isLoadingLiveData(false)
            }else{
                error.value = response.error!!
                isLoadingLiveData(false)
            }
        }
    }

    private fun isLoadingLiveData(isLoading:Boolean){
        this.isApiProgress.value = isLoading
    }
}