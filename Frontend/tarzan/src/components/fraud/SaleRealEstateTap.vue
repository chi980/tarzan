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
      return {
        items: response.data.data.list,
        isNext: response.data.data.isNext,
      };
    } else {
      console.error("Failed to fetch data:", response.data.message);
      return {
        items: [],
        isNext: false,
      };
    }
  } catch (error) {
    console.error("API request error:", error);
    return {
      items: [],
      isNext: false,
    };
  }
};
</script>

<style scoped lang="scss"></style>
