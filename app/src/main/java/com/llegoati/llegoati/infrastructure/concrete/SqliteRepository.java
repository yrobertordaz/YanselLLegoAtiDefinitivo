package com.llegoati.llegoati.infrastructure.concrete;

import android.content.Context;
import android.database.Cursor;
import android.support.v7.widget.RecyclerView;
import android.util.Base64;

import com.android.volley.Request;
import com.llegoati.llegoati.exceptions.CuponDontExistException;
import com.llegoati.llegoati.exceptions.IncorrectConfirmException;
import com.llegoati.llegoati.exceptions.IncorrectPasswordException;
import com.llegoati.llegoati.exceptions.InvalidPasswordFormatException;
import com.llegoati.llegoati.exceptions.NotConfirmedException;
import com.llegoati.llegoati.exceptions.UserException;
import com.llegoati.llegoati.infrastructure.abstracts.IRepository;
import com.llegoati.llegoati.infrastructure.models.AcumulatedPoint;
import com.llegoati.llegoati.infrastructure.models.AttributesProductInfo;
import com.llegoati.llegoati.infrastructure.models.Califier;
import com.llegoati.llegoati.infrastructure.models.Category;
import com.llegoati.llegoati.infrastructure.models.CheckoutLoteryCode;
import com.llegoati.llegoati.infrastructure.models.Contact;
import com.llegoati.llegoati.infrastructure.models.Cupon;
import com.llegoati.llegoati.infrastructure.models.MessengerPrice;
import com.llegoati.llegoati.infrastructure.models.ProductDetail;
import com.llegoati.llegoati.infrastructure.models.ProductItem;
import com.llegoati.llegoati.infrastructure.models.Seller;
import com.llegoati.llegoati.infrastructure.models.ShoppingCartItem;
import com.llegoati.llegoati.infrastructure.models.ShoppingCartOrder;
import com.llegoati.llegoati.infrastructure.models.Subcategory;
import com.llegoati.llegoati.infrastructure.models.User;
import com.llegoati.llegoati.infrastructure.models.UserChangePassword;
import com.llegoati.llegoati.infrastructure.models.UserConfirm;
import com.llegoati.llegoati.infrastructure.models.UserLogin;
import com.llegoati.llegoati.infrastructure.models.UserRegister;
import com.llegoati.llegoati.models.Adds;
import com.llegoati.llegoati.models.Event;
import com.llegoati.llegoati.models.FrontDescription;
import com.llegoati.llegoati.models.Image;
import com.llegoati.llegoati.models.ImagesItem;
import com.llegoati.llegoati.models.Information;
import com.llegoati.llegoati.models.News;
import com.llegoati.llegoati.models.PartialSeller;
import com.llegoati.llegoati.models.PhoneNumber;
import com.llegoati.llegoati.models.Price;
import com.llegoati.llegoati.models.Province;
import com.llegoati.llegoati.models.SelectionSeller;
import com.llegoati.llegoati.models.UserConfiguration;
import com.llegoati.llegoati.models.UserModify;
import com.llegoati.llegoati.smsmodulo.SendMessage;
import com.llegoati.llegoati.smsmodulo.Utils.Constants;

import org.eclipse.jdt.core.IField;
import org.eclipse.jdt.internal.core.CompilationUnitStructureRequestor;
import org.eclipse.jdt.internal.core.INameEnvironmentWithProgress;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Random;

/**
 * Created by Richard on 6/26/2017.
 */


public class SqliteRepository implements IRepository {

    private static final String SMS_TYPE_PEDIDO = "p";

    public dbconection getmDbconection() {
        return mDbconection;
    }

    public void setmDbconection(dbconection mDbconection) {
        this.mDbconection = mDbconection;
    }

    dbconection mDbconection;
    List<dbconection> mConnections;


    public SqliteRepository(dbconection mDbconection) {
        this.mDbconection = mDbconection;
        if (this.mConnections == null)
            this.mConnections = new ArrayList<>();
        this.mConnections.add(this.mDbconection);
    }

    public SqliteRepository(){this.mConnections = new ArrayList<>();};


    //estas conecciones salen del preferences factorie
    public void addConnection(dbconection mNewConnection){
        this.mConnections.add(mNewConnection);
    }

    public void clearConnections(){
        this.mConnections.clear();
        this.mConnections.add(this.mDbconection);
    }


    @Override
    public void registerUser(UserRegister userRegister, Context context) throws IOException {

    }

    @Override
    public User loginUser(UserLogin userLogin, Context context) throws IOException, UserException, NotConfirmedException {
        return null ;
    }

    @Override
    public void confirmUser(UserConfirm userConfirm, Context context) throws IOException, IncorrectConfirmException {

    }

    @Override
    public User user(String userId) throws IOException {
        return null;
    }

    @Override
    public List<Category> categories() throws IOException {
        // TODO: 6/28/2017 hacer esto offline
        Cursor mCategories = mDbconection.getAllCategories();
        List<Category> mResult = new ArrayList<>();
        if (mCategories.moveToFirst()){

            final int posId = mCategories.getColumnIndex(dbconection.COLUMNS_CATEGORIAS.ID);
            final int posName = mCategories.getColumnIndex(dbconection.COLUMNS_CATEGORIAS.CNOMBRE);
            final int posCode = mCategories.getColumnIndex(dbconection.COLUMNS_CATEGORIAS.CCODIGO);
            final int posUpdateDate = mCategories.getColumnIndex(dbconection.COLUMNS_CATEGORIAS.CUPDATE_DATE);

            do {

                mResult.add(new Category(String.valueOf(mCategories.getString(posId))
                        ,mCategories.getString(posName)
                        ,getSubcategories(mCategories.getString(posId))));


            }while (mCategories.moveToNext());
        }

        return mResult;
    }

    @Override
    public List<ProductItem> products(String subcategory, int pageIndex, int pageSize, String filterArtisan, String filterProvince, Boolean filterWithoutMessenger) throws IOException {

        List<ProductItem> mList = new ArrayList<>();
        Cursor mSubcategorie = mDbconection.getSubcategoryByName(subcategory);
        mSubcategorie.moveToFirst();
        Cursor mItems = mDbconection.filter(mSubcategorie.getString(mSubcategorie.getColumnIndex(dbconection.COLUMNS_SUB_CATEGORIAS.ID)),pageIndex,pageSize,filterArtisan,filterProvince,filterWithoutMessenger);

        return getListByCursor(mItems);
    }


