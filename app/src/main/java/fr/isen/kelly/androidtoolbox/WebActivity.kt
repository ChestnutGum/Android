package fr.isen.kelly.androidtoolbox
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.*
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import kotlinx.android.synthetic.main.activity_web.*
import org.json.JSONObject
import org.json.JSONArray


@Suppress("DEPRECATION")
class WebActivity : AppCompatActivity() {
    val userNameList= arrayListOf<User>()
    var urlData:String="https://randomuser.me/api/"
    lateinit var myTextView:TextView
    lateinit var gson: Gson

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_web)

        val gsonBuilder = GsonBuilder()
        gsonBuilder.setDateFormat("M/d/yy hh:mm a")
        gson = gsonBuilder.create()
        listContactApi.layoutManager = LinearLayoutManager(this)
        val itemDecoration = DividerItemDecoration(this, DividerItemDecoration.VERTICAL)
        listContactApi.addItemDecoration(itemDecoration)
        //pour afficher 10 contacts
        for (i in 0 until 10) {
            getUsers()
        }

    }


    private fun getUsers():ArrayList<User> {
        // Instantiate the RequestQueue.
        val queue = Volley.newRequestQueue(this)
        var strUser = ""
        var myName = ""
        var myAddress=""
        var strLoc=""
        var strMail=""
        var myLoc=""
        var myPict=""
        var strPict=""
        // Request a string response from the provided URL.
        val stringReq = StringRequest(Request.Method.GET, urlData,
            Response.Listener<String> { response ->
                val strResp = response
                val jsonObj = JSONObject(strResp)
                val jsonArray: JSONArray = jsonObj.getJSONArray("results")

                for (i in 0 until jsonArray.length()) {
                    val jsonInner: JSONObject = jsonArray.getJSONObject(i)
                    strUser = strUser + "\n" + jsonInner.get("name")
                    strLoc += jsonInner.get("location")
                    strMail += jsonInner.get("email")
                    strPict += jsonInner.get("picture")
                    val myJsonName= JSONObject(strUser)
                    myName += myJsonName.getString("first")+" "+myJsonName.getString("last")
                    val myJsonLoc=JSONObject(strLoc)
                    myLoc+=myJsonLoc.getString("street")
                    val myJsonStreet=JSONObject(myLoc)
                    myAddress += myJsonStreet.getString("number")+" "+myJsonStreet.getString("name")+", "+myJsonLoc.getString("postcode")+" "+myJsonLoc.getString("city")
                    val myJsonPict= JSONObject(strPict)
                    myPict+=myJsonPict.getString("medium")
                    //affichage dans le recycler view
                    userNameList.add(User(myName,strMail,myAddress,myPict))
                    listContactApi.adapter = UsersAdapter(userNameList)
                }
            },
            Response.ErrorListener { myTextView.text = getString(R.string.text_error) })
        queue.add(stringReq)
        return userNameList
    }
}














