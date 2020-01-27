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
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.OnLifecycleEvent


/**
 * A simple [Fragment] subclass.
 */
class FragmentCycle : Fragment() {
    var tag2: String = "activity_fragment"
    lateinit var startTextViewFrag: TextView


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Log.i(tag2, getString(R.string.fragment_onCreate))
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        //on récupère la vue à afficher
        val view = inflater.inflate(R.layout.fragment_fragment_cycle, container, false)
        // Obtenir la référence du widget d'affichage de texte
        val tv = view.findViewById<TextView>(R.id.title)

        // Set a click listener for text view object
        tv.setOnClickListener {
            // Change la couleur du texte
            tv.setTextColor(Color.RED)
            // Montrer la confirmation du clic
            Toast.makeText(view.context, getString(R.string.message_text_fragment), Toast.LENGTH_SHORT).show()
        }
        startTextViewFrag = view.findViewById<TextView>(R.id.texte)
        startTextViewFrag.text = getString(R.string.state_fragment_onCreate)
        // Return the fragment view/layout
        return view
    }

    //Etat onPause du fragment
    override fun onPause() {
        super.onPause()
        Log.i(tag2, getString(R.string.fragment_onPause))
    }

    //Etat onAttach du fragment
    override fun onAttach(context: Context?) {
        super.onAttach(context)
        Log.i(tag2, getString(R.string.fragment_onAttach))
    }

    //Etat onDestroy du fragment
    override fun onDestroy() {
        super.onDestroy()
        Log.i(tag2, getString(R.string.fragment_onDestroy))
    }

    //Etat onDetach du fragment
    override fun onDetach() {
        super.onDetach()
        Log.i(tag2, getString(R.string.fragment_onDetach))
    }
    //Etat onResume du fragment
    override fun onResume() {
        super.onResume()
        Log.i(tag2, getString(R.string.fragment_onResume))
        startTextViewFrag = view?.findViewById<TextView>(R.id.texte) as TextView
        startTextViewFrag.text = getString(R.string.state_fragment_onResume)
    }

    //état onStart du fragment
    override fun onStart() {
        super.onStart()
        Log.i(tag2, getString(R.string.fragment_onStart))
        startTextViewFrag = view?.findViewById<TextView>(R.id.texte) as TextView
        startTextViewFrag.text = getString(R.string.state_fragment_onStart)
    }

    //état onStop du fragment
    override fun onStop() {
        super.onStop()
        Log.i(tag2, getString(R.string.fragment_onStop))
    }
}












