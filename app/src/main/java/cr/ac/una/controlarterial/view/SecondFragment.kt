package cr.ac.una.controlarterial.view

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import cr.ac.una.controlarterial.Entity.TomaArterial
import cr.ac.una.controlarterial.R
import cr.ac.una.controlarterial.databinding.FragmentSecondBinding
import cr.ac.una.controlarterial.viewModel.TomaArterialViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch


/**
 * A simple [Fragment] subclass as the second destination in the navigation.
 */
class SecondFragment : Fragment() {

    private var _binding: FragmentSecondBinding? = null
    private lateinit var tomaArterialViewModel: TomaArterialViewModel
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        _binding = FragmentSecondBinding.inflate(inflater, container, false)
        return binding.root

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.buttonSecond.setOnClickListener {
            findNavController().navigate(R.id.action_SecondFragment_to_FirstFragment)
        }

        // Llamar al viewmodel para agregar una toma arterial
        tomaArterialViewModel = ViewModelProvider(this).get(TomaArterialViewModel::class.java)
        binding.buttonSave.setOnClickListener {
            val _uuid = null
            val distolica = binding.editTextDistolica.text.toString().toInt()
            val sistolica = binding.editTextSistolica.text.toString().toInt()
            val ritmo = binding.editTextRitmo.text.toString().toInt()

            val tomaArterial = TomaArterial(_uuid,distolica, sistolica, ritmo)

            // Lanzar una coroutine para llamar al método insertTomaArterial
            CoroutineScope(Dispatchers.Main).launch {
                tomaArterialViewModel.insertTomaArterial(tomaArterial)

                // Limpiar los campos de texto
                binding.editTextDistolica.setText("")
                binding.editTextSistolica.setText("")
                binding.editTextRitmo.setText("")

                //mensaje
                Toast.makeText(context, "Se agregó correctamente", Toast.LENGTH_SHORT).show()

            }


        }
    }
        override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}