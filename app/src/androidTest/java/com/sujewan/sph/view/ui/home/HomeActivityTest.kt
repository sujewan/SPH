package com.sujewan.sph.view.ui.home

import android.content.res.Resources
import android.content.res.Resources.NotFoundException
import android.view.View
import androidx.recyclerview.widget.RecyclerView
import androidx.test.espresso.Espresso
import androidx.test.espresso.action.ViewActions
import androidx.test.espresso.action.ViewActions.pressBack
import androidx.test.espresso.assertion.ViewAssertions
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.contrib.RecyclerViewActions
import androidx.test.espresso.matcher.RootMatchers
import androidx.test.espresso.matcher.RootMatchers.isDialog
import androidx.test.espresso.matcher.ViewMatchers
import androidx.test.espresso.matcher.ViewMatchers.isDisplayed
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.filters.LargeTest
import androidx.test.rule.ActivityTestRule
import androidx.test.runner.AndroidJUnit4
import com.sujewan.sph.R
import org.hamcrest.Description
import org.hamcrest.Matcher
import org.hamcrest.Matchers
import org.hamcrest.TypeSafeMatcher
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith


@RunWith(AndroidJUnit4::class)
@LargeTest
class HomeActivityTest {
    @get:Rule
    val activityRule = ActivityTestRule(HomeActivity::class.java)

    var recyclerView: RecyclerView? = null
    private var recyclerViewMatcher: RecyclerViewMatcher? = null

    companion object {
        private const val ITEM_FIRST_YEAR = "2008"
        private const val ITEM_LAST_YEAR = "2018"
        private val ITEMS_DECREASE_USAGES_YEARS = intArrayOf(3, 5, 7)
    }

    @Before
    fun initActivity() {
        recyclerView = activityRule.activity.findViewById(R.id.rv_records)
        recyclerViewMatcher = RecyclerViewMatcher(R.id.rv_records)
    }

    @Test
    fun testRecyclerViewScroll() {
        // Get total item of RecyclerView
        val recyclerView =
            activityRule.activity.findViewById<RecyclerView>(R.id.rv_records)
        val itemCount = recyclerView.adapter!!.itemCount

        // Scroll to end of page with position
        Espresso.onView(withId(R.id.rv_records))
            .inRoot(
                RootMatchers.withDecorView(
                    Matchers.`is`(activityRule.activity.window.decorView)
                )
            )
            .perform(RecyclerViewActions.scrollToPosition<RecyclerView.ViewHolder>(itemCount - 1))
    }

    @Test
    fun verifyFirstEntry() {
        try {
            Thread.sleep(6000)
        } catch (e: InterruptedException) {
            e.printStackTrace()
        }

        // Verify the first element
        Espresso
            .onView(recyclerViewMatcher!!.atPosition(0))
            .check(
                ViewAssertions.matches(
                    ViewMatchers.hasDescendant(
                        ViewMatchers.withText(
                            ITEM_FIRST_YEAR
                        )
                    )
                )
            )
    }

    @Test
    fun verifyLastEntry() {
        try {
            Thread.sleep(6000)
        } catch (e: InterruptedException) {
            e.printStackTrace()
        }

        // Scroll to bottom of recyclerview
        Espresso
            .onView(withId(R.id.rv_records))
            .perform(
                RecyclerViewActions.scrollToPosition<RecyclerView.ViewHolder>(
                    recyclerView!!.adapter!!.itemCount - 1
                )
            )

        // Verify the last element
        Espresso
            .onView(recyclerViewMatcher!!.atPosition(recyclerView!!.adapter!!.itemCount - 1))
            .check(
                ViewAssertions.matches(
                    ViewMatchers.hasDescendant(
                        ViewMatchers.withText(
                            ITEM_LAST_YEAR
                        )
                    )
                )
            )
    }

