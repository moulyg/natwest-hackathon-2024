import { SET_LOADING,SET_ERROR, SET_DATA } from '../actionCreators/common'

export const setLoader = (loading) => {
    return {type:SET_LOADING,loading}
}

export const setError = (error) =>{
    return {type: SET_ERROR,error}
}

export const setData = (data) =>{
    return {type: SET_DATA,data}
}