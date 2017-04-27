package com.example.hai.eventfinder;


import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.util.Log;
import android.util.EventLog;

import com.ramotion.foldingcell.FoldingCell;

import java.util.ArrayList;
import java.util.List;


/**
 * A simple {@link Fragment} subclass.
 */
public class EventFragment extends Fragment {

    ListView listView;
    ArrayList<Event> arrayList;
    myFoldingCellListAdapter adapter;

    public EventFragment() {
        // Required empty public constructor
    }

    public static EventFragment newInstance(){
        return new EventFragment();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_event, container, false);
        //final ArrayList<Event> arrayList = new ArrayList<Event>();
        arrayList = new ArrayList<Event>();


        listView = (ListView) v.findViewById(R.id.listView);
        //final myFoldingCellListAdapter adapter = new myFoldingCellListAdapter(v.getContext(), 0, arrayList);
        Activity mainActivity = getActivity();
        adapter = new myFoldingCellListAdapter(v.getContext(), 0, arrayList , mainActivity);
        listView.setAdapter(adapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Log.d("I've been toggled" , "true");
                // toggle clicked cell state
                ((FoldingCell) view).toggle(false);
                // register in adapter that state for selected cell is toggled
                adapter.registerToggle(position);

            }
        });
        listView.setRecyclerListener(ViewHolder.mRecycleListener);

        /*
        ASYNCparams eventArgs = new ASYNCparams(0 , arrayList , this.getContext() , adapter );
        EventRequestAsyncTask BriteRequest = new EventRequestAsyncTask();
        BriteRequest.execute(eventArgs);
        */

        return v;
    }

    public void updateAdapter(double lat , double lon) {

        ASYNCparams eventArgs = new ASYNCparams(0, arrayList, this.getContext(), adapter , lat, lon);
        EventRequestAsyncTask BriteRequest = new EventRequestAsyncTask(this.getContext());

        UserEventAsyncTask UserRequest = new UserEventAsyncTask(this.getContext() , BriteRequest);

        UserRequest.execute(eventArgs);

        //BriteRequest.execute(eventArgs);
    }
}
