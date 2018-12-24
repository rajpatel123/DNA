package edu.com.medicalapp.Activities;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import butterknife.BindView;
import butterknife.ButterKnife;
import edu.com.medicalapp.R;

public class ShoppingActivity extends AppCompatActivity implements View.OnClickListener{


    @BindView(R.id.theory_books)
    Button theoryBooks;


    @BindView(R.id.mcqBooks)
    Button mcqBook;

    @BindView(R.id.f2fClasses)
    Button faceTofaceClass;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shopping);
        ButterKnife.bind(this);
        theoryBooks.setOnClickListener(this);
        mcqBook.setOnClickListener(this);
        faceTofaceClass.setOnClickListener(this);


        if (getSupportActionBar() != null) {

            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowHomeEnabled(true);
        }





    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        int id=item.getItemId();
        if(id==R.id.home)
        {
            finish();

        }
        return super.onOptionsItemSelected(item);
    }




    @Override
    public void onClick(View view) {
        switch (view.getId())
        {
            case R.id.theory_books:
                startActivity(new Intent(ShoppingActivity.this,TheorybookActivity.class));
                break;


            case R.id.mcqBooks:
                Toast.makeText(this,getString(R.string.coming_soon), Toast.LENGTH_SHORT).show();
                break;


            case R.id.f2fClasses:
                Toast.makeText(this,getString(R.string.coming_soon), Toast.LENGTH_SHORT).show();
                break;


        }
    }
}
