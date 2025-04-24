<template>
  <div>
    <List
      v-if="props.params"
      :fetchItems="fetchSaleRealEstate"
      :params="props.params">
      <template #item="{ item }">
        <SaleRealEstateItem :data="item" :type="'매매'" />
      </template>
    </List>
  </div>
</template>

<script setup lang="ts">
import { defineProps } from "vue";
import { axiosInstance } from "@/plugins/axiosPlugin";
import List from "@/components/common/List.vue";
import SaleRealEstateItem from "@/components/fraud/SaleRealEstateItem.vue";

const props = defineProps({
  params: {
    type: Object,
    required: true,
  },
});

const fetchSaleRealEstate = async (page: number, params: any) => {
  try {
    console.log(params);
    const response = await axiosInstance.get(`/fraud/price/sale`, {
      params: {
        ...params,
        page: page,
      },
    });

    if (response.data.success) {
      return response.data.data.list;
    } else {
      console.error("Failed to fetch data:", response.data.message);
      return [];
    }
  } catch (error) {
    console.error("API request error:", error);
    return [];
  }
};
</script>

<style scoped lang="scss"></style>
