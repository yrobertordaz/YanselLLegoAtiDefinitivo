package com.llegoati.llegoati.infrastructure.concrete;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.llegoati.llegoati.smsmodulo.Utils.Constants;

import java.io.File;

/**
 * Created by Richard on 6/30/2017.
 */

public class dbconection extends SQLiteOpenHelper {

    private static final String TABLE_PROVINCES = "PROVINCIA";
    private static String DB_PATH;
    private static String DB_NAME;
    private Cursor allSellersId;
    private Cursor promotion;
    private Cursor nuevos;
    private Cursor ofertas;
    private Cursor messengerPrices;

    public SQLiteDatabase getMyDataBase() {
        return myDataBase;
    }

    public void setMyDataBase(String path,String name) {

        this.DB_NAME = name;
        this.DB_PATH = path;
        String myPath = DB_PATH + File.separator + DB_NAME;
        this.myDataBase = SQLiteDatabase.openDatabase(myPath, null,
                SQLiteDatabase.OPEN_READWRITE);
    }

    private SQLiteDatabase myDataBase;

    public Context getMyContext() {
        return myContext;
    }

    public void setMyContext(Context myContext) {
        this.myContext = myContext;
    }

    public Context myContext;




    @SuppressWarnings("static-access")
    public dbconection(Context contexto, String path, String name) {
        super(contexto, name, null, 2);
        this.DB_NAME = name;
        this.DB_PATH = path;
        this.myContext = contexto;
        String myPath = DB_PATH + File.separator + DB_NAME;
        this.myDataBase = SQLiteDatabase.openDatabase(myPath, null,
                SQLiteDatabase.OPEN_READWRITE);
    }


    @Override
    public void onCreate(SQLiteDatabase db) {

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }

    public static final String TABLE_ANUNCIO = "ANUNCIO";
    public static final String TABLE_ATRIBUTO = "ATRIBUTO";
    public static final String TABLE_ATRIBUTO_PRODUCTO = "ATRIBUTO_PRODUCTO";
    public static final String TABLE_CALIFICADOR_PRODUCTO = "CALIFICADOR_PODUCTO";//CALIFICADOR_PODUCTO
    public static final String TABLE_CARROCOMPRA = "CARROCOMPRA";
    public static final String TABLE_CATEGORIA = "CATEGORIA";
    public static final String TABLE_CLIENTE_CUPONREBAJA = "CLIENTE_CUPONREBAJA";
    public static final String TABLE_COLOR = "COLOR";
    public static final String TABLE_COLOR_PRODUCTO = "COLOR_PRODUCTO";
    public static final String TABLE_COMENTARIO = "COMENTARIO";
    public static final String TABLE_CONFIGURACION = "CONFIGURACION";
    public static final String TABLE_CONTACTO_CLIENTE = "CONTACTO_CLIENTE";
    public static final String TABLE_CUPONREBAJA = "CUPONREBAJA";
    public static final String TABLE_CISTOM_STORIES= "CustomStories";
    public static final String TABLE_EVENTO = "EVENTO";
    public static final String TABLE_HISTORICO_PUNTOS = "HISTORICO_PUNTOS";
    public static final String TABLE_MATERIAL = "MATERIAL";
    public static final String TABLE_MATERIAL_CATEGORIA = "MATERIAL_CATEGORIA";
    public static final String TABLE_MENSAJE = "MENSAJE";
    public static final String TABLE_MUNICIPIO = "MUNICIPIO";
    public static final String TABLE_MUNICIPIO_PRODUCTO = "MUNICIPIO_PRODUCTO";
    public static final String TABLE_NOTICIA = "NOTICIA";
    public static final String TABLE_OPERARIO = "OPERARIO";
    public static final String TABLE_PEDIDO = "PEDIDO";
    public static final String TABLE_PEDIDO_PRODUCTO = "PEDIDO_PRODUCTO";
    public static final String TABLE_PREGUNTAS_FRECUENTES = "PREGUNTAS_FRECUENTES";
    public static final String TABLE_PRODUCTO = "PRODUCTO";
    public static final String TABLE_IMAGE_EVENT = "EVENTO_IMAGEN";
    public static final String TABLE_RANKING = "RANKING";
    public static final String TABLE_RANKING_CLIENTE_PRODUCTO = "RANKING_CLIENTE_PRODUCTO";
    public static final String TABLE_REGISTRO_INTEGRACION = "REGISTRO_INTEGRACION";
    public static final String TABLE_SUBCATEGORIA = "SUBCATEGORIA";
    public static final String TABLE_TALLAS_NUMEROS = "TALLAS_NUMEROS";


