package project.achsan.quotesapps.interfaces

import project.achsan.quotesapps.models.Login
import project.achsan.quotesapps.models.Quote

interface MainView {
    fun showMessage(messsage : String)
    fun resultQuote(data: ArrayList<Quote>)
    fun resultLogin(data: Login)
}