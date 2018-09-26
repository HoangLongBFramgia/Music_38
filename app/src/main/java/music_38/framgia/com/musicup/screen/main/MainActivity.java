package music_38.framgia.com.musicup.screen.main;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.constraint.Group;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import music_38.framgia.com.musicup.R;
import music_38.framgia.com.musicup.screen.base.BaseActivity;
import music_38.framgia.com.musicup.screen.home.HomeFragment;
import music_38.framgia.com.musicup.screen.person.PersonFragment;
import music_38.framgia.com.musicup.screen.search.SearchFragment;
import music_38.framgia.com.musicup.utils.FragmentTransactionUtils;

public class MainActivity extends BaseActivity implements View.OnClickListener,
        BottomNavigationView.OnNavigationItemSelectedListener, OnHideViewCallback {

    private BottomNavigationView mBottomBar;
    private TextView mTextTitle;
    private ImageView mImageInfo;
    private Group mGroup;

    @Override
    protected int getLayoutResource() {
        return R.layout.activity_main;
    }

    @Override
    protected void initComponent() {
        initView();
        initListener();
        openDefaultFragment();
    }

    private void initView() {
        mBottomBar = findViewById(R.id.bottom_bar);
        mTextTitle = findViewById(R.id.text_title);
        mImageInfo = findViewById(R.id.image_info);
        mGroup = findViewById(R.id.constraint_group);

    }

    private void initListener() {
        mBottomBar.setOnNavigationItemSelectedListener(this);
        mImageInfo.setOnClickListener(this);
    }

    private void openDefaultFragment() {
        mTextTitle.setText(R.string.title_discover);
        mBottomBar.setSelectedItemId(R.id.action_home);
        FragmentTransactionUtils.addFragment(getSupportFragmentManager(), new HomeFragment(),
                R.id.container, HomeFragment.TAG);
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
        Fragment mFragment = null;

        switch (menuItem.getItemId()) {
            case R.id.action_home:
                mFragment = getSupportFragmentManager().findFragmentByTag(HomeFragment.TAG);
                if (mFragment == null) {
                    mFragment = new HomeFragment();
                }
                mTextTitle.setText(R.string.title_discover);
                FragmentTransactionUtils.addFragment(getSupportFragmentManager(), mFragment,
                        R.id.container, HomeFragment.TAG);
                mGroup.setVisibility(View.VISIBLE);
                return true;
            case R.id.action_trending:
                mFragment = getSupportFragmentManager().findFragmentByTag(SearchFragment.TAG);
                if (mFragment == null) {
                    mFragment = new SearchFragment();
                }
                mTextTitle.setText(R.string.title_trending);
                FragmentTransactionUtils.addFragment(getSupportFragmentManager(), mFragment,
                        R.id.container, SearchFragment.TAG);
                mGroup.setVisibility(View.GONE);
                return true;
            case R.id.action_person:
                mFragment = getSupportFragmentManager().findFragmentByTag(PersonFragment.TAG);
                if (mFragment == null) {
                    mFragment = new PersonFragment();
                }
                mTextTitle.setText(R.string.title_person);
                mGroup.setVisibility(View.VISIBLE);
                FragmentTransactionUtils.addFragment(getSupportFragmentManager(), mFragment,
                        R.id.container, PersonFragment.TAG);
                return true;
        }
        return false;
    }

    @Override
    public void onBackPressed() {
        if (getSupportFragmentManager().getBackStackEntryCount() == 0) {
            finish();
        } else {
            getSupportFragmentManager().popBackStack();
        }
    }

    @Override
    protected void initData(Bundle savedInstanceState) {
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.image_info:
                //TODO: info
                break;
            default:
                break;
        }
    }

    @Override
    public void onHideBottomBar(int visibility) {
        mBottomBar.setVisibility(visibility);
    }
}
