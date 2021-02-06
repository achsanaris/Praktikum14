package project.achsan.quotesapps.ui

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.TextUtils
import android.view.View
import android.widget.Toast
import project.achsan.quotesapps.MainActivity
import project.achsan.quotesapps.R
import project.achsan.quotesapps.interfaces.MainView
import project.achsan.quotesapps.databinding.ActivityLoginBinding
import project.achsan.quotesapps.models.Login
import project.achsan.quotesapps.models.Quote
import project.achsan.quotesapps.models.Token
import project.achsan.quotesapps.presenters.MainPresenter
import project.achsan.quotesapps.utils.CoroutineContextProvider
import project.achsan.quotesapps.utils.TokenPref

class LoginActivity : AppCompatActivity(), View.OnClickListener, MainView {
    private lateinit var presenter: MainPresenter
    private lateinit var binding: ActivityLoginBinding
    private lateinit var tokenPref: TokenPref
    private lateinit var token: Token
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)
        binding.btnSign.setOnClickListener(this)
        presenter = MainPresenter(this, CoroutineContextProvider())
        tokenPref = TokenPref(this)
        token = tokenPref.getToken()
    }

    override fun onClick(v: View?) {
        when (v?.id) {
            R.id.btnSign -> {
                presenter.login(
                    binding.inputNim.text.toString(),
                    binding.inputPassword.text.toString()
                )
            }
        }
    }

    override fun showMessage(messsage: String) {
        Toast.makeText(this, messsage, Toast.LENGTH_SHORT).show()
    }

    override fun resultQuote(data: ArrayList<Quote>) {
        TODO("Not yet implemented")
    }

    override fun resultLogin(data: Login) {
        if (!TextUtils.isEmpty(data.token)) {
            token.token = data.token
            tokenPref.setToken(token)
            val intent = Intent(this@LoginActivity, MainActivity::class.java)
            startActivity(intent)
            finish()
        }
    }
}