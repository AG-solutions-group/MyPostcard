package app.presentation

import android.content.Context
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.domain.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import kotlinx.coroutines.launch
import org.koin.androidx.scope.LifecycleViewModelScopeDelegate


class ArchiveVM (
    private val getOverviewData: LoadOverviewDomain,
    private val getDetailsData: LoadDetailsDomain,
        ) : ViewModel(){

    private val _orderListOverview = MutableLiveData<List<Order>> ()
    val orderListOverview: LiveData<List<Order>>
        get() = _orderListOverview

    private val _orderListDetails = MutableLiveData<Recipient> ()
    val orderListDetails: LiveData<Recipient>
        get() = _orderListDetails

    private val _orderListDetailsFromOverview = MutableLiveData<Order> ()
    val orderListDetailsFromOverview: LiveData<Order>
        get() = _orderListDetailsFromOverview

    private val _failure = MutableLiveData<String> ()
    val failure: LiveData<String>
        get() = _failure

    init {
        _failure.value = ""
        _orderListDetails.value  = Recipient("", "", "", "")
        _orderListDetailsFromOverview.value = Order(0, "", 0, "", "", 0, "", 0, "")
    }


    fun loadOverviewDataUseCase(){
        // load overview data via coroutine
        viewModelScope.launch {
            val data = getOverviewData.execute()
            // handle data we get back
            data.either(::handleFailure, ::handleOverviewList)
        }
    }

    fun loadDetailsDataUseCase(id: Int, position: Int) {
        // load details data via coroutine
        viewModelScope.launch {
            val data = getDetailsData.execute(id)
            // handle data we get back
            data.either(::handleFailure, ::handleDetailsList)
            handleDetailsFromOverview(position)
        }
    }

    fun resetFailure() {
        _failure.postValue("")
    }

    //-----------------------------------------------------------------------------
    //-----------------------------------------------------------------------------
    //-----------------------------------------------------------------------------

    private fun handleOverviewList(orders: List<Order>) {
        // update ViewModel data
        _orderListOverview.postValue(orders)
    }

    private fun handleDetailsFromOverview(position: Int) {
        // update ViewModel data
        _orderListDetailsFromOverview.postValue(orderListOverview.value!![position])
    }

    private fun handleDetailsList(recipient: List<Recipient>) {
        // update ViewModel data
        _orderListDetails.postValue(recipient[0])
    }

    private fun handleFailure(failure: Failure) {
        when (failure){
            is Failure.NetworkConnection -> _failure.postValue("no network connection")
            is Failure.ServerError -> _failure.postValue("server error")
            else -> _failure.postValue("unknown error")
        }
    }

}