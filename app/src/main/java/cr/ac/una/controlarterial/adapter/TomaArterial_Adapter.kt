package cr.ac.una.controlarterial.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.TextView
import cr.ac.una.controlarterial.Entity.TomaArterial
import cr.ac.una.controlarterial.R


class TomaArterial_Adapter(context: Context, presiones: List<TomaArterial>) :
    ArrayAdapter<TomaArterial>(context, 0, presiones) {

    private var items: List<TomaArterial> = presiones

    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        var view = convertView
        if (view == null) {
            view = LayoutInflater.from(context).inflate(R.layout.list_item, parent, false)
        }

        val presion = getItem(position)

        val distolicaTextView = view!!.findViewById<TextView>(R.id.distolica)
        val sistolicaTextView = view.findViewById<TextView>(R.id.sistolica)
        val ritmoTextView = view.findViewById<TextView>(R.id.ritmo)

        distolicaTextView.text = presion?.distolica.toString()
        sistolicaTextView.text = presion?.sistolica.toString()
        ritmoTextView.text = presion?.ritmo.toString()

        return view
    }

    fun updateItems(newItems: List<TomaArterial>) {
        items = newItems
        notifyDataSetChanged()
    }

    override fun getCount(): Int {
        return items.size
    }

    override fun getItem(position: Int): TomaArterial? {
        return items[position]
    }

    override fun getItemId(position: Int): Long {
        return position.toLong()
    }
}
