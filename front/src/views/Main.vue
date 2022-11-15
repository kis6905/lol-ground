<template>
  <v-container>
    <DivisionTitle>Registration</DivisionTitle>
    <div class="division-content">
      <Registration @registraionSummoner="registraionSummoner" />
    </div>
    <DivisionTitle>Members</DivisionTitle>
    <div class="division-content">
      <v-chip
        v-for="summonerName in summonerNames"
        :key="summonerName"
        class="ma-2"
        color="green"
        text-color="white"
        variant="outlined"
        size="large"
      >
        {{ summonerName }}
        <v-icon
          class="ml-2"
          icon="mdi-close"
          @click="removeSummoner(summonerName)"
        />
      </v-chip>
    </div>

    <DivisionTitle>Informations</DivisionTitle>
    <div class="division-content">
      <Information
        v-for="summonerDetail in summonerDetailList"
        :key="summonerDetail.summonerName"
        :summonerDetail="summonerDetail"
      />
    </div>
  </v-container>
</template>

<script setup>
import DivisionTitle from "../components/DivisionTitle.vue";
import Information from "../components/Information.vue";
import Registration from "../components/Registration.vue";
import { ref, onBeforeMount } from "vue";

const summonerNames = ref([
  "Develeaf",
  "세훈이에요",
  "퐁대전설",
  "정치위원장",
  "RIAN",
  "PororiS2",
]);

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
  {
    summonerName: "신월동불주먹",
    soloTier: "SILVER",
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
      { win: true, playedAgo: "10d" },
      { win: false, playedAgo: "10d" },
      { win: true, playedAgo: "10d" },
      { win: true, playedAgo: "10d" },
    ],
  },
  {
    summonerName: "신월동불주먹",
    soloTier: "GOLD",
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
      { win: false, playedAgo: "10d" },
      { win: true, playedAgo: "10d" },
    ],
  },
]);

function removeSummoner(summonerName) {
  console.log("removeSummoner!", summonerName);
  summonerNames.value = summonerNames.value.filter(
    (name) => name != summonerName
  );
}

function registraionSummoner(summonerName) {
  console.log("registraionSummoner!", summonerName);
}
onBeforeMount(async () => {
  console.log("onBeforeMount");

  // https://kr.api.riotgames.com/lol/summoner/v4/summoners/by-name/develeaf

  // https://asia.api.riotgames.com/lol/match/v5/matches/by-puuid/{puuid}/ids?type=ranked&start=0&count=20

  // https://asia.api.riotgames.com/lol/match/v5/matches/KR_6190359301
});
</script>

<style lang="scss" scoped>
.division-content {
  margin-bottom: 30px;
}
</style>
