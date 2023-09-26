import { defineStore } from 'pinia'
import { ref } from "vue";

export const useAppStore = defineStore('appStore', () => {
  const appId = ref();
  const fcmToken = ref();

  const initAppId = async () => {
    appId.value = localStorage.getItem("appId");
    if (!appId.value) {
      appId.value = await saveApp(generateAppId());
    }
    localStorage.setItem("appId", appId.value);
    return appId.value;
  }

  const saveApp = async (appId) => {
    let finalAppId = appId;
    const response = await fetch(`/api/app`, {
      method: "POST",
      headers: {
        "content-type": "application/json"
      },
      body: JSON.stringify({ appId: finalAppId, fcmTokne: "" }),
    });

    console.log(`saveApp response.status: ${response.status}`);

    if (response.status === 409) {
      finalAppId = await saveApp(generateAppId());
    }

    return finalAppId;
  }

  const generateAppId = () => {
    return crypto.randomUUID();
  }

  const saveSubscribers = async (summonerNames) => {
    console.log(`[saveSubscribers] appId: ${appId.value}, summonerNames: ${summonerNames}`)
    const response = await fetch(`/api/subscribers`, {
      method: "POST",
      headers: {
        "content-type": "application/json"
      },
      body: JSON.stringify({ appId: appId.value, summonerNameList: summonerNames }),
    });
    console.log(`saveSubscribers response.status: ${response.status}`);
  }

  return {
    appId,
    initAppId,
    saveSubscribers,
  }
})