package com.example.task.utils

import android.view.View
import androidx.test.espresso.UiController
import androidx.test.espresso.ViewAction
import androidx.test.espresso.action.ViewActions
import org.hamcrest.Matcher

/**
 * Created by Tarik MANKAOGLU on 6.05.2022.
 */
class ClickOnButtonView(private val id: Int) : ViewAction {
    private var click = ViewActions.click()

    override fun getConstraints(): Matcher<View> {
        return click.constraints
    }

    override fun getDescription(): String {
        return " click on custom button view"
    }

    override fun perform(uiController: UiController, view: View) {
        //btnClickMe -> Custom row item view button
        click.perform(uiController, view.findViewById(id))
    }
}