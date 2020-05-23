
package com.taopao.hulkbase.delegate.life;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import com.taopao.hulkbase.delegate.fragment.FragmentDelegate;
import com.taopao.hulkbase.delegate.fragment.FragmentDelegateImpl;

/**
 * @Copyright (C), 2017-2020
 * @Author: TaoPao
 * @Date: 2020/5/22 下午3:53
 * @Description: java类作用描述
 */
public class FragmentLifecycle extends FragmentManager.FragmentLifecycleCallbacks {
    private FragmentDelegateImpl mFragmentDelegate;

    @Override
    public void onFragmentAttached(@NonNull FragmentManager fm, @NonNull Fragment f, @NonNull Context context) {
        FragmentDelegate fragmentDelegate = fetchFragmentDelegate(fm, f);
        if (fragmentDelegate != null) {
            fragmentDelegate.onAttach(context);
        }

    }

    @Override
    public void onFragmentCreated(@NonNull FragmentManager fm, @NonNull Fragment f, Bundle savedInstanceState) {
        FragmentDelegate fragmentDelegate = fetchFragmentDelegate(fm, f);
        if (fragmentDelegate != null) {
            fragmentDelegate.onCreate(savedInstanceState);
        }
    }

    @Override
    public void onFragmentViewCreated(@NonNull FragmentManager fm, @NonNull Fragment f, @NonNull View v, Bundle savedInstanceState) {
        FragmentDelegate fragmentDelegate = fetchFragmentDelegate(fm, f);
        if (fragmentDelegate != null) {
            fragmentDelegate.onCreateView(v, savedInstanceState);
        }
    }

    @Override
    public void onFragmentActivityCreated(@NonNull FragmentManager fm, @NonNull Fragment f, Bundle savedInstanceState) {
        FragmentDelegate fragmentDelegate = fetchFragmentDelegate(fm, f);
        if (fragmentDelegate != null) {
            fragmentDelegate.onActivityCreate(savedInstanceState);
        }
    }

    @Override
    public void onFragmentStarted(@NonNull FragmentManager fm, @NonNull Fragment f) {
        FragmentDelegate fragmentDelegate = fetchFragmentDelegate(fm, f);
        if (fragmentDelegate != null) {
            fragmentDelegate.onStart();
        }
    }

    @Override
    public void onFragmentResumed(@NonNull FragmentManager fm, @NonNull Fragment f) {
        FragmentDelegate fragmentDelegate = fetchFragmentDelegate(fm, f);
        if (fragmentDelegate != null) {
            fragmentDelegate.onResume();
        }
    }

    @Override
    public void onFragmentPaused(@NonNull FragmentManager fm, @NonNull Fragment f) {
        FragmentDelegate fragmentDelegate = fetchFragmentDelegate(fm, f);
        if (fragmentDelegate != null) {
            fragmentDelegate.onPause();
        }
    }

    @Override
    public void onFragmentStopped(@NonNull FragmentManager fm, @NonNull Fragment f) {
        FragmentDelegate fragmentDelegate = fetchFragmentDelegate(fm, f);
        if (fragmentDelegate != null) {
            fragmentDelegate.onStop();
        }
    }

    @Override
    public void onFragmentSaveInstanceState(@NonNull FragmentManager fm, @NonNull Fragment f, @NonNull Bundle outState) {
        FragmentDelegate fragmentDelegate = fetchFragmentDelegate(fm, f);
        if (fragmentDelegate != null) {
            fragmentDelegate.onSaveInstanceState(outState);
        }
    }

    @Override
    public void onFragmentViewDestroyed(@NonNull FragmentManager fm, @NonNull Fragment f) {
        FragmentDelegate fragmentDelegate = fetchFragmentDelegate(fm, f);
        if (fragmentDelegate != null) {
            fragmentDelegate.onDestroyView();
        }
    }

    @Override
    public void onFragmentDestroyed(@NonNull FragmentManager fm, @NonNull Fragment f) {
        FragmentDelegate fragmentDelegate = fetchFragmentDelegate(fm, f);
        if (fragmentDelegate != null) {
            fragmentDelegate.onDestroy();
        }
    }

    @Override
    public void onFragmentDetached(@NonNull FragmentManager fm, @NonNull Fragment f) {
        FragmentDelegate fragmentDelegate = fetchFragmentDelegate(fm, f);
        if (fragmentDelegate != null) {
            fragmentDelegate.onDetach();
        }
    }

    private FragmentDelegate fetchFragmentDelegate(FragmentManager fm, Fragment fragment) {
        if (mFragmentDelegate == null) {
            mFragmentDelegate = new FragmentDelegateImpl(fm, fragment);
        }
        return mFragmentDelegate;
    }
}