package kopycinski.tomasz.worldclock

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import kopycinski.tomasz.worldclock.databinding.TimeZoneRecyclerItemBinding

class TimeZoneAdapter : RecyclerView.Adapter<TimeZoneAdapter.ViewHolder>() {
    private var data = listOf<String>()
    private var listener: TimeZoneAdapterListener? = null

    interface TimeZoneAdapterListener {
        fun onTimeZoneChosen(timeZoneId: String)
    }

    class ViewHolder(
        val binding: TimeZoneRecyclerItemBinding) : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = TimeZoneRecyclerItemBinding
            .inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.binding.timeZoneId.text = data[position]

        holder.binding.root.setOnClickListener {
            listener?.onTimeZoneChosen(data[position])
        }
    }

    override fun getItemCount(): Int {
        return data.size
    }

    fun setData(zoneList: List<String>, newListener: TimeZoneAdapterListener?) {
        data = zoneList
        listener = newListener
        notifyDataSetChanged()
    }
}