package ru.visualmath.android.util;

import android.support.annotation.IdRes;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;

import io.reactivex.functions.Function;

public class FragmentUtil {

    public static void showFragment(FragmentManager fragmentManager, @IdRes int id, String tag, Function<Void, Fragment> newInstance) {
        Fragment fragment = fragmentManager.findFragmentByTag(tag);
        if (fragment == null) {
            try {
                fragment = newInstance.apply(null);
                fragmentManager
                        .beginTransaction()
                        .replace(id, fragment, tag)
                        .addToBackStack(null)
                        .commit();
            } catch (Exception ignored) {
            }
        } else {
            fragmentManager
                    .beginTransaction()
                    .replace(id, fragment, tag)
                    .commit();
        }
    }
}
