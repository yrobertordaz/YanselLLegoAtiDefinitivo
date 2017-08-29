package com.llegoati.llegoati.infrastructure.abstracts;

import android.content.Context;

import com.llegoati.llegoati.exceptions.CuponDontExistException;
import com.llegoati.llegoati.exceptions.IncorrectConfirmException;
import com.llegoati.llegoati.exceptions.IncorrectPasswordException;
import com.llegoati.llegoati.exceptions.InvalidPasswordFormatException;
import com.llegoati.llegoati.exceptions.NotConfirmedException;
import com.llegoati.llegoati.exceptions.UserException;
import com.llegoati.llegoati.infrastructure.models.AcumulatedPoint;
import com.llegoati.llegoati.infrastructure.models.Category;
import com.llegoati.llegoati.infrastructure.models.Contact;
import com.llegoati.llegoati.infrastructure.models.Cupon;
import com.llegoati.llegoati.infrastructure.models.MessengerPrice;
import com.llegoati.llegoati.infrastructure.models.ProductDetail;
import com.llegoati.llegoati.infrastructure.models.ProductItem;
import com.llegoati.llegoati.infrastructure.models.ShoppingCartOrder;
import com.llegoati.llegoati.infrastructure.models.User;
import com.llegoati.llegoati.infrastructure.models.UserChangePassword;
import com.llegoati.llegoati.infrastructure.models.UserConfirm;
import com.llegoati.llegoati.infrastructure.models.UserLogin;
import com.llegoati.llegoati.infrastructure.models.UserRegister;
import com.llegoati.llegoati.models.Adds;
import com.llegoati.llegoati.models.Event;
import com.llegoati.llegoati.models.Information;
import com.llegoati.llegoati.models.News;
import com.llegoati.llegoati.models.PartialSeller;
import com.llegoati.llegoati.models.Province;
import com.llegoati.llegoati.models.Request;
import com.llegoati.llegoati.models.SelectionSeller;
import com.llegoati.llegoati.models.UserConfiguration;
import com.llegoati.llegoati.models.UserModify;

import java.io.IOException;
import java.util.List;

/**
 * Created by Yansel on 09/10/2016.
 */
public interface
IRepository {
    void registerUser(UserRegister userRegister, Context context) throws IOException;

    User loginUser(UserLogin userLogin, Context context) throws IOException, UserException, NotConfirmedException;

    void confirmUser(UserConfirm userConfirm, Context context) throws IOException, IncorrectConfirmException;

    User user(String userId) throws IOException;

    List<Category> categories() throws IOException;

    List<ProductItem> products(String subcategory, int pageIndex, int pageSize, String filterArtisan, String filterProvince, Boolean filterWithoutMessenger) throws IOException;

    List<ProductItem> promotions() throws IOException;

    ProductDetail productDetail(String productId) throws IOException;

    List<String> images(String productId) throws IOException;

    AcumulatedPoint acumulatedPoints(String userId) throws IOException;

    void changePassword(UserChangePassword param, Context context) throws IOException, IncorrectPasswordException, InvalidPasswordFormatException;

    com.llegoati.llegoati.infrastructure.models.CheckoutLoteryCode checkOutLoteryCode(String code) throws IOException, CuponDontExistException;

    void updateUser(UserModify param, Context context) throws IOException;

    void updateContactUser(String id, Contact[] contacts) throws IOException;

    List<Request> requestHistory(String clientId, boolean includeProducts) throws IOException;

    List<Cupon> loteryCodeHistory(String userId) throws IOException;

    Request request(String requestId) throws IOException;

    void commentProduct(String comment, String userId, String requestId, String productId) throws IOException;

    void rateProduct(String productId, String userId, String requestId, Integer ratingProduct) throws IOException;

    void rateMessenger(String productId, String userId, Integer ratingMessenger, String requestId) throws IOException;

    List<MessengerPrice> messengerPrices() throws IOException;

    void checkoutShoppingCart(ShoppingCartOrder shoppingCartOrder) throws IOException;

    List<Adds> adds() throws IOException;

    List<News> news() throws IOException;

    List<Information> informations() throws IOException;

    List<Event> events() throws IOException;

    List<ProductItem> search(String query, int pageIndex, int pageSize, String artisanId, String provinceId, Boolean withMessenger) throws IOException;

    List<SelectionSeller> sellers() throws IOException;

    List<Province> provinces() throws IOException;



    List<ProductItem> products(String subcategory) throws IOException;

    List<PartialSeller> getPartialSellers(int page);

    PartialSeller getSeller(String Id);

    int getRankingSeller(String id);

    void validate();

    boolean avaliableSms();
    //List<Adds> adds() throws IOException;
    UserConfiguration getUserConfiguration(String token);

    boolean sendMessageOnline(
            String fromID,String toId,String subject,String body
            ///POST nonrest/Message/SendSmsToFoxBox?from={from}&to={to}&subject={subject}&body={body}
    );




}