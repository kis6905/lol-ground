importScripts('https://www.gstatic.com/firebasejs/10.4.0/firebase-app-compat.js');
importScripts('https://www.gstatic.com/firebasejs/10.4.0/firebase-messaging-compat.js');

firebase.initializeApp({
  apiKey: "AIzaSyCzaVlgWB8A53NjVPEHirkMmNeqSyCJp04",
  authDomain: "lolground-cc413.firebaseapp.com",
  projectId: "lolground-cc413",
  storageBucket: "lolground-cc413.appspot.com",
  messagingSenderId: "572612643433",
  appId: "1:572612643433:web:195f6225532c467cbd70f8"
});

const messaging = firebase.messaging();
messaging.onBackgroundMessage(function(payload) { // background handler
  console.log('onBackgroundMessage()', payload);
  const notificationTitle = payload.notification.title;
  const notificationOptions = {
    body: payload.notification.body,
  };

  self.registration.showNotification(notificationTitle, notificationOptions);
});
