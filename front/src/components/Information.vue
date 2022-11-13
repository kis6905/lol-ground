<template>
  <div>
    <v-card
      :color="tierConstants[props.summonerDetail.soloTier].color"
      theme="dark"
      class="mt-2"
    >
      <v-card-item>
        <v-card-title class="text-subtitle-2">
          {{ props.summonerDetail.summonerName }}
        </v-card-title>
      </v-card-item>

      <v-card-text>
        <v-row class="flex-nowrap">
          <v-col cols="4" class="align-start align-self-center detail-box-left">
            <div class="d-flex align-center flex-column">
              <v-avatar size="60">
                <v-img
                  :src="tierConstants[props.summonerDetail.soloTier].image"
                ></v-img>
              </v-avatar>
              <p class="pt-2">
                {{
                  `${props.summonerDetail.soloTier} ${props.summonerDetail.soloRank}`
                }}
              </p>
            </div>
          </v-col>
          <v-col cols="8">
            <p>{{ `${props.summonerDetail.soloLeaguePoints} LP` }}</p>
            <p>
              {{
                `${props.summonerDetail.soloWins}승 ${props.summonerDetail.soloLosses}패`
              }}
            </p>
            <p>{{ `승률 ${props.summonerDetail.soloWinRate}%` }}</p>
            <p>이번주 게임 수 10</p>
            <p>최근 솔랭 전적</p>
            <div class="d-flex">
              <div
                v-for="latestRecord in props.summonerDetail.latestRecord"
                :key="latestRecord"
                class="lates-record-box d-flex align-center flex-column mr-2"
              >
                <v-chip
                  :color="latestRecord.info == '승' ? 'primary' : 'red'"
                  class="pa-2 my-1"
                  size="default"
                >
                  {{ latestRecord.info }}
                </v-chip>
                <p class="text-caption text-medium-emphasis">
                  {{ latestRecord.time }}
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
import { onBeforeMount } from "vue";

const props = defineProps({
  summonerDetail: Object,
});

onBeforeMount(() => {
  console.log("onBeforeMount", tierConstants.silver);
  console.log("summonerDetail", props.summonerDetail);
  console.log("color", tierConstants[props.summonerDetail.soloTier].color);
});
</script>

<style lang="scss" scoped>
.detail-box-left {
  max-width: 111px;
  min-width: 111px;
}

.lates-record-box {
  width: 45px;
}
</style>>