    public static final String STRING_TYPE = "TEXT";
    public static final String BOOLEAN_TYPE = "BOOLEAN";
    public static final String INTEGER_TYPE = "INTEGER";
    public static final String FLOAT_TYPE = "FLOAT";
    public static final String DATE_TYPE = "DATETIME";
    public static final String BOLD_TYPE = "BOLB";

    public Cursor getSubcategories(String anInt) {
        return this.myDataBase.query(
                TABLE_SUBCATEGORIA,
                null,
                String.format("%s = ?", COLUMNS_SUB_CATEGORIAS.CCATEGORIE_ID),
                new String[]{String.valueOf(anInt)},
                null,
                null,
                null
        );
    }

    public Cursor getProducts(String subcategoryId) {
        return myDataBase.query(
                TABLE_PRODUCTO,
                null,
                String.format("%s = ?", COLUMNS_PRODUCTO.SubCategoriaId),
                new String[]{String.valueOf(subcategoryId)},
                null,
                null,
                COLUMNS_PRODUCTO.FechaActualizacion
        );
    }

    public Cursor getSubcategoryByName(String subcategory) {
        return myDataBase.query(
                TABLE_SUBCATEGORIA,
                new String[]{COLUMNS_SUB_CATEGORIAS.ID},
                String.format("%s LIKE ?", COLUMNS_SUB_CATEGORIAS.CNOMBRE),
                new String[]{"%"+String.valueOf(subcategory)+"%"},
                null,
                null,
                null
                );
    }

    public Cursor getSeller(String mId)  {


        //return myDataBase.rawQuery(query,null);

        return myDataBase.query(
                TABLE_OPERARIO,
                null,
                String.format("%s = ?", COLUMNS_OPERARIO.ID),
                new String[]{String.valueOf(mId)},
                null,
                null,
                null
        );
    }

    public Cursor getMyCalifier(String anInt) {
        return  myDataBase.query(
                TABLE_CALIFICADOR_PRODUCTO,
                null,
                String.format("%s = ?", COLUMNS_CALIFICADOR.ID),
                new String[]{String.valueOf(anInt)},
                null,
                null,
                null);
    }

    public Cursor getProductoById(String productId) {
        return  myDataBase.query(
                TABLE_PRODUCTO,
                null,
                String.format("%s = ?", COLUMNS_PRODUCTO.ID),
                new String[]{productId},
                null,
                null,
                null);
    }

    public Cursor getSubcategoryById(String anInt) {
        return myDataBase.query(
                TABLE_SUBCATEGORIA,
                null,
                String.format("%s = ?", COLUMNS_SUB_CATEGORIAS.ID),
                new String[]{String.valueOf(anInt)},
                null,
                null,
                null
        );
    }

    public Cursor getRankingById(String anInt) {
        return myDataBase.query(
                TABLE_RANKING,
                null,
                String.format("%s = ?", COLUMNS_RANKING.ID),
                new String[]{String.valueOf(anInt)},
                null,
                null,
                null);
    }

    public Cursor getAllSellersId() {
        return myDataBase.query(
                TABLE_OPERARIO,
                new String[]{COLUMNS_OPERARIO.ID},
                null,
                null,
                null,
                null,
                null);
    }

    public int getCountAtributes(String mId) {

        Cursor mCount = myDataBase.query(
                TABLE_ATRIBUTO_PRODUCTO,
                new String[]{COLUMNS_ATRIBUTO_PRODUCTO.ID},
                String.format("%s = ?", COLUMNS_ATRIBUTO_PRODUCTO.ProductoId),
                new String[]{String.valueOf(mId)}, null,
                null,
                null);

        if (mCount.moveToFirst()){
            return mCount.getCount();
        }else {
            return 0;
        }

    }


    public Cursor getAllAtributes(String mId){
            return myDataBase.query(
                TABLE_ATRIBUTO_PRODUCTO,
                null,
                String.format("%s = ?", COLUMNS_ATRIBUTO_PRODUCTO.ProductoId),
                new String[]{String.valueOf(mId)}, null,
                null,
                null);

    }

