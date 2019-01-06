package com.example.notify

import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import android.location.Location
import android.location.LocationListener
import android.location.LocationManager
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v4.content.ContextCompat
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.ListView
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.ChildEventListener
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices

class ChooseUserActivity : AppCompatActivity() {

    var chooseUserListView: ListView? = null
    var emails: ArrayList<String> = ArrayList()
    var keys: ArrayList<String> = ArrayList()
    /*internal var locationManager: LocationManager? = null
    internal var locationListener: LocationListener? = null
    private lateinit var fusedLocationClient: FusedLocationProviderClient*/


    /*override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<String>, grantResults: IntArray) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)


        if (grantResults.size > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {

            if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) !=
                    PackageManager.PERMISSION_GRANTED)
                locationManager?.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 0f, locationListener)
        }
    }*/

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_choose_user)

/*        locationManager = this.getSystemService(Context.LOCATION_SERVICE) as LocationManager

        locationListener = object : LocationListener {
            override fun onLocationChanged(location: Location) {
                Log.i("Location-Kabirwala-1", location.toString())

            }

            override fun onStatusChanged(provider: String, status: Int, extras: Bundle) {
                Log.i("Location-Kabirwala-2", provider)
            }

            override fun onProviderEnabled(provider: String) {
                Log.i("Location-Kabirwala-3", provider)
            }

            override fun onProviderDisabled(provider: String) {
                Log.i("Location-Kabirwala-4", provider)
            }
        }

        if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this,
                    arrayOf(Manifest.permission.ACCESS_FINE_LOCATION), 1)
        } else {
            locationManager?.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 0f, locationListener)
        }*/

        chooseUserListView = findViewById(R.id.chooseUserListView)
        val adapter= ArrayAdapter (this,android.R.layout.simple_list_item_1, emails)
        chooseUserListView?.adapter = adapter

            FirebaseDatabase.getInstance().getReference().child("users")
                    .addChildEventListener(object : ChildEventListener {

                        override fun onChildAdded(p0: DataSnapshot, p1: String?) {
                            val email = p0?.child("e-mail").value as String
                            val currUserEmail = FirebaseAuth.getInstance().currentUser!!.email!!

                            if(email != currUserEmail) {
                                emails.add(email)
                                keys.add(p0.key!!)
                            }
                            adapter.notifyDataSetChanged()
                        }

                        override fun onCancelled(p0: DatabaseError) {}
                        override fun onChildMoved(p0: DataSnapshot, p1: String?) {}
                        override fun onChildChanged(p0: DataSnapshot, p1: String?) {}
                        override fun onChildRemoved(p0: DataSnapshot) {}
                    })

        val imageName = intent.getStringExtra("imageName")
        val imageUrl = intent.getStringExtra("imageURL")
        val message = intent.getStringExtra("message")

        /*fusedLocationClient = LocationServices.getFusedLocationProviderClient(this)*/

//        val location = fusedLocationClient.lastLocation.addOnSuccessListener { location : Location? ->
//
//        }

        chooseUserListView?.onItemClickListener =
                AdapterView.OnItemClickListener { adapterView, view, i,l ->
            val snapMap:Map<String, String> =
                    mapOf("from" to FirebaseAuth.getInstance().currentUser!!.email!!,
                    "imageName" to imageName,
                    "imageURL" to imageUrl,
                    "message" to message,
                    "location" to "")

            FirebaseDatabase.getInstance().getReference()
                    .child("users").child(keys.get(i))
                    .child("post").push().setValue(snapMap)

            val intent = Intent(this, SnapsActivity::class.java)
            //intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
            startActivity(intent)
        }

    }
}
