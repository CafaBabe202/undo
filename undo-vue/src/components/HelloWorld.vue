<template xmlns:v-on="http://www.w3.org/1999/xhtml">
  <div class="hello">
    <h1>这里是正文</h1>
  </div>
</template>

<script>
export default {
  name: 'HelloWorld',
  data() {
    return {
      msg: 'Welcome to Your Vue.js App'
    }
  },
  methods: {
    a: function () {
      get();
    }
  }
}
</script>
