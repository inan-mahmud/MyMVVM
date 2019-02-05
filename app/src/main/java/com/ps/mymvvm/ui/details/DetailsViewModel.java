package com.ps.mymvvm.ui.details;

import android.os.Bundle;

import com.ps.mymvvm.data.model.GitHubRepo;
import com.ps.mymvvm.data.rest.GithubRepository;

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
public class DetailsViewModel extends ViewModel {

    public static final String KEY_REPO_DETAILS = "repo_details";

    private final GithubRepository githubRepository;
    private final MutableLiveData<GitHubRepo> selectedRepo = new MutableLiveData<>();
    private final MutableLiveData<Boolean> repoLoadError = new MutableLiveData<>();
    private final MutableLiveData<Boolean> loading = new MutableLiveData<>();
    private CompositeDisposable disposable;

    @Inject
    public DetailsViewModel(GithubRepository githubRepository) {
        this.githubRepository = githubRepository;
        disposable = new CompositeDisposable();
    }

    public LiveData<GitHubRepo> getSelectedRepo() {
        return selectedRepo;
    }

    public void setSelectedRepo(GitHubRepo repo) {
        selectedRepo.setValue(repo);
    }

    LiveData<Boolean> getError() {
        return repoLoadError;
    }

    LiveData<Boolean> getLoading() {
        return loading;
    }

    public void saveToBundle(Bundle outState) {
        if (selectedRepo.getValue() != null) {
            outState.putStringArray(KEY_REPO_DETAILS, new String[]{
                    selectedRepo.getValue().getOwner().getLogin(),
                    selectedRepo.getValue().getName()
            });
        }
    }

    public void restoreFromBundle(Bundle savedInstanceState) {
        if (selectedRepo.getValue() != null) {
            if (savedInstanceState != null && savedInstanceState.containsKey(KEY_REPO_DETAILS)) {
                loadRepo(savedInstanceState.getStringArray(KEY_REPO_DETAILS));
            }
        }
    }

    private void loadRepo(String[] repo_details) {
        disposable.add(githubRepository.getRepo(repo_details[0], repo_details[1])
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(new DisposableSingleObserver<GitHubRepo>() {
                    @Override
                    public void onSuccess(GitHubRepo value) {
                        repoLoadError.setValue(false);
                        selectedRepo.setValue(value);
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
