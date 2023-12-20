package com.team2.todo.screens.add_todo.ui_components

import android.Manifest
import android.graphics.Bitmap
import android.graphics.ImageDecoder
import android.net.Uri
import android.os.Build
import android.provider.MediaStore
import android.util.Log
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.team2.todo.R
import com.team2.todo.ui.theme.BlueColor
import com.team2.todo.ui.theme.PrimaryColor
import com.team2.todo.utils.PermissionUtil
import com.team2.todo.utils.PermissionUtil.checkAndRequestLocationPermissions

/**
 * Created by Atharva K on 11/14/23.
 */

@Composable
fun PickImagesForTodo(bitmapList: (List<Bitmap>) -> Unit) {
    val context = LocalContext.current
    var imageUris by remember {
        mutableStateOf<List<Uri>>(emptyList())
    }
    var bitmaps by remember {
        //Bitmap is similar to a pixel of an image
        mutableStateOf<List<Bitmap>>(emptyList())
    }
    val launcher = rememberLauncherForActivityResult(
        contract =
        ActivityResultContracts.GetMultipleContents()
    ) { uris: List<Uri>? ->
        uris?.let {
            imageUris = it
            bitmaps =
                it.map { uri ->
                    uri.path?.let { it1 -> Log.d("Image", it1) }

                    if (Build.VERSION.SDK_INT < 28) {
                        MediaStore.Images.Media.getBitmap(context.contentResolver, uri)
                    } else {
                        val source = ImageDecoder.createSource(context.contentResolver, uri)
                        ImageDecoder.decodeBitmap(source)
                    }
                }
        }
    }
    val launchImagePickerPermission = rememberLauncherForActivityResult(
        ActivityResultContracts.RequestMultiplePermissions()
    ) { permissionsMap ->
        val areGranted = permissionsMap.values.reduce { acc, next -> acc || next }
        if (areGranted) {
            launcher.launch("image/*")
        } else {
            PermissionUtil.showToastAndLaunchSetting(
                context,
                "Please Enable Read External Storage permission"
            )
        }
    }


    Box(
        modifier = Modifier
            .border(
                2.dp,
                BlueColor,
                shape = RoundedCornerShape(8.dp)
            )
            .clickable {
                checkAndRequestLocationPermissions(
                    context,
                    arrayOf(
                        Manifest.permission.READ_MEDIA_IMAGES,
                        Manifest.permission.READ_EXTERNAL_STORAGE
                    ),
                    launchImagePickerPermission
                ) {
//                    launcher.launch("image/*")
                }
            }
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center,
            modifier = Modifier.padding(5.dp)
        ) {
            Image(
                painter = painterResource(id = R.drawable.ic_gallery),
                contentDescription = "loading",
                modifier = Modifier.height(150.dp)
            )
            Spacer(modifier = Modifier.width(26.dp))
            Text(text = "Gallery")
        }

    }

    if (bitmaps.isNotEmpty()) {
        bitmapList(bitmaps)
    }
}

@Composable
fun UploadImagePlaceHolder(onCLick: () -> Unit) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 10.dp)
            .clickable { onCLick() },
    ) {
        Text(
            text = "Upload Image",
            modifier = Modifier.padding(bottom = 10.dp),
            fontWeight = FontWeight.SemiBold,
            color = PrimaryColor,
            fontSize = 18.sp
        )
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(150.dp)
                .background(color = Color.White, shape = RoundedCornerShape(8.dp))
                .border(1.dp, color = PrimaryColor, shape = RoundedCornerShape(8.dp)),
            contentAlignment = Alignment.Center,
        ) {

            Image(
                painter = painterResource(id = R.drawable.image_upload_placeholder),
                contentDescription = "Upload Image",
                modifier = Modifier
                    .height(150.dp)
                    .fillMaxWidth(.6f)
            )

        }
    }
}
