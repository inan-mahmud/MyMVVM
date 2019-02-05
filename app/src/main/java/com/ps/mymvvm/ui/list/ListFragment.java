package com.ps.mymvvm.ui.list;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.ps.mymvvm.R;
import com.ps.mymvvm.base.BaseFragment;
import com.ps.mymvvm.data.model.GitHubRepo;
import com.ps.mymvvm.ui.details.DetailsFragment;
import com.ps.mymvvm.ui.details.DetailsViewModel;
import com.ps.mymvvm.util.ViewModelFactory;

import javax.inject.Inject;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import butterknife.BindView;

/**
 * Created by Prokash Sarkar on 2/5/19.
 */
public class ListFragment extends BaseFragment implements RepoSelectedListener {

    public static final String BUNDLE_KEY_USERNAME = "username";

    @BindView(R.id.recycler_view)
    RecyclerView listView;

    @BindView(R.id.tv_error)
    TextView errorTextView;

    @BindView(R.id.pb_loading)
    View loadingProgressBar;

    @Inject
    ViewModelFactory viewModelFactory;

    private ListViewModel viewModel;

    public static ListFragment newInstance(String username) {
        ListFragment fragment = new ListFragment();
        Bundle args = new Bundle();
        args.putString(BUNDLE_KEY_USERNAME, username);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    protected int layoutRes() {
        return R.layout.fragment_list;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        viewModel = ViewModelProviders.of(this, viewModelFactory).get(ListViewModel.class);

        listView.addItemDecoration(new DividerItemDecoration(getBaseActivity(), DividerItemDecoration.VERTICAL));
        listView.setAdapter(new RepoListAdapter(getActivity(), viewModel, this, this));
        listView.setLayoutManager(new LinearLayoutManager(getContext()));

        // Get bundle argument passed from activity and load the repo
        if (getArguments() != null && getArguments().getString(BUNDLE_KEY_USERNAME) != null) {
            viewModel.fetchGithubRepos(getArguments().getString(BUNDLE_KEY_USERNAME));
        }

        observableViewModel();
    }

    private void observableViewModel() {
        viewModel.getGithubRepoList().observe(this, repos -> {
            if (repos != null) listView.setVisibility(View.VISIBLE);
        });

        viewModel.getError().observe(this, isError -> {
            if (isError != null) if (isError) {
                errorTextView.setVisibility(View.VISIBLE);
                listView.setVisibility(View.GONE);
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
                    listView.setVisibility(View.GONE);
                }
            }
        });
    }

    @Override
    public void onRepoSelected(GitHubRepo repo) {
        DetailsViewModel detailsViewModel = ViewModelProviders.of(getBaseActivity(), viewModelFactory).get(DetailsViewModel.class);
        detailsViewModel.setSelectedRepo(repo);
        getBaseActivity().getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, new DetailsFragment())
                .addToBackStack(null).commit();
    }
}
