package com.llegoati.llegoati.infrastructure.concrete;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.util.Base64;

import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;
import com.llegoati.llegoati.R;
import com.llegoati.llegoati.exceptions.CuponDontExistException;
import com.llegoati.llegoati.exceptions.IncorrectConfirmException;
import com.llegoati.llegoati.exceptions.IncorrectPasswordException;
import com.llegoati.llegoati.exceptions.InvalidPasswordFormatException;
import com.llegoati.llegoati.exceptions.NotConfirmedException;
import com.llegoati.llegoati.exceptions.UserException;
import com.llegoati.llegoati.exceptions.UserExistException;
import com.llegoati.llegoati.infrastructure.abstracts.IRepository;
import com.llegoati.llegoati.infrastructure.concrete.utils.Jsonable;
import com.llegoati.llegoati.infrastructure.models.AcumulatedPoint;
import com.llegoati.llegoati.infrastructure.models.Category;
import com.llegoati.llegoati.infrastructure.models.Contact;
import com.llegoati.llegoati.infrastructure.models.Cupon;
import com.llegoati.llegoati.infrastructure.models.MessengerPrice;
import com.llegoati.llegoati.infrastructure.models.ProductDetail;
import com.llegoati.llegoati.infrastructure.models.ProductItem;
import com.llegoati.llegoati.infrastructure.models.ShoppingCartItem;
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
import com.llegoati.llegoati.smsmodulo.Infraestructure.concrete.Repository;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;

import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.RequestBody;


/**
 * Created by Yansel on 07/11/2016.
 */
public class RemoteRepository implements IRepository {

    ConnectivityManager connectivityManager;
    OkHttpClient okHttpClient;

    public static int PRODUCT_COUNT = 0;
    Context context;


    public RemoteRepository(OkHttpClient okHttpClient, ConnectivityManager connectivityManager, Context context) {
        this.okHttpClient = okHttpClient;
        this.connectivityManager = connectivityManager;
        this.context = context;
    }

    public boolean isNetworkAvailable() {
        NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
        return activeNetworkInfo != null && activeNetworkInfo.isConnectedOrConnecting();
//        return true;
    }

