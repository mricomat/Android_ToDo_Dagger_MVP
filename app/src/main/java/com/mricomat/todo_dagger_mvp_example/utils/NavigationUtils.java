package com.mricomat.todo_dagger_mvp_example.utils;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentTransaction;

/**
 * Created by mricomat on 05/03/2018.
 */

public class NavigationUtils {

    public static void navigateToFragment(FragmentActivity activity, Fragment fragment,
                                          int contentFrame, boolean addToBackStack) {
        if(fragment == null) {
            return;
        }

        FragmentTransaction fragmentTransaction = activity.getSupportFragmentManager()
            .beginTransaction();

        String tag = ((Object)fragment).getClass().getName();

        fragmentTransaction.replace(contentFrame, fragment, tag);

        if(addToBackStack) {
            fragmentTransaction.addToBackStack(tag);
        }

        fragmentTransaction.commit();

        if(contentFrame < 0) {
            activity.getSupportFragmentManager().executePendingTransactions();
        }
    }

    public static void navigateToActivity(Activity activityLaunching, Class classToStartIntent,
                                          Bundle extras, int requestCode) {
        Intent intent = createIntent(activityLaunching.getApplicationContext(), classToStartIntent, extras);
        performNavigationToActivity(activityLaunching, intent, requestCode, false);
    }

    private static Intent createIntent(Context context, Class classToStartIntent, Bundle extras) {
        Intent intent = new Intent(context, classToStartIntent);
        if (extras != null) {
            intent.putExtras(extras);
        }
        return intent;
    }

    private static void performNavigationToActivity(Activity activityLaunching, Intent intent,
                                                    int requestCode, boolean removePreviousActivity) {
        if (activityLaunching != null) {
            if (requestCode > 0) {
                activityLaunching.startActivityForResult(intent, requestCode);
            } else {
                activityLaunching.startActivity(intent);
            }
        }
        if (removePreviousActivity) {
            activityLaunching.finish();
        }
    }
}
