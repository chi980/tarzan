<template></template>

<script lang="ts" setup>
import { useRouter } from "vue-router";

const urlParams = new URLSearchParams(window.location.search);
const message = urlParams.get("message");
alert(message);

const router = useRouter();
router.push("/login");
</script>

<style></style>
