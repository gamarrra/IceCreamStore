package istea.edu.heladeria.dao

import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import istea.edu.heladeria.dto.Pedido

class DBHelper(context: Context, factory: SQLiteDatabase.CursorFactory?):
    SQLiteOpenHelper(context, DATABASE_NAME, factory, DATABASE_VERSION) {


    companion object{
        private val DATABASE_NAME = "pedidos.db"
        private val DATABASE_VERSION=10
        val TABLA_PEDIDOS ="pedido"
        val COLUMN_PRODUCTO="producto"
        val COLUMN_SABOR1="sabor1"
        val COLUMN_SABOR2="sabor2"
        val COLUMN_SABOR3="sabor3"
        val COLUMN_SABOR4="sabor4"
        val COLUMN_SABOROP="saborOp"
        val COLUMN_ID ="id"
        val COLUMN_CAJA ="caja"
        val COLUMN_REPARTIDOR="repartidor"
        val COLUMN_DIA="dia"
        var ID:Int=0
    }

    override fun onCreate(db: SQLiteDatabase?) {
        val createTable = ("CREATE TABLE "+ TABLA_PEDIDOS + " ( " +
                COLUMN_ID +" INTEGER PRIMARY KEY, " +
                COLUMN_PRODUCTO + " TEXT," +
                COLUMN_SABOR1 +" TEXT, " +
                COLUMN_SABOR2 +" TEXT, " +
                COLUMN_SABOR3 +" TEXT, " +
                COLUMN_SABOR4 +" TEXT, " +
                COLUMN_SABOROP +" TEXT, " +
                COLUMN_REPARTIDOR +" TEXT, " +
                COLUMN_CAJA +" INTEGER, " +
                COLUMN_DIA +" TEXT " +
                ")")
        db?.execSQL(createTable)

    }

    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
        db?.execSQL("DROP TABLE IF EXISTS $TABLA_PEDIDOS")
        onCreate(db)
    }


    fun guardarPedido(pedido: Pedido){

        val db = this.writableDatabase
        val values = ContentValues()

        values.put(COLUMN_PRODUCTO, pedido.producto)
        values.put(COLUMN_SABOR1, pedido.sabor1)
        values.put(COLUMN_SABOR2, pedido.sabor2)
        values.put(COLUMN_SABOR3, pedido.sabor3)
        values.put(COLUMN_SABOR4, pedido.sabor4)
        values.put(COLUMN_SABOROP, pedido.saborOp)
        values.put(COLUMN_CAJA, pedido.caja)
        values.put(COLUMN_REPARTIDOR, pedido.repartidor)
        values.put(COLUMN_DIA, pedido.dia)
        db.insert(TABLA_PEDIDOS, null, values)

    }


    fun obtenerPedidos():ArrayList<Pedido>{

        val listaPedidos: ArrayList<Pedido> = ArrayList<Pedido>();

        val query = "SELECT * FROM "+ TABLA_PEDIDOS

        val db= this.readableDatabase

        val cursor = db.rawQuery(query, null)

        if(cursor.moveToFirst()){

            do{
                ID =  cursor.getInt(cursor.getColumnIndex(COLUMN_ID))
                val repartidor = cursor.getString(cursor.getColumnIndex(COLUMN_REPARTIDOR))
                val caja = cursor.getInt(cursor.getColumnIndex(COLUMN_CAJA))
                val sabor1 = cursor.getString(cursor.getColumnIndex(COLUMN_SABOR1))
                val sabor2 = cursor.getString(cursor.getColumnIndex(COLUMN_SABOR2))
                val sabor3 = cursor.getString(cursor.getColumnIndex(COLUMN_SABOR3))
                val sabor4 = cursor.getString(cursor.getColumnIndex(COLUMN_SABOR4))
                val saborOp = cursor.getString(cursor.getColumnIndex(COLUMN_SABOROP))
                val producto = cursor.getString(cursor.getColumnIndex(COLUMN_PRODUCTO))
                val dia=cursor.getString(cursor.getColumnIndex(COLUMN_DIA))
                listaPedidos.add(
                    Pedido(
                        producto,
                        sabor1,
                        sabor2,
                        sabor3,
                        sabor4,
                        saborOp,
                        caja,
                        repartidor,
                        dia
                    )
                )
            }while(cursor.moveToNext())
        }

        return listaPedidos
    }



    fun cajaHabilitada():Int
    {
        var contadorCj1=0
        var contadorCj2=0
        var contadorCj3=0
        var resultado=0

        val listaPedidos: ArrayList<Pedido> = this.obtenerPedidos()
        listaPedidos.forEach { item ->
            if(item.caja==1)
            {
                contadorCj1=contadorCj1+1
            }
            else if(item.caja==2){
                contadorCj2=contadorCj2+1
            }
            else if(item.caja==3){
                contadorCj3=contadorCj3+1
            }
        }
        if(contadorCj1<=5)
        {
            resultado=resultado+1
            return resultado
        }
        else if(contadorCj2<=10){
            resultado=resultado+2
            return resultado
        }
        else {
            resultado=resultado+3
            return resultado
        }

    }

    fun repartidorDelMes():String{
        var contadorAriel=0
        var contadorJose=0
        var contadorPablo=0
        var contadorRaul=0
        val listaPedidos: ArrayList<Pedido> = this.obtenerPedidos()
        listaPedidos.forEach { item ->
            if(item.repartidor=="Ariel")
            {
                contadorAriel=contadorAriel+1
            }
            else if(item.repartidor=="Jose")
            {
                contadorJose=contadorJose+1
            }
            else if(item.repartidor=="Pablo")
            {
                contadorPablo=contadorPablo+1
            }
            else if(item.repartidor=="Raul")
            {
                contadorRaul=contadorRaul+1
            }
        }
        if(contadorAriel>contadorJose&&contadorAriel>contadorPablo&&contadorAriel>contadorRaul){
            val resultado="Ariel"
            return resultado
        }
        else if(contadorJose>contadorAriel&&contadorJose>contadorPablo&&contadorJose>contadorRaul){
            val resultado="Jose"
            return resultado
        }
        else if(contadorRaul>contadorAriel&&contadorRaul>contadorPablo&&contadorRaul>contadorJose){
            val resultado="Raul"
            return resultado
        }
        else{
            val resultado="Pablo"
            return resultado
        }
    }
}


