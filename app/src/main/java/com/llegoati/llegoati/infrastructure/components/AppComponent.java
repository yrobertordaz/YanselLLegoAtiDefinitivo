package com.llegoati.llegoati.infrastructure.components;

import com.llegoati.llegoati.SplashActivity;
import com.llegoati.llegoati.activities.AddContactActivity;
import com.llegoati.llegoati.activities.ChangePasswordActivity;
import com.llegoati.llegoati.activities.ConfirmActivity;
import com.llegoati.llegoati.activities.EventsActivity;
import com.llegoati.llegoati.activities.InformationActivity;
import com.llegoati.llegoati.activities.LoginActivity;
import com.llegoati.llegoati.activities.MainActivity;
import com.llegoati.llegoati.activities.MyAccount;
import com.llegoati.llegoati.activities.PickDialog;
import com.llegoati.llegoati.activities.ProductDetailActivity;
import com.llegoati.llegoati.activities.ProductListActivity;
import com.llegoati.llegoati.activities.RegisterActivity;
import com.llegoati.llegoati.activities.RequestDetailActivity;
import com.llegoati.llegoati.activities.SearchResultActivity;
import com.llegoati.llegoati.activities.SellerListActivity;
import com.llegoati.llegoati.activities.SellerProfileActivity;
import com.llegoati.llegoati.adapter.ContactAdapter;
import com.llegoati.llegoati.adapter.ProductHomeRecyclerViewAdapter;
import com.llegoati.llegoati.adapter.ShoppingCartRecyclerViewAdapter;
import com.llegoati.llegoati.adapter.viewholders.ContactViewHolder;
import com.llegoati.llegoati.dialogs.CheckoutContactDialog;
import com.llegoati.llegoati.dialogs.CommentDialog;
import com.llegoati.llegoati.dialogs.FilterDialog;
import com.llegoati.llegoati.dialogs.IndexDialog;
import com.llegoati.llegoati.explorer.ExplorerActivity;
import com.llegoati.llegoati.explorer.MultiDatabaseActivity;
import com.llegoati.llegoati.fragments.CategoryFragment;
import com.llegoati.llegoati.fragments.ChangePasswordFragment;
import com.llegoati.llegoati.fragments.CheckoutLoteryCodeFragment;
import com.llegoati.llegoati.fragments.ContactsActivityFragment;
import com.llegoati.llegoati.fragments.EventsActivityFragment;
import com.llegoati.llegoati.fragments.HomeFragment;
import com.llegoati.llegoati.fragments.InformationActivityFragment;
import com.llegoati.llegoati.fragments.ModifyAccountActivityFragment;
import com.llegoati.llegoati.fragments.RequestHistoryActivityFragment;
import com.llegoati.llegoati.fragments.ShoppingCartFragment;
import com.llegoati.llegoati.infrastructure.abstracts.IRepository;
import com.llegoati.llegoati.infrastructure.modules.ApiModule;
import com.llegoati.llegoati.infrastructure.modules.AppModule;
import com.llegoati.llegoati.infrastructure.modules.ShoppingCartModule;
import com.llegoati.llegoati.infrastructure.modules.UserModule;
import com.llegoati.llegoati.smsmodulo.View.SmsHistoryActivity;

import javax.inject.Singleton;

import dagger.Component;

/**
 * Created by Yansel on 3/19/2017.
 */
@Singleton
@Component(modules = {
        AppModule.class,
        ApiModule.class,
        UserModule.class,
        ShoppingCartModule.class
})
public interface AppComponent {

    void inject(HomeFragment homeFragment);

    void inject(SplashActivity homeFragment);

    void inject(MultiDatabaseActivity homeFragment);

    void inject(ExplorerActivity homeFragment);

    void inject(SellerListActivity homeFragment);

    void inject(SellerProfileActivity homeFragment);

    void inject(SmsHistoryActivity homeFragment);

    void inject(IRepository remoteRepository);

    void inject(CategoryFragment categoryFragment);

    void inject(ProductListActivity productListActivity);

    void inject(MainActivity mainActivity);

    void inject(ConfirmActivity confirmActivity);

    void inject(RegisterActivity registerActivity);

    void inject(LoginActivity loginActivity);

    void inject(ProductDetailActivity productDetailActivity);

    void inject(ShoppingCartRecyclerViewAdapter shoppingCartRecyclerViewAdapter);

    void inject(PickDialog pickDialog);

    void inject(MyAccount myAccount);

    void inject(ProductHomeRecyclerViewAdapter productHomeRecyclerViewAdapter);

    void inject(ChangePasswordFragment changePasswordFragment);

    void inject(ChangePasswordActivity changePasswordActivity);

    void inject(ContactsActivityFragment contactsActivityFragment);

    void inject(CheckoutLoteryCodeFragment checkoutLoteryCodeFragment);

    void inject(ModifyAccountActivityFragment modifyAccountActivityFragment);

    void inject(ContactAdapter contactAdapter);

    void inject(AddContactActivity addContactActivity);

    void inject(ContactViewHolder contactViewHolder);

    void inject(RequestHistoryActivityFragment requestHistoryActivityFragment);

    void inject(RequestDetailActivity requestDetailActivity);

    void inject(CommentDialog commentDialog);

    void inject(ShoppingCartFragment shoppingCartFragment);

    void inject(InformationActivity informationActivity);

    void inject(InformationActivityFragment informationActivityFragment);

    void inject(CheckoutContactDialog checkoutContactDialog);

    void inject(ShoppingCartFragment.CheckoutShoppingCartAsync checkoutShoppingCartAsync);

    void inject(IndexDialog indexDialog);

    void inject(EventsActivity eventsActivity);

    void inject(EventsActivityFragment eventsActivityFragment);

    void inject(FilterDialog filterDialog);

    void inject(SearchResultActivity searchResultActivity);

}
