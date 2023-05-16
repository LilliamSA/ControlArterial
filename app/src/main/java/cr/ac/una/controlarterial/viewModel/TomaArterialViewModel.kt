package cr.ac.una.controlarterial.viewModel


import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import cr.ac.una.controlarterial.AuthInterceptor
import cr.ac.una.controlarterial.Entity.TomaArterial
import cr.ac.una.controlarterial.dao.TomaArterialDAO
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
            .addInterceptor(AuthInterceptor("jKy950eLKXPiJrNLBXFj59IOlPbqkq4VTmFLAWGLzl_ItOgWyw"))
            .build()
        val retrofit = Retrofit.Builder()
            .baseUrl("https://crudapi.co.uk/api/v1/")
            .client(client)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
        apiService = retrofit.create(TomaArterialDAO::class.java)
    }
}