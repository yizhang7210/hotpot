<template>
  <b-navbar class="main-nav" type="dark" variant="info">
    <b-navbar-brand to="/home"> Hotpot App </b-navbar-brand>
  </b-navbar>
</template>

<script>
export default {
  name: 'MainNav',
  computed: {},
  mounted() {},
}
</script>

<style scoped lang="scss">
.main-nav {
  display: flex;
  justify-content: space-between;
  background-color: $primary-color !important;
}
.buttons {
  display: flex;
}
.nav-button {
  font-size: $grip-title-font-size;
  background-color: inherit;
  color: $light-text-color;
  border-width: 0;
  cursor: pointer;
}
</style>
