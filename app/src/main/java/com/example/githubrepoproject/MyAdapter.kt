package com.example.githubrepoproject

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.accessibility.AccessibilityNodeInfo
import android.widget.Button
import android.widget.TextView
import androidx.core.view.iterator
import androidx.recyclerview.widget.RecyclerView

class MyAdapter(context: Context,gitHubList : List<GitHubInfo>) : RecyclerView.Adapter<MyAdapter.Holder>() {

    private val con = context
    private val gitHubRepoList = gitHubList
    private lateinit var interaction : Interaction

    class Holder(itemView: View,interaction: Interaction) : RecyclerView.ViewHolder(itemView)
    {
        val title = itemView.findViewById<TextView>(R.id.title)
        val description = itemView.findViewById<TextView>(R.id.description)
        val programmingLanguageText = itemView.findViewById<TextView>(R.id.programmingLanguageText)
        val starCount = itemView.findViewById<TextView>(R.id.starCount)
        val forkCount = itemView.findViewById<TextView>(R.id.forkCount)
        val button = itemView.findViewById<Button>(R.id.goToRepo)

        init {
            button.setOnClickListener {
                interaction.onBottomButtonClicked(adapterPosition)
            }
        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Holder {
        val view = LayoutInflater.from(con).inflate(R.layout.github_repo_cell,parent,false)
        return Holder(view,interaction)
    }

    override fun onBindViewHolder(holder: Holder, position: Int) {
        holder.description.text = gitHubRepoList[position].description
        holder.forkCount.text = gitHubRepoList[position].forks_count.toString()
        holder.starCount.text = gitHubRepoList[position].stargazers_count.toString()
        holder.title.text = gitHubRepoList[position].name
        holder.programmingLanguageText.text = gitHubRepoList[position].language
        disableAccessibilityForAllChildViews(holder.itemView as ViewGroup)
        setAccessibilityAction(holder.itemView,position)
    }


    override fun getItemCount(): Int {
        return gitHubRepoList.size
    }

    interface Interaction{
        fun onBottomButtonClicked(position: Int)
    }


    fun setInteraction(interaction: Interaction) {
        this.interaction = interaction
    }

    private fun disableAccessibilityForAllChildViews(view : ViewGroup) {

       // view.importantForAccessibility = View.IMPORTANT_FOR_ACCESSIBILITY_YES
        for(child in view) {
            child.importantForAccessibility = View.IMPORTANT_FOR_ACCESSIBILITY_NO
        }

    }

    private fun setAccessibilityAction(view : View,position: Int) {
        view.accessibilityDelegate = object : View.AccessibilityDelegate() {
            override fun onInitializeAccessibilityNodeInfo(
                host: View?,
                info: AccessibilityNodeInfo?
            ) {
                super.onInitializeAccessibilityNodeInfo(host, info)
                info?.addAction(AccessibilityNodeInfo.AccessibilityAction(R.id.accessibility_custom_action_1,"Click to see the repository"))
            }

            override fun performAccessibilityAction(
                host: View?,
                action: Int,
                args: Bundle?
            ): Boolean {
                if(action == R.id.accessibility_custom_action_1) {
                    interaction.onBottomButtonClicked(position)
                }
                return super.performAccessibilityAction(host, action, args)
            }
        }
    }
}

