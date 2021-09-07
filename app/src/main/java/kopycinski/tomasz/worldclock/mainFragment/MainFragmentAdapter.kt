package kopycinski.tomasz.worldclock.mainFragment

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import kopycinski.tomasz.worldclock.databinding.FragmentMainRecyclerItemBinding
import java.util.*

class MainFragmentAdapter : RecyclerView.Adapter<MainFragmentAdapter.ViewHolder>() {
    private var data = listOf<String>()
    private var listener: ClickHandler? = null

    interface ClickHandler {
        fun onTimeZoneClicked(timezone: String)
    }

    class ViewHolder(
        val binding: FragmentMainRecyclerItemBinding) : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = FragmentMainRecyclerItemBinding
            .inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.binding.zoneId.text = data[position]
        holder.binding.zoneHour.timeZone = data[position]
        holder.binding.zoneDate.timeZone = data[position]

        holder.binding.root.setOnClickListener {
            listener?.onTimeZoneClicked(data[position])
        }
    }

    override fun getItemCount(): Int {
        return data.size
    }

    fun setData(ids: List<String>, newListener: ClickHandler?) {
        data = ids
        listener = newListener
        notifyDataSetChanged()
    }
}