<template>
  <div class="d-flex">
    <v-text-field
      v-model="summonerName"
      class="mr-3"
      variant="solo"
      density="compact"
      clearable
      full-width
      hide-details
      @keyup.enter="registSummoner"
    ></v-text-field>
    <v-btn
      rounded="lg"
      icon="mdi-plus"
      color="gray"
      size="small"
      min-width="40"
      @click="registSummoner"
    ></v-btn>
  </div>
</template>

<script setup>
import { ref, onBeforeMount } from "vue";
import { useSummonerStore } from "../store/summoner";
import { storeToRefs } from "pinia";

const summonerStore = useSummonerStore();
const { summonerNames } = storeToRefs(summonerStore);

const summonerName = ref("");
const registSummoner = () => {
  summonerStore.registerSummoner(summonerName.value);
  summonerName.value = "";
};

onBeforeMount(() => {});
</script>