    public boolean isConnectedToService(String url) {
        try {
            if (isNetworkAvailable()) {
                HttpURLConnection httpURLConnection = (HttpURLConnection) (new URL(url).openConnection());
                httpURLConnection.setRequestProperty("User-Agent", "Android Application");
                httpURLConnection.setRequestProperty("Connection", "close");
                httpURLConnection.setConnectTimeout(5 * 1000);
                httpURLConnection.connect();
                int responseCode = httpURLConnection.getResponseCode();
                if (responseCode == HttpURLConnection.HTTP_OK)
                    return true;
            }

        } catch (Exception e) {
            e.getMessage();
        }
        return false;
    }

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    @Override
    public void registerUser(UserRegister userRegister, Context context) throws IOException {

        RequestBody requestBody = new FormBody.Builder()
                .addEncoded("Name", userRegister.getName())
                .addEncoded("Email", userRegister.getEmail())
                .addEncoded("Sex", userRegister.getSex())
                .addEncoded("Phone", userRegister.getPhone())
                .addEncoded("Password", userRegister.getPassword())
                .build();

        okhttp3.Request.Builder builder = new okhttp3.Request.Builder();

        okhttp3.Request request = builder.url(context.getResources().getString(R.string.llegoati_service) + "Seller/Register")
                .post(requestBody)
                .build();

        try (okhttp3.Response response = okHttpClient.newCall(request).execute()) {
            if (!response.isSuccessful()) {
                String message = response.body().string();
                if (message.contains("Email exist"))
                    throw new UserExistException(context.getString(R.string.email_exist));
                throw new IOException("Estamos presentando problemas con los servicios. Intentelo más tarde.");
            }
        }
    }

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    @Override
    public User loginUser(UserLogin userLogin, Context context) throws IOException, UserException, NotConfirmedException {
        RequestBody requestBody = new FormBody.Builder()
                .addEncoded("Email", userLogin.getEmail())
                .addEncoded("Password", userLogin.getPassword())
                .build();
        String message = "";
        okhttp3.Request.Builder builder = new okhttp3.Request.Builder();

        okhttp3.Request request = builder.url(context.getResources().getString(R.string.llegoati_service) + "Seller/LogIn")
                .post(requestBody)
                .build();

        try (okhttp3.Response response = okHttpClient.newCall(request).execute()) {

            message = response.body().string();
            if (!response.isSuccessful()) {
                if (message.contains("IncorrectCredential"))
                    throw new UserException(message);
                else if (message.contains("NoConfirmed"))
                    throw new NotConfirmedException(message);
                throw new IOException(message);
            }
            // Todo: Esto es hasta que pedro devuelva lo que tiene que devolver
            User user = (User) Jsonable.fromJson(message, User.class);
            user.setPassword(userLogin.getPassword());
            return user;
        }
    }


    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    @Override
    public void confirmUser(UserConfirm userConfirm, Context context) throws IOException, IncorrectConfirmException {

        RequestBody requestBody = new FormBody.Builder()
                .addEncoded("Email", userConfirm.getEmail())
                .addEncoded("ConfirmationCode", userConfirm.getConfirmationCode())
                .build();

        okhttp3.Request.Builder builder = new okhttp3.Request.Builder();

        okhttp3.Request request = builder.url(context.getString(R.string.llegoati_service) + "Seller/Confirm")
                .post(requestBody)
                .build();

        try (okhttp3.Response response = okHttpClient.newCall(request).execute()) {
            if (!response.isSuccessful()) throw new IOException(response.message());

            ConfirmResult confirmResult = (ConfirmResult) Jsonable.fromJson(response.body().string(), ConfirmResult.class);
            if (!confirmResult.ConfirmResult)
                throw new IncorrectConfirmException("Código de confirmación incorrecto");

        }
    }

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    @Override
    public User user(String userId) throws IOException {
        okhttp3.Request.Builder builder = new okhttp3.Request.Builder();
        okhttp3.Request request = builder
                .url(String.format("%s%s?sellerId=%s", context.getString(R.string.llegoati_service), "Seller/GetSelerDetails", userId))
                .build();
        try (okhttp3.Response response = okHttpClient.newCall(request).execute()) {
            if (!response.isSuccessful()) throw new IOException(response.message());
            User user = (User) Jsonable.fromJson(response.body().string(), User.class);
            return user;
        }
    }


    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    @Override
    public List<Category> categories() throws IOException, JsonSyntaxException {
        List<Category> cat = new ArrayList<>();
        okhttp3.Request.Builder builder = new okhttp3.Request.Builder();

        okhttp3.Request request = builder
                .url(String.format("%s%s", context.getString(R.string.llegoati_service), "Category/GetAllCategoriesWhitSubcategories"))
                .build();
        try (okhttp3.Response response = okHttpClient.newCall(request).execute()) {
            if (!response.isSuccessful()) throw new IOException(response.message());
            cat.addAll(Arrays.asList((Category[]) Jsonable.fromJson(response.body().string(), Category[].class)));
            return cat;
        }
    }


    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    @Override
    public List<ProductItem> products(String subcategory, int pageIndex, int pageSize, String filterArtisan, String filterProvince, Boolean filterWithoutMessenger) throws IOException {
        List<ProductItem> productItemList = new ArrayList<>();


        okhttp3.Request request = new okhttp3.Request.Builder()
                .url(String.format("%s%s?subcategory=%s&pageIndex=%d&pageLoad=%d&vendor=%s&province=%s&distribution=%s",
                        context.getString(R.string.llegoati_service),
                        "Product/FilterProduct",
                        subcategory,
                        pageIndex,
                        pageSize,
                        filterArtisan != null ? filterArtisan : "",
                        filterProvince != null ? filterProvince : "",
                        filterWithoutMessenger != null ? filterWithoutMessenger : ""
                ))
                .build();
        try (okhttp3.Response response = okHttpClient.newCall(request).execute()) {
            if (!response.isSuccessful()) throw new IOException(response.message());

            productItemList.addAll(Arrays.asList((ProductItem[]) Jsonable.fromJson(response.body().string(), ProductItem[].class)));
        }
        Iterator<ProductItem> productItemIterator = productItemList.iterator();
        while (productItemIterator.hasNext()) {
            ProductItem item = productItemIterator.next();
            item.setPhoto(this.images(item.getProductId()).get(0));
        }

        return productItemList;
    }


    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    @Override
    public List<ProductItem> promotions() throws JsonSyntaxException, IOException {
        List<ProductItem> result = null;
        okhttp3.Request.Builder builder = new okhttp3.Request.Builder();
        okhttp3.Request request = builder
                .url(String.format("%s%s", context.getString(R.string.llegoati_service), "Product/GetPromotionsProducts"))
                .get()
                .build();
        try (okhttp3.Response response = okHttpClient.newCall(request).execute()) {
            if (!response.isSuccessful()) throw new IOException(response.message());

            result = Arrays.asList((ProductItem[]) Jsonable.fromJson(response.body().string(), ProductItem[].class));
            return result;
        }

    }

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    @Override
    public ProductDetail productDetail(String productId) throws IOException {

        okhttp3.Request.Builder builder = new okhttp3.Request.Builder();
        okhttp3.Request request = builder
                .url(String.format("%s%s?productId=%s", context.getString(R.string.llegoati_service), "Product/GetProductDetails", productId))
                .get()
                .build();

        try (okhttp3.Response response = okHttpClient.newCall(request).execute()) {
            if (!response.isSuccessful()) throw new IOException(response.message());

            return (ProductDetail) Jsonable.fromJson(response.body().string(), ProductDetail.class);
        }
    }

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    @Override
    public List<String> images(String productId) throws IOException {

        okhttp3.Request.Builder builder = new okhttp3.Request.Builder();

        okhttp3.Request request = builder
                .url(String.format("%s%s?subCategory=%s", context.getString(R.string.llegoati_service), "Product/GetProductImages", productId))
                .get()
                .build();

        try (okhttp3.Response response = okHttpClient.newCall(request).execute()) {
            if (!response.isSuccessful()) throw new IOException(response.message());

            return Arrays.asList((String[]) Jsonable.fromJson(response.body().string(), String[].class));
        }
    }

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    @Override
    public AcumulatedPoint acumulatedPoints(String userId) throws IOException {
        okhttp3.Request.Builder builder = new okhttp3.Request.Builder();
        okhttp3.Request request = builder
                .url(String.format("%s%s?userId=%s", context.getString(R.string.llegoati_service), "Seller/GetAcumulatedPointToUser", userId))
                .get()
                .build();

        try (okhttp3.Response response = okHttpClient.newCall(request).execute()) {
            if (!response.isSuccessful()) throw new IOException(response.message());
            return (AcumulatedPoint) Jsonable.fromJson(response.body().string(), AcumulatedPoint.class);
        }
    }

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    @Override
    public void changePassword(UserChangePassword param, Context context) throws
            IOException, IncorrectPasswordException, InvalidPasswordFormatException {
        RequestBody requestBody = new FormBody.Builder()
                .build();

        okhttp3.Request.Builder builder = new okhttp3.Request.Builder();

        okhttp3.Request request = builder
                .url(
                        String.format("%s%s?userName=%s&oldPassword=%s&newPassword=%s",
                                context.getResources().getString(R.string.llegoati_service),
                                "Seller/ModifyAccount",
                                param.getEmail(),
                                param.getOldPassword(),
                                param.getPassword()
                        ))
                .post(requestBody)
                .build();
        // Este comportamiento es raro en comparación a todos los métodos
        // Debería devolver un Bad Request con la información del error
        try (okhttp3.Response response = okHttpClient.newCall(request).execute()) {
            String message = response.body().string(); // Esta linea pudiese lanzar un error
            if (!response.isSuccessful()) {

                throw new IOException(message);
            }
            if (message.contains("false")) {
                throw new IncorrectPasswordException();
            } else if (message.contains("characters")) {
                throw new InvalidPasswordFormatException();
            }
        }

    }

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    @Override
    public com.llegoati.llegoati.infrastructure.models.CheckoutLoteryCode checkOutLoteryCode(String code) throws IOException, CuponDontExistException {
        RequestBody requestBody = new FormBody.Builder()
                .addEncoded("Code", code)
                .build();
        okhttp3.Request.Builder builder = new okhttp3.Request.Builder();
        okhttp3.Request request = builder
                .url(
                        String.format("%s%s",
                                context.getResources().getString(R.string.llegoati_service),
                                "Coupon/CheckoutLoteryCode"
                        ))
                .post(requestBody)
                .build();

        try (okhttp3.Response response = okHttpClient.newCall(request).execute()) {
            String message = response.body().string();
            if (!response.isSuccessful()) {

                throw new IOException(message);
            }
            if (message.contains("not exist")) {
                throw new CuponDontExistException();
            }
            return (com.llegoati.llegoati.infrastructure.models.CheckoutLoteryCode) Jsonable.fromJson(message, com.llegoati.llegoati.infrastructure.models.CheckoutLoteryCode.class);
        }
    }

