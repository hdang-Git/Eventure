package com.example.hai.eventfinder;


import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v4.view.MenuItemCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.logging.Logger;

import io.github.luizgrp.sectionedrecyclerviewadapter.Section;
import io.github.luizgrp.sectionedrecyclerviewadapter.SectionedRecyclerViewAdapter;
import io.github.luizgrp.sectionedrecyclerviewadapter.StatelessSection;


/**
 * A simple {@link Fragment} subclass.
 */
public class SearchFragment extends Fragment implements View.OnClickListener, SearchView.OnQueryTextListener {

    private SectionedRecyclerViewAdapter sectionAdapter;
    FloatingActionButton fab;
    ArrayList<Integer> mSelectedItems;
    final String eventOptions[] = {"Event Name", "Location", "Price", "Time", "Type"};
    Logger log = Logger.getAnonymousLogger();

    public SearchFragment() {
        // Required empty public constructor
    }

    public static SearchFragment newInstance(){
        return new SearchFragment();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_search, container, false);

        //fill array list
        mSelectedItems = new ArrayList<>();
        //Set up floating action button for filter dialog box
        fab = (FloatingActionButton) v.findViewById(R.id.filterFab);
        fab.setOnClickListener(this);


        //Set up listview adapter
        sectionAdapter = new SectionedRecyclerViewAdapter();


        //TODO: add more section variety; loop around an array of different event types
        //Add section titles and divides the data into different sections
        List<Event> eventData = getEvents();
        if(eventData.size() > 0){
            Category category = new Category("Event", eventData);
            sectionAdapter.addSection(category);
        }


        RecyclerView recyclerView = (RecyclerView) v.findViewById(R.id.recyclerview);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.setAdapter(sectionAdapter);

        return v;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }

    @Override
    public void onResume() {
        super.onResume();

        if (getActivity() instanceof AppCompatActivity) {
            AppCompatActivity activity = ((AppCompatActivity) getActivity());
            if (activity.getSupportActionBar() != null)
                activity.getSupportActionBar().setTitle("Eventure");
        }
    }

    //Return back full list
    public List<Event> getEvents(){
        List<Event> e = new ArrayList<>();

        Event event1 = new Event.Builder("Blah")
                .setEventPriceString("$5")
                .setEventStartTime("7:00")
                .setEventLocation("Philadelphia")
                .build();

        Event event2 = new Event.Builder("what")
                .setEventPriceString("$10")
                .setEventStartTime("8:00")
                .build();

        log.info("builder " + event2);
        e.add(event1);
        e.add(event2);
        return e;
    }

    /*TODO: When making JSON request to populate arraylist in getEvents() method, modify url based on
     *TODO: what the user picks in the createDialogBox()
     */
    /*  This is a dialog box that gets created for options on what to search.
     *  The values are located in the array eventOptions[].
     *  mSelectedItems[] stores the index of the selected/chosen items of the eventOptions[] array
     */
    public void createDialogBox(View v){
        AlertDialog.Builder builder = new AlertDialog.Builder(v.getContext())
                .setTitle("Filter Options")
                .setMultiChoiceItems(eventOptions, null, new DialogInterface.OnMultiChoiceClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which, boolean isChecked) {
                        if (isChecked) {
                            // If the user checked the item, add it to the selected items
                            mSelectedItems.add(which);
                        } else if (mSelectedItems.contains(which)) {
                            // Else, if the item is already in the array, remove it
                            mSelectedItems.remove(Integer.valueOf(which));
                        }
                        log.info("mSelectedItems: " + mSelectedItems.toString());
                    }
                });

        builder.setPositiveButton("ok", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                // User clicked OK button
            }
        });
        builder.setNegativeButton("cancel", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                // User cancelled the dialog
            }
        });
        builder.create();
        builder.show();
    }

    //all on click events are registered here
    @Override
    public void onClick(View v) {
        switch(v.getId()){
            case R.id.filterFab:
                log.info("Filter Fab clicked");
                createDialogBox(v);
                break;
            default:
                log.info("Filter");
                break;

        }
    }

    //sets up the search bar in the toolbar only when this fragment is visible
    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.main, menu);

        final MenuItem item = menu.findItem(R.id.search);
        final SearchView searchView = (SearchView) MenuItemCompat.getActionView(item);
        searchView.setOnQueryTextListener(this);
    }

    //Goes through each section and filters each section for the query
    //Then calls the adapter to update the dataset and change the view
    @Override
    public boolean onQueryTextChange(String query) {

        // getSectionsMap requires library version 1.0.4+
        for (Section section : sectionAdapter.getSectionsMap().values()) {
            if (section instanceof FilterableSection) {
                ((FilterableSection)section).filter(query);
            }
        }
        sectionAdapter.notifyDataSetChanged();

        return true;
    }

    @Override
    public boolean onQueryTextSubmit(String query) {
        return false;
    }


    //Class is used to store different catagories of events in the future
    class Category extends StatelessSection implements FilterableSection {
        String title;
        List<Event> list;
        List<Event> filteredList;

        public Category(String title, List<Event> list) {
            super(R.layout.section_header_search, R.layout.item_search);

            this.title = title;
            this.list = list;
            this.filteredList = new ArrayList<>(list);
        }


        @Override
        public int getContentItemsTotal() {
            return filteredList.size();
        }

        @Override
        public RecyclerView.ViewHolder getItemViewHolder(View view) {
            return new ItemViewHolder(view);
        }

        @Override
        public void onBindItemViewHolder(RecyclerView.ViewHolder holder, int position) {
            final ItemViewHolder itemHolder = (ItemViewHolder) holder;

            Event event = filteredList.get(position);

            itemHolder.eventItem.setText(event.getEventName());
            //TODO: get actual pic and handle with Picasso
            itemHolder.imgItem.setImageResource(R.drawable.ic_event_note_black_24dp);
            itemHolder.eventPrice.setText(event.getEventPrice());
            itemHolder.eventTime.setText(event.getEventStartTime());
            itemHolder.eventLocation.setText(event.getEventLocation());

            //rootView gets list item and prints out toast when clicked on
            /*
            itemHolder.rootView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Toast.makeText(getContext(), String.format("Clicked on position #%s of Section %s", sectionAdapter.getSectionPosition(itemHolder.getAdapterPosition()), title), Toast.LENGTH_SHORT).show();
                }
            });
            */
        }

        @Override
        public RecyclerView.ViewHolder getHeaderViewHolder(View view) {
            return new HeaderViewHolder(view);
        }

        @Override
        public void onBindHeaderViewHolder(RecyclerView.ViewHolder holder) {
            HeaderViewHolder headerHolder = (HeaderViewHolder) holder;

            headerHolder.tvTitle.setText(title);
        }

        @Override
        public void filter(String query) {
            if (TextUtils.isEmpty(query)) {
                filteredList = new ArrayList<>(list);
                this.setVisible(true);
            } else {
                filteredList.clear();
                for (Event value : list) {
                    if (value.toString().toLowerCase().contains(query.toLowerCase())) {
                        filteredList.add(value);
                    }
                }
                this.setVisible(!filteredList.isEmpty());
            }
        }
    }

    interface FilterableSection {
        void filter(String query);
    }
}