    private List<Subcategory> getSubcategories(String anInt) {
        List<Subcategory> mList = new ArrayList<>();

        Cursor mSubcategories = mDbconection.getSubcategories(anInt);
        if (mSubcategories.moveToFirst()){

            final int posId = mSubcategories.getColumnIndex(dbconection.COLUMNS_SUB_CATEGORIAS.ID);
            final int posName = mSubcategories.getColumnIndex(dbconection.COLUMNS_SUB_CATEGORIAS.CNOMBRE);
            final int posCategoriaId = mSubcategories.getColumnIndex(dbconection.COLUMNS_SUB_CATEGORIAS.CCATEGORIE_ID);
            final int posImage = mSubcategories.getColumnIndex(dbconection.COLUMNS_SUB_CATEGORIAS.CIMAGEN);
            final int posDate = mSubcategories.getColumnIndex(dbconection.COLUMNS_SUB_CATEGORIAS.CUPDATE_DATE);
            do {
                mList.add(new Subcategory(
                                String.valueOf(mSubcategories.getString(posId))
                                ,mSubcategories.getString(posName)
                                ,mSubcategories.getString(posImage)
                        )

                );
            }while (mSubcategories.moveToNext());
        }
        return mList;
    }


    private List<ProductItem> getListByCursor(Cursor mProducts){

        List<ProductItem> mList = new ArrayList<>();


        if (mProducts.moveToFirst()) {
                /*
                *   private int Id;
                    private String ProductId;
                    private String Photo;
                    private float Price;
                    private Seller Seller;
                    private Califier Califier;
                    private String NameSubcategory;
                * */

            int mPosId = mProducts.getColumnIndex(dbconection.COLUMNS_PRODUCTO.ID);
            int mPosSku = mProducts.getColumnIndex(dbconection.COLUMNS_PRODUCTO.Sku);
            int mPosPhoto = mProducts.getColumnIndex(dbconection.COLUMNS_PRODUCTO.Foto1);
            int mPosPrice = mProducts.getColumnIndex(dbconection.COLUMNS_PRODUCTO.PrecioUnitario);
            int mPosSellerId = mProducts.getColumnIndex(dbconection.COLUMNS_PRODUCTO.VendedorId);
            int mPosCalifier = mProducts.getColumnIndex(dbconection.COLUMNS_PRODUCTO.CalificadorId);
            int mPosSubcategoria = mProducts.getColumnIndex(dbconection.COLUMNS_PRODUCTO.SubCategoriaId);
            int mPosModifyDate = mProducts.getColumnIndex(dbconection.COLUMNS_PRODUCTO.FechaActualizacion);
            do {
                //ProductItem(float price, String photo, String productId, Seller seller, Califier califier, String nameSubcategory)
                //ProductItem(float price, String photo, String productId, String photoUrl, Seller seller, Califier califier, String nameSubcategory, String sku, Double productPrice, Subcategory subcategory) {
                mList.add(new ProductItem(
                        mProducts.getFloat(mPosPrice),
                        getStringImage(mProducts, mPosPhoto),
                        mProducts.getString(mPosId),//// TODO: 7/1/2017 preguntar por el ID si es el SKU
                        getMySeller(mProducts.getString(mPosSellerId)),
                        getMyCalifier(mProducts.getString(mPosCalifier)),
                        getSubcategory(mProducts.getString(mPosSubcategoria)).getNameSubcategory(),
                        mProducts.getString(mPosModifyDate)
                ));

            } while (mProducts.moveToNext());
        }

        return mList;
    }
    /*private List<ProductItem> optimizeProductList(List<ProductItem> mList) {
        List<ProductItem> finalList = new ArrayList<>();
        for (int i=0;i<mList.size()-1;i++){
            final ProductItem tmp1Product = mList.get(i);
            for (int j=i+1;j< mList.size();j++){
                final ProductItem tmp2Product = mList.get(j);
                if (tmp1Product.getId()==tmp2Product.getId()) {

                        finalList.add(
                                tmp1Product.getModifyDate().isAfter(tmp2Product.getModifyDate())?tmp1Product:tmp2Product
                        );

                    break;
                }
            }
        }

        return finalList;
    }*/
    @Override
    public List<ProductItem> products(String subcategory) throws IOException {
        // TODO: 6/28/2017 hacer esto offline
        List<ProductItem> mList = new ArrayList<>();
        Cursor mSubcategorie = mDbconection.getSubcategoryByName(subcategory);
        if (mSubcategorie.moveToFirst()) {
            String mIdSubcategorie = mSubcategorie.getString(mSubcategorie.getColumnIndex(dbconection.COLUMNS_SUB_CATEGORIAS.ID));
            Cursor mProducts = mDbconection.getProducts(mIdSubcategorie);
            mList = getListByCursor(mProducts);
        }




        return mList;
    }




    // TODO: 7/1/2017 hacer que todas las imagenes las recojo como byte[] y las mando a convertir a string base64
    private Califier getMyCalifier(String anInt) {

        Cursor myCalifier = mDbconection.getMyCalifier(anInt);
        Califier mResult = null;

        if (myCalifier.moveToFirst()){
            int posId = myCalifier.getColumnIndex(dbconection.COLUMNS_CALIFICADOR.ID);
            int posName = myCalifier.getColumnIndex(dbconection.COLUMNS_CALIFICADOR.Nombre);
            int posCodigo = myCalifier.getColumnIndex(dbconection.COLUMNS_CALIFICADOR.Codigo);
            int posImagen = myCalifier.getColumnIndex(dbconection.COLUMNS_CALIFICADOR.Imagen);

            mResult = new Califier(
                    String.valueOf(myCalifier.getString(posId)),
                    myCalifier.getString(posName),
                    myCalifier.getInt(posCodigo),
                    getStringImage(myCalifier,posImagen)
            );

        }


        return mResult;
    }

