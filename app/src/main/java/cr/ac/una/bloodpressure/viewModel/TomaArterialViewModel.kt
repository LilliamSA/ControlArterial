package cr.ac.una.bloodpressure.viewModel


import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import cr.ac.una.bloodpressure.AuthInterceptor
import cr.ac.una.bloodpressure.Entity.TomaArterial
import cr.ac.una.bloodpressure.dao.TomaArterialDAO
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class TomaArterialViewModel: ViewModel (){
    private var _tomasArteriales: MutableLiveData<List<TomaArterial>> = MutableLiveData()
    var tomasArteriales: LiveData<List<TomaArterial>> = _tomasArteriales
    lateinit var  apiService : TomaArterialDAO


    suspend fun listTomaArterial() {
        intService()
        var lista = apiService.getItems()
        _tomasArteriales.postValue(lista.items)

    }

    suspend fun insertTomaArterial(tomaArterial: TomaArterial) {
        intService()
        var lista = apiService.createItem(listOf(tomaArterial))
        _tomasArteriales.postValue(lista.items)
    }
    fun intService(){
        val client = OkHttpClient.Builder()
            .addInterceptor(AuthInterceptor("24AtEiHD6enBaq1Yqit_i_wF71TY71eUjtHZ_CVaztYPuco9nQ"))
            .build()
        val retrofit = Retrofit.Builder()
            .baseUrl("https://crudapi.co.uk/api/v1/")
            .client(client)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
        apiService = retrofit.create(TomaArterialDAO::class.java)
    }

    //buscar toma por id
    suspend fun getItem(uuid: String) {
        intService()
        var toma = apiService.getItem(uuid)
        _tomasArteriales.postValue(listOf(toma))
    }

    //actualizar toma
    suspend fun updateItem(uuid: String, tomaArterial: TomaArterial) {
        intService()
        var lista = apiService.updateItem(uuid, tomaArterial)
        _tomasArteriales.postValue(lista.items)
        listTomaArterial()
    }

    //eliminar toma
    suspend fun deleteItem(uuid: String) {
        intService()
        apiService.deleteItem(uuid)
        listTomaArterial()
    }
}