package com.llegoati.llegoati.infrastructure.concrete;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.AsyncTask;

import com.llegoati.llegoati.exceptions.NotConfirmedException;
import com.llegoati.llegoati.exceptions.UserException;
import com.llegoati.llegoati.infrastructure.abstracts.IRepository;
import com.llegoati.llegoati.infrastructure.abstracts.IUserManager;
import com.llegoati.llegoati.infrastructure.concrete.utils.Jsonable;
import com.llegoati.llegoati.infrastructure.models.AcumulatedPoint;
import com.llegoati.llegoati.infrastructure.models.User;
import com.llegoati.llegoati.infrastructure.models.UserLogin;

import java.io.IOException;

/**
 * Created by Yansel on 3/27/2017.
 */

public class UserManagerSharedPreference implements IUserManager {
    Context context;
    private final IRepository repository;
    private final SharedPreferences sharedPreferences;
    private SharedPreferences.Editor editor;
    private static final String SERIALIZED_USER = "serialized_user";
    private static final String SERIALIZED_ACCUMUATED_POINTS = "serialized_accumulated_points";

    public UserManagerSharedPreference(SharedPreferences sharedPreferences, IRepository repository, Context context) {
        this.sharedPreferences = sharedPreferences;
        editor = sharedPreferences.edit();
        this.repository = repository;
        this.context = context;
    }

    @Override
    public boolean isUserPermanentAuthenticated() {
        return this.sharedPreferences.contains(SERIALIZED_USER) && user() != null;
    }

    @Override
    public void updateLoginInformation() {
        UpdateLoginTask updateLoginTask = new UpdateLoginTask();
        updateLoginTask.execute(new UserLogin(user().getEmail(), user().getPassword()));
    }

    @Override
    public void saveUser(User user) {
        editor.putString(SERIALIZED_USER, Jsonable.toJson(user));
        editor.commit();
    }

    @Override
    public void saveAccumulatePoints(AcumulatedPoint acumulatedPoint) {
        editor.putString(SERIALIZED_ACCUMUATED_POINTS, Jsonable.toJson(acumulatedPoint));
        editor.commit();
    }

    @Override
    public AcumulatedPoint accumulatedPoints() {
        return (AcumulatedPoint) Jsonable.fromJson(sharedPreferences.getString(SERIALIZED_ACCUMUATED_POINTS, null), AcumulatedPoint.class);
    }

    @Override
    public User user() {
        return (User) Jsonable.fromJson(sharedPreferences.getString(SERIALIZED_USER, null), User.class);
    }

    public class UpdateLoginTask extends AsyncTask<UserLogin, Void, Void> {


        @Override
        protected Void doInBackground(UserLogin... userLogins) {
            User user = null;
            try {
                user = repository.loginUser(userLogins[0], context);

                saveUser(user);
            } catch (IOException e) {
                e.printStackTrace();
            } catch (UserException e) {
                e.printStackTrace();
            } catch (NotConfirmedException e) {
                e.printStackTrace();
            }
            saveUser(user);

            return null;
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();

        }

        @Override
        protected void onPostExecute(Void aVoid) {
//            if (loginDone) {
//                LoginActivity.this.setResult(MainActivity.LOGIN_CODE);
//                LoginActivity.this.onBackPressed();
//            }
            super.onPostExecute(aVoid);
        }
    }
}