    @Override
    public void updateUser(UserModify param, Context context) throws IOException {
        RequestBody requestBody = new FormBody.Builder()
                .addEncoded("Id", param.getId())
                .addEncoded("Name", param.getName())
                .addEncoded("Email", param.getEmail())
                .addEncoded("Sex", param.getSex())
                .addEncoded("Phone", param.getPhone())
                .build();

        okhttp3.Request.Builder builder = new okhttp3.Request.Builder();

        okhttp3.Request request = builder.url(context.getResources().getString(R.string.llegoati_service) + "Seller/UpdateUser")
                .post(requestBody)
                .build();

        try (okhttp3.Response response = okHttpClient.newCall(request).execute()) {
            if (!response.isSuccessful()) {
                String message = response.body().string();
                if (message.contains("Email exist"))
                    throw new UserExistException(context.getString(R.string.email_exist));
                throw new IOException("Estamos presentando problemas con los servicios. Intentelo más tarde.");
            }
        }
    }

    @Override
    public void updateContactUser(String id, Contact[] contacts) throws IOException {

        okhttp3.FormBody.Builder formBuilder = new FormBody.Builder()
                .addEncoded("Id", id);
        for (int i = 0; i < contacts.length; i++) {
            formBuilder.addEncoded(String.format("Contacts[%d].Type", i), String.valueOf(contacts[i].getType()));
            formBuilder.addEncoded(String.format("Contacts[%d].ContactValue", i), String.valueOf(contacts[i].getContactValue()));
            formBuilder.addEncoded(String.format("Contacts[%d].Active", i), String.valueOf(contacts[i].getActive()));
        }
        RequestBody requestBody = formBuilder.build();

        okhttp3.Request.Builder builder = new okhttp3.Request.Builder();

        okhttp3.Request request = builder.url(context.getResources().getString(R.string.llegoati_service) + "Seller/UpdateUser")
                .post(requestBody)
                .build();

        try (okhttp3.Response response = okHttpClient.newCall(request).execute()) {
            if (!response.isSuccessful()) {
                String message = response.body().string();
                throw new IOException("Estamos presentando problemas con los servicios. Intentelo más tarde.");
            }
        }
    }

