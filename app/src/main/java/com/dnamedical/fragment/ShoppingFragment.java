package com.dnamedical.fragment;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import butterknife.BindView;
import butterknife.ButterKnife;
import com.dnamedical.R;

public class ShoppingFragment extends Fragment implements View.OnClickListener{

    @BindView(R.id.mcqBooks)
    Button mcqBooks;


    @BindView(R.id.theory_books)
    Button theoryBooks;


    @BindView(R.id.f2fClasses)
    Button face2faceclasses;




    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view= inflater.inflate(R.layout.shopping_fragment,container,false);

        ButterKnife.bind(this,view);
      //  mcqBooks.setOnClickListener(this);
        theoryBooks.setOnClickListener(this);
       // face2faceclasses.setOnClickListener(this);


        return view;


    }

    @Override
    public void onClick(View view) {

        Fragment selectedFragment=null;

        switch (view.getId())
        {
            case R.id.f2fClasses:
                Toast.makeText(getContext(), getString(R.string.coming_soon), Toast.LENGTH_SHORT).show();

            case R.id.theory_books:
                selectedFragment=new TheoryFragment();


            case R.id.mcqBooks:
                Toast.makeText(getContext(),getString(R.string.coming_soon), Toast.LENGTH_SHORT).show();

        }

       // getFragmentManager().beginTransaction().replace(R.id.fraigment_container,selectedFragment).addToBackStack(null).commit();


    }
}
