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
    ></v-text-field>
    <v-btn
      rounded="lg"
      icon="mdi-plus"
      color="primary"
      size="small"
      min-width="40"
      @click="registSummoner"
    ></v-btn>
  </div>
</template>

<script setup>
import { ref, defineEmits, onBeforeMount } from "vue";
let props = defineProps({
  summonerNames: Array,
});
let summonerName = ref("");
const emit = defineEmits(["registraionSummoner"]);

const registSummoner = () => {
  const value = summonerName.value.trim();
  if (value && !props.summonerNames.includes(value)) {
    props.summonerNames.push(value);
    localStorage.setItem("summonerNames", JSON.stringify(props.summonerNames));
  }
  summonerName.value = "";
};

onBeforeMount(() => {
  // console.log("registraion onBeforeMount");
  // emit("registraionSummoner", summonerName);
});
</script>