    public Cursor getAtributo(String mId) {

        return myDataBase.query(
                TABLE_ATRIBUTO,
                null,
                String.format("%s = ?", COLUMNS_ATRIBUTO.ID),
                new String[]{String.valueOf(mId)}, null,
                null,
                null);



    }

    public Cursor getPromotion() {
        return myDataBase.query(
                TABLE_PRODUCTO,
                new String[]{COLUMNS_PRODUCTO.ID},
                String.format("%s = ?", COLUMNS_PRODUCTO.CalificadorId),
                new String[]{String.valueOf(2)},
                null,
                null,
                null);
    }

    public Cursor getCalificador(String mId) {
        return myDataBase.query(
                TABLE_PRODUCTO,
                null,
                String.format("%s = ?", COLUMNS_PRODUCTO.CalificadorId),
                new String[]{String.valueOf(mId)},
                null,
                null,
                null);
    }

    public Cursor getNuevos() {
        return myDataBase.query(
                TABLE_PRODUCTO,
                new String[]{COLUMNS_PRODUCTO.ID},
                String.format("%s = ?", COLUMNS_PRODUCTO.Promocionado),
                new String[]{String.valueOf(1)},
                null,
                null,
                null);
    }

    public Cursor getOfertas() {
        return myDataBase.query(
                TABLE_PRODUCTO,
                new String[]{COLUMNS_PRODUCTO.ID},
                String.format("%s = ?", COLUMNS_PRODUCTO.Promocionado),
                new String[]{String.valueOf(3)},
                null,
                null,
                null);
    }

    public String getCalificadorId(int i) {

        Cursor myDataCursor = myDataBase.query(
                TABLE_CALIFICADOR_PRODUCTO,
                new String[]{COLUMNS_CALIFICADOR.ID},
                String.format("%s = ?", COLUMNS_CALIFICADOR.Codigo),
                new String[]{String.valueOf(i)},
                null,
                null,
                null);
        myDataCursor.moveToFirst();



        return myDataCursor.getString(myDataCursor.getColumnIndex(COLUMNS_CALIFICADOR.ID));
    }

    public Cursor getAllAdds() {
        return myDataBase.query(
                TABLE_ANUNCIO,
                null,
                null,
                null,
                null,
                null,
                null
        );
    }

    public Cursor getAllNews() {
        return myDataBase.query(
                TABLE_NOTICIA,
                null,
                String.format("%s = 1", COLUMNS_NOTICIA.Publicada),
                null,
                null,
                null,
                null
        );
    }

    public Cursor getAllCategories(){
        return this.myDataBase.query(
                TABLE_CATEGORIA,
                null,
                null,
                null,
                null,
                null,
                null
        );
    }

    public Cursor getAllEvents() {
        return this.myDataBase.query(
                TABLE_EVENTO,
                null,
                null,
                null,
                null,
                null,
                null
        );
    }

    public Cursor getAllInformation() {
        return this.myDataBase.query(
                TABLE_PREGUNTAS_FRECUENTES,
                null,
                null,
                null,
                null,
                null,
                COLUMNS_PREGUNTAS_FRECUENTES.Orden
        );
    }

