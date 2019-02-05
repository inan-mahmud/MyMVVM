package com.ps.mymvvm.ui.list;


import com.ps.mymvvm.data.model.GitHubRepo;

/**
 * Created by Prokash Sarkar on 2/5/19.
 */
public interface RepoSelectedListener {

    void onRepoSelected(GitHubRepo repo);
}
