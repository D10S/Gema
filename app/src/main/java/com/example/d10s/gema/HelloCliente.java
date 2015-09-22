package com.example.d10s.gema;

import android.app.ExpandableListActivity;
import android.content.res.Resources;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

/**
 * Created by D10S on 10/09/2015.
 */
public class HelloCliente extends ExpandableListActivity {
    //Initialize variables
    private static final String STR_CHECKED = " has Checked!";
    private static final String STR_UNCHECKED = " has unChecked!";
    private int ParentClickStatus=-1;
    private ArrayList<Producto> parents;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);

        Resources res = this.getResources();
        //Drawable devider = res.getDrawable(R.drawable.line);

        // Set ExpandableListView values

        getExpandableListView().setGroupIndicator(null);
        //getExpandableListView().setDivider(devider);
        getExpandableListView().setDividerHeight(1);
        registerForContextMenu(getExpandableListView());

        //Creating static data in arraylist
        final ArrayList<Producto> dummyList = buildDummyData();

        // Adding ArrayList data to ExpandableListView values
        loadHosts(dummyList);
    }

    /**
     * here should come your data service implementation
     * @return
     */
    private ArrayList<Producto> buildDummyData()
    {
        // Creating ArrayList of type parent class to store parent class objects
        final ArrayList<Producto> list = new ArrayList<Producto>();
        for (int i = 1; i < 4; i++)
        {
            //Create parent class object
            final Producto parent = new Producto();

            // Set values in parent class object
            if(i==1){
                parent.setName("" + i);
                parent.setText1("Parent 0");
                parent.setText2("Disable App On \nBattery Low");
            }
            else if(i==2){
                parent.setName("" + i);
                parent.setText1("Parent 1");
                parent.setText2("Auto disable/enable App \n at specified time");

            }
            else if(i==3){
                parent.setName("" + i);
                parent.setText1("Parent 1");
                parent.setText2("Show App Icon on \nnotification bar");

            }

            //Adding Parent class object to ArrayList
            list.add(parent);
        }
        return list;
    }


    public void loadHosts(final ArrayList<Producto> newParents)
    {
        if (newParents == null)
            return;

        parents = newParents;

        // Check for ExpandableListAdapter object
        if (this.getExpandableListAdapter() == null)
        {
            //Create ExpandableListAdapter Object
            final MyExpandableListAdapter mAdapter = new MyExpandableListAdapter() {
                @Override
                public int getChildrenCount(int groupPosition) {
                    return 0;
                }

                @Override
                public Object getChild(int groupPosition, int childPosition) {
                    return null;
                }

                @Override
                public long getChildId(int groupPosition, int childPosition) {
                    return 0;
                }

                @Override
                public View getChildView(int groupPosition, int childPosition, boolean isLastChild, View convertView, ViewGroup parent) {
                    return null;
                }

                @Override
                public boolean isChildSelectable(int groupPosition, int childPosition) {
                    return false;
                }
            };

            // Set Adapter to ExpandableList Adapter
            this.setListAdapter(mAdapter);
        }
        else
        {
            // Refresh ExpandableListView data
            ((MyExpandableListAdapter)getExpandableListAdapter()).notifyDataSetChanged();
        }
    }

    /**
     * A Custom adapter to create Parent view (Used grouprow.xml) and Child View((Used childrow.xml).
     */
    public abstract class MyExpandableListAdapter extends BaseExpandableListAdapter
    {
        private LayoutInflater inflater;

        public MyExpandableListAdapter()
        {
            // Create Layout Inflator
            inflater = LayoutInflater.from(HelloCliente.this);
        }


        // This Function used to inflate parent rows view

        @Override
        public View getGroupView(int groupPosition, boolean isExpanded,
                                 View convertView, ViewGroup parentView)
        {
            final Producto parent = parents.get(groupPosition);

            // Inflate grouprow.xml file for parent rows
            convertView = inflater.inflate(R.layout.hellocliente, parentView, false);

            // Get grouprow.xml file elements and set values
            ((TextView) convertView.findViewById(R.id.text1)).setText(parent.getText1());
            ((TextView) convertView.findViewById(R.id.text)).setText(parent.getText2());
            ImageView image=(ImageView)convertView.findViewById(R.id.image);

            image.setImageResource(
                    getResources().getIdentifier(
                            "com.androidexample.customexpandablelist:drawable/setting"+parent.getName(),null,null));

            ImageView rightcheck=(ImageView)convertView.findViewById(R.id.rightcheck);

            //Log.i("onCheckedChanged", "isChecked: "+parent.isChecked());

            // Change right check image on parent at runtime
            if(parent.isChecked()==true){
                rightcheck.setImageResource(
                        getResources().getIdentifier(
                                "com.androidexample.customexpandablelist:drawable/rightcheck",null,null));
            }
            else{
                rightcheck.setImageResource(
                        getResources().getIdentifier(
                                "com.androidexample.customexpandablelist:drawable/button_check",null,null));
            }

            // Get grouprow.xml file checkbox elements
            CheckBox checkbox = (CheckBox) convertView.findViewById(R.id.checkbox);
            checkbox.setChecked(parent.isChecked());

            // Set CheckUpdateListener for CheckBox (see below CheckUpdateListener class)
            checkbox.setOnCheckedChangeListener(new CheckUpdateListener(parent));

            return convertView;
        }

        @Override
        public Object getGroup(int groupPosition)
        {
            Log.i("Producto", groupPosition+"=  getGroup ");

            return parents.get(groupPosition);
        }

        @Override
        public int getGroupCount()
        {
            return parents.size();
        }

        //Call when parent row clicked
        @Override
        public long getGroupId(int groupPosition)
        {
            Log.i("Parent", groupPosition+"=  getGroupId "+ParentClickStatus);

            if(groupPosition==2 && ParentClickStatus!=groupPosition){

                //Alert to user
                Toast.makeText(getApplicationContext(), "Parent :"+groupPosition ,
                        Toast.LENGTH_LONG).show();
            }

            ParentClickStatus=groupPosition;
            if(ParentClickStatus==0)
                ParentClickStatus=-1;

            return groupPosition;
        }

        @Override
        public void notifyDataSetChanged()
        {
            // Refresh List rows
            super.notifyDataSetChanged();
        }

        @Override
        public boolean isEmpty()
        {
            return ((parents == null) || parents.isEmpty());
        }
        @Override
        public boolean hasStableIds()
        {
            return true;
        }

        @Override
        public boolean areAllItemsEnabled()
        {
            return true;
        }

        /******************* Checkbox Checked Change Listener ********************/

        private final class CheckUpdateListener implements CompoundButton.OnCheckedChangeListener
        {
            private final Producto parent;

            private CheckUpdateListener(Producto parent)
            {
                this.parent = parent;
            }
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked)
            {
                Log.i("onCheckedChanged", "isChecked: " + isChecked);
                parent.setChecked(isChecked);

                ((MyExpandableListAdapter)getExpandableListAdapter()).notifyDataSetChanged();

                final Boolean checked = parent.isChecked();
                Toast.makeText(getApplicationContext(),
                        "Parent : "+parent.getName() + " " + (checked ? STR_CHECKED : STR_UNCHECKED),
                        Toast.LENGTH_LONG).show();
            }
        }
        /***********************************************************************/

    }
}
