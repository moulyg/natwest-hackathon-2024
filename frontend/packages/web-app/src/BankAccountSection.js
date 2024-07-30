import React, { useState } from "react";
import { Typography, Card } from "antd";

import {
  Divider,
  List,
  Avatar,
} from "antd";
const { Title } = Typography;
const data = [
  {
    title: "Natwest Bank",
    description: "Earn Rewards with NatWest Bank!",
    url: "/images/natwest.jpg",
    id: 1,
  },
  {
    title: "Royal Bank of Scotland",
    description: "Earn Rewards with Royal Bank of Scotland!",
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

export default function BankAccountSection() {
  const [value, setValue] = useState(0);
  const onChange = (index) => {
    setValue(index);
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
          <List
           role="list"
            itemLayout="horizontal"
            dataSource={data}
            renderItem={(item,index) => (
              <List.Item aria-label="Item" role="listitem" tabIndex="0" className={(index === value ? "selected" : "" )} onClick={() => onChange(index)}>
                <List.Item.Meta
                  avatar={<Avatar alt={item.title} src={item.url} />}
                  title={item.title}
                  description={item.description}
                />
              </List.Item>
            )}
          />
          <Divider />
      </Card>
    </div>
  );
}
