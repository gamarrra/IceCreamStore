package istea.edu.heladeria.Adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import istea.edu.heladeria.R
import istea.edu.heladeria.dto.Pedido

class HistorialAdapter(private val dataSet: ArrayList<Pedido>) : RecyclerView.Adapter<HistorialAdapter.ViewHolder>()
{


    class ViewHolder(view: View) : RecyclerView.ViewHolder(view){
        val pProducto: TextView
        val pSabor1: TextView
        val pSabor2: TextView
        val pSabor3: TextView
        val pSabor4: TextView
        val pSaborOp: TextView
        val pRepartidor: TextView
        val pCaja: TextView
        val pDia: TextView


        init {

            pProducto = view.findViewById(R.id.p_producto)
            pSabor1=view.findViewById(R.id.p_sabor1)
            pCaja=view.findViewById(R.id.p_caja)
            pSabor2=view.findViewById(R.id.p_sabor2)
            pSabor3=view.findViewById(R.id.p_sabor3)
            pSabor4=view.findViewById(R.id.p_sabor4)
            pSaborOp=view.findViewById(R.id.p_saborOp)
            pRepartidor=view.findViewById(R.id.p_repartidor)
            pDia=view.findViewById(R.id.p_dia)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {

        val view = LayoutInflater.from(parent.context).inflate(R.layout.pedidolayout,parent,false)

        return ViewHolder(view)
    }

    override fun getItemCount(): Int {

        return dataSet.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.pProducto.text = "Producto: " + dataSet[position].producto
        holder.pCaja.text = "Caja: " +dataSet[position].caja.toString()
        holder.pSabor1.text = "Sabor 1: " + dataSet[position].sabor1
        holder.pSabor2.text = "Sabor 2: " + dataSet[position].sabor2
        holder.pSabor3.text = "Sabor 3: " + dataSet[position].sabor3
        holder.pSabor4.text = "Sabor 4: " + dataSet[position].sabor4
        holder.pSaborOp.text = "Sabor Opcional: " + dataSet[position].saborOp
        holder.pRepartidor.text = "Repartidor: " + dataSet[position].repartidor
        holder.pDia.text="Fecha: "+ dataSet[position].dia
    }
}