    public Cursor filter(String subcategory, int pageIndex, int pageSize, String filterArtisan, String filterProvince,Boolean filterWithoutMessenger) {



        if (pageIndex == 0)
             Constants.START_LIMIT = 0;

        Constants.PAGE_INDEX = pageIndex;
        String filtArtesano = "";
        if (filterArtisan!=null){
            Cursor artesano = getSellerByName(filterArtisan);
            if (artesano.moveToFirst()) {
                filtArtesano = " AND "+COLUMNS_PRODUCTO.VendedorId +"=\""+ artesano.getString(artesano.getColumnIndex(COLUMNS_OPERARIO.ID)) +"\" ";
            }
        }


        String filtSubcategorie = subcategory!=null ? COLUMNS_PRODUCTO.SubCategoriaId+ "=\""+subcategory + "\" ":"";
        String filtMensageria = filterWithoutMessenger!=null ? (filterWithoutMessenger ? "1":"0"):"0";
        String mensajeriTxt1 = filterWithoutMessenger!=null ? "AND PROD." + COLUMNS_PRODUCTO.Mensajeria+"="+filtMensageria : "";
        String mensajeriTxt2 = filterWithoutMessenger!=null? "AND "+COLUMNS_PRODUCTO.Mensajeria+"="+filtMensageria:"";

        String query = "SELECT * from PRODUCTO AS PROD JOIN PROVINCIA_PRODUCTO AS MP ON MP.IdProducto = PROD.Id JOIN PROVINCIA AS PROV ON (PROV.Id = MP.IdProvincia " + mensajeriTxt1 + ")";;



        Cursor mC = null;
        if (filterProvince!=null){
            Cursor prov = getProvincia(filterProvince);
            if (prov.moveToFirst()) {
                int code = prov.getInt(prov.getColumnIndex(COLUMNS_PROVINCIA.Codigo));
                query = "SELECT * from PRODUCTO AS PROD JOIN PROVINCIA_PRODUCTO AS MP ON MP.IdProducto = PROD.Id JOIN PROVINCIA AS PROV ON (PROV.Id = MP.IdProvincia AND PROV.Codigo = " + code + " " + mensajeriTxt1 + " "+ filtArtesano +" AND PROD."+filtSubcategorie+")";
            }
            mC = this.myDataBase.rawQuery(query,null);
        }else {
            query = filtSubcategorie + filtArtesano +
                            mensajeriTxt2 +
                            " LIMIT " +
                            String.valueOf(Constants.START_LIMIT) + "," +
                            String.valueOf(Constants.START_LIMIT + pageSize);

            mC = this.myDataBase.query(
                    TABLE_PRODUCTO,
                    null,
                    query,
                    null,
                    null,
                    null,
                    null,
                    null

            );
        }
        Constants.START_LIMIT += pageSize;

        return mC;
    }



    public Cursor search(String searchQuery, int pageIndex, int pageSize, String filterArtisan, String filterProvince,Boolean filterWithoutMessenger) {

        if (pageIndex == 0)
            Constants.START_LIMIT = 0;

        Constants.PAGE_INDEX = pageIndex;
        String filtArtesano = "";

        if (filterArtisan!=null) filtArtesano = " AND "+COLUMNS_PRODUCTO.VendedorId +"=\""+ filtArtesano +"\" ";

        String filtSearch = "";//searchQuery!=null ? COLUMNS_PRODUCTO.SubCategoriaId+ "=\""+searchQuery + "\" ":"";

        Cursor mSubcate = getSubcategoryByName(searchQuery);
        filtSearch +=   " " + COLUMNS_PRODUCTO.Descripcion +" LIKE \"%"+searchQuery+"%\" OR " +
                        COLUMNS_PRODUCTO.Material +" LIKE \"%"+searchQuery+"%\" OR " +
                        COLUMNS_PRODUCTO.Modelo +" LIKE \"%"+searchQuery+"%\" " ;

        if (mSubcate.moveToFirst()){
            final String idSubcategorie = mSubcate.getString(mSubcate.getColumnIndex(COLUMNS_SUB_CATEGORIAS.ID));
            filtSearch += " OR "+COLUMNS_PRODUCTO.SubCategoriaId+ "=\""+idSubcategorie + "\" ";
        }

        String filtMensageria = filterWithoutMessenger!=null ? (filterWithoutMessenger ? "1":"0"):"0";
        String mensajeriTxt1 = filterWithoutMessenger!=null ? "AND PROD." + COLUMNS_PRODUCTO.Mensajeria+"="+filtMensageria : "";
        String mensajeriTxt2 = filterWithoutMessenger!=null? "AND "+COLUMNS_PRODUCTO.Mensajeria+"="+filtMensageria:"";

        String query = "";//"SELECT * from PRODUCTO AS PROD JOIN PROVINCIA_PRODUCTO AS MP ON MP.IdProducto = PROD.Id JOIN PROVINCIA AS PROV ON (PROV.Id = MP.IdProvincia " + mensajeriTxt1 + filtSearch + ")";;

        Cursor mC = null;
        if (filterProvince!=null){
            Cursor prov = getProvincia(filterProvince);
            if (prov.moveToFirst()) {
                int code = prov.getInt(prov.getColumnIndex(COLUMNS_PROVINCIA.Codigo));
                query = "SELECT * from PRODUCTO AS PROD JOIN PROVINCIA_PRODUCTO AS MP ON MP.IdProducto = PROD.Id JOIN PROVINCIA AS PROV ON (PROV.Id = MP.IdProvincia AND PROV.Codigo = " + code + " " + mensajeriTxt1 + " "+ filtArtesano +" OR "+filtSearch+")";
            }
            mC = this.myDataBase.rawQuery(query,null);
        }else {
            query = filtSearch + filtArtesano +
                    mensajeriTxt2 +
                    " LIMIT " +
                    String.valueOf(Constants.START_LIMIT) + "," +
                    String.valueOf(Constants.START_LIMIT + pageSize);

            mC = this.myDataBase.query(
                    TABLE_PRODUCTO,
                    null,
                    query,
                    null,
                    null,
                    null,
                    null,
                    null

            );
        }
        Constants.START_LIMIT += pageSize;

        return mC;
    }