    private Seller getMySeller(String anInt) {

        Cursor mSeller = mDbconection.getSeller(anInt);
        Seller mResult = null;
        if (mSeller.moveToFirst()){
            int posId = mSeller.getColumnIndex(dbconection.COLUMNS_OPERARIO.ID);
            int posName = mSeller.getColumnIndex(dbconection.COLUMNS_OPERARIO.Nombre);
            int posLowerPrice = mSeller.getColumnIndex(dbconection.COLUMNS_OPERARIO.PrecioRebaja);
            int posLowerPriceParametre = mSeller.getColumnIndex(dbconection.COLUMNS_OPERARIO.ParametroRebaja);

            mResult = new Seller(
                    mSeller.getString(posId),
                    mSeller.getString(posName),
                    mSeller.getDouble(posLowerPrice),
                    mSeller.getDouble(posLowerPriceParametre)

            );
        }

        return mResult;
    }


    private ProductDetail getProductDetailByCursor(Cursor mProducto){



        int mPosId = mProducto.getColumnIndex(dbconection.COLUMNS_PRODUCTO.ID);
        int mPosSku = mProducto.getColumnIndex(dbconection.COLUMNS_PRODUCTO.Sku);
        int mPosPhoto1 = mProducto.getColumnIndex(dbconection.COLUMNS_PRODUCTO.Foto1);
        int mPosPhoto2 = mProducto.getColumnIndex(dbconection.COLUMNS_PRODUCTO.Foto2);
        int mPosPhoto3 = mProducto.getColumnIndex(dbconection.COLUMNS_PRODUCTO.Foto3);
        int mPosPhoto4 = mProducto.getColumnIndex(dbconection.COLUMNS_PRODUCTO.Foto4);
        int mPosPhoto5 = mProducto.getColumnIndex(dbconection.COLUMNS_PRODUCTO.Foto5);
        int mPosPrice = mProducto.getColumnIndex(dbconection.COLUMNS_PRODUCTO.PrecioUnitario);
        int mPosValueRanking = mProducto.getColumnIndex(dbconection.COLUMNS_PRODUCTO.RankingId);
        int mPosSellerId = mProducto.getColumnIndex(dbconection.COLUMNS_PRODUCTO.VendedorId);
        int mPosCountShop = mProducto.getColumnIndex(dbconection.COLUMNS_PRODUCTO.Existencia);
        int mPosSubcateg = mProducto.getColumnIndex(dbconection.COLUMNS_PRODUCTO.SubCategoriaId);
        int mPosCalifier = mProducto.getColumnIndex(dbconection.COLUMNS_PRODUCTO.CalificadorId);
        int mPosUpdateDate = mProducto.getColumnIndex(dbconection.COLUMNS_PRODUCTO.FechaActualizacion);
        AttributesProductInfo[] mAttributes = getAtriButes(mProducto.getString(mPosId));
       return new ProductDetail(
                String.valueOf(mProducto.getString(mPosId)),
                mProducto.getString(mPosSku),
                getStringImage(mProducto,mPosPhoto1),
                getStringImage(mProducto,mPosPhoto2),
                getStringImage(mProducto,mPosPhoto3),
                getStringImage(mProducto,mPosPhoto4),
                getStringImage(mProducto,mPosPhoto5),
                mProducto.getDouble(mPosPrice),
                getValueRanking(mProducto.getString(mPosValueRanking)),
                getMySeller(mProducto.getString(mPosSellerId)),
                mAttributes,
                mProducto.getInt(mPosCountShop),
                getSubcategory(mProducto.getString(mPosSubcateg)),
                getMyCalifier(mProducto.getString(mPosCalifier)),
               (double)0,   //esto tengo que ver de donde sale
                true        //esto tengo que ver de donde sale
                //mProducto.getString(mPosUpdateDate)
        );


    }




    @Override
    public ProductDetail productDetail(String productId) throws IOException {

            ProductDetail mResult = null;

            final Cursor mProducto = mDbconection.getProductoById(productId);
            if (mProducto.moveToFirst()) {
                mResult = getProductDetailByCursor(mProducto);
            }

        return mResult;
    }

    private ProductDetail getProductDetailByDate(List<ProductDetail> mListProducts) {
        /*ProductDetail mFinalyDetails = null;
        if (mListProducts.size()==0){
            return null;
        }else {
            if (mListProducts.size() == 1) {
                return mListProducts.get(0);
            } else {
                mFinalyDetails = mListProducts.get(0);

                for (int j = 1; j < mListProducts.size(); j++) {
                    if (mFinalyDetails.getUpdateDate().isBefore(mListProducts.get(j).getUpdateDate())) {
                        mFinalyDetails = mListProducts.get(j);
                    }
                }

            }
        }
        return mFinalyDetails;*/
        return null;
    }

    private AttributesProductInfo[] getAtriButes(String mId) {


        int total = mDbconection.getCountAtributes(mId);
        AttributesProductInfo[] mResult = new AttributesProductInfo[total];

        if (total>0){
            Cursor mAllAtributes = mDbconection.getAllAtributes(mId);
            if (mAllAtributes.moveToFirst()){
                int posId = mAllAtributes.getColumnIndex(dbconection.COLUMNS_ATRIBUTO_PRODUCTO.ID);
                int posAtributeId = mAllAtributes.getColumnIndex(dbconection.COLUMNS_ATRIBUTO_PRODUCTO.AtributoId);
                int posValue = mAllAtributes.getColumnIndex(dbconection.COLUMNS_ATRIBUTO_PRODUCTO.ValorAtributo);
                int cont = 0;
                do {

                    Cursor mAtributoInfo = mDbconection.getAtributo(mAllAtributes.getString(posAtributeId));
                    if (mAtributoInfo.moveToFirst()) {
                        int posName = mAtributoInfo.getColumnIndex(dbconection.COLUMNS_ATRIBUTO.Nombre);
                        String mName = mAtributoInfo.getString(posName);
                        String mValue = mAllAtributes.getString(posValue);
                        final AttributesProductInfo mTemp = new AttributesProductInfo(mName, mValue, mAllAtributes.getString(posId));
                        mResult[cont] = mTemp;
                        cont++;
                    }

                }while (mAllAtributes.moveToNext());
            }
        }


        return mResult;
    }

