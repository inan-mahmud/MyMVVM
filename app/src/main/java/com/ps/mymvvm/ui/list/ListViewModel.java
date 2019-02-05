package com.ps.mymvvm.ui.list;


import com.ps.mymvvm.data.model.GitHubRepo;
import com.ps.mymvvm.data.rest.GithubRepository;

import java.util.List;
import java.util.concurrent.TimeUnit;

import javax.inject.Inject;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.observers.DisposableSingleObserver;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by Prokash Sarkar on 2/5/19.
 */
public class ListViewModel extends ViewModel {

    private final GithubRepository repoRepository;
    private final MutableLiveData<List<GitHubRepo>> githubRepoList = new MutableLiveData<>();
    private final MutableLiveData<Boolean> repoLoadError = new MutableLiveData<>();
    private final MutableLiveData<Boolean> loading = new MutableLiveData<>();
    private CompositeDisposable disposable;

    @Inject
    public ListViewModel(GithubRepository repoRepository) {
        this.repoRepository = repoRepository;
        disposable = new CompositeDisposable();
    }

    LiveData<List<GitHubRepo>> getGithubRepoList() {
        return githubRepoList;
    }

    LiveData<Boolean> getError() {
        return repoLoadError;
    }

    LiveData<Boolean> getLoading() {
        return loading;
    }

    public void fetchGithubRepos(String username) {
        loading.setValue(true);
        disposable.add(repoRepository.getRepositoriesByUsername(username)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(new DisposableSingleObserver<List<GitHubRepo>>() {
                    @Override
                    public void onSuccess(List<GitHubRepo> value) {
                        repoLoadError.setValue(false);
                        githubRepoList.setValue(value);
                        loading.setValue(false);
                    }

                    @Override
                    public void onError(Throwable e) {
                        repoLoadError.setValue(true);
                        loading.setValue(false);
                    }
                }));
    }

    @Override
    protected void onCleared() {
        super.onCleared();
        if (disposable != null) {
            disposable.clear();
            disposable = null;
        }
    }
}
