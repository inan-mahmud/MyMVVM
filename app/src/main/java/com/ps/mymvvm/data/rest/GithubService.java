package com.ps.mymvvm.data.rest;

import com.ps.mymvvm.data.model.GitHubRepo;

import java.util.List;

import io.reactivex.Single;
import retrofit2.http.GET;
import retrofit2.http.Path;

/**
 * Created by Prokash Sarkar on 2/5/19.
 */
public interface GithubService {

    @GET("users/{user}/repos")
    Single<List<GitHubRepo>> getReposForUser(@Path("user") String user);

    @GET("repos/{user}/{repo}")
    Single<GitHubRepo> getRepoDetails(@Path("user") String username, @Path("repo") String repo);

    /*@GET("user/{user}")
    Single<> getUser(@Path("user") String username);*/
}
