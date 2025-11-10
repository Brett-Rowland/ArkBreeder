import { useState } from 'react'
import { Route, Routes } from 'react-router'
import Login from './pages/Login'
import Register from './pages/Register'
import BreedingLines from './pages/BreedingLines'

export default function App() {

  return (
    <>
      <Routes>
        <Route path="/" element={<Login/>}/>
        <Route path='/registar' element={<Register/>}/>
        <Route path="/breeding-lines" element={<BreedingLines/>}/>
      </Routes>
    </>
  )
}