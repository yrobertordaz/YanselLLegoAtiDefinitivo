package com.llegoati.llegoati.activities;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.SpannableStringBuilder;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.llegoati.llegoati.App;
import com.llegoati.llegoati.R;
import com.llegoati.llegoati.infrastructure.abstracts.IRepository;
import com.llegoati.llegoati.infrastructure.abstracts.IUserManager;
import com.llegoati.llegoati.infrastructure.concrete.RemoteRepository;
import com.llegoati.llegoati.infrastructure.concrete.utils.UtilsFunction;
import com.llegoati.llegoati.models.PartialSeller;
import com.llegoati.llegoati.models.UserConfiguration;
import com.llegoati.llegoati.smsmodulo.Utils.Constants;

import org.w3c.dom.Text;

import javax.inject.Inject;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.OnTextChanged;

public class SellerProfileActivity extends BaseActivity {

    String mId;

    @Inject
    IRepository mRepository;

    @Bind(R.id.seller_banner)
    ImageView mBanner;

    @Bind(R.id.seller_icon)
    ImageView mIcon;

    @Bind(R.id.seller_name)
    TextView mName;

    @Bind(R.id.ranking_text)
    TextView mRankingText;

    @Bind(R.id.seller_lastname)
    TextView mLastName;

    @Bind(R.id.seller_email)
    TextView mEmail;

    @Bind(R.id.seller_vip)
    TextView mVip;

    @Bind(R.id.seller_content)
    TextView mContent;

    @Bind(R.id.btn_send_responce)
    View btnSendMessage;

    @Bind(R.id.sms_responce)
    EditText mMessage;

    @Bind(R.id.letters_count)
    TextView countLetters;


    @SuppressLint("SetTextI18n")
    @OnTextChanged(R.id.sms_responce)
    public void textChange(SpannableStringBuilder letters){
        countLetters.setText(String.valueOf(letters.length())+"/150");
        if (letters.length()>150) {
            countLetters.setTextColor(getResources().getColor(R.color.redType));
            final String temp = letters.toString().substring(0,letters.length()-1);
            mMessage.setText(temp);
            mMessage.setSelection(temp.length());
        }
        else if (letters.length()>100 && letters.length()<150)
            countLetters.setTextColor(getResources().getColor(R.color.orange));
        else
            countLetters.setTextColor(getResources().getColor(R.color.black));
    }

    private Object context;


    @OnClick(R.id.btn_send_responce)
    public void setBtnSendMessage() {
        final String message = mMessage.getText().toString();
        mMessage.setText("");
        mMessage.setActivated(false);
        if (!message.isEmpty() && message.split(" ").length > 0) {

           /* AlertDialog.Builder mD = new AlertDialog.Builder(getContext());
            mD.setMessage(R.string.sms_message);
            mD.setPositiveButton("Entiendo", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {*/

              /*  if (!UtilsFunction.isOnline(getContext())) {
                    SendMessage.sendMessage(getContext(), userManager.user() != null ? userManager.user().getId() : "0000",
                            mSeller.getId(),
                            message);
                    Snackbar.make(btnSendMessage, R.string.sms_exito, Snackbar.LENGTH_LONG).show();
                    //mMessage.setFocusableInTouchMode(false);
                    // mMessage.setFocusable(false);
                    mMessage.setPressed(false);
                }else {*/
            mMessage.setPressed(false);
            AsyncTask mAsyncTask = new AsyncTask() {

                boolean responce = false;
                UserConfiguration mConfig;
                @Override
                protected void onPostExecute(Object o) {

                    //mMessage.setPressed(true);
                    mMessage.setActivated(true);
                    Snackbar.make(btnSendMessage, responce? R.string.sms_exito: R.string.sms_error , Snackbar.LENGTH_LONG).show();
                    super.onPostExecute(o);
                }

                @Override
                protected Object doInBackground(Object[] params) {


                 responce =   mRepository.sendMessageOnline(
                            userManager.user() != null ? userManager.user().getToken() : Constants.ID_NULL,
                            mSeller.getId(),"Le han enviado un mensaje",message

                    );
                    return null;
                }
            };

            mAsyncTask.execute();

           /*
                }  }
            });

            mD.setNegativeButton("Cancelar", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    mDia.cancel();
                    mDia.dismiss();
                }
            });

            mDia = mD.create();
            mDia.setCanceledOnTouchOutside(false);
            mDia.show();*/

        }else {
            Snackbar.make(btnSendMessage,"Error al enviar mensaje",Snackbar.LENGTH_LONG).show();
        }
    }

