<template>
    <div class="service-list">
        <b-form-input
                v-model="filter"
                type="search"
                class="services-search"
                placeholder="Type to Search"
        />
        <b-table
                class="services-table"
                striped
                hover
                responsive="true"
                sticky-header="100vh"
                :current-page="currentPage"
                :per-page="perPage"
                :sort-by.sync="sortBy"
                :filter="filter"
                :fields="fields"
                :items="services"
                @filtered="onFiltered"
                @row-clicked="onRowClicked"
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
      },
      onRowClicked(item) {
        this.$router.push(`/services/${item.id}`);
      }
    },
  }
</script>
<style scoped lang="scss">
    .service-list {
        display: flex;
        flex-direction: column;
        flex: 1;
        overflow: auto;
        align-self: center;
        width: 100%;
    }

    .services-table {
        align-self: center;
        width: $main-section-max-width;
        cursor: pointer;
    }

    .services-search {
        display: flex;
        align-self: center;
        width: $main-section-max-width;
        margin: $small-margin 0;
    }
</style>
