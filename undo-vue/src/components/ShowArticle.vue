<template>
  <h1>ShowArticle{{ id }}</h1>
</template>

<script>
export default {
  name: "ShowArticle",
  data() {
    return {
      id: this.$route.params.id
    }
  }
}
</script>

<style scoped>

</style>
