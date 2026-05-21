import {defineStore } from 'pinia'

export const loginStore = defineStore('user' ,{
    state: () => ({
        token:' ',
        role:''
    }),
    actions :{
        setToken:(token) => {
            this.token = token
        },
        setRole (state, role) {
            this.role = role
        }
    }
})
