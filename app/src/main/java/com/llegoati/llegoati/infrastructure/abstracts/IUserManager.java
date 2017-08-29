package com.llegoati.llegoati.infrastructure.abstracts;

import com.llegoati.llegoati.infrastructure.models.AcumulatedPoint;
import com.llegoati.llegoati.infrastructure.models.User;

/**
 * Created by Yansel on 3/27/2017.
 */

public interface IUserManager {

    boolean isUserPermanentAuthenticated();

    void updateLoginInformation();

    void saveUser(User user);

    User user();

    void saveAccumulatePoints(AcumulatedPoint acumulatedPoint);

    AcumulatedPoint accumulatedPoints();

}
