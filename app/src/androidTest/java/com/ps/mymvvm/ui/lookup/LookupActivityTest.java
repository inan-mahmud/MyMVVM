package com.ps.mymvvm.ui.lookup;

import android.widget.TextView;

import com.ps.mymvvm.R;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.MockitoJUnit;
import org.mockito.junit.MockitoRule;

import androidx.test.espresso.ViewInteraction;
import androidx.test.rule.ActivityTestRule;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.replaceText;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.ViewMatchers.hasErrorText;
import static androidx.test.espresso.matcher.ViewMatchers.isEnabled;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withParent;
import static androidx.test.espresso.matcher.ViewMatchers.withText;
import static com.ps.mymvvm.matchers.EditTextMatchers.hasNoErrorText;
import static org.hamcrest.CoreMatchers.instanceOf;
import static org.hamcrest.CoreMatchers.not;
import static org.hamcrest.core.AllOf.allOf;

/**
 * Created by Prokash Sarkar on 2/6/19.
 */
public class LookupActivityTest {

    @Rule
    public ActivityTestRule<LookupActivity> lookupActivityActivityTestRule = new ActivityTestRule<>(LookupActivity.class);

    @Rule
    public MockitoRule mockitoRule = MockitoJUnit.rule();

    @Before
    public void setUp() throws Exception {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void whenTheActivityIsCreatedThenTheTitleIsSet() {
        onView(allOf(instanceOf(TextView.class), withParent(withId(R.id.toolbar))))
                .check(matches(withText(R.string.activity_lookup)));
    }

    @Test
    public void whenUsernameIsInvalidEditTextHasErrors() {
        ViewInteraction usernameEditText = onView(allOf(withId(R.id.edit_username)));
        usernameEditText.perform(replaceText("p"));
        usernameEditText.check(matches(hasErrorText("Username is too short!")));
    }

    @Test
    public void whenUsernameIsValidEditTextHasNoErrors() {
        ViewInteraction usernameEditText = onView(allOf(withId(R.id.edit_username)));
        usernameEditText.perform(replaceText("prokash-sarkar"));
        usernameEditText.check(matches(hasNoErrorText()));
    }

    @Test
    public void whenUsernameIsInvalidLookupButtonIsDisable() {
        ViewInteraction usernameEditText = onView(allOf(withId(R.id.edit_username)));
        usernameEditText.perform(replaceText("p"));
        onView(withId(R.id.button_lookup)).check(matches(not(isEnabled())));
    }

    @Test
    public void whenUsernameIsInvalidLookupButtonIsEnabled() {
        ViewInteraction usernameEditText = onView(allOf(withId(R.id.edit_username)));
        usernameEditText.perform(replaceText("prokash-sarkar"));
        onView(withId(R.id.button_lookup)).check(matches(isEnabled()));
    }

}