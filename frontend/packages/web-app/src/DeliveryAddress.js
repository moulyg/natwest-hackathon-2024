import React, { useState } from "react";
import type { RadioChangeEvent } from "antd";
import { Typography, Button, Card, Col, Row, Statistic, Space } from "antd";

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
const data = [
  {
    title: "Natwest Bank",
    description: "Earn Reward Points with NatWest Bank!",
    url: "/images/natwest.jpg",
    id: 1,
  },
  {
    title: "Royal Bank of Scotland",
    description: "Earn Reward Points with Royal Bank of Scotland!",
    url: "/images/rbs.png",
    id:2,
  },
  {
    title: "Lloyd Bank",
    description: "Cash Back!",
    url: "/images/lloyd.png",
    id: 3,
  },
  {
    title: "Halifax",
    description: "Cash Back!",
    url: "/images/Halifax.png",
    id: 4,
  },
];

export default function DeliveryAddress() {
  const [value, setValue] = useState(1);
  const onChange = (e: RadioChangeEvent) => {
    console.log("radio checked", e.target.value);
    setValue(e.target.value);
  };

  return (
    <div>
      <Card type="inner">
        <Title
          level={5}
          style={{
            margin: 0,
          }}
        >
          
          Bank Accounts
        </Title>
        <Divider />
        <Radio.Group onChange={onChange} value={value} className="block">
          <List
            itemLayout="horizontal"
            dataSource={data}
            renderItem={(item, index) => (
              <List.Item actions={[<Radio value={item.id} />]}>
                <List.Item.Meta
                  avatar={<Avatar src={item.url} />}
                  title={item.title}
                  description={item.description}
                />
              </List.Item>
            )}
          />
          <Title
            level={5}
            style={{
              margin: "16px 0 0 0",
            }}
          >Credit Cards
          </Title>
          <Divider />
        </Radio.Group>
      </Card>
    </div>
  );
}
