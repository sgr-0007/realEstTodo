package com.team2.todo.utils

import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.provider.Settings
import android.widget.Toast
import androidx.activity.compose.ManagedActivityResultLauncher
import androidx.core.content.ContextCompat

object PermissionUtil {

    fun checkAndRequestLocationPermissions(
        context: Context,
        permissions: Array<String>,
        launcher: ManagedActivityResultLauncher<Array<String>, Map<String, Boolean>>,
        permissionCallback: () -> Unit
    ) {
        if (
            permissions.all {
                ContextCompat.checkSelfPermission(
                    context,
                    it
                ) == PackageManager.PERMISSION_GRANTED
            }
        ) {
            permissionCallback()
        } else {
            // Request permissions
            launcher.launch(permissions)
        }
    }

    fun showToastAndLaunchSetting(context: Context, toastMessage: String) {
        Toast.makeText(context, toastMessage, Toast.LENGTH_SHORT).show()
        val intent = Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS)
        val uri: Uri = Uri.fromParts("package", context.packageName, null)
        intent.data = uri
        context.startActivity(intent)
    }
}