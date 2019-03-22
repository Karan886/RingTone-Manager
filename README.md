Imagine you have an important event that requires mobile devices to be on silent or vibrate.
There are 2 scenarios that happen:
  1. The mobile device is not on silent/vibrate etc...
  2. The mobile device is not swiched back from silent/vibrate and important calls/texts/etc... are missed 
  
RingToneManager prevents the above problems by allowing user to schedule events ahead of time.

This application solves this simple problem, RingToneManager allows you to schedule such events ahead of time, so that on the occasion that you forget to turn your device to silent or back from silent, you are totally covered.

Testing and Compiling:

This application is built on Android Studio, please find the attached download link: https://developer.android.com/studio/


Notes/Thoughts:

- Application is built on top of an SQL database
- Events are scheduled using AlarmManager. However, Android Studio is buggy with scheduling the first item/event, all other
events work
- An array of improvements can be made: i.e. invites friends to events and synchronizes the event to their
devices (as a ringtonemanager event) is a desireable feature
- Tools class is implemented to assist with date formatting that is needed across several classes 
(rather than writing seperate methods for each class). However, some classes have not completely migrated to use the 
tools class, helper methods are used to format date and time.


Checkout screenshots in the demo folder!!