    @Override
    public List<Request> requestHistory(String clientId, boolean includeProducts) throws IOException {
        okhttp3.Request.Builder builder = new okhttp3.Request.Builder();
        okhttp3.Request request = builder
                .url(String.format("%s%s?clientId=%s%s", context.getString(R.string.llegoati_service), "Order/GetOrderHistoricToClient", clientId, includeProducts ? "&includeProducts=true" : ""))
                .get()
                .build();

        try (okhttp3.Response response = okHttpClient.newCall(request).execute()) {
            if (!response.isSuccessful()) throw new IOException(response.message());
            return Arrays.asList((Request[]) Jsonable.fromJson(response.body().string(), Request[].class));
        }
    }

    @Override
    public List<Cupon> loteryCodeHistory(String userId) throws IOException {
        okhttp3.Request.Builder builder = new okhttp3.Request.Builder();
        okhttp3.Request request = builder
                .url(String.format("%s%s?userGuid=%s", context.getString(R.string.llegoati_service), "Coupon/LoteryCodeHistoric", userId))
                .get()
                .build();

        try (okhttp3.Response response = okHttpClient.newCall(request).execute()) {
            if (!response.isSuccessful()) throw new IOException(response.message());
            return Arrays.asList((Cupon[]) Jsonable.fromJson(response.body().string(), Cupon[].class));
        }
    }

