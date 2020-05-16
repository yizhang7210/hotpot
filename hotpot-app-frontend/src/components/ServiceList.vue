<template>
    <div class="services-container">
        <b-form-input
                v-model="filter"
                type="search"
                placeholder="Type to Search"
        />
        <b-table
                striped
                hover
                :filter="filter"
                :items="services"></b-table>
    </div>
</template>

<script>
  export default {
    name: 'ServiceList',
    props: [
      'services',
    ],
    data() {
      return {
        filter: null

      }
    },
    methods: {
      onSearchInput: function (value) {
        if (!value) {
          this.$store.dispatch('fetchAllGrips');
          return;
        }
        this.$store.dispatch('searchGrips', value);
      }
    },
  }
</script>
<style scoped lang="scss">
    .services-container {
        display: flex;
        flex-direction: column;
        flex: 1;
        overflow: auto;
    }
</style>