package com.example.ramki.listy;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;

import com.example.ramki.listy.ReaderViewPagerTransformer.TransformType;
import com.example.ramki.listy.model.TodoEntry;
import com.example.ramki.listy.persistence.TodoListDaoPersistenceManager;
import com.example.ramki.listy.persistence.TodoListPersistenceManager;


import java.util.ArrayList;
import java.util.List;


public class TodoActivity extends FragmentActivity implements
    OnTodoUpdateHandler, OnTodoAddHandler, OnTodoDeleteHandler, OnTodoSelectHandler {

    private List<TodoEntry> todoList = new ArrayList<>();
    private TodoListPersistenceManager todoListPersistenceManager;

    /**
     * The pager widget, which handles animation and allows swiping horizontally to access previous
     * and next wizard steps.
     */
    private ViewPager mPager;

    /**
     * The pager adapter, which provides the pages to the view pager widget.
     */
    private PagerAdapter mPagerAdapter;


    private OnPageChangeListener pageChangeListener = new OnPageChangeListener() {

        int currentPosition = 0;

        @Override
        public void onPageSelected(int newPosition) {
            ScreenSlidePagerAdapter _pagerAdapter = (ScreenSlidePagerAdapter) mPagerAdapter;
            Fragment fragmentToHide = (Fragment) _pagerAdapter.instantiateItem(mPager,
                currentPosition);
            fragmentToHide.onPause();
            currentPosition = newPosition;
        }

        @Override
        public void onPageScrolled(int arg0, float arg1, int arg2) {
        }

        public void onPageScrollStateChanged(int arg0) {
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        todoListPersistenceManager = new TodoListDaoPersistenceManager(this);
        try {
            todoList = todoListPersistenceManager.readItems();
        } catch (TodoListManagerException e) {
            e.printStackTrace();
        }

        // Instantiate a ViewPager and a PagerAdapter.
        mPager = (ViewPager) findViewById(R.id.pager);
        mPagerAdapter = new ScreenSlidePagerAdapter(getSupportFragmentManager(), this);
        mPager.setAdapter(mPagerAdapter);
        mPager.setPageTransformer(false, new ReaderViewPagerTransformer(TransformType.SLIDE_OVER));
        mPager.addOnPageChangeListener(pageChangeListener);
    }

    @Override
    protected void onDestroy() {
        todoListPersistenceManager.close();
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

    // TODOLIST event handlers (to handle add, update, delete and select

    @Override
    public void onTodoAdd(TodoEntry todo) {
        mPagerAdapter.notifyDataSetChanged();
        try {
            todo = todoListPersistenceManager.addItem(todo);
        } catch (TodoListManagerException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onTodoSelect(int position) {
        // move to the detailed view of the selected todo
        mPager.setCurrentItem(position + 1, true);
    }

    @Override
    public void onTodoDelete(TodoEntry deleted) {
        todoList.remove(deleted);
        // update the view pager
        int i = mPager.getCurrentItem();
        mPagerAdapter.notifyDataSetChanged();
        if (todoList.size() == 0) {
            mPager.setCurrentItem(0, true);
        } else {
            mPager.setCurrentItem(i, true);
        }
        // delete from the db
        try {
            todoListPersistenceManager.deleteItem(deleted);
        } catch (TodoListManagerException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onTodoUpdate(TodoEntry todo) {
        // update in the db
        try {
            todoListPersistenceManager.updateItem(todo);
        } catch (TodoListManagerException e) {
            e.printStackTrace();
        }
    }


    private class ScreenSlidePagerAdapter extends FragmentStatePagerAdapter {

        private final Activity _activity;

        public ScreenSlidePagerAdapter(FragmentManager fm, Activity activity) {
            super(fm);
            this._activity = activity;
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
                ITodoListFragment todoListFragment = new TodoListFragment();
                todoListFragment.setTodoList(todoList);
                todoListFragment.setOnAddHandler((OnTodoAddHandler) _activity);
                todoListFragment.setOnDeleteHandler((OnTodoDeleteHandler) _activity);
                todoListFragment.setOnSelectHandler((OnTodoSelectHandler) _activity);
                return todoListFragment;

            } else {
                ITodoDetailFragment todoDetailsFragment = new TodoDetailsFragment();
                todoDetailsFragment.setTodoEntry(todoList.get(position - 1));
                todoDetailsFragment.setOnUpdateHandler((OnTodoUpdateHandler) _activity);
                todoDetailsFragment.setOnDeleteHandler((OnTodoDeleteHandler) _activity);
                return todoDetailsFragment;
            }
        }

        @Override
        public int getCount() {
            return todoList.size() + 1;
        }
    }

}
