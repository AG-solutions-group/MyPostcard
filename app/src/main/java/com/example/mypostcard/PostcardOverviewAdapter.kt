package app

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.domain.Order
import com.example.mypostcard.databinding.RecyclerCardBinding

class PostcardOverviewAdapter (
    private val listener: OnClickListener
    ) : RecyclerView.Adapter<PostcardOverviewAdapter.OrdersViewHolder>() {

        // implement onClick for recycler items
        inner class OrdersViewHolder(val binding: RecyclerCardBinding) : RecyclerView.ViewHolder(binding.root), View.OnClickListener {

            init {
                itemView.setOnClickListener(this)
            }

            override fun onClick(v: View?) {
                val position = adapterPosition
                if (position != RecyclerView.NO_POSITION) {
                    listener.onClick(position)
                }
            }
        }
        interface OnClickListener {
            fun onClick(position: Int)
        }


        private val diffCallback = object : DiffUtil.ItemCallback<Order>() {
            override fun areItemsTheSame(oldItem: Order, newItem: Order): Boolean {
                return oldItem.id == newItem.id
            }

            override fun areContentsTheSame(oldItem: Order, newItem: Order): Boolean {
                return oldItem == newItem
            }
        }

        private val differ = AsyncListDiffer(this, diffCallback)
        var orders: List<Order>
        get() = differ.currentList
        set(value) { differ.submitList(value) }

        override fun getItemCount() = orders.size

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): OrdersViewHolder {
            return OrdersViewHolder(RecyclerCardBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            ))
        }

        override fun onBindViewHolder(holder: OrdersViewHolder, position: Int) {
            holder.binding.apply {
                val order = orders[position]

                // add the API data to the recycler
                productTypeTV.text = order.type_name
                orderDateTV.text = order.creation_date.toString() // I cant find the correct value from the response that would fit the date format
                orderStatusTV.text = order.status
                numberRecipientTV.text = "Recipients: ${order.recipient_count}"
                Glide.with(holder.itemView)
                        .load(order.image)
                        .centerCrop()
                        .into(productIV)
            }
        }
    }