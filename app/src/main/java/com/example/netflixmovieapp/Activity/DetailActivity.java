package com.example.netflixmovieapp.Activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.widget.NestedScrollView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.bumptech.glide.Glide;
import com.example.netflixmovieapp.Adapter.ImageListAdapter;
import com.example.netflixmovieapp.Domain.FilmItem;
import com.example.netflixmovieapp.R;
import com.google.android.material.imageview.ShapeableImageView;
import com.google.gson.Gson;

public class DetailActivity extends AppCompatActivity {
    private RequestQueue mRequestQueue;
    private StringRequest mStringRequest;
    private ProgressBar progressBar;
    private TextView titleText,movieRateText,movieTimeText,movieDateText,movieSummaryInfo,movieActorInfo;
    private NestedScrollView scrollView;
    private int idFilm;
    private ShapeableImageView pic1;
    private ImageView pic2,backImg;
    private RecyclerView.Adapter adapterImgList;
    private RecyclerView recyclerView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        idFilm = getIntent().getIntExtra("id",0);
        initView();
        sendRequest();
    }

    private void sendRequest() {
        mRequestQueue = Volley.newRequestQueue(this);
        progressBar.setVisibility(View.VISIBLE);
        scrollView.setVisibility(View.GONE);

        mStringRequest = new StringRequest(Request.Method.GET, "https://moviesapi.ir/api/v1/movies/"+idFilm, response -> {
            Gson gson = new Gson();
            progressBar.setVisibility(View.GONE);
            scrollView.setVisibility(View.VISIBLE);

            FilmItem item = gson.fromJson(response,FilmItem.class);

            Glide.with(DetailActivity.this)
                    .load(item.getPoster())
                            .into(pic1);

            Glide.with(DetailActivity.this)
                    .load(item.getPoster())
                    .into(pic2);

            titleText.setText(item.getTitle());
            movieRateText.setText(item.getRated());
            movieTimeText.setText(item.getRuntime());
            movieDateText.setText(item.getReleased());
            movieSummaryInfo.setText(item.getPlot());
            movieActorInfo.setText(item.getActors());
            if(item.getImages()!=null){
                adapterImgList = new ImageListAdapter(item.getImages());
                recyclerView.setAdapter(adapterImgList);
            }
        }, error -> {
            progressBar.setVisibility(View.GONE);
            Log.i("Xomoon Developer","error"+ error.toString());
        });
        mRequestQueue.add(mStringRequest);
    }

    private void initView() {
        titleText = findViewById(R.id.movieNameTV);
        progressBar = findViewById(R.id.detailLoading);
        scrollView = findViewById(R.id.scrollView3);
        pic1 = findViewById(R.id.posterNormalImg);
        pic2 = findViewById(R.id.posterBigImg);
        movieRateText = findViewById(R.id.movieRateTV);
        movieTimeText = findViewById(R.id.movieTimeTV);
        movieDateText = findViewById(R.id.movieDateTV);
        movieSummaryInfo = findViewById(R.id.movieSummaryinfoTV);
        movieActorInfo = findViewById(R.id.movieActorInfoTV);
        backImg = findViewById(R.id.backImg);
        recyclerView = findViewById(R.id.imagesRecycleView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL,false));

        backImg.setOnClickListener(v -> finish());
    }
}