package istea.edu.heladeria

import android.app.AlertDialog
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.*
import istea.edu.heladeria.dao.DBHelper
import istea.edu.heladeria.dto.Pedido
import java.util.*

class MainActivity : AppCompatActivity() {

    lateinit var producto: RadioGroup
    lateinit var opcion: RadioButton
    lateinit var repartidores: RadioGroup
    lateinit var repartidorEleg:RadioButton
    lateinit var sabor1sp: Spinner
    lateinit var sabor2sp: Spinner
    lateinit var sabor3sp: Spinner
    lateinit var sabor4sp: Spinner
    lateinit var saborOpsp: Spinner
    lateinit var siguientebt: Button
    val listaSabores = arrayOf("Banana Split", "Frutilla", "Durazno", "Limon", "Melon")
    val listaSaboresOpcionales = arrayOf("Chocolate Fundido", "Almendra", "Vainilla")


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        inicilizarElementos()
        val caja: Int = asignaCaja()
        siguientebt.setOnClickListener(View.OnClickListener {
            opcion = findViewById(producto.checkedRadioButtonId)
            repartidorEleg=findViewById(repartidores.checkedRadioButtonId)
                val opcionSeleccionada: String = opcion.text.toString()
                val repartidorSelec: String = repartidorEleg.text.toString()
                if (opcionSeleccionada == "Cono") {
                    val cono: Pedido = Pedido(
                        opcionSeleccionada,
                        sabor1sp.selectedItem.toString(),
                        sabor2sp.selectedItem.toString(),
                        "nulo",
                        "nulo",
                        "nulo",
                        caja,
                        repartidorSelec,
                        Calendar.getInstance().time.toString()
                    )
                    alert(cono)
                } else if (opcionSeleccionada == "1/4 de Kilo") {
                    val cuarto: Pedido = Pedido(
                        opcionSeleccionada,
                        sabor1sp.selectedItem.toString(),
                        sabor2sp.selectedItem.toString(),
                        sabor3sp.selectedItem.toString(),
                        "nulo",
                        saborOpsp.selectedItem.toString(),
                        caja,
                        repartidorSelec,
                        Calendar.getInstance().time.toString()
                    )
                    alert(cuarto)

                } else if (opcionSeleccionada == "1 Kilo") {
                    val kilo: Pedido = Pedido(
                        opcionSeleccionada,
                        sabor1sp.selectedItem.toString(),
                        sabor2sp.selectedItem.toString(),
                        sabor3sp.selectedItem.toString(),
                        sabor4sp.selectedItem.toString(),
                        saborOpsp.selectedItem.toString(),
                        caja,
                        repartidorSelec,
                        Calendar.getInstance().time.toString()
                    )
                    alert(kilo)
                }
        })

    }

    private fun inicilizarElementos() {
        producto = findViewById(R.id.producto)
        repartidores=findViewById(R.id.repartidores)
        sabor1sp = findViewById(R.id.sabor1sp)
        sabor2sp = findViewById(R.id.sabor2sp)
        sabor3sp = findViewById(R.id.sabor3sp)
        sabor4sp = findViewById(R.id.sabor4sp)
        saborOpsp = findViewById(R.id.saborOpsp)
        siguientebt = findViewById(R.id.siguientebt)
        creoSpinnerLayout()
    }

    private fun creoSpinnerLayout() {
        val adapter = ArrayAdapter(this, android.R.layout.simple_spinner_item, listaSabores)
        adapter.setDropDownViewResource(android.R.layout.simple_dropdown_item_1line)
        sabor1sp.adapter = adapter
        sabor2sp.adapter = adapter
        sabor3sp.adapter = adapter
        sabor4sp.adapter = adapter
        val adapterOp =
            ArrayAdapter(this, android.R.layout.simple_spinner_item, listaSaboresOpcionales)
        adapterOp.setDropDownViewResource(android.R.layout.simple_dropdown_item_1line)
        saborOpsp.adapter = adapterOp
    }

    private fun asignaCaja(): Int {
        var caja: Int = 1
        val dbHelper = DBHelper(this, null)
        val cajaHabilitada = dbHelper.cajaHabilitada()
        val listadoPedidos = dbHelper.obtenerPedidos()
        if (listadoPedidos.isNotEmpty()) {
            if (listadoPedidos.last().caja == 3 && cajaHabilitada == 1) {
                caja = 1
                return caja
            } else if (listadoPedidos.last().caja == 1 && cajaHabilitada < 2) {
                caja = 2
                return caja
            } else {
                caja = 3
                return caja
            }
        } else {
            return caja
        }
    }

    private fun alert(helado: Pedido){
        val dbHelper = DBHelper(this, null)
        val builder = AlertDialog.Builder(this)
        builder.setTitle("Por favor confirme los datos del " + helado.producto)
        builder.setIcon(android.R.drawable.ic_dialog_alert)
        if (helado.producto == "Cono") {
            builder.setMessage("Los sabores elegidos son " + helado.sabor1 + " y " + helado.sabor2 + " y el costo es de $250."+ "El pedido será entregado por: "+ helado.repartidor)
        } else if (helado.producto == "1/4 de Kilo") {
            builder.setMessage("Los sabores elegidos son " + helado.sabor1 + " , " + helado.sabor2 + " , " + helado.sabor3 + " y el sabor opcional " + helado.saborOp + ".El costo es de $450."+ "El pedido será entregado por: "+ helado.repartidor)
        } else if (helado.producto == "1 Kilo") {
            builder.setMessage("Los sabores elegidos son " + helado.sabor1 + " , " + helado.sabor2 + " , " + helado.sabor3 + " , " + helado.sabor4 + " y el sabor opcional " + helado.saborOp + ".El costo es de $900."+ "El pedido será entregado por: "+ helado.repartidor)
        }
        builder.setPositiveButton("OK") {
                dialogInterface, i ->
            Toast.makeText(
                this,
                helado.producto + " comprado",
                Toast.LENGTH_LONG
            ).show()
            dbHelper.guardarPedido(helado)
            siguiente()
        }
        builder.setNegativeButton("NO") { dialogInterface, i ->
            Toast.makeText(
                this,
                "Por favor corrija su elección",
                Toast.LENGTH_LONG
            ).show()

        }
        builder.setNeutralButton("CANCELAR") { dialogInterface, i ->
            Toast.makeText(
                this,
                "Operacion cancelada",
                Toast.LENGTH_LONG
            ).show()
        }
        val alerta: AlertDialog = builder.create()
        alerta.setCancelable(true)
        alerta.show()
    }

    private fun siguiente() {
    val intent = Intent(this, Historial::class.java)
    startActivity(intent)
}
}