import React, {useState} from 'react'
import { connect } from 'react-redux'
import './mobile.css'
import { Button, Flex, Table, Skeleton } from 'antd';
import { getRewardPoints } from '@openbanking/ui-data/lib/services/payment-service'
const RewardScreen = ({getRewardPointsFn, loading, data}) => {
    const items= ["My Transaction", "Payment & Transfer", "My Rewards","Manage my card & Apple Pay",
    "My Overdraft", "Get Cash", "Round Ups", "Direct Debits", "Standing Orders"]
    const newItems = [2, 3,5];
    const [show, setShow] = useState(0);
    const expandSection = (index) => {
     if (index === 2) {
        setShow(index);
        getRewardPointsFn("NWBK50000012345601");
     } else {
        setShow(0);
     }
    }
    const columns = [
      {
        title: 'Date',
        dataIndex: 'createdDate',
        key: 'createdDate',
        render: (text) => text.split('T')[0]
      },
      {
        title: 'Transaction Amount',
        dataIndex: 'transactionAmount',
        key: 'transactionAmount',
        render: (text) => "£" + text
      },
      {
        title: 'Cashback Value',
        dataIndex: 'cashbackValue',
        key: 'cashbackValue',
        render: (text) => "£" + text
      },
    ];
    return (

        <div className="mobile-screen">
            <header>
            <small>&#8592;</small>
                <div className="h1">My Current account</div>
            </header>
            <div className="scroll">
            <div className="slider">
            <img src="/images/natwest.jpg" alt="natwest" />
                <h5>John Block</h5>
                <p><small>&#9432;</small>Select Account | NWBK50000012345601 | 51-81-22</p>
                <strong><small>&#9432;</small> £ 73.50</strong>
            </div>
            <div className="pagination">
                <a href="/mobile">&lt;</a>
                <a href="/mobile" className="pink">&#9633;</a>
                <a href="/mobile" className="pink">&#9633;</a>
                <a href="/mobile">&gt;</a>
            </div>

            <div className="menu">
            {items.map((item, index) => (<div key={index}>
            <div onClick={() => expandSection(index)} className="item">
            <p>{item}</p>
            <div>{newItems.indexOf(index) !== -1 ? <small>New</small> : null}<a href="/mobile" className="pink">&gt;</a></div>
            </div>
            { show === index && <div className="reward-section">
            {data && data.totalCashback}
            {loading && <Skeleton />}
            {data && data.cashBacks && <div><Table
            columns={columns}
            pagination={false}
            dataSource={data.cashBacks}
            summary={() => (
                    <Table.Summary fixed>
                      <Table.Summary.Row>
                        <Table.Summary.Cell index={0}>Total Cashback</Table.Summary.Cell>
                        <Table.Summary.Cell index={0}></Table.Summary.Cell>
                        <Table.Summary.Cell index={1}>£{data.totalCashback}</Table.Summary.Cell>
                      </Table.Summary.Row>
                    </Table.Summary>
                  )}
            />
             <Flex vertical gap="small" style={{marginTop: 10, width: '100%' }}>
                <Button type="primary" block>Bank your Rewards</Button>
                <Button type="primary" block>Exchange your Rewards</Button>
                <Button type="primary" block>Donate your Rewards</Button>
              </Flex>
            </div>}
            </div>}
            </div>
            ))}
            </div>


            </div>
        </div>
    )
}

const mapStateToProps = (state) => ({
    loading: state.common.loading,
    error: state.common.error,
    data: state.common.data,
})

const mapDispatchToProps = (dispatch) => ({
    getRewardPointsFn: (accountId) => getRewardPoints(dispatch, accountId),
})

export default connect(mapStateToProps, mapDispatchToProps)(RewardScreen)


