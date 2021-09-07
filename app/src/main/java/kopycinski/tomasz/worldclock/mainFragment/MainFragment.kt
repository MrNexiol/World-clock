package kopycinski.tomasz.worldclock.mainFragment

import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import kopycinski.tomasz.worldclock.databinding.FragmentMainBinding

class MainFragment : Fragment(), MainFragmentAdapter.ClickHandler {
    private var _binding: FragmentMainBinding? = null
    private val binding get() = _binding!!
    private val adapter = MainFragmentAdapter()
    private lateinit var prefs: SharedPreferences
    private var chosenTimeZoneList: MutableList<String>? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentMainBinding.inflate(inflater, container, false)

        prefs = requireActivity().getSharedPreferences("data", Context.MODE_PRIVATE)
        chosenTimeZoneList = prefs.getStringSet("list", null)?.toMutableList()

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.chosenTimeZoneList.layoutManager = LinearLayoutManager(requireContext())
        binding.chosenTimeZoneList.adapter = adapter

        binding.addTimeZoneButton.setOnClickListener {
            val action = MainFragmentDirections.actionMainFragmentToTimeZoneListFragment()
            findNavController().navigate(action)
        }
    }

    override fun onResume() {
        super.onResume()

        chosenTimeZoneList?.let {
            adapter.setData(it, this)
        }
    }

    override fun onTimeZoneClicked(timezone: String) {
        val index = chosenTimeZoneList!!.indexOf(timezone)
        chosenTimeZoneList!!.removeAt(index)
        with(prefs.edit()) {
            this.putStringSet("list", chosenTimeZoneList!!.toSet())
            this.commit()
        }
        adapter.setData(chosenTimeZoneList!!, this)
    }
}