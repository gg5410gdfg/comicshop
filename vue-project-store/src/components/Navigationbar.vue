<template>
    <nav ref="navbarRef" class="navbar bg-body-tertiary fixed-top">
        <div class="container-fluid">
            <RouterLink to="/" class="navbar-brand">bookstore</RouterLink>
            <button class="navbar-toggler" type="button" data-bs-toggle="offcanvas" data-bs-target="#offcanvasNavbar" aria-controls="offcanvasNavbar" aria-label="Toggle navigation">
            <span class="navbar-toggler-icon"></span>
            </button>
            <div class="offcanvas offcanvas-end" tabindex="-1" id="offcanvasNavbar" aria-labelledby="offcanvasNavbarLabel">
                <div class="offcanvas-header">
                    <h5 class="offcanvas-title" id="offcanvasNavbarLabel">書店導覽</h5>
                    <button type="button" class="btn-close" data-bs-dismiss="offcanvas" aria-label="Close"></button>
                </div>
                <div class="offcanvas-body">
                    <ul class="navbar-nav">
                        <li class="nav-item dropdown">
                            <a class="nav-link dropdown-toggle" href="#" role="button" data-bs-toggle="dropdown" aria-expanded="false">
                            書店後台
                            </a>
                            <ul class="dropdown-menu">
                                <li class="nav-item"><RouterLink to="/" class="nav-link" aria-current="page" >書店管理</RouterLink></li>
                                <li class="nav-item"><RouterLink to="/back/insert" class="nav-link" aria-current="page" >書店一覽</RouterLink></li>
                                <li class="nav-item"><RouterLink to="/back/orders" class="nav-link" aria-current="page" >訂單管理</RouterLink></li>
                                
                            </ul>
                        </li>
                    </ul>
                </div>
            </div>
        </div>
    </nav>
</template>

<script setup>
    import { onMounted, ref, } from 'vue';
    import { useRouteStore } from '../stores/routeStore';
    const routeStore = useRouteStore();
    const navbarRef = ref(null); 
    const emits = defineEmits(["updateHeight"])
    onMounted(() => {
        const navbarHeight = navbarRef.value.offsetHeight;
        emits('updateHeight', navbarHeight); 
    });
</script>

<style scoped>
    .navbar {
        position: fixed;
        width: 100%;
        z-index: 1000;
        padding: 1rem 0;
        transition: all 0.3s ease;
    }

    .navbar-brand {
        font-size: 1.8rem;
        font-weight: 700;
        background: linear-gradient(45deg, #FF3CAC 0%, #784BA0 50%, #2B86C5 100%);
        -webkit-background-clip: text;
        -webkit-text-fill-color: transparent;
    }

    .navbar .nav-link {
        /* color: white !important; */
        padding: 0.5rem 1.2rem !important;
        margin: 0 0.3rem;
        font-size: 1.1em;
        transition: all 0.3s ease;
        border-radius: 25px;
    }

    .navbar .nav-link:hover {
        background-color: rgba(255, 255, 255, 0.1);
        transform: translateY(-2px);
    }
</style>