    @Test
    fun testBreakdownPopup() {
        try {
            Thread.sleep(6000)
        } catch (e: InterruptedException) {
            e.printStackTrace()
        }

        // >>>> Start - Check the data usages decrease item 01
        // Move to the position
        Espresso
            .onView(withId(R.id.rv_records))
            .perform(
                RecyclerViewActions.scrollToPosition<RecyclerView.ViewHolder>(
                    ITEMS_DECREASE_USAGES_YEARS[0]
                )
            )

        // Click on the item cell
        Espresso
            .onView(recyclerViewMatcher!!.atPositionOnView(
                ITEMS_DECREASE_USAGES_YEARS[0], R.id.row_base_ui))
            .perform(ViewActions.click())


        Espresso
            .onView(withId(R.id.lbl_info_title))
            .inRoot(isDialog())
            .check(matches(isDisplayed()))
            .perform(pressBack())
        // >>>> End - Check the data usages decrease item 01

        // >>>> Start - Check the data usages decrease item 02
        // Move to the position
        Espresso
            .onView(withId(R.id.rv_records))
            .perform(
                RecyclerViewActions.scrollToPosition<RecyclerView.ViewHolder>(
                    ITEMS_DECREASE_USAGES_YEARS[1]
                )
            )

        // Click on the item cell
        Espresso
            .onView(recyclerViewMatcher!!.atPositionOnView(
                ITEMS_DECREASE_USAGES_YEARS[1], R.id.row_base_ui))
            .perform(ViewActions.click())


        Espresso
            .onView(withId(R.id.lbl_info_title))
            .inRoot(isDialog())
            .check(matches(isDisplayed()))
            .perform(pressBack())
        // >>>> End - Check the data usages decrease item 02

        // >>>> Start - Check the data usages decrease item 03
        // Move to the position
        Espresso
            .onView(withId(R.id.rv_records))
            .perform(
                RecyclerViewActions.scrollToPosition<RecyclerView.ViewHolder>(
                    ITEMS_DECREASE_USAGES_YEARS[2]
                )
            )

        // Click on the item cell
        Espresso
            .onView(recyclerViewMatcher!!.atPositionOnView(
                ITEMS_DECREASE_USAGES_YEARS[2], R.id.row_base_ui))
            .perform(ViewActions.click())


        Espresso
            .onView(withId(R.id.lbl_info_title))
            .inRoot(isDialog())
            .check(matches(isDisplayed()))
            .perform(pressBack())
        // >>>> End - Check the data usages decrease item 03
    }

    inner class RecyclerViewMatcher(private val recyclerViewId: Int) {
        fun atPosition(position: Int): Matcher<View> {
            return atPositionOnView(position, -1)
        }

        fun atPositionOnView(
            position: Int,
            targetViewId: Int
        ): Matcher<View> {
            return object : TypeSafeMatcher<View>() {
                var resources: Resources? = null
                var childView: View? = null
                override fun describeTo(description: Description) {
                    var idDescription = Integer.toString(recyclerViewId)
                    if (resources != null) {
                        idDescription = try {
                            resources!!.getResourceName(recyclerViewId)
                        } catch (var4: NotFoundException) {
                            String.format(
                                "%s (resource name not found)",
                                Integer.valueOf(recyclerViewId)
                            )
                        }
                    }
                    description.appendText("with id: $idDescription")
                }

                public override fun matchesSafely(view: View): Boolean {
                    resources = view.resources
                    if (childView == null) {
                        val recyclerView: RecyclerView =
                            view.rootView.findViewById(recyclerViewId)
                        childView = if (recyclerView.id == recyclerViewId) {
                            recyclerView.findViewHolderForAdapterPosition(position)!!.itemView
                        } else {
                            return false
                        }
                    }
                    return if (targetViewId == -1) {
                        view === childView
                    } else {
                        val targetView =
                            childView!!.findViewById<View>(targetViewId)
                        view === targetView
                    }
                }
            }
        }
    }
}
