<script setup>
	import 'bootstrap/dist/css/bootstrap.min.css'
	import 'bootstrap/dist/js/bootstrap.bundle.min.js'
	import Navigationbar from './components/Navigationbar.vue';
	import { ref } from 'vue';
	const navbarHeight = ref(0);// 儲存 Navigationbar 的高度
	const updateNavbarHeight = (height) => {
		navbarHeight.value = height;
	};
</script>

<template>
  <div class="layout" >
		<header :style="{ paddingBottom: navbarHeight + 'px' }">
			<Navigationbar class="navbar" @updateHeight="updateNavbarHeight" ></Navigationbar>
		</header>
		<div class="content" >
			<RouterView></RouterView>
		</div>
	</div>
</template>

<style scoped>
	header {
		display: block; 
		position: relative;
	}

	.layout {
		display: flex;
		flex-direction: column;
		height: 100vh; 
		overflow: hidden;
	}

	.navbar {
		flex-shrink: 0; 
		z-index: 10; 
		box-sizing: border-box;
	}

	.content {
		flex-grow: 1; 
		height: 100vh;
		overflow-y: auto;
	}
</style>
