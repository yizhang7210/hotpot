<template>
    <div class="services-container">
        <b-form-input
                v-model="filter"
                type="search"
                class="search"
                placeholder="Type to Search"
        />
        <b-table
                class="services-table"
                striped
                hover
                bordered
                responsive="true"
                sticky-header="100vh"
                :current-page="currentPage"
                :per-page="perPage"
                :sort-by.sync="sortBy"
                :filter="filter"
                :fields="fields"
                :items="services"
                @filtered="onFiltered"
        />
        <b-pagination
                v-model="currentPage"
                :total-rows="totalRows"
                :per-page="perPage"
                align="center"
        />
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
        filter: null,
        totalRows: 0,
        currentPage: 1,
        perPage: 10,
        sortBy: 'id'
      }
    },
    mounted() {
      this.totalRows = this.services.length
    },
    computed: {
      fields() {
        return Object.keys(this.services[0]).map(k => ({key: k, sortable: true}))
      }
    },
    methods: {
      onFiltered(filteredItems) {
        // Trigger pagination to update the number of buttons/pages due to filtering
        this.totalRows = filteredItems.length;
        this.currentPage = 1;
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
        align-self: center;
    }
    .services-table {
        align-self: center;
    }
    .search {
        display: flex;
        align-self: center;
        max-width: $main-section-max-width;
        margin: $small-margin 0;
    }
</style>