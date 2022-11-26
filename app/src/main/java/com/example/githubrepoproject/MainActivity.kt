package com.example.githubrepoproject

import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
class MainActivity : AppCompatActivity() {

    val baseURL = " https://api.github.com/"
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setUpViewsAndPopulateData()
    }

    private fun setUpViewsAndPopulateData() {
        val recyclerView = findViewById<RecyclerView>(R.id.recyclerView)
        val title = findViewById<TextView>(R.id.title)
        val companyName = intent.extras?.getString("Company")
        title.text = companyName

        recyclerView.setHasFixedSize(true)
        recyclerView.layoutManager = LinearLayoutManager(this)

        val retrofit = Retrofit.Builder().
                       baseUrl(baseURL).
                       addConverterFactory(GsonConverterFactory.create()).
                       build()

        val jsonResponseForGitHubInfo : JSONResponseForGitHubInfo = retrofit.create(JSONResponseForGitHubInfo::class.java)
        val call : Call<List<GitHubInfo>> = jsonResponseForGitHubInfo.getGitHubInfo(companyName!!)

        call.enqueue(object : Callback<List<GitHubInfo>> {
            override fun onResponse(
                call: Call<List<GitHubInfo>>,
                response: Response<List<GitHubInfo>>
            ) {
                if(!response.isSuccessful) {
                    Toast.makeText(baseContext,response.message(),Toast.LENGTH_SHORT).show()
                    return
                }

                val gitHubInfoList = response.body()
                val adapter = MyAdapter(baseContext,gitHubInfoList!!)
                adapter.setInteraction(object : MyAdapter.Interaction {
                    override fun onBottomButtonClicked(position: Int) {
                        val url = gitHubInfoList.get(position).html_url
                        val intent = Intent(Intent.ACTION_VIEW)
                        intent.setData(Uri.parse(url))
                        startActivity(intent)
                    }
                })
                recyclerView.adapter = adapter
            }

            override fun onFailure(call: Call<List<GitHubInfo>>, t: Throwable) {
               Toast.makeText(baseContext,t.message,Toast.LENGTH_SHORT).show()
            }

        })
    }
}