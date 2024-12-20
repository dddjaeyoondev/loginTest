package com.Refee.RefeeDB

import android.graphics.Color
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.Refee.RefeeDB.R
import com.google.firebase.firestore.FirebaseFirestore


class PostAdapter(private val posts: List<Post>) :
    RecyclerView.Adapter<PostAdapter.PostViewHolder>() {

    // ViewHolder
    class PostViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val postTitle: TextView = itemView.findViewById(R.id.post_title)
        val postBody: TextView = itemView.findViewById(R.id.post_body)
        val likeButton: ImageView = itemView.findViewById(R.id.like_button)
        val likeCount: TextView = itemView.findViewById(R.id.like_count)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PostViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_post, parent, false)
        return PostViewHolder(view)
    }

    override fun onBindViewHolder(holder: PostViewHolder, position: Int) {
        val post = posts[position]
        holder.postTitle.text = post.title
        holder.postBody.text = post.body

        // 초기 좋아요 개수
        var likeCount = 0
        holder.likeCount.text = likeCount.toString()

        // 좋아요 버튼 클릭 리스너
        holder.likeButton.setOnClickListener {
            likeCount++
            holder.likeCount.text = likeCount.toString()

            // 애니메이션 효과 (선택사항)
            holder.likeButton.setColorFilter(Color.RED) // 좋아요 버튼 색 변경
        }
    }

    override fun getItemCount(): Int = posts.size
}

//-------------------------------------------------------------------------

//
//class PostAdapter(private val posts: List<Post>) :
//    RecyclerView.Adapter<PostAdapter.PostViewHolder>() {
//
//    private val db = FirebaseFirestore.getInstance()
//
//    class PostViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
//        val postTitle: TextView = itemView.findViewById(R.id.post_title)
//        val postBody: TextView = itemView.findViewById(R.id.post_body)
//        val likeButton: ImageView = itemView.findViewById(R.id.like_button)
//        val likeCount: TextView = itemView.findViewById(R.id.like_count)
//    }
//
//    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PostViewHolder {
//        val view = LayoutInflater.from(parent.context)
//            .inflate(R.layout.item_post, parent, false)
//        return PostViewHolder(view)
//    }
//
//    override fun onBindViewHolder(holder: PostViewHolder, position: Int) {
//        val post = posts[position]
//
//        // Firestore의 posts 컬렉션에서 해당 문서 가져오기
//        val postRef = db.collection("posts").document(post.id)
//        postRef.get().addOnSuccessListener { document ->
//            if (document.exists()) {
//                val likeCount = document.getLong("likeCount")?.toInt() ?: 0
//                holder.likeCount.text = likeCount.toString()
//                post.likeCount = likeCount
//            }
//        }
//
//        // View에 데이터 설정
//        holder.postTitle.text = post.title
//        holder.postBody.text = post.body
//        holder.likeCount.text = post.likeCount.toString()
//
//        // 좋아요 버튼 클릭 이벤트 처리
//        holder.likeButton.setOnClickListener {
//            postRef.get().addOnSuccessListener { document ->
//                if (document.exists()) {
//                    val currentLikeCount = document.getLong("likeCount")?.toInt() ?: 0
//                    val updatedLikeCount = currentLikeCount + 1
//
//                    // Firestore에 좋아요 수 업데이트
//                    postRef.update("likeCount", updatedLikeCount)
//                        .addOnSuccessListener {
//                            holder.likeCount.text = updatedLikeCount.toString()
//                            holder.likeButton.setColorFilter(Color.RED) // 좋아요 색 변경
//                            post.likeCount = updatedLikeCount
//                        }
//                        .addOnFailureListener { e ->
//                            Log.e("FirestoreError", "Error updating likes", e)
//                        }
//                }
//            }
//        }
//    }
//
//    override fun getItemCount(): Int = posts.size
//}
