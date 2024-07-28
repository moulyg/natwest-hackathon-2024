import axios from 'axios'
import { setRequestInterceptor } from './request-interceptor'
import { setError, setLoader } from '../actions/common'

const base_url = 'http://localhost:8080/api/v1'

setRequestInterceptor()

export default function createRequest(
    dispatch,
    endpoint,
    method = 'GET',
    data = {},
    headers = {},
    callbackFn
) {
    dispatch(setLoader(true))
    axios({
        url: base_url + endpoint,
        method,
        data: data,
        headers,
        changeOrigin: true
    })
        .then(function (response) {
            // dont stop loader in case of redirection
            if(!endpoint.includes('init')){
                dispatch(setLoader(false))
            }
            callbackFn(response.data)
        })
        .catch(function (error) {
            dispatch(setLoader(false))
            dispatch(setError(error))
        })
}
