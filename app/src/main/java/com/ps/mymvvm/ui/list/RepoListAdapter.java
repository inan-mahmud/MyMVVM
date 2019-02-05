package com.ps.mymvvm.ui.list;

import android.view.LayoutInflater;
import android.view.ViewGroup;

import com.ps.mymvvm.R;
import com.ps.mymvvm.data.model.GitHubRepo;
import com.ps.mymvvm.databinding.ListItemRepoBinding;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.FragmentActivity;
import androidx.lifecycle.LifecycleOwner;
import androidx.recyclerview.widget.RecyclerView;

/**
 * Created by Prokash Sarkar on 2/5/19.
 */
public class RepoListAdapter extends RecyclerView.Adapter<RepoListAdapter.RepoViewHolder> {

    private final List<GitHubRepo> data = new ArrayList<>();
    private FragmentActivity activity;
    private RepoSelectedListener repoSelectedListener;

    RepoListAdapter(FragmentActivity activity, ListViewModel viewModel, LifecycleOwner lifecycleOwner, RepoSelectedListener repoSelectedListener) {
        this.activity = activity;
        this.repoSelectedListener = repoSelectedListener;
        viewModel.getGithubRepoList().observe(lifecycleOwner, repos -> {
            data.clear();
            if (repos != null) {
                data.addAll(repos);
                notifyDataSetChanged();
            }
        });
        setHasStableIds(true);
    }

    @NonNull
    @Override
    public RepoViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        ListItemRepoBinding binding = DataBindingUtil.inflate(LayoutInflater.from(activity), R.layout.list_item_repo, null, false);
        return new RepoViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull RepoViewHolder holder, int position) {
        GitHubRepo repo = data.get(position);
        holder.binding.setRepo(repo);
        holder.itemView.setOnClickListener(e -> repoSelectedListener.onRepoSelected(repo));
    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    @Override
    public long getItemId(int position) {
        return data.get(position).getId();
    }

    static final class RepoViewHolder extends RecyclerView.ViewHolder {

        ListItemRepoBinding binding;

        RepoViewHolder(ListItemRepoBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }
    }
}
