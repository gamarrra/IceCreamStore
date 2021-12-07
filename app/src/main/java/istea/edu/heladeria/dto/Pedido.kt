package istea.edu.heladeria.dto

import java.io.Serializable
import java.util.*

data class Pedido(
    val producto: String,
    val sabor1: String,
    val sabor2: String,
    val sabor3: String,
    val sabor4: String,
    val saborOp: String,
    val caja: Int,
    val repartidor: String,
    val dia: String
    ):Serializable