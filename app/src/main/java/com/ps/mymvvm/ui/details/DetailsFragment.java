package com.ps.mymvvm.ui.details;

import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.ps.mymvvm.R;
import com.ps.mymvvm.base.BaseFragment;
import com.ps.mymvvm.util.ViewModelFactory;

import javax.inject.Inject;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.lifecycle.ViewModelProviders;
import butterknife.BindView;

/**
 * Created by Prokash Sarkar on 2/5/19.
 */
public class DetailsFragment extends BaseFragment {

    @BindView(R.id.tv_repo_name)
    TextView repoNameTextView;

    @BindView(R.id.tv_repo_description)
    TextView repoDescriptionTextView;

    @BindView(R.id.tv_forks)
    TextView forksTextView;

    @BindView(R.id.tv_stars)
    TextView starsTextView;

    @BindView(R.id.layout_body)
    LinearLayout layoutBody;

    @BindView(R.id.tv_error)
    TextView errorTextView;

    @BindView(R.id.pb_loading)
    View loadingProgressBar;

    @Inject
    ViewModelFactory viewModelFactory;

    private DetailsViewModel viewModel;

    @Override
    protected int layoutRes() {
        return R.layout.fragment_details;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        viewModel = ViewModelProviders.of(getBaseActivity()).get(DetailsViewModel.class);
        viewModel.restoreFromBundle(savedInstanceState);
        observableViewModel();
        displayRepoDetails();
    }

    private void observableViewModel() {
        viewModel.getSelectedRepo().observe(this, repos -> {
            if (repos != null) layoutBody.setVisibility(View.VISIBLE);
        });

        viewModel.getError().observe(this, isError -> {
            if (isError != null) if (isError) {
                errorTextView.setVisibility(View.VISIBLE);
                layoutBody.setVisibility(View.GONE);
                errorTextView.setText(getString(R.string.error_loading_data));
            } else {
                errorTextView.setVisibility(View.GONE);
                errorTextView.setText(null);
            }
        });

        viewModel.getLoading().observe(this, isLoading -> {
            if (isLoading != null) {
                loadingProgressBar.setVisibility(isLoading ? View.VISIBLE : View.GONE);
                if (isLoading) {
                    errorTextView.setVisibility(View.GONE);
                    layoutBody.setVisibility(View.GONE);
                }
            }
        });
    }

    private void displayRepoDetails() {
        viewModel.getSelectedRepo().observe(this, repo -> {
            if (repo != null) {
                layoutBody.setVisibility(View.VISIBLE);
                errorTextView.setVisibility(View.GONE);
                loadingProgressBar.setVisibility(View.GONE);

                repoNameTextView.setText(repo.getName());
                repoDescriptionTextView.setText(repo.getDescription());
                forksTextView.setText(String.valueOf(repo.getForks()));
                starsTextView.setText(String.valueOf(repo.getStargazersCount()));
            }
        });
    }

    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        viewModel.saveToBundle(outState);
    }

}
