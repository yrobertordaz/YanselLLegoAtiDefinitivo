package com.llegoati.llegoati.smsmodulo.View;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.AsyncTask;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.SpannableStringBuilder;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.llegoati.llegoati.App;
import com.llegoati.llegoati.R;
import com.llegoati.llegoati.explorer.PreferencesFactory;
import com.llegoati.llegoati.infrastructure.abstracts.IRepository;
import com.llegoati.llegoati.infrastructure.abstracts.IUserManager;
import com.llegoati.llegoati.infrastructure.concrete.utils.UtilsFunction;
import com.llegoati.llegoati.models.PartialSeller;
import com.llegoati.llegoati.models.UserConfiguration;
import com.llegoati.llegoati.smsmodulo.Infraestructure.concrete.Repository;
import com.llegoati.llegoati.smsmodulo.Models.SmsMessage;


import com.llegoati.llegoati.smsmodulo.SendMessage;
import com.llegoati.llegoati.smsmodulo.Utils.Constants;


import java.util.List;

import javax.inject.Inject;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.OnTextChanged;

import static com.llegoati.llegoati.smsmodulo.View.SmsMainActivity.mAdaptSend;
import static com.llegoati.llegoati.smsmodulo.View.SmsMainActivity.mAdaptTrash;


public class SmsHistoryActivity extends AppCompatActivity {


    private Repository mSmsRepository;

    @Inject
    IRepository mRepository;



    TextView mSmsBody;


    EditText responce;

    @Inject
    IRepository mIRepositoryOnline;


    View mBtnSendResponce;

    SmsMessage mM;
    int mType;
    int mId;
    View mDelete;

    PreferencesFactory mPreferencesFactory;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_responce_sms);

        App.getInstance().getAppComponent().inject(this);
        ButterKnife.bind(this);

        mSmsBody = (TextView) findViewById(R.id.sms_body);
        responce = (EditText) findViewById(R.id.sms_responce);
        mBtnSendResponce = findViewById(R.id.btn_send_responce);

        mDelete=  findViewById(R.id.action_delete);
        mDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                delete();
            }
        });

        mPreferencesFactory = new PreferencesFactory(this);

        mSmsRepository = new Repository(this);
        mId = getIntent().getIntExtra("sms_id",0);

        mSmsRepository.changeStatus(mId);

        mM = mSmsRepository.getMessageById(mId);
        mSmsBody.setText(mM.getmBody());

        AsyncTask mA = new AsyncTask() {

            @Override
            protected void onPostExecute(Object o) {

                if (UtilsFunction.isOnline(getContext())){
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

                super.onPostExecute(o);
            }

            @Override
            protected Object doInBackground(Object[] params) {
                mSeller = mRepository.getSeller(mM.getIdSeller());
                return null;
            }
        };


        findViewById(R.id.layout_send).setVisibility(View.GONE);


        mA.execute();




    }

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


        }else {
            Snackbar.make(btnSendMessage,"Error al enviar mensaje",Snackbar.LENGTH_LONG).show();
        }
    }

    @Inject
    IUserManager userManager;

    PartialSeller mSeller;


    public void btnSendResponceAction() {

        String mResponce = responce.getText().toString();

         if (!mResponce.isEmpty() && mResponce.split(" ").length > 1) {

            if (UtilsFunction.isOnline(this)){
                // TODO: 7/29/2017 poner que mande el sms por la red
            }else{

                 if (mType == 1) {//si soy cliente
                     SendMessage.sendMessageToSeller(getContext(), mM.getIdME(), mM.getIdSeller(), mResponce);
                 } else {//si soy vendedor y estoy respondiendo un mensaje
                     SendMessage.sendMessageToClient(getContext(), mM.getIdSms(), mResponce);
                 }
                 Toast.makeText(getContext(), "Mensaje enviado correctamente", Toast.LENGTH_LONG).show();
             }

            onBackPressed();
         }else
                Toast.makeText(getContext(), "Verifique respuesta", Toast.LENGTH_LONG).show();
    }

    private Context getContext() {
        return SmsHistoryActivity.this;
    }

    @Override
    public void onBackPressed() {
        mAdaptSend.addAll(mSmsRepository.getResponces());
        super.onBackPressed();
    }


    private void delete(){

        if(mM.isDelete()){
            Snackbar.make(mSmsBody, R.string.sms_delete,Snackbar.LENGTH_SHORT).setAction(
                    "SI", new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            mSmsRepository.totalDelete(mId);
                            List<SmsMessage> mTrash = mSmsRepository.getTrash();
                            mAdaptTrash.addAll(mTrash);
                            onBackPressed();
                        }
                    }
            ).show();
        }else {
            Snackbar.make(mSmsBody, R.string.sms_sending,Snackbar.LENGTH_SHORT).setAction(
                    "SI", new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            mSmsRepository.deleteSms(mId);
                            List<SmsMessage> mTrash = mSmsRepository.getTrash();
                            mAdaptTrash.addAll(mTrash);
                            onBackPressed();
                        }
                    }
            ).show();
        }

    }
}
