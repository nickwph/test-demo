package com.nicholasworkshop.tinklabstest.fragment.guide;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.nicholasworkshop.tinklabstest.MainApplication;
import com.nicholasworkshop.tinklabstest.MainComponent;

import javax.inject.Inject;

/**
 * Created by nicholas.wong on 2017/05/15.
 */

public class GuideFragment extends Fragment {

    private static final int INITIAL_STORY_COUNT = 4;
    private static final int FETCH_STORY_COUNT = 10;

    @Inject GuideView guideView;
    @Inject GuideModel guideModel;

    public static GuideFragment newInstance() {
        return new GuideFragment();
    }

    public GuideFragment() {
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        MainComponent component = ((MainApplication) this.getActivity().getApplication()).getComponent();
        component.inject(this);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        return guideView.createView(inflater, container);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        guideModel
                .storyList(INITIAL_STORY_COUNT)
                .subscribe(stories -> guideView.setStoryList(stories));
        guideView
                .storyRequests()
                .flatMap(firstVisibleItemPosition -> guideModel.storyList(firstVisibleItemPosition + FETCH_STORY_COUNT))
                .subscribe(stories -> guideView.setStoryList(stories));
        guideModel
                .ads()
                .subscribe(ads -> guideView.setAdList(ads));
    }
}
