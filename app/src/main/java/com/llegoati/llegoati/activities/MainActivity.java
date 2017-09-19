package com.llegoati.llegoati.activities;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.app.SearchManager;
import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.design.widget.Snackbar;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.GravityCompat;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.AppCompatTextView;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.Toolbar;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.style.ImageSpan;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.webkit.WebView;
import android.widget.FrameLayout;
import android.widget.ImageView;

import com.llegoati.llegoati.App;
import com.llegoati.llegoati.R;
import com.llegoati.llegoati.adapter.CategoryAdapter;
import com.llegoati.llegoati.adapter.ProductRecyclerViewAdapter;
import com.llegoati.llegoati.explorer.ExplorerActivity;
import com.llegoati.llegoati.explorer.MultiDatabaseActivity;
import com.llegoati.llegoati.explorer.PreferencesFactory;
import com.llegoati.llegoati.fragments.CategoryFragment;
import com.llegoati.llegoati.fragments.HomeFragment;
import com.llegoati.llegoati.fragments.ShoppingCartFragment;
import com.llegoati.llegoati.infrastructure.abstracts.IRepository;
import com.llegoati.llegoati.infrastructure.abstracts.IUserManager;
import com.llegoati.llegoati.infrastructure.concrete.RemoteRepository;
import com.llegoati.llegoati.infrastructure.concrete.SqliteRepository;
import com.llegoati.llegoati.infrastructure.concrete.dbconection;
import com.llegoati.llegoati.infrastructure.concrete.utils.UtilsFunction;
import com.llegoati.llegoati.infrastructure.events.LoginEvent;
import com.llegoati.llegoati.models.Adds;
import com.llegoati.llegoati.smsmodulo.View.SmsMainActivity;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import javax.inject.Inject;

import butterknife.Bind;
import butterknife.ButterKnife;
import de.greenrobot.event.EventBus;

