package com.samuel.lab_week_09

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.snapshots.SnapshotStateList
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.samuel.lab_week_09.ui.theme.LAB_WEEK_09Theme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            LAB_WEEK_09Theme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    Home()
                }
            }
        }
    }
}

data class Student(var name: String)

@Composable
fun Home() {
    //Here, we create a mutable state list of Student
    //We use remember to make the list remember its value
    //This is so that the list won’t be recreated when the composable
    //recomposes
    //We use mutableStateListOf to make the list mutable
    //This is so that we can add or remove items from the List
    //If you’re still confused, this is basically the same concept as
    //using
    //useState in React
    val listData = remember {
        mutableStateListOf(
            Student("Tanu"),
            Student("Tina"),
            Student("Tono")
        )
    }
    //Here, we create a mutable state of Student
    //This is so that we can get the value of the input field
    val inputField = remember { mutableStateOf(Student("")) }

    //We call the HomeContent composable
    //Here, we pass:
    //listData to show the list of items inside HomeContent
    //inputField to show the input field value inside HomeContent
    //A lambda function to update the value of the inputField
    //A lambda function to add the inputField to the listData
    HomeContent(
        listData = listData,
        inputField = inputField.value,
        onInputValueChange = { input -> inputField.value = inputField.value.copy(name = input) },
        onButtonClick = {
            if (inputField.value.name.isNotBlank()) {
                listData.add(inputField.value)
                inputField.value = Student("")
            }
        }
    )
}

@Composable
fun HomeContent(
    listData: SnapshotStateList<Student>,
    inputField: Student,
    onInputValueChange: (String) -> Unit,
    onButtonClick: () -> Unit
) {
    LazyColumn {
        item {
            Column(
                modifier = Modifier.padding(16.dp).fillMaxSize(),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(text = stringResource(id = R.string.enter_item))
                TextField(
                    value = inputField.name,
                    onValueChange = onInputValueChange,
                    keyboardOptions = KeyboardOptions(
                        keyboardType = KeyboardType.Text
                    )
                )
                Button(onClick = onButtonClick) {
                    Text(text = stringResource(id = R.string.button_click))
                }
            }
        }
        items(listData) { item ->
            Column(
                modifier = Modifier.padding(vertical = 4.dp).fillMaxSize(),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(text = item.name)
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun PreviewHome() {
    Home()
}