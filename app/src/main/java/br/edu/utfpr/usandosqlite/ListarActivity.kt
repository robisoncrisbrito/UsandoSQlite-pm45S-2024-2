package br.edu.utfpr.usandosqlite

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.ArrayAdapter
import android.widget.SimpleCursorAdapter
import androidx.appcompat.app.AppCompatActivity
import br.edu.utfpr.usandosqlite.adapter.ElementoListaAdapter
import br.edu.utfpr.usandosqlite.database.DatabaseHandler
import br.edu.utfpr.usandosqlite.databinding.ActivityListarBinding

class ListarActivity : AppCompatActivity() {

    private lateinit var binding : ActivityListarBinding
    private lateinit var banco : DatabaseHandler

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityListarBinding.inflate( layoutInflater )
        setContentView( binding.root )

        banco = DatabaseHandler(this )
        val cursor = banco.list()

        /*val paises = listOf( "Brasil", "Argentina", "Paraguai", "Uruguai" )
        val adapter = ArrayAdapter<String>( this, android.R.layout.simple_list_item_1, paises )*/

        /*val adapter = SimpleCursorAdapter(
            this,
            android.R.layout.simple_list_item_2,
            cursor,
            arrayOf( "nome", "telefone" ),
            intArrayOf( android.R.id.text1, android.R.id.text2 ),
            0
        )*/

        val adapter = ElementoListaAdapter( this, cursor )
        binding.lvPrincipal.adapter = adapter

    }

    fun btIncluirOnClick(view: View) {
        val intent = Intent( this, MainActivity::class.java )
        startActivity( intent )
    }
}