    @Override
    public Request request(String requestId) throws IOException {
        okhttp3.Request.Builder builder = new okhttp3.Request.Builder();
        okhttp3.Request request = builder
                .url(String.format("%s%s%s", context.getString(R.string.llegoati_service), "Order/GetOrderDetail/", requestId))
                .get()
                .build();

        try (okhttp3.Response response = okHttpClient.newCall(request).execute()) {
            if (!response.isSuccessful()) throw new IOException(response.message());
            return (Request) Jsonable.fromJson(response.body().string(), Request.class);
        }
    }

    @Override
    public void commentProduct(String comment, String userId, String requestId, String productId) throws IOException {

        okhttp3.FormBody.Builder formBuilder = new FormBody.Builder()
                .addEncoded("TextComment", comment)
                .addEncoded("IdCommentProvider", userId)
                .addEncoded("PartnerOrder", requestId)
                .addEncoded("ProductId", productId);
        RequestBody requestBody = formBuilder.build();

        okhttp3.Request.Builder builder = new okhttp3.Request.Builder();

        okhttp3.Request request = builder.url(context.getResources().getString(R.string.llegoati_service) + "Product/PostCommentToProduct")
                .post(requestBody)
                .build();

        try (okhttp3.Response response = okHttpClient.newCall(request).execute()) {
            if (!response.isSuccessful()) {
                throw new IOException("");
            }
        }
    }

    @Override
    public void rateProduct(String productId, String userId, String requestId, Integer ratingProduct) throws IOException {

        okhttp3.Request.Builder builder = new okhttp3.Request.Builder();
        okhttp3.Request request = builder
                .url(String.format(Locale.US, "%s%s?productId=%s&userId=%s&rankingProduct=%s&partnerOrder=%s",
                        context.getResources().getString(R.string.llegoati_service),
                        "Product/PostRankingProduct",
                        productId,
                        userId,
                        ratingProduct.toString(),
                        requestId
                ))
                .get()
                .build();


        try (okhttp3.Response response = okHttpClient.newCall(request).execute()) {
            if (!response.isSuccessful()) {
                throw new IOException("");
            }
        }
    }

    @Override
    public void rateMessenger(String productId, String userId, Integer ratingMessenger, String requestId) throws IOException {


        okhttp3.Request.Builder builder = new okhttp3.Request.Builder();

        okhttp3.Request request = builder
                .url(String.format(Locale.US, "%s%s?productId=%s&userId=%s&rankingMessenger=%s&partnerOrder=%s",
                        context.getResources().getString(R.string.llegoati_service),
                        "Product/PostRankingMessengerProduct",
                        productId,
                        userId,
                        ratingMessenger.toString(),
                        requestId
                ))
                .get()
                .build();

        try (okhttp3.Response response = okHttpClient.newCall(request).execute()) {
            if (!response.isSuccessful()) {
                throw new IOException(response.message());
            }
        }
    }

    @Override
    public List<MessengerPrice> messengerPrices() throws IOException, JsonSyntaxException {
        okhttp3.Request.Builder builder = new okhttp3.Request.Builder();

        okhttp3.Request request = builder
                .url(String.format(Locale.US, "%s%s",
                        context.getResources().getString(R.string.llegoati_service),
                        "MessengerPrice/GetAllMessengerPrice"
                ))
                .get()
                .build();

        try (okhttp3.Response response = okHttpClient.newCall(request).execute()) {
            if (!response.isSuccessful()) throw new IOException(response.message());
            return Arrays.asList((MessengerPrice[]) Jsonable.fromJson(response.body().string(), MessengerPrice[].class));
        }
    }

