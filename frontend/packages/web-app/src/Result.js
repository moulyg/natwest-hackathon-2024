import React, { useEffect } from "react";
import { Skeleton, Result } from "antd";

import Header from './Header'
import { connect } from 'react-redux'
import { createDomesticPayment } from '@openbanking/ui-data/lib/services/payment-service'
import { parse } from 'query-string'
import {
  Layout,
} from "antd";
const { Content } = Layout;

const ResultPage = ({createDomesticPaymentFn, loading, error}) => {
  useEffect(() => {
    const { code, state } = parse(window.location.hash.substring(1))
    createDomesticPaymentFn({code, state, sustainableProductsAmount:  window.sessionStorage.getItem("amount")})
  }, []);
  return (
     <Layout>
                  <Header />
                  <Content style={{ padding: '48px' }}>
      {loading ? (
        <div style={{ width: '400px', textAlign: 'center', margin: '0 auto' }}>
        <img  style={{ borderRadius: '100%'}} src="images/loading.png" alt="loading" width="200" height="200"/>
        <h3>Please wait while we securely process your payment. This may take a few moments.

            Thank you for your patience.

</h3>
        <Skeleton />
        </div>
      ) : (
      error ? <Result
                 status={error.response.status}
                 title="Sorry, something went wrong."
                 subTitle={error.response.data.message}
               /> :
                <div style={{ width: '400px', textAlign: 'center', margin: '0 auto' }}>

                <Result
                icon={ <img  style={{ borderRadius: '100%'}} src="images/Sucessful.png" alt="loading" width="200" height="200"/>}
                   title="Payment Successful - Thank You for Your Purchase!"
                   subTitle="Order number: 2017182818828182881. Thank you for choosing Fin Fusion Online Market. Your order is now being processed, and you will receive a confirmation email with tracking information once your items have shipped."
                 />
                 </div>

      )}
    </Content>
    </Layout>
  );
}

const mapStateToProps = (state) => ({
    loading: state.common.loading,
    error: state.common.error,
})

const mapDispatchToProps = (dispatch) => ({
    createDomesticPaymentFn: (payload) => createDomesticPayment(dispatch, payload),
})

export default connect(mapStateToProps, mapDispatchToProps)(ResultPage)

