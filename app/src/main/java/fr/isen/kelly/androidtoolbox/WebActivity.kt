package fr.isen.kelly.androidtoolbox

import android.annotation.SuppressLint
import android.app.AlertDialog
import android.app.ProgressDialog
import android.graphics.Bitmap
import android.graphics.ColorSpace
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.ContactsContract
import android.util.Log
import android.view.View
import android.widget.*
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.Adapter
import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.VolleyError
import com.android.volley.toolbox.JsonArrayRequest
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import com.google.android.material.snackbar.Snackbar
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.activity_informations.*
import kotlinx.android.synthetic.main.activity_web.*
import kotlinx.android.synthetic.main.item_user.*
import okhttp3.Call
import okhttp3.Callback
import okhttp3.OkHttpClient
import okhttp3.*
import org.json.JSONObject
import java.lang.Exception
import java.net.HttpURLConnection
import java.net.URL
import org.json.JSONArray
import org.json.JSONException
import org.xml.sax.Parser
import java.io.*
import java.net.MalformedURLException
import java.net.ProtocolException
import java.util.logging.Level.parse
import java.util.HashMap


@Suppress("DEPRECATION")
class WebActivity : AppCompatActivity() {
    val userNameList = arrayListOf<User>()
    lateinit var response:String
    val TAG:String="test"
    var urlData:String="https://randomuser.me/api/"
    val startTime=System.currentTimeMillis()
    var listUsers = ArrayList<User>()
    lateinit var myTextView:TextView
    val usersList=ArrayList<User>()
    //val adapter = UsersAdapter(usersList)
    lateinit var recyclerView:RecyclerView
    lateinit var progressBar:ProgressBar
    lateinit var adapter:UsersAdapter
    //var photoUser:ImageView=findViewById(R.id.imageTest)
    //var recyclerView:RecyclerView=findViewById(R.id.listContactApi)

    private var jsonURL = "https://randomuser.me/api/"
    lateinit var gson: Gson
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_web)
        myTextView=findViewById(R.id.textViewTest)

        //val view = inflater.inflate(R.layout.fragment_list, container, false)
        val gsonBuilder = GsonBuilder()
        gsonBuilder.setDateFormat("M/d/yy hh:mm a")
        gson = gsonBuilder.create()

        //getUsers()
        displayUser()

    }


        /*listContactApi.setHasFixedSize(true)
        listContactApi.layoutManager = LinearLayoutManager(this)
        listContactApi.adapter = UsersAdapter(loadUrlData())
        val itemDecoration = DividerItemDecoration(this, DividerItemDecoration.VERTICAL)
        listContactApi.addItemDecoration(itemDecoration)*/



    // function for network call
    private fun getUsers():List<User> {

        // Instantiate the RequestQueue.
        val queue = Volley.newRequestQueue(this)

        // Request a string response from the provided URL.
        val stringReq = StringRequest(Request.Method.GET, urlData,
            Response.Listener<String> { response ->

                var strResp = response
                val jsonObj: JSONObject = JSONObject(strResp)
                val jsonArray: JSONArray = jsonObj.getJSONArray("results")
                var strUser: String = ""
                var myName: String = ""
                var myAddress=""
                var strLoc=""
                var strMail=""
                var myLoc=""
                var myPict=""
                var strPict=""
                //var picture:Bitmap

                for (i in 0 until jsonArray.length()) {
                    var jsonInner: JSONObject = jsonArray.getJSONObject(i)
                    strUser = strUser + "\n" + jsonInner.get("name")
                    strLoc= strLoc + jsonInner.get("location")
                    strMail=strMail+jsonInner.get("email")
                    strPict=strPict+jsonInner.get("picture")
                    val myJsonName:JSONObject= JSONObject(strUser)
                    myName += myJsonName.getString("first")+" "+myJsonName.getString("last")
                    val myJsonLoc:JSONObject=JSONObject(strLoc)
                    myLoc+=myJsonLoc.getString("street")
                    val myJsonStreet:JSONObject=JSONObject(myLoc)
                    myAddress += myJsonStreet.getString("number")+" "+myJsonStreet.getString("name")
                    val myJsonPict:JSONObject= JSONObject(strPict)
                    myPict+=myJsonPict.getString("medium")
                    Picasso.get().load(myPict).resize (180, 180).centerCrop ().into (imageTest)





                }
                userNameList.add(User(myName,strMail,myAddress))
                myTextView.text = "$myName \n $myAddress \n $strMail \n$myPict"
            },
            Response.ErrorListener { myTextView.text = "That didn't work!" })
        queue.add(stringReq)
        return userNameList
    }

    /*fun updateWithUrl(url: String) {
        Picasso.with(itemView.context).load(url).into(myImageView)
    }*/

    private fun displayUser() {
        listContactApi.layoutManager = LinearLayoutManager(this)
        listContactApi.adapter = UsersAdapter(getUsers())
        val itemDecoration = DividerItemDecoration(this, DividerItemDecoration.VERTICAL)
        listContactApi.addItemDecoration(itemDecoration)
    }




    private fun loadUrlData():List<User> {
        val stringRequest=StringRequest(Request.Method.GET,urlData,Response.Listener<String>{
            fun onResponse(response:String){
                try {
                    var jsonObject:JSONObject= JSONObject(response)
                    var array: JSONArray=jsonObject.getJSONArray("results")
                    for(i in 0 until array.length()){
                        var jo:JSONObject=array.getJSONObject(i)
                        var myUser:User=User(jo.getString("name"),jo.getString("email"),jo.getString("location"))
                        listUsers.add(myUser)
                        //textViewTest.text=myUser.toString()
                    }
                    //listContactApi.adapter=UsersAdapter(listUsers)
                }catch(e:Exception){
                    e.printStackTrace()
                }
            }
        },Response.ErrorListener {
            fun onErrorResponse(error: VolleyError){
                Toast.makeText(this,"Error"+error.toString(),Toast.LENGTH_SHORT).show()
            }
        })
        //val adapter=UsersAdapter(users))

        return listUsers
    }
}
