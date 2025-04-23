package org.example.models

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import java.util.*

@Serializable
@SerialName("Owner")
data class Owner(
    val id: Int,
    val name: String,
    val phone: String,
    val email: String,
)