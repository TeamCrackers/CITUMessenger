package com.cebuinstituteoftechnology_university.citumessenger;

import android.app.Fragment;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.cebuinstituteoftechnology_university.citumessenger.Adapters.InboxAdapter;
import com.cebuinstituteoftechnology_university.citumessenger.Models.Conversation;
import com.cebuinstituteoftechnology_university.citumessenger.Models.User;

import java.util.Date;

import butterknife.Bind;
import butterknife.ButterKnife;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link InboxFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link InboxFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class InboxFragment extends android.support.v4.app.Fragment {
    @Bind(R.id.inbox_list)
    RecyclerView inboxList;
    InboxAdapter inboxAdapter;
    RecyclerView.LayoutManager inboxManager;
    public InboxFragment() {

        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_inbox, container, false);
        ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        inboxList.setHasFixedSize(true);
        inboxManager = new LinearLayoutManager(view.getContext());
        inboxList.setLayoutManager(inboxManager);
        inboxAdapter = new InboxAdapter();
        inboxList.setAdapter(inboxAdapter);
        super.onViewCreated(view, savedInstanceState);
    }
}
