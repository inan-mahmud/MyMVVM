package com.ps.mymvvm.ui.lookup;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;

import com.jakewharton.rxbinding3.view.RxView;
import com.jakewharton.rxbinding3.widget.RxTextView;
import com.ps.mymvvm.R;
import com.ps.mymvvm.base.BaseActivity;
import com.ps.mymvvm.ui.main.MainActivity;
import com.ps.mymvvm.util.FormValidator;

import androidx.annotation.NonNull;
import butterknife.BindView;

import static com.ps.mymvvm.ui.main.MainActivity.INTENT_KEY_USERNAME;

/**
 * Created by Prokash Sarkar on 2/5/19.
 */
public class LookupActivity extends BaseActivity {

    public static final String KEY_USERNAME = "username";

    @BindView(R.id.edit_username)
    EditText usernameEditText;

    @BindView(R.id.button_lookup)
    Button lookupButton;

    @Override
    protected int layoutRes() {
        return R.layout.activity_lookup;
    }

    @Override
    protected int toolbarIcon() {
        return R.mipmap.ic_launcher;
    }

    @Override
    protected int toolbarTitle() {
        return R.string.lookup_user;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Check if any data available from Bundle instance
        if (savedInstanceState != null && savedInstanceState.containsKey(KEY_USERNAME)) {
            usernameEditText.setText(savedInstanceState.getString(KEY_USERNAME));
        }

        observeUsernameInput();
        observeLookupButtonClick();
    }

    /**
     * Observes button click and redirect the current activity to MainActivity
     * Along with username as intent extra
     */
    @SuppressLint("CheckResult")
    private void observeLookupButtonClick() {
        RxView.clicks(lookupButton).subscribe(__ ->
                startActivity(new Intent(LookupActivity.this, MainActivity.class)
                        .putExtra(INTENT_KEY_USERNAME, usernameEditText.getText().toString())));
    }

    /**
     * Listen to text inputs and validates string
     */
    @SuppressLint("CheckResult")
    private void observeUsernameInput() {
        RxTextView.textChanges(usernameEditText)
                .skip(1)
                .map(CharSequence::toString)
                .subscribe(username -> {
                    if (!FormValidator.validateFieldsWithLength(username, 3)) {
                        usernameEditText.setError(getString(R.string.error_username_too_short));
                        lookupButton.setEnabled(false);
                    } else {
                        lookupButton.setEnabled(true);
                    }
                });
    }

    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putString(KEY_USERNAME, usernameEditText.getText().toString());
    }
}
