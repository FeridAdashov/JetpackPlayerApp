package com.example.playerapp.ui.screen.playlistDetailScreen

import android.content.ActivityNotFoundException
import android.content.Intent
import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.BottomSheetScaffold
import androidx.compose.material3.BottomSheetScaffoldState
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.SheetState
import androidx.compose.material3.SheetValue
import androidx.compose.material3.Text
import androidx.compose.material3.rememberBottomSheetScaffoldState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.rememberUpdatedState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.net.toUri
import com.example.playerapp.R
import com.example.playerapp.alarmManager.AlarmItem
import com.example.playerapp.alarmManager.AndroidAlarmScheduler
import com.example.playerapp.ui.globalComponents.ShadowedIconButton
import com.example.playerapp.ui.globalComponents.ShowAlarmSchedulerDialog
import com.example.playerapp.ui.model.Music
import com.example.playerapp.ui.model.MusicMoreMenu
import com.example.playerapp.ui.theme.GrayLight
import com.example.playerapp.ui.theme.SecondaryLight
import com.example.playerapp.utils.DataHelper
import com.example.playerapp.utils.WorkManagerUtils
import com.example.playerapp.workManager.FileForDownload
import com.example.playerapp.workManager.WorkManagerStatusListener
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PlaylistDetailScreenMorePanel(
    scaffoldState: BottomSheetScaffoldState,
    scope: CoroutineScope,
    music: Music,
    partialExpandHeight: Dp = 200.dp,
) {
    val context = LocalContext.current

    val alarmScheduler = remember {
        AndroidAlarmScheduler(context = context)
    }

    val openDialog = remember { mutableStateOf(false) }
    val lifecycleOwner = rememberUpdatedState(LocalLifecycleOwner.current)

    fun downloadMusic(music: Music) {
        WorkManagerUtils.startDownloadingFileWork(
            FileForDownload(
                id = music.hashCode().toString(),
                name = music.title,
                type = "MP3",
                url = music.url,
                downloadedUri = null
            ),
            context,
            lifecycleOwner.value,
            statusListener = object : WorkManagerStatusListener {
                override fun success(uri: String, mimeType: String) {
                    val intent = Intent(Intent.ACTION_VIEW)
                    intent.setDataAndType(uri.toUri(), mimeType)
                    intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION)
                    try {
                        context.startActivity(intent)
                    } catch (e: ActivityNotFoundException) {
                        Toast.makeText(context, "Can't open file", Toast.LENGTH_SHORT).show()
                    }
                }

                override fun failed(message: String) {
                    Toast.makeText(context, message, Toast.LENGTH_SHORT).show()
                }

                override fun running() {
                    Toast.makeText(context, "Loading", Toast.LENGTH_SHORT).show()
                }

                override fun enqueued(message: String) {
                    Toast.makeText(context, message, Toast.LENGTH_SHORT).show()
                }

                override fun blocked(message: String) {
                    Toast.makeText(context, message, Toast.LENGTH_SHORT).show()
                }

                override fun canceled(message: String) {
                    Toast.makeText(context, message, Toast.LENGTH_SHORT).show()
                }
            },
        )
    }

    BottomSheetScaffold(
        scaffoldState = scaffoldState,
        sheetPeekHeight = partialExpandHeight,
        sheetContainerColor = GrayLight,
        sheetDragHandle = { MusicMorePanelTop(music, scope, scaffoldState) },
        sheetContent = {
            LazyColumn(
                Modifier
                    .fillMaxWidth()
                    .fillMaxHeight(fraction = 0.8f)
                    .padding(horizontal = 16.dp),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.spacedBy(16.dp),
                contentPadding = PaddingValues(vertical = 31.dp)
            ) {
                items(DataHelper.musicMoreMenus) { menu ->
                    MusicMorePanelMenuItem(musicMoreMenu = menu) {
                        when (it) {
                            is MusicMoreMenu.Schedule -> {
                                openDialog.value = true
                            }

                            is MusicMoreMenu.Download -> {
                                downloadMusic(music)
                            }

                            else -> Toast.makeText(
                                context,
                                it.musicMoreMenuItem.title,
                                Toast.LENGTH_SHORT
                            )
                                .show()
                        }
                    }
                }
            }
        },
    ) {}

    if (openDialog.value)
        ShowAlarmSchedulerDialog(onDismiss = { openDialog.value = false }) { time, message ->
            alarmScheduler.schedule(
                AlarmItem(time, message, music.url)
            )
        }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun MusicMorePanelTop(
    music: Music,
    scope: CoroutineScope,
    scaffoldState: BottomSheetScaffoldState
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(start = 16.dp, end = 16.dp, top = 31.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        music.imageDrawable?.let {
            Image(
                painter = painterResource(R.drawable.img_six60),
                contentDescription = "avatar",
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .size(42.dp)
                    .clip(CircleShape)
            )
            Spacer(modifier = Modifier.width(8.dp))
        }
        Column(modifier = Modifier.weight(1f)) {
            Text(
                text = music.title,
                style = TextStyle(fontSize = 14.sp, fontWeight = FontWeight.W600),
                color = Color.White,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis
            )
            Text(
                text = music.desc,
                style = TextStyle(fontSize = 12.sp, fontWeight = FontWeight.W500),
                color = Color(0xFF7F8489),
                textAlign = TextAlign.Center
            )
        }
        ShadowedIconButton(
            drawable = R.drawable.ic_arrow_down,
            onClick = { scope.launch { scaffoldState.bottomSheetState.hide() } })
    }
}

@Composable
private fun MusicMorePanelMenuItem(
    modifier: Modifier = Modifier,
    musicMoreMenu: MusicMoreMenu,
    onClick: (MusicMoreMenu) -> Unit
) {
    val menuItem = musicMoreMenu.musicMoreMenuItem

    Row(modifier = modifier
        .clickable(indication = null,
            interactionSource = remember { MutableInteractionSource() }) {
            onClick.invoke(musicMoreMenu)
        },
        verticalAlignment = Alignment.CenterVertically
    ) {
        ShadowedIconButton(drawable = menuItem.leadingDrawable, size = 44.dp)
        Spacer(modifier = Modifier.width(16.dp))
        Row(
            modifier = Modifier
                .height(44.dp)
                .weight(1f)
                .background(color = SecondaryLight, shape = RoundedCornerShape(size = 26.dp))
                .padding(horizontal = 16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = menuItem.title,
                style = TextStyle(fontSize = 14.sp, fontWeight = FontWeight.W600),
                color = Color.White,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis
            )
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Preview
@Composable
private fun PlaylistDetailScreenMorePanelPreview() {
    val bottomSheetScaffoldState =
        rememberBottomSheetScaffoldState(
            bottomSheetState = SheetState(
                skipPartiallyExpanded = false,
                initialValue = SheetValue.Expanded
            )
        )
    val bottomSheetCoroutineScope = rememberCoroutineScope()

    PlaylistDetailScreenMorePanel(
        bottomSheetScaffoldState,
        bottomSheetCoroutineScope,
        Music("Don’t forget your roots - 2021", "Six 60", imageDrawable = R.drawable.img_six60)
    )
}