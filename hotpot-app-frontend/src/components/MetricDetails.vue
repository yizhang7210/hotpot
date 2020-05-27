<template>
    <div class="metric-details">
        <p class="metric-title"> Metric: {{ $route.params.mid }}</p>

        <b-tabs lazy v-model="tabIndex">
            <b-tab title="Basic Information" @click="onTabClick(0)" class="metric-tab-container">
                <div class="metric-details-list">
                    <dl class="metric-detail" v-for="detail in details" :key="detail.key">
                        <dt>
                            {{detail.display}}
                        </dt>
                        <dd>
                            <span v-bind:class="{warning: !detail.value}">
                                {{ detail.value || 'Not Available'}}
                            </span>
                        </dd>
                    </dl>
                </div>
            </b-tab>

            <b-tab title="Service Metric Values" @click="onTabClick(1)" class="metric-tab-container">
                <div class="metric-details-list">
                    <dl class="metric-detail" v-for="result in serviceResults" :key="result.serviceId">
                        <dt>
                            <router-link :to="'/services/' + result.serviceId">
                                {{result.serviceId}}
                            </router-link>
                        </dt>
                        <dd>
                            <span v-bind:class="{warning: !result.value}">
                                {{ result.value || 'Not Available'}}
                            </span>
                        </dd>
                    </dl>
                </div>
            </b-tab>
        </b-tabs>
    </div>
</template>

<script>
  import http from '../utils/http'
  import moment from 'moment'

  export default {
    name: 'MetricDetails',
    data() {
      return {
        details: null,
        serviceResults: null,
        tabIndex: this.$route.query.tab || 0
      }
    },
    mounted() {
      this.populateMetrics();
    },
    methods: {
      populateMetrics: async function () {
        const response = await http.get(`v1/metrics/${this.$route.params.mid}`);
        const metric = response.data.metric;
        this.serviceResults = response.data.results;

        Object.keys(this.serviceResults).forEach((key) => {
          if (!Number.isNaN(Number.parseFloat(this.serviceResults[key].value))) {
            this.serviceResults[key].value = Number.parseFloat(this.serviceResults[key].value).toFixed(2);
          }
        });

        this.details = [
          {
            key: "description",
            display: "Description",
            value: metric.description
          },
          {
            key: "timeSpan",
            display: "Measured over",
            value: moment.duration(metric.timeSpan).humanize() + ` (${metric.timeSpan})`
          }
        ];
      },
      onTabClick: function(index) {
        this.$router.push(this.$route.path + `?tab=${index}`)
      }
    },
  }
</script>
<style scoped lang="scss">
    .metric-details {
        align-self: center;
        width: $main-section-max-width;
    }

    .metric-title {
        font-size: $section-title-font-size;
        margin: $small-margin;
    }

    .metric-detail {
        width: 33%; /* to ensure 3 columns */
        max-height: $dl-height;
    }

    .metric-details-list {
        display: flex;
        flex-wrap: wrap;
        justify-content: flex-start;
        overflow: auto;
        margin-top: $small-margin;
    }

    .warning {
        background-color: $warning;
    }

    .metric-tab-container {
        padding: 0 $small-padding;
    }

</style>
