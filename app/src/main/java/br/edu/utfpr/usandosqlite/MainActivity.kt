package br.edu.utfpr.usandosqlite

import android.content.ContentValues
import android.content.DialogInterface
import android.content.Intent
import android.database.sqlite.SQLiteDatabase
import android.os.Bundle
import android.view.View
import android.widget.EditText
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AlertDialog
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

        if ( intent.getIntExtra( "cod", 0 ) != 0 ) {
            binding.etCod.setText( intent.getIntExtra( "cod", 0 ).toString() )
            binding.etNome.setText( intent.getStringExtra( "nome" ) )
            binding.etTelefone.setText( intent.getStringExtra( "telefone" ) )
        } else {
           binding.btExcluir.visibility = View.GONE
           binding.btPesquisar.visibility = View.GONE
        }


        initDatabase()

        binding.btAlterar.setOnClickListener {
            btAlterarOnClick()
        }

        binding.btExcluir.setOnClickListener {
            btExcluirOnClick()
        }

        binding.btPesquisar.setOnClickListener {
            btPesquisarOnClick()
        }

    }

    private fun initDatabase() {

        banco = DatabaseHandler( this )

    }

    private fun btPesquisarOnClick() {

        val etCodDialog = EditText( this )

        val dialog = AlertDialog.Builder( this )
        dialog.setTitle( "Código de Pesquisa" )
        dialog.setView(etCodDialog)
        dialog.setCancelable( false )
        dialog.setNegativeButton( "Fechar", null )
        dialog.setPositiveButton( "Pesquisar", { dialogInterface, i ->
            val registro = banco.find( etCodDialog.text.toString().toInt() )

            if ( registro != null ) {
                binding.etCod.setText( etCodDialog.text.toString() )
                binding.etNome.setText( registro.nome )
                binding.etTelefone.setText( registro.telefone )
            } else {
                Toast.makeText( this, "Registro não encontrado", Toast.LENGTH_LONG ).show()
            }

        } )
        dialog.show()



    }

    private fun btExcluirOnClick() {
        banco.delete( binding.etCod.text.toString().toInt() )

        Toast.makeText( this, "Sucesso", Toast.LENGTH_LONG ).show()

        finish()
    }

    private fun btAlterarOnClick() {

        if ( binding.etCod.text.toString().isEmpty() ) {
            val cadastro = Cadastro(
                0,
                binding.etNome.text.toString(),
                binding.etTelefone.text.toString()
            )
            banco.insert( cadastro )
        } else {
            val cadastro = Cadastro(
                binding.etCod.text.toString().toInt(),
                binding.etNome.text.toString(),
                binding.etTelefone.text.toString()
            )
            banco.update(cadastro)
        }

        Toast.makeText( this, "Sucesso", Toast.LENGTH_LONG ).show()
        finish()
    }

}