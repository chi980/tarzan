import 'vue';

declare module '@vue/runtime-core' {
  interface ComponentCustomProperties {
    $axios: typeof import('axios').default;
  }
}
