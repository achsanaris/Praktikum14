package project.achsan.quotesapps.adapter

import android.annotation.SuppressLint
import android.app.Activity
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import project.achsan.quotesapps.R
import project.achsan.quotesapps.databinding.ItemQuoteBinding
import project.achsan.quotesapps.models.Quote
import project.achsan.quotesapps.ui.QuoteAddUpdateActivity
import project.achsan.quotesapps.utils.helper.EXTRA_POSITION
import project.achsan.quotesapps.utils.helper.EXTRA_QUOTE
import project.achsan.quotesapps.utils.helper.REQUEST_UPDATE

class QuoteAdapter(private val activity: Activity): RecyclerView.Adapter<QuoteAdapter.QuoteViewHolder>() {
    var listQuotes = ArrayList<Quote>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): QuoteViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_quote, parent, false)
        return QuoteViewHolder(view)
    }

    override fun getItemCount(): Int = this.listQuotes.size

    override fun onBindViewHolder(holder: QuoteViewHolder, position: Int) {
        holder.bind(listQuotes[position],position)
    }

    inner class QuoteViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val binding = ItemQuoteBinding.bind(itemView)
        @SuppressLint("SetTextI18n")
        fun bind(quote: Quote, position: Int) {
            binding.tvItemCategory.text = quote.user_name+" ("+quote.class_name+")"
            binding.tvItemTitle.text = quote.quote_name
            binding.tvItemDate.text = quote.created_at
            binding.tvItemDescription.text = quote.quote_description
            binding.cvItemQuote.setOnClickListener{
                val intent = Intent(activity,
                    QuoteAddUpdateActivity::class.java)
                intent.putExtra(EXTRA_POSITION, position)
                intent.putExtra(EXTRA_QUOTE, quote)
                activity.startActivityForResult(intent, REQUEST_UPDATE)
            }
        }
    }
}