    private Subcategory getSubcategory(String anInt) {
        Subcategory mResult = null;
        Cursor mSubcategory = mDbconection.getSubcategoryById(anInt);
        //(String id, String nameSubcategory, String image) {
        if (mSubcategory.moveToFirst()){

            int posId = mSubcategory.getColumnIndex(dbconection.COLUMNS_SUB_CATEGORIAS.ID);
            int posName = mSubcategory.getColumnIndex(dbconection.COLUMNS_SUB_CATEGORIAS.CNOMBRE);
            int posImage = mSubcategory.getColumnIndex(dbconection.COLUMNS_SUB_CATEGORIAS.CIMAGEN);

            mResult = new Subcategory(
                    String.valueOf(mSubcategory.getString(posId)),
                    mSubcategory.getString(posName),
                    getStringImage(mSubcategory,posImage)
            );
        }

        return mResult;
    }

    private float getValueRanking(String anInt) {
        float mResult = 0;
        Cursor mRanking = mDbconection.getRankingById(anInt);
        if (mRanking.moveToFirst()){
            mResult = mRanking.getInt(mRanking.getColumnIndex(dbconection.COLUMNS_RANKING.ValorRankin));
        }
        return mResult;
    }

    private String getStringImage(Cursor mC, int mPosImg){
        //Drawable result = null;
        final byte[] imageByteArray = mC.getBlob(mPosImg);
        if (imageByteArray != null) {
           //  ByteArrayInputStream imageStream = new ByteArrayInputStream(
           //  imageByteArray);
           //  Bitmap company_logo = BitmapFactory.decodeStream(imageStream);
           //  result = new BitmapDrawable(mDbconection.getMyContext().getResources(), company_logo);
           return Base64.encodeToString(imageByteArray,Base64.DEFAULT);
        }
        else return null;
    }


    @Override
    public List<ProductItem> promotions() throws IOException {
        // TODO: 6/28/2017 hacer esto offline
        // TODO: 8/5/2017 retorna las promocions que se cogen de la BD
        Cursor mPromotions = getPromotion();
        Cursor mNuevos = getNuevos();
        Cursor mOfertas = getOfertas();
        List<ProductItem> mPromotionsItemsTemp = getProductItem(mPromotions);
        List<ProductItem> mNuevosItemsTemp = getProductItem(mNuevos);
        List<ProductItem> mOfertasItemsTemp = getProductItem(mOfertas);

        List<ProductItem> mPromotionsItems = new ArrayList<>();
        List<ProductItem> mNuevosItems = new ArrayList<>();
        List<ProductItem> mOfertasItems = new ArrayList<>();

        int[] mPosPromotions = new int[]{0,1,2,3,4};
        int[] mPosNuevos = new int[]{0,1};
        int[] mPosOfertas = new int[]{0};

        if (mPromotionsItemsTemp.size()>5)
            mPosPromotions = createRandomPos(mPromotionsItemsTemp.size());

        if (mNuevosItemsTemp.size()>2)
            mPosNuevos = createRandomPos(mNuevosItemsTemp.size());

        if (mOfertasItemsTemp.size()>1)
            mPosOfertas = createRandomPos(mOfertasItemsTemp.size());

        for (int i :
                mPosPromotions) {
            mPromotionsItems.add(mPromotionsItemsTemp.get(i));
        }

        for (int i :
                mPosNuevos) {
            mNuevosItems.add(mNuevosItemsTemp.get(i));
        }

        for (int i :
                mPosOfertas) {
            mOfertasItems.add(mOfertasItemsTemp.get(i));
        }

        List<ProductItem> mResult = new ArrayList<>();


        if (mPromotionsItems.get(3)!=null)
            mResult.add(mPromotionsItems.get(3));
        if (mNuevosItems.get(1)!=null)
            mResult.add(mNuevosItems.get(1));
        if (mPromotionsItems.get(5)!=null)
            mResult.add(mPromotionsItems.get(5));
        if (mOfertasItems.get(1)!=null)
            mResult.add(mOfertasItems.get(1));
        if (mPromotionsItems.get(2)!=null)
            mResult.add(mPromotionsItems.get(2));
        if (mNuevosItems.get(2)!=null)
            mResult.add(mNuevosItems.get(2));
        if (mPromotionsItems.get(3)!=null)
            mResult.add(mPromotionsItems.get(4));
        if (mPromotionsItems.get(3)!=null)
            mResult.add(mPromotionsItems.get(1));

        return mResult;
    }

    private List<ProductItem> getProductItem(Cursor mProducts) {

        List<ProductItem> mList = new ArrayList<>();


        if (mProducts.moveToFirst()) {
                /*
                *   private int Id;
                    private String ProductId;
                    private String Photo;
                    private float Price;
                    private Seller Seller;
                    private Califier Califier;
                    private String NameSubcategory;
                * */

            int mPosId = mProducts.getColumnIndex(dbconection.COLUMNS_PRODUCTO.ID);
            int mPosSku = mProducts.getColumnIndex(dbconection.COLUMNS_PRODUCTO.Sku);
            int mPosPhoto = mProducts.getColumnIndex(dbconection.COLUMNS_PRODUCTO.Foto1);
            int mPosPrice = mProducts.getColumnIndex(dbconection.COLUMNS_PRODUCTO.PrecioUnitario);
            int mPosSellerId = mProducts.getColumnIndex(dbconection.COLUMNS_PRODUCTO.VendedorId);
            int mPosCalifier = mProducts.getColumnIndex(dbconection.COLUMNS_PRODUCTO.CalificadorId);
            int mPosModifyDate = mProducts.getColumnIndex(dbconection.COLUMNS_PRODUCTO.FechaActualizacion);
            int mPosSubcategorieId = mProducts.getColumnIndex(dbconection.COLUMNS_PRODUCTO.SubCategoriaId);
            do {
                //ProductItem(float price, String photo, String productId, Seller seller, Califier califier, String nameSubcategory)
                mList.add(new ProductItem(
                        mProducts.getFloat(mPosPrice),
                        getStringImage(mProducts, mPosPhoto),
                        mProducts.getString(mPosId),//// TODO: 7/1/2017 preguntar por el ID si es el SKU
                        getMySeller(mProducts.getString(mPosSellerId)),
                        getMyCalifier(mProducts.getString(mPosCalifier)),
                        getSubcategory(mProducts.getString(mPosSubcategorieId)).getNameSubcategory(),
                        mProducts.getString(mPosModifyDate)
                ));

            } while (mProducts.moveToNext());
        }

        return mList;
    }

