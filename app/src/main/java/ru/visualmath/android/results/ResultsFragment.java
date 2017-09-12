package ru.visualmath.android.results;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.arellomobile.mvp.MvpAppCompatFragment;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import ru.visualmath.android.R;
import ru.visualmath.android.api.model.Results;

public class ResultsFragment extends MvpAppCompatFragment {

    public static final String TAG = "ResultsFragment";

    private static final String RESULTS_ARGUMENT = "RESULTS_ARGUMENT";

    @BindView(R.id.recyclerView)
    RecyclerView recyclerView;

    private Results results;
    private Unbinder unbinder;

    public static ResultsFragment newInstance(Results results) {

        Bundle args = new Bundle();
        args.putSerializable(RESULTS_ARGUMENT, results);

        ResultsFragment fragment = new ResultsFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        results = (Results) getArguments().getSerializable(RESULTS_ARGUMENT);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_resuts, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        unbinder = ButterKnife.bind(this, view);

        recyclerView.setHasFixedSize(true);
        recyclerView.setAdapter(new ResultsAdapter(results));
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }
}
