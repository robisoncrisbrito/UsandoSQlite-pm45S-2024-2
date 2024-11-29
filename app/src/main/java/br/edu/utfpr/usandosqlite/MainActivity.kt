package br.edu.utfpr.usandosqlite

import android.content.ContentValues
import android.database.sqlite.SQLiteDatabase
import android.os.Bundle
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import br.edu.utfpr.usandosqlite.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private lateinit var binding : ActivityMainBinding
    private lateinit var banco : SQLiteDatabase

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate( layoutInflater )
        setContentView( binding.root )

        initDatabase()

        binding.btIncluir.setOnClickListener {
            btIncluirOnClick()
        }

        binding.btAlterar.setOnClickListener {
            btAlterarOnClick()
        }

        binding.btExcluir.setOnClickListener {
            btExcluirOnClick()
        }

        binding.btPesquisar.setOnClickListener {
            btPesquisarOnClick()
        }

        binding.btListar.setOnClickListener {
            btListarOnClick()
        }

    }

    private fun initDatabase() {

        banco = SQLiteDatabase.openOrCreateDatabase(
            this.getDatabasePath( "bdfile.sqlite" ),
            null
        )

        banco.execSQL( "CREATE TABLE IF NOT EXISTS cadastro (_id INTEGER PRIMARY KEY AUTOINCREMENT, " +
            "nome TEXT, telefone TEXT, email TEXT )" )
    }

    private fun btListarOnClick() {

        val registros = banco.query(
            "cadastro",
            null,
            null,
            null,
            null,
            null,
            null
        )

        var saida = StringBuilder()

        while ( registros.moveToNext() ) {
            saida.append( registros.getInt( 0 ) )
            saida.append( "-" )
            saida.append( registros.getInt( 1 ) )
            saida.append( "-" )
            saida.append( registros.getInt( 2 ) )
        }


    }

    private fun btPesquisarOnClick() {
        val registro = banco.query(
            "cadastro",
            null,
            "_id=${binding.etCod.text.toString()}",
            null,
            null,
            null,
            null
        )

        if ( registro.moveToNext() ) {
            binding.etNome.setText( registro.getString( 1 ) )
            binding.etTelefone.setText( registro.getString( 2 ) )
        } else {
            Toast.makeText( this, "Registro não encontrado", Toast.LENGTH_LONG ).show()
        }
    }

    private fun btExcluirOnClick() {
        banco.delete( "cadastro", "_id=${binding.etCod.text.toString()}", null )

        Toast.makeText( this, "Sucesso", Toast.LENGTH_LONG ).show()
    }

    private fun btAlterarOnClick() {
        val registro = ContentValues()
        registro.put( "nome", binding.etNome.text.toString() )
        registro.put( "telefone", binding.etTelefone.text.toString() )

        banco.update( "cadastro", registro, "_id=${binding.etCod.text.toString()}", null )

        Toast.makeText( this, "Sucesso", Toast.LENGTH_LONG ).show()
    }

    private fun btIncluirOnClick() {
        val registro = ContentValues()
        registro.put( "nome", binding.etNome.text.toString() )
        registro.put( "telefone", binding.etTelefone.text.toString() )

        banco.insert( "cadastro", null, registro )

        Toast.makeText( this, "Sucesso", Toast.LENGTH_LONG ).show()
    }
}