package com.example.wwapp.model


import android.content.res.Resources
import android.net.Uri
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.example.wwapp.R
import com.example.wwapp.databinding.SingleWinItemBinding
import java.text.SimpleDateFormat


class MyAdapter(private val mWins: List<MyWinsItems>, private val listener: OnWinClickListener) :
    RecyclerView.Adapter<MyAdapter.ViewHolder>() {

    interface OnWinClickListener {
        fun onItemClick(win: MyWinsItems)
        fun onItemLongClick(win: MyWinsItems)

    }

    inner class ViewHolder(private val binding: SingleWinItemBinding) :
        RecyclerView.ViewHolder(binding.root) {
        init {
            binding.apply {
                root.setOnClickListener {
                    val position = adapterPosition
                    if (position != RecyclerView.NO_POSITION) {
                        val win = mWins[position]
                        listener.onItemClick(win)
                    }
                }
                root.setOnLongClickListener {
                    val position = adapterPosition
                    if (position != RecyclerView.NO_POSITION) {
                        val win = mWins[position]
                        listener.onItemLongClick(win)
                    }
                    true
                }

            }
        }

        fun bind(win: MyWinsItems) {
            binding.apply {
                tvTitle.text = win.winTitle
                val formatter = SimpleDateFormat("dd/MMMM/yyyy")
                tvDay.text = formatter.format(win.winDay)
                Glide.with(yourImage)
                    .load(Uri.parse("android.resource://${itemView.context.packageName}/drawable/${win.winImage ?: getRandomImagePath()}"))
                    .diskCacheStrategy(DiskCacheStrategy.ALL)
                    .into(yourImage)
            }



    }



    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding =
            SingleWinItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun getItemCount(): Int {
        return mWins.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val win = mWins[position]

        holder.bind(win)
    }
    companion object {
        private val IMAGE_PATHS = listOf(
            "bluebrown",
            "park",
            "radio",
            "grapefruit",
            "beach",
        )


        fun getRandomImagePath(): String {
            val randomIndex = (Math.random() * IMAGE_PATHS.size).toInt()
            return IMAGE_PATHS[randomIndex]
        }
    }

}