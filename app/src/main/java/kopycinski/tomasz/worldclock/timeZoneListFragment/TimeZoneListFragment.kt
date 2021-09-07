package kopycinski.tomasz.worldclock.timeZoneListFragment

import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import kopycinski.tomasz.worldclock.TimeZoneAdapter
import kopycinski.tomasz.worldclock.databinding.FragmentTimeZoneListBinding
import java.util.*

class TimeZoneListFragment : Fragment(), TimeZoneAdapter.TimeZoneAdapterListener {
    private var _binding: FragmentTimeZoneListBinding? = null
    private val binding get() = _binding!!
    private val adapter = TimeZoneAdapter()
    private val timeZoneList = TimeZone.getAvailableIDs().asList()
    private var currentTimeZoneList = timeZoneList
    private lateinit var prefs: SharedPreferences
    private var chosenTimeZoneList: Set<String>? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentTimeZoneListBinding.inflate(inflater, container, false)
        prefs = requireActivity().getSharedPreferences("data", Context.MODE_PRIVATE)
        chosenTimeZoneList = prefs.getStringSet("list", null)

        binding.timeZoneRecyclerView.layoutManager = LinearLayoutManager(requireContext())
        binding.timeZoneRecyclerView.adapter = adapter
        adapter.setData(currentTimeZoneList, this)

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.timeZoneSearchField.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                currentTimeZoneList = timeZoneList.filter { it.lowercase().contains(s!!) }
                adapter.setData(currentTimeZoneList, this@TimeZoneListFragment)
            }

            override fun afterTextChanged(s: Editable?) {}

        })
    }

    override fun onTimeZoneChosen(timeZoneId: String) {
        val prefs = requireActivity().getSharedPreferences("data", Context.MODE_PRIVATE)
        val toSave = mutableSetOf<String>()

        chosenTimeZoneList?.let { toSave.addAll(it) }
        toSave.add(timeZoneId)
        with(prefs.edit()) {
            this.putStringSet("list", toSave)
            this.commit()
        }
        Toast.makeText(requireContext(), timeZoneId, Toast.LENGTH_SHORT).show()
    }
}