    private int[] createRandomPos(int count) {

        int[] mr = new int[count];
        Random mR = new Random();
        int mN = 0 ;

        mN = mR.nextInt(count);

        for (int i = 0; i<count;i++){
            mr[i] = mN;
            if (mN+1 < count){
                mN++;
            }else
                mN = 0;
        }

        return mr;
    }

    private boolean exist(int mN, int[] mr) {

        for (int i : mr)
            if (i==mN) return true;

        return false;
    }


    public Cursor getPromotion(){
        return mDbconection.getCalificador(mDbconection.getCalificadorId(2));
    }
    public Cursor getNuevos(){
        return mDbconection.getCalificador(mDbconection.getCalificadorId(1));
    }
    public Cursor getOfertas(){
        return mDbconection.getCalificador(mDbconection.getCalificadorId(3));
    }

    @Override
    public List<String> images(String productId) throws IOException {
        List<String> mResult = new ArrayList<>();
        Cursor mProducto = mDbconection.getProductoById(productId);
        if (mProducto.moveToFirst()) {
            int mPosPhoto1 = mProducto.getColumnIndex(dbconection.COLUMNS_PRODUCTO.Foto1);
            int mPosPhoto2 = mProducto.getColumnIndex(dbconection.COLUMNS_PRODUCTO.Foto2);
            int mPosPhoto3 = mProducto.getColumnIndex(dbconection.COLUMNS_PRODUCTO.Foto3);
            int mPosPhoto4 = mProducto.getColumnIndex(dbconection.COLUMNS_PRODUCTO.Foto4);
            int mPosPhoto5 = mProducto.getColumnIndex(dbconection.COLUMNS_PRODUCTO.Foto5);


            String m1 = getStringImage(mProducto,mPosPhoto1);
            if (m1!=null)mResult.add(m1);

            String m2 = getStringImage(mProducto,mPosPhoto2);
            if (m2!=null)mResult.add(m2);

            String m3 = getStringImage(mProducto,mPosPhoto3);
            if (m3!=null)mResult.add(m3);

            String m4 = getStringImage(mProducto,mPosPhoto4);
            if (m4!=null)mResult.add(m4);

            String m5 = getStringImage(mProducto,mPosPhoto5);
            if (m5!=null)mResult.add(m5);

        }

        return mResult;
    }

    @Override
    public AcumulatedPoint acumulatedPoints(String userId) throws IOException {

        // TODO: 7/1/2017 preguntar si necesito esto

        return null;
    }

    @Override
    public void changePassword(UserChangePassword param, Context context) throws IOException, IncorrectPasswordException, InvalidPasswordFormatException {

    }

    @Override
    public CheckoutLoteryCode checkOutLoteryCode(String code) throws IOException, CuponDontExistException {
        // TODO: 7/1/2017 esto no lo necesito hacer
        return null;
    }

    public List<Seller> getSellers(){
        Cursor mCursorSellet = mDbconection.getAllSellersId();
        List<Seller> mResult = new ArrayList<>();

        if (mCursorSellet.moveToFirst()){
            int posId = mCursorSellet.getColumnIndex(dbconection.COLUMNS_OPERARIO.ID);
            do {

                mResult.add(getMySeller(mCursorSellet.getString(posId)));

            }while (mCursorSellet.moveToNext());
        }


        return mResult;
    }

    @Override
    public List<PartialSeller> getPartialSellers(int page) {
        // TODO: 7/1/2017 preguntar como obtengo los acumulates points
        // TODO: 6/28/2017 hacer esto offline
        List<PartialSeller>  mResults  = new ArrayList<>();
        List<Seller> mSeller = getSellers();

        for (Seller mySeller: mSeller)
            mResults.add(getSeller(mySeller.getId()));

        return mResults;
    }

    @Override
    public PartialSeller getSeller(String Id) {
        // TODO: 7/1/2017 convertir el id para string
        //\String id, String name, String lastName, String email, String phone, String sex,
        // Double acumulatedPoint, String specification, String image, Boolean vip, String token
        Cursor mSeller = mDbconection.getSeller(Id);
        PartialSeller mResult = null;
        if (mSeller.moveToFirst()){
            int posId = mSeller.getColumnIndex(dbconection.COLUMNS_OPERARIO.ID);
            int posName = mSeller.getColumnIndex(dbconection.COLUMNS_OPERARIO.Nombre);
            int posLowerPrice = mSeller.getColumnIndex(dbconection.COLUMNS_OPERARIO.PrecioRebaja);
            int posLowerPriceParametre = mSeller.getColumnIndex(dbconection.COLUMNS_OPERARIO.ParametroRebaja);
            int posEmail = mSeller.getColumnIndex(dbconection.COLUMNS_OPERARIO.Correo);
            int posPhone = mSeller.getColumnIndex(dbconection.COLUMNS_OPERARIO.Telefono);
            int posSex = mSeller.getColumnIndex(dbconection.COLUMNS_OPERARIO.Sex);
            //int posPoints = mSeller.getColumnIndex(dbconection.COLUMNS_OPERARIO.);
            int posEspecificaciones = mSeller.getColumnIndex(dbconection.COLUMNS_OPERARIO.Especificaciones);
            int posImage = mSeller.getColumnIndex(dbconection.COLUMNS_OPERARIO.Imagen);
            int posVip = mSeller.getColumnIndex(dbconection.COLUMNS_OPERARIO.Vip);
            int posToken = mSeller.getColumnIndex(dbconection.COLUMNS_OPERARIO.Token);
            int posBanner = mSeller.getColumnIndex(dbconection.COLUMNS_OPERARIO.Banner);
            //(String id, String name, String lastName, String email, String phone, String sex, Double acumulatedPoint,
            // String specification, String image, Boolean vip, String token)

            mResult = new PartialSeller(
                    mSeller.getString(posId),
                    mSeller.getString(posName),
                    mSeller.getString(posName),
                    mSeller.getString(posEmail),
                    mSeller.getString(posPhone),
                    mSeller.getString(posSex),
                    (double)0,
                    mSeller.getString(posEspecificaciones),
                    getStringImage(mSeller,posImage),
                    mSeller.getInt(posVip) == 1,
                    mSeller.getString(posToken),
                    getStringImage(mSeller,posBanner)
            );
        }



        return mResult;


    }

