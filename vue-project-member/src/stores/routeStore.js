import { defineStore } from 'pinia';

export const useRouteStore = defineStore('route', {
    state: () => ({
        memberId: null,
    }),
    actions: {
        setMemberId(id) {
            this.memberId = id;
        },
    },
});