    @Override
    public void checkoutShoppingCart(ShoppingCartOrder shoppingCartOrder) throws IOException {

        okhttp3.FormBody.Builder formBuilder = new FormBody.Builder();

        formBuilder.addEncoded("ClientId", (shoppingCartOrder.getClientId() != null) ? shoppingCartOrder.getClientId() : "")
                .addEncoded("LoteryId", (shoppingCartOrder.getLoteryId() == null) ? "" : shoppingCartOrder.getLoteryId())
                .addEncoded("LowerVip", shoppingCartOrder.getLowerVip().toString())
                .addEncoded("MessengerPrice", shoppingCartOrder.getMessengerPrice().toString());
        int itemIndex = 0;

//        Aqui adicionamos los elementos importantes de cada producto para confeccionar el pedido
        while (shoppingCartOrder.getItems().hasNext()) {
            ShoppingCartItem item = shoppingCartOrder.getItems().next();

            formBuilder.addEncoded(String.format(Locale.US, "Products[%d].IdProduct", itemIndex), item.getProductId());
            formBuilder.addEncoded(String.format(Locale.US, "Products[%d].SelectedCant", itemIndex), String.valueOf(item.getQuantity()));
            formBuilder.addEncoded(String.format(Locale.US, "Products[%d].ProductPrice", itemIndex), String.valueOf(item.getPrice()));
            //Color, Talla y Número
            itemIndex++;
        }
        if (shoppingCartOrder.getClientId() == null) {
            // TODO: 7/15/2017 Hacerlo con un solo campo para el telefono
            formBuilder.addEncoded("PhoneContact", shoppingCartOrder.getPhoneContact());
            formBuilder.addEncoded("ClientName", shoppingCartOrder.getNameContact());
            formBuilder.addEncoded("AddresContact", shoppingCartOrder.getAddressContact());
        }

        RequestBody requestBody = formBuilder.build();

        okhttp3.Request.Builder builder = new okhttp3.Request.Builder();

        okhttp3.Request request = builder.url(context.getResources().getString(R.string.llegoati_service) + "Order/GenerateOrder")
                .post(requestBody)
                .build();

        try (okhttp3.Response response = okHttpClient.newCall(request).execute()) {
            if (!response.isSuccessful()) {
                throw new IOException("");
            }
        }
    }

    @Override
    public List<Adds> adds() throws IOException {
        okhttp3.Request.Builder builder = new okhttp3.Request.Builder();

        okhttp3.Request request = builder
                .url(String.format(Locale.US, "%s%s",
                        context.getResources().getString(R.string.llegoati_service),
                        "Events/GetAllAdds"
                ))
                .get()
                .build();

        try (okhttp3.Response response = okHttpClient.newCall(request).execute()) {
            if (!response.isSuccessful()) throw new IOException(response.message());
            return Arrays.asList((Adds[]) Jsonable.fromJson(response.body().string(), Adds[].class));
        }
    }

    @Override
    public List<News> news() throws IOException {
        okhttp3.Request.Builder builder = new okhttp3.Request.Builder();

        okhttp3.Request request = builder
                .url(String.format(Locale.US, "%s%s",
                        context.getResources().getString(R.string.llegoati_service),
                        "News/GetNews"
                ))
                .get()
                .build();

        try (okhttp3.Response response = okHttpClient.newCall(request).execute()) {
            if (!response.isSuccessful()) throw new IOException(response.message());
            return Arrays.asList((News[]) Jsonable.fromJson(response.body().string(), News[].class));
        }
    }

    @Override
    public List<Information> informations() throws IOException {

        okhttp3.Request.Builder builder = new okhttp3.Request.Builder();

        okhttp3.Request request = builder
                .url(String.format(Locale.US, "%s%s",
                        context.getResources().getString(R.string.llegoati_service),
                        "News/GetAllAnswers"
                ))
                .get()
                .build();

        try (okhttp3.Response response = okHttpClient.newCall(request).execute()) {
            if (!response.isSuccessful()) throw new IOException(response.message());
            return Arrays.asList((Information[]) Jsonable.fromJson(response.body().string(), Information[].class));

        }
    }

