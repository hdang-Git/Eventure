package com.example.hai.eventfinder;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import com.ramotion.foldingcell.FoldingCell;

import java.util.ArrayList;
import java.util.List;


/**
 * A simple {@link Fragment} subclass.
 */
public class EventFragment extends Fragment {
    ListView listView;

    //Event myEvent = new Event();
    //Event myEvent2 = new Event();


    //This is a test to see if my branch is working propoerly

    public EventFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_event, container, false);
        //final ArrayList<EventItem> arrayList = EventItem.getTest();
        final ArrayList<Event> arrayList = new ArrayList<Event>();

        //This will be looped in the future
        //Use the same myEvent but pass different int to drill JSON differently
        /*
        myEvent.requestEvent(0);
        arrayList.add(myEvent);
        myEvent2.requestEvent(1);
        arrayList.add(myEvent2);
        */

        for(int i = 0;i<10;i++) {
            arrayList.add(new Event(i));
        }

        /*
        final FoldingCell fc = (FoldingCell) v.findViewById(R.id.folding_cell);
        fc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                fc.toggle(false);
            }
        });
        */

        listView = (ListView) v.findViewById(R.id.listView);
        final myFoldingCellListAdapter adapter = new myFoldingCellListAdapter(v.getContext(), 0, arrayList);
        listView.setAdapter(adapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                // toggle clicked cell state
                ((FoldingCell) view).toggle(false);
                // register in adapter that state for selected cell is toggled
                adapter.registerToggle(position);

            }
        });
        return v;
    }

}