    @Inject
    IUserManager userManager;

    Dialog mDia;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_seller_profile);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Vendedor");

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        App.getInstance().getAppComponent().inject(this);
        ButterKnife.bind(this);

        mId = getIntent().getStringExtra("mId");

        mChargeData = new LoadSellersInformationAsync();
        mChargeData.execute();
        findViewById(R.id.layout_send).setVisibility(View.GONE);
        if (UtilsFunction.isOnline(this)){
                //// TODO: 8/19/2017 ver como es que capturo la informacion del usuario actual y si esta logueado


            AsyncTask mAsyncTask = new AsyncTask() {

                UserConfiguration mConfig;
                @Override
                protected void onPostExecute(Object o) {

                    if (mConfig!=null) {
                        ((TextView) findViewById(R.id.help_text)).setText(
                                !mConfig.CanSendFree
                                        ? getString(R.string.sms_1)+ mConfig.ValueToSendSms +" puntos)" //// TODO: 8/4/2017 buscar cuales son los N puntos
                                        : "(tiene " + mConfig.MaxMessajesAllowed + " SMS libre de costo)");
                    }

                    findViewById(R.id.layout_send).setVisibility(View.VISIBLE);
                    super.onPostExecute(o);
                }

                @Override
                protected Object doInBackground(Object[] params) {
                    mConfig = mRepository.getUserConfiguration(userManager.user() != null ? userManager.user().getId() : Constants.ID_NULL);
                    return null;
                }
            };

            if (userManager.user()!=null)
                mAsyncTask.execute();
            else {
                findViewById(R.id.layout_send).setVisibility(View.GONE);
            }



        }




    }

    @Override
    protected void onDestroy() {
        if(!mChargeData.isCancelled()){
            mChargeData.cancel(true);

        }
        super.onDestroy();
    }

    LoadSellersInformationAsync mChargeData;

    @Override
    protected void onStop() {
        mChargeData.cancel(true);

        super.onStop();
    }

    public void setIcon(String icon) {
        if (icon!=null)
                mIcon.setImageBitmap(RemoteRepository.getBitmap(icon));
    }

    public void setBanner(String icon) {
        if (icon!=null)
            mBanner.setImageBitmap(RemoteRepository.getBitmap(icon));
    }

    public Context getContext() {
        return SellerProfileActivity.this;
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == android.R.id.home){
            onBackPressed();
        }

        return true;
    }

    private class LoadSellersInformationAsync extends AsyncTask<Void, Void, Void> {




        public LoadSellersInformationAsync() {

        }

        @Override
        protected void onPreExecute() {


        }


        @Override
        protected void onPostExecute(Void aVoid) {
            setText(mName, mSeller.getName()!=null?mSeller.getName():"Dato no registrado");
            setText(mLastName, mSeller.getLastName()!=null?mSeller.getLastName():"Dato no registrado");
            setText(mEmail, mSeller.getEmail()!=null?mSeller.getEmail():"Dato no registrado");
            setText(mVip, mSeller.getVip()?getString(R.string.si):getString(R.string.no));
            setText(mContent, mSeller.getSpecification()!=null?mSeller.getSpecification():"Dato no registrado");
            setIcon(mSeller.getImage());
            setBanner(mSeller.getBanner());
            setText(mRankingText,String.valueOf(mRanking));
        }

        @Override
        protected Void doInBackground(Void... params) {



            mSeller = mRepository.getSeller(mId);
            mRanking = mRepository.getRankingSeller(mId);
            return null;
        }
    }
    PartialSeller mSeller;
    int mRanking;
    public void setText(TextView mView,String text){
        mView.setText(text);
    }

}
