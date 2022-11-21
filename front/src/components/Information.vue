<template>
  <div v-if="summoner.summonerName">
    <v-card
      :color="tierConstants[summoner.soloTier].color"
      theme="dark"
      class="mt-2"
    >
      <v-toolbar density="compact" color="#0000004a">
        <v-toolbar-title class="text-subtitle-1">
          {{ summoner.summonerName }}
        </v-toolbar-title>
      </v-toolbar>

      <v-card-text>
        <v-row class="flex-nowrap">
          <v-col cols="5" class="detail-box-left">
            <div class="tier-box">
              <v-avatar size="90">
                <v-img
                  class="img-box"
                  :src="tierConstants[summoner.soloTier].image"
                  max-width="80%"
                ></v-img>
              </v-avatar>
              <p class="pt-5">
                {{ `${summoner.soloTier} ${summoner.soloRank}` }}
              </p>
            </div>
          </v-col>
          <v-col cols="7">
            <p>{{ `${summoner.soloLeaguePoints} LP` }}</p>
            <p>
              {{ `${summoner.soloWins}승 ${summoner.soloLosses}패` }}
            </p>
            <p>{{ `승률 ${summoner.soloWinRate}%` }}</p>
            <p class="d-flex">
              <span style="min-width: 93px">이번주 게임 수</span>
              <span v-if="!isLoading" class="pl-2">
                {{ matchInfo.playedGameCountOfThisWeek }}
              </span>
              <Skeletor v-else width="100%" height="14px" pill class="ml-2" />
            </p>
            <p>최근 솔랭 전적</p>
            <div class="d-flex" v-if="!isLoading">
              <div
                v-for="(recentMatch, index) in matchInfo.recentMatches"
                :key="index"
                class="lates-record-box"
              >
                <v-chip :color="recentMatch.win ? 'blue' : 'red'">
                  {{ recentMatch.win ? "승" : "패" }}
                </v-chip>
                <p class="text-caption text-medium-emphasis">
                  {{ recentMatch.playedAgo }}
                </p>
              </div>
            </div>
            <Skeletor v-else height="60" pill />
          </v-col>
        </v-row>
      </v-card-text>
    </v-card>
  </div>
</template>

<script setup>
import tierConstants from "../constants/tierConstants";
import { ref, onBeforeMount } from "vue";
import { useSummonerStore } from "../store/summoner";

const props = defineProps({
  summonerName: String,
});

const summoner = ref({});
const matchInfo = ref({});

const emit = defineEmits(["showSnackbar"]);
const isLoading = ref(false);

onBeforeMount(async () => {
  try {
    const summonerResponse = await fetch(`/api/summoner/${props.summonerName}`);
    const summonerData = await summonerResponse.json();
    summoner.value = summonerData;

    isLoading.value = true;
    const matchInfoResponse = await fetch(
      `/api/match/info/${summonerData.puuid}`
    );
    const matchInfoData = await matchInfoResponse.json();
    matchInfo.value = matchInfoData;

    isLoading.value = false;
  } catch (e) {
    console.error(e);
    summonerStore.removeSummoner(props.summonerName);
    emit("showSnackbar", props.summonerName);
  }
});
</script>

<style lang="scss" scoped>
.vue-skeletor {
  border-radius: 5px !important;
}

.detail-box-left {
  min-width: 111px;
  max-width: 315px;
  align-self: center;
  align-items: flex-start;

  .tier-box {
    align-items: center;
    flex-direction: column;
    display: flex;

    .v-avatar {
      box-shadow: inset 25px -26px 34px #00000082;
    }
  }
}

.lates-record-box {
  width: 32px;
  margin-right: 10px;
  display: flex;
  align-items: center;
  flex-direction: column;
  .v-chip {
    width: 32px;
    height: 32px;
    line-height: 31px;
    padding-left: 9px;
    margin-top: 4px;
    margin-bottom: 4px;
  }
}
</style>