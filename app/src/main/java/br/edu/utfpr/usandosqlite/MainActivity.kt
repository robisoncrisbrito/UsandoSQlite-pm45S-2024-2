package br.edu.utfpr.usandosqlite

import android.content.ContentValues
import android.content.Intent
import android.database.sqlite.SQLiteDatabase
import android.os.Bundle
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import br.edu.utfpr.usandosqlite.database.DatabaseHandler
import br.edu.utfpr.usandosqlite.databinding.ActivityMainBinding
import br.edu.utfpr.usandosqlite.entity.Cadastro

class MainActivity : AppCompatActivity() {

    private lateinit var binding : ActivityMainBinding
    private lateinit var banco : DatabaseHandler

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

        banco = DatabaseHandler( this )

    }

    private fun btListarOnClick() {

        val intent = Intent(  this, ListarActivity::class.java )
        startActivity( intent )

        /*val registros = banco.list()

        var saida = StringBuilder()

        while ( registros.moveToNext() ) {
            saida.append( registros.getInt( 0 ) )
            saida.append( "-" )
            saida.append( registros.getString( 1 ) )
            saida.append( "-" )
            saida.append( registros.getString( 2 ) )
        }

        Toast.makeText( this, saida.toString(), Toast.LENGTH_LONG ).show()
*/
    }

    private fun btPesquisarOnClick() {
        val registro = banco.find( binding.etCod.text.toString().toInt() )

        if ( registro != null ) {
            binding.etNome.setText( registro.nome )
            binding.etTelefone.setText( registro.telefone )
        } else {
            Toast.makeText( this, "Registro não encontrado", Toast.LENGTH_LONG ).show()
        }
    }

    private fun btExcluirOnClick() {
        banco.delete( binding.etCod.text.toString().toInt() )

        Toast.makeText( this, "Sucesso", Toast.LENGTH_LONG ).show()
    }

    private fun btAlterarOnClick() {
        val cadastro = Cadastro(
            binding.etCod.text.toString().toInt(),
            binding.etNome.text.toString(),
            binding.etTelefone.text.toString()
        )

        banco.update( cadastro )

        Toast.makeText( this, "Sucesso", Toast.LENGTH_LONG ).show()
    }

    private fun btIncluirOnClick() {
        val cadastro = Cadastro(
            0,
            binding.etNome.text.toString(),
            binding.etTelefone.text.toString()
        )

        banco.insert( cadastro )

        Toast.makeText( this, "Sucesso", Toast.LENGTH_LONG ).show()
    }
}