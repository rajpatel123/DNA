package com.dnamedical.fragment;

import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;


import com.dnamedical.R;

public class ShoppingFragment extends Fragment implements View.OnClickListener{

   // @BindView(R.id.mcqBooks)
    Button mcqBooks;


    //@BindView(R.id.theory_books)
    Button theoryBooks;


    //@BindView(R.id.f2fClasses)
    Button face2faceclasses;




    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view= inflater.inflate(R.layout.shopping_fragment,container,false);

        //ButterKnife.bind(this,view);
        theoryBooks=view.findViewById(R.id.theory_books);
        mcqBooks=view.findViewById(R.id.mcqBooks);
        face2faceclasses=view.findViewById(R.id.f2fClasses);

      //  mcqBooks.setOnClickListener(this);
        theoryBooks.setOnClickListener(this);
       // face2faceclasses.setOnClickListener(this);


        return view;


    }

    @Override
    public void onClick(View view) {

        Fragment selectedFragment=null;

        if (view.getId()==R.id.f2fClasses) {
            Toast.makeText(getContext(), getString(R.string.coming_soon), Toast.LENGTH_SHORT).show();
        } else if (view.getId()==R.id.theory_books) {
            selectedFragment = new TheoryFragment();


        }else if (view.getId()==R.id.mcqBooks){
                Toast.makeText(getContext(),getString(R.string.coming_soon), Toast.LENGTH_SHORT).show();

        }

       // getFragmentManager().beginTransaction().replace(R.id.fraigment_container,selectedFragment).addToBackStack(null).commit();


    }
}
