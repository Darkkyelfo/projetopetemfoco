package projetaobcc20172.com.projetopetemfoco.fragment;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

import projetaobcc20172.com.projetopetemfoco.R;
import projetaobcc20172.com.projetopetemfoco.adapter.PetAdapter;
import projetaobcc20172.com.projetopetemfoco.config.ConfiguracaoFirebase;
import projetaobcc20172.com.projetopetemfoco.model.Pet;

//Classe que monta um Fragmente (pedaço de tela para exibir os pets)
//Sua utilização é útil para dividir uma mesma tela em mais partes.
public class PetsFragment extends Fragment {

    private ArrayAdapter<Pet> mAdapter;
    private ArrayList<Pet> mPets;
    private DatabaseReference mFirebase;

    public PetsFragment() {
    }

    @Override
    public View onCreateView(final LayoutInflater inflater, final ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.activity_fragment, container, false);

        // Monta listview e adapter
        mPets = new ArrayList<>();
        ListView mListView;
        mListView = view.findViewById(R.id.lv_pets);
        mAdapter = new PetAdapter(getActivity(), mPets);
        mListView.setAdapter(mAdapter);

        //Listener que "ouve" o banco de dados
        ValueEventListener valueEventListenerPets = new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                mPets.clear();
                for (DataSnapshot dados : dataSnapshot.getChildren()) {
                    Pet pet = dados.getValue(Pet.class);
                    mPets.add(pet);
                }
                mAdapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                // vazio
            }
        };
        mFirebase.addValueEventListener(valueEventListenerPets);
        return view;
    }

}