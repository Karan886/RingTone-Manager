Imagine you have an important lecture, exam, or even an event that requires your mobile device to be on silent or vibrate.
There are 2 scenarios that happen:
  1. you forgot to turn your device on silent/vibrate etc...
  2. you forgot to switch your device back from silent/vibrate etc.... and as a result missed important calls/texts/etc...
  
This application solves this simple problem, RingToneManager allows you to schedule such events ahead of time, so that on
the occasion that you forget to turn your device to silent or back from silent, you are totally covered.

Testing and Compiling:

This app is built on Android Studio, you may download it here: https://developer.android.com/studio/


Notes/Thoughts:

- Application is built on top of an SQL database
- Events are scheduled using AlarmManager, however, android studio is buggy with scheduling the first item/event, all other
events work
- There are still lots of areas to explore/add/improve: the ability to invite friends to events and have it update on their
devices (as a ringtonemanager event) is a desired feature I want to add
- Tools class was implemented to assist with some of the date formatting that is needed across several other classes 
(rather than writing seperate methods for each class), however, some classes have not completely migrated to using the 
tools class (they have some helper methods to format date and time)


Checkout screenshots in the demo folder!!



