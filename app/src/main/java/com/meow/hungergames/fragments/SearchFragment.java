package com.meow.hungergames.fragments;

import static androidx.core.content.ContextCompat.startActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;

import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.meow.hungergames.R;
import com.meow.hungergames.activities.RecipeActivity;
import com.meow.hungergames.adapters.SearchAdapter;
import com.meow.hungergames.adapters.SearchRecipeAdapter;
import com.meow.hungergames.dao.PostDao;
import com.meow.hungergames.databinding.FragmentLoginBinding;
import com.meow.hungergames.databinding.FragmentSearchBinding;
import com.meow.hungergames.listeners.ItemClickListener;
import com.meow.hungergames.models.RecipePost;
import com.meow.hungergames.utilities.Constants;
import com.meow.hungergames.volley.RequestCallBack;
import com.meow.hungergames.volley.TempRequestCallBack;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class SearchFragment extends Fragment implements RequestCallBack {

    private FragmentSearchBinding binding;
    private SearchAdapter adapter;
    private SearchRecipeAdapter recipeAdapter;
    ArrayList<String> historyList;
    private ArrayList<RecipePost> queryPosts;
    private PostDao postDao;
    private int messageState;
    FirebaseFirestore firestore;
    FirebaseFirestore db;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentSearchBinding.inflate(inflater, container, false);

        postDao = new PostDao(getContext(), this);

        firestore = FirebaseFirestore.getInstance();
        db = FirebaseFirestore.getInstance();

        historyList = new ArrayList<>();
        queryPosts = new ArrayList<>();

        adapter = new SearchAdapter(getContext(), historyList, new ItemClickListener() {
            @Override
            public void onItemClick(View view, int position, int check) {
                switch (check) {
                    case Constants.RECIPE_ITEM_CLICKED:
                        recipeItemClicked(position);
                        break;
                    case Constants.HISTORY_CROSS_CLICKED:
                        deleteFromHistory(position);
                        break;
                }
            }
        });
        binding.searchRv.setLayoutManager(new LinearLayoutManager(getContext()));
        binding.searchRv.setAdapter(adapter);
        
        initialiseListeners();

        getHistoryList();
        showHistory();

        return binding.getRoot();
    }

    private void initialiseListeners() {
        binding.searchEditText.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (actionId == EditorInfo.IME_ACTION_SEARCH) {
                    InputMethodManager imgr = (InputMethodManager) getContext().getSystemService(Context.INPUT_METHOD_SERVICE);
                    imgr.hideSoftInputFromWindow(v.getRootView().getWindowToken(), 0);
                    binding.searchEditText.clearFocus();
                    performSearch();
                    return true;
                }
                return false;
            }
        });

        binding.searchBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                InputMethodManager imgr = (InputMethodManager) getContext().getSystemService(Context.INPUT_METHOD_SERVICE);
                imgr.hideSoftInputFromWindow(v.getRootView().getWindowToken(), 0);
                binding.searchEditText.clearFocus();
                performSearch();
            }
        });
    }

    private void performSearch() {
        String query = binding.searchEditText.getText().toString();
        if (query.trim().isEmpty()) {
            getHistoryList();
            showHistory();
        }
        else {
            showProgressBar();
            addToHistory();
            postDao.getPostQuery(query);
        }
    }

    private void showHistory() {
        showRecyclerView();
        binding.searchRv.setLayoutManager(new LinearLayoutManager(getContext()));
        binding.searchRv.setAdapter(adapter);
        binding.searchRv.clearFocus();
    }

    private void recipeItemClicked(int position) {
        DocumentReference document = firestore.collection("history").document(FirebaseAuth.getInstance().getUid());
        document.get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
            @Override
            public void onSuccess(DocumentSnapshot documentSnapshot) {
                if (documentSnapshot.exists()) {
                    ArrayList<String> temp = (ArrayList<String>) documentSnapshot.get("history");
                    Collections.reverse(temp);
                    binding.searchEditText.setText(temp.get(position));
                    performSearch();
                }
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
            }
        });
    }

    private void getHistoryList() {
        DocumentReference document = firestore.collection("history").document(FirebaseAuth.getInstance().getUid());
        document.get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
            @Override
            public void onSuccess(DocumentSnapshot documentSnapshot) {
                if (documentSnapshot.exists()) {
                    ArrayList<String> temp = (ArrayList<String>) documentSnapshot.get("history");
                    Collections.reverse(temp);
                    adapter.updateHistory(temp);
                }
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
            }
        });
    }

    private void deleteFromHistory(int position) {
        ArrayList<String> temp = new ArrayList<>();
        DocumentReference document = firestore.collection("history").document(FirebaseAuth.getInstance().getUid());
        document.get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
            @Override
            public void onSuccess(DocumentSnapshot documentSnapshot) {
                if (documentSnapshot.exists()) {
                    temp.addAll((ArrayList<String>) documentSnapshot.get("history"));
                    Collections.reverse(temp);
                    temp.remove(position);
                    Collections.reverse(temp);
                    firestore.collection("history").document(FirebaseAuth.getInstance().getUid())
                            .update("history", temp).addOnSuccessListener(new OnSuccessListener<Void>() {
                                @Override
                                public void onSuccess(Void unused) {
                                    Collections.reverse(temp);
                                    adapter.updateHistory(temp);
                                }
                            }).addOnFailureListener(new OnFailureListener() {
                                @Override
                                public void onFailure(@NonNull Exception e) {

                                }
                            });
                }
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
            }
        });
    }

    private void addToHistory() {
        List<String> temp = new ArrayList<>();
        DocumentReference document = firestore.collection("history").document(FirebaseAuth.getInstance().getUid());
        document.get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
            @Override
            public void onSuccess(DocumentSnapshot documentSnapshot) {
                if (documentSnapshot.exists()) {
                    temp.addAll((ArrayList<String>) documentSnapshot.get("history"));
                    String hehe = binding.searchEditText.getText().toString();
                    temp.remove(hehe);
                    temp.add(hehe);
                    firestore.collection("history").document(FirebaseAuth.getInstance().getUid())
                            .update("history", temp).addOnSuccessListener(new OnSuccessListener<Void>() {
                                @Override
                                public void onSuccess(Void unused) {

                                }
                            }).addOnFailureListener(new OnFailureListener() {
                                @Override
                                public void onFailure(@NonNull Exception e) {

                                }
                            });
                } else {
                    temp.add(binding.searchEditText.getText().toString());
                    Map<String, List<String>> mal = new HashMap<>();
                    mal.put("history", temp);
                    firestore.collection("history").document(FirebaseAuth.getInstance().getUid())
                            .set(mal).addOnSuccessListener(new OnSuccessListener<Void>() {
                                @Override
                                public void onSuccess(Void unused) {

                                }
                            }).addOnFailureListener(new OnFailureListener() {
                                @Override
                                public void onFailure(@NonNull Exception e) {

                                }
                            });
                }
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
            }
        });
    }

    private void showRecyclerView() {
        binding.rlNoInternetConnection.setVisibility(View.GONE);
        binding.rlNoSearchHistory.setVisibility(View.GONE);
        binding.rlNoSearchResultFound.setVisibility(View.GONE);
        binding.searchProgressbar.setVisibility(View.GONE);
        binding.searchRv.setVisibility(View.VISIBLE);
        messageState = Constants.SHOW_RECYCLER_VIEW;
    }

    private void showProgressBar() {
        binding.rlNoInternetConnection.setVisibility(View.GONE);
        binding.rlNoSearchHistory.setVisibility(View.GONE);
        binding.rlNoSearchResultFound.setVisibility(View.GONE);
        binding.searchProgressbar.setVisibility(View.VISIBLE);
        binding.searchRv.setVisibility(View.GONE);
        messageState = Constants.SHOW_PROGRESS_BAR;
    }

    private void showNoSearchResultFoundMessage() {
        binding.rlNoInternetConnection.setVisibility(View.GONE);
        binding.rlNoSearchHistory.setVisibility(View.GONE);
        binding.rlNoSearchResultFound.setVisibility(View.VISIBLE);
        binding.searchProgressbar.setVisibility(View.GONE);
        binding.searchRv.setVisibility(View.GONE);
        messageState = Constants.SHOW_NO_SEARCH_RESULT;
    }

    private void showNoInternetMessage() {
        binding.rlNoInternetConnection.setVisibility(View.VISIBLE);
        binding.rlNoSearchHistory.setVisibility(View.GONE);
        binding.rlNoSearchResultFound.setVisibility(View.GONE);
        binding.searchProgressbar.setVisibility(View.GONE);
        binding.searchRv.setVisibility(View.GONE);
        messageState = Constants.SHOW_NO_INTERNET_CONNECTION;
    }

    private void showNoSearchHistoryMessage() {
        binding.rlNoInternetConnection.setVisibility(View.GONE);
        binding.rlNoSearchHistory.setVisibility(View.VISIBLE);
        binding.rlNoSearchResultFound.setVisibility(View.GONE);
        binding.searchProgressbar.setVisibility(View.GONE);
        binding.searchRv.setVisibility(View.GONE);
        messageState = Constants.SHOW_NO_HISTORY;
    }

    @Override
    public void onResume() {
        super.onResume();
        binding.searchEditText.setText("");
    }

    @Override
    public void onRequestSuccessful(Object object, int check, boolean status) {
        switch (check) {
            case Constants.GET_QUERY_POST:
                if (status) {
                    queryPosts.clear();
                    queryPosts.addAll((ArrayList<RecipePost>) object);
                    recipeAdapter = new SearchRecipeAdapter(getContext(), queryPosts, new ItemClickListener() {
                        @Override
                        public void onItemClick(View view, int position, int check) {
                            switch (check) {
                                case Constants.SEARCHED_ITEM_CLICKED:
                                    startActivity(new Intent(getActivity(), RecipeActivity.class).putParcelableArrayListExtra(Constants.RECIPE_MODEL_KEY, queryPosts).putExtra("position", position));
                                    break;
                            }
                        }
                    });
                    recipeAdapter.updateQueryPost(queryPosts);
                    binding.searchRv.setAdapter(recipeAdapter);
                    binding.searchRv.setLayoutManager(new StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL));
                    showRecyclerView();
                    if (recipeAdapter.getItemCount() == 0) showNoSearchResultFoundMessage();
                }
                break;
        }
    }
}