package com.titi.app.feature.setting.ui

import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.safeDrawingPadding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.titi.app.core.designsystem.R
import com.titi.app.core.designsystem.component.TdsIconButton
import com.titi.app.core.designsystem.component.TdsText
import com.titi.app.core.designsystem.theme.TdsColor
import com.titi.app.core.designsystem.theme.TdsTextStyle
import com.titi.app.feature.setting.model.Version

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun UpdatesListScreen(onNavigateUp: () -> Unit) {
    val firebaseDatabase = FirebaseDatabase.getInstance()
    val databaseReference = firebaseDatabase.getReference("versions")

    val updates = remember { mutableStateListOf<Version>() }

    LaunchedEffect(Unit) {
        databaseReference.addValueEventListener(
            object : ValueEventListener {
                override fun onDataChange(snapshot: DataSnapshot) {
                    for (data in snapshot.children) {
                        data.getValue(Version::class.java)?.let {
                            updates.add(it)
                        }
                    }
                }

                override fun onCancelled(error: DatabaseError) {
                    Log.e("UpdateListScreen", error.message)
                }
            },
        )
    }

    val containerColor = if (isSystemInDarkTheme()) {
        0xFF000000
    } else {
        0xFFF2F2F7
    }

    Scaffold(
        containerColor = Color(containerColor),
        topBar = {
            CenterAlignedTopAppBar(
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = TdsColor.GROUPED_BACKGROUND.getColor(),
                ),
                navigationIcon = {
                    TdsIconButton(onClick = onNavigateUp) {
                        Icon(
                            modifier = Modifier.size(32.dp),
                            painter = painterResource(id = R.drawable.arrow_left_icon),
                            contentDescription = "back",
                            tint = TdsColor.TEXT.getColor(),
                        )
                    }
                },
                title = {
                    TdsText(
                        text = "업데이트 내역",
                        textStyle = TdsTextStyle.EXTRA_BOLD_TEXT_STYLE,
                        fontSize = 24.sp,
                        color = TdsColor.TEXT,
                    )
                },
            )
        },
    ) {
        UpdateListScreen(
            modifier = Modifier
                .fillMaxSize()
                .safeDrawingPadding()
                .padding(it),
            updates = updates.reversed(),
        )
    }
}

@Composable
private fun UpdateListScreen(modifier: Modifier = Modifier, updates: List<Version>) {
    LazyColumn(modifier) {
        items(updates) {
            VersionContent(version = it)
        }
    }
}

@Composable
private fun VersionContent(version: Version) {
    Column {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 8.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
        ) {
            TdsText(
                text = "ver ${version.currentVersion}",
                textStyle = TdsTextStyle.SEMI_BOLD_TEXT_STYLE,
                color = TdsColor.TEXT,
                fontSize = 16.sp,
            )

            TdsText(
                text = version.date,
                textStyle = TdsTextStyle.NORMAL_TEXT_STYLE,
                color = TdsColor.LIGHT_GRAY,
                fontSize = 16.sp,
            )
        }

        Spacer(modifier = Modifier.height(8.dp))

        TdsText(
            modifier = Modifier
                .fillMaxWidth()
                .background(color = TdsColor.SECONDARY_BACKGROUND.getColor())
                .padding(
                    horizontal = 8.dp,
                    vertical = 12.dp,
                ),
            text = version.description,
            textStyle = TdsTextStyle.NORMAL_TEXT_STYLE,
            color = TdsColor.LIGHT_GRAY,
            fontSize = 16.sp,
        )

        Spacer(modifier = Modifier.height(8.dp))
    }
}

@Preview
@Composable
private fun UpdatesListScreenPreview() {
    UpdateListScreen(
        modifier = Modifier.fillMaxSize(),
        updates = listOf(
            Version(
                currentVersion = "repudiandae",
                date = "utinam",
                description = "magnis",
            ),
            Version(
                currentVersion = "repudiandae",
                date = "utinam",
                description = "magnis",
            ),
            Version(
                currentVersion = "repudiandae",
                date = "utinam",
                description = "magnis",
            ),
        ),
    )
}
