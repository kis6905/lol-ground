<template>
  <div>
    <v-card
      v-for="memberDetail in memberDetailList"
      :key="memberDetail.name"
      color="#385F73"
      theme="dark"
    >
      <v-card-item>
        <v-card-title>This is a title</v-card-title>

        <v-card-subtitle>This is a subtitle</v-card-subtitle>
      </v-card-item>

      <v-card-text>
        This is content
      </v-card-text>
    </v-card>
  </div>
</template>

<script setup>
import tierConstants from '../constants/tierConstants'
import { onBeforeMount } from 'vue'

const props = defineProps({
  memberDetailList: Array
})

onBeforeMount(() => {
  console.log('onBeforeMount', tierConstants.silver)
})

</script>
