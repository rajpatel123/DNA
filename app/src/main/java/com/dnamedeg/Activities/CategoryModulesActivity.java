package com.dnamedeg.Activities;

import android.content.Intent;
import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.dnamedeg.Adapters.CourseModuleListAdapter;
import com.dnamedeg.Models.maincat.CategoryDetailData;
import com.dnamedeg.Models.maincat.Detail;
import com.dnamedeg.Models.modulesforcat.CatModuleResponse;
import com.dnamedeg.R;
import com.dnamedeg.Retrofit.RestClient;
import com.dnamedeg.utils.Constants;
import com.dnamedeg.utils.DnaPrefs;
import com.dnamedeg.utils.Utils;
import com.google.gson.Gson;

import butterknife.BindView;
import butterknife.ButterKnife;
import okhttp3.MediaType;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CategoryModulesActivity extends AppCompatActivity  {

    @BindView(R.id.image)
    ImageView image;

    @BindView(R.id.subjectName)
    TextView subjectName;

    @BindView(R.id.videosRL)
    RelativeLayout videosRL;

    @BindView(R.id.notesRl)
    RelativeLayout notesRl;

    @BindView(R.id.eBookRl)
    RelativeLayout eBookRl;

    MainActivity mainActivity;

    private CatModuleResponse catModuleResponse;
    private CategoryDetailData categoryDetailData;
    private String catId;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_category_modules);


        catId = getIntent().getStringExtra("catId");
        categoryDetailData = new Gson().fromJson(getIntent().getStringExtra("catData"), CategoryDetailData.class);


//        if (getSupportActionBar() != null) {
//
//            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
//            getSupportActionBar().setDisplayShowHomeEnabled(true);
//
//            for (Detail detail:categoryDetailData.getDetails()){
//                if (detail.getCatId().equalsIgnoreCase(catId)){
//                    getSupportActionBar().setTitle(detail.getCatName());
//                    break;
//
//                }
//            }
//        }

        ButterKnife.bind(this);
       // getCourse();

        if (getIntent().hasExtra("image")){
            Glide.with(this)
                    .load(getIntent().getStringExtra("image"))
                    .into(image);


        }

        getSupportActionBar().setTitle(getIntent().getStringExtra("SubCategoryName"));
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        subjectName.setText(getIntent().getStringExtra("SubCategoryName"));


        videosRL.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(CategoryModulesActivity.this, VideoActivity.class);
                intent.putExtra("SubCategoryName",getIntent().getStringExtra("SubCategoryName"));
                intent.putExtra("image",getIntent().getStringExtra("image"));
                intent.putExtra("subCatId",getIntent().getStringExtra("subCatId"));
                intent.putExtra("discountonfullpurchase",80);
                intent.putExtra(Constants.CONTENT_TYPE,1);
                startActivity(intent);
            }
        });

        notesRl.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(CategoryModulesActivity.this, VideoActivity.class);
                intent.putExtra("SubCategoryName",getIntent().getStringExtra("SubCategoryName"));
                intent.putExtra("image",getIntent().getStringExtra("image"));
                intent.putExtra("subCatId",getIntent().getStringExtra("subCatId"));
                intent.putExtra("discountonfullpurchase",80);
                intent.putExtra(Constants.CONTENT_TYPE,2);
                startActivity(intent);
            }
        });

        eBookRl.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(CategoryModulesActivity.this, VideoActivity.class);
                intent.putExtra("SubCategoryName",getIntent().getStringExtra("SubCategoryName"));
                intent.putExtra("image",getIntent().getStringExtra("image"));
                intent.putExtra("subCatId",getIntent().getStringExtra("subCatId"));
                intent.putExtra("discountonfullpurchase",80);
                intent.putExtra(Constants.CONTENT_TYPE,3);
                startActivity(intent);
            }
        });


    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        onBackPressed();
        return super.onOptionsItemSelected(item);
    }
}
