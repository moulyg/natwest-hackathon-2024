import React from 'react'
import { useSelector } from 'react-redux'
import { Route, Switch, BrowserRouter } from 'react-router-dom'
import ErrorBoundary from '@openbanking/ui-common/lib/ErrorBoundary'
import Loading from '@openbanking/ui-common/lib/Loading'
import Error from '@openbanking/ui-common/lib/Error'
import NotFound from '@openbanking/ui-common/lib/NotFound'
import './App.css'
import DeliveryAddress from './DeliveryAddress'
import { ArrowDownOutlined, ArrowUpOutlined } from '@ant-design/icons';
import { Typography, Button, Card, Col, Row, Statistic, Space } from 'antd';
import { List, Breadcrumb, Layout, Menu, theme } from 'antd';
import { Collapse } from 'antd';
import type { CollapseProps } from 'antd';
const { Title } = Typography;
const { Header, Content, Footer } = Layout;
// views
const list= [
{name: "Items:", amount: "GBP 18,12"},
{name: "Postage & Packing:", amount: "GBP 5,91"},
{name: "Exchange rate guarantee fee:", amount: "GBP 0,42"},
{name: "Order Total:", amount: "GBP 24,45"},
];


const items: CollapseProps['items'] = [
  {
    key: '1',
    label: <Title level={5} style={{ margin: 0 }}>1 Choose a delivery address</Title>,
    children: <DeliveryAddress />,
  },
  {
    key: '2',
    label: <Title level={5} style={{ margin: 0 }}>2 Payment method</Title>,
    children: <DeliveryAddress />,
  },
  {
    key: '3',
    label: <Title level={5} style={{ margin: 0 }}>3 Items and delivery</Title>,
    children: <DeliveryAddress />,
  },
];
const App = () => {
    const loading = useSelector((state) => state.common.loading)
    const error = useSelector((state) => state.common.error)

    return (
        <div className="app">
         <Layout>
              <Header style={{ display: 'flex', alignItems: 'center' }}>
                <div className="demo-logo" />
              </Header>
              <Content style={{ padding: '48px' }}>
            <ErrorBoundary>
                <Row gutter={16}>
                    <Col span={18}>
                     <Card bordered={false}>
                    <Collapse accordion collapsible="header" ghost items={items} bordered={true} defaultActiveKey={['2']} />
                    <p>

Need help? Check our <a href="">Help pages</a> or <a href="">contact us</a>
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

<form action="http://localhost:8080/open-banking/v3/payment-submit" method="POST">
          <input type="hidden" value="50" name="amount" />
      <Button type="primary" htmlType="submit" block>Buy now</Button>
        </form>
                      </Card>
                      <Card bordered={false}
                          title={<Title level={5}>Your Sustainable Contribution</Title>}
                          >
                        <Statistic
                          value={9.3}
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
