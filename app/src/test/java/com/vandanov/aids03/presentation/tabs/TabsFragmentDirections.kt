package com.vandanov.aids03.presentation.tabs

import android.os.Bundle
import androidx.navigation.NavDirections
import com.vandanov.aids03.R
import kotlin.Int
import kotlin.String

public class TabsFragmentDirections private constructor() {
  private data class ActionTabsFragmentToRegisterItemFragment(
    public val mode: String,
    public val registerID: Int
  ) : NavDirections {
    public override val actionId: Int = R.id.action_tabsFragment_to_registerItemFragment

    public override val arguments: Bundle
      get() {
        val result = Bundle()
        result.putString("mode", this.mode)
        result.putInt("registerID", this.registerID)
        return result
      }
  }

  public companion object {
    public fun actionTabsFragmentToRegisterItemFragment(mode: String, registerID: Int):
        NavDirections = ActionTabsFragmentToRegisterItemFragment(mode, registerID)
  }
}
