import React from "react";
import { BrowserRouter as Router, Routes, Route } from "react-router-dom";
import Home from "./components/Home";
import Recommendation from "./components/Recommendation";
import ExchangeRatio from "./components/ExchangeRatio";
import Login from "./components/Login";
import { Nav } from "./Nav";
import SignUp from "./components/SignUp";


const App = () => {
  return (
    <Router>
      <Nav/>
      <Routes>
        <Route/>
        <Route exact path='/' element={<Home/>}/>
        <Route path="/recommendation" element={<Recommendation/>}/>
        <Route path="/exchange" element={<ExchangeRatio/>}/>
        <Route path='/login/*' element={<Login/>}/>
        <Route path='/signup' element={<SignUp/>}/>
      </Routes>
    </Router>
  )
}

export default App