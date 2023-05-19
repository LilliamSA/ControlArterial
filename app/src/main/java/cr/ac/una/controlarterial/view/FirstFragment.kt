package cr.ac.una.controlarterial.view

import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.os.Bundle
import android.view.*
import androidx.fragment.app.Fragment
import android.widget.ListView
import androidx.core.content.ContextCompat

import androidx.core.os.bundleOf
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
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import cr.ac.una.controlarterial.Entity.TomasArteriales
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope

import kotlinx.coroutines.launch

class FirstFragment : Fragment() {

    private var _binding: FragmentFirstBinding? = null
    private val binding get() = _binding!!
    private lateinit var tomaArterialViewModel : TomaArterialViewModel
    private lateinit var tomasArteriales :List<TomaArterial>


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

        //Se inicializa el Adapter con una lista vacía
        val listView = view.findViewById<RecyclerView>(R.id.list_presiones)
        tomasArteriales = mutableListOf<TomaArterial>()
        var adapter =  TomaArterial_Adapter(tomasArteriales as ArrayList<TomaArterial>)
        listView.adapter = adapter
        listView.layoutManager = LinearLayoutManager(requireContext())

        //Se atacha al viewModel de la activiad principal
        tomaArterialViewModel = ViewModelProvider(requireActivity()).get(TomaArterialViewModel::class.java)

        //cada vez que el observador nota reporta un cambio en los datos se actualiza la lista de elementos
        tomaArterialViewModel.tomasArteriales.observe(viewLifecycleOwner) { elementos ->

            adapter.updateData(elementos as ArrayList<TomaArterial>)
            tomasArteriales = elementos

        }
        // Se llama el código del ViewModel que cargan los datos
        GlobalScope.launch(Dispatchers.Main) {
            tomaArterialViewModel.listTomaArterial()!!
        }

        // Crea el ItemTouchHelper
        val itemTouchHelper = ItemTouchHelper(object : ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.RIGHT) {
            override fun onMove(recyclerView: RecyclerView, viewHolder: RecyclerView.ViewHolder, target: RecyclerView.ViewHolder): Boolean {
                return false
            }

            override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
                val position = viewHolder.adapterPosition
                if (position != 0) {
                    val id = tomasArteriales[position]._uuid
                    val deletedItem = tomasArteriales[position]
                    (tomasArteriales as MutableList<TomaArterial>).removeAt(position)
                    adapter.updateData(tomasArteriales as ArrayList<TomaArterial>)

                    // Llama al código del ViewModel que elimina los datos y que agarre el parametro de uuid correspondiente
                    GlobalScope.launch(Dispatchers.Main) {
                        deletedItem._uuid?.let { tomaArterialViewModel.deleteItem(it) }
                    }
                }}
            // Sobrescribe el método para dibujar la etiqueta al deslizar
            override fun onChildDraw(
                c: Canvas,
                recyclerView: RecyclerView,
                viewHolder: RecyclerView.ViewHolder,
                dX: Float,
                dY: Float,
                actionState: Int,
                isCurrentlyActive: Boolean
            ) {
                if (viewHolder is TomaArterial_Adapter.ViewHolder) {
                    super.onChildDraw(
                        c,
                        recyclerView,
                        viewHolder,
                        dX,
                        dY,
                        actionState,
                        isCurrentlyActive
                    )
                    if (actionState == ItemTouchHelper.ACTION_STATE_SWIPE) {
                        val itemView = viewHolder.itemView
                        val paint = Paint()
                        paint.color = Color.RED
                        val deleteIcon = ContextCompat.getDrawable(
                            requireContext(),
                            android.R.drawable.ic_menu_delete
                        )
                        val iconMargin = (itemView.height - deleteIcon!!.intrinsicHeight) / 2
                        val iconTop =
                            itemView.top + (itemView.height - deleteIcon.intrinsicHeight) / 2
                        val iconBottom = iconTop + deleteIcon.intrinsicHeight

                        // Dibuja el fondo rojo
                        c.drawRect(
                            itemView.left.toFloat(),
                            itemView.top.toFloat(),
                            itemView.right.toFloat(),
                            itemView.bottom.toFloat(),
                            paint
                        )

                        // Calcula las posiciones del icono de eliminar
                        val iconLeft = itemView.right - iconMargin - deleteIcon.intrinsicWidth
                        val iconRight = itemView.right - iconMargin
                        deleteIcon.setBounds(iconLeft, iconTop, iconRight, iconBottom)

                        // Dibuja el icono de eliminar
                        deleteIcon.draw(c)
                    }
                }
            }
        })

        // Adjunta el ItemTouchHelper al RecyclerView
        itemTouchHelper.attachToRecyclerView(listView)


    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }


}