    private Cursor getProvinciaById(String filterProvince) {
        return this.myDataBase.query(
                TABLE_PROVINCES,
                null,
                COLUMNS_PROVINCIA.ID+" LIKE '%"+filterProvince+"%'",
                null,
                null,
                null,
                null,
                null

        );
    }

    private Cursor getSellerByName(String filterArtisan) {
        return this.myDataBase.query(
                TABLE_OPERARIO,
                null,
                COLUMNS_OPERARIO.Nombre+" LIKE '%"+filterArtisan+"%'",
                null,
                null,
                null,
                null,
                null);
    }

    private Cursor getProvincia(String filterProvince) {
        return this.myDataBase.query(
                TABLE_PROVINCES,
                null,
                COLUMNS_PROVINCIA.Nombre+" LIKE '%"+filterProvince+"%'",
                null,
                null,
                null,
                null,
                null

        );
    }

    public Cursor getEventsImages(String string, String imagen) {
        return this.myDataBase.query(
                TABLE_IMAGE_EVENT,
                new String[]{imagen},
                String.format("%s  = '%s'",COLUMNS_IMAGE_EVENTS.IdEvento,string),
                null,
                null,
                null,
                null,
                null

        );
    }

    public Cursor getProvinces() {
        return this.myDataBase.query(
                TABLE_PROVINCES,
                null,
                null,
                null,
                null,
                null,
                null,
                null

        );
    }

    public Cursor getMessengerPrices() {
        return this.myDataBase.query(
                TABLE_MUNICIPIO_PRODUCTO,
                null,
                null,
                null,
                null,
                null,
                null,
                null
        );

    }


    public static class COLUMNS_ATRIBUTO_PRODUCTO {
        public static final String ID = "Id";
        public static final String ProductoId = "ProductoId";
        public static final String AtributoId = "AtributoId";
        public static final String ValorAtributo = "ValorAtributo";

    }

    public static class COLUMNS_PROVINCIA {
        public static final String ID = "Id";
        public static final String Nombre = "Nombre";
        public static final String Codigo = "Codigo";


    }

    public static class COLUMNS_MUNICIPIO_PRODUCTO {
        public static final String ID = "Id";
        public static final String ProductoId = "ProductoId";
        public static final String DestinoId = "DestinoId";
        public static final String PrecioMensajeria = "PrecioMensajeria ";


    }


    public static class COLUMNS_IMAGE_EVENTS {
        public static final String ID = "Id";
        public static final String IdEvento = "IdEvento";
        public static final String Imagen1 = "Imagen1";
        public static final String Imagen2 = "Imagen2";
        public static final String Imagen3 = "Imagen3";
        public static final String Imagen4 = "Imagen4";
        public static final String Imagen5 = "Imagen5";


    }


    public static class COLUMNS_ATRIBUTO {
        public static final String ID = "Id";
        public static final String Nombre = "Nombre";
    }


    public static class COLUMNS_CATEGORIAS {
        public static final String ID = "Id";
        public static final String CNOMBRE = "Nombre";
        public static final String CCODIGO = "Codigo";
        public static final String CUPDATE_DATE = "FechaActualizacion";

    }

    public static class COLUMNS_RANKING {
        public static final String ID = "Id";
        public static final String ValorRankin = "ValorRankin";
        public static final String CountPostRankin = "CountPostRankin";
    }

    public static class COLUMNS_CALIFICADOR{
        public static final String ID = "Id";
        public static final String Nombre = "Nombre";
        public static final String Codigo = "Codigo";
        public static final String Imagen = "Imagen";

    }

