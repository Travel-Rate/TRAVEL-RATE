import {BrowserRouter, Routes, Route} from 'react-router-dom';
import React from 'react';
import ExchangeRatio from '../components/ExchangeRatio';
import App from '../App';
import Recommendation from '../components/Recommendation';

const Router = () => {
    return (
        <BrowserRouter>
        <Routes>
            <Route path='/' element={<App/>}/>
            <Route path='/recommend' element={<Recommendation/>}/>
            <Route path='/exchange' element={<ExchangeRatio/>}/>

        </Routes>
        </BrowserRouter>
    )
}

export default Router