<template>
  <b-navbar class="main-nav" type="dark" variant="info">
    <b-navbar-brand to="/home"> Hotpot Demo </b-navbar-brand>
    <b-navbar-brand class="nav-button" to="/home"> Services </b-navbar-brand>
    <b-navbar-brand class="nav-button" to="/objectives"> Objectives </b-navbar-brand>
    <b-navbar-brand class="nav-button" to="/metrics"> Metrics </b-navbar-brand>
  </b-navbar>
</template>

<script>
export default {
  name: 'MainNav'
}
</script>

<style scoped lang="scss">
.main-nav {
  display: flex;
  background-color: $primary-color !important;
  margin-bottom: $small-margin;
}
.nav-button {
  font-size: $grip-title-font-size;
}
</style>
