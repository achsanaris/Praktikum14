package project.achsan.quotesapps.ui.class_quotes

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import kotlinx.android.synthetic.main.fragment_my_quotes.*
import org.jetbrains.anko.support.v4.onRefresh
import project.achsan.quotesapps.R
import project.achsan.quotesapps.adapter.QuoteAdapter
import project.achsan.quotesapps.databinding.FragmentClassQuotesBinding
import project.achsan.quotesapps.interfaces.MainView
import project.achsan.quotesapps.models.Login
import project.achsan.quotesapps.models.Quote
import project.achsan.quotesapps.models.Token
import project.achsan.quotesapps.presenters.MainPresenter
import project.achsan.quotesapps.utils.CoroutineContextProvider
import project.achsan.quotesapps.utils.TokenPref

class ClassQuotesFragment : Fragment(), MainView {
    private lateinit var tokenPref : TokenPref
    private lateinit var token: Token
    private lateinit var adapter : QuoteAdapter
    private lateinit var presenter: MainPresenter
    private var quotes: MutableList<Quote> = mutableListOf()
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState:
        Bundle?
    ): View? = inflater.inflate(R.layout.fragment_class_quotes, container, false)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val binding = FragmentClassQuotesBinding.bind(view)
        binding.recyclerviewClassQuotes.layoutManager = LinearLayoutManager(activity)
        tokenPref = TokenPref(requireActivity())
        token = tokenPref.getToken()
        adapter = QuoteAdapter(requireActivity())
        binding.recyclerviewClassQuotes.adapter = adapter
        presenter =
            MainPresenter(this, CoroutineContextProvider())
        progressbar.visibility = View.VISIBLE
        presenter.getMyQuotes(token.token)
        swiperefresh.onRefresh {
            progressbar.visibility = View.INVISIBLE
            presenter.getClassQuotes(token.token)
        }
    }

    override fun onResume() {
        super.onResume()
        presenter.getClassQuotes(token.token)
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