    @Override
    public List<Event> events() throws IOException {
        okhttp3.Request.Builder builder = new okhttp3.Request.Builder();

        okhttp3.Request request = builder
                .url(String.format(Locale.US, "%s%s",
                        context.getResources().getString(R.string.llegoati_service),
                        "Events/GetEventsWithOutBase64"
                ))
                .get()
                .build();

        try (okhttp3.Response response = okHttpClient.newCall(request).execute()) {
            if (!response.isSuccessful()) throw new IOException(response.message());
            return Arrays.asList((Event[]) Jsonable.fromJson(response.body().string(), Event[].class));

        }
    }

    @Override
    public List<ProductItem> search(String query, int pageIndex, int pageSize, String artisanId, String provinceId, Boolean withMessenger) throws IOException {


        okhttp3.Request.Builder builder = new okhttp3.Request.Builder();

        okhttp3.FormBody.Builder formBuilder = new FormBody.Builder();

        formBuilder.addEncoded("SearchQuery", query);

        formBuilder.addEncoded("PageIndex", String.valueOf(pageIndex));
        formBuilder.addEncoded("PageSize", String.valueOf(pageSize));

        int f = 0;
        //Filters
        if (provinceId != null) {
            formBuilder.addEncoded(String.format("Filters[%d].Key", f), "13");
            formBuilder.addEncoded(String.format("Filters[%d].Value", f), provinceId);
            f++;
        }
        if (artisanId != null) {
            formBuilder.addEncoded(String.format("Filters[%d].Key", f), "7");
            formBuilder.addEncoded(String.format("Filters[%d].Value", f), artisanId);
            f++;
        }
        if (withMessenger != null) {
            formBuilder.addEncoded(String.format("Filters[%d].Key", f), "12");
            formBuilder.addEncoded(String.format("Filters[%d].Value", f), withMessenger ? "true" : "false");
            f++;
        }


        okhttp3.Request request = builder
                .url(String.format(Locale.US, "%s%s",
                        context.getResources().getString(R.string.llegoati_service),
                        "Product/SearchProducts"
                ))
                .post(formBuilder.build())
                .build();

        try (okhttp3.Response response = okHttpClient.newCall(request).execute()) {
            if (!response.isSuccessful()) throw new IOException(response.message());
            String message = response.body().string();
            return Arrays.asList((ProductItem[]) Jsonable.fromJson(message, ProductItem[].class));

        }
    }

    @Override
    public List<SelectionSeller> sellers() throws IOException {
        okhttp3.Request.Builder builder = new okhttp3.Request.Builder();

        okhttp3.Request request = builder
                .url(String.format(Locale.US, "%s%s?pageIndex=%d&pageSize=%d&image=false",
                        context.getResources().getString(R.string.llegoati_service),
                        "Seller/GetSellers",
                        0,
                        Integer.MAX_VALUE
                ))
                .get()
                .build();

        try (okhttp3.Response response = okHttpClient.newCall(request).execute()) {
            if (!response.isSuccessful()) throw new IOException(response.message());
            return Arrays.asList((SelectionSeller[]) Jsonable.fromJson(response.body().string(), SelectionSeller[].class));

        }
    }

    @Override
    public List<Province> provinces() throws IOException {
        okhttp3.Request.Builder builder = new okhttp3.Request.Builder();

        okhttp3.Request request = builder
                .url(String.format(Locale.US, "%s%s",
                        context.getResources().getString(R.string.llegoati_service),
                        "Product/GetAllProvinces"
                ))
                .get()
                .build();

        try (okhttp3.Response response = okHttpClient.newCall(request).execute()) {
            if (!response.isSuccessful()) throw new IOException(response.message());
            return Arrays.asList((Province[]) Jsonable.fromJson(response.body().string(), Province[].class));


        }
    }

    @Override
    public List<ProductItem> products(String subcategory) throws IOException {
        return null;
    }



    @Override
    public int getRankingSeller(String id) {
        return 0;
    }

    @Override
    public void validate() {

    }



