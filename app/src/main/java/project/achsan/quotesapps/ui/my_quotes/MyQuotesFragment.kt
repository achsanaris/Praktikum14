package project.achsan.quotesapps.ui.my_quotes

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import kotlinx.android.synthetic.main.fragment_my_quotes.*
import org.jetbrains.anko.support.v4.onRefresh
import project.achsan.quotesapps.R
import project.achsan.quotesapps.adapter.QuoteAdapter
import project.achsan.quotesapps.databinding.FragmentMyQuotesBinding
import project.achsan.quotesapps.interfaces.MainView
import project.achsan.quotesapps.models.Login
import project.achsan.quotesapps.models.Quote
import project.achsan.quotesapps.models.Token
import project.achsan.quotesapps.presenters.MainPresenter
import project.achsan.quotesapps.ui.QuoteAddUpdateActivity
import project.achsan.quotesapps.utils.CoroutineContextProvider
import project.achsan.quotesapps.utils.TokenPref
import project.achsan.quotesapps.utils.helper.REQUEST_ADD

class MyQuotesFragment : Fragment(), MainView {
    private lateinit var tokenPref : TokenPref
    private lateinit var token: Token
    private lateinit var adapter : QuoteAdapter
    private lateinit var presenter:MainPresenter
    private var quotes: MutableList<Quote> = mutableListOf()
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState:
        Bundle?
    ): View? = inflater.inflate(R.layout.fragment_my_quotes, container, false)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val binding = FragmentMyQuotesBinding.bind(view)
        binding.recyclerviewMyQuotes.layoutManager = LinearLayoutManager(activity)
        tokenPref = TokenPref(requireActivity())
        token = tokenPref.getToken()
        adapter = QuoteAdapter(requireActivity())
        binding.recyclerviewMyQuotes.adapter = adapter
        presenter =
            MainPresenter(this, CoroutineContextProvider())
        progressbar.visibility = View.VISIBLE
        presenter.getMyQuotes(token.token)
        swiperefresh.onRefresh {
            progressbar.visibility = View.INVISIBLE
            presenter.getMyQuotes(token.token)
        }
        binding.fab.setOnClickListener {
            val intent = Intent(requireActivity(),
                QuoteAddUpdateActivity::class.java)
            startActivityForResult(intent, REQUEST_ADD)
        }
    }

    override fun onResume() {
        super.onResume()
        presenter.getMyQuotes(token.token)
    }

    override fun showMessage(messsage: String) {
        Toast.makeText(requireActivity(),messsage, Toast.LENGTH_SHORT).show()
    }

    override fun resultQuote(data: ArrayList<Quote>) {
        quotes.clear()
        adapter.listQuotes = data
        quotes.addAll(data)
        adapter.notifyDataSetChanged()
        progressbar.visibility = View.INVISIBLE
        swiperefresh.isRefreshing = false
    }

    override fun resultLogin(data: Login) {
        TODO("Not yet implemented")
    }

}