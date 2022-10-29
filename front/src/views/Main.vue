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
      순위
    </DivisionTitle>
    <div class="division-content">

    </div>

    <DivisionTitle>
      이번주 판 수
    </DivisionTitle>
    <div class="division-content">
      
    </div>
  </v-container>
</template>

<script setup>
import DivisionTitle from '../components/DivisionTitle.vue';
import { ref } from 'vue';

const summonerNames = ref(["Develeaf", "세훈이에요", "퐁대전설", "정치위원장", "RIAN", "PororiS2"])

function removeSummoner(summonerName) {
  console.log('removeSummoner!', summonerName)
  summonerNames.value = summonerNames.value.filter(name => name != summonerName)
}

</script>

<style lang="scss" scoped>
.division-content {
  margin-bottom: 30px;
}
</style>
