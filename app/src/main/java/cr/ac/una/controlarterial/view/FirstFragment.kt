package cr.ac.una.controlarterial.view

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ListView
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import cr.ac.una.controlarterial.Entity.TomaArterial
import cr.ac.una.controlarterial.R
import cr.ac.una.controlarterial.adapter.TomaArterial_Adapter
import cr.ac.una.controlarterial.databinding.FragmentFirstBinding
import cr.ac.una.controlarterial.viewModel.TomaArterialViewModel
import androidx.lifecycle.lifecycleScope
import kotlinx.coroutines.launch
import androidx.lifecycle.observe
import kotlinx.coroutines.launch

class FirstFragment : Fragment() {

    private var _binding: FragmentFirstBinding? = null
    private val binding get() = _binding!!
    private lateinit var tomaArterialViewModel: TomaArterialViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentFirstBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.buttonFirst.setOnClickListener {
            findNavController().navigate(R.id.action_FirstFragment_to_SecondFragment)
        }

        // Llamar al viewmodel para listar las tomas arteriales
        tomaArterialViewModel = ViewModelProvider(this).get(TomaArterialViewModel::class.java)
        viewLifecycleOwner.lifecycleScope.launch {
            tomaArterialViewModel.listTomaArterial()
        }

        // Observar los cambios en las tomas arteriales y actualizar el ListView
        tomaArterialViewModel.tomasArteriales.observe(viewLifecycleOwner) { tomasArteriales ->
            val adapter = TomaArterial_Adapter(requireContext(), tomasArteriales ?: emptyList())
            val listView = view.findViewById<ListView>(R.id.list_presiones)
            listView?.adapter = adapter
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}

