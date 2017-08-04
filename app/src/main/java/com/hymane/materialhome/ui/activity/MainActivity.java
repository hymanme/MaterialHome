package com.hymane.materialhome.ui.activity;

import android.animation.ValueAnimator;
import android.content.Intent;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.support.customtabs.CustomTabsIntent;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.design.widget.Snackbar;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.GravityCompat;
import android.support.v4.view.MenuItemCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatDelegate;
import android.support.v7.widget.SwitchCompat;
import android.support.v7.widget.Toolbar;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;
import android.widget.CompoundButton;
import android.widget.PopupWindow;

import com.hymane.materialhome.R;
import com.hymane.materialhome.common.Constant;
import com.hymane.materialhome.holder.SearchViewHolder;
import com.hymane.materialhome.ui.fragment.BaseFragment;
import com.hymane.materialhome.ui.fragment.BookshelfFragment;
import com.hymane.materialhome.ui.fragment.EBookFragment;
import com.hymane.materialhome.ui.fragment.HomeFragment;
import com.hymane.materialhome.utils.common.KeyBoardUtils;
import com.hymane.materialhome.utils.common.PermissionUtils;
import com.hymane.materialhome.utils.common.SPUtils;
import com.hymane.materialhome.utils.common.ScreenUtils;
import com.hymane.materialhome.utils.customtabs.CustomTabActivityHelper;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MainActivity extends BaseActivity implements NavigationView.OnNavigationItemSelectedListener {
    private static final int EXIT_APP_DELAY = 1000;
    @BindView(R.id.drawer_layout)
    DrawerLayout drawer;
    @BindView(R.id.nav_view)
    NavigationView mNavigationView;
    private FloatingActionButton mFab;

    private FragmentManager fragmentManager = getSupportFragmentManager();
    private BaseFragment currentFragment;
    private int currentIndex;
    private SwitchCompat mThemeSwitch;
    private PopupWindow mPopupWindow;
    private SearchViewHolder holder;
    private long lastTime = 0;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        if (fragmentManager == null) {
            fragmentManager = getSupportFragmentManager();
        }
        if (savedInstanceState == null) {
            currentFragment = HomeFragment.newInstance();
            FragmentTransaction ft = fragmentManager.beginTransaction();
            ft.replace(R.id.fl_content, currentFragment).commit();
        } else {
            //activity销毁后记住销毁前所在页面
            currentIndex = savedInstanceState.getInt("currentIndex");
            switch (this.currentIndex) {
                case 0:
                    if (currentFragment == null) {
                        currentFragment = HomeFragment.newInstance();
                    }
                    switchContent(null, currentFragment);
                    break;
                case 1:
                    if (currentFragment == null) {
                        currentFragment = BookshelfFragment.newInstance();
                    }
                    switchContent(null, currentFragment);
                    break;
            }
        }
        initNavView();
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    private void initNavView() {
        boolean night = SPUtils.getPrefBoolean(Constant.THEME_MODEL, false);
        if (night) {
            getDelegate().setLocalNightMode(AppCompatDelegate.MODE_NIGHT_YES);
        } else {
            getDelegate().setLocalNightMode(AppCompatDelegate.MODE_NIGHT_NO);
        }
        MenuItem item = mNavigationView.getMenu().findItem(R.id.nav_theme);
        mNavigationView.getMenu().findItem(R.id.nav_home).setChecked(true);
        mThemeSwitch = (SwitchCompat) MenuItemCompat.getActionView(item).findViewById(R.id.view_switch);
        mThemeSwitch.setChecked(night);
        mThemeSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                SPUtils.setPrefBoolean(Constant.THEME_MODEL, isChecked);
                mThemeSwitch.setChecked(isChecked);
                if (isChecked) {
                    getDelegate().setLocalNightMode(AppCompatDelegate.MODE_NIGHT_YES);
                } else {
                    getDelegate().setLocalNightMode(AppCompatDelegate.MODE_NIGHT_NO);
                }
            }
        });
    }

    @Override
    protected void initEvents() {

    }

    @Override
    public void onBackPressed() {
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            if (currentFragment == null) {
                currentFragment = HomeFragment.newInstance();
            }
            if (!(currentFragment instanceof HomeFragment)) {
                switchContent(currentFragment, HomeFragment.newInstance());
                mNavigationView.getMenu().findItem(R.id.nav_home).setChecked(true);
                return;
            }
            if ((System.currentTimeMillis() - lastTime) > EXIT_APP_DELAY) {
                Snackbar.make(drawer, getString(R.string.press_twice_exit), Snackbar.LENGTH_SHORT)
                        .setAction(R.string.exit_directly, v -> {
                            MainActivity.super.onBackPressed();
                        })
                        .show();
                lastTime = System.currentTimeMillis();
            } else {
                moveTaskToBack(true);
            }
        }
    }

    public void setToolbar(Toolbar toolbar) {
        if (toolbar != null) {
            mToolbar = toolbar;
            setSupportActionBar(toolbar);
            getSupportActionBar().setHomeButtonEnabled(true);
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);

            ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                    this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
            drawer.setDrawerListener(toggle);
            toggle.syncState();
            mNavigationView.setNavigationItemSelectedListener(this);
        }
    }

    public void setFab(FloatingActionButton fab) {
        mFab = fab;
        mFab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });
    }

    /**
     * 搜索框
     */
    public void showSearchView() {
        final WindowManager.LayoutParams lp = getWindow().getAttributes();
        if (mPopupWindow == null) {
            holder = new SearchViewHolder(this, code -> {
                switch (code) {
                    case SearchViewHolder.RESULT_SEARCH_EMPTY_KEYWORD:
                        Snackbar.make(drawer, R.string.keyword_is_empty, Snackbar.LENGTH_SHORT).show();
                        break;
                    case SearchViewHolder.RESULT_SEARCH_SEARCH:
                        String q = holder.et_search_content.getText().toString();
                        if (q.startsWith("@")) {
                            CustomTabActivityHelper.openCustomTab(
                                    this,
                                    new CustomTabsIntent.Builder()
                                            .setShowTitle(true)
                                            .setToolbarColor(ContextCompat.getColor(this, R.color.colorPrimary))
                                            .addDefaultShareMenuItem()
                                            .build(),
                                    Uri.parse(q.replace("@", "")));
                        } else {
                            Intent intent;
                            if (currentFragment instanceof EBookFragment) {
                                intent = new Intent(this, ESearchResultActivity.class);
                                intent.putExtra("type", 0);
                            } else {
                                intent = new Intent(this, SearchResultActivity.class);
                            }
                            intent.putExtra("q", q);
                            startActivity(intent);
                        }
                        break;
                    case SearchViewHolder.RESULT_SEARCH_GO_SCAN:
                        if (PermissionUtils.requestCameraPermission(this)) {
                            startActivity(new Intent(this, CaptureActivity.class));
                        }
                        break;
                    case SearchViewHolder.RESULT_SEARCH_CANCEL:
                        mPopupWindow.dismiss();
                        break;
                }
            });
            mPopupWindow = new PopupWindow(holder.getContentView(),
                    WindowManager.LayoutParams.MATCH_PARENT,
                    WindowManager.LayoutParams.WRAP_CONTENT, true);
            mPopupWindow.setBackgroundDrawable(new BitmapDrawable());
            mPopupWindow.setFocusable(true);
            mPopupWindow.setOutsideTouchable(true);
            // 设置popWindow的显示和消失动画
//                mPopupWindow.setAnimationStyle(R.style.PopupWindowStyle);
            mPopupWindow.setOnDismissListener(new PopupWindow.OnDismissListener() {
                @Override
                public void onDismiss() {
                    holder.et_search_content.setText("");
                    KeyBoardUtils.closeKeyBord(holder.et_search_content, MainActivity.this);
                    ValueAnimator animator = ValueAnimator.ofFloat(0.7f, 1f);
                    animator.setDuration(500);
                    animator.addUpdateListener(animation -> {
                        lp.alpha = (float) animation.getAnimatedValue();
                        lp.dimAmount = (float) animation.getAnimatedValue();
                        getWindow().setAttributes(lp);
                        getWindow().addFlags(WindowManager.LayoutParams.FLAG_DIM_BEHIND);
                    });
                    animator.start();
                }
            });
            mPopupWindow.setTouchInterceptor(new View.OnTouchListener() {
                @Override
                public boolean onTouch(View v, MotionEvent event) {
                    return false;
                }
            });
        }
        KeyBoardUtils.openKeyBord(holder.et_search_content, MainActivity.this);
        ValueAnimator animator = ValueAnimator.ofFloat(1f, 0.7f);
        animator.setDuration(500);
        animator.addUpdateListener(animation -> {
            lp.alpha = (float) animation.getAnimatedValue();
            lp.dimAmount = (float) animation.getAnimatedValue();
            getWindow().setAttributes(lp);
            getWindow().addFlags(WindowManager.LayoutParams.FLAG_DIM_BEHIND);
        });
        mPopupWindow.showAtLocation(mToolbar, Gravity.NO_GRAVITY, 0, ScreenUtils.getStatusHeight(activity));
        animator.start();
    }

    /**
     * switch fragment.
     *
     * @param from
     * @param to
     */

    public void switchContent(BaseFragment from, BaseFragment to) {
        if (currentFragment == to) {
            return;
        } else {
            currentFragment = to;
            //添加渐隐渐现的动画
            FragmentTransaction ft = fragmentManager.beginTransaction();
            ft.setCustomAnimations(R.anim.fragment_fade_in, R.anim.fragment_fade_out);
            ft.replace(R.id.fl_content, to).commit();
        }
        invalidateOptionsMenu();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        int menuId = R.menu.main;
        if (currentFragment instanceof HomeFragment) {
            menuId = R.menu.main;
        } else if (currentFragment instanceof BookshelfFragment) {
            menuId = R.menu.bookshelf_main;
        } else if (currentFragment instanceof EBookFragment) {
            menuId = R.menu.ebook_main;
        }
        getMenuInflater().inflate(menuId, menu);
        currentFragment.onCreateOptionsMenu(menu, getMenuInflater());
        return true;
    }

    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {
        return super.onPrepareOptionsMenu(menu);
    }

    public void showFloatingBar() {
        if (mFab != null) {
            mFab.show();
        }
    }

    public void hideFloatingBar() {
        if (mFab != null) {
            mFab.hide();
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_search) {
            showSearchView();
            return true;
        }
        currentFragment.onOptionsItemSelected(item);
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        outState.putInt("currentIndex", currentIndex);
        super.onSaveInstanceState(outState);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_home) {
            // Handle the camera action
            switchContent(currentFragment, HomeFragment.newInstance());
        } else if (id == R.id.nav_ebook) {
            switchContent(currentFragment, EBookFragment.newInstance());
        } else if (id == R.id.nav_bookshelf) {
            switchContent(currentFragment, BookshelfFragment.newInstance());
        } else if (id == R.id.nav_manage) {

        } else if (id == R.id.nav_theme) {


        } else if (id == R.id.nav_send) {
            Intent data = new Intent(Intent.ACTION_SENDTO);
            data.setData(Uri.parse("mailto:hymanme@163.com"));
            startActivity(data);
        }

        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
}
