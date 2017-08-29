package com.llegoati.llegoati.smsmodulo.View;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.text.SpannableStringBuilder;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;

import com.llegoati.llegoati.R;
import com.llegoati.llegoati.smsmodulo.Adapters.AdapterSmsMain;
import com.llegoati.llegoati.smsmodulo.Infraestructure.concrete.Repository;
import com.llegoati.llegoati.smsmodulo.Models.SmsMessage;


import com.llegoati.llegoati.smsmodulo.Utils.Constants;

import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnTextChanged;


public class SmsMainActivity extends AppCompatActivity {



    ViewPager mViewPager;




    TabLayout tabLayout;
    public static AdapterSmsMain mAdaptNew;
    public static AdapterSmsMain mAdaptSend;
    public static AdapterSmsMain mAdaptTrash;

    static Repository mRepository;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sms_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        mViewPager = (ViewPager) findViewById(R.id.container);
        tabLayout = (TabLayout) findViewById(R.id.sms_tabs);

        mRepository = new Repository(getContext());

        getSupportActionBar().setTitle("Historial de mensajes");

        boolean isNew  = getIntent().getBooleanExtra(Constants.EXTRA_IS_NEW,false);

        if (isNew){

            int mIdSms = getIntent().getIntExtra("sms_id",0);
            Intent mIntent = new Intent(this,SmsHistoryActivity.class);
            mIntent.putExtra("sms_id",mIdSms);
            startActivity(mIntent);
/*

            String contenido = getIntent().getStringExtra(Constants.EXTRA_CONTENT);
            char[] mContent = contenido.toCharArray();
            int mIDFlujo = Integer.parseInt(String.valueOf(mContent[Constants.POSITION_FLUJO]));
            //// TODO: 4/1/2017 ver como coje el id mio
            String mId = "n";
            switch (mIDFlujo){
                case Constants.FLUJO_CLIENTE_VENDEDOR:{
                    String message = contenido.substring(Constants.FLUJO_CLIENTE_VENDEDOR_START_SMS,contenido.length());
                    String idMessage = contenido.substring(1,7);
                    String idClient = idMessage.substring(0,4);

                    saveClientePetition(idMessage,idClient,mId,message);

                    break;
                }
                case Constants.FLUJO_VENDEDOR_CLIENTE:{

                    break;
                }
            }
            */

        }


        List<SmsMessage> mNews = mRepository.getInbox();
        mAdaptNew = new AdapterSmsMain(mNews);

        List<SmsMessage> mSend = mRepository.getResponces();
        mAdaptSend = new AdapterSmsMain(mSend);

        List<SmsMessage> mTrash = mRepository.getTrash();
        mAdaptTrash = new AdapterSmsMain(mTrash);

        SectionsPagerAdapter mSectionsPagerAdapter = new SectionsPagerAdapter(getSupportFragmentManager());
        mViewPager.setAdapter(mSectionsPagerAdapter);
        tabLayout.setupWithViewPager(mViewPager);
    }

    private void saveClientePetition(String idMessage, String idClient, String mId, String message) {

        int mIdSms = (int) mRepository.insertNewSms(idMessage,message,idClient,mId);

        Intent mIntent = new Intent(this,SmsHistoryActivity.class);
        mIntent.putExtra("sms_id",mIdSms);
        startActivity(mIntent);


    }

    private Context getContext() {
        return SmsMainActivity.this;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        //getMenuInflater().inflate(R.menu.menu_sms_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }


    public class SectionsPagerAdapter extends FragmentPagerAdapter {

        public SectionsPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            // getItem is called to instantiate the fragment for the given page.
            // Return a PlaceholderFragment (defined as a static inner class below).
            return PlaceholderFragment.newInstance(position + 1);
        }

        @Override
        public int getCount() {
            // Show 3 total pages.
            return 3;
        }

        @Override
        public CharSequence getPageTitle(int position) {
            switch (position) {
                case 0:
                    return "Entrada";
                case 1:
                    return "Enviados";
                case 2:
                    return "Papelera";

            }
            return null;
        }
    }

    public static class PlaceholderFragment extends Fragment implements View.OnClickListener  {
        /**
         * The fragment argument representing the section number for this
         * fragment.
         */
        private static final String ARG_SECTION_NUMBER = "section_number";
        private static final String ARG_ACTIVITY = "mContext";


        public PlaceholderFragment() {



        }

        /**
         * Returns a new instance of this fragment for the given section
         * number.
         */
        public static PlaceholderFragment newInstance(int sectionNumber) {
            PlaceholderFragment fragment = new PlaceholderFragment();
            Bundle args = new Bundle();
            args.putInt(ARG_SECTION_NUMBER, sectionNumber);
            fragment.setArguments(args);
            return fragment;
        }

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                                 Bundle savedInstanceState) {
            View rootView = null;

            rootView = inflater.inflate(R.layout.layout_home, container, false);
            RecyclerView mSmsList = (RecyclerView) rootView.findViewById(R.id.sms_content);
            mSmsList.setLayoutManager(new GridLayoutManager(getContext(),1));

            switch (getArguments().getInt(ARG_SECTION_NUMBER)){
                case 1:


                    mSmsList.setAdapter(mAdaptNew);

                    break;
                case 2:

                    mSmsList.setAdapter(mAdaptSend);

                    break;
                case 3:

                    mSmsList.setAdapter(mAdaptTrash);

                    break;
            }

            return rootView;
        }




        @Override
        public void onClick(View v) {



        }
    }
}
