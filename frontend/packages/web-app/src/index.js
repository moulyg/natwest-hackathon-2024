import React from 'react'
import ReactDOM from 'react-dom'
import { Provider } from 'react-redux'
import { createBrowserHistory } from 'history'
import store from '@openbanking/ui-data/lib/store'
import networkService from '@openbanking/ui-data/lib/services/network-service'
import App from './App'
import Result from './Result'
import './index.css'
import {
                      BrowserRouter as Router,
                      Switch,
                      Route,
                    } from "react-router-dom";

// bootstrapping react app

const history = createBrowserHistory()
networkService.setupInterceptors(store, history)

ReactDOM.render(
    <Provider store={store}>
        <React.StrictMode>
        <Router>
            <Switch>
            <Route exact path="/">
                <App />
            </Route>
            <Route path="/redirect">
                <Result />
            </Route>
            </Switch>
        </Router>
        </React.StrictMode>
    </Provider>,
    document.getElementById('root')
)
