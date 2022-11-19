<template>
  <v-container>
    <DivisionTitle>Registration</DivisionTitle>
    <div class="division-content">
      <Registration :summonerNames="summonerNames" />
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
          @click="store.removeSummoner(summonerName)"
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

import { useSummonerStore } from "../store/summoner";
import { storeToRefs } from "pinia";

const store = useSummonerStore();

const { summonerNames, summonerDetailList } = storeToRefs(store);

onBeforeMount(async () => {
  const localStorageSummonerNames = store.getLocalStorageSummonerNames();
  if (localStorageSummonerNames && localStorageSummonerNames.length > 0) {
    store.setSummonerNames(localStorageSummonerNames);
    return;
  }
  store.initSummonerNames();
  localStorage.setItem("summonerNames", JSON.stringify(summonerNames.value));

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
