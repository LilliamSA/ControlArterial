package cr.ac.una.bloodpressure.adapter


import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import cr.ac.una.bloodpressure.Entity.TomaArterial
import cr.ac.una.bloodpressure.R


class TomaArterial_Adapter(var tomasArteriales: ArrayList<TomaArterial>) :
    RecyclerView.Adapter<RecyclerView.ViewHolder>() {
    private val VIEW_TYPE_HEADER = 0
    private val VIEW_TYPE_ITEM = 1

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return if (viewType == VIEW_TYPE_HEADER) {
            val view = LayoutInflater.from(parent.context).inflate(R.layout.list_item_header, parent, false)
            HeaderViewHolder(view)
        } else {
            val view = LayoutInflater.from(parent.context).inflate(R.layout.list_item, parent, false)
            ViewHolder(view)
        }


    }
    fun getItem(position: Int): TomaArterial {
        return tomasArteriales[position]
    }

    override fun getItemViewType(position: Int): Int {
        return if (position == 0) {
            VIEW_TYPE_HEADER
        } else {
            VIEW_TYPE_ITEM
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val item = tomasArteriales[position]

        if (holder is HeaderViewHolder) {
            holder.bind()
        } else if (holder is ViewHolder) {
            val controlArterialItem = item
            holder.bind(controlArterialItem)
        }
    }

    override fun getItemCount(): Int {
        return tomasArteriales.size
    }
    fun updateData(newData: ArrayList<TomaArterial>) {

        tomasArteriales = newData
        if (!newData.isEmpty())
            if(newData[0]._uuid !=null)
                newData.add(0,TomaArterial(null,0,0,0))
        notifyDataSetChanged()
    }

    inner class HeaderViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView){
        fun bind(){
            val sistolicaTextView = itemView.findViewById<TextView>(R.id.sistolica)
            val distolicaTextView = itemView.findViewById<TextView>(R.id.distolica)
            val ritmoTextView = itemView.findViewById<TextView>(R.id.ritmo)
            sistolicaTextView.text = "Sistólica"
            distolicaTextView.text = "Diastólica"
            ritmoTextView.text = "Ritmo"
        }

    }
    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        val sistolicaTextView = itemView.findViewById<TextView>(R.id.sistolica)
        val distolicaTextView = itemView.findViewById<TextView>(R.id.distolica)
        val ritmoTextView = itemView.findViewById<TextView>(R.id.ritmo)

        fun bind(tomaArterial: TomaArterial) {

            if (tomaArterial.sistolica < 120 || tomaArterial.distolica < 80 ) {
                itemView.setBackgroundColor(Color.GREEN)
            } else if (tomaArterial.sistolica > 120 && tomaArterial.sistolica < 129 ||
                tomaArterial.distolica < 80) {
                itemView.setBackgroundColor(Color.YELLOW)
            } else if (tomaArterial.sistolica > 130 && tomaArterial.sistolica < 139 ||
                tomaArterial.distolica > 80 && tomaArterial.distolica < 89){
                itemView.setBackgroundColor(Color.GRAY)
            }else{
                itemView.setBackgroundColor(Color.RED)
            }

            /* itemView.setBackgroundColor (Color.LTGRAY)
             itemView.setBackgroundColor (Color.LTGRAY)*/
            sistolicaTextView.text = tomaArterial.sistolica.toString()
            distolicaTextView.text = tomaArterial.distolica.toString()
            ritmoTextView.text = tomaArterial.ritmo.toString()
        }

    }
}