    public static int CATEGORY_COUNT = 0;
    public static int SUBCATEGORY_COUNT = 0;
    private static final String PETTITION_GET_SELLERS = "Seller/GetSeller?pageIndex=";
    private static final String PETTITION_GET_SELLER_INFO = "Seller/GetSelerDetails";
    private static final String PETTITION_GET_SELLER_CONFIGURATION   = "Seller/GetMessageConfiguration?userId=";
    private static final String PETTITION_MESSAGE_SEND   = "Message/SendSmsToFoxBox";

    @Override
    public boolean avaliableSms() {
        return false;
    }

    @Override
    public UserConfiguration getUserConfiguration(String token) {
        okhttp3.Request.Builder builder = new okhttp3.Request.Builder();
        okhttp3.Request request = builder
                .url(String.format("%s%s%s", context.getString(R.string.llegoati_service), PETTITION_GET_SELLER_CONFIGURATION, token))
                .build();
        try (okhttp3.Response response = okHttpClient.newCall(request).execute()) {
            if (!response.isSuccessful()) throw new IOException(response.message());
            return (UserConfiguration) Jsonable.fromJson(response.body().string(), UserConfiguration.class);
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public boolean sendMessageOnline(String fromID, String toId, String subject, String body) {

        RequestBody requestBody = new FormBody.Builder()
                .addEncoded("from", fromID)
                .addEncoded("to", toId)
                .addEncoded("subject", subject)
                .addEncoded("body", body)
                .build();
        //?from=%s&to=%s&subject=%s&body=%s
        okhttp3.Request.Builder builder = new okhttp3.Request.Builder();

        okhttp3.Request request = builder.url(context.getResources().getString(R.string.llegoati_service) + PETTITION_MESSAGE_SEND)
                .post(requestBody)
                .build();


        try (okhttp3.Response response = okHttpClient.newCall(request).execute()) {
            if (!response.isSuccessful())
                return false;
            else {

                final Repository mTempRepo = new Repository(getContext());
                mTempRepo.insertResponceSms(body, fromID, toId);

                return response.isSuccessful();

            }

        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public List<PartialSeller> getPartialSellers(int mPage) {
        List<PartialSeller> mTestList = new ArrayList<>();

        final Gson gson = new Gson();

        okhttp3.Request.Builder builder = new okhttp3.Request.Builder();
        okhttp3.Request request = builder.url(context.getResources().getString(R.string.llegoati_service) + "Seller/GetSeller?pageIndex="+String.valueOf(mPage))
                .get()
                .build();

        try (okhttp3.Response response = okHttpClient.newCall(request).execute()) {
            if (response.isSuccessful()) {
                String message = response.body().string();
//                PartialSeller[] mSell = gson.fromJson(message, PartialSeller[].class);
                //              mTestList = Arrays.asList(mSell);

                PartialSeller[] mSell = gson.fromJson(message, PartialSeller[].class);
                mTestList = Arrays.asList(mSell);

            }else {

            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        return mTestList;
    }

    @Override
    public PartialSeller getSeller(String Id) {

        okhttp3.Request.Builder builder = new okhttp3.Request.Builder();
        okhttp3.Request request = builder
                .url(String.format("%s%s?sellerId=%s", context.getString(R.string.llegoati_service), PETTITION_GET_SELLER_INFO, Id))
                .build();
        try (okhttp3.Response response = okHttpClient.newCall(request).execute()) {
            if (!response.isSuccessful()) throw new IOException(response.message());
            return (PartialSeller) Jsonable.fromJson(response.body().string(), PartialSeller.class);
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }



    }

    public static Bitmap getBitmap(String base64) {
        if (base64 == null) {
            return null;
        }
        byte[] decodedString = Base64.decode(base64, Base64.NO_WRAP);
        return BitmapFactory.decodeByteArray(decodedString, 0, decodedString.length);
    }

    public Context getContext() {
        return context;
    }

    private class ConfirmResult {
        boolean ConfirmResult;

        public ConfirmResult(boolean confirmResult) {
            ConfirmResult = confirmResult;
        }

        public boolean isConfirmResult() {
            return ConfirmResult;
        }

        public void setConfirmResult(boolean confirmResult) {
            ConfirmResult = confirmResult;
        }
    }
}
