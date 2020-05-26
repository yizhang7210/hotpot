<template>
    <div class="objective-details">
        <p class="objective-title">Objective: {{ $route.params.oid }}</p>

        <b-tabs lazy v-model="tabIndex">
            <b-tab title="Basic Information" @click="onTabClick(0)">
                <div class="objective-details-list">
                    <dl class="objective-detail">
                        <dt> Description</dt>
                        <dd> {{ objective.description }}</dd>
                        <dt> Criteria</dt>
                        <dd>
                            <ul>
                                <li v-for="metric in Object.keys(objective.criteria)" :key="metric">
                                    <router-link :to="'/metrics/' + metric"> {{metric}} </router-link>
                                    : {{ objective.criteria[metric] }}
                                </li>
                            </ul>
                        </dd>
                    </dl>
                </div>
            </b-tab>

            <b-tab title="Service Objective Results" @click="onTabClick(1)">
                <div class="objective-details-list">
                    <dl class="objective-results" v-for="result in serviceResults" :key="result.serviceId">
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
            </b-tab>
        </b-tabs>
    </div>
</template>

<script>
  import http from '../utils/http'

  export default {
    name: 'ObjectiveDetails',
    data() {
      return {
        objective: {description: '', criteria: {}},
        serviceResults: null,
        tabIndex: this.$route.query.tab || 0
      }
    },
    mounted() {
      this.populateService();
    },
    methods: {
      populateService: async function () {
        const response = await http.get(`v1/objectives/${this.$route.params.oid}`);
        this.objective = response.data.objective;
        this.serviceResults = response.data.results;
      },
      onTabClick: function(index) {
        this.$router.push(this.$route.path + `?tab=${index}`)
      }
    },
  }
</script>
<style scoped lang="scss">
    .objective-details {
        align-self: center;
        width: $main-section-max-width;
    }

    .objective-title {
        margin: $small-margin;
        font-size: $section-title-font-size;
    }

    .objective-detail {
        padding: 0 $small-padding;
    }

    .objective-results {
        width: 33%; /* to ensure 3 columns */
        max-height: $dl-height;
        padding: 0 $small-padding;
    }

    .objective-details-list {
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
