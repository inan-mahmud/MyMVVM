package com.ps.mymvvm.data.rest;

import com.ps.mymvvm.data.model.GitHubRepo;

import java.util.List;

import javax.inject.Inject;

import io.reactivex.Single;

/**
 * Created by Prokash Sarkar on 2/5/19.
 */
public class GithubRepository {


    private final GithubService githubService;

    @Inject
    public GithubRepository(GithubService githubService) {
        this.githubService = githubService;
    }

    public Single<List<GitHubRepo>> getRepositoriesByUsername(String username) {
        return githubService.getReposForUser(username);
    }

    public Single<GitHubRepo> getRepo(String owner, String name) {
        return githubService.getRepoDetails(owner, name);
    }
}