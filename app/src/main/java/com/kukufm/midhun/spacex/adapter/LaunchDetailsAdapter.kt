package com.kukufm.midhun.spacex.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.bumptech.glide.Glide
import com.kukufm.midhun.spacex.R
import com.kukufm.midhun.spacex.databinding.LaunchDetailsItemBinding
import com.kukufm.midhun.spacex.models.LaunchInfoModel
import com.squareup.picasso.Picasso

class LaunchDetailsAdapter(
    private val launchDetailsList :List<LaunchInfoModel>,
    private val clickListner: LaunchItemClickListner,
    private val context : Context
) : RecyclerView.Adapter<LaunchDetailsAdapter.LaunchDetailsViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): LaunchDetailsViewHolder {
        val binder = LaunchDetailsItemBinding.inflate(
            LayoutInflater.from(parent.context),parent,false
        )
        return LaunchDetailsViewHolder(binder);
    }

    override fun onBindViewHolder(holder: LaunchDetailsViewHolder, position: Int) {
        val launchDetails = launchDetailsList.get(position)
        holder.binder.let {
            it.launchYear.text = launchDetails.launch_year
            it.missionName.text = launchDetails.mission_name
            it.rocketName.text = launchDetails.rocket?.rocket_name
//            Picasso.get()
//                .load(launchDetails.links?.mission_patch)
//                .error(R.drawable.ic_launcher_background)
//                .placeholder(R.drawable.ic_launcher_background)
//                .into(it.missionPatch)

            Glide.with(context)
                .load(launchDetails.links?.mission_patch)
                .placeholder(R.drawable.ic_launcher_background)
                .into(it.missionPatch)
        }
    }

    override fun getItemCount(): Int {
        return launchDetailsList.size
    }

    inner class LaunchDetailsViewHolder(val binder : LaunchDetailsItemBinding) :
        ViewHolder(binder.root) {

            init {
                binder.root.setOnClickListener{
                    clickListner.onItemClicker(adapterPosition)
                }
            }

    }

}