package com.meow.hungergames.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.google.android.material.chip.Chip;
import com.google.firebase.auth.FirebaseAuth;
import com.meow.hungergames.R;
import com.meow.hungergames.adapters.CommentsAdapter;
import com.meow.hungergames.adapters.RecipeAdapter;
import com.meow.hungergames.dao.CommentDao;
import com.meow.hungergames.dao.LikeDao;
import com.meow.hungergames.dao.PostDao;
import com.meow.hungergames.dao.SavedDao;
import com.meow.hungergames.dao.UserDao;
import com.meow.hungergames.databinding.ActivityLoginBinding;
import com.meow.hungergames.databinding.ActivityRecipeBinding;
import com.meow.hungergames.listeners.ItemClickListener;
import com.meow.hungergames.listeners.ItemClickWithMessage;
import com.meow.hungergames.models.Comment;
import com.meow.hungergames.models.ExpItem;
import com.meow.hungergames.models.Like;
import com.meow.hungergames.models.RecipePost;
import com.meow.hungergames.models.Saved;
import com.meow.hungergames.models.User;
import com.meow.hungergames.utilities.Constants;
import com.meow.hungergames.volley.RequestCallBack;
import com.meow.hungergames.volley.TempRequestCallBack;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.UUID;

public class RecipeActivity extends AppCompatActivity implements RequestCallBack, TempRequestCallBack {

    private ActivityRecipeBinding binding;
    private RecipeAdapter adapter;
    private CommentDao commentDao;
    private UserDao userDao;
    private LikeDao likeDao;
    private SavedDao savedDao;
    private PostDao postDao;
    private RecyclerView commentsRv;
    private CommentsAdapter commentsAdapter;
    private String name = "", img = "";
    private boolean liked;
    ArrayList<RecipePost> recipePosts = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityRecipeBinding.inflate(getLayoutInflater());

        commentDao = new CommentDao(RecipeActivity.this, this);
        postDao = new PostDao(RecipeActivity.this, this, this);
        userDao = new UserDao(RecipeActivity.this, this, this);
        likeDao = new LikeDao(RecipeActivity.this, this, this);
        savedDao = new SavedDao(RecipeActivity.this, this, this);

        View v = binding.getRoot();
        setContentView(v);
        Intent intent = getIntent();

        recipePosts.addAll(getIntent().getParcelableArrayListExtra(Constants.RECIPE_MODEL_KEY));

