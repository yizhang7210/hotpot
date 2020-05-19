<template>
    <div class="objective-details">
        <p class="objective-title">{{ this.$route.params.oid }}</p>
        <p class="objective-subtitle"> Basic Information </p>
        <div class="objective-details-list">
            <dl class="objective-detail" v-for="detail in this.details" :key="detail.key">
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
        <p class="objective-subtitle"> Service Objective Results</p>
        <div class="objective-details-list">
            <dl class="objective-detail" v-for="result in this.serviceResults" :key="result.serviceId">
                <dt>
                    {{result.serviceId}}
                </dt>
                <dd>
                    <span v-bind:class="{warning: !result.status}">
                        {{ result.status || 'Not Available'}}
                    </span>
                </dd>
            </dl>
        </div>
    </div>
</template>

<script>
  import http from '../utils/http'

  export default {
    name: 'ObjectiveDetails',
    data() {
      return {
        details: null,
        serviceResults: null
      }
    },
    mounted() {
      this.populateService();
    },
    computed: {},
    methods: {
      populateService: async function () {
        const response = await http.get(`v1/objectives/${this.$route.params.oid}`);
        const objective = response.data.objective;
        this.serviceResults = response.data.results;

        this.details = [
          {
            key: "description",
            display: "Description",
            value: objective.description
          }
        ];

      }
    },
  }
</script>
<style scoped lang="scss">
    .objective-details {
        align-self: center;
        width: $main-section-max-width;
        margin: $small-margin;
    }

    .objective-title {
        font-size: $section-title-font-size;
    }

    .objective-subtitle {
        font-size: $subtitle-font-size;
    }

    .objective-detail {
        width: 33%; /* to ensure 3 columns */
        max-height: $dl-height;
    }

    .objective-details-list {
        display: flex;
        flex-wrap: wrap;
        justify-content: flex-start;
        overflow: auto;
        margin: 0 $small-margin;
    }

    .warning {
        background-color: $warning;
    }

</style>
