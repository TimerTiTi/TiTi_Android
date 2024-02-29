package com.titi.app.feature.log.ui

import android.Manifest
import android.content.Context
import android.content.pm.PackageManager
import android.graphics.Picture
import android.net.Uri
import android.os.Build
import android.widget.Toast
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedCard
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.content.ContextCompat
import com.kizitonwose.calendar.compose.HorizontalCalendar
import com.kizitonwose.calendar.compose.rememberCalendarState
import com.kizitonwose.calendar.core.CalendarDay
import com.kizitonwose.calendar.core.DayPosition
import com.kizitonwose.calendar.core.OutDateStyle
import com.kizitonwose.calendar.core.daysOfWeek
import com.titi.app.core.designsystem.R
import com.titi.app.core.designsystem.component.TdsColorRow
import com.titi.app.core.designsystem.component.TdsDialog
import com.titi.app.core.designsystem.component.TdsIconButton
import com.titi.app.core.designsystem.component.TdsStandardDailyGraph
import com.titi.app.core.designsystem.component.TdsTaskProgressDailyGraph
import com.titi.app.core.designsystem.component.TdsText
import com.titi.app.core.designsystem.component.TdsTimeLineDailyGraph
import com.titi.app.core.designsystem.component.TdsTimeTableDailyGraph
import com.titi.app.core.designsystem.model.TdsDialogInfo
import com.titi.app.core.designsystem.model.TdsTaskData
import com.titi.app.core.designsystem.model.TdsTimeTableData
import com.titi.app.core.designsystem.theme.TdsColor
import com.titi.app.core.designsystem.theme.TdsTextStyle
import com.titi.app.core.designsystem.theme.TiTiTheme
import com.titi.app.core.designsystem.util.saveBitmapFromComposable
import java.time.DayOfWeek
import java.time.LocalDate
import java.time.YearMonth
import java.time.format.TextStyle
import java.util.Locale
import kotlinx.coroutines.Deferred
import kotlinx.coroutines.async
import kotlinx.coroutines.awaitAll
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.launch

@Composable
fun DailyScreen(
    currentDate: LocalDate,
    totalTime: String,
    maxTime: String,
    hasDailies: List<LocalDate>,
    taskData: List<TdsTaskData>,
    tdsColors: List<TdsColor>,
    timeLines: List<Long>,
    timeTableData: List<TdsTimeTableData>,
    checkedButtonStates: List<Boolean>,
    onClickDate: (LocalDate) -> Unit,
    onClickGraphColor: (Int) -> Unit,
    onCalendarLocalDateChanged: (LocalDate) -> Unit,
    onCheckedChange: (page: Int, checked: Boolean) -> Unit,
) {
    val context = LocalContext.current
    val scrollState = rememberScrollState()
    val coroutineScope = rememberCoroutineScope()
    val pictureList = remember {
        List(4) { Picture() }
    }
    var showPermissionDialog by remember {
        mutableStateOf(false)
    }

    val requestWritePermissionLauncher =
        rememberLauncherForActivityResult(
            ActivityResultContracts.RequestPermission(),
        ) { isGranted: Boolean ->
            if (isGranted) {
                coroutineScope.launch {
                    val message = saveDailyGraph(
                        context = context,
                        pictureList = pictureList,
                        checkedButtonStates = checkedButtonStates,
                    )
                    Toast.makeText(context, message, Toast.LENGTH_SHORT).show()
                }
            } else {
                showPermissionDialog = true
            }
        }

    fun saveBitmapFromComposableWithPermission() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
            coroutineScope.launch {
                val message = saveDailyGraph(
                    context = context,
                    pictureList = pictureList,
                    checkedButtonStates = checkedButtonStates,
                )
                Toast.makeText(context, message, Toast.LENGTH_SHORT).show()
            }
        } else {
            if (
                ContextCompat.checkSelfPermission(
                    context,
                    Manifest.permission.WRITE_EXTERNAL_STORAGE,
                ) == PackageManager.PERMISSION_GRANTED
            ) {
                coroutineScope.launch {
                    val message = saveDailyGraph(
                        context = context,
                        pictureList = pictureList,
                        checkedButtonStates = checkedButtonStates,
                    )
                    Toast.makeText(context, message, Toast.LENGTH_SHORT).show()
                }
            } else {
                requestWritePermissionLauncher.launch(Manifest.permission.WRITE_EXTERNAL_STORAGE)
            }
        }
    }

    if (showPermissionDialog) {
        TdsDialog(
            tdsDialogInfo = TdsDialogInfo.Confirm(
                title = "사진을 저장하기 위해서 권한이 필요로 합니다.",
                message = "허용하시겠습니까?",
                positiveText = stringResource(id = R.string.Ok),
                negativeText = stringResource(id = R.string.Cancel),
                onPositive = {
                    requestWritePermissionLauncher.launch(
                        Manifest.permission.WRITE_EXTERNAL_STORAGE,
                    )
                },
            ),
            onShowDialog = { showPermissionDialog = it },
        ) {
            Spacer(modifier = Modifier.height(15.dp))
        }
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(scrollState),
    ) {
        CalendarContent(
            modifier = Modifier.fillMaxWidth(),
            themeColor = tdsColors.first(),
            currentDate = currentDate,
            hasDailies = hasDailies,
            onClickDate = onClickDate,
            onCalendarLocalDateChanged = onCalendarLocalDateChanged,
        )

        Spacer(modifier = Modifier.height(15.dp))

        TdsColorRow(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 42.dp),
            onClick = onClickGraphColor,
        )

        Spacer(modifier = Modifier.height(15.dp))

        DailyButtonRow(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 50.dp),
            onSaveClick = {
                saveBitmapFromComposableWithPermission()
            },
            onShareClick = {},
        )

        Spacer(modifier = Modifier.height(15.dp))

        GraphContent(
            modifier = Modifier.fillMaxWidth(),
            todayDate = currentDate.toString().replace('-', '.'),
            todayDayOfTheWeek = currentDate.dayOfWeek.value - 1,
            totalTime = totalTime,
            maxTime = maxTime,
            taskData = taskData,
            tdsColors = tdsColors,
            timeLines = timeLines,
            timeTableData = timeTableData,
            checkedButtonStates = checkedButtonStates,
            pictureList = pictureList,
            onCheckedChange = onCheckedChange,
        )
    }
}

