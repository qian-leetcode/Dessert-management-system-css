import {defineStore } from 'pinia'

export const useLoginStore = defineStore('user' ,{
    state: () => ({
        token:'',
        role:''
    }),
    actions :{
        setToken (token)  {
            this.token = token
        },
        setRole (state, role) {
            this.role = role
        }
    }
})