public class MainActivity extends BaseActivity
        implements NavigationView.OnNavigationItemSelectedListener {
    public static boolean goShoppingCart = false;
    public static final int LOGIN_CODE = 0x01;
    public static final int MODIFY_CODE = 0x02;
    private static final int SELECTED_DB = 0x03;

    @Inject
    IUserManager userManager;

    @Inject
    IRepository repository;

    @Bind(R.id.tv_user_email)
    AppCompatTextView userEmail;




    FrameLayout flAdds;

    ImageView ivClose;
    DrawerLayout drawer;

    public static final String PREFERENCES = "PREF";
    public static ProductRecyclerViewAdapter productAdapter;
    Toolbar toolbar;
    TabLayout tabLayout;
    ViewPager viewPager;

    public static CategoryAdapter categoryAdapter;
    private MenuItem mSearchAction;
    private boolean isSearchOpenned = false;
    public static MainActivity INSTANCE;
    private WebView wvAdds;
    private boolean seeAdds = true;
    PreferencesFactory mPreferencesFactory;

    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {
        mSearchAction = menu.findItem(R.id.search_action);
        return super.onPrepareOptionsMenu(menu);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        App.getInstance().getAppComponent().inject(this);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        mPreferencesFactory = new PreferencesFactory(this);
        if (!UtilsFunction.isOnline(this)) {
            if (!mPreferencesFactory.existDb()) {
                startActivityForResult(new Intent(this, ExplorerActivity.class), SELECTED_DB);
            }
            else {
                File mF = new File(mPreferencesFactory.getDbPatch());
                String[] mPatch = mF.getAbsolutePath().split(File.separator);
                String mFinalPath = "";
                for (int i=0;i<mPatch.length-1;i++){
                    mFinalPath += mPatch[i] + File.separator;
                }
                if (repository instanceof SqliteRepository)
                    ((SqliteRepository) repository).setmDbconection(
                            new dbconection(this, mFinalPath,mF.getName())
                    );

                initialization();
            }
        }else {


            initialization();
        }





        //if (UtilsFunction.isOnline(this))



    }


    private void initialization() {


        drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        viewPager = (ViewPager) findViewById(R.id.viewpager);
        setupViewPager(viewPager);
        tabLayout = (TabLayout) findViewById(R.id.tabs);
        tabLayout.setupWithViewPager(viewPager);
        wvAdds = (WebView) findViewById(R.id.wv_adds);
        flAdds = (FrameLayout) findViewById(R.id.fl_adds);
        ivClose = (ImageView) findViewById(R.id.iv_close);

        tabLayout.setOnTabSelectedListener(new TabLayout.ViewPagerOnTabSelectedListener(viewPager) {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                if (tab.getPosition() == tabLayout.getTabCount() - 1) {
                    flAdds.animate().alpha(0).setListener(new AnimatorListenerAdapter() {

                        @Override
                        public void onAnimationEnd(Animator animation) {
                            flAdds.setVisibility(View.GONE);
                        }
                    });
                } else {
                    if (seeAdds)
                        flAdds.animate().alpha(1).setListener(new AnimatorListenerAdapter() {
                            @Override
                            public void onAnimationEnd(Animator animation) {
                                flAdds.setVisibility(View.VISIBLE);
                            }
                        });
                }
                super.onTabSelected(tab);
            }
        });
        ivClose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                flAdds.animate().alpha(0).setListener(new AnimatorListenerAdapter() {

                    @Override
                    public void onAnimationEnd(Animator animation) {
                        flAdds.setVisibility(View.GONE);
                    }
                });
                seeAdds = false;
            }
        });
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
        ButterKnife.bind(this, navigationView.getHeaderView(0));

        INSTANCE = this;
        if (userManager.isUserPermanentAuthenticated()) {
            userEmail.setText(userManager.user().getEmail());
            userEmail.setVisibility(View.VISIBLE);
        }
        setupMarqueeRV();
    }

    private void setupMarqueeRV() {

        FillMarqueeAdapterAsync fillMarqueeAdapterAsync = new FillMarqueeAdapterAsync();
        fillMarqueeAdapterAsync.execute();

    }


    private void setupViewPager(ViewPager viewPager) {
        ViewPagerAdapter adapter = new ViewPagerAdapter(getSupportFragmentManager());
        adapter.addFragment(new HomeFragment(), getString(R.string.home_tab));
        adapter.addFragment(new CategoryFragment(), getString(R.string.category_tab));
        adapter.addFragment(new ShoppingCartFragment(), getString(R.string.cart_tab));
        viewPager.setAdapter(adapter);
    }

    @Override
    public void onBackPressed() {

        if (isSearchOpenned) {
            handleMenuSearch();
            return;
        }

        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);

        SearchManager searchManager = (SearchManager) getSystemService(Context.SEARCH_SERVICE);
        SearchView searchView = (SearchView) menu.findItem(R.id.search_action).getActionView();
        searchView.setSearchableInfo(searchManager.getSearchableInfo(getComponentName()));
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
//        if (id == R.id.filter_action) {
//            FilterDialog filterDialog = new FilterDialog();
//            filterDialog.show(this.getSupportFragmentManager(), MainActivity.class.getSimpleName());
//        }
        //noinspection SimplifiableIfStatement

        switch (item.getItemId()){
            case android.R.id.home:
                if (drawer.isDrawerOpen(GravityCompat.START)) {
                    drawer.closeDrawer(GravityCompat.START);
                } else {
                    drawer.openDrawer(GravityCompat.START);
                }
                break;
        }

        return true;//uper.onOptionsItemSelected(item);
    }

    @Override
    protected void onResume() {
        if (MainActivity.goShoppingCart) {
            tabLayout.setScrollPosition(2, 0f, true);
            viewPager.setCurrentItem(2);
            MainActivity.goShoppingCart = false;
        }
        super.onResume();
    }

    private void handleMenuSearch() {
//        ActionBar actionBar = getSupportActionBar();
//        if (isSearchOpenned) {
//            actionBar.setDisplayShowCustomEnabled(false);
//            actionBar.setDisplayShowTitleEnabled(true);
//
//            InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
//            imm.hideSoftInputFromWindow(this.getCurrentFocus().getWindowToken(), 0);
//            mSearchAction.setIcon(getResources().getDrawable(R.drawable.ic_action_search));
//            isSearchOpenned = false;
//
//
//        } else {
//            actionBar.setDisplayShowCustomEnabled(true);
//            actionBar.setCustomView(R.layout.search_bar);
//            actionBar.setDisplayShowTitleEnabled(false);
//            editText = (AppCompatEditText) actionBar.getCustomView().findViewById(R.id.edit_search);
//            editText.setOnEditorActionListener(new EditText.OnEditorActionListener() {
//                @Override
//                public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
//                    System.out.println(String.format(Locale.US, "The action is : %s", actionId));
//                    if (actionId == EditorInfo.IME_ACTION_SEARCH) {
//                        doSearch(v.getText().toString());
//                        return true;
//                    }
//                    return false;
//                }
//            });
//            editText.setOnKeyListener(new View.OnKeyListener() {
//                @Override
//                public boolean onKey(View v, int keyCode, KeyEvent event) {
//                    EditText et = (EditText) v;
//                    doSearch(et.getText().toString());
//                    return false;
//                }
//            });
//
//
//            editText.requestFocus();
//
//            //open the keyboard focused in the edtSearch
//            InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
//            imm.showSoftInput(editText, InputMethodManager.SHOW_IMPLICIT);
//
//            //add the close icon
//            mSearchAction.setIcon(getResources().getDrawable(android.R.drawable.ic_menu_close_clear_cancel));
//
//            isSearchOpenned = true;
//        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == LOGIN_CODE) {
            userEmail.setText(userManager.user().getEmail());
            userEmail.setVisibility(View.VISIBLE);
            EventBus.getDefault().post(new LoginEvent());
        }else
        if (resultCode == MODIFY_CODE) {
            userEmail.setText(userManager.user().getEmail());
            userEmail.setVisibility(View.VISIBLE);
        }else
        if (resultCode == SELECTED_DB){
            initialization();
        }

        super.onActivityResult(requestCode, resultCode, data);
    }


    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        final Intent[] intent = new Intent[1];
        Snackbar snackbar = Snackbar.make(this.viewPager, R.string.item_not_available, Snackbar.LENGTH_LONG).setAction(R.string.action_register, new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                intent[0] = new Intent(MainActivity.this, LoginActivity.class);
                startActivityForResult(intent[0], LOGIN_CODE);
            }
        });
        // Handle navigation view item clicks here.
        int id = item.getItemId();
        if (id == R.id.nav_my_account) {

            if (userManager.user()!=null && userManager.isUserPermanentAuthenticated() && userManager.user().getEmail() != null) {
                intent[0] = new Intent(this, MyAccount.class);
                startActivity(intent[0]);
                return true;
            } else {
                snackbar.show();
            }

        } else if (id == R.id.nav_change_password) {
            if (userManager.user()!=null && userManager.isUserPermanentAuthenticated() && userManager.user().getEmail() != null) {
                intent[0] = new Intent(this, ChangePasswordActivity.class);
                startActivity(intent[0]);
                return true;
            } else
                snackbar.show();
        } else if (id == R.id.nav_check_lotery_code) {
            if (userManager.user()!=null && userManager.isUserPermanentAuthenticated() && userManager.user().getEmail() != null) {
                intent[0] = new Intent(this, CheckoutLoteryCodeActivity.class);
                startActivity(intent[0]);
                return true;
            } else
                snackbar.show();
        }
     /*    else if (id == R.id.nav_edit_account) {
           if (userManager.user()!=null && userManager.isUserPermanentAuthenticated() && userManager.user().getEmail() != null) {
                intent[0] = new Intent(this, ModifyAccountActivity.class);
                startActivityForResult(intent[0], MODIFY_CODE);
                return true;
            } else
                snackbar.show();

        }*/

        else if (id == R.id.nav_information) {
            intent[0] = new Intent(this, InformationActivity.class);
            startActivity(intent[0]);
            return true;
        } else if (id == R.id.nav_events) {
            intent[0] = new Intent(this, EventsActivity.class);
            startActivity(intent[0]);
            return true;
        } else if (id == R.id.nav_contacts) {
            if (userManager.user()!=null && userManager.isUserPermanentAuthenticated() && userManager.user().getEmail() != null) {
                intent[0] = new Intent(this, ContactsActivity.class);
                startActivity(intent[0]);
                return true;
            } else {
                snackbar.show();
            }
        } else if (id == R.id.nav_request_history) {
            if (userManager.user()!=null && userManager.isUserPermanentAuthenticated() && userManager.user().getEmail() != null) {
                intent[0] = new Intent(this, RequestHistoryActivity.class);
                startActivity(intent[0]);
                return true;
            } else {
                snackbar.show();
            }
        }else  if(id == R.id.nav_mensajes){
            intent[0] = new Intent(this, SmsMainActivity.class);
            startActivity(intent[0]);
            return true;
        }else  if(id == R.id.nav_sellers){
            intent[0] = new Intent(this, SellerListActivity.class);
            startActivity(intent[0]);
            return true;
        }else  if(id == R.id.nav_db_config){
            intent[0] = new Intent(this, MultiDatabaseActivity.class);
            startActivity(intent[0]);
            return true;
        }



