import React from 'react'

import { Layout} from 'antd';
const { Header } = Layout;
const HeaderSection = () => {

    return (
         <Header style={{ display: 'flex', alignItems: 'center', color: "#fff" }}>
                        <img alt="logo" width="48" height="48" src="/images/logo.png" /> <h2 style={{paddingLeft: "16px", color: "#fff" }}>Fin Fusion Online Market</h2>
                      </Header>
    )
}

export default HeaderSection
