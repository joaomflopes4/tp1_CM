package ipvc.estg.cmprojeto.adapters

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.tp1_cm.EditNotasActivity
import com.example.tp1_cm.NotasActivity
import com.example.tp1_cm.R
import com.example.tp1_cm.entities.Notas


const val TITULO="TITULO"
const val DESCRICAO="DESCRICAO"
const val ID="ID"

class NotasAdapter internal constructor(
    context: Context, private val callbackInterface: NotasActivity
) : RecyclerView.Adapter<NotasAdapter.NotaViewHolder>() {

    private val inflater: LayoutInflater = LayoutInflater.from(context)
    private var notas = emptyList<Notas>() // Cached copy of cities

    interface CallbackInterface {
        fun passResultCallback(id: Int?)
    }

    class NotaViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val notaItemView: TextView = itemView.findViewById(R.id.nota)
        val descricao: TextView = itemView.findViewById(R.id.desc)
        val edit : ImageButton = itemView.findViewById(R.id.edit)
        val delete : ImageButton = itemView.findViewById(R.id.delete)

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NotaViewHolder {
        val itemView = inflater.inflate(R.layout.recyclerview_item, parent, false)
        return NotaViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: NotaViewHolder, position: Int) {
        val current = notas[position]
        holder.notaItemView.text = current.titulo
        holder.descricao.text = current.descricao
        val id: Int? = current.id

        holder.delete.setOnClickListener {
            callbackInterface.delete(current.id)
        }


        holder.edit.setOnClickListener {
            val context = holder.notaItemView.context
            val titl = holder.notaItemView.text.toString()
            val desc = holder.descricao.text.toString()

            val intent = Intent(context, EditNotasActivity::class.java).apply {
                putExtra(TITULO, titl)
                putExtra(DESCRICAO, desc )
                putExtra( ID,id)
            }
            context.startActivity(intent)
        }
    }

    internal fun setNotas(notas: List<Notas>) {
        this.notas = notas
        notifyDataSetChanged()
    }

    override fun getItemCount() = notas.size

}