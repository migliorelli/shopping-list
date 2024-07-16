package migliorelli.shoppinglist.viewmodels

import RetrofitClient
import android.util.Log
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import migliorelli.shoppinglist.models.GeocodingResult
import migliorelli.shoppinglist.models.LocationData

class LocationViewModel : ViewModel() {
    private val _location = mutableStateOf<LocationData?>(null)
    val location: State<LocationData?> = _location

    private val _address = mutableStateOf(listOf<GeocodingResult>())
    val address: State<List<GeocodingResult>> = _address

    fun updateLocation(newLocation: LocationData) {
        _location.value = newLocation
    }

    fun setAddress(str: String?) {
        _address.value =
            listOf(GeocodingResult(formatted_address = str ?: "No Address"))
    }

    fun fetchAddress(latlng: String) {
        try {
            viewModelScope.launch {
                val result = RetrofitClient.create().getAddressFromCoordinates(
                    latlng = latlng, apiKey = "AIzaSyBTSr2jEY0Evnutew2w02uqJtjIif9eFu8"
                )

                _address.value = result.results
                Log.d("SUCCESS", "$latlng - ${result.results[0].formatted_address}")
            }

        } catch (e: Exception) {
            Log.d("res1", "${e.cause} ${e.message}")
        }
    }

}