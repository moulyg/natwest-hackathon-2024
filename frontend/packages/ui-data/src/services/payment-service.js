import createRequest from './request'
import { setData, setError } from '../actions/common'

export function createDomesticPayment(dispatch, payload) {
    createRequest(
        dispatch,
        '/domestic-payments',
        'POST',
        { ...payload },
        {},
        function (response) {
           console.log(response);
        }
    )
}

export function getRewardPoints(dispatch, payload) {
    createRequest(
        dispatch,
        '/cashback/accounts/' + payload,
        'GET',
        {},
        {},
        function (response) {
           console.log(response);
           dispatch(setData(response));
        }
    )
}