    @Override
    public int getRankingSeller(String id) {

        Cursor mSeller = mDbconection.getSeller(id);
        int posRanking = mSeller.getColumnIndex(dbconection.COLUMNS_OPERARIO.RankingId);
        mSeller.moveToFirst();
        String mRankingId = mSeller.getString(posRanking);
        return (int)getValueRanking(mRankingId);
    }

    @Override
    public void updateUser(UserModify param, Context context) throws IOException {

    }

    @Override
    public void validate() {
        getSellers();
    }

    @Override
    public void updateContactUser(String id, Contact[] contacts) throws IOException {
        //throw new IOException("Esta funcion no trabaja offline");

    }

    @Override
    public List<com.llegoati.llegoati.models.Request> requestHistory(String clientId, boolean includeProducts) throws IOException {
        //throw new IOException("Esta funcion no trabaja offline");
        return new ArrayList<>();
    }

    @Override
    public List<Cupon> loteryCodeHistory(String userId) throws IOException {
        //throw new IOException("Esta funcion no trabaja offline");

        return new ArrayList<>();

    }

    @Override
    public com.llegoati.llegoati.models.Request request(String requestId) throws IOException {
        //throw new IOException("Esta funcion no trabaja offline");
        return null;
    }

    @Override
    public void commentProduct(String comment, String userId, String requestId, String productId) throws IOException {
        //throw new IOException("Esta funcion no trabaja offline");

    }

    @Override
    public void rateProduct(String productId, String userId, String requestId, Integer ratingProduct) throws IOException {
       // throw new IOException("Esta funcion no trabaja offline");
    }

    @Override
    public void rateMessenger(String productId, String userId, Integer ratingMessenger, String requestId) throws IOException {
       // throw new IOException("Esta funcion no trabaja offline");
    }

    @Override
    public List<MessengerPrice> messengerPrices() throws IOException {

        Cursor mPrices = mDbconection.getMessengerPrices();
        List<MessengerPrice> messengerPrices = new ArrayList<>();

        if (mPrices.moveToFirst()){

            int posId = mPrices.getColumnIndex(dbconection.COLUMNS_MUNICIPIO_PRODUCTO.ID);
            int posDestId = mPrices.getColumnIndex(dbconection.COLUMNS_MUNICIPIO_PRODUCTO.DestinoId);
            int posProducId = mPrices.getColumnIndex(dbconection.COLUMNS_MUNICIPIO_PRODUCTO.ProductoId);
            int posPrecio = mPrices.getColumnIndex(dbconection.COLUMNS_MUNICIPIO_PRODUCTO.PrecioMensajeria);


            do {

            }while (mPrices.moveToNext());
        }


        throw new IOException("Esta funcion no trabaja offline");
    }

    @Override
    public void checkoutShoppingCart(ShoppingCartOrder shoppingCartOrder) throws IOException {

        //TODO: 7/29/2017 crear la orden a partir del shoppingCartOrder
        //Asunto: pedidollegoati
        //Body: <order> Orden de acuerdo con el documento </order>

        String mResponse = "<order>";
        Iterator<ShoppingCartItem> mItems = shoppingCartOrder.getItems();
        mResponse += Constants.ID_APP
                + shoppingCartOrder.getClientId()
                + SMS_TYPE_PEDIDO
                + "000"
        ;



        while (mItems.hasNext()) {
            final ShoppingCartItem item = mItems.next();


        }










        mResponse +="</order>";



    }

    @Override
    public List<Adds> adds() throws IOException {

        Cursor mAdds = mDbconection.getAllAdds();
        List<Adds> mResult = new ArrayList<>();
        if (mAdds.moveToFirst()){
            int posId = mAdds.getColumnIndex(dbconection.COLUMNS_ANUNCIO.ID);
            int posIdSubcategorie= mAdds.getColumnIndex(dbconection.COLUMNS_ANUNCIO.IdSubcategoria);
            int posPrecio= mAdds.getColumnIndex(dbconection.COLUMNS_ANUNCIO.Precio);
            int posTelefono= mAdds.getColumnIndex(dbconection.COLUMNS_ANUNCIO.Telefono);
            int posTitulo= mAdds.getColumnIndex(dbconection.COLUMNS_ANUNCIO.Titulo);
            int posUrl= mAdds.getColumnIndex(dbconection.COLUMNS_ANUNCIO.Url);

            do {
                final Adds mTempAdd = new Adds();
                mTempAdd.setId(mAdds.getString(posId));
                mTempAdd.setIdSubcateg(mAdds.getString(posIdSubcategorie));
                mTempAdd.setPrice(new Price(mAdds.getDouble(posPrecio),0));
                mTempAdd.setPhoneNumber(new PhoneNumber(mAdds.getString(posTelefono)));
                mTempAdd.setTitle(mAdds.getString(posTitulo));
                mTempAdd.setUrl(mAdds.getString(posUrl));
                mResult.add(mTempAdd);
            }while (mAdds.moveToNext());

        }

        return mResult;
    }

