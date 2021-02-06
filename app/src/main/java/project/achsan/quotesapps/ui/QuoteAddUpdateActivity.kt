package project.achsan.quotesapps.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import project.achsan.quotesapps.R
import project.achsan.quotesapps.databinding.ActivityQuoteAddUpdateBinding
import project.achsan.quotesapps.interfaces.MainView
import project.achsan.quotesapps.models.Login
import project.achsan.quotesapps.models.Quote
import project.achsan.quotesapps.models.Token
import project.achsan.quotesapps.presenters.MainPresenter
import project.achsan.quotesapps.utils.CoroutineContextProvider
import project.achsan.quotesapps.utils.TokenPref
import project.achsan.quotesapps.utils.helper.ALERT_DIALOG_CLOSE
import project.achsan.quotesapps.utils.helper.ALERT_DIALOG_DELETE
import project.achsan.quotesapps.utils.helper.EXTRA_QUOTE

class QuoteAddUpdateActivity : AppCompatActivity(), View.OnClickListener, MainView {
    private var isEdit = false
    private var quote: Quote? = null
    private lateinit var binding: ActivityQuoteAddUpdateBinding
    private lateinit var presenter: MainPresenter
    private lateinit var tokenPref: TokenPref
    private lateinit var token: Token
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityQuoteAddUpdateBinding.inflate(layoutInflater)
        setContentView(binding.root)
        tokenPref = TokenPref(this)
        token = tokenPref.getToken()
        quote = intent.getParcelableExtra(EXTRA_QUOTE)
        presenter = MainPresenter(this, CoroutineContextProvider())
        if (quote != null) {
            isEdit = true
        } else {
            quote = Quote()
        }
        val actionBarTitle: String
        val btnTitle: String

        if (isEdit) {
            actionBarTitle = "Ubah"
            btnTitle = "Update"
            quote?.let {
                binding.edtTitle.setText(it.quote_name)
                binding.edtDescription.setText(it.quote_description)
            }!!
        } else {
            actionBarTitle = "Tambah"
            btnTitle = "Simpan"
        }

        supportActionBar?.title = actionBarTitle
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        binding.btnSubmit.text = btnTitle
        binding.btnSubmit.setOnClickListener(this)
    }



    override fun onClick(view: View) {
        if (view.id == R.id.btn_submit) {
            val title = binding.edtTitle.text.toString().trim()
            val description = binding.edtDescription.text.toString().trim()
            if (title.isEmpty()) {
                binding.edtTitle.error = "Field can not be blank"
                return
            }
            if (isEdit) {
                presenter.updateQuote(
                        token.token.toString(),
                        quote!!.quote_id.toString(),
                        binding.edtTitle.text.toString(),
                        binding.edtDescription.text.toString()
                )
            } else {
                presenter.addQuote(
                        token.token.toString(),
                        binding.edtTitle.text.toString(),
                        binding.edtDescription.text.toString()
                )
            }
        }
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        if (isEdit) {
            menuInflater.inflate(R.menu.menu_delete, menu)
        }
        return super.onCreateOptionsMenu(menu)
    }
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.action_delete -> showAlertDialog(ALERT_DIALOG_DELETE)
            android.R.id.home -> showAlertDialog(ALERT_DIALOG_CLOSE)
        }
        return super.onOptionsItemSelected(item)
    }

    private fun showAlertDialog(type: Int) {
        val isDialogClose = type == ALERT_DIALOG_CLOSE
        val dialogTitle: String
        val dialogMessage: String
        if (isDialogClose) {
            dialogTitle = "Batal"
            dialogMessage = "Apakah anda ingin membatalkan perubahan pada form?"
        } else {
            dialogMessage = "Apakah anda yakin ingin menghapus item ini?"
            dialogTitle = "Hapus Quote"
        }
        val alertDialogBuilder = AlertDialog.Builder(this)
        alertDialogBuilder.setTitle(dialogTitle)
        alertDialogBuilder
                .setMessage(dialogMessage)
                .setCancelable(false)
                .setPositiveButton("Ya") { _, _ ->
                    if (isDialogClose) {
                        finish()
                    } else {
                        presenter.deleteQuote(token.token.toString(),quote?.quote_id.toString())
                    }
                }
                .setNegativeButton("Tidak") { dialog, _ -> dialog.cancel() }
        val alertDialog = alertDialogBuilder.create()
        alertDialog.show()
    }

    override fun showMessage(messsage: String) {
        Toast.makeText(this,messsage, Toast.LENGTH_SHORT).show()
        finish()
    }

    override fun resultQuote(data: ArrayList<Quote>) {
        TODO("Not yet implemented")
    }

    override fun resultLogin(data: Login) {
        TODO("Not yet implemented")
    }
}