<template>
    <div class="metrics-list">
        <div class="metrics-header">
            <p class="metrics-title">Metrics</p>
            <b-form-input
                    v-model="filter"
                    type="search"
                    class="metrics-search"
                    placeholder="Type to search metrics"
            />
        </div>
        <b-table
                class="metrics-table"
                striped
                hover
                responsive="true"
                sticky-header="100vh"
                :current-page="currentPage"
                :per-page="perPage"
                :sort-by.sync="sortBy"
                :filter="filter"
                :fields="fields"
                :items="$store.state.metrics"
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
    name: 'MetricList',
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
      this.$store.dispatch('fetchAllMetrics');
    },
    computed: {
      fields() {
        return Object.keys(this.$store.state.metrics[0]).map(k => ({key: k, sortable: true}))
      }
    },
    methods: {
      onFiltered(filteredItems) {
        // Trigger pagination to update the number of buttons/pages due to filtering
        this.totalRows = filteredItems.length;
        this.currentPage = 1;
      },
      onRowClicked(item) {
        this.$router.push(`/metrics/${item.id}`);
      }
    },
  }
</script>
<style scoped lang="scss">
    .metrics-list {
        display: flex;
        flex-direction: column;
        flex: 1;
        overflow: auto;
        align-self: center;
        width: 100%;
    }
    .metrics-table {
        align-self: center;
        width: $main-section-max-width;
        cursor: pointer;
    }
    .metrics-header {
        display: flex;
        align-self: center;
        align-items: center;
        width: $main-section-max-width;
    }
    .metrics-title {
        margin: $small-margin;
        font-size: $section-title-font-size;
        flex: 1;
    }
    .metrics-search {
        flex: 1;
    }
</style>
