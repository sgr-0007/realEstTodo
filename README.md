# COM6510 Assignment

Software Development for Mobile Device assignment

---

## Geolocation aware Todo app

<ins>Feature of the App</ins>

* Purpose/Usage driven Todo List
* Todos : Add Update Delete - capturing datetime and latest known location (GeoLocation)
* Capture/Upload image while add/edit the Todo List
* Subtasks in TODO
* Asking if the user needs a reminder (scope for discussion?)
* If Reminder is set show, Notification for showing the time remaining  (  Need to Dicuss : How to
  show if there are multiple
  reminders ) [How to Create Notification](https://developer.android.com/develop/ui/views/notifications/build-notification)
* Deeplinking for opening app from the notification that can take user to the exact TODO
  List [More Info On Deeplink]( https://developer.android.com/training/app-links/deep-linking )
* Done TODO List Screen, which can be used to view finished TODO and recover them later, and
  permanently delete the TODO List.
* Notification :

- When the reminder time is reached.
- When the user arrives at the location of a Todo (Geofenced Todo).

* Showing typical tasks (for your users/focus) that a User can copy and fill in further (scope for
  discussion??)


<ins>Important Note to include in Assignment </ins>

* Design : Use of Material Design https://m3.material.io/
* Architecture : MVVM with state
* Data Persistance for storing data
* GPS/GeoLocation
* Camera Sensor for capturing Image
* Minimum Android Version : 11



[![Folder Structure](./documents/images/folder_structure.png, "Hello World")]

Tutorial and information link

* [Navigation](https://developer.android.com/jetpack/compose/navigation)
* [Data Persistance](https://developer.android.com/courses/android-basics-kotlin/unit-5)

Cloning the project

* ``` git clone https://github.com/sgr-0007/realEstTodo.git ```
* Enter user name and password (set your password in github, if you have not)