    public static class COLUMNS_OPERARIO {
        public static final String ID = "Id";
        public static final String Token = "Token";
        public static final String NombreUsuario = "NombreUsuario";
        public static final String Nombre = "Nombre";
        public static final String Correo = "Correo";
        public static final String Telefono = "Telefono";
        public static final String Fijo = "Fijo";
        public static final String Vip = "Vip";
        public static final String Especificaciones = "Especificaciones";
        public static final String Activo = "Activo";
        public static final String RankingId = "RankingId";
        public static final String Imagen = "Imagen";
        public static final String Banner = "Banner";
        public static final String PrecioRebaja = "PrecioRebaja";
        public static final String ParametroRebaja = "ParametroRebaja";
        public static final String Sex = "Sex";
        public static final String FechaActualizacion = "FechaActualizacion";


    }



    public static class COLUMNS_PRODUCTO {
        public static final String ID = "Id";
        public static final String Sku = "Sku";
        public static final String PrecioUnitario = "PrecioUnitario";
        public static final String PrecioRebaja = "PrecioRebaja";
        public static final String Ganancia = "Ganancia";
        public static final String ParametroRebaja = "ParametroRebaja";
        public static final String Existencia = "Existencia";
        public static final String MinimaExistencia = "MinimaExistencia";
        public static final String Publicado = "Publicado";
        public static final String FechaPublicacion = "FechaPublicacion";
        public static final String Modelo = "Modelo";
        public static final String Especificaciones = "Especificaciones";
        public static final String FechaActualizacion = "FechaActualizacion";
        public static final String Foto1 = "Foto1";
        public static final String Foto2 = "Foto2";
        public static final String Foto3 = "Foto3";
        public static final String Foto4 = "Foto4";
        public static final String Foto5 = "Foto5";
        public static final String Material = "Material";
        public static final String Promocionado = "Promocionado";
        public static final String Mensajeria = "Mensajeria";
        public static final String CalificadorId = "CalificadorId";
        public static final String RankingId = "RankingId";
        public static final String SubCategoriaId = "SubCategoriaId";
        public static final String VendedorId = "VendedorId";
        public static final String Descripcion = "Descripcion";
        public static final String Designer = "Designer";
        public static final String Tela = "Tela";
        public static final String Medidas = "Medidas";
        public static final String Dimensiones = "Dimensiones";
        public static final String Discriminator = "Discriminator";
    }

    public static class COLUMNS_SUB_CATEGORIAS {
        public static final String ID = "Id";
        public static final String CNOMBRE = "Nombre";
        public static final String CIMAGEN = "Imagen";
        public static final String CCATEGORIE_ID = "CategoriaId";
        public static final String CUPDATE_DATE = "FechaActualizacion";

    }

    public static class COLUMNS_ANUNCIO {
        public static final String ID = "Id";
        public static final String IdSubcategoria = "IdSubcategoria";
        public static final String Precio = "Precio";
        public static final String Telefono = "Telefono";
        public static final String Titulo = "Titulo";
        public static final String Url = "Url";
    }

    public static class COLUMNS_NOTICIA {
        public static final String ID = "Id";
        public static final String Titulo = "Titulo";
        public static final String Resumen = "Resumen";
        public static final String Url = "Url";
        public static final String Fuente = "Fuente";
        public static final String Tag = "Tag";
        public static final String Fecha = "Fecha";
        public static final String Publicada = "Publicada";
        public static final String Logo = "Logo";
    }

    public static class COLUMNS_PREGUNTAS_FRECUENTES {

        public static final String ID = "Id";
        public static final String Pregunta = "Pregunta";
        public static final String Respuesta = "Respuesta";
        public static final String Fecha = "Fecha";
        public static final String Orden = "Orden";

    }



    public static class COLUMNS_EVENTO {
        public static final String ID = "Id";
        public static final String Descripcion = "Descripcion";
        public static final String Clasificacion = "Clasificacion";
        public static final String Aleatorio = "Aleatorio";
        public static final String Front = "Front";
        public static final String FrontDescripcion = "FrontDescripcion";
        public static final String Activo = "Activo";
        public static final String FechaPublicacion = "FechaPublicacion";
        public static final String FechaInicio = "FechaInicio";
        public static final String FechaFin = "FechaFin";
        public static final String PieImagen1 = "PieImagen1";
        public static final String PieImagen2 = "PieImagen2";
        public static final String PieImagen3 = "PieImagen3";
        public static final String PieImagen4 = "PieImagen4";
        public static final String PieImagen5 = "PieImagen5";
    }


}