    @Override
    public List<News> news() throws IOException {

        List<News> mResult = new ArrayList<>();
        Cursor mNews = mDbconection.getAllNews();

        if (mNews.moveToFirst()){

            int posId = mNews.getColumnIndex(dbconection.COLUMNS_NOTICIA.ID);
            int posTitulo= mNews.getColumnIndex(dbconection.COLUMNS_NOTICIA.Titulo);
            int posResumen= mNews.getColumnIndex(dbconection.COLUMNS_NOTICIA.Resumen);
            int posUrl= mNews.getColumnIndex(dbconection.COLUMNS_NOTICIA.Url);
            int posFuente= mNews.getColumnIndex(dbconection.COLUMNS_NOTICIA.Fuente);
            int posTag= mNews.getColumnIndex(dbconection.COLUMNS_NOTICIA.Tag);
            int posFecha= mNews.getColumnIndex(dbconection.COLUMNS_NOTICIA.Fecha);
            int posPublicada= mNews.getColumnIndex(dbconection.COLUMNS_NOTICIA.Publicada);
            int posLogo= mNews.getColumnIndex(dbconection.COLUMNS_NOTICIA.Logo);
            do {
                final News mTempNew = new News();
                mTempNew.setId(mNews.getString(posId));
                mTempNew.setTitle(mNews.getString(posTitulo));
                mTempNew.setAbstrac(mNews.getString(posResumen));
                mTempNew.setUrl(mNews.getString(posUrl));
                mTempNew.setSource(mNews.getString(posFuente));
                mTempNew.setTag(mNews.getString(posTag));
                mTempNew.setDate(mNews.getString(posFecha));
                mTempNew.setPublished(mNews.getInt(posPublicada)==1);
                mTempNew.setLogo(getStringImage(mNews,posLogo));
                mResult.add(mTempNew);
            }while (mNews.moveToNext());
        }

        return mResult;
    }

    @Override
    public List<Information> informations() throws IOException {

        List<Information> mResult = new ArrayList<>();

        Cursor mInformation = mDbconection.getAllInformation();

        if (mInformation.moveToFirst()){

            int posId = mInformation.getColumnIndex(dbconection.COLUMNS_PREGUNTAS_FRECUENTES.ID);
            int posPregunta = mInformation.getColumnIndex(dbconection.COLUMNS_PREGUNTAS_FRECUENTES.Pregunta);
            int posRespuesta = mInformation.getColumnIndex(dbconection.COLUMNS_PREGUNTAS_FRECUENTES.Respuesta);
            int posFecha = mInformation.getColumnIndex(dbconection.COLUMNS_PREGUNTAS_FRECUENTES.Fecha);
            int posOrden = mInformation.getColumnIndex(dbconection.COLUMNS_PREGUNTAS_FRECUENTES.Orden);

            do {

                final Information mTempInformation = new Information();
                mTempInformation.setId(mInformation.getString(posId));
                mTempInformation.setQuestion(mInformation.getString(posPregunta));
                mTempInformation.setAnswer(mInformation.getString(posRespuesta));
                mTempInformation.setDate(mInformation.getString(posFecha));
                mTempInformation.setDisplayOrder(mInformation.getInt(posOrden));

                mResult.add(mTempInformation);

            }while (mInformation.moveToNext());
        }
        return mResult;
    }

