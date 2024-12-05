package br.edu.utfpr.usandosqlite.adapter

import android.content.Context
import android.database.Cursor
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.TextView
import br.edu.utfpr.usandosqlite.R
import br.edu.utfpr.usandosqlite.entity.Cadastro

class ElementoListaAdapter (val context: Context, val cursor : Cursor ) : BaseAdapter() {

    override fun getCount(): Int {
        return cursor.count
    }

    override fun getItem(id: Int): Any {
        cursor.moveToPosition( id )
        val cadastro = Cadastro(
            cursor.getInt( 0 ),
            cursor.getString( 1 ),
            cursor.getString( 2 )
        )
        return cadastro
    }

    override fun getItemId(id: Int): Long {
        cursor.moveToPosition( id )
        return cursor.getLong( 0 )
    }

    override fun getView(id: Int, p1: View?, p2: ViewGroup?): View {
        val inflater = context.getSystemService( Context.LAYOUT_INFLATER_SERVICE ) as LayoutInflater
        val v = inflater.inflate( R.layout.elemento_lista, null )

        val tvNomeElementoLista = v.findViewById<TextView>( R.id.tvNomeElementoLista )
        val tvTelefoneElementoLista = v.findViewById<TextView>( R.id.tvTelefoneElementoLista )

        cursor.moveToPosition( id )

        tvNomeElementoLista.text = cursor.getString( 1 )
        tvTelefoneElementoLista.text = cursor.getString( 2 )

        return v
    }
}