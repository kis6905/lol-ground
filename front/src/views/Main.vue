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
          @click="summonerStore.removeSummoner(summonerName)"
        />
      </v-chip>
    </div>

    <DivisionTitle>Informations</DivisionTitle>
    <div class="division-content">
      <Information
        v-for="summonerName in summonerNames"
        :key="summonerName"
        :summonerName="summonerName"
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

const summonerStore = useSummonerStore();
const { summonerNames, summonerDetailList } = storeToRefs(summonerStore);

onBeforeMount(() => {
  summonerStore.initSummonerNames();
});
</script>

<style lang="scss" scoped>
.division-content {
  margin-bottom: 30px;
}
</style>
