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

export function getDomesticPaymentStatus(dispatch) {
    createRequest(
        dispatch,
        '/domestic-payment-status',
        'GET',
        null,
        {},
        function (response) {
           // to do 
        }
    )
}
