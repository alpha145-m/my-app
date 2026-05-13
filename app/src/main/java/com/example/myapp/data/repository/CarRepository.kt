package com.example.myapp.data.repository

import com.example.myapp.data.models.CarModel
import io.github.jan.supabase.postgrest.postgrest
import io.github.jan.supabase.storage.storage
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

object CarRepository {
    private val supabase = AuthRepository.supabase

    suspend fun getAllCars(): List<CarModel> {
        return withContext(Dispatchers.IO) {
            supabase.postgrest["cars"].select().decodeList<CarModel>()
        }
    }

    suspend fun getCarsBySeller(sellerId: String): List<CarModel> {
        return withContext(Dispatchers.IO) {
            supabase.postgrest["cars"].select {
                filter {
                    eq("seller_id", sellerId)
                }
            }.decodeList<CarModel>()
        }
    }

    suspend fun uploadCarImage(byteArray: ByteArray, fileName: String): String {
        return withContext(Dispatchers.IO) {
            val bucket = supabase.storage["car_images"]
            val path = "uploads/$fileName"
            bucket.upload(path, byteArray) {
                upsert = true
            }
            bucket.publicUrl(path)
        }
    }

    suspend fun addCar(car: CarModel) {
        withContext(Dispatchers.IO) {
            supabase.postgrest["cars"].insert(car)
        }
    }
}