//        else if (id == R.id.nav_gallery) {
//
//        } else if (id == R.id.nav_slideshow) {
//
//        } else if (id == R.id.nav_manage) {
//
//        } else if (id == R.id.nav_share) {
//
//        } else if (id == R.id.nav_send) {
//
//        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    public String styles() {
        return "style='color:#175294;text-decoration:none;margin-right:20px;'";
    }

    public String wrapMarquee(String inside) {

        return String.format(Locale.US, "<marquee>%s</marquee>", inside);
    }

    class ViewPagerAdapter extends FragmentPagerAdapter {

        private int[] imageResId = {
                R.drawable.ic_home,
                R.drawable.ic_catalog,
                R.drawable.ic_shopping_cart
        };

        private final List<Fragment> mFragmentList = new ArrayList<>();
        private final List<String> mFragmentTitleList = new ArrayList<>();

        public ViewPagerAdapter(FragmentManager manager) {
            super(manager);
        }

        @Override
        public Fragment getItem(int position) {
            return mFragmentList.get(position);
        }

        @Override
        public int getCount() {
            return mFragmentList.size();
        }

        public void addFragment(Fragment fragment, String title) {
            mFragmentList.add(fragment);
            mFragmentTitleList.add(title);
        }

        @Override
        public CharSequence getPageTitle(int position) {
            Drawable image = ContextCompat.getDrawable(MainActivity.this, imageResId[position]);
            image.setBounds(0, 0, image.getIntrinsicWidth(), image.getIntrinsicHeight());
            SpannableString sb = new SpannableString(" ");
            ImageSpan imageSpan = new ImageSpan(image, ImageSpan.ALIGN_BOTTOM);
            sb.setSpan(imageSpan, 0, 1, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
            return sb;
        }
    }

    private class FillMarqueeAdapterAsync extends AsyncTask<Void, Void, Void> {
        final List<Adds> tmpAdds;

        public FillMarqueeAdapterAsync() {
            tmpAdds = new ArrayList<>();
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            Adds adds1 = new Adds();
            adds1.setTitle("Slide 1");
            adds1.setUrl(getString(R.string.llegoati_url));
            Adds adds2 = new Adds();
            adds2.setTitle("Slide 2");
            adds2.setUrl(getString(R.string.llegoati_url));

            tmpAdds.add(adds1);
            tmpAdds.add(adds2);
            String adds = "";
            int count = tmpAdds.size() - 1;
            for (Adds item : tmpAdds) {
                if (item.getTitle() != null && item.getUrl() != null) {
                    adds += String.format(Locale.US, "<a href='%s' %s>%s - <span style='font-color:yellow;'>%s</span></a>",
                            item.getUrl(),
                            styles(),
                            item.getTitle(),
                            item.getPrice());
                    if (count-- > 1) {
                        adds += "<span style='margin-left:5px;margin-right:5px;'>|</span>";
                    }
                }
            }
            wvAdds.loadData(wrapMarquee(adds), "text/html",
                    "UTF-8");

            super.onPostExecute(aVoid);
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected Void doInBackground(Void... params) {
            try {
              tmpAdds.addAll(repository.adds());
            } catch (IOException e) {
                e.printStackTrace();
            }
            return null;
        }
    }
}
