<template>
  <div>
    <v-autocomplete
      v-model="value"
      v-model:search-input="search"
      clearable
      label="Member Name"
      :items="summonerNames"
      :loading="isLoading"
      variant="solo"
      hide-details
      hide-selected
      @update:search="emit('registraionSummoner', value)"
    >
      <template v-slot:no-data>
        <v-list-item>
          <v-list-item-title> Search for member name </v-list-item-title>
        </v-list-item>
      </template></v-autocomplete
    >
  </div>
</template>

<script setup>
import { ref, defineEmits, onBeforeMount } from "vue";

const isLoading = ref(false),
  summonerNames = ref([
    "Develeaf",
    "세훈이에요",
    "퐁대전설",
    "정치위원장",
    "RIAN",
    "PororiS2",
  ]),
  search = ref(null),
  value = ref("");
const emit = defineEmits(["registraionSummoner"]);

onBeforeMount(() => {
  console.log("registraion onBeforeMount");
  emit("registraionSummoner", value);
});
</script>