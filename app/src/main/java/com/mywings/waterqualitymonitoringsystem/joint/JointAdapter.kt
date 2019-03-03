package generalknowledge.mywings.com.smartdustbinsystem.joint

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.mywings.waterqualitymonitoringsystem.R
import com.mywings.waterqualitymonitoringsystem.model.Location
import kotlinx.android.synthetic.main.layout_row.view.*

class JointAdapter(var list: List<Location>) : RecyclerView.Adapter<JointAdapter.JointViewHolder>() {

    var lstVehicle = list;

    private lateinit var onLocationQListener: OnLocationQListener

    override fun onCreateViewHolder(parent: ViewGroup, itemType: Int): JointViewHolder {
        return JointViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.layout_row, parent, false))
    }

    override fun getItemCount(): Int = lstVehicle.size

    override fun onBindViewHolder(viewHolder: JointViewHolder, postion: Int) {

        viewHolder.lblName.text = lstVehicle[postion].name

        viewHolder.lblName.setOnClickListener {
            onLocationQListener.onVehicleSelected(lstVehicle[postion])
        }

    }


    inner class JointViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        val lblName = itemView.lblName

    }

    fun setOnVehicleSelectedListener(onLocationQListener: OnLocationQListener) {
        this.onLocationQListener = onLocationQListener
    }

}