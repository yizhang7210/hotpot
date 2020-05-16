<template>
  <div id="app">
    <MainNav/>
    <router-view></router-view>
  </div>
</template>

<script>
import MainNav from './components/MainNav'

export default {
  name: 'app',
  components: {
    MainNav,
  },
}
</script>
<style>
#app {
  font-family: 'Avenir', Helvetica, Arial, sans-serif;
  display: flex;
  flex: 1;
  flex-direction: column;
}
</style>
