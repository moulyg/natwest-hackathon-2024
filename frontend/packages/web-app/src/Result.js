import React, { useState, useEffect } from "react";
import type { RadioChangeEvent } from "antd";
import { Skeleton, Typography, Button, Card, Col, Row, Statistic, Space, Result } from "antd";

import { connect } from 'react-redux'
import { createDomesticPayment } from '@openbanking/ui-data/lib/services/payment-service'
import { parse } from 'query-string'
import {
  Divider,
  List,
  Radio,
  Avatar,
  Breadcrumb,
  Layout,
  Menu,
  theme,
} from "antd";
const { Title } = Typography;

const ResultPage = ({createDomesticPaymentFn, loading, error}) => {
  useEffect(() => {
    const { code, state } = parse(window.location.hash.substring(1))
    createDomesticPaymentFn({code, state, sustainableProductsAmount: "50"})
  }, []);
  return (
    <div>
      {loading ? <Skeleton /> : (
      error ? <Result
                 status={error.response.status}
                 title="Sorry, something went wrong."
                 subTitle={error.response.data.message}
               /> :
               <Result
                   status="success"
                   title="Successfully"
                   subTitle="Order number: 2017182818828182881 "
                 />

      )}
    </div>
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

