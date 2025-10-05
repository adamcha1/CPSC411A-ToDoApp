# Overview of To-Do App

This app is a simple To Do app that was built by using Jetpack Compose in Kotlin.

The app has 2 sections called Active Items and Completed Items, which gives an efficient way of managing tasks.

The User Interface handles edge cases and supports persistence for configuration changes such as rotating the screen.

# App Features

- *Add Items*

- *Active Items Section*

- *Completed Items Section*

- *Empty States*

- *Persistence*


# Screenshot of App In Use

<img width="704" height="790" alt="Screenshot2" src="https://github.com/user-attachments/assets/5cea193b-9e85-45f7-8ef4-8f1b36244c48" />

<img width="354" height="791" alt="TodoListScreenshot" src="https://github.com/user-attachments/assets/a52dcbb7-749e-42f4-8745-efc5d8c6ecfe" />

<img width="656" height="293" alt="Screenshot3-Persistence" src="https://github.com/user-attachments/assets/da6254ae-626e-413f-8a59-b3078a0636a8" />

<img width="359" height="784" alt="Screenshot4-Toast(NoInput)" src="https://github.com/user-attachments/assets/695b03fa-8382-487d-afbe-cb97c3d1e921" />

# Concepts Used

1. *Data Classes*
   - 'TodoItem' models each task with properties such as task label and completion status.

2. *State Management:*
   - 'mutableStateListOf' and 'mutableStateOf' manage the list of current tasks.
  
3. *State Hoisting:*
   - State and events passed down from the parent composable 'TodoApp'
   - The UI components 'TodoRow' and 'TodoSection', are stateless.

5. *Compose Layouts:*
   - The app uses Row, Column, Spacer, Button, Checkbox, TextField, and Spacer.

6. *Toast & Validation:*
   - The app gives feedback when the user tries to enter a blank task.