@Composable
fun CalendarContent(
    modifier: Modifier = Modifier,
    themeColor: TdsColor,
    currentDate: LocalDate,
    hasDailies: List<LocalDate>,
    onClickDate: (LocalDate) -> Unit,
    onCalendarLocalDateChanged: (LocalDate) -> Unit,
) {
    val scope = rememberCoroutineScope()

    val currentMonth = remember { YearMonth.now() }
    val startMonth = remember { currentMonth.minusMonths(100) }
    val endMonth = remember { currentMonth.plusMonths(100) }
    val daysOfWeek = remember { daysOfWeek() }

    val state = rememberCalendarState(
        startMonth = startMonth,
        endMonth = endMonth,
        firstVisibleMonth = currentMonth,
        firstDayOfWeek = daysOfWeek.first(),
        outDateStyle = OutDateStyle.EndOfGrid,
    )

    LaunchedEffect(state.firstVisibleMonth.yearMonth.atDay(1)) {
        onCalendarLocalDateChanged(state.firstVisibleMonth.yearMonth.atDay(1))
    }
    Row(
        modifier = modifier,
        verticalAlignment = Alignment.CenterVertically,
    ) {
        val visibleYear = state.firstVisibleMonth.yearMonth.year
        val visibleMonth = state.firstVisibleMonth.yearMonth.monthValue

        TdsIconButton(
            onClick = {
                val previousYearMonth = state.firstVisibleMonth.yearMonth.minusMonths(1)

                scope.launch {
                    state.animateScrollToMonth(previousYearMonth)
                }
            },
        ) {
            Icon(
                imageVector = ImageVector.vectorResource(R.drawable.arrow_left_icon),
                contentDescription = "arrowLeft",
                tint = TdsColor.TEXT.getColor(),
            )
        }

        BoxWithConstraints(
            modifier = modifier
                .weight(1f)
                .padding(vertical = 10.dp),
            contentAlignment = Alignment.Center,
        ) {
            val size = if (maxWidth >= 365.dp) 345.dp else maxWidth - 20.dp

            OutlinedCard(
                modifier = Modifier
                    .width(size)
                    .wrapContentHeight(),
                shape = RoundedCornerShape(25.dp),
                colors = CardDefaults.cardColors(
                    containerColor = TdsColor.BACKGROUND.getColor(),
                ),
                elevation = CardDefaults.outlinedCardElevation(defaultElevation = 5.dp),
                border = BorderStroke(3.dp, TdsColor.SHADOW.getColor()),
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(3.dp),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.Center,
                ) {
                    Spacer(modifier = Modifier.height(10.dp))

                    TdsText(
                        text = "$visibleYear ${
                            visibleMonth.toString().padStart(2, '0')
                        }",
                        textStyle = TdsTextStyle.EXTRA_BOLD_TEXT_STYLE,
                        fontSize = 24.sp,
                        color = themeColor,
                    )

                    Spacer(modifier = Modifier.height(10.dp))

                    DaysOfWeekTitle(
                        daysOfWeek,
                        themeColor,
                    )

                    HorizontalCalendar(
                        state = state,
                        dayContent = { day ->
                            Day(
                                day = day,
                                isSelected = currentDate == day.date,
                                hasDaily = hasDailies.contains(day.date),
                                themeColor = themeColor,
                            ) { selectedDay ->
                                if (currentDate != selectedDay.date) {
                                    onClickDate(selectedDay.date)
                                }
                            }
                        },
                    )
                }
            }
        }

        TdsIconButton(
            onClick = {
                val nextYearMonth = state.firstVisibleMonth.yearMonth.plusMonths(1)

                scope.launch {
                    state.animateScrollToMonth(nextYearMonth)
                }
            },
        ) {
            Icon(
                imageVector = ImageVector.vectorResource(R.drawable.arrow_right_icon),
                contentDescription = "arrowRight",
                tint = TdsColor.TEXT.getColor(),
            )
        }
    }
}

