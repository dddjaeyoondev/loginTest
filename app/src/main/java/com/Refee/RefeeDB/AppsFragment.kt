package com.Refee.RefeeDB

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.firestore.FirebaseFirestore

class AppsFragment : Fragment(R.layout.fragment_apps) {

    private val firestore: FirebaseFirestore by lazy { FirebaseFirestore.getInstance() }
    private lateinit var recyclerView: RecyclerView
    private val posts = mutableListOf<Post>()
    private lateinit var adapter: PostAdapter

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // RecyclerView 설정
        recyclerView = view.findViewById(R.id.recycler_view_posts)
        adapter = PostAdapter(posts) { post ->
            // 포스트 클릭 시 메시지 보내는 액티비티로 이동
            val intent = Intent(requireContext(), SendMessage::class.java)
            intent.putExtra("postId", post.id)
            intent.putExtra("postTitle", post.title)
            startActivity(intent)
        }
        recyclerView.layoutManager = LinearLayoutManager(requireContext())
        recyclerView.adapter = adapter

        // Firestore에서 데이터 가져오기
        fetchPosts()
    }

    private fun fetchPosts() {
        firestore.collection("posts")
            .orderBy("timestamp") // 시간순 정렬
            .get()
            .addOnSuccessListener { result ->
                posts.clear()
                for (document in result) {
                    val post = document.toObject(Post::class.java)
                    posts.add(post)
                }
                adapter.notifyDataSetChanged()
            }
            .addOnFailureListener {
                // 에러 처리
            }
    }
}