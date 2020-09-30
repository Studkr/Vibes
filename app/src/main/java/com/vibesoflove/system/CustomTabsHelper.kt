package com.vibesoflove.system

import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.text.TextUtils
import androidx.browser.customtabs.CustomTabsIntent
import androidx.browser.customtabs.CustomTabsService
import androidx.core.content.ContextCompat
import androidx.core.net.toUri
import androidx.fragment.app.Fragment
import com.vibesoflove.R


class CustomTabsHelper {
    companion object {
        var sPackageNameToUse: String? = null
        val STABLE_PACKAGE = "com.android.chrome"
        val BETA_PACKAGE = "com.chrome.beta"
        val DEV_PACKAGE = "com.chrome.dev"
        val LOCAL_PACKAGE = "com.google.android.apps.chrome"

        fun getPackageNameToUse(context: Context, uri: String): String? {
            sPackageNameToUse?.let {
                return it
            }

            val pm = context.packageManager

            val activityIntent = Intent(Intent.ACTION_VIEW, Uri.parse(uri))
            val defaultViewHandlerInfo = pm.resolveActivity(activityIntent, 0)
            var defaultViewHandlerPackageName: String? = null

            defaultViewHandlerInfo?.let {
                defaultViewHandlerPackageName = it.activityInfo.packageName
            }

            val resolvedActivityList = pm.queryIntentActivities(activityIntent, 0)
            val packagesSupportingCustomTabs = ArrayList<String>()
            for (info in resolvedActivityList) {
                val serviceIntent = Intent()
                serviceIntent.action = CustomTabsService.ACTION_CUSTOM_TABS_CONNECTION
                serviceIntent.setPackage(info.activityInfo.packageName)

                pm.resolveService(serviceIntent, 0)?.let {
                    packagesSupportingCustomTabs.add(info.activityInfo.packageName)
                }
            }

            when {
                packagesSupportingCustomTabs.isEmpty() -> sPackageNameToUse = null
                packagesSupportingCustomTabs.size == 1 -> sPackageNameToUse =
                    packagesSupportingCustomTabs[0]
                !TextUtils.isEmpty(defaultViewHandlerPackageName)
                        && !hasSpecializedHandlerIntents(
                    context,
                    activityIntent
                )
                        && packagesSupportingCustomTabs.contains(defaultViewHandlerPackageName) ->
                    sPackageNameToUse = defaultViewHandlerPackageName
                packagesSupportingCustomTabs.contains(STABLE_PACKAGE) -> sPackageNameToUse =
                    STABLE_PACKAGE
                packagesSupportingCustomTabs.contains(BETA_PACKAGE) -> sPackageNameToUse =
                    BETA_PACKAGE
                packagesSupportingCustomTabs.contains(DEV_PACKAGE) -> sPackageNameToUse =
                    DEV_PACKAGE
                packagesSupportingCustomTabs.contains(LOCAL_PACKAGE) -> sPackageNameToUse =
                    LOCAL_PACKAGE
            }
            return sPackageNameToUse
        }

        private fun hasSpecializedHandlerIntents(context: Context, intent: Intent): Boolean {
            try {
                val pm = context.packageManager
                val handlers = pm.queryIntentActivities(
                    intent,
                    PackageManager.GET_RESOLVED_FILTER
                )
                if (handlers.isEmpty()) {
                    return false
                }
                for (resolveInfo in handlers) {
                    val filter = resolveInfo.filter ?: continue
                    if (filter.countDataAuthorities() == 0 || filter.countDataPaths() == 0) continue
                    if (resolveInfo.activityInfo == null) continue
                    return true
                }
            } catch (e: RuntimeException) {}
            return false
        }
    }
}

fun Fragment.browse(url: String) {
    requireContext().browse(url)
}

fun Context.browse(url: String) {
    val sendIntent = Intent(Intent.ACTION_VIEW, url.toUri())
    startActivity(Intent.createChooser(sendIntent, ""))
}

fun Fragment.showChromeTab(url: String) {
    context?.showChromeTab(url)
}

fun Context.showChromeTab(browseUri: String) {
    val url = browseUri.trim()
    if (CustomTabsHelper.getPackageNameToUse(this, url) == null) {
        browse(url)
    } else {
        val builder = CustomTabsIntent.Builder()
        builder.setShowTitle(true)
        builder.setToolbarColor(ContextCompat.getColor(this, R.color.colorPrimary))
        val intent = builder.build()
        intent.launchUrl(this, Uri.parse(url))
    }


}
