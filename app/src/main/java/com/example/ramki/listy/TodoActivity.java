package com.example.ramki.listy;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;


import com.example.ramki.listy.ReaderViewPagerTransformer.TransformType;
import com.example.ramki.listy.model.TodoEntry;
import com.example.ramki.listy.persistence.TodoListDaoPersistenceManager;
import com.example.ramki.listy.persistence.TodoListPersistenceManager;


import java.util.ArrayList;
import java.util.List;


public class TodoActivity extends FragmentActivity {
    private List<TodoEntry> todoEntries = new ArrayList<>();
    private TodoListPersistenceManager _todoListPersistenceManager;

    /**
     * The pager widget, which handles animation and allows swiping horizontally to access previous
     * and next wizard steps.
     */
    private ViewPager mPager;

    /**
     * The pager adapter, which provides the pages to the view pager widget.
     */
    private PagerAdapter mPagerAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        _todoListPersistenceManager = new TodoListDaoPersistenceManager(this);
        try {
            todoEntries = _todoListPersistenceManager.readItems();
        } catch (TodoListManagerException e) {
            e.printStackTrace();
        }

        // Instantiate a ViewPager and a PagerAdapter.
        mPager = (ViewPager) findViewById(R.id.pager);
        mPagerAdapter = new ScreenSlidePagerAdapter(getSupportFragmentManager());
        mPager.setAdapter(mPagerAdapter);
        mPager.setPageTransformer(false, new ReaderViewPagerTransformer(TransformType.SLIDE_OVER));
    }

    @Override
    protected void onDestroy() {
        _todoListPersistenceManager.close();
        super.onDestroy();

    }

    @Override
    public void onBackPressed() {
        if (mPager.getCurrentItem() == 0) {
            // If the user is currently looking at the first step, allow the system to handle the
            // Back button. This calls finish() on this activity and pops the back stack.
            super.onBackPressed();
        } else {
            // Otherwise, select the previous step.
            mPager.setCurrentItem(mPager.getCurrentItem() - 1);
        }
    }


    private class ScreenSlidePagerAdapter extends FragmentStatePagerAdapter {
        public ScreenSlidePagerAdapter(FragmentManager fm) {

            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            /**
             * First page is the list (TodoListFragment) and the subsequent ones
             * are detail for each of the todo (TodoDetailsFragment)
             *
             */
            if (position == 0) {
                // Create a new Fragment to be placed in the activity layout
                TodoListFragment todoListFragment =  new TodoListFragment();
                todoListFragment.setTodoEntries(todoEntries);
                todoListFragment.setTodoListPersistenceManager(_todoListPersistenceManager);
                todoListFragment.setPagerAdapter(mPagerAdapter);
                todoListFragment.setPager(mPager);
                return todoListFragment;

            } else {
                TodoDetailsFragment todoDetailsFragment = new TodoDetailsFragment();
                todoDetailsFragment.setTodo(todoEntries.get(position - 1));
                return todoDetailsFragment;
            }
        }

        @Override
        public int getCount() {
            return todoEntries.size()+1;
        }
    }

}
