package istea.edu.heladeria

import android.annotation.SuppressLint
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.LinearLayout
import android.widget.TextView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import istea.edu.heladeria.Adapter.HistorialAdapter
import istea.edu.heladeria.dao.DBHelper


class Historial : AppCompatActivity() {

    lateinit var rvHelado: RecyclerView
    lateinit var volverBtn: Button
    lateinit var repartidor: TextView
    @SuppressLint("WrongConstant")

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_recycler)
        inicilizarElementos()
        val dbHelper= DBHelper(this,null)
        repartidor.setText(dbHelper.repartidorDelMes())

        rvHelado=findViewById(R.id.recycleview_helado)

        rvHelado.layoutManager = LinearLayoutManager(this, LinearLayout.VERTICAL,false)

        rvHelado.adapter= HistorialAdapter(dbHelper.obtenerPedidos())

        volverBtn.setOnClickListener(View.OnClickListener {
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
        })
    }
    private fun inicilizarElementos(){
        volverBtn= findViewById(R.id.volverBoton)
        repartidor=findViewById(R.id.elMejorRep)


    }
}