@Composable
fun Day(
    day: CalendarDay,
    isSelected: Boolean,
    hasDaily: Boolean,
    themeColor: TdsColor,
    onClickDate: (CalendarDay) -> Unit,
) {
    Box(
        modifier = Modifier
            .padding(horizontal = 5.dp)
            .aspectRatio(1f)
            .clickable(
                enabled = day.position == DayPosition.MonthDate,
                onClick = { onClickDate(day) },
            ),
    ) {
        Box(
            modifier = Modifier
                .fillMaxSize(0.75f)
                .clip(CircleShape)
                .background(
                    color = if (isSelected) {
                        themeColor.getColor()
                    } else {
                        Color.Transparent
                    },
                )
                .align(Alignment.Center),
            contentAlignment = Alignment.Center,
        ) {
            Text(
                text = day.date.dayOfMonth.toString(),
                color = if (day.position == DayPosition.MonthDate) {
                    if (isSelected) {
                        TdsColor.BACKGROUND.getColor()
                    } else {
                        TdsColor.TEXT.getColor()
                    }
                } else {
                    Color.Gray
                },
                style = TdsTextStyle.SEMI_BOLD_TEXT_STYLE.getTextStyle(fontSize = 18.sp),
            )
        }

        if (hasDaily) {
            Box(
                modifier = Modifier
                    .size(3.dp)
                    .clip(CircleShape)
                    .background(
                        color = if (isSelected) {
                            themeColor.getColor()
                        } else {
                            Color.Red
                        },
                    )
                    .align(Alignment.BottomCenter),
            )
        }
    }
}

@Composable
fun DaysOfWeekTitle(daysOfWeek: List<DayOfWeek>, themeColor: TdsColor) {
    Row(modifier = Modifier.fillMaxWidth()) {
        for (dayOfWeek in daysOfWeek) {
            Text(
                modifier = Modifier.weight(1f),
                textAlign = TextAlign.Center,
                text = dayOfWeek.getDisplayName(TextStyle.SHORT, Locale.getDefault()),
                color = themeColor.getColor(),
            )
        }
    }
}

