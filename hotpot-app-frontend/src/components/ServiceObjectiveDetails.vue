<template>
    <div class="result-details">
        <p class="result-title"> Service Objective Detail </p>

        <div class="result-container">
            <dl>
                <dt>Servivce</dt>
                <dd>
                    <router-link :to="`/services/${$route.params.sid}`">
                        {{ $route.params.sid }}
                    </router-link>
                </dd>
                <dt>Objective</dt>
                <dd>
                    <router-link :to="`/objectives/${$route.params.oid}`">
                        {{ $route.params.oid }}
                    </router-link>
                </dd>
            </dl>

            <b-table
                    class="metrics-table"
                    striped
                    hover
                    responsive="true"
                    sticky-header="100vh"
                    :fields="fields"
                    :items="criteria"
            />
        </div>
    </div>
</template>

<script>
  import http from '../utils/http'

  export default {
    name: 'ServiceObjectiveDetails',
    data() {
      return {
        objective: null,
        metricResults: null,
        fields: [
          {key: "name", label: "Metric"},
          {key: "description", label: "Description"},
          {key: "value", label: "Current Value"},
          {key: "measuredAt", label: "Measured At"}
        ],
        total: 0,
        successes: 0,
        failures: 0
      }
    },
    mounted() {
      this.populateObjective();
      this.populateMetrics();
    },
    computed: {
      criteria() {
        if (this.objective === null || this.metricResults === null) {
          return []
        }

        const criteria = Object.entries(this.objective.criteria)
          .map((entry) => ({name: entry[0], description: entry[1]}));

        criteria.forEach(criterion => {
          criterion['value'] = this.format(this.metricResults[criterion['name']].value);
          criterion['measuredAt'] = this.metricResults[criterion['name']].measuredAt;
        });
        return criteria;
      }
    },
    methods: {
      populateObjective: async function () {
        const response = await http.get(`v1/objectives/${this.$route.params.oid}`);
        this.objective = response.data.objective;
      },
      populateMetrics: async function () {
        const response = await http.get(`v1/metrics/values/${this.$route.params.sid}`);
        this.metricResults = response.data;
      },
      format: function (value) {
        if (!Number.isNaN(Number.parseFloat(value))) {
          return Number.parseFloat(value).toFixed(2);
        } else {
          return value;
        }
      }
    },
  }
</script>
<style scoped lang="scss">
    .result-details {
        align-self: center;
        width: $main-section-max-width;
    }

    .result-title {
        margin: $small-margin;
        font-size: $section-title-font-size;
    }

    .result-container {
        padding: 0 $small-padding;
    }

</style>
