<template>
  <v-container>
    <DivisionTitle>관심 멤버 추가</DivisionTitle>
    <div class="division-content">
      <Registration :summonerNames="summonerNames" />
    </div>
    <DivisionTitle>멤버</DivisionTitle>
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

    <DivisionTitle>정보</DivisionTitle>
    <div class="division-content">
      <Information
        v-for="summonerName in summonerNames"
        :key="summonerName"
        :summonerName="summonerName"
        @showSnackbar="showSnackbar"
      />
    </div>
    <!-- snackbar -->
    <v-snackbar v-model="isShowSnackbar" :timeout="snackbarTimeout" color="red">
      {{ snackbarText }}
      <template v-slot:actions>
        <v-icon class="ml-2" icon="mdi-close" @click="isShowSnackbar = false" />
      </template>
    </v-snackbar>
  </v-container>
</template>

<script setup>
import DivisionTitle from "../components/DivisionTitle.vue";
import Information from "../components/Information.vue";
import Registration from "../components/Registration.vue";
import { ref, onBeforeMount } from "vue";

import { useSummonerStore } from "../store/summoner";
import { useAppStore } from "../store/app";
import { storeToRefs } from "pinia";

const summonerStore = useSummonerStore();
const appStore = useAppStore();
const { summonerNames, summonerDetailList } = storeToRefs(summonerStore);
useAppStore

const isShowSnackbar = ref(false);
const snackbarText = ref("");
const snackbarTimeout = ref(2000);

const showSnackbar = (summonerName) => {
  isShowSnackbar.value = true;
  snackbarText.value = `"${summonerName}" 은 존재하지 않는 사용자입니다.`;
};

onBeforeMount(async () => {
  const summonerNames = summonerStore.initSummonerNames();
  const appId = await appStore.initAppId();
  appStore.saveSubscribers(summonerNames);
});

const removeSummoner = (summonerName) => {
  summonerStore.removeSummoner(summonerName);
  appStore.saveSubscribers(summonerNames.value);
}
</script>

<style lang="scss" scoped>
.division-content {
  margin-bottom: 30px;
}
</style>
