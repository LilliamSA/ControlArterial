package cr.ac.una.controlarterial.dao

import cr.ac.una.controlarterial.Entity.TomaArterial
import cr.ac.una.controlarterial.Entity.TomasArteriales
import retrofit2.http.*

interface TomaArterialDAO {

        @GET("prueba")
        suspend fun getItems(): TomasArteriales

        @GET("postman/{uuid}")
        suspend fun getItem(@Path("uuid") uuid: String): TomaArterial

        @POST("prueba")
        suspend fun createItem( @Body items: List<TomaArterial>): TomasArteriales

        @PUT("postman/{uuid}")
        suspend fun updateItem(@Path("uuid") uuid: String, @Body item: TomaArterial): TomasArteriales

        @DELETE("postman/{uuid}")
        suspend fun deleteItem(@Path("uuid") uuid: String)
}
