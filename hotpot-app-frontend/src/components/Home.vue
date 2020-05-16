<template>
    <div class="home">
        <div class="services">
            <ServiceList
                    :services="this.$store.state.services"/>
        </div>
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
      console.log("mounttee");
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
        max-height: 100vh;
        margin-bottom: $small-margin;
    }

    .services {
        display: flex;
        flex: 1;
    }
</style>