@Composable
private fun DailyButtonRow(
    modifier: Modifier = Modifier,
    onSaveClick: () -> Unit,
    onShareClick: () -> Unit,
) {
    BoxWithConstraints(
        modifier = modifier,
        contentAlignment = Alignment.Center,
    ) {
        val width = if (maxWidth >= 365.dp) 345.dp else maxWidth - 20.dp

        Row(
            modifier = Modifier.width(width),
            horizontalArrangement = Arrangement.SpaceBetween,
        ) {
            Row(
                modifier = Modifier.clickable { onSaveClick() },
                verticalAlignment = Alignment.CenterVertically,
            ) {
                Icon(
                    imageVector = ImageVector.vectorResource(R.drawable.save_photo_icon),
                    contentDescription = "save",
                    tint = TdsColor.TEXT.getColor(),
                )

                Spacer(modifier = Modifier.width(4.dp))

                TdsText(
                    text = "Save",
                    textStyle = TdsTextStyle.SEMI_BOLD_TEXT_STYLE,
                    fontSize = 16.sp,
                    color = TdsColor.TEXT,
                )
            }

            Row(
                modifier = Modifier.clickable { onShareClick() },
                verticalAlignment = Alignment.CenterVertically,
            ) {
                Icon(
                    imageVector = ImageVector.vectorResource(R.drawable.share_icon),
                    contentDescription = "save",
                    tint = TdsColor.TEXT.getColor(),
                )

                Spacer(modifier = Modifier.width(4.dp))

                TdsText(
                    text = "Share",
                    textStyle = TdsTextStyle.SEMI_BOLD_TEXT_STYLE,
                    fontSize = 16.sp,
                    color = TdsColor.TEXT,
                )
            }
        }
    }
}

@OptIn(ExperimentalFoundationApi::class)
@Composable
private fun GraphContent(
    modifier: Modifier = Modifier,
    todayDate: String,
    todayDayOfTheWeek: Int,
    totalTime: String,
    maxTime: String,
    taskData: List<TdsTaskData>,
    tdsColors: List<TdsColor>,
    timeLines: List<Long>,
    timeTableData: List<TdsTimeTableData>,
    checkedButtonStates: List<Boolean>,
    pictureList: List<Picture>,
    onCheckedChange: (page: Int, checked: Boolean) -> Unit,
) {
    val pagerState = rememberPagerState(
        pageCount = {
            4
        },
    )
    val scope = rememberCoroutineScope()

    Row(
        modifier = modifier,
        verticalAlignment = Alignment.CenterVertically,
    ) {
        Column(modifier = Modifier.size(32.dp)) {
            AnimatedVisibility(visible = pagerState.currentPage != 0) {
                TdsIconButton(
                    onClick = {
                        scope.launch {
                            if (pagerState.currentPage - 1 >= 0) {
                                pagerState.animateScrollToPage(pagerState.currentPage - 1)
                            }
                        }
                    },
                ) {
                    Icon(
                        imageVector = ImageVector.vectorResource(R.drawable.arrow_left_icon),
                        contentDescription = "arrowLeft",
                        tint = TdsColor.TEXT.getColor(),
                    )
                }
            }
        }

        HorizontalPager(
            modifier = Modifier.weight(1f),
            userScrollEnabled = true,
            state = pagerState,
            beyondBoundsPageCount = 2,
        ) { page ->
            when (page % 4) {
                0 -> TdsStandardDailyGraph(
                    modifier = Modifier.fillMaxWidth(),
                    todayDate = todayDate,
                    todayDayOfTheWeek = todayDayOfTheWeek,
                    tdsColors = tdsColors,
                    timeLines = timeLines,
                    taskData = taskData,
                    totalTime = totalTime,
                    maxTime = maxTime,
                    picture = pictureList[0],
                    checked = checkedButtonStates[0],
                    onCheckedChange = {
                        onCheckedChange(0, it)
                    },
                )

                1 -> TdsTimeTableDailyGraph(
                    modifier = Modifier.fillMaxWidth(),
                    todayDate = todayDate,
                    todayDayOfTheWeek = todayDayOfTheWeek,
                    tdsColors = tdsColors,
                    taskData = taskData,
                    timeTableData = timeTableData,
                    totalTime = totalTime,
                    maxTime = maxTime,
                    picture = pictureList[1],
                    checked = checkedButtonStates[1],
                    onCheckedChange = {
                        onCheckedChange(1, it)
                    },
                )

                2 -> TdsTimeLineDailyGraph(
                    modifier = Modifier.fillMaxWidth(),
                    todayDate = todayDate,
                    todayDayOfTheWeek = todayDayOfTheWeek,
                    tdsColors = tdsColors,
                    timeLines = timeLines,
                    totalTime = totalTime,
                    maxTime = maxTime,
                    picture = pictureList[2],
                    checked = checkedButtonStates[2],
                    onCheckedChange = {
                        onCheckedChange(2, it)
                    },
                )

                3 -> TdsTaskProgressDailyGraph(
                    modifier = Modifier.fillMaxWidth(),
                    todayDate = todayDate,
                    taskData = taskData,
                    tdsColors = tdsColors,
                    picture = pictureList[3],
                    checked = checkedButtonStates[3],
                    onCheckedChange = {
                        onCheckedChange(3, it)
                    },
                )
            }
        }

        Column(modifier = Modifier.size(32.dp)) {
            AnimatedVisibility(visible = pagerState.currentPage != 3) {
                TdsIconButton(
                    onClick = {
                        scope.launch {
                            if (pagerState.currentPage + 1 < 4) {
                                pagerState.animateScrollToPage(pagerState.currentPage + 1)
                            }
                        }
                    },
                ) {
                    Icon(
                        imageVector = ImageVector.vectorResource(R.drawable.arrow_right_icon),
                        contentDescription = "arrowRight",
                        tint = TdsColor.TEXT.getColor(),
                    )
                }
            }
        }
    }
}

