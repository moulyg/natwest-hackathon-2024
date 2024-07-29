import createRequest from './request'

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

