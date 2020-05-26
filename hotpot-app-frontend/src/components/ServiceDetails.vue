<template>
    <div class="service-details">
        <p class="service-title"> Service: {{ $route.params.sid }} </p>

        <div>
            <b-tabs lazy v-model="tabIndex">
                <b-tab title="Basic Information" @click="onTabClick(0)">
                    <div class="service-details-list">
                        <dl class="service-detail" v-for="detail in details" :key="detail.key">
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
                <b-tab title="Service Objective Results" @click="onTabClick(1)">
                    <div class="service-details-list">
                        <dl class="service-detail" v-for="result in objectiveResults" :key="result.objectiveId">
                            <dt>
                                <router-link :to="'/objectives/' + result.objectiveId">
                                    {{result.objectiveId}}
                                </router-link>
                            </dt>
                            <dd>
                                <span v-bind:class="{warning: !result.status}">
                                    {{ result.status || 'Not Available'}}
                                </span>
                            </dd>
                        </dl>
                    </div>
                </b-tab>
                <b-tab title="Service Metric Values" @click="onTabClick(2)">
                    <div class="service-details-list">
                        <dl class="service-detail" v-for="result in metricResults" :key="result.metricId">
                            <dt>
                                <router-link :to="'/metrics/' + result.metricId">
                                    {{result.metricId}}
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


    </div>
</template>

<script>
  import http from '../utils/http'

  export default {
    name: 'ServiceDetails',
    data() {
      return {
        details: null,
        objectiveResults: null,
        metricResults:null,
        tabIndex: this.$route.query.tab || 0
      }
    },
    mounted() {
      this.populateService();
      this.populateMetrics();
    },
    methods: {
      populateService: async function () {
        const response = await http.get(`v1/services/${this.$route.params.sid}`);
        const metaData = response.data.service.metaData;
        this.objectiveResults = response.data.results;

        this.details = [
          {
            key: "tier",
            display: "Tier",
            value: metaData.tier && `Tier ${metaData.tier.value}`
          }, {
            key: "owner",
            display: "Owner",
            value: metaData.owner && metaData.owner.name && metaData.owner.name.value
          }, {
            key: "channel",
            display: "Channel",
            value: metaData.channel && metaData.channel.value
          }, {
            key: "repository",
            display: "Repository",
            value: metaData.repository && metaData.repository.location && metaData.repository.location.value
          }, {
            key: "oncallTeam",
            display: "Team on-call",
            value: metaData.currentOnCallTeam && metaData.currentOnCallTeam.name && metaData.currentOnCallTeam.name.value
          }, {
            key: "oncallPerson",
            display: "Person on-call",
            value: metaData.currentOnCallPerson && metaData.currentOnCallPerson.name
          }, {
            key: "version",
            display: "Version",
            value: metaData.currentVersion && metaData.currentVersion.value
          }, {
            key: "selfLocation",
            display: "Running at",
            value: metaData.selfLocation && metaData.selfLocation.value
          }, {
            key: "metricsLocation",
            display: "Metrics",
            value: metaData.metricsLocation && metaData.metricsLocation.value
          }, {
            key: "docsLocation",
            display: "Documentation",
            value: metaData.docsLocation && metaData.docsLocation.value
          }, {
            key: "logsLocation",
            display: "Logs",
            value: metaData.logsLocation && metaData.logsLocation.value
          }
        ];

      },
      populateMetrics: async function () {
        const response = await http.get(`v1/metrics/values/${this.$route.params.sid}`);
        this.metricResults = response.data;

        Object.keys(this.metricResults).forEach((key) => {
          if (!Number.isNaN(Number.parseFloat(this.metricResults[key].value))) {
            this.metricResults[key].value = Number.parseFloat(this.metricResults[key].value).toFixed(2);
          }
        });
      },
      onTabClick: function(index) {
        this.$router.push(this.$route.path + `?tab=${index}`)
      }
    },
  }
</script>
<style scoped lang="scss">
    .service-details {
        align-self: center;
        width: $main-section-max-width;
    }

    .service-title {
        margin: $small-margin;
        font-size: $section-title-font-size;
    }

    .service-detail {
        width: 33%; /* to ensure 3 columns */
        max-height: $dl-height;
        padding: 0 $small-padding;
    }

    .service-details-list {
        display: flex;
        flex-wrap: wrap;
        justify-content: flex-start;
        overflow: auto;
        margin-top: $small-margin;
    }

    .warning {
        background-color: $warning;
    }

</style>
