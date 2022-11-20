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
                {{
                  `${summoner.soloTier} ${summoner.soloRank}`
                }}
              </p>
            </div>
          </v-col>
          <v-col cols="7">
            <p>{{ `${summoner.soloLeaguePoints} LP` }}</p>
            <p>
              {{
                `${summoner.soloWins}승 ${summoner.soloLosses}패`
              }}
            </p>
            <p>{{ `승률 ${summoner.soloWinRate}%` }}</p>
            <p>이번주 게임 수 10</p>
            <p>최근 솔랭 전적</p>
            <div class="d-flex">
              <div
                v-for="(recentMatch, index) in summoner.recentMatches"
                :key="index"
                class="lates-record-box"
              >
                <v-chip :color="recentMatch.win ? 'primary' : 'red'">
                  {{ recentMatch.win ? "승" : "패" }}
                </v-chip>
                <p class="text-caption text-medium-emphasis">
                  {{ recentMatch.playedAgo }}
                </p>
              </div>
            </div>
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

const summonerStore = useSummonerStore();

const props = defineProps({
  summonerName: String,
});

const summoner = ref({});
const matchInfo = ref({});

onBeforeMount(async () => {
  try {
    const summonerResponse = await fetch(`/api/summoner/${props.summonerName}`);
    const data = await summonerResponse.json();
    summoner.value = data;

    const matchInfoResponse = await fetch(`/api/match/info/${data.puuid}`);

  } catch (e) {
    console.error(e);
    summonerStore.removeSummoner(props.summonerName);
    // TODO: toast 로 알려주기
  }
});
</script>

<style lang="scss" scoped>
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