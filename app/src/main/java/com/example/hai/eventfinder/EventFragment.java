package com.example.hai.eventfinder;


import android.app.Activity;
import android.app.SearchManager;
import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.SearchView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
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
    Menu eventMenu;

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
        listView.setTextFilterEnabled(true);

        //Report that this fragment would like to participate in populating the options menu by receiving a call to onCreateOptionsMenu()
        setHasOptionsMenu(true);
        /*
        MainActivity myParent = (MainActivity)getActivity();

        this.updateAdapter(myParent.getUserLocation().latitude , myParent.getUserLocation().longitude);
        */

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

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        eventMenu = menu;
        //Associate searchable configuration with the SearchView
        SearchManager searchManager = (SearchManager) getActivity().getSystemService(Context.SEARCH_SERVICE);
        SearchView searchView = (SearchView) menu.findItem(R.id.search).getActionView();
        searchView.setSearchableInfo(searchManager.getSearchableInfo(getActivity().getComponentName()));

        Log.d("EventFragSearch", "Event fragment menu retrieved");

        SearchView.OnQueryTextListener queryTextListener = new SearchView.OnQueryTextListener() {
            //Implement Search
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            //Implement Filter
            @Override
            public boolean onQueryTextChange(String newText) {
                adapter.filter(newText);
                adapter.notifyDataSetChanged();
                return false;
            }
        };
        searchView.setOnQueryTextListener(queryTextListener);
        super.onCreateOptionsMenu(menu, inflater);
    }
}
