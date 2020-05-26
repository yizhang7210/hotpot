<template>
    <div class="objectives-list">
        <div class="objectives-header">
            <p class="objectives-title">Objectives</p>
            <b-form-input
                    v-model="filter"
                    type="search"
                    class="objectives-search"
                    placeholder="Type to search objectives"
            />
        </div>
        <b-table
                class="objectives-table"
                striped
                hover
                responsive="true"
                sticky-header="100vh"
                :current-page="currentPage"
                :per-page="perPage"
                :sort-by.sync="sortBy"
                :filter="filter"
                :fields="fields"
                :items="$store.state.objectives"
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
    name: 'ObjectiveList',
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
      this.$store.dispatch('fetchAllObjectives');
    },
    computed: {
      fields() {
        return ['id', 'description']
      }
    },
    methods: {
      onFiltered(filteredItems) {
        // Trigger pagination to update the number of buttons/pages due to filtering
        this.totalRows = filteredItems.length;
        this.currentPage = 1;
      },
      onRowClicked(item) {
        this.$router.push(`/objectives/${item.id}`);
      }
    },
  }
</script>
<style scoped lang="scss">
    .objectives-list {
        display: flex;
        flex-direction: column;
        flex: 1;
        overflow: auto;
        align-self: center;
        width: 100%;
    }
    .objectives-table {
        align-self: center;
        width: $main-section-max-width;
        cursor: pointer;
    }
    .objectives-header {
        display: flex;
        align-self: center;
        align-items: center;
        width: $main-section-max-width;
    }
    .objectives-title {
        margin: $small-margin;
        font-size: $section-title-font-size;
        flex: 1;
    }
    .objectives-search {
        flex: 1;
    }
</style>
