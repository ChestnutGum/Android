package fr.isen.kelly.androidtoolbox
import android.content.Context
import android.graphics.Color
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast


/**
 * A simple [Fragment] subclass.
 */
class FragmentCycle:Fragment(){
    var TAG2:String="activity_fragment"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Log.i(TAG2, "Fragment onCreate")
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_fragment_cycle,container,false)
        // Obtenir la référence du widget d'affichage de texte
        val tv = view.findViewById<TextView>(R.id.title)

        // Set a click listener for text view object
        tv.setOnClickListener{
            // Change la couleur du texte
            tv.setTextColor(Color.RED)
            // Montrer la confirmation du clic
            Toast.makeText(view.context,"TextView clicked.",Toast.LENGTH_SHORT).show()
        }
        // Return the fragment view/layout
        return view
    }
    //état onPause du fragment
    override fun onPause() {
        super.onPause()
        Log.i(TAG2, "Fragment onPause")
    }
    //état onAttach du fragment
    override fun onAttach(context: Context?) {
        super.onAttach(context)
        Log.i(TAG2, "Fragment onAttach")
    }
    //état onDestroy du fragment
    override fun onDestroy() {
        super.onDestroy()
        Log.i(TAG2, "Fragment onDestroy")
    }
    //état onDetach du fragment
    override fun onDetach() {
        super.onDetach()
        Log.i(TAG2, "Fragment onDetach")
    }
    //état onStart du fragment
    override fun onStart() {
        super.onStart()
        Log.i(TAG2, "Fragment onStart")
    }
    //état onStop du fragment
    override fun onStop() {
        super.onStop()
        Log.i(TAG2, "Fragment onStop")
    }
    //Affichage d'un message dans un fragment
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val view: TextView = getView()?.findViewById(R.id.texte) as TextView
        view.text = "Je suis un fragment"
    }
}