private suspend fun saveDailyGraph(
    context: Context,
    pictureList: List<Picture>,
    checkedButtonStates: List<Boolean>,
): String = coroutineScope {
    val jobs = mutableListOf<Deferred<Result<Uri>>>()

    checkedButtonStates.forEachIndexed { index, isChecked ->
        if (isChecked) {
            val job = async {
                saveBitmapFromComposable(pictureList[index], context)
            }
            jobs.add(job)
        }
    }

    val isCompleted = jobs.awaitAll().all { it.isSuccess }
    if (isCompleted) "모든 사진이 갤러리에 저장되었습니다." else "갤러리에 저장이 실패하였습니다."
}

@Composable
@Preview
private fun DailyScreenPreview() {
    val taskData = listOf(
        TdsTaskData(
            key = "수업",
            value = "02:00:00",
            progress = 0.2f,
        ),
        TdsTaskData(
            key = "인공지능",
            value = "03:00:00",
            progress = 0.3f,
        ),
        TdsTaskData(
            key = "알고리즘",
            value = "02:00:00",
            progress = 0.2f,
        ),
        TdsTaskData(
            key = "개발",
            value = "03:00:00",
            progress = 0.3f,
        ),
    )

    val tdsColors = listOf(
        TdsColor.D1,
        TdsColor.D2,
        TdsColor.D3,
        TdsColor.D4,
        TdsColor.D5,
        TdsColor.D6,
        TdsColor.D7,
        TdsColor.D8,
        TdsColor.D9,
        TdsColor.D11,
        TdsColor.D12,
    )

    val timeLines = listOf(
        3600L,
        1200,
        300,
        400,
        100,
        600,
        800,
        1200,
        300,
        400,
        100,
        600,
        800,
        1200,
        300,
        400,
        100,
        600,
        800,
        1200,
        300,
        400,
        100,
        600,
    )

    val timeTableData = listOf(
        TdsTimeTableData(
            hour = 3,
            start = 1800,
            end = 2400,
        ),
        TdsTimeTableData(
            hour = 5,
            start = 1234,
            end = 2555,
        ),
        TdsTimeTableData(
            hour = 12,
            start = 600,
            end = 3444,
        ),
        TdsTimeTableData(
            hour = 23,
            start = 2121,
            end = 3333,
        ),
    )

    TiTiTheme {
        DailyScreen(
            taskData = taskData,
            tdsColors = tdsColors,
            totalTime = "08:00:00",
            maxTime = "03:00:00",
            hasDailies = emptyList(),
            timeLines = timeLines,
            timeTableData = timeTableData,
            currentDate = LocalDate.now(),
            checkedButtonStates = List(4) { false },
            onClickDate = {},
            onClickGraphColor = {},
            onCalendarLocalDateChanged = {},
            onCheckedChange = { _, _ -> },
        )
    }
}
