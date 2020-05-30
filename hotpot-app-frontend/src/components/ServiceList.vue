<template>
    <div class="services-list">
        <div class="services-header">
            <p class="services-title">Services</p>
            <b-form-input
                    v-model="filter"
                    type="search"
                    class="services-search"
                    placeholder="Type to search service"
            />
        </div>
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
        return this.services.length > 0 && Object.keys(this.services[0]).map(k => ({key: k, sortable: true}))
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
    .services-list {
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
    .services-header {
        display: flex;
        align-self: center;
        align-items: center;
        width: $main-section-max-width;
    }
    .services-title {
        margin: $small-margin;
        font-size: $section-title-font-size;
        flex: 1;
    }
    .services-search {
        flex: 1;
    }
</style>
