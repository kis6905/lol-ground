<template>
  <v-container>
    <DivisionTitle>
      Members
    </DivisionTitle>
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
        <v-icon class="ml-2" icon="mdi-close" @click="removeSummoner(summonerName)" />
      </v-chip>
    </div>

    <DivisionTitle>
      Informations
    </DivisionTitle>
    <div class="division-content">
      <Information></Information>
    </div>
  </v-container>
</template>

<script setup>
import DivisionTitle from '../components/DivisionTitle.vue';
import Information from '../components/Information.vue';
import { ref, onBeforeMount } from 'vue';

const summonerNames = ref(['Develeaf', '세훈이에요', '퐁대전설', '정치위원장', 'RIAN', 'PororiS2'])

function removeSummoner(summonerName) {
  console.log('removeSummoner!', summonerName)
  summonerNames.value = summonerNames.value.filter(name => name != summonerName)
}

onBeforeMount(async () => {
  console.log('onBeforeMount')

  // https://kr.api.riotgames.com/lol/summoner/v4/summoners/by-name/develeaf
  
  // https://asia.api.riotgames.com/lol/match/v5/matches/by-puuid/{puuid}/ids?type=ranked&start=0&count=20

  // https://asia.api.riotgames.com/lol/match/v5/matches/KR_6190359301
})

</script>

<style lang="scss" scoped>
.division-content {
  margin-bottom: 30px;
}
</style>