        int pos = intent.getIntExtra("position", 0);
        adapter = new RecipeAdapter(this, recipePosts, new ItemClickWithMessage() {
            @Override
            public void onItemClick(View view, int position, int check, String message) {
                switch (check) {
                    case Constants.COMMENT_CLICKED:
                        userDao.getUserComment(FirebaseAuth.getInstance().getUid(), position, message);
                        break;
                    case Constants.LIKE_CLICKED:
                        likeDao.checkIfLiked(recipePosts.get(position).getPostId(), FirebaseAuth.getInstance().getUid(), position, message);
                        break;
                    case Constants.SAVE_CLICKED:
                        savedDao.checkIfSaved(recipePosts.get(position).getPostId(), FirebaseAuth.getInstance().getUid(), position, message);
                        break;
                }
            }
        }, new ItemClickListener() {
            @Override
            public void onItemClick(View view, int position, int check) {
                switch (check) {
                    case Constants.VIEW_COMMENTS_CLICKED:
                        commentDao.getComments(recipePosts.get(position).getPostId());
                        break;
                    case Constants.VIEW_LIKES_CLICKED:
                        likeDao.getLikes(recipePosts.get(position).getPostId());
                        break;
                }
            }
        });
        binding.viewPagerRecipe.setAdapter(adapter);
        binding.viewPagerRecipe.setCurrentItem(pos);
    }

    private void showComments(ArrayList<Comment> comments) {
        View dialogView = getLayoutInflater().inflate(R.layout.comments_view, null);
        BottomSheetDialog dialog = new BottomSheetDialog(this);
        dialog.setContentView(dialogView);
        commentsRv = dialog.findViewById(R.id.comments_rv);
        TextView header = dialog.findViewById(R.id.header);
        header.setText(comments.size() + " Comments");
        commentsAdapter = new CommentsAdapter(this, comments);
        commentsRv.setLayoutManager(new LinearLayoutManager(this));
        commentsRv.setAdapter(commentsAdapter);
        dialog.show();
    }

    private void showLikes(ArrayList<Like> likes) {
        View dialogView = getLayoutInflater().inflate(R.layout.comments_view, null);
        BottomSheetDialog dialog = new BottomSheetDialog(this);
        dialog.setContentView(dialogView);
        commentsRv = dialog.findViewById(R.id.comments_rv);
        TextView header = dialog.findViewById(R.id.header);
        header.setText(likes.size() + " Likes");
        commentsAdapter = new CommentsAdapter(likes, this);
        commentsRv.setLayoutManager(new LinearLayoutManager(this));
        commentsRv.setAdapter(commentsAdapter);
        dialog.show();
    }

    @Override
    public void onRequestSuccessful(Object object, int check, boolean status) {
        switch (check) {
            case Constants.GET_COMMENTS_OF_POST:
                if (status) {
                    ArrayList<Comment> comments = new ArrayList<>((ArrayList<Comment>) object);
                    showComments(comments);
                }
                break;
            case Constants.GET_LIKES_OF_POST:
                if (status) {
                    ArrayList<Like> likes = new ArrayList<>((ArrayList<Like>) object);
                    showLikes(likes);
                }
                break;
        }
    }

    @Override
    public void onRequestTempSuccessful(Object object, int check, boolean status, int position, String message) {
        switch (check) {
            case Constants.GET_USER:
                if (status && message != null) {
                    User user = (User) object;
                    name = user.getUserName();
                    img = user.getUserImage();
                    Date date = new Date();
                    SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy hh:mm");
                    String hehe = dateFormat.format(date);
                    RecipePost post = recipePosts.get(position);
                    Comment comment = new Comment(
                            post.getPostId(),
                            UUID.randomUUID().toString().replaceAll("-", ""),
                            FirebaseAuth.getInstance().getUid(),
                            name,
                            img,
                            message,
                            hehe);
                    commentDao.saveComment(comment.getJSONObject());
                    postDao.getSinglePost(recipePosts.get(position).getPostId(), "addc");
                } else if (status) {
                    User user = (User) object;
                    name = user.getUserName();
                    img = user.getUserImage();
                    Date date = new Date();
                    SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy hh:mm");
                    String hehe = dateFormat.format(date);
                    RecipePost post = recipePosts.get(position);
                    Like like = new Like(
                            post.getPostId(),
                            FirebaseAuth.getInstance().getUid(),
                            name,
                            img,
                            hehe);
                    likeDao.saveLike(like.getJSONObject());
                    postDao.getSinglePost(recipePosts.get(position).getPostId(), "add");
                }
                break;
            case Constants.CHECK_LIKED:
                if (status) {
                    Like liked = (Like) object;
                    if (!liked.getUserId().equals("hehe")) {
                        likeDao.deleteLike(liked.getJSONObject(), recipePosts.get(position).getPostId(), FirebaseAuth.getInstance().getUid());
                        postDao.getSinglePost(recipePosts.get(position).getPostId(), "sub");
                        adapter.updateLiked(false);
                    } else {
                        userDao.getUserComment(FirebaseAuth.getInstance().getUid(), position, message);
                        adapter.updateLiked(true);
                    }
                }
                break;
            case Constants.CHECK_SAVED:
                if (status) {
                    Saved saved = (Saved) object;
                    if (!saved.getSavedBy().equals("hehe")) {
                        savedDao.deleteSave(saved.getJSONObject(), FirebaseAuth.getInstance().getUid(), recipePosts.get(position).getPostId());
                        adapter.updateSaved(false);
                    } else {
                        Date date = new Date();
                        SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy hh:mm");
                        String hehe = dateFormat.format(date);
                        Saved savedd = new Saved(recipePosts.get(position), FirebaseAuth.getInstance().getUid(), hehe);
                        savedDao.saveSaved(savedd.getJSONObject());
                        adapter.updateSaved(true);
                    }
                }
                break;
            case Constants.GET_SINGLE_POST:
                if (status) {
                    RecipePost recipePost = (RecipePost) object;
                    switch (message) {
                        case "add":
                            recipePost.setLikes(recipePost.getLikes() + 1);
                            break;
                        case "sub":
                            recipePost.setLikes(recipePost.getLikes() - 1);
                            break;
                        case "addc":
                            recipePost.setComments(recipePost.getComments() + 1);
                            break;
                    }
                    postDao.updatePost(recipePost.getJSONObject());
                }
                break;
        }
    }
}