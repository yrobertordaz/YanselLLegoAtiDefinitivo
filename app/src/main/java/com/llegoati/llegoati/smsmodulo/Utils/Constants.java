package com.llegoati.llegoati.smsmodulo.Utils;

import android.content.Context;

/**
 * Created by Richard on 08/11/2016.
 */
public class Constants {

    public static final String SERVER_PHONE_SERVICE = "98764987"; //// TODO: 07/01/2017 definir el numero de telefono
    public static final String KEY_SMS1 = "1A1";
    public static final String KEY_SMS2 = "1B1";
    public static final String KEY_SMS3 = "1C1";
    public static final String SEPARATOR = ";";
    public static final String SEPARATOR_ADDRESS = "AS;AS";
    public static final String SHARED_PREFERENCES_NAME = "llegoati_taxi_receiver";

    public static final int PUBLIC_CIPHER_KEY = 23;
    public static final int TAXISTA_MAIN_PAGE = 68696;
    public static final int PETITION_6 = 6 ;
    public static final int CLIENT_MAIN_PAGE = 85269;
    public static final String[] SIMULATE_RECEIVER_TRAVEL = {
            // type:idPetition:name:add1:add2:hora1:hora2:pasajeros:provincia
            String.valueOf(1) ,
            String.valueOf(326) ,
                    "Name" ,
                    "[asdas"+Constants.SEPARATOR_ADDRESS+"asdasd"+Constants.SEPARATOR_ADDRESS+"asdasd]" ,
                    "[asdas"+Constants.SEPARATOR_ADDRESS+"asdasd"+Constants.SEPARATOR_ADDRESS+"asdasd]",
                    String.valueOf(1132) ,
                    String.valueOf(1232) ,
                    String.valueOf(3),
                    String.valueOf(2)

    };
    //flujo type:id_pettion:name:phone:price
    public static final String[] SIMULATE_RECEIVER_CONFIRMATION_CLIENT ={
            String.valueOf(3),
            String.valueOf(263),
            "Name",
            String.valueOf(53696869),
            String.valueOf(20)
    } ;
    public static final String[] SIMULATE_RECEIVER_CONFIRMATION_TAXISTA = {
            String.valueOf(3),
            String.valueOf(263),
            "Name",
            String.valueOf(53696869),
            String.valueOf(20)
    };
    public static final int PAGE_END_TRAVEL_CLIENT = 2365 ;
    public static final int PAGE_END_TRAVEL_TAXISTA = 9686;
    public static final int PAGE_LOGO = 98273;
    public static final int USER_STATE_IN_PROCESS = 0;
    public static final int USER_STATE_TRAVEL_PROCESS = 432;
    public static final int USER_STATE_TRAVEL_SEND = 432;
    public static final int USER_STATE_INITIATE = 32;
    public static final int USER_STATE_ACTIVE = 1;
    public static final int USER_STATE_OCUPATE = 2;
    public static final int USER_STATE_CANCELED = 3;
    public static final String EXTRA_ID_EVENT = "ID_EVENT";
    public static final String EXTRA_IS_NEW = "is_new";
    public static final String EXTRA_CONTENT = "content_sms";
    public static final int FLUJO_CLIENTE_VENDEDOR = 1;
    public static final String ID_APP = "2";
    public static final int FLUJO_VENDEDOR_CLIENTE = 2;
    public static final int POSITION_FLUJO = 0;
    public static final int FLUJO_CLIENTE_VENDEDOR_START_SMS = 10;
    public static final int FLUJO_VENDEDOR_CLIENTE_START_SMS = 12;
    public static final String ID_NULL = "00000000-0000-0000-0000-000000000000";
    public static final int CONCEDER_PERMISOS = 1321;
    public static int START_LIMIT = 0;
    public static int PAGE_INDEX = 0;
    public static String DB_DEFAULT = "CONSTANT";
    public static boolean ADD_DATABASE = false;


    public static PreferencesFactory getPreferences(Context mContext){
        return PreferencesFactory.getInstance(mContext);}

    public static final int POSITION_PETITION = 0;

