import { initializeApp } from 'firebase/app';
import { getToken, getMessaging, onMessage } from "firebase/messaging";
import { useAppStore } from '../store/app';

const firebaseConfig = {
  apiKey: "AIzaSyCzaVlgWB8A53NjVPEHirkMmNeqSyCJp04",
  authDomain: "lolground-cc413.firebaseapp.com",
  projectId: "lolground-cc413",
  storageBucket: "lolground-cc413.appspot.com",
  messagingSenderId: "572612643433",
  appId: "1:572612643433:web:195f6225532c467cbd70f8"
}

const initFcm = () => {
  const app = initializeApp(firebaseConfig)

  const messaging = getMessaging();
  getToken(messaging, { vapidKey: "BOE0KVbxOaeSIM0J9fANs6JGzBFVf6b22ifpNyeqb0BO2BzO__HhBk6AB_JlKOU1uaqu5At03TrEETUMrRgmPb8" })
    .then(async (currentToken) => {

      if (currentToken) {
        const appStore = useAppStore();

        console.log("getToken() currentToken: ", currentToken);
        console.log("getToken() appId       : ", appStore.appId);
        
        let appId = appStore.appId;
        if (!appStore.appId) {
          appId = appStore.initAppId();
        }

        appStore.updateFcmToken(appId, currentToken);

      } else {
        console.warn('No registration token available.');
      }

    }).catch((err) => {
      console.log('An error occurred while retrieving token. ', err);
    });

  onMessage(messaging, (payload) => { // foreground handler
    console.log('Message received. ', payload);
    // FIXME: 필요한가?
  });
}

export {
  initFcm
}
