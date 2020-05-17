<template>
    <div class="service-details">
        <p class="service-title">{{ this.$route.params.sid }}</p>
        <div class="service-details-list">
            <dl class="service-detail" v-for="detail in this.details" :key="detail.key">
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
    </div>
</template>

<script>
  import http from '../utils/http'

  export default {
    name: 'ServiceDetails',
    data() {
      return {
        details: null,
      }
    },
    mounted() {
      this.populateService();
    },
    computed: {},
    methods: {
      populateService: async function () {
        const response = await http.get(`v1/services/${this.$route.params.sid}`);
        const metaData = response.data.metaData;

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
        font-size: $section-title-font-size;
        margin: $small-margin;
    }

    .service-detail {
        width: 50%; /* to ensure 2 columns */
        max-height: $dl-height;
        padding: $small-padding;
    }

    .service-details-list {
        display: flex;
        flex-wrap: wrap;
        justify-content: space-between;
        overflow: auto;
    }

    .warning {
        background-color: $warning;
    }

</style>
