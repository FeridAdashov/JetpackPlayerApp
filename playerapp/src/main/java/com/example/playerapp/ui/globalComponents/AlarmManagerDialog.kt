package com.example.playerapp.ui.globalComponents

import android.app.TimePickerDialog
import android.widget.Toast
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import com.example.playerapp.R
import com.example.playerapp.ui.theme.Blue
import com.example.playerapp.ui.theme.DarkYellow
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import java.util.Calendar

@Composable
fun ShowAlarmSchedulerDialog(
    onDismiss: () -> Unit,
    onSchedule: (time: LocalDateTime, message: String) -> Unit
) {
    val scheduleMessage = remember { mutableStateOf("") }

    val context = LocalContext.current
    val calendar = Calendar.getInstance()

    var selectedTime by remember { mutableStateOf<LocalDateTime?>(null) }

    val year = calendar[Calendar.YEAR]
    val month = calendar[Calendar.MONTH] + 1
    val day = calendar[Calendar.DAY_OF_MONTH]
    val hour = calendar[Calendar.HOUR_OF_DAY]
    val minute = calendar[Calendar.MINUTE]

    val timePicker = TimePickerDialog(
        context,
        { _, selectedHour, selectedMinute ->
            selectedTime = LocalDateTime.of(year, month, day, selectedHour, selectedMinute)
        },
        hour, minute, true
    )

    Dialog(onDismiss) {
        Surface(shape = MaterialTheme.shapes.medium) {
            Column {
                Column(Modifier.padding(24.dp)) {
                    Text(
                        text = selectedTime?.format(DateTimeFormatter.ISO_LOCAL_TIME)
                            ?: stringResource(R.string.click_to_select_time),
                        style = TextStyle(
                            color = DarkYellow,
                            fontStyle = FontStyle.Italic,
                            fontSize = 18.sp
                        ),
                        modifier = Modifier.clickable { timePicker.show() }
                    )
                    Spacer(Modifier.size(16.dp))
                    OutlinedTextField(
                        value = scheduleMessage.value,
                        onValueChange = { scheduleMessage.value = it },
                        placeholder = { Text(text = "Type message") }
                    )
                }
                Spacer(Modifier.size(4.dp))
                Row(
                    Modifier
                        .padding(8.dp)
                        .fillMaxWidth(),
                    Arrangement.spacedBy(8.dp, Alignment.End),
                ) {
                    Button(onClick = {
                        if (selectedTime == null) {
                            Toast.makeText(context, "Select time", Toast.LENGTH_SHORT).show()
                            return@Button
                        } else if (scheduleMessage.value.isEmpty()) {
                            Toast.makeText(context, "Type message", Toast.LENGTH_SHORT).show()
                            return@Button
                        }

                        onSchedule.invoke(
                            selectedTime!!,
                            scheduleMessage.value
                        )
                        onDismiss.invoke()
                    }) {
                        Text(text = stringResource(R.string.schedule))
                    }
                    Button(onClick = onDismiss) {
                        Text(text = stringResource(R.string.cancel))
                    }
                }
            }
        }
    }
}