    @Override
    public List<Event> events() throws IOException {

        List<Event> mResult = new ArrayList<>();
        Cursor mEvents = mDbconection.getAllEvents();
        int i = 0;
        if (mEvents.moveToFirst()){

            int posId = mEvents.getColumnIndex(dbconection.COLUMNS_EVENTO.ID);
            int posDescripcion = mEvents.getColumnIndex(dbconection.COLUMNS_EVENTO.Descripcion);
            int posClasificacion = mEvents.getColumnIndex(dbconection.COLUMNS_EVENTO.Clasificacion);
            int posAleatorio = mEvents.getColumnIndex(dbconection.COLUMNS_EVENTO.Aleatorio);
            int posFront = mEvents.getColumnIndex(dbconection.COLUMNS_EVENTO.Front);
            int posFrontDescripcion = mEvents.getColumnIndex(dbconection.COLUMNS_EVENTO.FrontDescripcion);
            int posActivo = mEvents.getColumnIndex(dbconection.COLUMNS_EVENTO.Activo);
            int posFechaPublicacion = mEvents.getColumnIndex(dbconection.COLUMNS_EVENTO.FechaPublicacion);
            int posFechaInicio = mEvents.getColumnIndex(dbconection.COLUMNS_EVENTO.FechaInicio);
            int posFin = mEvents.getColumnIndex(dbconection.COLUMNS_EVENTO.FechaFin);
            int posPieImagen1 = mEvents.getColumnIndex(dbconection.COLUMNS_EVENTO.PieImagen1);
            int posPieImagen2 = mEvents.getColumnIndex(dbconection.COLUMNS_EVENTO.PieImagen2);
            int posPieImagen3 = mEvents.getColumnIndex(dbconection.COLUMNS_EVENTO.PieImagen3);
            int posPieImagen4 = mEvents.getColumnIndex(dbconection.COLUMNS_EVENTO.PieImagen4);
            int posPieImagen5 = mEvents.getColumnIndex(dbconection.COLUMNS_EVENTO.PieImagen5);
            Cursor mImage = null;

            do {
                final Event mTempEvent = new Event();
                mTempEvent.setId(mEvents.getString(posId));
                mTempEvent.setDescription(mEvents.getString(posDescripcion));
                mTempEvent.setClasification(mEvents.getString(posClasificacion));
                mTempEvent.setAleatory(mEvents.getInt(posAleatorio)==1);
                mTempEvent.setAlwysFront(mEvents.getInt(posFront)==1);
                mTempEvent.setFrontDescription(
                        mTempEvent.isAlwysFront()?
                        new FrontDescription(mEvents.getString(posFrontDescripcion)):
                                new FrontDescription()
                );
                mTempEvent.setActive(mEvents.getInt(posActivo)==1);
                mTempEvent.setDatePublish(mEvents.getString(posFechaPublicacion));
                mTempEvent.setDatePublishString(mEvents.getString(posFechaPublicacion));//preguntar que es esto
                mTempEvent.setEventStart(mEvents.getString(posFechaInicio));
                mTempEvent.setEventEnd(mEvents.getString(posFin));


                //final Cursor mImage2 = mDbconection.getEventsImages(mEvents.getString(posId), dbconection.COLUMNS_IMAGE_EVENTS.Imagen2);
               // final Cursor mImage3 = mDbconection.getEventsImages(mEvents.getString(posId), dbconection.COLUMNS_IMAGE_EVENTS.Imagen3);
               // final Cursor mImage4 = mDbconection.getEventsImages(mEvents.getString(posId), dbconection.COLUMNS_IMAGE_EVENTS.Imagen4);
                //final Cursor mImage5 = mDbconection.getEventsImages(mEvents.getString(posId), dbconection.COLUMNS_IMAGE_EVENTS.Imagen5);
                List<String> mPiesDePagina = new ArrayList<>();

                mPiesDePagina.add(mEvents.getString(posPieImagen1));
                mPiesDePagina.add(mEvents.getString(posPieImagen2));
                mPiesDePagina.add(mEvents.getString(posPieImagen3));
                mPiesDePagina.add(mEvents.getString(posPieImagen4));
                mPiesDePagina.add(mEvents.getString(posPieImagen5));
                final List<String> mImageBase = new ArrayList<>();

                final Cursor mImage1 = mDbconection.getEventsImages(mEvents.getString(posId), dbconection.COLUMNS_IMAGE_EVENTS.Imagen1);
                if (mImage1.moveToFirst()) {
                    final String mI1 = getStringImage(mImage1,0);
                    if (mI1 != null) mImageBase.add(mI1);
                }
                final Cursor mImage2 = mDbconection.getEventsImages(mEvents.getString(posId), dbconection.COLUMNS_IMAGE_EVENTS.Imagen2);
                if (mImage2.moveToFirst()) {
                    final String mI1 = getStringImage(mImage2,0);
                    if (mI1 != null) mImageBase.add(mI1);
                }
                /*final Cursor mImage3 = mDbconection.getEventsImages(mEvents.getString(posId), dbconection.COLUMNS_IMAGE_EVENTS.Imagen3);
                if (mImage3.moveToFirst()) {
                    final String mI1 = getStringImage(mImage3,0);
                    if (mI1 != null) mImageBase.add(mI1);
                }
                final Cursor mImage4 = mDbconection.getEventsImages(mEvents.getString(posId), dbconection.COLUMNS_IMAGE_EVENTS.Imagen4);
                if (mImage4.moveToFirst()) {
                    final String mI1 = getStringImage(mImage4,0);
                    if (mI1 != null) mImageBase.add(mI1);
                }
                final Cursor mImage5 = mDbconection.getEventsImages(mEvents.getString(posId), dbconection.COLUMNS_IMAGE_EVENTS.Imagen5);
                if (mImage5.moveToFirst()) {
                    final String mI1 = getStringImage(mImage5,0);
                    if (mI1 != null) mImageBase.add(mI1);
                }*/
                       /* final String mI2 = getStringImage(mImage,mImage.getColumnIndex(dbconection.COLUMNS_IMAGE_EVENTS.Imagen2));
                        if (mI2!=null) mImageBase.add(mI1);
                        final String mI3 = getStringImage(mImage,mImage.getColumnIndex(dbconection.COLUMNS_IMAGE_EVENTS.Imagen3));
                        if (mI3!=null) mImageBase.add(mI1);
                        final String mI4 = getStringImage(mImage,mImage.getColumnIndex(dbconection.COLUMNS_IMAGE_EVENTS.Imagen4));
                        if (mI4!=null) mImageBase.add(mI1);
                        final String mI5 = getStringImage(mImage,mImage.getColumnIndex(dbconection.COLUMNS_IMAGE_EVENTS.Imagen5));
                        if (mI5!=null) mImageBase.add(mI1);*/
                    if (mImageBase.size()>0) {
                        final ImagesItem[] mImagesItems = new ImagesItem[mImageBase.size()];
                        i = 0;
                        for (String mIma : mImageBase) {
                            mImagesItems[i] = getImageItem(mIma, mPiesDePagina.get(i), "offline-mode");
                            i++;
                        }
                        mTempEvent.setImages(mImagesItems);
                    }









                // TODO: 8/22/2017 faltan lo de los pie de imagen

                mResult.add(mTempEvent);

            }while (mEvents.moveToNext());
        }

        return mResult;
    }

    private ImagesItem getImageItem(String s, String s1,String mUrl) {
        ImagesItem mImageItem = new ImagesItem();
        mImageItem.setPieTextImage(s1);
        mImageItem.setImage(s);
        mImageItem.setImageUrl(mUrl);

        return mImageItem;
    }

    @Override
    public List<ProductItem> search(String query, int pageIndex, int pageSize, String artisanId, String provinceId, Boolean withMessenger) throws IOException {

        //List<ProductItem> mList = new ArrayList<>();
        Cursor mItems = mDbconection.search(query,pageIndex,pageSize,artisanId,provinceId,withMessenger);

        return getListByCursor(mItems);
    }

    @Override
    public List<SelectionSeller> sellers() throws IOException {

        List<SelectionSeller> mResult = new ArrayList<>();

        for (Seller mS : getSellers()){
            final SelectionSeller mTempSeller = new SelectionSeller();
            mTempSeller.setId(mS.getId());
            mTempSeller.setName(mS.getName());
            mTempSeller.setImage(new Image(getSeller(mS.getId()).getImage()));
            mResult.add(mTempSeller);
        }

        return mResult;
    }

    @Override
    public List<Province> provinces() throws IOException {
        // TODO: 8/22/2017 ver de donde saco esto
        Cursor mProvinces = mDbconection.getProvinces();
        List<Province> mResult = new ArrayList<>();
        if (mProvinces.moveToFirst()){

            int posId = mProvinces.getColumnIndex(dbconection.COLUMNS_PROVINCIA.ID);
            int posName = mProvinces.getColumnIndex(dbconection.COLUMNS_PROVINCIA.Nombre);
            int posCode = mProvinces.getColumnIndex(dbconection.COLUMNS_PROVINCIA.Codigo);

            do {
                final Province tempPro = new Province();
                tempPro.setName(mProvinces.getString(posName));
                tempPro.setId(mProvinces.getString(posId));
                tempPro.setCode(mProvinces.getInt(posCode));
                mResult.add(tempPro);
            }while (mProvinces.moveToNext());

        }

        return mResult;
    }

    @Override
    public boolean avaliableSms() {
        return false;
    }

    @Override
    public UserConfiguration getUserConfiguration(String token) {
        return null;
    }

    @Override
    public boolean sendMessageOnline(String fromID, String toId, String subject, String body) {
        SendMessage.sendMessage(mDbconection.getMyContext(), fromID,
                toId,
                body);
        return true;
    }
}
