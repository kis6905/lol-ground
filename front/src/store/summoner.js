import { defineStore } from 'pinia'
import { ref, onBeforeMount, reactive, computed } from "vue";

export const useSummonerStore = defineStore('useSummonerStore', () => {
    const summonerNames = ref();
    const summonerDetailList = ref([
        {
            summonerName: "1LEAF",
            soloTier: "BRONZE",
            soloRank: "IV",
            soloLeaguePoints: 0,
            soloWins: 26,
            soloLosses: 20,
            freeTier: "GOLD",
            freeRank: "II",
            freeLeaguePoints: 43,
            freeWins: 178,
            freeLosses: 178,
            soloWinRate: "56.52",
            freeWinRate: "50.00",
            recentMatches: [
                { win: true, playedAgo: "10d" },
                { win: true, playedAgo: "10d" },
                { win: true, playedAgo: "10d" },
                { win: true, playedAgo: "10d" },
                { win: true, playedAgo: "10d" },
            ],
        },
        {
            summonerName: "신월동불주먹",
            soloTier: "PLATINUM",
            soloRank: "III",
            soloLeaguePoints: 1,
            soloWins: 35,
            soloLosses: 46,
            freeTier: "BRONZE",
            freeRank: "II",
            freeLeaguePoints: 33,
            freeWins: 300,
            freeLosses: 400,
            soloWinRate: "30.00",
            freeWinRate: "40.00",
            recentMatches: [
                { win: true, playedAgo: "10d" },
                { win: false, playedAgo: "10d" },
                { win: true, playedAgo: "10d" },
                { win: true, playedAgo: "10d" },
                { win: true, playedAgo: "10d" },
            ],
        },
    ]);

    const registraionSummoner = (value) => {
        const summonerName = value.trim();

        if (summonerName && !summonerNames.value.includes(summonerName)) {
            summonerNames.value.push(summonerName);
            localStorage.setItem("summonerNames", JSON.stringify(summonerNames.value));
        }
    }

    const initSummonerNames = () => {
        summonerNames.value = [
            "1LEAF",
            "신월동불주먹"
            // "Develeaf",
            // "세훈이에요",
            // "퐁대전설",
            // "정치위원장",
            // "RIAN",
            // "PororiS2",
        ];
    }

    const setSummonerNames = (data) => {
        summonerNames.value = data;
    }

    const setSummonerDetailList = (data) => {
        summonerDetailList.value = data;
    }

    const removeSummoner = (summonerName) => {
        summonerNames.value = summonerNames.value.filter(
            (name) => name != summonerName
        );
        removeSummonerDetail(summonerName);
        localStorage.setItem("summonerNames", JSON.stringify(summonerNames.value));
    }

    const removeSummonerDetail = (summonerName) => {
        summonerDetailList.value = summonerDetailList.value.filter(
            (summonerDetail) => summonerDetail.summonerName != summonerName
        );
    }

    const getLocalStorageSummonerNames = () => {
        return JSON.parse(localStorage.getItem("summonerNames"));
    };

    return {
        summonerNames
        , summonerDetailList
        , registraionSummoner
        , removeSummoner
        , initSummonerNames
        , setSummonerNames
        , setSummonerDetailList
        , getLocalStorageSummonerNames
    }
})