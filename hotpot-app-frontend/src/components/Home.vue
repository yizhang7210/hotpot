<template>
    <div class="home">
        <ServiceList
                :services="this.$store.state.services"/>
    </div>
</template>

<script>
  import ServiceList from './ServiceList'

  export default {
    name: 'Home',
    components: {
      ServiceList
    },
    mounted() {
      this.$store.dispatch('fetchAllServices');
    },
  }
</script>

<style scoped lang="scss">
    .home {
        display: flex;
        flex-direction: column;
        flex: 1;
        max-width: 100vw;
        max-height: 150vh;
        margin-bottom: $small-margin;
    }

    .service-list {
        display: flex;
        flex: 1;
    }
</style>
