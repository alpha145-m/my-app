package com.example.myapp.data.models

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class CarModel(
    @SerialName("id")
    val id: Int? = null,
    @SerialName("seller_id")
    val sellerId: String = "",
    @SerialName("name")
    val name: String = "",
    @SerialName("price")
    val price: String = "",
    @SerialName("year")
    val year: String = "",
    @SerialName("engine")
    val engine: String = "",
    @SerialName("description")
    val description: String = "",
    @SerialName("location")
    val location: String = "",
    @SerialName("image_url")
    val imageUrl: String = "",
    @SerialName("created_at")
    val createdAt: String? = null
)