    /*public static ArrayList<Province> getProvincesNames(){
        ArrayList<Province> mResult = new ArrayList<>();
        ArrayList<String> names = new ArrayList<>();
        ArrayList<String> mPinar = new ArrayList<>();
        mPinar.add("Pinar del R�o");
        mPinar.add("Consolaci�n del Sur");
        mPinar.add("San Juan y Mart�nez");
        mPinar.add("Los Palacios");
        mPinar.add("Sandino");
        mPinar.add("Guane");
        mPinar.add("La Palma");
        mPinar.add("San Luis");
        mPinar.add("Minas de Matahambre");
        mPinar.add("Vi�ales");
        mPinar.add("Mantua");

        Province province = new Province("Pinar del R�o");
        province.setmMunicipios(mPinar);
        mResult.add(province);

        ArrayList<String> mArtemisa = new ArrayList<>();
        mArtemisa.add("Artemisa");
        mArtemisa.add("Bah�a Honda");
        mArtemisa.add("Mariel");
        mArtemisa.add("Guanajay");
        mArtemisa.add("Caimito");
        mArtemisa.add("Bauta");
        mArtemisa.add("San Antonio de los Ba�os");
        mArtemisa.add("G�ira de Melena");
        mArtemisa.add("Alqu�zar");
        mArtemisa.add("Candelaria");
        mArtemisa.add("San Crist�bal");
        province =new Province("Artemisa");
        province.setmMunicipios(mArtemisa);
        mResult.add(province);



        ArrayList<String> mHabana = new ArrayList<>();
        mHabana.add("Playa");
        mHabana.add("Plaza de la Revoluci�n");
        mHabana.add("Centro Habana ");
        mHabana.add("La Habana Vieja ");
        mHabana.add("Regla");
        mHabana.add("La Habana del Este");
        mHabana.add("Guanabacoa");
        mHabana.add("San Miguel del Padr�n");
        mHabana.add("Diez de Octubre");
        mHabana.add("Cerro");
        mHabana.add("Marianao");
        mHabana.add("La Lisa ");
        mHabana.add("Boyeros");
        mHabana.add("Arroyo Naranjo ");
        mHabana.add("Cotorro");
        province =new Province("La Habana");
        province.setmMunicipios(mHabana);
        mResult.add(province);

        ArrayList<String> mMayabeque = new ArrayList<>();
        mMayabeque.add("Bejucal");
        mMayabeque.add("San Jos� de las Lajas");
        mMayabeque.add("Jaruco");
        mMayabeque.add("Santa Cruz del Norte");
        mMayabeque.add("Madruga");
        mMayabeque.add("Nueva Paz");
        mMayabeque.add("San Nicol�s");
        mMayabeque.add("G�ines");
        mMayabeque.add("Melena del Sur");
        mMayabeque.add("Bataban�");
        mMayabeque.add("Quivic�n");
        province =new Province("Mayabeque");
        province.setmMunicipios(mMayabeque);
        mResult.add(province);

        ArrayList<String> mMatanzas = new ArrayList<>();
        mMatanzas.add("Matanzas");
        mMatanzas.add("C�rdenas");
        mMatanzas.add("Mart�");
        mMatanzas.add("Col�n");
        mMatanzas.add("Perico");
        mMatanzas.add("Jovellanos");
        mMatanzas.add("Pedro Betancourt");
        mMatanzas.add("Limonar");
        mMatanzas.add("Uni�n de Reyes");
        mMatanzas.add("Ci�naga de Zapata");
        mMatanzas.add("Jag�ey Grande");
        mMatanzas.add("Calimete");
        mMatanzas.add("Loa Arabos");
        province =new Province("Matanzas");
        province.setmMunicipios(mMatanzas);
        mResult.add(province);

        ArrayList<String> mVilla = new ArrayList<>();
        mVilla.add("Corralillo");
        mVilla.add("Quemado de G�ines");
        mVilla.add("Sagua la Grande");
        mVilla.add("Encrucijada");
        mVilla.add("Camajuan�");
        mVilla.add("Caibari�n");
        mVilla.add("Remedios");
        mVilla.add("Placetas");
        mVilla.add("Santa Clara");
        mVilla.add("Cifuentes");
        mVilla.add("Santo Domingo");
        mVilla.add("Ranchuelo");
        mVilla.add("Manicaragua");
        province =new Province("Villa Clara");
        province.setmMunicipios(mVilla);
        mResult.add(province);

        ArrayList<String> mSS = new ArrayList<>();
        mSS.add("Yaguajay");
        mSS.add("Jatibonico");
        mSS.add("Taguasco");
        mSS.add("Cabaigu�n");
        mSS.add("Fomento");
        mSS.add("Trinidad");
        mSS.add("Sancti Sp�ritus");
        mSS.add("La Sierpe");
        province =new Province("Sancti Sp�ritus");
        province.setmMunicipios(mSS);
        mResult.add(province);

        ArrayList<String> mCA = new ArrayList<>();
        mCA.add("Chambas");
        mCA.add("Mor�n");
        mCA.add("Bolivia");
        mCA.add("Primero de Enero");
        mCA.add("Ciro Redondo");
        mCA.add("Florencia");
        mCA.add("Majagua");
        mCA.add("Ciego de �vila");
        mCA.add("Venezuela");
        mCA.add("Baragu�");
        province =new Province("Ciego de �vila");
        province.setmMunicipios(mCA);
        mResult.add(province);

        ArrayList<String> mCamaguey = new ArrayList<>();
        mCamaguey.add("Carlos Manuel de C�spedes");
        mCamaguey.add("Esmeralda");
        mCamaguey.add("Sierra de Cubitas");
        mCamaguey.add("Minas");
        mCamaguey.add("Nuevitas");
        mCamaguey.add("Gu�imaro");
        mCamaguey.add("Sibanic�");
        mCamaguey.add("Camag�ey");
        mCamaguey.add("Florida");
        mCamaguey.add("Vertientes");
        mCamaguey.add("Jimaguay�");
        mCamaguey.add("Najasa");
        mCamaguey.add("Santa Cruz del Sur");
        province =new Province("Camag�ey");
        province.setmMunicipios(mCamaguey);
        mResult.add(province);

        ArrayList<String> mLT = new ArrayList<>();
        mLT.add("Manat�");
        mLT.add("Puerto Padre");
        mLT.add("Jes�s Men�ndez");
        mLT.add("Majibacoa");
        mLT.add("Las Tunas");
        mLT.add("Jobabo");
        mLT.add("Colombia");
        mLT.add("Amancio");
        province =new Province("Las Tunas");
        province.setmMunicipios(mLT);
        mResult.add(province);

        ArrayList<String> mHolguin = new ArrayList<>();
        mHolguin.add("Gibara");
        mHolguin.add("Rafael Freyre");
        mHolguin.add("Banes");
        mHolguin.add("Antilla");
        mHolguin.add("B�guanos");
        mHolguin.add("Holgu�n");
        mHolguin.add("Calixto Garc�a");
        mHolguin.add("Cacocum");
        mHolguin.add("Urbano Noris");
        mHolguin.add("Cueto");
        mHolguin.add("Mayar�");
        mHolguin.add("Frank Pa�s");
        mHolguin.add("Sagua de T�namo");
        mHolguin.add("Moa");
        province =new Province("Holgu�n");
        province.setmMunicipios(mHolguin);
        mResult.add(province);

        ArrayList<String> mGranma = new ArrayList<>();
        mGranma.add("R�o Cauto");
        mGranma.add("Cauto Cristo");
        mGranma.add("Jiguan�");
        mGranma.add("Bayamo");
        mGranma.add("Yara");
        mGranma.add("Manzanillo");
        mGranma.add("Campechuela");
        mGranma.add("Media Luna");
        mGranma.add("Niquero");
        mGranma.add("Pil�n");
        mGranma.add("Bartolom� Mas�");
        mGranma.add("Buey Arriba");
        mGranma.add("Guisa");
        province =new Province("Granma");
        province.setmMunicipios(mGranma);
        mResult.add(province);


        ArrayList<String> mSC = new ArrayList<>();
        mSC.add("Contramaestre");
        mSC.add("Mella");
        mSC.add("San Luis");
        mSC.add("Segundo Frente");
        mSC.add("Songo - La Maya");
        mSC.add("Santiago de Cuba");
        mSC.add("Palma Soriano");
        mSC.add("Tercer Frente");
        mSC.add("Guam�");
        province =new Province("Santiago de Cuba");
        province.setmMunicipios(mSC);
        mResult.add(province);

        ArrayList<String> mGuantanamo = new ArrayList<>();
        mGuantanamo.add("El Salvador");
        mGuantanamo.add("Manuel Tames");
        mGuantanamo.add("Yateras");
        mGuantanamo.add("Baracoa");
        mGuantanamo.add("Mais�");
        mGuantanamo.add("Im�as");
        mGuantanamo.add("San Antonio del Sur");
        mGuantanamo.add("Caimanera");
        mGuantanamo.add("Guant�namo");
        mGuantanamo.add("Niceto P�rez");
        province =new Province("Guant�namo");
        province.setmMunicipios(mGuantanamo);
        mResult.add(province);

        ArrayList<String> mIJ = new ArrayList<>();
        mIJ.add("Nueva Gerona");
        mIJ.add("Santa F�");
        mIJ.add("Patria");
        mIJ.add("La Victoria");
        mIJ.add("Jos� Mart�");
        mIJ.add("Delio Chac�n");
        province =new Province("Isla de la Juventud");
        province.setmMunicipios(mIJ);
        mResult.add(province);

        return mResult;

    };*/
// TODO: 3/19/2017 cambiar los significados de las peticiones
    public static final int PETITION_0 = 0; // Cliente - Server ->  envia solicitud
    public static final int PETITION_1 = 1; // Server - Taxista -> Informacion del viaje
    public static final int PETITION_2 = 2; // Taxita - Server -> Confirmaci�n.
    public static final int PETITION_3 = 3; // Server - Cliente | Taxista -> Envio de contactos
    public static final int PETITION_4 = 4; // Cliente - Server -> Confirma que el viaje se termin�
    public static final int PETITION_5 = 5; // Taxista - Server -> Confirma que el viaje se termin�

    public static final int PETITION_7 = 7; // Taxista se autentica
    public static final int PETITION_8 = 8; // Taxista actualiza su informaci�n
    public static final int PETITION_9 = 9; // Taxista reactiva su cuenta
    public static final int PETITION_10 = 9; // Taxista cambia estado





    /*
    *
    *
    * */
}