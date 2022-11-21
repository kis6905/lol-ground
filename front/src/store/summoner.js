import { defineStore } from 'pinia'
import { ref } from "vue";

export const useSummonerStore = defineStore('summonerStore', () => {
    const summonerNames = ref();

    const registerSummoner = (value) => {
      const summonerName = value.trim();

      if (summonerName && !summonerNames.value.includes(summonerName)) {
        summonerNames.value.push(summonerName);
        localStorage.setItem("summonerNames", JSON.stringify(summonerNames.value));
      }
    }

    const initSummonerNames = () => {
      const loadedSummonerNames = JSON.parse(localStorage.getItem("summonerNames"));
      if (loadedSummonerNames && loadedSummonerNames.length > 0) {
        summonerNames.value = loadedSummonerNames;
        return;
      }

      // default
      summonerNames.value = [
        "Develeaf",
      ];
    }

    const removeSummoner = (summonerName) => {
      summonerNames.value = summonerNames.value.filter((name) => name != summonerName);
      localStorage.setItem("summonerNames", JSON.stringify(summonerNames.value));
    }

    return {
      summonerNames,
      registerSummoner,
      removeSummoner,
      initSummonerNames,
    }
})