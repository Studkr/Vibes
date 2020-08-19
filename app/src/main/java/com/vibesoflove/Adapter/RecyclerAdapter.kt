package com.vibesoflove.Adapter

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.RelativeLayout
import android.widget.TextView
import com.vibesoflove.Construct.Room_constr
import com.vibesoflove.MainActivity
import com.vibesoflove.R

import de.hdodenhof.circleimageview.CircleImageView


class RecyclerAdapter(private val horizontalGrocderyList: List<Room_constr>, var context: Context, var top: MainActivity,ch:Int) : RecyclerView.Adapter<RecyclerAdapter.GroceryViewHolder>() {
    internal var pos = 1

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): GroceryViewHolder {
        //inflate the layout file
        val groceryProductView = LayoutInflater.from(parent.context).inflate(R.layout.recycler_horizont, parent, false)
        return GroceryViewHolder(groceryProductView)
    }

    override fun onBindViewHolder(holder: GroceryViewHolder, position: Int) {
        holder.imageView.setImageResource(horizontalGrocderyList[position].image)
        holder.txtview.text = horizontalGrocderyList[position].about
        holder.imageView.setOnClickListener {
            pos = position
            notifyDataSetChanged()
            top.top(position)
        }
        if (pos == position) {
            holder.txtview.setTextColor(context.resources.getColor(R.color.colorWhite))
            holder.relativeLayout.background = context.resources.getDrawable(R.drawable.relative_round_selected)
            holder.imageView.borderColor = context.resources.getColor(R.color.colorMain)
        } else {
            holder.txtview.setTextColor(context.resources.getColor(R.color.colorBack))
            holder.relativeLayout.background = context.resources.getDrawable(R.drawable.relative_round)
            holder.imageView.borderColor = context.resources.getColor(R.color.colorWhite)
        }

    }

    override fun getItemCount(): Int {
        return horizontalGrocderyList.size
    }

    inner class GroceryViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        internal var imageView: CircleImageView
        internal var relativeLayout: RelativeLayout
        internal var global: Int = 0
        internal var txtview: TextView

        init {
            imageView = view.findViewById(R.id.image_room)
            txtview = view.findViewById(R.id.text_description)
            relativeLayout = view.findViewById(R.id.ralative_text)
            imageView.setOnClickListener {
                global = adapterPosition
                notifyDataSetChanged()
            }
        }
    }


}
