import React from 'react'
import ErrorBoundary from '@openbanking/ui-common/lib/ErrorBoundary'
import './App.css'
import BankAccountSection from './BankAccountSection'
import Header from './Header'
import { Typography, Button, Card, Col, Row, Statistic, Space } from 'antd';
import { List, Divider, Layout } from 'antd';
const { Title } = Typography;
const { Content, Footer } = Layout;
// views
const list= [
{name: "Items:", amount: "GBP 18.12"},
{name: "Postage & Packing:", amount: "GBP 5.91"},
{name: "Exchange rate guarantee fee:", amount: "GBP 0.42"},
{name: "Order Total:", amount: "GBP 24.45"},
];

const App = () => {
const randomIntFromInterval = (min, max) => {
  const number = Math.floor(Math.random() * (max - min + 1) + min);
  window.sessionStorage.setItem("amount", number);
  return number;
}
    return (
        <div className="app">
         <Layout>
              <Header />
              <Content style={{ padding: '48px' }}>
            <ErrorBoundary>
                <Row gutter={16}>
                    <Col span={18}>
                     <Card bordered={false}>
                     <Title level={4} style={{ margin: 0 }}>1 Choose a delivery address</Title>
          <Divider />
                     <Title level={4} style={{ margin: 0 }}>2 Items and delivery</Title>
                               <Divider />
                     <Title level={4} style={{ margin: 0 }}>3 Payment method</Title>
                               <Divider />
                               <BankAccountSection />
                    <p>

Need help? Check our Help pages or contact us
When you click the "Buy now" button, we'll send you an e-mail message acknowledging receipt of your order. Your contract to purchase an item will not be complete until we send you an e-mail to indicate that the item has been dispatched.
Within 30 days of delivery, you may return new, unopened physical merchandise in its original condition. Exceptions and restrictions apply.
                    </p>
                    </Card>
                    </Col>
                    <Col span={6}>


  <Space direction="vertical" size="middle" style={{ display: 'flex' }}>
                      <Card bordered={false}
                      title={ <Title level={5}>Order Summary</Title>}
                      >
 <List
      itemLayout="horizontal"
      dataSource={list}
      renderItem={(item) => (
        <List.Item>
            <List.Item.Meta description={item.name} />
            <div><b>{item.amount}</b></div>
        </List.Item>
      )}
    />

      <p>Order totals include VAT. See details</p>

<form action="http://localhost:8080/api/v1/domestic-payments-consents" method="POST">
          <input type="hidden" value="50" name="amount" />
      <Button type="primary" htmlType="submit" block>Buy now</Button>
        </form>
                      </Card>
                      <Card bordered={false}
                          title={<Title level={5}>Your Sustainable Contribution</Title>}
                          >
                        <Statistic
                          value={randomIntFromInterval(5, 10)}
                          precision={2}
                          valueStyle={{ color: '#86bc25' }}
                          prefix={<div className="green-icon" />}
                          suffix=" GBP"
                        />
                        <p>Thank you for choosing to invest in sustainable products! Every purchase you make not only supports eco-friendly practices but also contributes to a healthier planet.</p>
                      </Card>

                      </Space>
                    </Col>
                  </Row>
            </ErrorBoundary>

              </Content>

      <Footer style={{ textAlign: 'center' }}>
        Natwest Summer Hackathon ©{new Date().getFullYear()} Created by FinFusion
      </Footer>

         </Layout>
        </div>
    )
}

export default App
